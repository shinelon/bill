package com.pay.aile.bill.service.mail.analyze.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pay.aile.bill.service.mail.analyze.AbstractBankMailAnalyzer;
import com.pay.aile.bill.service.mail.analyze.banktemplate.ccb.AbstractCCBTemplate;
import com.pay.aile.bill.service.mail.analyze.enums.BankCodeEnum;

/**
 *
 * @author Charlie
 * @description 建设银行
 */
@Service("CCBAnalyzer")
public class CCBAnalyzer extends AbstractBankMailAnalyzer<AbstractCCBTemplate> {
    public static final BankCodeEnum bankCode = BankCodeEnum.CCB;

    @Override
    public boolean support(String name) {
        return StringUtils.hasText(name)
                && (name.contains(BankCodeEnum.CCB.getBankCode())
                        || name.contains(BankCodeEnum.CCB.getBankName()));
    }

}
