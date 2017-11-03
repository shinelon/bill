package com.pay.aile.bill.service.mail.download;

import com.pay.aile.bill.entity.CreditEmail;

/***
 * DownloadMail.java
 *
 * @author shinelon
 *
 * @date 2017年10月30日
 *
 */
public interface DownloadMail {
	/**
	 *
	 * @Title: execute
	 * @Description:下载邮件
	 * @param creditEmail
	 * @throws Exception
	 *             参数 @return void 返回类型 @throws
	 */
	void execute(CreditEmail creditEmail) throws Exception;

	/***
	 * 下载用户邮件
	 *
	 * @param mailAddr
	 * @param password
	 * @throws Exception
	 */
	void execute(final String mailAddr, final String password) throws Exception;
}
