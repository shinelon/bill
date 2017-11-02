package com.pay.aile.bill.entity;

import java.util.Date;
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
@TableName("credit_bill_detail")
public class CreditBillDetail extends Model<CreditBillDetail> {

    private static final long serialVersionUID = 1L;

	private Long id;
    /**
     * 到期还款日
     */
	@TableField("bill_id")
	private Long billId;
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
	@TableField("account_type")
	private String accountType;
    /**
     * 有效标志1有效0无效
     */
	private Integer status;
    /**
     * 修改时间
     */
	@TableField("update_date")
	private Date updateDate;
    /**
     * 创建时间
     */
	@TableField("create_date")
	private Date createDate;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBillId() {
		return billId;
	}

	public void setBillId(Long billId) {
		this.billId = billId;
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

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "CreditBillDetail{" +
			"id=" + id +
			", billId=" + billId +
			", transactionDate=" + transactionDate +
			", billingDate=" + billingDate +
			", transactionDescription=" + transactionDescription +
			", transactionAmount=" + transactionAmount +
			", accountableAmount=" + accountableAmount +
			", accountType=" + accountType +
			", status=" + status +
			", updateDate=" + updateDate +
			", createDate=" + createDate +
			"}";
	}
}
