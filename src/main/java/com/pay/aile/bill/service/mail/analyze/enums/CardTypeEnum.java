package com.pay.aile.bill.service.mail.analyze.enums;

/**
 *
 * @author Charlie
 * @description 信用卡类别
 */
public enum CardTypeEnum {
    CZB_DEFAULT("CZB_DEFAULT", BankCodeEnum.CZB, "浙商银行信用卡"), BOB_DEFAULT(
            "BOB_DEFAULT", BankCodeEnum.BOB,
            "北京银行信用卡"), GDB_DEFAULT("GDB_DEFAULT", BankCodeEnum.GDB,
                    "广发银行信用卡"), BSB_DEFAULT("BSB_DEFAULT", BankCodeEnum.BSB,
                            "包商银行信用卡"), ABC_DEFAULT("ABC_DEFAULT",
                                    BankCodeEnum.ABC, "农业银行信用卡"), BCM_DEFAULT(
                                            "BCM_DEFAULT", BankCodeEnum.BCM,
                                            "交通银行信用卡"), CIB_DEFAULT(
                                                    "CIB_DEFAULT",
                                                    BankCodeEnum.CIB,
                                                    "兴业银行信用卡"), CEB_GOLD(
                                                            "CEB_GOLD",
                                                            BankCodeEnum.CCB,
                                                            "光大信用卡金卡"), CEB_DEFAULT(
                                                                    "CEB_DEFAULT",
                                                                    BankCodeEnum.CCB,
                                                                    "光大信用卡PDF"), CCB_LK(
                                                                            "CCB_LK",
                                                                            BankCodeEnum.CCB,
                                                                            "建行龙卡"), CITIC_STANDARD(
                                                                                    "CITIC_STANDARD",
                                                                                    BankCodeEnum.CITIC,
                                                                                    "中信银行标准卡IC信用卡"), CMB_UNIONPAY(
                                                                                            "CMB_UNIONPAY",
                                                                                            BankCodeEnum.CMB,
                                                                                            "招商银行银联单币卡"), ICBC_MDC(
                                                                                                    "ICBC_MDC",
                                                                                                    BankCodeEnum.ICBC,
                                                                                                    "工商银行牡丹卡"), SPDB(
                                                                                                            "SPDB",
                                                                                                            BankCodeEnum.SPDB,
                                                                                                            "浦发银行信用卡"),

    CMB_YOUNG("CMB_YOUNG", BankCodeEnum.CMB, "招商银行YOUNG卡");

    private BankCodeEnum bankCode;
    private String cardCode;
    private String cardName;

    private CardTypeEnum(String cardCode, BankCodeEnum bankCode,
            String cardName) {
        this.cardCode = cardCode;
        this.bankCode = bankCode;
        this.cardName = cardName;
    }

    public BankCodeEnum getBankCode() {
        return bankCode;
    }

    public CardTypeEnum getByCardCode(String cardCode) {
        for (CardTypeEnum c : values()) {
            if (cardCode.equals(c.cardCode)) {
                return c;
            }
        }
        return null;
    }

    public String getCardCode() {
        return cardCode;
    }

    public String getCardName() {
        return cardName;
    }
}
