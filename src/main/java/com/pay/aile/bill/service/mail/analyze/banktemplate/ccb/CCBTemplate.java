package com.pay.aile.bill.service.mail.analyze.banktemplate.ccb;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pay.aile.bill.entity.CreditTemplate;
import com.pay.aile.bill.mapper.CreditTemplateMapper;
import com.pay.aile.bill.service.mail.analyze.MailContentExtractor;
import com.pay.aile.bill.service.mail.analyze.enums.CardTypeEnum;
import com.pay.aile.bill.service.mail.analyze.model.AnalyzeParamsModel;

/**
 *
 * @author Charlie
 * @description 建设银行信用卡账单内容解析模板
 */
@Service
public class CCBTemplate extends AbstractCCBTemplate {

    @Resource(name = "pdfExtractor")
    private MailContentExtractor pextractor;
    @Autowired
    CreditTemplateMapper creditTemplateMapper;

    @Override
    public void initRules() {
        super.initRules();
        if (rules == null) {
            rules = new CreditTemplate();
            rules.setCardtypeId(13L);
            rules = creditTemplateMapper.selectById(3);
        }
    }

    @Override
    protected void initContext(AnalyzeParamsModel apm) {
        // TODO Auto-generated method stub
        super.initContext(apm);

    }

    @Override
    protected void setCardType() {
        cardType = CardTypeEnum.CCB_LK;
    }
}
