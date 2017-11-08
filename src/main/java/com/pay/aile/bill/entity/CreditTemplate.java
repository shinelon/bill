package com.pay.aile.bill.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;

/**
 * <p>
 * 邮件解析模板
 * </p>
 *
 * @author yaoqiang.sun
 * @since 2017-11-02
 */
@TableName("credit_template")
public class CreditTemplate extends Model<CreditTemplate> {

	private static final long serialVersionUID = 1L;

	/**
	 * 入账币种/金额
	 */
	@TableField("accountable_amount")
	private String accountableAmount;
	/**
	 * 账单日
	 */
	@TableField(value = "bill_day")
	private String billDay;
	/**
	 * 记账日志
	 */
	@TableField("billing_date")
	private String billingDate;

	/**
	 * 卡类型
	 */
	@TableField("cardtype_id")
	private Long cardtypeId;

	/**
	 * 取现额度
	 */
	private String cash;
	/**
	 * 消费/取现/其他费用
	 */
	private String consumption;
	/**
	 * 创建时间
	 */
	@TableField(value = "create_date", fill = FieldFill.INSERT)
	private Date createDate;
	/**
	 * 信用额度
	 */
	private String credits;
	/**
	 * 本期应还款额
	 */
	@TableField("current_amount")
	private String currentAmount;
	private String cycle;
	/**
	 * 账单明细
	 */
	private String details;
	/**
	 * 到期还款日
	 */
	@TableField("due_date")
	private String dueDate;
	private Long id;
	@TableField("last_amount")
	private String lastAmount;
	/**
	 * 预借现金额度
	 */
	@TableField("prepaid_cash_amount")
	private String prepaidCashAmount;
	/**
	 * 还款/退货/费用返还
	 */
	private String repayment;
	/**
	 * 有效标志1有效0无效
	 */
	@TableField(fill = FieldFill.INSERT)
	private Integer status;

	/**
	 * 交易金额
	 */
	@TableField("transaction_amount")
	private String transactionAmount;

	/**
	 * 交易币种
	 */
	@TableField("transaction_currency")
	private String transactionCurrency;

	/**
	 * 交易日期
	 */
	@TableField("transaction_date")
	private String transactionDate;
	/**
	 * 交易说明
	 */
	@TableField("transaction_description")
	private String transactionDescription;
	/**
	 * 修改时间
	 */
	@TableField(value = "update_date", fill = FieldFill.UPDATE)
	private Date updateDate;

	public String getAccountableAmount() {
		return accountableAmount;
	}

	public String getBillDay() {
		return billDay;
	}

	public String getBillingDate() {
		return billingDate;
	}

	public Long getCardtypeId() {
		return cardtypeId;
	}

	public String getCash() {
		return cash;
	}

	public String getConsumption() {
		return consumption;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public String getCredits() {
		return credits;
	}

	public String getCurrentAmount() {
		return currentAmount;
	}

	public String getCycle() {
		return cycle;
	}

	public String getDetails() {
		return details;
	}

	public String getDueDate() {
		return dueDate;
	}

	public Long getId() {
		return id;
	}

	public String getLastAmount() {
		return lastAmount;
	}

	public String getPrepaidCashAmount() {
		return prepaidCashAmount;
	}

	public String getRepayment() {
		return repayment;
	}

	public Integer getStatus() {
		return status;
	}

	public String getTransactionAmount() {
		return transactionAmount;
	}

	public String getTransactionCurrency() {
		return transactionCurrency;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public String getTransactionDescription() {
		return transactionDescription;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setAccountableAmount(String accountableAmount) {
		this.accountableAmount = accountableAmount;
	}

	public void setBillDay(String billDay) {
		this.billDay = billDay;
	}

	public void setBillingDate(String billingDate) {
		this.billingDate = billingDate;
	}

	public void setCardtypeId(Long cardtypeId) {
		this.cardtypeId = cardtypeId;
	}

	public void setCash(String cash) {
		this.cash = cash;
	}

	public void setConsumption(String consumption) {
		this.consumption = consumption;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setCredits(String credits) {
		this.credits = credits;
	}

	public void setCurrentAmount(String currentAmount) {
		this.currentAmount = currentAmount;
	}

	public void setCycle(String cycle) {
		this.cycle = cycle;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLastAmount(String lastAmount) {
		this.lastAmount = lastAmount;
	}

	public void setPrepaidCashAmount(String prepaidCashAmount) {
		this.prepaidCashAmount = prepaidCashAmount;
	}

	public void setRepayment(String repayment) {
		this.repayment = repayment;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setTransactionAmount(String transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public void setTransactionCurrency(String transactionCurrency) {
		this.transactionCurrency = transactionCurrency;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public void setTransactionDescription(String transactionDescription) {
		this.transactionDescription = transactionDescription;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public String toString() {
		return "CreditTemplate{" + "id=" + id + ", dueDate=" + dueDate + ", currentAmount=" + currentAmount
				+ ", credits=" + credits + ", cash=" + cash + ", lastAmount=" + lastAmount + ", repayment=" + repayment
				+ ", consumption=" + consumption + ", transactionDate=" + transactionDate + ", billingDate="
				+ billingDate + ", transactionDescription=" + transactionDescription + ", transactionAmount="
				+ transactionAmount + ", accountableAmount=" + accountableAmount + ", prepaidCashAmount="
				+ prepaidCashAmount + ", details=" + details + ", cardtypeId=" + cardtypeId + ", status=" + status
				+ ", updateDate=" + updateDate + ", createDate=" + createDate + "}";
	}

	@Override
	protected Serializable pkVal() {
		return id;
	}
}
