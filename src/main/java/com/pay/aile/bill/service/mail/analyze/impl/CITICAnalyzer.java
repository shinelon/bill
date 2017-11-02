package com.pay.aile.bill.service.mail.analyze.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
        return StringUtils.hasText(name)
                && (name.contains("CITIC") || name.contains("中信银行"));
    }

    @Override
    protected void preAnalyze(String content) {

    }

}
