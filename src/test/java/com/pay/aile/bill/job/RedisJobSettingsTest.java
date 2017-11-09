package com.pay.aile.bill.job;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pay.aile.bill.BillApplication;

/***
 * RedisJobSettingsTest.java
 *
 * @author shinelon
 *
 * @date 2017年11月9日
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BillApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RedisJobSettingsTest {
    private static final Logger logger = LoggerFactory.getLogger(RedisJobSettingsTest.class);
    @Autowired
    private RedisJobHandle redisJobSettings;

    // @Test
    public void testInitJobList() {
        redisJobSettings.initJobList();
    }

    // @Test
    public void unLockTest() {
        redisJobSettings.unLock(redisJobSettings.MAIL_DOWANLOD_LIST_NAME);
    }
}
