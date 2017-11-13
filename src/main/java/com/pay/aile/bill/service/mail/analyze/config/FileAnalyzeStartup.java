package com.pay.aile.bill.service.mail.analyze.config;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.pay.aile.bill.service.mail.analyze.task.FileAnalyzeScheduler;
import com.pay.aile.bill.service.mail.analyze.task.FileQueueRedisHandle;

/**
 *
 * @ClassName: FileAnalyzeStartup
 * @Description: 文件解析任务启动类
 * @author jinjing
 * @date 2017年11月9日
 *
 */
@Component
public class FileAnalyzeStartup implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(FileAnalyzeStartup.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            logger.info("do FileAnalyzeStartup init");

            FileAnalyzeScheduler fileAnalyzeScheduler = event.getApplicationContext()
                    .getBean(FileAnalyzeScheduler.class);
            // 初始化文件列表
            FileQueueRedisHandle fileQueueRedisHandle = event.getApplicationContext()
                    .getBean(FileQueueRedisHandle.class);
            fileQueueRedisHandle.initFileList();
            ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
                    new BasicThreadFactory.Builder().namingPattern("loop-file-pool-%d").daemon(true)
                            .build());

            executorService.execute(() -> {
                fileAnalyzeScheduler.analyzeLoop();
            });

        }
    }

}
