package com.pay.aile.bill.service.mail.analyze.banktemplate.ccb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pay.aile.bill.mapper.CreditTemplateMapper;
import com.pay.aile.bill.service.mail.analyze.enums.CardTypeEnum;
import com.pay.aile.bill.service.mail.analyze.model.AnalyzeParamsModel;

/**
 *
 * @author Charlie
 * @description 广大银行信用卡账单内容解析模板
 */
@Service
public class CCBTemplate extends AbstractCCBTemplate {

	@Autowired
	CreditTemplateMapper creditTemplateMapper;

	@Override
	public void initRules() {
		// super.initRules();
		if (rules == null) {
			rules = creditTemplateMapper.selectById(3);
		}
	}

	@Override
	protected void analyzeInternal(AnalyzeParamsModel apm) {
		super.analyzeInternal(apm);
	}

	@Override
	protected void setCardType() {
		cardType = CardTypeEnum.CCB_LK;
	}
}
