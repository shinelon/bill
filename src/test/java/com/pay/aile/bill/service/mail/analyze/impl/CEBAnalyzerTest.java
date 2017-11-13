package com.pay.aile.bill.service.mail.analyze.impl;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pay.aile.bill.BillApplication;
import com.pay.aile.bill.exception.MailBillException;
import com.pay.aile.bill.service.mail.analyze.BankMailAnalyzer;
import com.pay.aile.bill.service.mail.analyze.model.AnalyzeParamsModel;
import com.pay.aile.bill.utils.MongoDownloadUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BillApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CEBAnalyzerTest {

    @Resource
    private BankMailAnalyzer CEBAnalyzer;
    @Autowired
    private MongoDownloadUtil downloadUtil;

    @Test
    public void test() {

        String content = "";
        try {
            content = downloadUtil.getFile("24232c86-f320-4565-a6f3-8dfd2eb70415");
        } catch (MailBillException e) {
            e.printStackTrace();
        }
        AnalyzeParamsModel amp = new AnalyzeParamsModel();
        amp.setOriginContent(content);
        amp.setBankCode("CEB");
        amp.setBankId("1");
        amp.setCardtypeId(2l);
        amp.setEmail("123@QQ.com");
        amp.setEmailId(6L);

        CEBAnalyzer.analyze(amp);
    }

}
