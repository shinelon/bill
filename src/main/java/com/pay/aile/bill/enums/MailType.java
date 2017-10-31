package com.pay.aile.bill.enums;

import com.pay.aile.bill.service.mail.download.BaseMailOperation;
import com.pay.aile.bill.service.mail.download.impl.Mail126OperationImpl;
import com.pay.aile.bill.service.mail.download.impl.Mail139OperationImpl;
import com.pay.aile.bill.service.mail.download.impl.Mail163OperationImpl;
import com.pay.aile.bill.service.mail.download.impl.MailAliyunOperationImpl;
import com.pay.aile.bill.service.mail.download.impl.MailQQOperationImpl;
import com.pay.aile.bill.service.mail.download.impl.MailSinaOperationImpl;

/***
 * MailType.java
 *
 * @author shinelon
 *
 * @date 2017年10月30日
 *
 */
public enum MailType {

    MAIL_126("126.com", Mail126OperationImpl.class), MAIL_139("139.com", Mail139OperationImpl.class), MAIL_163(
            "163.com", Mail163OperationImpl.class), MAIL_ALIYUN("aliyun.com", MailAliyunOperationImpl.class), MAIL_QQ(
                    "qq.com", MailQQOperationImpl.class), MAIL_SINA("sina.com", MailSinaOperationImpl.class);

    public static MailType getMailType(String mailAddrSuffix) {
        for (MailType tmpMailType : MailType.values()) {
            if (tmpMailType.getKey().equals(mailAddrSuffix)) {
                return tmpMailType;
            }
        }
        return null;
    }

    private Class<? extends BaseMailOperation> classes;

    private String key;

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
