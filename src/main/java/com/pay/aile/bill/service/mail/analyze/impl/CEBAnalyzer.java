package com.pay.aile.bill.service.mail.analyze.impl;

import java.util.HashMap;
import java.util.List;

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
    protected void preAnalyze(List<String> content) {

    }

    @Override
    protected void initTemplateCache() {
        templateCache = new HashMap<String, Class<? extends AbstractCEBTemplate>>();
    }

}
