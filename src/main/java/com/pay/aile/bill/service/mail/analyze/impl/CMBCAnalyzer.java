package com.pay.aile.bill.service.mail.analyze.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pay.aile.bill.service.mail.analyze.AbstractBankMailAnalyzer;
import com.pay.aile.bill.service.mail.analyze.banktemplate.cmbc.AbstractCMBCTemplate;
import com.pay.aile.bill.service.mail.analyze.enums.BankCodeEnum;

/**
 *
 * @author zhibin.cui
 * @description 民生银行解析模版
 */
@Service("CMBCAnalyzer")
public class CMBCAnalyzer extends AbstractBankMailAnalyzer<AbstractCMBCTemplate> {

    @Override
    public boolean support(String name) {
        return StringUtils.hasText(name) && (name.equalsIgnoreCase(BankCodeEnum.CMBC.getBankCode())
                || name.contains(BankCodeEnum.CMBC.getBankName()));
    }

}
