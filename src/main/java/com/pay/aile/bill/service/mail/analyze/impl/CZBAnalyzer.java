package com.pay.aile.bill.service.mail.analyze.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pay.aile.bill.service.mail.analyze.AbstractBankMailAnalyzer;
import com.pay.aile.bill.service.mail.analyze.banktemplate.czb.AbstractCZBTemplate;
import com.pay.aile.bill.service.mail.analyze.enums.BankCodeEnum;

/**
 *
 * @author Charlie
 * @description 浙商银行
 */
@Service
public class CZBAnalyzer extends AbstractBankMailAnalyzer<AbstractCZBTemplate> {

    @Override
    public boolean support(String name) {
        return StringUtils.hasText(name)
                && (name.contains(BankCodeEnum.CZB.getBankCode())
                        || name.contains(BankCodeEnum.CZB.getBankName()));
    }

}
