package com.pay.aile.bill.service.mail.download.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pay.aile.bill.entity.CreditEmail;
import com.pay.aile.bill.enums.MailType;
import com.pay.aile.bill.service.mail.download.BaseMailOperation;
import com.pay.aile.bill.service.mail.download.DownloadMail;
import com.pay.aile.bill.utils.SpringContextUtil;

/***
 * DownloadMailImpl.java
 *
 * @author shinelon
 *
 * @date 2017年10月30日
 *
 */
@Service
public class DownloadMailImpl implements DownloadMail {

	private static final Logger logger = LoggerFactory.getLogger(DownloadMailImpl.class);

	@Override
	public void execute(CreditEmail creditEmail) throws Exception {
		String[] mailParms = StringUtils.split(creditEmail.getEmail(), "@");
		BaseMailOperation mailOperation = SpringContextUtil.getBean(MailType.getMailType(mailParms[1]).getClzz());
		mailOperation.downloadMail(creditEmail.getEmail(), creditEmail.getPassword());

	}

	@Override
	public void execute(final String mailAddr, final String password) throws Exception {
		String[] mailParms = StringUtils.split(mailAddr, "@");
		BaseMailOperation mailOperation = SpringContextUtil.getBean(MailType.getMailType(mailParms[1]).getClzz());
		mailOperation.downloadMail(mailAddr, password);

	}

}
