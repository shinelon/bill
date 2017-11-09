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
import com.pay.aile.bill.service.mail.analyze.util.TextExtractUtil;
import com.pay.aile.bill.utils.MongoDownloadUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BillApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CITICAnalyzerTest {

    @Resource(name = "CITICAnalyzer")
    private BankMailAnalyzer CITICAnalyzer;
    @Autowired
    private MongoDownloadUtil downloadUtil;

    @Test
    public void test() {
        String content = "";
        try {
            content = downloadUtil.getFile(
                    "CITIC_中信银行信用卡电子账单2017年10月_2017-11-02T10:54:34.html");
        } catch (MailBillException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        content = TextExtractUtil.parseHtml(content, "td");
        System.out.println(content);
        AnalyzeParamsModel amp = new AnalyzeParamsModel();
        amp.setContent(content);
        amp.setBankCode("CITIC");
        amp.setEmail("123@qq.com");
        amp.setEmailId(1L);
        CITICAnalyzer.analyze(amp);
    }

}
