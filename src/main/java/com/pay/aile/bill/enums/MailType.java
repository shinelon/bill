package com.pay.aile.bill.enums;

import com.pay.aile.bill.service.mail.download.BaseMailOperation;
import com.pay.aile.bill.service.mail.download.impl.Mail126OperationImpl;

/***
 * MailType.java
 *
 * @author shinelon
 *
 * @date 2017年10月30日
 *
 */
public enum MailType {

    Mail_126("126.com", Mail126OperationImpl.class);
    private String key;
    private Class<? extends BaseMailOperation> classes;

    MailType(String key, Class<? extends BaseMailOperation> classes) {
        this.key = key;
        this.classes = classes;
    }

    public Class<? extends BaseMailOperation> getClzz() {
        return classes;
    }

    public String getKey() {
        return key;
    }
}
