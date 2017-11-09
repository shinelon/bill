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
import com.pay.aile.bill.service.mail.analyze.util.TextExtractUtil;
import com.pay.aile.bill.utils.MongoDownloadUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BillApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ABCAnalyzerTest {

    @Resource(name = "ABCAnalyzer")
    private BankMailAnalyzer ABCAnalyzer;
    @Autowired
    private MongoDownloadUtil downloadUtil;

    @Autowired
    CreditTemplateMapper creditTemplateMapper;

    @Test
    public void test() {
        String content = "";
        try {
            content = downloadUtil.getFile("中国农业银行金穗信用卡电子对账单");
        } catch (MailBillException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        content = TextExtractUtil.parseHtml(content, "td");
        System.out.println(content);
        AnalyzeParamsModel amp = new AnalyzeParamsModel();
        amp.setContent(content);
        amp.setBankCode("ABC");
        amp.setEmail("jinjing_0316@outlook.com");
        amp.setEmailId(1L);
        ABCAnalyzer.analyze(amp);
    }

}
