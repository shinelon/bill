package com.pay.aile.bill.service.mail.analyze.enums;

/**
 *
 * @author Charlie
 * @description 银行编码
 */
public enum BankCodeEnum {

    BSB("BSB", "包商"), ABC("ABC", "农业"), BCM("BCM", "交通"), BOB("BOB", "北京"), BOC(
            "BOC", "中国"), CCB("CCB", "建设"), CEB("CEB", "光大"), CIB("CIB",
                    "兴业"), CITIC("CITIC", "中信"), CMB("CMB", "招商"), CMBC("CMBC",
                            "民生"), GDB("GDB", "广发"), HXB("HXB", "华夏"),

    ICBC("ICBC", "工商"), SDB("SDB", "深圳发展"), SPDB("SPDB", "浦发银行");

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

    public static BankCodeEnum getByString(String str) {
        for (BankCodeEnum c : values()) {
            if (str.contains(c.getBankName())) {
                return c;
            }
        }
        return null;
    }

    private String bankCode;

    private String bankName;

    private BankCodeEnum(String bankCode, String name) {
        this.bankCode = bankCode;
        bankName = name;
    }

    public String getBankCode() {
        return bankCode;
    }

    public String getBankName() {
        return bankName;
    }
}
