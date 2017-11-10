package com.pay.aile.bill.service.mail.analyze.banktemplate.boc;

import org.springframework.stereotype.Service;

import com.pay.aile.bill.entity.CreditBillDetail;
import com.pay.aile.bill.entity.CreditTemplate;
import com.pay.aile.bill.service.mail.analyze.enums.CardTypeEnum;
import com.pay.aile.bill.service.mail.analyze.util.DateUtil;

/**
 *
 * @author zhibin.cui
 * @description 中国银行信用卡账单内容解析模板
 */
@Service
public class BOCTemplate extends AbstractBOCTemplate {

	@Override
	public void initRules() {
		if (rules == null) {
			rules = new CreditTemplate();
			rules.setBillingDate("Due \\d{4}-\\d{2}-\\d{2} \\d{4}-\\d{2}-\\d{2}"); // 账单日
			rules.setDueDate("Due \\d{4}-\\d{2}-\\d{2}");
			rules.setCurrentAmount("Due \\d{4}-\\d{2}-\\d{2} \\d{4}-\\d{2}-\\d{2} \\d+.?\\d*");
			rules.setDetails("\\d{4}-\\d{2}-\\d{2} \\d{4}-\\d{2}-\\d{2} \\d{4} \\S+ \\d+.?\\d*");
		}
	};

	@Override
	protected void setCardType() {
		cardType = CardTypeEnum.BOC_DEFAULT;
	}

	@Override
	protected CreditBillDetail setCreditBillDetail(String detail) {
		CreditBillDetail cbd = new CreditBillDetail();
		String[] sa = detail.split(" ");
		cbd.setTransactionDate(DateUtil.parseDate(sa[0]));
		cbd.setBillingDate(DateUtil.parseDate(sa[1]));
		if (sa[sa.length - 2] != null) {
			// 存入
			cbd.setTransactionAmount("-" + sa[sa.length - 2].replaceAll("\\n", ""));
		} else if (sa[sa.length - 1] != null) {
			// 支出
			cbd.setTransactionAmount(sa[sa.length - 1].replaceAll("\\n", ""));
		}
		String desc = "";
		for (int i = 2; i < sa.length - 2; i++) {
			desc = desc + sa[i];
		}
		cbd.setTransactionDescription(desc);
		return cbd;
	}

}
