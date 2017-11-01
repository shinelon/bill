package com.pay.aile.bill.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pay.aile.bill.BillApplication;

/***
 * TestMapperTest.java
 *
 * @author shinelon
 *
 * @date 2017年11月1日
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BillApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestMapperTest {

    private static final Logger logger = LoggerFactory.getLogger(TestMapperTest.class);
    @Autowired
    private TestMapper testMapper;

    @Test
    public void testSelect() {
        List<com.pay.aile.bill.entity.Test> list = testMapper.selectList(null);
        logger.info(list.toString());
    }

}
