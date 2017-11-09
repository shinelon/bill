package com.pay.aile.bill.service.mail.analyze.banktemplate.psbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pay.aile.bill.entity.CreditTemplate;
import com.pay.aile.bill.service.mail.analyze.enums.CardTypeEnum;
import com.pay.aile.bill.service.mail.analyze.model.AnalyzeParamsModel;

/**
 *
 * @author zhibin.cui
 * @description 邮储银行信用卡账单内容解析模板
 */
@Service
public class PSBCTemplate extends AbstractPSBCTemplate {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	protected void analyzeInternal(AnalyzeParamsModel apm) {
		super.analyzeInternal(apm);
	}

	@Override
	public void initRules() {
		// super.initRules();
		if (rules == null) {
			rules = new CreditTemplate();
			rules.setBillingDate("账单日 \\d{2}");
			rules.setDueDate("到期还款日 \\d{4}年\\d{2}月\\d{2}日");
			rules.setCurrentAmount("本期应还款总额 \\d+.?\\d*");
			rules.setCredits("信用额度 \\d+.?\\d*");
			rules.setPrepaidCashAmount("预借现金额度 \\d+.?\\d*");
			rules.setDetails("\\d{4}/\\d{2}/\\d{2} \\d{4}/\\d{2}/\\d{2} \\S+ \\d+.?\\d* \\d{4}");
		}
	}

	@Override
	protected void setCardType() {
		cardType = CardTypeEnum.PSBC_DEFAULT;
	}
}
