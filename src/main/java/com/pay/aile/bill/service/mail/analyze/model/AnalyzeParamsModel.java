package com.pay.aile.bill.service.mail.analyze.model;

/**
 * 
 * @author Charlie
 * @description 存放邮件解析过程中需要传递的数据
 */
public class AnalyzeParamsModel {
    private String bankCode;//银行
    private String content;//邮件内容
    private String email;//用户邮箱

    private Object afterAnalyze;//解析之后的规则数据,暂用Object代替,待实体定以后改为具体的实体

    public boolean success() {
        return afterAnalyze != null;
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

    public Object getAfterAnalyze() {
        return afterAnalyze;
    }

    public void setAfterAnalyze(Object afterAnalyze) {
        this.afterAnalyze = afterAnalyze;
    }

    @Override
    public String toString() {
        return "AnalyzeParamsModel [bankCode=" + bankCode + ", content="
                + content + ", email=" + email + ", afterAnalyze="
                + afterAnalyze + "]";
    }
}
