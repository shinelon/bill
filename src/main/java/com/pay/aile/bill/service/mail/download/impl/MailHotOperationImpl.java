package com.pay.aile.bill.service.mail.download.impl;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pay.aile.bill.exception.MailBillException;
import com.pay.aile.bill.service.mail.download.BaseMailOperation;
import com.pay.aile.bill.utils.MailSearchUtil;
import com.pay.aile.bill.utils.MongoDownloadUtil;

/***
 * MailOperationImpl.java
 *
 * @author shinelon
 *
 * @date 2017年10月30日
 *
 */
@Service
public class MailHotOperationImpl extends BaseMailOperation {
	private static final Logger logger = LoggerFactory.getLogger(MailHotOperationImpl.class);
	@Autowired
	private MongoDownloadUtil downloadUtil;
	final String host = "imap-mail.outlook.com";
	final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

	@Override
	public void downloadMail(String mailAddr, String password) {
		Store store = login(getMailProperties(), mailAddr, password);
		Folder defaultFolder = null;
		Folder[] folderArr = null;
		try {
			if (store != null && store.isConnected()) {
				defaultFolder = store.getDefaultFolder();

				folderArr = defaultFolder.list();
				for (Folder tempFolder : folderArr) {
					Folder folder = store.getFolder(tempFolder.getName());
					folder.open(Folder.READ_ONLY);
					Message[] messages = MailSearchUtil.search(getKeywords(), folder);
					for (int i = 0; i < messages.length; i++) {
						downloadUtil.saveFile(messages[i]);
					}
					folder.close(true);
				}
			}

		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (MailBillException e) {
			e.printStackTrace();
		} finally {
			folderArr = null;
			try {
				if (defaultFolder.isOpen() && defaultFolder != null) {
					defaultFolder.close(false);
				}

				if (store != null) {
					store.close();
				}
			} catch (MessagingException e) {
				e.printStackTrace();
			}

			// MailReleaseUtil.releaseFolderAndStore(defaultFolder, store);
		}
	}

	/***
	 * 获取搜索邮件关键字
	 *
	 * @return
	 */

	@Override
	public Properties getMailProperties() {
		Properties props = new Properties();

		props.setProperty("mail.imap.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.imap.socketFactory.port", "993");
		props.setProperty("mail.store.protocol", "imap");
		props.setProperty("mail.imap.host", host);
		props.setProperty("mail.imap.port", "993");
		props.setProperty("mail.imap.auth.login.disable", "true");

		return props;
	}

	public Store login(Properties mailProps, final String mailAddrWithoutSuffix, final String password) {
		logger.info("begin landing user mail:{}", mailAddrWithoutSuffix);
		// 创建Session实例对象
		Session session = Session.getInstance(mailProps);
		// 创建pop3协议的Store对象
		try {
			Store store = session.getStore("imap");
			store.connect(mailAddrWithoutSuffix, password);
			boolean connected = store.isConnected();
			logger.info("mail:{} \tis connected:{}", mailAddrWithoutSuffix, connected);
			// if (!connected) {
			// throw new MailBillException("");
			// }
			return store;
		} catch (NoSuchProviderException e) {
			e.printStackTrace();

		} catch (MessagingException e) {
			e.printStackTrace();

		}
		return null;
	}
}
