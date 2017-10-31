package com.pay.aile.bill.service.mail.analyze.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pay.aile.bill.service.mail.analyze.AbstractBankMailAnalyzer;
import com.pay.aile.bill.service.mail.analyze.banktemplate.citic.AbstractCITICTemplate;

/**
 * 
 * @author Charlie
 * @description 中信银行解析模板
 */
@Service
public class CITICAnalyzer
        extends AbstractBankMailAnalyzer<AbstractCITICTemplate> {

    @Override
    public boolean support(String name) {
        return true;
    }

    @Override
    protected void preAnalyze(List<String> content) {

    }

    @Override
    protected void initTemplateCache() {

    }

}
