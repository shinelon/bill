package com.pay.aile.bill.utils;

import com.pay.aile.bill.enums.MailType;

/***
 * MailTypeUtil.java
 *
 * @author shinelon
 *
 * @date 2017年10月30日
 *
 */
public class MailTypeUtil {
    public static MailType getMailType(String mailAddrSuffix) {
        for (MailType tmpMailType : MailType.values()) {
            if (tmpMailType.getValue().equals(mailAddrSuffix)) {
                return tmpMailType;
            }
        }
        return null;
    }
}
