package com.pay.aile.bill.service.mail.analyze.banktemplate.gdb;

import org.springframework.stereotype.Service;

import com.pay.aile.bill.entity.CreditTemplate;
import com.pay.aile.bill.service.mail.analyze.enums.CardTypeEnum;

/**
 *
 * @author Charlie
 * @description 广发银行解析模板
 */
@Service
public class GDBTemplate extends AbstractGDBTemplate {

    @Override
    public void initRules() {
        super.initRules();
        if (rules == null) {
            rules = new CreditTemplate();
            rules.setDueDate(
                    "\\d{4}\\*+\\d{4} \\d+\\.?\\d* \\d+\\.?\\d* \\d{4}/\\d{2}/\\d{2}");
            rules.setCurrentAmount("\\d{4}\\*+\\d{4} \\d+\\.?\\d*");
            rules.setCredits(
                    "\\d{4}\\*+\\d{4} \\d+\\.?\\d* \\d+\\.?\\d* \\d{4}/\\d{2}/\\d{2} \\S+ \\d+\\.?\\d*");
        }
    }

    @Override
    protected void setCardType() {
        cardType = CardTypeEnum.GDB_DEFAULT;
    }

}
