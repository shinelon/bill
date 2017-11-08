package com.pay.aile.bill.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

@TableName("credit_card")
public class CreditCard extends Model<CreditAccountType> {

	/**
	 * @Fields
	 */

	private static final long serialVersionUID = -8413068283560221578L;
	/**
	 * 所属银行
	 */
	@TableField("bank_id")
	private Long bankId;
	/**
	 * 还款日
	 */
	@TableField("bill_day")
	private Integer billDay;
	/**
	 * 取现额度
	 */
	private String cash;

	/**
	 * 信用额度
	 */
	private String credits;

	private Long id;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 卡号
	 */
	private String numbers;

	/**
	 * 预借现金额度
	 */
	@TableField("prepaid_cash_amount")
	private String prepaidCashAmount;

	public Long getBankId() {
		return bankId;
	}

	public Integer getBillDay() {
		return billDay;
	}

	public String getCash() {
		return cash;
	}

	public String getCredits() {
		return credits;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getNumbers() {
		return numbers;
	}

	public String getPrepaidCashAmount() {
		return prepaidCashAmount;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public void setBillDay(Integer billDay) {
		this.billDay = billDay;
	}

	public void setCash(String cash) {
		this.cash = cash;
	}

	public void setCredits(String credits) {
		this.credits = credits;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNumbers(String numbers) {
		this.numbers = numbers;
	}

	public void setPrepaidCashAmount(String prepaidCashAmount) {
		this.prepaidCashAmount = prepaidCashAmount;
	}

	@Override
	protected Serializable pkVal() {
		// TODO Auto-generated method stub
		return null;
	}

}
