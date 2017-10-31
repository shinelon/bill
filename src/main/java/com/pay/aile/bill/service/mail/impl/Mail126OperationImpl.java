package com.pay.aile.bill.service.mail.impl;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pay.aile.bill.exception.MailBillException;
import com.pay.aile.bill.service.mail.MailOperation;
import com.pay.aile.bill.utils.DownloadUtil;
import com.pay.aile.bill.utils.MailLoginUtil;
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
@Service
public class Mail126OperationImpl implements MailOperation {
    private static final Logger logger = LoggerFactory.getLogger(Mail126OperationImpl.class);

    @Override
    public void downloadMail(String mailAddr, String password) throws Exception {
        Store store = MailLoginUtil.login("pop.126.com", mailAddr, password);
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

}
