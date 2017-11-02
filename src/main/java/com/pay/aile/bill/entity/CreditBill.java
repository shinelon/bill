package com.pay.aile.bill.entity;

import java.math.BigDecimal;
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
@TableName("credit_bill")
public class CreditBill extends Model<CreditBill> {

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
	private BigDecimal currentAmount;
    /**
     * 信用额度
     */
	private BigDecimal credits;
    /**
     * 取现额度
     */
	private BigDecimal cash;
	@TableField("last_amount")
	private BigDecimal lastAmount;
    /**
     * 还款/退货/费用返还
     */
	private BigDecimal repayment;
    /**
     * 消费/取现/其他费用
     */
	private BigDecimal consumption;
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

	public BigDecimal getCurrentAmount() {
		return currentAmount;
	}

	public void setCurrentAmount(BigDecimal currentAmount) {
		this.currentAmount = currentAmount;
	}

	public BigDecimal getCredits() {
		return credits;
	}

	public void setCredits(BigDecimal credits) {
		this.credits = credits;
	}

	public BigDecimal getCash() {
		return cash;
	}

	public void setCash(BigDecimal cash) {
		this.cash = cash;
	}

	public BigDecimal getLastAmount() {
		return lastAmount;
	}

	public void setLastAmount(BigDecimal lastAmount) {
		this.lastAmount = lastAmount;
	}

	public BigDecimal getRepayment() {
		return repayment;
	}

	public void setRepayment(BigDecimal repayment) {
		this.repayment = repayment;
	}

	public BigDecimal getConsumption() {
		return consumption;
	}

	public void setConsumption(BigDecimal consumption) {
		this.consumption = consumption;
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
		return "CreditBill{" +
			"id=" + id +
			", dueDate=" + dueDate +
			", currentAmount=" + currentAmount +
			", credits=" + credits +
			", cash=" + cash +
			", lastAmount=" + lastAmount +
			", repayment=" + repayment +
			", consumption=" + consumption +
			", status=" + status +
			", updateDate=" + updateDate +
			", createDate=" + createDate +
			"}";
	}
}
