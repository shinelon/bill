package com.pay.aile.bill.job;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.pay.aile.bill.entity.CreditEmail;
import com.pay.aile.bill.entity.JobMsg;
import com.pay.aile.bill.service.CreditEmailService;

/***
 * RedisLock.java
 *
 * @author shinelon
 *
 * @date 2017年11月9日
 *
 */

@Component
public class RedisJobSettings {

    private static final Logger logger = LoggerFactory.getLogger(RedisJobSettings.class);
    private static final long ttlInSeconds = 300;
    public static final String MAIL_DOWANLOD_LIST_NAME = "aile-mail-job";

    @Autowired
    private CreditEmailService creditEmailService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void delLock(String key) {
        redisTemplate.delete(key);
    }

    /***
     * 获取任务执行内容
     *
     * @return
     */
    public CreditEmail getJob() {
        String jsonString = redisTemplate.opsForList().rightPopAndLeftPush(MAIL_DOWANLOD_LIST_NAME,
                MAIL_DOWANLOD_LIST_NAME);
        return JSON.parseObject(jsonString, CreditEmail.class);
    }

    /**
     * @param creditEmail
     * @return
     */
    public JobMsg getJobMsg(CreditEmail creditEmail) {
        return null;
    }

    public boolean getLock(String key) {
        return incrementAndGet(key, ttlInSeconds) <= 1;
    }

    public int incrementAndGet(String key, long ttlInSeconds) {
        RedisAtomicInteger redisAtomicInteger = new RedisAtomicInteger(key, redisTemplate.getConnectionFactory());
        if (redisAtomicInteger.getExpire() < 0) {
            redisAtomicInteger.expire(ttlInSeconds, TimeUnit.SECONDS);
        }
        logger.debug("key:{} expire:{}", key, redisAtomicInteger.getExpire());
        return redisAtomicInteger.incrementAndGet();
    }

    public void initJobList() {
        List<CreditEmail> list = creditEmailService.getCreditEmails();
        List<String> listJsonString = list.stream().map(e -> JSON.toJSONString(e)).collect(Collectors.toList());
        logger.debug("key:{},value:{}", MAIL_DOWANLOD_LIST_NAME, listJsonString);
        redisTemplate.opsForList().leftPushAll(MAIL_DOWANLOD_LIST_NAME, listJsonString);
        redisTemplate.opsForList().rightPopAndLeftPush(MAIL_DOWANLOD_LIST_NAME, MAIL_DOWANLOD_LIST_NAME);
    }

    public void saveJobMsg() {
    }
}
