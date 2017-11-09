package com.pay.aile.bill.service.mail.analyze.banktemplate.abc;

import org.springframework.stereotype.Service;

import com.pay.aile.bill.entity.CreditTemplate;
import com.pay.aile.bill.service.mail.analyze.enums.CardTypeEnum;

/**
 *
 * @author Charlie
 * @description 兴业银行解析模板
 */
@Service
public class ABCTemplate extends AbstractABCTemplate {

    @Override
    public void initRules() {
        super.initRules();
        if (rules == null) {
            rules = new CreditTemplate();
            rules.setDueDate("PaymentDueDate\\d{4}年\\d{2}月\\d{2}日");
            rules.setCurrentAmount("NewBalance[a-zA-Z]{3}\\d+\\.?\\d*");
            rules.setCredits("CreditLimit\\([a-zA-Z]{3}\\)\\d+\\.?\\d*");
            rules.setPrepaidCashAmount(
                    "CashAdvanceLimit\\([a-zA-Z]{3}\\)\\d+\\.?\\d*");
            rules.setDetails(
                    "\\d{4}-\\d{2}-\\d{2} \\d{4}-\\d{2}-\\d{2} \\S+ -?\\d+\\.?\\d*");
            rules.setTransactionDate("0");
            rules.setBillingDate("1");
            rules.setTransactionDescription("2");
            rules.setTransactionAmount("3");
        }
    }

    @Override
    protected void setCardType() {
        cardType = CardTypeEnum.CIB_DEFAULT;
    }

}
