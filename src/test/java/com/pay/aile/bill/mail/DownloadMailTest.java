package com.pay.aile.bill.mail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pay.aile.bill.BillApplication;
import com.pay.aile.bill.service.mail.DownloadMail;

/***
 * DownloadMailTest.java
 *
 * @author shinelon
 *
 * @date 2017年10月30日
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BillApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DownloadMailTest {
    @Autowired
    public DownloadMail downloadMail;

    @Test
    public void test() {
        try {
            downloadMail.execute("jinjing_0316@126.com", "jinjing03161");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
