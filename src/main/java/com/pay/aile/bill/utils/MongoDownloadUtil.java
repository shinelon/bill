package com.pay.aile.bill.utils;

import java.io.InputStream;

import javax.mail.Message;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.mongodb.DB;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
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
		DB db = mongoTemplate.getDb();
		// 存储fs的根节点
		GridFS gridFS = new GridFS(db);
		// InputStream in = new
		// ByteArrayInputStream(content.toString().getBytes());
		// gridFS.createFile(in, formatFileName(subject, sentDate));
		try {
			GridFSDBFile dbfile = gridFS.findOne(fileName);
			if (dbfile != null) {
				InputStream input = dbfile.getInputStream();

				StringBuffer out = new StringBuffer();

				byte[] b = new byte[4096];
				for (int n; (n = input.read(b)) != -1;) {
					out.append(new String(b, 0, n));
				}
				return out.toString();
			}
		} catch (Exception e) {

			logger.error(e.getMessage());
			throw new MailBillException(e.getMessage());
		}
		return null;

	}

	public void saveFile(Message message) throws MailBillException {

		MimeMessage msg = (MimeMessage) message;
		String subject = MailDecodeUtil.getSubject(msg);
		String receiveAdd = MailDecodeUtil.getReceiveAddress(msg, null);
		String senderAdd = MailDecodeUtil.getFrom(msg);
		String sentDate = MailDecodeUtil.getSentDate(msg, "yyyyMMddHHmmss");
		logger.debug("subject:{} receiveAdd:{} senderAdd:{} sentData:{}", subject, receiveAdd, senderAdd, sentDate);
		StringBuffer content = new StringBuffer(20480);
		MailDecodeUtil.getMailTextContent(msg, content);
		content = MailDecodeUtil.getUtf8(content);

		DB db = mongoTemplate.getDb();
		// 存储fs的根节点
		GridFS gridFS = new GridFS(db);
		// InputStream in = new
		// ByteArrayInputStream(content.toString().getBytes());
		// gridFS.createFile(in, formatFileName(subject, sentDate));
		GridFSInputFile gfs = gridFS.createFile(content.toString().getBytes());
		gfs.put("fileType", "html");
		gfs.put("filename", subject);

		gfs.save();

		logger.info(subject);
	}
}
