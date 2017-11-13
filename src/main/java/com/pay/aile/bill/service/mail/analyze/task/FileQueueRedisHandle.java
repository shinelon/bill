package com.pay.aile.bill.service.mail.analyze.task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pay.aile.bill.entity.CreditFile;
import com.pay.aile.bill.service.CreditFileService;
import com.pay.aile.bill.service.mail.analyze.model.CreditFileModel;

/**
 *
 * @ClassName: FileQueueRedisHandle
 * @Description: 邮件附件的队列
 * @author jinjing
 * @date 2017年11月12日
 *
 */
@Component
public class FileQueueRedisHandle {

    private static final Logger logger = LoggerFactory.getLogger(FileQueueRedisHandle.class);
    private static final long ttlInSeconds = 300;
    public static final String MAIL_FILE_LIST = "aile-mail-file-list";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private CreditFileService creditFileService;

    /***
     * 添加任务，将任务放在队头
     *
     * @param creditEmail
     */
    public void addFile(CreditFileModel creditFile) {

        redisTemplate.opsForList().leftPush(MAIL_FILE_LIST, JSON.toJSONString(creditFile));
    }

    /**
     *
     * @Title: bathPushFile
     * @Description:；批量保存
     * @param creditFileList
     * @return void 返回类型 @throws
     */
    public void bathPushFile(List<CreditFile> creditFileList) {
        List<String> values = new ArrayList<String>();
        for (CreditFile file : creditFileList) {
            values.add(JSONObject.toJSONString(file));
        }
        // 批量保存
        redisTemplate.opsForList().rightPushAll(MAIL_FILE_LIST, values);

    }

    /***
     * 从头部获取任务执行内容
     *
     * @return
     */
    public CreditFileModel getFile() {
        String fileJson = redisTemplate.opsForList().leftPop(MAIL_FILE_LIST);
        if (StringUtils.isEmpty(fileJson)) {
            return null;
        }

        return JSON.parseObject(fileJson, CreditFileModel.class);
    }

    /***
     * 获取redis锁，默认时间ttlInSeconds
     *
     * @param key
     * @return
     */
    public boolean getLock(String key) {
        return incrementAndGet(key, ttlInSeconds) <= 1;
    }

    public int incrementAndGet(String key, long ttlInSeconds) {
        RedisAtomicInteger redisAtomicInteger = new RedisAtomicInteger(key,
                redisTemplate.getConnectionFactory());
        if (redisAtomicInteger.getExpire() < 0) {
            redisAtomicInteger.expire(ttlInSeconds, TimeUnit.SECONDS);
        }
        logger.debug("key:{} expire:{}", key, redisAtomicInteger.getExpire());
        return redisAtomicInteger.incrementAndGet();
    }

    /***
     * 初始化任务列表，程序启动自动调用
     */
    public void initFileList() {
        // 清空队列
        redisTemplate.delete(MAIL_FILE_LIST);

        List<CreditFileModel> creditFileList = creditFileService.findUnAnalyzedList();
        List<String> values = new ArrayList<String>();
        for (CreditFileModel file : creditFileList) {
            values.add(JSONObject.toJSONString(file));
        }
        // 批量保存
        redisTemplate.opsForList().rightPushAll(MAIL_FILE_LIST, values);
    }

    /***
     * 执行任务完成,将任务放在队尾
     *
     * @return
     */
    public void pushFile(CreditFileModel creditFile) {

        redisTemplate.opsForList().rightPush(MAIL_FILE_LIST, JSON.toJSONString(creditFile));
    }

    /***
     * 删除任务
     *
     * @param id
     */
    public void removeFile(String id) {
        // redisTemplate.delete(MAIL_DOWANLOD_JOB_CONTENT.concat(id));
    }

    /***
     * 解锁
     *
     * @param key
     */
    public void unLock(String key) {
        redisTemplate.delete(key);
    }

}
