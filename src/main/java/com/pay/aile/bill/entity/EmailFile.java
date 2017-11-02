package com.pay.aile.bill.entity;

import java.io.Serializable;

/**
 *
 * @ClassName: EmailFile
 * @Description: email内容存储
 * @author jinjing
 * @date 2017年11月2日
 *
 */
public class EmailFile implements Serializable {

    /**
     * @Fields field:field:{todo}(用一句话描述这个变量表示什么)
     */

    private static final long serialVersionUID = 3698900308682223943L;

    // 邮件内容
    private String content;

    // 创建时间
    private String createDate;

    // 邮件 地址
    private String email;

    // 文件名称 主键
    private String fileName;

    // 接收时间
    private String receiveDate;

    // 邮件主题
    private String subject;

    public String getContent() {
        return content;
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getEmail() {
        return email;
    }

    public String getFileName() {
        return fileName;
    }

    public String getReceiveDate() {
        return receiveDate;
    }

    public String getSubject() {
        return subject;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
