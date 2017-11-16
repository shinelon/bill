package com.pay.aile.bill.service.mail.analyze.constant;

/**
 *
 * @author Charlie
 * @description 通用常量
 */
public class Constant {

    /**
     * 指定银行下用户邮箱对应信用卡类型的模板缓存 bank email:template
     */
    public static final String redisTemplateCache = "bill_bank_email_template";

    /**
     * 正则-匹配金额
     */
    public static final String pattern_amount = "-?\\d+\\.?\\d*";

    /**
     * 发送邮件
     */
    public static final String redisSendMail = "bill_bank_send_email";
}
