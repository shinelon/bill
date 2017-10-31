package com.pay.aile.bill.service.mail.download;

/***
 * DownloadMail.java
 *
 * @author shinelon
 *
 * @date 2017年10月30日
 *
 */
public interface DownloadMail {
    /***
     * 下载用户邮件
     *
     * @param mailAddr
     * @param password
     * @throws Exception
     */
    void execute(final String mailAddr, final String password) throws Exception;
}
