package com.pay.aile.bill.service.mail.analyze.banktemplate.cmb;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pay.aile.bill.entity.CreditBill;
import com.pay.aile.bill.entity.CreditBillDetail;
import com.pay.aile.bill.entity.CreditTemplate;
import com.pay.aile.bill.service.mail.analyze.enums.AccountTypeEnum;
import com.pay.aile.bill.service.mail.analyze.enums.CardTypeEnum;
import com.pay.aile.bill.service.mail.analyze.model.AnalyzeParamsModel;
import com.pay.aile.bill.service.mail.analyze.model.AnalyzeResult;
import com.pay.aile.bill.service.mail.analyze.util.DateUtil;
import com.pay.aile.bill.service.mail.analyze.util.PatternMatcherUtil;

/**
 * 
 * @author Charlie
 * @description 招商银行YOUNG卡解析模板
 */
@Service
public class CMBYoungTemplate extends AbstractCMBTemplate {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void analyzeInternal(AnalyzeParamsModel apm) {
        logger.info("账单内容：{}", apm);
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
        if (StringUtils.hasText(rules.getDueDate())) {
            //还款日
            list = PatternMatcherUtil.getMatcher(rules.getDueDate(), content);
            if (!list.isEmpty()) {

                result = list.get(0);
                Calendar c = Calendar.getInstance();
                c.setTime(new Date());
                int year = c.get(Calendar.YEAR);
                Date date = DateUtil.parseDateFromString(
                        String.valueOf(year) + "/" + result, "yyyy/MM/dd");
                bill.setDueDate(date);
            }
        }
        if (StringUtils.hasText(rules.getCurrentAmount())) {
            //应还款额
            list = PatternMatcherUtil.getMatcher(rules.getCurrentAmount(),
                    content);
            if (!list.isEmpty()) {
                result = list.get(0);
                sa = result.split(" ");
                String currentAmount = sa[sa.length - 1];
                bill.setCurrentAmount(new BigDecimal(currentAmount));
            }
        }
        if (StringUtils.hasText(rules.getCredits())) {
            //信用额度
            list = PatternMatcherUtil.getMatcher(rules.getCredits(), content);
            if (!list.isEmpty()) {
                result = list.get(0);
                sa = result.split(" ");
                String used = sa[0];
                String notUse = sa[1];
                BigDecimal credits = new BigDecimal(used)
                        .add(new BigDecimal(notUse));
                bill.setCredits(credits);
            }
        }
        if (StringUtils.hasText(rules.getDetails())) {
            //交易明细
            list = PatternMatcherUtil.getMatcher(rules.getDetails(), content);
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    String s = list.get(i);
                    sa = s.split(" ");
                    CreditBillDetail cbd = new CreditBillDetail();
                    cbd.setTransactionDate(
                            DateUtil.parseDateFromString(sa[1], "yyyyMMdd"));//交易日期
                    cbd.setBillingDate(
                            DateUtil.parseDateFromString(sa[1], "yyyyMMdd"));//记账日期
                    AccountTypeEnum t = AccountTypeEnum.getByCnName(sa[3]);
                    cbd.setAccountType(
                            t == null ? AccountTypeEnum.RMB.name() : t.name());
                    cbd.setTransactionDescription(sa[4]);//交易描述
                    cbd.setTransactionAmount(sa[5]);//交易货币/金额
                    cbd.setAccountableAmount(sa[5]);//记账货币/金额
                    detail.add(cbd);
                }
            }
        }
        if (bill.getDueDate() != null && detail.isEmpty()) {
            throw new RuntimeException("CMB YOUNG 解析失败!");
        }
        apm.setResult(ar);

    }

    @Override
    public void initRules() {
        super.initRules();
        if (rules == null) {
            rules = new CreditTemplate();
            rules.setDueDate("\\d{2}/\\d{2}");
            rules.setCurrentAmount("\\d{2}/\\d{2} \\d+.?\\d*");
            rules.setCredits("\\d+.?\\d* \\d+.?\\d* 卡号后四位");
            rules.setDetails(
                    "\\d{4} \\d{8} \\d{2}:\\d{2}:\\d{2} \\S+ \\S+ \\d+.?\\d*");
        }
    }

    @Override
    protected void setCardType() {
        this.cardType = CardTypeEnum.CMB_YOUNG;
    }

}
