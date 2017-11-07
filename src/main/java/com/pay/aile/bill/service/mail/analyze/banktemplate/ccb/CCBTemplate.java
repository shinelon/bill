package com.pay.aile.bill.service.mail.analyze.banktemplate.ccb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pay.aile.bill.entity.CreditTemplate;
import com.pay.aile.bill.mapper.CreditTemplateMapper;
import com.pay.aile.bill.service.mail.analyze.enums.CardTypeEnum;
import com.pay.aile.bill.service.mail.analyze.model.AnalyzeParamsModel;

/**
 *
 * @author Charlie
 * @description 建设银行信用卡账单内容解析模板
 */
@Service
public class CCBTemplate extends AbstractCCBTemplate {

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
            rules.setDueDate("Due Date \\d{4}-\\d{2}-\\d{2}");
            rules.setCredits("Credit Limit CNY \\d+.?\\d");
            rules.setCash("Cash Advance Limit CNY \\d+.?\\d");
            rules.setDetails(
                    "\\d{4}-\\d{2}-\\d{2} \\d{4}-\\d{2}-\\d{2} \\d{4} (\\S+){3,4} \\d+.?\\d \\S+ \\S+ \\d+.?\\d");
        }
    }

    @Override
    protected void analyzeInternal(AnalyzeParamsModel apm) {
        super.analyzeInternal(apm);
    }

    @Override
    protected void setCardType() {
        cardType = CardTypeEnum.CCB_LK;
    }
}
