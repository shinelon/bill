package com.pay.aile.bill.service.mail.analyze.banktemplate.cib;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pay.aile.bill.entity.CreditBill;
import com.pay.aile.bill.entity.CreditCard;
import com.pay.aile.bill.entity.CreditTemplate;
import com.pay.aile.bill.service.mail.analyze.enums.CardTypeEnum;
import com.pay.aile.bill.service.mail.analyze.model.AnalyzeParamsModel;
import com.pay.aile.bill.service.mail.analyze.util.DateUtil;
import com.pay.aile.bill.service.mail.analyze.util.PatternMatcherUtil;

/**
 *
 * @author Charlie
 * @description 兴业银行解析模板
 */
@Service
public class CIBTemplate extends AbstractCIBTemplate {

    @Override
    public void initRules() {
        super.initRules();
        if (rules == null) {
            rules = new CreditTemplate();
            rules.setCardtypeId(11L);
            rules.setCardholder("尊敬的[\\u4e00-\\u9fa5]+您好");
            rules.setCycle("StatementCycle\\d{4}/\\d{2}/\\d{2}-\\d{4}/\\d{2}/\\d{2}");
            rules.setDueDate("PaymentDueDate\\d{4}年\\d{2}月\\d{2}日");
            rules.setCurrentAmount("NewBalance[a-zA-Z]{3}\\d+\\.?\\d*");
            rules.setCredits("CreditLimit\\([a-zA-Z]{3}\\)\\d+\\.?\\d*");
            rules.setPrepaidCashAmount("CashAdvanceLimit\\([a-zA-Z]{3}\\)\\d+\\.?\\d*");
            rules.setMinimum("MinimumPayment[a-zA-Z]{3}\\d+\\.?\\d*");
            rules.setCardNumbers("卡号末四位\\d{4}");
            rules.setDetails("\\d{4}-\\d{2}-\\d{2} \\d{4}-\\d{2}-\\d{2} \\S+ -?\\d+\\.?\\d*");
            rules.setTransactionDate("0");
            rules.setBillingDate("1");
            rules.setTransactionDescription("2");
            rules.setTransactionAmount("3");
        }
    }

    @Override
    protected void analyzeCardNo(CreditCard card, String content, AnalyzeParamsModel apm) {
        if (StringUtils.hasText(rules.getCardNumbers())) {
            String value = getValueByPattern("cardNumbers", content, rules.getCardNumbers(), apm, "");
            String cardNo = PatternMatcherUtil.getMatcherString("\\d{4}", value);
            card.setNumbers(cardNo);
        }
    }

    @Override
    protected void analyzeCash(CreditBill bill, String content, AnalyzeParamsModel apm) {
        if (StringUtils.hasText(rules.getCash())) {

            String cash = getValueByPattern("cash", content, rules.getCash(), apm, "CashAdvanceLimit\\([a-zA-Z]{3}\\)");
            cash = PatternMatcherUtil.getMatcherString("\\d+\\.?\\d*", cash);
            bill.setCash(new BigDecimal(cash));
        }
    }

    @Override
    protected void analyzeCredits(CreditBill bill, String content, AnalyzeParamsModel apm) {
        if (StringUtils.hasText(rules.getCredits())) {

            String credits = getValueByPattern("credits", content, rules.getCredits(), apm,
                    "CreditLimit\\([a-zA-Z]{3}\\)");
            credits = PatternMatcherUtil.getMatcherString("\\d+\\.?\\d", credits);
            bill.setCredits(new BigDecimal(credits));
        }
    }

    @Override
    protected void analyzeCurrentAmount(CreditBill bill, String content, AnalyzeParamsModel apm) {
        if (StringUtils.hasText(rules.getCurrentAmount())) {

            String currentAmount = getValueByPattern("currentAmount", content, rules.getCurrentAmount(), apm,
                    "NewBalance[a-zA-Z]{3}");
            currentAmount = PatternMatcherUtil.getMatcherString("\\d+\\.?\\d*", currentAmount);
            bill.setCurrentAmount(new BigDecimal(currentAmount));
        }
    }

    @Override
    protected void analyzeCycle(CreditBill bill, String content, AnalyzeParamsModel apm) {
        if (StringUtils.hasText(rules.getCycle())) {
            String cycle = getValueByPattern("cycle", content, rules.getCycle(), apm, "");
            cycle = cycle.replaceAll("StatementCycle", "");
            String[] sa = cycle.split("-");
            bill.setBeginDate(DateUtil.parseDate(sa[0]));
            bill.setEndDate(DateUtil.parseDate(sa[1]));
        }
    }

    @Override
    protected void analyzeDueDate(CreditBill bill, String content, AnalyzeParamsModel apm) {
        if (StringUtils.hasText(rules.getDueDate())) {
            String date = getValueByPattern("dueDate", content, rules.getDueDate(), apm, "PaymentDueDate");
            bill.setDueDate(DateUtil.parseDate(date));
        }
    }

    @Override
    protected void analyzeMinimum(CreditBill bill, String content, AnalyzeParamsModel apm) {
        if (StringUtils.hasText(rules.getMinimum())) {
            String minimum = getValueByPattern("minimum", content, rules.getMinimum(), apm, "");
            minimum = PatternMatcherUtil.getMatcherString("\\d+\\.?\\d*", minimum);
            bill.setMinimum(new BigDecimal(minimum));
        }
    }

    @Override
    protected void setCardType() {
        cardType = CardTypeEnum.CIB_DEFAULT;
    }

}
