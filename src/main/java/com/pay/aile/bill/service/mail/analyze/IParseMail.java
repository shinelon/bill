package com.pay.aile.bill.service.mail.analyze;

import com.pay.aile.bill.entity.CreditEmail;

/**
 *
 * @author Charlie
 * @description 账单解析任务的入口
 */
public interface IParseMail {

	public void execute();

	public void execute(CreditEmail creditEmail);
}
