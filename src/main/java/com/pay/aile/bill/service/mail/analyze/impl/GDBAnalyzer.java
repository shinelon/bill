package com.pay.aile.bill.service.mail.analyze.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pay.aile.bill.service.mail.analyze.AbstractBankMailAnalyzer;
import com.pay.aile.bill.service.mail.analyze.banktemplate.gdb.AbstractGDBTemplate;
import com.pay.aile.bill.service.mail.analyze.enums.BankCodeEnum;

/**
 *
 * @author Charlie
 * @description 广发银行
 */
@Service
public class GDBAnalyzer extends AbstractBankMailAnalyzer<AbstractGDBTemplate> {

    @Override
    public boolean support(String name) {
        return StringUtils.hasText(name)
                && (name.contains(BankCodeEnum.GDB.getBankCode())
                        || name.contains(BankCodeEnum.GDB.getBankName()));
    }

}
