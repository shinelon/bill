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
import com.pay.aile.bill.service.mail.analyze.exception.AnalyzeBillException;
import com.pay.aile.bill.service.mail.analyze.model.AnalyzeParamsModel;
import com.pay.aile.bill.service.mail.analyze.util.TextExtractUtil;
import com.pay.aile.bill.utils.MongoDownloadUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BillApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PABAnalyzerTest {

    @Resource(name = "PABAnalyzer")
    private BankMailAnalyzer PABAnalyzer;
    @Autowired
    private MongoDownloadUtil downloadUtil;

    @Autowired
    CreditTemplateMapper creditTemplateMapper;

    @Test
    public void test() throws AnalyzeBillException {
        String content = "";
        try {
            // content = downloadUtil.getFile("平安一账通卡电子账单");
            content = downloadUtil.getFile("INBOX|1tbi0A6RWFUJZO8elwABsI");
        } catch (MailBillException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        content = TextExtractUtil.parseHtml(content, "font");
        System.out.println(content);
        AnalyzeParamsModel amp = new AnalyzeParamsModel();
        amp.setOriginContent(content);
        amp.setBankCode("PAB");
        amp.setBankId("1");
        amp.setEmail("123@qq.com");
        amp.setEmailId(1L);
        PABAnalyzer.analyze(amp);
    }

}
