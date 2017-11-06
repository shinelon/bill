package com.pay.aile.bill.service.mail.analyze.banktemplate.ceb;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pay.aile.bill.entity.CreditBill;
import com.pay.aile.bill.entity.CreditBillDetail;
import com.pay.aile.bill.entity.CreditTemplate;
import com.pay.aile.bill.service.mail.analyze.enums.CardTypeEnum;
import com.pay.aile.bill.service.mail.analyze.model.AnalyzeParamsModel;
import com.pay.aile.bill.service.mail.analyze.model.AnalyzeResult;
import com.pay.aile.bill.service.mail.analyze.util.DateUtil;
import com.pay.aile.bill.service.mail.analyze.util.PatternMatcherUtil;

/**
 * 
 * @author Charlie
 * @description 广大银行信用卡账单内容解析模板
 */
@Service
public class CEBTemplate extends AbstractCEBTemplate {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void analyzeInternal(AnalyzeParamsModel apm) {
        logger.debug("账单内容：{}", apm);
        String content = apm.getContent();
        AnalyzeResult ar = new AnalyzeResult();
        CreditBill bill = ar.getBill();
        List<CreditBillDetail> detail = ar.getDetail();
        if (rules == null) {
            throw new RuntimeException("账单模板规则未初始化");
        }
        List<String> list = null;
        String result = "";
        String[] sa = null;
        if (StringUtils.hasText(rules.getBillingDate())) {
            //账单日
            list = PatternMatcherUtil.getMatcher(rules.getBillingDate(),
                    content);
            if (list.isEmpty()) {
                handleNotMatch("billingDate", rules.getDueDate(), apm);
            }
            result = list.get(0);
            sa = result.split(" ");
            String date = sa[sa.length - 1];
            bill.setDueDate(DateUtil.parseDateFromString(date, "yyyy/MM/dd"));
        }
        if (StringUtils.hasText(rules.getDueDate())) {
            //还款日
            list = PatternMatcherUtil.getMatcher(rules.getDueDate(), content);
            if (list.isEmpty()) {
                handleNotMatch("dueDate", rules.getDueDate(), apm);
            }
            result = list.get(0);
            sa = result.split(" ");
            String date = sa[sa.length - 1];
            bill.setDueDate(DateUtil.parseDateFromString(date, "yyyy/MM/dd"));
        }
        if (StringUtils.hasText(rules.getCurrentAmount())) {
            //应还款额
            list = PatternMatcherUtil.getMatcher(rules.getCurrentAmount(),
                    content);
            if (list.isEmpty()) {
                handleNotMatch("currentAmount", rules.getCurrentAmount(), apm);
            }
            result = list.get(0);
            sa = result.split(" ");
            String currentAmount = sa[sa.length - 1];
            bill.setCurrentAmount(new BigDecimal(currentAmount));
        }
        if (StringUtils.hasText(rules.getCredits())) {
            //信用额度
            list = PatternMatcherUtil.getMatcher(rules.getCredits(), content);
            if (list.isEmpty()) {
                handleNotMatch("credits", rules.getCredits(), apm);
            }
            result = list.get(0);
            sa = result.split(" ");
            String credits = sa[sa.length - 1];
            bill.setCredits(new BigDecimal(credits));
        }
        if (StringUtils.hasText(rules.getPrepaidCashAmount())) {
            //预借现金
            //            list = PatternMatcherUtil.getMatcher(rules.getPrepaidCashAmount(), content);
            //            if(!list.isEmpty()){
            //                result = list.get(0);
            //                sa = result.split(" ");
            //                String preCash = sa[sa.length - 1];
            //                
            //            }
        }
        if (StringUtils.hasText(rules.getCash())) {
            //取现
            list = PatternMatcherUtil.getMatcher(rules.getCredits(), content);
            if (!list.isEmpty()) {
                result = list.get(0);
                sa = result.split(" ");
                String cash = sa[sa.length - 1];
                bill.setCash(new BigDecimal(cash));
            }
        }
        if (StringUtils.hasText(rules.getDetails())) {
            //交易明细
            list = PatternMatcherUtil.getMatcher(rules.getDetails(), content);
            if (list.isEmpty()) {
                handleNotMatch("details", rules.getDetails(), apm);
            }
            for (int i = 0; i < list.size(); i++) {
                String s = list.get(i);
                sa = s.split(" ");
                CreditBillDetail cbd = new CreditBillDetail();
                cbd.setTransactionDate(
                        DateUtil.parseDateFromString(sa[0], "yyyyMMdd"));//交易日期
                cbd.setBillingDate(
                        DateUtil.parseDateFromString(sa[1], "yyyyMMdd"));//记账日期
                cbd.setTransactionDescription(sa[3]);//交易描述
                cbd.setAccountType(sa[4]);
                cbd.setTransactionAmount(sa[5]);//交易货币/金额
                cbd.setAccountableAmount(sa[7]);//记账货币/金额
                detail.add(cbd);
            }
        }
        apm.setResult(ar);

    }

    @Override
    public void initRules() {
        super.initRules();
        if (rules == null) {
            rules = new CreditTemplate();
            rules.setBillingDate("账单日 \\d{4}/\\d{2}/\\d{2}");//账单日
            rules.setDueDate("到期还款日 \\d{4}/\\d{2}/\\d{2}");
            rules.setCurrentAmount("人民币本期应还款额 \\d+.?\\d*");
            rules.setCredits("信用额度 \\d+.?\\d*");
            rules.setDetails(
                    "\\d{4}/\\d{2}/\\d{2} \\d{4}/\\d{2}/\\d{2} \\d{4} \\S+ \\d+.?\\d*");
        }
    }

    @Override
    protected void setCardType() {
        this.cardType = CardTypeEnum.CITIC_STANDARD;
    }

    private void handleNotMatch(String key, String reg,
            AnalyzeParamsModel apm) {
        apm.setResult(null);
        throw new RuntimeException(String.format(
                "未找到匹配值,bank=CITIC,cardType=CITIC_STANDARD,key=%s,reg=%s", key,
                reg));
    }
}
