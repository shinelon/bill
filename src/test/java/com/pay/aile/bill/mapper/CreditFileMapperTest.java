package com.pay.aile.bill.mapper;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pay.aile.bill.BillApplication;
import com.pay.aile.bill.service.mail.analyze.model.CreditFileModel;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BillApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CreditFileMapperTest {
    @Autowired
    private CreditFileMapper creditFileMapper;

    // @Test
    public void testSelectUnAnalyzedList() {
        List<CreditFileModel> list = creditFileMapper.selectUnAnalyzedList();
        Assert.assertNotNull(list);
        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void testSelectUnAnalyzedListByEmail() {
        List<CreditFileModel> list = creditFileMapper.selectUnAnalyzedListByEmail("jinjing_0316@outlook.com");
        Assert.assertNotNull(list);
        Assert.assertFalse(list.isEmpty());
    }

    // @Test
    public void testUpdateProcessResult() {
        creditFileMapper.updateProcessResult(1, 1L);
    }

}
