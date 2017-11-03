package com.pay.aile.bill.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;

/**
 * <p>
 * 邮箱文件
 * </p>
 *
 * @author yaoqiang.sun
 * @since 2017-11-02
 */
@TableName("credit_file")
public class CreditFile extends Model<CreditFile> {

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 邮箱对应的内容文件名称
     */
    @TableField("file_name")
    private String fileName;
    /**
     * 邮件收到时间
     */
    @TableField("receive_date")
    private Long receiveDate;
    /**
     * 邮箱id
     */
    @TableField("email_id")
    private Long emailId;
    /**
     * 0 未处理 1 成功 2 失败
     */
    @TableField("process_result")
    private Integer processResult;
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

    public Date getCreateDate() {
        return createDate;
    }

    public Long getEmailId() {
        return emailId;
    }

    public Long getId() {
        return id;
    }

    public Integer getProcessResult() {
        return processResult;
    }

    public Long getReceiveDate() {
        return receiveDate;
    }

    public Integer getStatus() {
        return status;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setEmailId(Long emailId) {
        this.emailId = emailId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProcessResult(Integer processResult) {
        this.processResult = processResult;
    }

    public void setReceiveDate(Long receiveDate) {
        this.receiveDate = receiveDate;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "CreditFile [id=" + id + ", fileName=" + fileName
                + ", receiveDate=" + receiveDate + ", emailId=" + emailId
                + ", processResult=" + processResult + ", status=" + status
                + ", updateDate=" + updateDate + ", createDate=" + createDate
                + "]";
    }
}
