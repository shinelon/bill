package com.pay.aile.bill.mapper;

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
    public void updateProcessResult() {
        creditFileMapper.updateProcessResult(1, 1L);
    }

}
