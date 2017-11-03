package com.pay.aile.bill.service.mail.analyze.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pay.aile.bill.service.mail.analyze.AbstractBankMailAnalyzer;
import com.pay.aile.bill.service.mail.analyze.banktemplate.cmb.AbstractCMBTemplate;

/**
 * 
 * @author Charlie
 * @description 招商银行解析模板
 */
@Service
public class CMBAnalyzer extends AbstractBankMailAnalyzer<AbstractCMBTemplate> {

    @Override
    public boolean support(String name) {
        return StringUtils.hasText(name)
                && (name.contains("CMB") || name.contains("招商银行"));
    }

    @Override
    protected void preAnalyze(String content) {

    }

}
