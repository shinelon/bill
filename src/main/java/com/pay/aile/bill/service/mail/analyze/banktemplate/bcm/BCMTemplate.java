package com.pay.aile.bill.service.mail.analyze.banktemplate.bcm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pay.aile.bill.entity.CreditTemplate;
import com.pay.aile.bill.mapper.CreditTemplateMapper;
import com.pay.aile.bill.service.mail.analyze.enums.CardTypeEnum;
import com.pay.aile.bill.service.mail.analyze.model.AnalyzeParamsModel;

/**
 *
 * @author Charlie
 * @description 交通银行信用卡账单内容解析模板
 */
@Service
public class BCMTemplate extends AbstractBCMTemplate {

    @Autowired
    CreditTemplateMapper creditTemplateMapper;

    @Override
    public void initRules() {
        // super.initRules();
        if (rules == null) {
            rules = creditTemplateMapper.selectById(4);
        }
        if (rules == null) {
            rules = new CreditTemplate();
            rules.setDueDate("到期还款日 \\d{4}/\\d{2}/\\d{2}");
            rules.setCurrentAmount("本期应还款额 \\d+.?\\d*");
            rules.setCredits("信用额度 \\d+.?\\d*");
            rules.setCash("取现额度 \\d+.?\\d*");
            rules.setDetails(
                    "\\d{4}/\\d{2}/\\d{2} \\d{4}/\\d{2}/\\d{2} \\S+ [a-zA-Z]{3} \\d+.?\\d* [a-zA-Z]{3} \\d+.?\\d*");
        }
    }

    @Override
    protected void analyzeInternal(AnalyzeParamsModel apm) {
        super.analyzeInternal(apm);
    }

    @Override
    protected void setCardType() {
        cardType = CardTypeEnum.BCM_DEFAULT;
    }
}
