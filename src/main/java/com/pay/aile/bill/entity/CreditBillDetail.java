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

    public String getAccountableAmount() {
        return accountableAmount;
    }

    public String getAccountType() {
        return accountType;
    }

    public Long getBillId() {
        return billId;
    }

    public String getBillingDate() {
        return billingDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Long getId() {
        return id;
    }

    public Integer getStatus() {
        return status;
    }

    public String getTransactionAmount() {
        return transactionAmount;
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

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public void setBillingDate(String billingDate) {
        this.billingDate = billingDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
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
        return "CreditBillDetail{" + "id=" + id + ", billId=" + billId + ", transactionDate=" + transactionDate
                + ", billingDate=" + billingDate + ", transactionDescription=" + transactionDescription
                + ", transactionAmount=" + transactionAmount + ", accountableAmount=" + accountableAmount
                + ", accountType=" + accountType + ", status=" + status + ", updateDate=" + updateDate + ", createDate="
                + createDate + "}";
    }

    @Override
    protected Serializable pkVal() {
        return id;
    }
}
