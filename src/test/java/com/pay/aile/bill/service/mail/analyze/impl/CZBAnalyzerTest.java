package com.pay.aile.bill.service.mail.analyze.impl;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pay.aile.bill.BillApplication;
import com.pay.aile.bill.exception.MailBillException;
import com.pay.aile.bill.mapper.CreditTemplateMapper;
import com.pay.aile.bill.service.mail.analyze.BankMailAnalyzer;
import com.pay.aile.bill.service.mail.analyze.model.AnalyzeParamsModel;
import com.pay.aile.bill.utils.MongoDownloadUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BillApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CZBAnalyzerTest {

    @Resource(name = "CZBAnalyzer")
    private BankMailAnalyzer CZBAnalyzer;
    @Autowired
    private MongoDownloadUtil downloadUtil;

    @Autowired
    CreditTemplateMapper creditTemplateMapper;

    @Test
    public void test() {
        String content = "";
        try {
            content = downloadUtil.getFile("浙商银行信用卡电子账单");
        } catch (MailBillException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        AnalyzeParamsModel amp = new AnalyzeParamsModel();
        amp.setOriginContent(content);
        amp.setBankCode("CZB");
        amp.setBankId("1");
        amp.setEmail("123@qq.com");
        amp.setEmailId(1L);
        CZBAnalyzer.analyze(amp);
    }

}
