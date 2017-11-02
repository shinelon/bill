package com.pay.aile.bill.utils;

import javax.mail.Message;
import javax.mail.internet.MimeMessage;

import org.assertj.core.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.pay.aile.bill.entity.EmailFile;
import com.pay.aile.bill.exception.MailBillException;

/***
 * DownloadUtil.java
 *
 * @author shinelon
 *
 * @date 2017年10月30日
 *
 */
@Component
public class MongoDownloadUtil {
	private static final Logger logger = LoggerFactory.getLogger(MongoDownloadUtil.class);

	@Autowired
	private MongoTemplate mongoTemplate;

	public String getFile(String fileName) throws MailBillException {

		try {

			Criteria criteria = new Criteria("fileName");
			criteria.is(fileName);
			Query query = new Query(criteria);
			EmailFile ef = mongoTemplate.findOne(query, EmailFile.class);
			return ef.getContent();
		} catch (Exception e) {

			logger.error(e.getMessage());
			throw new MailBillException(e.getMessage());
		}

	}

	public void saveFile(Message message) throws MailBillException {
		StringBuffer content = new StringBuffer(20480);
		String subject = "";
		try {
			EmailFile ef = new EmailFile();

			MimeMessage msg = (MimeMessage) message;
			subject = MailDecodeUtil.getSubject(msg);
			String receiveAdd = MailDecodeUtil.getReceiveAddress(msg, null);
			String senderAdd = MailDecodeUtil.getFrom(msg);
			String sentDate = MailDecodeUtil.getSentDate(msg, "yyyyMMddHHmmss");
			logger.debug("subject:{} receiveAdd:{} senderAdd:{} sentData:{}", subject, receiveAdd, senderAdd, sentDate);

			MailDecodeUtil.getMailTextContent(msg, content);
			content = MailDecodeUtil.getUtf8(content);
			ef.setSubject(message.getSubject());
			ef.setReceiveDate(DateUtil.formatAsDatetime(message.getReceivedDate()));
			ef.setContent(content.toString());
			ef.setFileName(message.getSubject() + "_" + DateUtil.formatAsDatetime(message.getReceivedDate()));
			// ef.setEmail(message.get);
			mongoTemplate.insert(ef);
		} catch (Exception e) {
			// TODO: handle exception
		}

		logger.info(subject);
	}
}
