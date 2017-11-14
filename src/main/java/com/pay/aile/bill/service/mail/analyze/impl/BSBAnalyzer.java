package com.pay.aile.bill.service.mail.analyze.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pay.aile.bill.service.mail.analyze.AbstractBankMailAnalyzer;
import com.pay.aile.bill.service.mail.analyze.banktemplate.bsb.AbstractBSBTemplate;
import com.pay.aile.bill.service.mail.analyze.enums.BankCodeEnum;

/**
 *
 * @author Charlie
 * @description 包商银行
 */
@Service
public class BSBAnalyzer extends AbstractBankMailAnalyzer<AbstractBSBTemplate> {

    @Override
    public boolean support(String name) {
        return StringUtils.hasText(name) && (name.equalsIgnoreCase(BankCodeEnum.BSB.getBankCode())
                || name.contains(BankCodeEnum.BSB.getBankName()));
    }

}
