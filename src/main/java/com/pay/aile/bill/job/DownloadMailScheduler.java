package com.pay.aile.bill.job;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.pay.aile.bill.entity.CreditEmail;
import com.pay.aile.bill.service.CreditEmailService;
import com.pay.aile.bill.service.mail.download.DownloadMail;

/***
 * DownloadMailScheduler.java
 *
 * @author shinelon
 *
 * @date 2017年11月8日
 *
 */
@Service
public class DownloadMailScheduler {
    private static final Logger logger = LoggerFactory.getLogger(DownloadMailScheduler.class);
    @Autowired
    private CreditEmailService creditEmailService;
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;
    @Autowired
    private DownloadMail downloadMail;

    public void downLoadMail() {
        long startTime = System.currentTimeMillis();
        List<CreditEmail> list = creditEmailService.getCreditEmails();
        logger.debug("DownloadMailScheduler list:{}", list);
        CountDownLatch doneSignal = new CountDownLatch(list.size());
        for (CreditEmail creditEmail : list) {
            taskExecutor.execute(() -> {
                try {
                    downloadMail.execute(creditEmail);
                } catch (Exception e) {
                    logger.warn("download exception:{}", creditEmail);
                } finally {
                    doneSignal.countDown();
                }
            });
        }
        try {
            doneSignal.await();
            long endTime = System.currentTimeMillis();
            logger.debug("DownloadMailScheduler done:{}ms", endTime - startTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
