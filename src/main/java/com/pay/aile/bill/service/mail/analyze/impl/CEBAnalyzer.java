package com.pay.aile.bill.service.mail.analyze.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pay.aile.bill.service.mail.analyze.AbstractBankMailAnalyzer;
import com.pay.aile.bill.service.mail.analyze.banktemplate.ceb.AbstractCEBTemplate;
import com.pay.aile.bill.service.mail.analyze.enums.BankCodeEnum;

/**
 * 
 * @author Charlie
 * @description 光大银行
 */
@Service
public class CEBAnalyzer extends AbstractBankMailAnalyzer<AbstractCEBTemplate> {

    @Override
    public boolean support(String name) {
        return StringUtils.hasText(name)
                && (name.contains(BankCodeEnum.CEB.getBankCode())
                        || name.contains(BankCodeEnum.CEB.getBankName()));
    }

    @Override
    protected void preAnalyze(String content) {

    }

}
