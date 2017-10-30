package com.pay.aile.bill.enums;

/***
 * MailType.java
 *
 * @author shinelon
 *
 * @date 2017年10月30日
 *
 */
public enum MailType {
    Mail_126("126.com");
    private String value;

    MailType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
