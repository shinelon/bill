package com.pay.aile.bill.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.pay.aile.bill.BillApplication;
import com.pay.aile.bill.entity.SendMail;
import com.pay.aile.bill.service.mail.analyze.constant.Constant;
import com.pay.aile.bill.service.mail.analyze.util.JedisClusterUtils;

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

    @Test
    public void getBean() {
        try {
            // SendMail sendMail =
            // JedisClusterUtils.getBean(Constant.redisSendMail +
            // "czb18518679659@126.com", SendMail.class);

            String map = JedisClusterUtils.hashGet(Constant.redisSendMail, "SendMail", String.class);

            List<SendMail> studentList1 = JSON.parseArray(JSON.parseObject(map).getString("sendMail"), SendMail.class);
            for (SendMail sendMail : studentList1) {
                System.out.println(sendMail.getAddresser());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void hashset() {
        try {

            Map<String, Object> map = new HashMap<String, Object>();

            List<SendMail> list = new ArrayList<SendMail>();
            SendMail sendMail = new SendMail();
            sendMail.setRecipients("czb18518679659@126.com");
            sendMail.setAddresser("18518679659@139.com");
            sendMail.setPasword("12345qwert");
            sendMail.setHost("smtp.139.com");
            sendMail.setPort("25");
            list.add(sendMail);

            SendMail sendMail2 = new SendMail();
            sendMail2.setRecipients("18518679659@139.com");
            sendMail2.setAddresser("18518679659@139.com");
            sendMail2.setPasword("12345qwert");
            sendMail2.setHost("smtp.139.com");
            sendMail2.setPort("25");
            list.add(sendMail2);

            map.put("sendMail", list);

            JedisClusterUtils.hashSet(Constant.redisSendMail, "SendMail", JSON.toJSONString(map));
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // @Test
    public void testInitJobList() {
        redisJobSettings.initJobList();
    }

    // @Test
    public void unLockTest() {
        redisJobSettings.unLock(redisJobSettings.MAIL_DOWANLOD_LIST_NAME);
    }

}
