package com.pay.aile.bill.service.mail.analyze.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pay.aile.bill.service.mail.analyze.AbstractBankMailAnalyzer;
import com.pay.aile.bill.service.mail.analyze.banktemplate.pab.AbstractPABTemplate;
import com.pay.aile.bill.service.mail.analyze.enums.BankCodeEnum;

/**
 *
 * @author Charlie
 * @description 平安银行
 */
@Service
public class PABAnalyzer extends AbstractBankMailAnalyzer<AbstractPABTemplate> {

    @Override
    public boolean support(String name) {
        return StringUtils.hasText(name) && (name.equalsIgnoreCase(BankCodeEnum.PAB.getBankCode())
                || name.contains(BankCodeEnum.PAB.getBankName()));
    }

}
