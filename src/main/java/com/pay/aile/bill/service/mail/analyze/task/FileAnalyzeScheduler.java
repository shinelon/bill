package com.pay.aile.bill.service.mail.analyze.task;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.pay.aile.bill.service.mail.analyze.impl.ParseMailImpl;
import com.pay.aile.bill.service.mail.analyze.model.CreditFileModel;

/***
 * DownloadMailScheduler.java
 *
 * @author shinelon
 *
 * @date 2017年11月8日
 *
 */
@Service
public class FileAnalyzeScheduler {
    private static boolean flagJobLoop = true;
    private static final Logger logger = LoggerFactory.getLogger(FileAnalyzeScheduler.class);
    // 当邮箱扫描时间间隔大约loopIntervalSeconds 重新下载邮箱
    private static final long loopIntervalSeconds = 100L;

    // 当循环线程发现没有邮件需要下载的时候，等待waitSeconds
    private static final long waitSeconds = 100L;

    @Resource(name = "fileTaskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;
    @Resource(name = "parseMail")
    private ParseMailImpl parseMail;
    @Autowired
    private FileQueueRedisHandle fileQueueRedisHandle;

    public void analyzeLoop() {
        while (flagJobLoop) {

            taskExecutor.execute(() -> {
                try {
                    CreditFileModel file = fileQueueRedisHandle.getFile();
                    if (file != null) {
                        parseMail.executeParseFile(file);
                    }
                } catch (Exception e) {
                    logger.warn("download exception:{}");
                }
            });

        }

    }

    public void offJobLoop() {
        flagJobLoop = false;
    }
}
