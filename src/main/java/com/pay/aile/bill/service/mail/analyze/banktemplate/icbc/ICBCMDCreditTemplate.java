package com.pay.aile.bill.service.mail.analyze.banktemplate.icbc;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pay.aile.bill.entity.CreditBill;
import com.pay.aile.bill.entity.CreditTemplate;
import com.pay.aile.bill.service.mail.analyze.enums.CardTypeEnum;
import com.pay.aile.bill.service.mail.analyze.model.AnalyzeParamsModel;
import com.pay.aile.bill.service.mail.analyze.util.PatternMatcherUtil;

/**
 * @author ji
 * @description 中国工商银行-牡丹贷记卡
 */
@Service
public class ICBCMDCreditTemplate extends AbstractICBCTemplate {

    @Override
    public void initRules() {
        super.initRules();
        if (rules == null) {
            rules = new CreditTemplate();
            rules.setCardtypeId(14L);
            rules.setDueDate("贷记卡到期还款日 \\d{4}年\\d{1,2}月\\d{1,2}日");
            rules.setCurrentAmount(
                    "合计 -?\\d+\\.?\\d*/[a-z-A-Z]+ -?\\d+\\.?\\d*/[a-z-A-Z]+ -?\\d+\\.?\\d*/[a-z-A-Z]+ -?\\d+\\.?\\d*/[a-z-A-Z]+");
            rules.setCredits(
                    "信用额度 \\d{4}\\(\\S+\\) [\\u4e00-\\u9fa5]+ (\\d+\\.?\\d*/[a-z-A-Z]+ ){2} \\d+\\.?\\d*/[a-z-A-Z]+");
            rules.setDetails(
                    "\\d{4} \\d{4}-\\d{2}-\\d{2} \\d{4}-\\d{2}-\\d{2} \\S+ \\S+ \\d+\\.?\\d*/[a-zA-Z]+ \\d+\\.?\\d*/[a-zA-Z]+");
        }
    }

    private List<String> getValueListByPattern(String content, String ruleValue) {

        if (StringUtils.hasText(ruleValue)) {

            List<String> list = PatternMatcherUtil.getMatcher(ruleValue, content);
            return list;
        }
        return null;
    }

    @Override
    protected void analyzeCredits(CreditBill bill, String content, AnalyzeParamsModel apm) {
        if (StringUtils.hasText(rules.getCredits())) {
            String ruleValue = rules.getCredits();
            List<String> list = PatternMatcherUtil.getMatcher(ruleValue, content);
            if (!list.isEmpty()) {
                String result = list.get(0);
                String[] sa = result.split(" ");
                String value = sa[sa.length - 1];
                value = PatternMatcherUtil.getMatcherString("\\d+\\.?\\d", value);
                if (StringUtils.hasText(value)) {
                    bill.setCredits(new BigDecimal(value));
                }
            }
        }
    }

    @Override
    protected void analyzeCurrentAmount(CreditBill bill, String content, AnalyzeParamsModel apm) {
        if (StringUtils.hasText(rules.getCurrentAmount())) {

            List<String> list = getValueListByPattern(content, rules.getCurrentAmount());
            if (list == null || list.isEmpty()) {
                handleNotMatch("currentAmount", rules.getCurrentAmount(), apm);
            }
            list.forEach(string -> {
                String[] sa = string.split(" ");
                String value = sa[sa.length - 1];
                String[] sa1 = value.split("/");
                String amount = sa1[0];
                if (StringUtils.hasText(amount) && amount.startsWith("-")) {
                    amount = amount.replaceAll("-", "");
                    bill.setCurrentAmount(new BigDecimal(amount));
                    bill.setAccountType(sa1[1]);
                }
            });
            if (bill.getCurrentAmount() == null) {
                bill.setCurrentAmount(BigDecimal.ZERO);
                bill.setAccountType("RMB");
            }
        }
    }

    /**
     * 设置信用卡类型
     */
    @Override
    protected void setCardType() {
        cardType = CardTypeEnum.ICBC_MDC;
    }
}
