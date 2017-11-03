package com.pay.aile.bill.mapper;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pay.aile.bill.BillApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BillApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CreditFileMapperTest {
    @Autowired
    private CreditFileMapper creditFileMapper;

    @Test
    public void testUpdateProcessResult() {
        creditFileMapper.updateProcessResult(1, 1L);
    }

    @Test
    public void testSelectUnAnalyzedList() {
        List<Map<String, Object>> list = creditFileMapper
                .selectUnAnalyzedList();
        Assert.assertNotNull(list);
        Assert.assertFalse(list.isEmpty());
    }
}
