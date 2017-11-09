package com.pay.aile.bill.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;

@TableName("credit_card")
public class CreditCard extends Model<CreditCard> {

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
	private String billDay;
	/**
	 * 取现额度
	 */
	private String cash;

	/**
	 * 创建时间
	 */
	@TableField(value = "create_date", fill = FieldFill.INSERT)
	private Date createDate;

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
	/**
	 * 有效标志1有效0无效
	 */
	@TableField(fill = FieldFill.INSERT)
	private Integer status;
	/**
	 * 修改时间
	 */
	@TableField(value = "update_date", fill = FieldFill.UPDATE)
	private Date updateDate;

	public Long getBankId() {
		return bankId;
	}

	public String getBillDay() {
		return billDay;
	}

	public String getCash() {
		return cash;
	}

	public Date getCreateDate() {
		return createDate;
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

	public Integer getStatus() {
		return status;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public void setBillDay(String billDay) {
		this.billDay = billDay;
	}

	public void setCash(String cash) {
		this.cash = cash;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	protected Serializable pkVal() {
		// TODO Auto-generated method stub
		return null;
	}

}
