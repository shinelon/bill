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
    // 若银行有多个关键字需要使用汉字逗号分隔
    public static final String BANK_KEYWORD_CCB = "中国建设银行";
    public static final String BANK_KEYWORD_ABC = "中国农业银行";
    public static final String BANK_KEYWORD_ICBC = "中国工商银行";
    public static final String BANK_KEYWORD_BOC = "中国银行";
    public static final String BANK_KEYWORD_CMBC = "中国民生银行";
    public static final String BANK_KEYWORD_CMB = "招商银行";
    public static final String BANK_KEYWORD_CIB = "兴业银行";
    public static final String BANK_KEYWORD_BOB = "北京银行";
    public static final String BANK_KEYWORD_BCM = "交通银行";
    public static final String BANK_KEYWORD_CEB = "中国光大银行";
    public static final String BANK_KEYWORD_CITIC = "中信银行";
    public static final String BANK_KEYWORD_GDB = "广东发展银行";
    public static final String BANK_KEYWORD_SPDB = "上海浦东发展银行";
    public static final String BANK_KEYWORD_SDB = "深圳发展银行";

    static {
        ALL_BANK_KEYWORDS = formatKeywords();
    }

    public static String formatKeywords() {
        StringBuffer formatKeywords = new StringBuffer(16);
        Field[] fields = BankKeywordContants.class.getDeclaredFields();
        try {
            for (Field field : fields) {
                String tmpValue = (String) field.get(BankKeywordContants.class);
                if (!StringUtils.isEmpty(tmpValue)) {
                    formatKeywords.append(field.get(BankKeywordContants.class));
                    // 中文输入法汉字逗号
                    formatKeywords.append("，");
                }
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return formatKeywords.deleteCharAt(formatKeywords.length() - 1).toString();
    }
}
