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

    /**
     * 账户类型
     */
    @TableField(value = "account_type")
    private String accountType;

    /**
     * 银行编码
     */
    @TableField(value = "bank_code")
    private String bankCode;
    /**
     * 账单开始时间
     */
    @TableField("begin_date")
    private Date beginDate;
    /**
     * 对象卡id
     */
    @TableField(value = "card_id")
    private Long cardId;
    /**
     * 取现额度
     */
    private BigDecimal cash;

    /**
     * 消费/取现/其他费用
     */
    private BigDecimal consumption;

    /**
     * 创建时间
     */
    @TableField(value = "create_date", fill = FieldFill.INSERT)
    private Date createDate;

    /**
     * 信用额度
     */
    private BigDecimal credits;

    /**
     * 本期应还款额
     */
    @TableField("current_amount")
    private BigDecimal currentAmount;

    /**
     * 到期还款日
     */
    @TableField("due_date")
    private Date dueDate;

    /**
     * 用户邮箱id
     */
    @TableField(value = "email_id")
    private Long emailId;
    /**
     * 账单结束时间
     */
    @TableField("end_date")
    private Date endDate;
    private Long id;

    @TableField("last_amount")
    private BigDecimal lastAmount;

    /**
     * 最低还款额
     */
    private BigDecimal minimum;

    /**
     * 月
     */
    private String month;

    /**
     * 还款/退货/费用返还
     */
    private BigDecimal repayment;

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
     * 年
     */
    private String year;

    /**
     * 邮件发送shijian
     */
    @TableField(value = "sent_date")
    private Date sentDate;

    public String getAccountType() {
        return accountType;
    }

    public String getBankCode() {
        return bankCode;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public Long getCardId() {
        return cardId;
    }

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

    public Long getEmailId() {
        return emailId;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getLastAmount() {
        return lastAmount;
    }

    public BigDecimal getMinimum() {
        return minimum;
    }

    public String getMonth() {
        return month;
    }

    public BigDecimal getRepayment() {
        return repayment;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public Integer getStatus() {
        return status;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public String getYear() {
        return year;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
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

    public void setEmailId(Long emailId) {
        this.emailId = emailId;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLastAmount(BigDecimal lastAmount) {
        this.lastAmount = lastAmount;
    }

    public void setMinimum(BigDecimal minimum) {
        this.minimum = minimum;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setRepayment(BigDecimal repayment) {
        this.repayment = repayment;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "CreditBill [accountType=" + accountType + ", bankCode=" + bankCode + ", beginDate="
                + beginDate + ", cardId=" + cardId + ", cash=" + cash + ", consumption="
                + consumption + ", createDate=" + createDate + ", credits=" + credits
                + ", currentAmount=" + currentAmount + ", dueDate=" + dueDate + ", emailId="
                + emailId + ", endDate=" + endDate + ", id=" + id + ", lastAmount=" + lastAmount
                + ", minimum=" + minimum + ", month=" + month + ", repayment=" + repayment
                + ", status=" + status + ", updateDate=" + updateDate + ", year=" + year + "]";
    }

    @Override
    protected Serializable pkVal() {
        return id;
    }

}
