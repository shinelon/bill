package com.pay.aile.bill.service.mail.analyze.enums;

import org.springframework.util.StringUtils;

/**
 * 
 * @author Charlie
 * @description 解析账单时要提取的内容标识
 */
public enum MailKey {
    payDate, //到期还款日
    billDate, //账单日
    payAmount, //本期应还
    minAmount, //最低还款额
    limitAmount, //额度
    preCash, //预借现金
    enCash, //取现
    transDetail;//账单明细

    public static MailKey getByString(String key) {
        if (!StringUtils.hasText(key)) {
            return null;
        }
        for (MailKey k : values()) {
            if (key.equals(k.name())) {
                return k;
            }
        }
        return null;
    }
}
