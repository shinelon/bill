package com.pay.aile.bill.service.mail.analyze.banktemplate.ceb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pay.aile.bill.entity.CreditTemplate;
import com.pay.aile.bill.service.mail.analyze.enums.CardTypeEnum;

/**
 *
 * @author Charlie
 * @description 广大银行信用卡账单内容解析模板
 */
@Service
public class CEBTemplate extends AbstractCEBTemplate {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void initRules() {
        super.initRules();
        if (rules == null) {
            rules = new CreditTemplate();
            rules.setBillingDate("账单日 \\d{4}/\\d{2}/\\d{2}");// 账单日
            rules.setDueDate("到期还款日 \\d{4}/\\d{2}/\\d{2}");
            rules.setCurrentAmount("人民币本期应还款额 \\d+.?\\d*");
            rules.setCredits("信用额度 \\d+.?\\d*");
            rules.setDetails(
                    "\\d{4}/\\d{2}/\\d{2} \\d{4}/\\d{2}/\\d{2} \\d{4} \\S+ \\d+.?\\d*");
        }
    }

    @Override
    protected void setCardType() {
        cardType = CardTypeEnum.CEB_DEFAULT;
    }
}
