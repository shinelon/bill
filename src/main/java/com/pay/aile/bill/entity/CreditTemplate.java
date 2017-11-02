package com.pay.aile.bill.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

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

	private String id;
    /**
     * 到期还款日
     */
	@TableField("due_date")
	private String dueDate;
    /**
     * 本期应还款额
     */
	@TableField("current_amount")
	private String currentAmount;
    /**
     * 信用额度
     */
	private String credits;
    /**
     * 取现额度
     */
	private String cash;
	@TableField("last_amount")
	private String lastAmount;
    /**
     * 还款/退货/费用返还
     */
	private String repayment;
    /**
     * 消费/取现/其他费用
     */
	private String consumption;
    /**
     * 交易日期
     */
	@TableField("transaction_date")
	private String transactionDate;
    /**
     * 记账日志
     */
	@TableField("billing_date")
	private String billingDate;
    /**
     * 交易说明
     */
	@TableField("transaction_description")
	private String transactionDescription;
    /**
     * 交易币种/金额
     */
	@TableField("transaction_amount")
	private String transactionAmount;
    /**
     * 入账币种/金额
     */
	@TableField("accountable_amount")
	private String accountableAmount;
    /**
     * 预借现金额度
     */
	@TableField("prepaid_cash_amount")
	private BigDecimal prepaidCashAmount;
    /**
     * 账单明细
     */
	private String details;
    /**
     * 卡类型
     */
	@TableField("cardtype_id")
	private String cardtypeId;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getCurrentAmount() {
		return currentAmount;
	}

	public void setCurrentAmount(String currentAmount) {
		this.currentAmount = currentAmount;
	}

	public String getCredits() {
		return credits;
	}

	public void setCredits(String credits) {
		this.credits = credits;
	}

	public String getCash() {
		return cash;
	}

	public void setCash(String cash) {
		this.cash = cash;
	}

	public String getLastAmount() {
		return lastAmount;
	}

	public void setLastAmount(String lastAmount) {
		this.lastAmount = lastAmount;
	}

	public String getRepayment() {
		return repayment;
	}

	public void setRepayment(String repayment) {
		this.repayment = repayment;
	}

	public String getConsumption() {
		return consumption;
	}

	public void setConsumption(String consumption) {
		this.consumption = consumption;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getBillingDate() {
		return billingDate;
	}

	public void setBillingDate(String billingDate) {
		this.billingDate = billingDate;
	}

	public String getTransactionDescription() {
		return transactionDescription;
	}

	public void setTransactionDescription(String transactionDescription) {
		this.transactionDescription = transactionDescription;
	}

	public String getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(String transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public String getAccountableAmount() {
		return accountableAmount;
	}

	public void setAccountableAmount(String accountableAmount) {
		this.accountableAmount = accountableAmount;
	}

	public BigDecimal getPrepaidCashAmount() {
		return prepaidCashAmount;
	}

	public void setPrepaidCashAmount(BigDecimal prepaidCashAmount) {
		this.prepaidCashAmount = prepaidCashAmount;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getCardtypeId() {
		return cardtypeId;
	}

	public void setCardtypeId(String cardtypeId) {
		this.cardtypeId = cardtypeId;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "CreditTemplate{" +
			"id=" + id +
			", dueDate=" + dueDate +
			", currentAmount=" + currentAmount +
			", credits=" + credits +
			", cash=" + cash +
			", lastAmount=" + lastAmount +
			", repayment=" + repayment +
			", consumption=" + consumption +
			", transactionDate=" + transactionDate +
			", billingDate=" + billingDate +
			", transactionDescription=" + transactionDescription +
			", transactionAmount=" + transactionAmount +
			", accountableAmount=" + accountableAmount +
			", prepaidCashAmount=" + prepaidCashAmount +
			", details=" + details +
			", cardtypeId=" + cardtypeId +
			"}";
	}
}
