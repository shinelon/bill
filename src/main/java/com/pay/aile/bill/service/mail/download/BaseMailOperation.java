package com.pay.aile.bill.service.mail.download;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.pay.aile.bill.contant.BankKeywordContants;
import com.pay.aile.bill.entity.CreditEmail;
import com.pay.aile.bill.exception.MailBillException;
import com.pay.aile.bill.service.CreditBankService;
import com.pay.aile.bill.utils.MailLoginUtil;
import com.pay.aile.bill.utils.MailReleaseUtil;
import com.pay.aile.bill.utils.MailSearchUtil;
import com.pay.aile.bill.utils.MongoDownloadUtil;

/***
 * MailOperation.java
 *
 * @author shinelon
 *
 * @date 2017年10月30日
 *
 */
public abstract class BaseMailOperation {

    private static final Logger logger = LoggerFactory.getLogger(BaseMailOperation.class);
    public static final String STROE_POP3 = "pop3";
    public static final String STROE_IMAP = "imap";
    @Autowired
    private MongoDownloadUtil downloadUtil;
    @Autowired
    private CreditBankService creditBankService;

    /***
     * 下载邮件
     *
     * 注意126、163、和qq邮箱使用POP3授权码
     *
     * @param mailAddr
     * @param password
     * @throws Exception
     */
    public void downloadMail(CreditEmail creditEmail) throws MailBillException {
        Store store = MailLoginUtil.login(getMailProperties(), getStoreType(), creditEmail.getEmail(),
                creditEmail.getPassword());
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
                    downloadUtil.saveFile(tmpMessage, creditEmail);
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

    public void downloadMail(String mailAddr, String password) throws MailBillException {
        CreditEmail creditEmail = new CreditEmail(mailAddr, password);
        this.downloadMail(creditEmail);
    }

    public abstract Properties getMailProperties();

    /***
     * 使用关键字搜索邮件，可以定制Override
     *
     * @return
     */
    protected String getKeywords() {
        if (StringUtils.isEmpty(BankKeywordContants.ALL_BANK_KEYWORDS)) {
            creditBankService.initKeywords();
        }
        return BankKeywordContants.ALL_BANK_KEYWORDS;
    }

    /***
     * 使用POP3 若使用imap需要子类复写
     *
     * @return
     */
    protected String getStoreType() {
        return BaseMailOperation.STROE_POP3;
    }

}
