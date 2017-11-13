package com.pay.aile.bill.service.mail.analyze.model;

import java.util.Date;

/**
 *
 * @author Charlie
 * @description 存放邮件解析过程中需要传递的数据
 */
public class AnalyzeParamsModel {
    private String bankCode;// 银行编码
    private String bankId;// 银行id
    private Long cardtypeId;// 卡片类型
    private String content;// 邮件内容
    private String attachment;// 邮件内容
    private String email;// 用户邮箱

    private Long emailId;// 邮箱id

    private AnalyzeResult result;// 解析之后的规则数据
    private Date sentDate;// 邮件发送日期

    public String getAttachment() {
        return attachment;
    }

    public String getBankCode() {
        return bankCode;
    }

    public String getBankId() {
        return bankId;
    }

    public Long getCardtypeId() {
        return cardtypeId;
    }

    public String getContent() {
        return content;
    }

    public String getEmail() {
        return email;
    }

    public Long getEmailId() {
        return emailId;
    }

    public AnalyzeResult getResult() {
        return result;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public void setCardtypeId(Long cardtypeId) {
        this.cardtypeId = cardtypeId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEmailId(Long emailId) {
        this.emailId = emailId;
    }

    public void setResult(AnalyzeResult result) {
        this.result = result;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public boolean success() {
        return result != null;
    }

    @Override
    public String toString() {
        return "AnalyzeParamsModel [bankCode=" + bankCode + ", content=" + content + ", email="
                + email + ", emailId=" + emailId + ", cardtypeId=" + cardtypeId + ", sentDate="
                + sentDate + ", result=" + result + "]";
    }

}
