package com.pay.aile.bill.contant;

import java.lang.reflect.Field;

import org.apache.commons.lang3.StringUtils;

/***
 * BankKeywordContants.java
 *
 * @author shinelon
 *
 * @date 2017年10月31日
 *
 * @描述 通过关键字检索邮件并下载
 */
public class BankKeywordContants {

	public static String ALL_BANK_KEYWORDS = "";
	public static final String BANK_KEYWORD_ABC = "中国农业银行";
	public static final String BANK_KEYWORD_BCM = "交通银行";
	public static final String BANK_KEYWORD_BOB = "北京银行";
	public static final String BANK_KEYWORD_BOC = "中国银行";
	public static final String BANK_KEYWORD_CCB = "中国建设银行";
	public static final String BANK_KEYWORD_CEB = "光大银行";
	public static final String BANK_KEYWORD_CIB = "兴业银行";
	public static final String BANK_KEYWORD_CITIC = "中信银行";
	public static final String BANK_KEYWORD_CMB = "招商银行";
	public static final String BANK_KEYWORD_CMBC = "中国民生银行";
	public static final String BANK_KEYWORD_GDB = "广东发展银行";
	public static final String BANK_KEYWORD_ICBC = "中国工商银行";
	public static final String BANK_KEYWORD_SDB = "深圳发展银行";
	// 汉字使用汉字逗号分割
	public static final String BANK_KEYWORD_SEPARATOR = "，";
	public static final String BANK_KEYWORD_SPDB = "上海浦东发展银行";
	public static final String BANK_KEYWORD_PSBC = "邮储银行";

	static {
		ALL_BANK_KEYWORDS = formatKeywords();
	}

	public static String formatKeywords() {
		StringBuffer formatKeywords = new StringBuffer(16);
		Field[] fields = BankKeywordContants.class.getDeclaredFields();
		try {
			for (Field field : fields) {
				String tmpValue = (String) field.get(BankKeywordContants.class);
				if (!StringUtils.isEmpty(tmpValue) && !tmpValue.equals(BANK_KEYWORD_SEPARATOR)) {
					formatKeywords.append(field.get(BankKeywordContants.class));
					formatKeywords.append(BANK_KEYWORD_SEPARATOR);
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return formatKeywords.deleteCharAt(formatKeywords.length() - 1).toString();
	}

	public static void main(String[] args) {
		System.out.println(formatKeywords());
	}
}
