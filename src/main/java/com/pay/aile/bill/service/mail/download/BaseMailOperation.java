package com.pay.aile.bill.service.mail.download;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;

import com.pay.aile.bill.contant.BankKeywordContants;
import com.pay.aile.bill.exception.MailBillException;
import com.pay.aile.bill.utils.DownloadUtil;
import com.pay.aile.bill.utils.MailLoginUtil;
import com.pay.aile.bill.utils.MailReleaseUtil;
import com.pay.aile.bill.utils.MailSearchUtil;

/***
 * MailOperation.java
 *
 * @author shinelon
 *
 * @date 2017年10月30日
 *
 */
public abstract class BaseMailOperation {
    /***
     * 下载邮件
     *
     * 注意126、163、和qq邮箱使用POP3授权码
     *
     * @param mailAddr
     * @param password
     * @throws Exception
     */
    public void downloadMail(String mailAddr, String password) throws Exception {
        Store store = MailLoginUtil.login(getMailProperties(), mailAddr, password);
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
                    DownloadUtil.saveFile(messages[i]);
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

    public abstract Properties getMailProperties();

    /***
     * 使用关键字搜索邮件，可以定制Override
     *
     * @return
     */
    protected String getKeywords() {

        return BankKeywordContants.ALL_BANK_KEYWORDS;
    }

}
