package com.pay.aile.bill.mongo;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pay.aile.bill.BillApplication;
import com.pay.aile.bill.entity.CreditEmail;
import com.pay.aile.bill.utils.MongoDownloadUtil;

/***
 * EmlToMongoUtil.java
 *
 * @author shinelon
 *
 * @date 2017年11月7日
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BillApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmlToMongoUtilTest {
    private static final Logger logger = LoggerFactory
            .getLogger(EmlToMongoUtilTest.class);
    @Autowired
    private MongoDownloadUtil MongoDownloadUtil;

    @Test
    public void emlToMongo() throws Exception {
        logger.info("emlToMongo");

        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        InputStream inMsg = new FileInputStream(
                "D:\\银行账单eml文件\\邮件eml\\兴业银行信用卡10月电子账单-.eml");
        Message msg = new MimeMessage(session, inMsg);
        CreditEmail creditEmail = new CreditEmail();
        creditEmail.setEmail("123@qq.com");
        creditEmail.setId(1L);
        MongoDownloadUtil.saveFile(msg, creditEmail);
    }
}
