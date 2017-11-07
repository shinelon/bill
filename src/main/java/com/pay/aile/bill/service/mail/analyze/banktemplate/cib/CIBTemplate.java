package com.pay.aile.bill.service.mail.analyze.banktemplate.cib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pay.aile.bill.entity.CreditTemplate;
import com.pay.aile.bill.service.mail.analyze.enums.CardTypeEnum;

/**
 *
 * @author Charlie
 * @description 兴业银行解析模板
 */
@Service
public class CIBTemplate extends AbstractCIBTemplate {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void initRules() {
        super.initRules();
        if (rules == null) {
            rules = new CreditTemplate();
            rules.setCycle(
                    "Statement Cycle \\d{4}/\\d{2}/\\d{2}-\\d{4}/\\d{2}/\\d{2}");
            rules.setDueDate("Payment Due Date \\d{4}年\\d{2}月\\d{2}日");
            rules.setCurrentAmount("New Balance [a-zA-Z]{3} \\d+.?\\d*");
            rules.setCredits("Credit Limit\\([a-zA-Z]{3}\\) \\d+.?\\d*");
            rules.setPrepaidCashAmount(
                    "Cash Advance Limit\\([a-zA-Z]{3}\\) \\d+.?\\d*");
            rules.setDetails(
                    "\\d{4}-\\d{2}-\\d{2} \\d{4}-\\d{2}-\\d{2} \\S+ \\d+.?\\d*");
        }
    }

    @Override
    protected void setCardType() {
        cardType = CardTypeEnum.CIB_DEFAULT;
    }
}
