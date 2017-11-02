package com.pay.aile.bill.service.mail.analyze.impl;

import org.springframework.stereotype.Service;

import com.pay.aile.bill.service.mail.analyze.AbstractBankMailAnalyzer;
import com.pay.aile.bill.service.mail.analyze.banktemplate.ceb.AbstractCEBTemplate;

/**
 * 
 * @author Charlie
 * @description 
 */
@Service
public class CEBAnalyzer extends AbstractBankMailAnalyzer<AbstractCEBTemplate> {

    @Override
    public boolean support(String name) {
        //TODO 
        return false;
    }

    @Override
    protected void preAnalyze(String content) {

    }

}
