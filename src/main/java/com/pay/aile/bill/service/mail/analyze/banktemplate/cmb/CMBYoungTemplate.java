package com.pay.aile.bill.service.mail.analyze.banktemplate.cmb;

import org.springframework.stereotype.Service;

import com.pay.aile.bill.entity.CreditTemplate;
import com.pay.aile.bill.service.mail.analyze.enums.CardTypeEnum;

/**
 * 
 * @author Charlie
 * @description 招商银行YOUNG卡解析模板
 */
@Service
public class CMBYoungTemplate extends AbstractCMBTemplate {

    @Override
    public void initRules() {
        super.initRules();
        if (rules == null) {
            rules = new CreditTemplate();
            rules.setDueDate("\\d{2}/\\d{2}");
            rules.setCurrentAmount("\\d{2}/\\d{2} \\d+.?\\d*");
            rules.setCredits("\\d+.?\\d* \\d+.?\\d* 卡号后四位");
            rules.setDetails(
                    "\\d{4} \\d{8} \\d{2}:\\d{2}:\\d{2} \\S+ \\S+ \\d+.?\\d*");
        }
    }

    @Override
    protected void setCardType() {
        this.cardType = CardTypeEnum.CMB_YOUNG;
    }

}
