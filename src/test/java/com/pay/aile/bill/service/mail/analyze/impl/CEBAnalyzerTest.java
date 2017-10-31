package com.pay.aile.bill.service.mail.analyze.impl;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pay.aile.bill.BillApplication;
import com.pay.aile.bill.service.mail.analyze.BankMailAnalyzer;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BillApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CEBAnalyzerTest {

    @Resource
    private BankMailAnalyzer CEBAnalyzer;

    @Test
    public void test() {
        CEBAnalyzer.analyze(null);
    }

}
