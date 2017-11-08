package com.pay.aile.bill.service.mail.analyze.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pay.aile.bill.service.mail.analyze.AbstractBankMailAnalyzer;
import com.pay.aile.bill.service.mail.analyze.banktemplate.bcm.AbstractBCMTemplate;
import com.pay.aile.bill.service.mail.analyze.enums.BankCodeEnum;

/**
 *
 * @author Charlie
 * @description 交通银行
 */
@Service
public class BCMAnalyzer extends AbstractBankMailAnalyzer<AbstractBCMTemplate> {

    @Override
    public boolean support(String name) {
        return StringUtils.hasText(name)
                && (name.contains(BankCodeEnum.BCM.getBankCode())
                        || name.contains(BankCodeEnum.BCM.getBankName()));
    }

}
