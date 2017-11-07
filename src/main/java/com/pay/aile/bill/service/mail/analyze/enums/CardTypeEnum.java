package com.pay.aile.bill.service.mail.analyze.enums;

/**
 *
 * @author Charlie
 * @description 信用卡类别
 */
public enum CardTypeEnum {
	CCB_LK("CCB_LK", BankCodeEnum.CCB, "建行龙卡"), CITIC_STANDARD("CITIC_STANDARD", BankCodeEnum.CITIC,
			"中信银行标准卡IC信用卡"), CMB_UNIONPAY("CMB_UNIONPAY", BankCodeEnum.CMB,
					"招商银行银联单币卡"), CMB_YOUNG("CMB_YOUNG", BankCodeEnum.CMB, "招商银行YOUNG卡");

	private BankCodeEnum bankCode;
	private String cardCode;
	private String cardName;

	private CardTypeEnum(String cardCode, BankCodeEnum bankCode, String cardName) {
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
