package com.pay.aile.bill.service.mail.analyze.banktemplate.pab;

import org.springframework.stereotype.Service;

import com.pay.aile.bill.entity.CreditTemplate;
import com.pay.aile.bill.service.mail.analyze.enums.CardTypeEnum;

/**
 *
 * @author Charlie
 * @description 平安银行解析模板
 */
@Service
public class PABTemplate extends AbstractPABTemplate {

    @Override
    public void initRules() {
        super.initRules();
        if (rules == null) {
            rules = new CreditTemplate();
            rules.setCardtypeId(18L);
            rules.setDueDate("本期还款日 \\d{4}-\\d{2}-\\d{2}");
            rules.setCurrentAmount("本期最低应还金额 \\d+\\.?\\d*");
            rules.setCredits("信用额度 \\d+\\.?\\d*");
            rules.setCash("取现额度 \\d+\\.?\\d*");
            rules.setDetails("\\d{4}-\\d{2}-\\d{2} \\d{4}-\\d{2}-\\d{2} \\S+ \\d+\\.?\\d*");
            rules.setTransactionDate("0");
            rules.setBillingDate("1");
            rules.setTransactionDescription("2");
            rules.setTransactionAmount("3");
        }
    }

    @Override
    protected void setCardType() {
        cardType = CardTypeEnum.PAB_DEFAULT;
    }

}
