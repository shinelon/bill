package com.pay.aile.bill.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
@TableName("credit_bill")
public class CreditBill extends Model<CreditBill> {

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 到期还款日
     */
    @TableField("due_date")
    private Date dueDate;
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
    @TableField(fill = FieldFill.INSERT)
    private Integer status;
    /**
     * 修改时间
     */
    @TableField(value = "update_date", fill = FieldFill.UPDATE)
    private Date updateDate;
    /**
     * 创建时间
     */
    @TableField(value = "create_date", fill = FieldFill.INSERT)
    private Date createDate;

    /**
     * 用户邮箱id
     */
    @TableField(value = "email_id")
    private Long emailId;
    @TableField(value = "cardtype_id")
    private Long cardtypeId;
    /**
     * 邮件的发送日期
     */
    @TableField(value = "sent_date")
    private Date sentDate;
    /**
     * 银行编码
     */
    @TableField(value = "bank_code")
    private String bankCode;

    /**
     * 账户类型
     */
    @TableField(value = "account_type")
    private String accountType;

    public BigDecimal getCash() {
        return cash;
    }

    public BigDecimal getConsumption() {
        return consumption;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public BigDecimal getCurrentAmount() {
        return currentAmount;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public BigDecimal getLastAmount() {
        return lastAmount;
    }

    public BigDecimal getRepayment() {
        return repayment;
    }

    public Integer getStatus() {
        return status;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public void setConsumption(BigDecimal consumption) {
        this.consumption = consumption;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }

    public void setCurrentAmount(BigDecimal currentAmount) {
        this.currentAmount = currentAmount;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setLastAmount(BigDecimal lastAmount) {
        this.lastAmount = lastAmount;
    }

    public void setRepayment(BigDecimal repayment) {
        this.repayment = repayment;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    protected Serializable pkVal() {
        return id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmailId() {
        return emailId;
    }

    public void setEmailId(Long emailId) {
        this.emailId = emailId;
    }

    public Long getCardtypeId() {
        return cardtypeId;
    }

    public void setCardtypeId(Long cardtypeId) {
        this.cardtypeId = cardtypeId;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    @Override
    public String toString() {
        return "CreditBill [id=" + id + ", dueDate=" + dueDate
                + ", currentAmount=" + currentAmount + ", credits=" + credits
                + ", cash=" + cash + ", lastAmount=" + lastAmount
                + ", repayment=" + repayment + ", consumption=" + consumption
                + ", status=" + status + ", updateDate=" + updateDate
                + ", createDate=" + createDate + ", emailId=" + emailId
                + ", cardtypeId=" + cardtypeId + ", sentDate=" + sentDate
                + ", bankCode=" + bankCode + ", accountType=" + accountType
                + "]";
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

}
