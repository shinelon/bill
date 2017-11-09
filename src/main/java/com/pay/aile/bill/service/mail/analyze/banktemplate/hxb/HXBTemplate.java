package com.pay.aile.bill.service.mail.analyze.banktemplate.hxb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pay.aile.bill.mapper.CreditTemplateMapper;
import com.pay.aile.bill.service.mail.analyze.enums.CardTypeEnum;
import com.pay.aile.bill.service.mail.analyze.model.AnalyzeParamsModel;

/**
 *
 * @author Charlie
 * @description 交通银行信用卡账单内容解析模板
 */
@Service
public class HXBTemplate extends AbstractHXBTemplate {

	@Autowired
	CreditTemplateMapper creditTemplateMapper;

	@Override
	public void initRules() {
		// super.initRules();
		if (rules == null) {
			rules = creditTemplateMapper.selectById(4);
		}
		super.initDetail();

	}

	// /**
	// *
	// * @Title: analyzeDueDate
	// * @Description: 解析参数
	// * @param card
	// * @param content
	// * @param apm
	// * @return void 返回类型 @throws
	// */
	// @Override
	// protected void analyzeBillDate(CreditCard card, String content,
	// AnalyzeParamsModel apm) {
	// if (StringUtils.hasText(rules.getBillDay())) {
	//
	// String billDay = getValueByPattern("billDay", content,
	// rules.getBillDay(), apm, " ");
	// card.setBillDay(billDay);
	// }
	// }

	@Override
	protected void analyzeInternal(AnalyzeParamsModel apm) {
		super.analyzeInternal(apm);
	}

	@Override
	protected void setCardType() {
		cardType = CardTypeEnum.BCM_DEFAULT;
	}

}
