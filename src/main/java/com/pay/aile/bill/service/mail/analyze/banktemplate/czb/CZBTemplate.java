package com.pay.aile.bill.service.mail.analyze.banktemplate.czb;

import org.springframework.stereotype.Service;

import com.pay.aile.bill.entity.CreditTemplate;
import com.pay.aile.bill.service.mail.analyze.enums.CardTypeEnum;

/**
 *
 * @author Charlie
 * @description 浙商银行解析模板
 */
@Service
public class CZBTemplate extends AbstractCZBTemplate {

    @Override
    public void initRules() {
        super.initRules();
        if (rules == null) {
            rules = new CreditTemplate();
            rules.setCardtypeId(6L);
            rules.setCardholder("尊敬的[\\u4e00-\\u9fa5]+");
            rules.setDueDate("PaymentDueDate \\d+\\.?\\d* \\d+\\.?\\d* \\d{8} \\d{8}");
            rules.setCurrentAmount("循环利息Interest \\d+\\.?\\d*");
            rules.setCredits("PaymentDueDate \\d+\\.?\\d*");
            rules.setDetails("\\d{8} \\d{8} \\S+ -?\\d+\\.?\\d* \\d{4}");
            rules.setIntegral("Interestthismonth \\d+");
            rules.setTransactionDate("0");
            rules.setBillingDate("1");
            rules.setTransactionDescription("2");
            rules.setTransactionAmount("3");
            rules.setCardNumbers("4");
        }
    }

    @Override
    protected void setCardType() {
        cardType = CardTypeEnum.CZB_DEFAULT;
    }

}
