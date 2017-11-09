package com.pay.aile.bill.service.mail.analyze.model;

import java.util.ArrayList;
import java.util.List;

import com.pay.aile.bill.entity.CreditBill;
import com.pay.aile.bill.entity.CreditBillDetail;
import com.pay.aile.bill.entity.CreditCard;

/**
 *
 * @author Charlie
 * @description 存储解析结果
 */
public class AnalyzeResult {
	/**
	 * 账单
	 */
	private CreditBill bill;
	/**
	 * 行用卡
	 */
	private CreditCard card;
	/**
	 * 账单明细
	 */
	private List<CreditBillDetail> detail;

	public AnalyzeResult() {
		bill = new CreditBill();
		detail = new ArrayList<CreditBillDetail>();
		card = new CreditCard();
	}

	public CreditBill getBill() {
		return bill;
	}

	public CreditCard getCard() {
		return card;
	}

	public List<CreditBillDetail> getDetail() {
		return detail;
	}

	public void setBill(CreditBill bill) {
		this.bill = bill;
	}

	public void setCard(CreditCard card) {
		this.card = card;
	}

	public void setDetail(List<CreditBillDetail> detail) {
		this.detail = detail;
	}

	@Override
	public String toString() {
		return "AnalyzeResult [bill=" + bill + ", detail=" + detail + "]";
	}

}
