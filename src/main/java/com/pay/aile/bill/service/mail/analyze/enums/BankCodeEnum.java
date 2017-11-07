package com.pay.aile.bill.service.mail.analyze.enums;

/**
 * 
 * @author Charlie
 * @description 银行编码
 */
public enum BankCodeEnum {
    ABC("ABC", "中国农业银行"), BCM("BCM", "交通银行"), BOB("BOB", "北京银行"), BOC("BOC",
            "中国银行"), CCB("CCB", "中国建设银行"), CEB("CEB", "光大银行"), CIB("CIB",
                    "兴业银行"), CITIC("CITIC", "中信银行"), CMB("CMB", "招商银行"), CMBC(
                            "CMBC", "中国民生银行"), GDB("GDB", "广东发展银行"), ICBC(
                                    "ICBC", "中国工商银行"), SDB("SDB", "深圳发展银行");

    private String bankName;
    private String bankCode;

    private BankCodeEnum(String bankCode, String name) {
        this.bankCode = bankCode;
        this.bankName = name;
    }

    public String getBankName() {
        return bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public static BankCodeEnum getByBankCode(String bankCode) {
        for (BankCodeEnum c : values()) {
            if (bankCode.equals(c.bankCode)) {
                return c;
            }
        }
        return null;
    }

    public static BankCodeEnum getByBankName(String bankName) {
        for (BankCodeEnum c : values()) {
            if (bankName.equals(c.bankName)) {
                return c;
            }
        }
        return null;
    }
}
