package com.pay.aile.bill.service.mail.analyze.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pay.aile.bill.service.mail.analyze.AbstractBankMailAnalyzer;
import com.pay.aile.bill.service.mail.analyze.banktemplate.hxb.AbstractHXBTemplate;
import com.pay.aile.bill.service.mail.analyze.enums.BankCodeEnum;

/**
 *
 * @author Charlie
 * @description 建设银行
 */
@Service("HBXAnalyzer")
public class HXBAnalyzer extends AbstractBankMailAnalyzer<AbstractHXBTemplate> {

    @Override
    public boolean support(String name) {
        return StringUtils.hasText(name) && (name.equalsIgnoreCase(BankCodeEnum.HXB.getBankCode())
                || name.contains(BankCodeEnum.HXB.getBankName()));
    }

}
