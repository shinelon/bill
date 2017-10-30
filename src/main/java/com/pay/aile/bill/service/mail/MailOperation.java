package com.pay.aile.bill.service.mail;

/***
 * MailOperation.java
 *
 * @author shinelon
 *
 * @date 2017年10月30日
 *
 */
public interface MailOperation {
    /***
     * 下载邮件
     *
     * 注意126、163、和qq邮箱使用POP3授权码
     *
     * @param mailAddr
     * @param password
     * @throws Exception
     */
    void downloadMail(final String mailAddr, final String password) throws Exception;
}
