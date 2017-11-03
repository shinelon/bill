package com.pay.aile.bill.mail.relation;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pay.aile.bill.BillApplication;
import com.pay.aile.bill.entity.CreditFile;
import com.pay.aile.bill.service.mail.relation.CreditFileRelation;

/***
 * CreditFileRelationTest.java
 *
 * @author shinelon
 *
 * @date 2017年11月3日
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BillApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CreditFileRelationTest {

    private static final Logger logger = LoggerFactory.getLogger(CreditFileRelationTest.class);
    @Autowired
    private CreditFileRelation creditFileRelation;

    @Test
    public void testSave() {
        CreditFile creditFile = new CreditFile();
        creditFile.setEmailId(1L);
        creditFile.setFilenName("testEmailName");
        creditFileRelation.saveCreditFile(creditFile);
    }

    @Test
    public void testSelect() {
        List<CreditFile> list = creditFileRelation.selectCreditFiles("jinjing_0316@126.com");
        logger.info("list:{}", list);
    }

    @Test
    public void testUpdate() {
        CreditFile creditFile = new CreditFile();
        creditFile.setId(1L);
        creditFile.setFilenName("testupdate");
        creditFileRelation.updateCreditFile(creditFile);
    }
}
