package com.pay.aile.bill.service.mail.analyze.banktemplate.icbc;

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
import com.pay.aile.bill.service.mail.analyze.util.DateUtil;
import com.pay.aile.bill.service.mail.analyze.util.PatternMatcherUtil;

/**
 * @author ji
 * @description 中国工商银行-牡丹贷记卡
 */
@Service
public class ICBCMDCreditTemplate extends AbstractICBCTemplate {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 解析账单明细
     * 卡号后四位 交易日 记账日 交易类型 商户名称/城市 交易金额 币种 记账金额 币种
     */
    @Override
    protected CreditBillDetail setCreditBillDetail(String details) {
        CreditBillDetail cbd = new CreditBillDetail();
        // 将/RMB 币种与金额的/替换成空格
        details = details.replaceAll("/", " ");
        String[] list = details.split(" ");
        if (list.length != 9) {
            logger.error("ICBC 牡丹贷记卡,账单明细解析数组长度异常...", details);
            return null;
        }
        cbd.setTransactionDate(DateUtil.parseDateFromString(list[1], null));
        cbd.setBillingDate(DateUtil.parseDateFromString(list[2], null));
        cbd.setTransactionDescription(list[4]);
        cbd.setTransactionAmount(list[5]);
        cbd.setTransactionCurrency(list[6]);
        cbd.setAccountableAmount(list[7]);
        cbd.setAccountType(list[8]);
        cbd.setStatus(1);

        return cbd;
    }

    @Override
    public void initRules() {
        super.initRules();
        if (rules == null) {
            rules = new CreditTemplate();
            rules.setDueDate("贷记卡到期还款日 \\d{4}年\\d{1,2}月\\d{1,2}日");
            rules.setCurrentAmount(
                    "合计 -?\\d+\\.?\\d*/[a-z-A-Z]+ -?\\d+\\.?\\d*/[a-z-A-Z]+ -?\\d+\\.?\\d*/[a-z-A-Z]+ -?\\d+\\.?\\d*/[a-z-A-Z]+");
            rules.setCredits(
                    "信用额度 \\d{4}\\(\\S+\\) [\\u4e00-\\u9fa5]+ (\\d+\\.?\\d*/[a-z-A-Z]+ ){2} \\d+\\.?\\d*/[a-z-A-Z]+");
            rules.setDetails(
                    "\\d{4} \\d{4}-\\d{2}-\\d{2} \\d{4}-\\d{2}-\\d{2} \\S+ \\S+ \\d+\\.?\\d*/[a-zA-Z]+ \\d+\\.?\\d*/[a-zA-Z]+");
        }
    }

    /**
     * 设置信用卡类型
     */
    @Override
    protected void setCardType() {
        cardType = CardTypeEnum.ICBC_MDC;
    }

    @Override
    protected void analyzeCurrentAmount(CreditBill bill, String content,
            AnalyzeParamsModel apm) {
        if (StringUtils.hasText(rules.getCurrentAmount())) {

            List<String> list = getValueListByPattern(content,
                    rules.getCurrentAmount());
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

    private List<String> getValueListByPattern(String content,
            String ruleValue) {

        if (StringUtils.hasText(ruleValue)) {

            List<String> list = PatternMatcherUtil.getMatcher(ruleValue,
                    content);
            return list;
        }
        return null;
    }

    @Override
    protected void analyzeCredits(CreditBill bill, String content,
            AnalyzeParamsModel apm) {
        if (StringUtils.hasText(rules.getCredits())) {
            String ruleValue = rules.getCredits();
            List<String> list = PatternMatcherUtil.getMatcher(ruleValue,
                    content);
            if (!list.isEmpty()) {
                String result = list.get(0);
                String[] sa = result.split(" ");
                String value = sa[sa.length - 1];
                value = PatternMatcherUtil.getMatcherString("\\d+\\.?\\d",
                        value);
                if (StringUtils.hasText(value)) {
                    bill.setCredits(new BigDecimal(value));
                }
            }
        }
    }
}
