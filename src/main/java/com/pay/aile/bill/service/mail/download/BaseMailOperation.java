package com.pay.aile.bill.service.mail.download;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

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
    private static final Logger logger = LoggerFactory.getLogger(BaseMailOperation.class);

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    public void downloadMail(String mailAddr, String password) throws MailBillException {
        Store store = MailLoginUtil.login(getMailProperties(), mailAddr, password);
        Folder defaultFolder = null;
        Folder[] folderArr = null;
        try {
            defaultFolder = store.getDefaultFolder();
            folderArr = defaultFolder.list();
            for (Folder tempFolder : folderArr) {
                Folder folder = store.getFolder(tempFolder.getName());
                folder.open(Folder.READ_ONLY);
                long startTime = System.currentTimeMillis();
                Message[] messages = MailSearchUtil.search(getKeywords(), folder);
                long endTime = System.currentTimeMillis();
                logger.debug("====搜索到{}封邮件，耗时{}ms", messages.length, endTime - startTime);
                for (int i = 0; i < messages.length; i++) {
                    Message tmpMessage = messages[i];
                    DownloadUtil.saveFile(tmpMessage);
                }
                folder.close(true);
            }
        } catch (MessagingException | MailBillException e) {
            logger.error("下载邮件异常");
            logger.error(e.getMessage(), e);
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
