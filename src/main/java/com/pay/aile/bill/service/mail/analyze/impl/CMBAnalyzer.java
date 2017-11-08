package com.pay.aile.bill.service.mail.analyze.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pay.aile.bill.service.mail.analyze.AbstractBankMailAnalyzer;
import com.pay.aile.bill.service.mail.analyze.banktemplate.cmb.AbstractCMBTemplate;
import com.pay.aile.bill.service.mail.analyze.enums.BankCodeEnum;

/**
 * 
 * @author Charlie
 * @description 招商银行解析模板
 */
@Service
public class CMBAnalyzer extends AbstractBankMailAnalyzer<AbstractCMBTemplate> {
    public static final BankCodeEnum bankCode = BankCodeEnum.CMB;

    @Override
    public boolean support(String name) {
        return StringUtils.hasText(name)
                && (name.contains(BankCodeEnum.CMB.getBankCode())
                        || name.contains(BankCodeEnum.CMB.getBankName()));
    }

}
