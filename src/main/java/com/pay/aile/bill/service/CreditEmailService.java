package com.pay.aile.bill.service;

import java.util.List;

import com.pay.aile.bill.entity.CreditEmail;

public interface CreditEmailService {
	/**
	 *
	 * @Title: getEmailList
	 * @Description:查询邮件
	 * @param email
	 * @return 参数 @return List<CreditEmail> 返回类型 @throws
	 */
	public List<CreditEmail> getEmailList(CreditEmail email);

	/**
	 *
	 * @Title: saveOrUpdate
	 * @Description: 保存
	 * @param email
	 * @return CreditEmail 返回类型 @throws
	 */
	public CreditEmail saveOrUpdate(CreditEmail email);

}
