package com.pay.aile.bill.service.mail.analyze.model;

import java.util.Date;

/**
 * 
 * @author Charlie
 * @description 存放邮件解析过程中需要传递的数据
 */
public class AnalyzeParamsModel {
    private String bankCode;//银行
    private String content;//邮件内容
    private String email;//用户邮箱
    private Long emailId;
    private Long cardtypeId;
    private Date receiveDate;//邮件日期

    private AnalyzeResult result;//解析之后的规则数据

    public boolean success() {
        return result != null;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AnalyzeResult getResult() {
        return result;
    }

    public void setResult(AnalyzeResult result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "AnalyzeParamsModel [bankCode=" + bankCode + ", content="
                + content + ", email=" + email + ", emailId=" + emailId
                + ", cardtypeId=" + cardtypeId + ", receiveDate=" + receiveDate
                + ", result=" + result + "]";
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

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

}
