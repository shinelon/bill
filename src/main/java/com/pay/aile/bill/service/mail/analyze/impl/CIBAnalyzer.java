package com.pay.aile.bill.service.mail.analyze.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pay.aile.bill.service.mail.analyze.AbstractBankMailAnalyzer;
import com.pay.aile.bill.service.mail.analyze.banktemplate.cib.AbstractCIBTemplate;
import com.pay.aile.bill.service.mail.analyze.enums.BankCodeEnum;

/**
 *
 * @author Charlie
 * @description 兴业银行
 */
@Service
public class CIBAnalyzer extends AbstractBankMailAnalyzer<AbstractCIBTemplate> {
    public static final BankCodeEnum bankCode = BankCodeEnum.CIB;

    @Override
    public boolean support(String name) {
        return StringUtils.hasText(name) && (name.equalsIgnoreCase(BankCodeEnum.CIB.getBankCode())
                || name.contains(BankCodeEnum.CIB.getBankName()));
    }

}
