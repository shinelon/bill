package com.pay.aile.bill.service.mail.analyze.banktemplate.abc;

import org.springframework.stereotype.Service;

import com.pay.aile.bill.entity.CreditBillDetail;
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
            rules.setDueDate("到期还款日 \\d{8}");
            rules.setCurrentAmount(
                    "CreditLimit [\\u4e00-\\u9fa5]+\\([a-zA-Z]+\\) -?\\d+\\.?\\d*");
            rules.setCredits(
                    "CreditLimit [\\u4e00-\\u9fa5]+\\([a-zA-Z]+\\) -?\\d+\\.?\\d* -?\\d+\\.?\\d* \\d+\\.?\\d*");
            rules.setDetails(
                    "\\d{8} \\d{8} \\d{4} \\S+ \\S+ \\d+\\.?\\d*/[a-zA-Z]+ -?\\d+\\.?\\d*/[a-zA-Z]+");
            rules.setTransactionDate("0");
            rules.setBillingDate("1");
        }
    }

    @Override
    protected void setCardType() {
        cardType = CardTypeEnum.ABC_DEFAULT;
    }

    @Override
    protected void setField(CreditBillDetail cbd, int index, String value) {
        if (index == 3) {
            cbd.setTransactionDescription(value);
        } else if (index == 4) {
            cbd.setTransactionDescription(
                    cbd.getTransactionDescription() + value);
        } else if (index == 5) {
            String[] trans = value.split("/");
            cbd.setTransactionAmount(trans[0]);
            cbd.setTransactionCurrency(trans[1]);
        } else if (index == 6) {
            String[] account = value.split("/");
            cbd.setAccountableAmount(account[0].replaceAll("-", ""));
            cbd.setAccountType(account[1]);
        }
    }

}
