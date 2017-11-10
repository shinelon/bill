package com.pay.aile.bill.service.mail.analyze.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pay.aile.bill.service.mail.analyze.AbstractBankMailAnalyzer;
import com.pay.aile.bill.service.mail.analyze.banktemplate.boc.AbstractBOCTemplate;
import com.pay.aile.bill.service.mail.analyze.enums.BankCodeEnum;

/**
 *
 * @author zhibin.cui
 * @description 中国银行解析模版
 */
@Service("BOCAnalyzer")
public class BOCAnalyzer extends AbstractBankMailAnalyzer<AbstractBOCTemplate> {

	@Override
	public boolean support(String name) {
		return StringUtils.hasText(name)
				&& (name.contains(BankCodeEnum.BOC.getBankCode()) || name.contains(BankCodeEnum.BOC.getBankName()));
	}

}
