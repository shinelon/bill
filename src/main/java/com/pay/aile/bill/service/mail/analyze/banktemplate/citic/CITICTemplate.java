package com.pay.aile.bill.service.mail.analyze.banktemplate.citic;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pay.aile.bill.service.mail.analyze.enums.CardTypeEnum;
import com.pay.aile.bill.service.mail.analyze.enums.MailKey;
import com.pay.aile.bill.service.mail.analyze.model.AnalyzeParamsModel;
import com.pay.aile.bill.service.mail.analyze.util.PatternMatcherUtil;

/**
 * 
 * @author Charlie
 * @description 中信银行解析模板
 */
@Service
public class CITICTemplate extends AbstractCITICTemplate {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void analyzeInternal(AnalyzeParamsModel apm) {
        logger.info("账单内容：{}", apm);
        String content = apm.getContent();
        if (keywords == null || keywords.isEmpty()) {
            throw new RuntimeException("账单关键字未初始化");
        }
        Iterator<Map.Entry<String, String>> it = keywords.entrySet().iterator();

        while (it.hasNext()) {
            Entry<String, String> en = it.next();
            String key = en.getKey();
            String value = en.getValue();//正则
            MailKey mailKey = MailKey.getByString(key);
            if (mailKey == null) {
                logger.warn("账单关键字未找到,key={}", key);
            }
            List<String> list = null;
            String[] sa = null;
            String result = "";
            list = PatternMatcherUtil.getMatcher(key, "", value, content);
            if ((list == null || list.isEmpty()) && mailKey != MailKey.preCash
                    && mailKey != MailKey.enCash) {
                logger.error("未匹配到内容,bank={},key={},reg={}", "CITIC", key,
                        value);
                throw new RuntimeException("未匹配到内容");
            }
            switch (mailKey) {
            case payDate://还款日
                result = list.get(0);
                String date = result.replaceAll("年", "/").replaceAll("月", "/")
                        .replaceAll("日", "");
                date = date.split("：")[1];
                System.out.println("还款日:" + date);
                break;
            case payAmount://应还款额
                result = list.get(0);
                sa = result.split(" ");
                String payAmount = sa[sa.length - 1];
                System.out.println("应还金额:" + payAmount);
                break;
            case minAmount://最低还款额
                result = list.get(0);
                sa = result.split(" ");
                String minAmount = sa[sa.length - 1];
                System.out.println("最低还款额:" + minAmount);
                break;
            case limitAmount://额度
                result = list.get(0);
                sa = result.split(" ");
                String limitAmount = sa[sa.length - 1];
                System.out.println("信用额度:" + limitAmount);
                break;
            case preCash://预借现金
                if (list == null || list.isEmpty()) {
                    break;
                }
                result = list.get(0);
                sa = result.split(" ");
                String preCash = sa[sa.length - 1];
                System.out.println("预借现金额度:" + preCash);
                break;
            case enCash://取现
                if (list == null || list.isEmpty()) {
                    break;
                }
                result = list.get(0);
                sa = result.split(" ");
                String enCash = sa[sa.length - 1];
                System.out.println("取现额度:" + enCash);
                break;
            case transDetail://交易明细
                System.out.println("交易明细:");
                System.out.println("交易日  银行记账日 卡号后四位 交易描述 交易货币 金额 记账货币 金额 ");
                for (int i = 0; i < list.size(); i++) {
                    String detail = list.get(i);
                    sa = detail.split(" ");
                    for (int j = 0; j < sa.length; j++) {
                        System.out.print(
                                sa[j] + ((j == sa.length - 1) ? "\n" : " "));
                    }
                }
                break;
            default:
                break;
            }
        }

    }

    @Override
    public void initKeywords() {
        Map<String, String> kw = new HashMap<String, String>();
        kw.put(MailKey.payDate.name(), "到期还款日：\\d{4}年\\d{2}月\\d{2}日");
        kw.put(MailKey.payAmount.name(),
                "到期还款日：\\d{4}年\\d{2}月\\d{2}日 RMB \\d+.?\\d+");
        kw.put(MailKey.minAmount.name(),
                "到期还款日：\\d{4}年\\d{2}月\\d{2}日 RMB \\d+.?\\d+ USD \\d+.?\\d+ RMB \\d+.?\\d+");
        kw.put(MailKey.limitAmount.name(),
                "到期还款日：\\d{4}年\\d{2}月\\d{2}日 RMB \\d+.?\\d+ USD \\d+.?\\d+ RMB \\d+.?\\d+ USD \\d+.?\\d+ \\d*\\D* RMB \\d+.?\\d+");
        kw.put(MailKey.preCash.name(), "预借现金额度 RMB \\d+.?\\d+ RMB \\d+.?\\d+");
        kw.put(MailKey.enCash.name(), "取现额度 RMB \\d+.?\\d+ RMB \\d+.?\\d+");
        kw.put(MailKey.transDetail.name(),
                "\\d{8} \\d{8} \\d{0,4} \\d*\\D* RMB -?\\d+.?\\d+ RMB -?\\d+.?\\d+");
        this.keywords = kw;
    }

    @Override
    protected void setCardType() {
        this.cardType = CardTypeEnum.CITIC_STANDARD;
    }

}
