package com.pay.aile.bill.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.search.OrTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.SubjectTerm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.pay.aile.bill.contant.BankKeywordContants;
import com.pay.aile.bill.contant.ErrorCodeContants;
import com.pay.aile.bill.exception.MailBillException;

/***
 * MailSearchUtil.java
 *
 * @author shinelon
 *
 * @date 2017年10月30日
 *
 */
public class MailSearchUtil {
    private static final Logger logger = LoggerFactory.getLogger(MailSearchUtil.class);

    /***
     * 根据主题搜索关键字
     *
     * @param queryKey
     * @param folder
     * @return
     * @throws MailBillException
     */
    public static Message[] search(String queryKey, Folder folder) throws MailBillException {
        try {
            SearchTerm searchTerm = getSearchTerm(queryKey);
            return folder.search(searchTerm);
        } catch (MessagingException e) {
            throw MailBillExceptionUtil.getWithLog(e, ErrorCodeContants.SEARCH_FAILED_CODE,
                    ErrorCodeContants.SEARCH_FAILED.getMsg(), logger);
        }

    }

    /***
     * 根据主题搜索关键字-多线程搜索
     *
     * @问题 开启线程多，线程切换多反而慢
     *
     * @param queryKey
     * @param folder
     * @param taskExecutor
     * @return
     * @throws MailBillException
     */
    public static Message[] search(String queryKey, Folder folder, ThreadPoolTaskExecutor taskExecutor)
            throws MailBillException {
        try {
            Message[] messageAry = folder.getMessages();
            return mulitSearch(queryKey, messageAry, taskExecutor);
        } catch (MessagingException | InterruptedException e) {
            throw MailBillExceptionUtil.getWithLog(e, ErrorCodeContants.SEARCH_FAILED_CODE,
                    ErrorCodeContants.SEARCH_FAILED.getMsg(), logger);
        }
    }

    private static SearchTerm getSearchTerm(String queryKey) {
        if (-1 != queryKey.indexOf(BankKeywordContants.BANK_KEYWORD_SEPARATOR)) {
            String[] queryKeyAry = queryKey.split(BankKeywordContants.BANK_KEYWORD_SEPARATOR);
            SubjectTerm[] subjectTermAry = new SubjectTerm[queryKeyAry.length];
            for (int i = 0; i < queryKeyAry.length; i++) {
                subjectTermAry[i] = new SubjectTerm(queryKeyAry[i]);
            }
            OrTerm orTerm = new OrTerm(subjectTermAry);
            return orTerm;
        } else {
            SearchTerm subjectTerm = new SubjectTerm(queryKey);
            return subjectTerm;
        }
    }

    private static Message[] mulitSearch(String queryKey, Message[] messageAry, ThreadPoolTaskExecutor taskExecutor)
            throws InterruptedException {
        SearchTerm searchTerm = getSearchTerm(queryKey);
        List<Message> retMessageList = new ArrayList<>();
        CountDownLatch doneSignal = new CountDownLatch(messageAry.length);
        for (Message message : messageAry) {
            taskExecutor.execute(() -> {
                try {
                    if (message.match(searchTerm)) {
                        retMessageList.add(message);
                    }
                } catch (Exception e) {
                    logger.error("mulit search mail with keywords error!", e);
                }
                doneSignal.countDown();
            });
        }
        doneSignal.await();
        Message[] retMessageAry = new Message[retMessageList.size()];
        retMessageList.toArray(retMessageAry);
        return retMessageAry;
    }

}
