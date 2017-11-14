package com.pay.aile.bill.service.mail.analyze.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pay.aile.bill.service.mail.analyze.AbstractBankMailAnalyzer;
import com.pay.aile.bill.service.mail.analyze.banktemplate.icbc.AbstractICBCTemplate;
import com.pay.aile.bill.service.mail.analyze.enums.BankCodeEnum;

/**
 *
 * @author Charlie
 * @description 工商银行解析模板
 */
@Service
public class ICBCAnalyzer extends AbstractBankMailAnalyzer<AbstractICBCTemplate> {

    @Override
    public boolean support(String name) {
        return StringUtils.hasText(name) && (name.equalsIgnoreCase(BankCodeEnum.ICBC.getBankCode())
                || name.contains(BankCodeEnum.ICBC.getBankName()));
    }

}
