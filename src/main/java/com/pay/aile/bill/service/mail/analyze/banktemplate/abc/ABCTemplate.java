package com.pay.aile.bill.service.mail.analyze.banktemplate.abc;

import org.springframework.stereotype.Service;

import com.pay.aile.bill.entity.CreditBillDetail;
import com.pay.aile.bill.entity.CreditTemplate;
import com.pay.aile.bill.service.mail.analyze.enums.CardTypeEnum;

/**
 *
 * @author Charlie
 * @description 农业银行解析模板
 */
@Service
public class ABCTemplate extends AbstractABCTemplate {

    @Override
    public void initRules() {
        super.initRules();
        if (rules == null) {
            rules = new CreditTemplate();
            rules.setCardtypeId(5L);
            rules.setCardholder("尊敬的[\\u4e00-\\u9fa5]+先生");
            rules.setYearMonth("以下是您2017年10月份的信用卡电子账单");
            rules.setCycle("账单周期 \\d{8}-\\d{8}");
            rules.setMinimum("CreditLimit [\\u4e00-\\u9fa5]+\\([a-zA-Z]+\\) -?\\d+\\.?\\d* -?\\d+\\.?\\d*");
            rules.setDueDate("到期还款日 \\d{8}");
            rules.setCurrentAmount("CreditLimit [\\u4e00-\\u9fa5]+\\([a-zA-Z]+\\) -?\\d+\\.?\\d*");
            rules.setLastAmount("AccountBalance [\\u4e00-\\u9fa5]+\\([a-zA-Z]+\\) -?\\d+\\.?\\d*");
            rules.setCredits(
                    "CreditLimit [\\u4e00-\\u9fa5]+\\([a-zA-Z]+\\) -?\\d+\\.?\\d* -?\\d+\\.?\\d* \\d+\\.?\\d*");
            rules.setDetails("\\d{8} \\d{8} \\d{4} \\S+ \\S+ \\d+\\.?\\d*/[a-zA-Z]+ -?\\d+\\.?\\d*/[a-zA-Z]+");
            rules.setIntegral("PointsRemaining \\d+ \\d+ \\d+");
            rules.setTransactionDate("0");
            rules.setBillingDate("1");
            rules.setCardNumbers("2");
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
            cbd.setTransactionDescription(cbd.getTransactionDescription() + value);
        } else if (index == 5) {
            String[] trans = value.split("/");
            cbd.setTransactionAmount(trans[0]);
            cbd.setTransactionCurrency(trans[1]);
        } else if (index == 6) {
            String[] account = value.split("/");
            String amount = account[0];
            if (amount.startsWith("-")) {
                amount = amount.replaceAll("-", "");
            } else {
                amount = "-" + amount;
            }
            cbd.setAccountableAmount(amount);
            cbd.setAccountType(account[1]);
        }
    }

}
