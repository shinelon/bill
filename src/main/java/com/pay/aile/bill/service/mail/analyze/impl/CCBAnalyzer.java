package com.pay.aile.bill.service.mail.analyze.impl;

import org.springframework.stereotype.Service;

import com.pay.aile.bill.service.mail.analyze.AbstractBankMailAnalyzer;
import com.pay.aile.bill.service.mail.analyze.banktemplate.ccb.AbstractCCBTemplate;

/**
 *
 * @author Charlie
 * @description
 */
@Service("CCBAnalyzer")
public class CCBAnalyzer extends AbstractBankMailAnalyzer<AbstractCCBTemplate> {

	@Override
	public boolean support(String name) {
		// TODO
		return true;
	}

	@Override
	protected void preAnalyze(String content) {

	}

}
