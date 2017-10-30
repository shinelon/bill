package com.pay.aile.bill.service.mail.impl;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pay.aile.bill.exception.MailBillException;
import com.pay.aile.bill.service.mail.MailOperation;
import com.pay.aile.bill.utils.DownloadUtil;
import com.pay.aile.bill.utils.MailReleaseUtil;
import com.pay.aile.bill.utils.MailSearchUtil;

/***
 * MailOperationImpl.java
 *
 * @author shinelon
 *
 * @date 2017年10月30日
 *
 */

public class Mail126OperationImpl implements MailOperation {
    private static final Logger logger = LoggerFactory.getLogger(Mail126OperationImpl.class);

    @Override
    public void downloadMail(String mailAddr, String password) throws Exception {
        Store store = login(mailAddr, password);
        Folder defaultFolder = null;
        Folder[] folder_arr = null;
        try {
            defaultFolder = store.getDefaultFolder();
            folder_arr = defaultFolder.list();
            for (Folder tempFolder : folder_arr) {
                Folder folder = store.getFolder(tempFolder.getName());
                folder.open(Folder.READ_ONLY);
                Message[] messages = MailSearchUtil.search(getKeywords(), folder);
                for (int i = 0; i < messages.length; i++) {
                    DownloadUtil.saveFile(messages[i], i);
                }
                folder.close(true);
            }

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (MailBillException e) {
            e.printStackTrace();
        } finally {
            MailReleaseUtil.releaseFolderAndStore(defaultFolder, store);
        }
    }

    /***
     * 获取搜索邮件关键字
     * 
     * @return
     */
    private String getKeywords() {
        return "中信银行";
    }

    /***
     * 自定义登录页面 注意使用后是否store
     *
     * @param mailAddrWithoutSuffix
     * @param password
     * @return
     * @throws MailBillException
     */
    private Store login(final String mailAddrWithoutSuffix, final String password) throws MailBillException {
        logger.info("begin landing user mail:{}", mailAddrWithoutSuffix);
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "pop3"); // 协议
        props.setProperty("mail.pop3.port", "110"); // 端口
        props.setProperty("mail.pop3.host", "pop.126.com");// 服务器地址
        // 创建Session实例对象
        Session session = Session.getInstance(props);
        // 创建pop3协议的Store对象
        try {
            Store store = session.getStore("pop3");
            store.connect(mailAddrWithoutSuffix, password);
            boolean connected = store.isConnected();
            logger.info("mail:{} \tis connected:{}", mailAddrWithoutSuffix, connected);
            if (!connected) {
                throw new MailBillException("");
            }
            return store;
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
            throw new MailBillException("");
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new MailBillException("");
        }
    }

}
