package com.pay.aile.bill.service.mail.analyze.model;

import java.io.Serializable;
import java.util.Date;

public class CreditFileModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String fileName;
    private Long receiveDate;
    private Long emailId;
    private Integer processResult;
    private Integer status;
    private Date updateDate;
    private Date createDate;
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Long receiveDate) {
        this.receiveDate = receiveDate;
    }

    public Long getEmailId() {
        return emailId;
    }

    public void setEmailId(Long emailId) {
        this.emailId = emailId;
    }

    public Integer getProcessResult() {
        return processResult;
    }

    public void setProcessResult(Integer processResult) {
        this.processResult = processResult;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "CreditFileModel [id=" + id + ", fileName=" + fileName
                + ", receiveDate=" + receiveDate + ", emailId=" + emailId
                + ", processResult=" + processResult + ", status=" + status
                + ", updateDate=" + updateDate + ", createDate=" + createDate
                + ", email=" + email + "]";
    }
}
