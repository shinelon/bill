package com.pay.aile.bill.service.mail.analyze.enums;

/**
 * 
 * @author Charlie
 * @description 信用卡类别
 */
public enum CardTypeEnum {
    CITIC_STANDARD("CITIC_STANDARD", BankCodeEnum.CITIC,
            "中信银行标准卡IC信用卡"), CMB_YOUNG("CMB_YOUNG", BankCodeEnum.CMB,
                    "招商银行YOUNG卡"), CMB_UNIONPAY("CMB_UNIONPAY",
                            BankCodeEnum.CMB,
                            "招商银行银联单币卡"), CEB_CREDITCARD("CEB_CREDITCARD",
                                    BankCodeEnum.CMB, "光大银行信用卡");

    private BankCodeEnum bankCode;
    private String cardCode;
    private String cardName;

    private CardTypeEnum(String cardCode, BankCodeEnum bankCode,
            String cardName) {
        this.cardCode = cardCode;
        this.bankCode = bankCode;
        this.cardName = cardName;
    }

    public String getCardCode() {
        return this.cardCode;
    }

    public CardTypeEnum getByCardCode(String cardCode) {
        for (CardTypeEnum c : values()) {
            if (cardCode.equals(c.cardCode)) {
                return c;
            }
        }
        return null;
    }

    public BankCodeEnum getBankCode() {
        return this.bankCode;
    }

    public String getCardName() {
        return this.cardName;
    }
}
