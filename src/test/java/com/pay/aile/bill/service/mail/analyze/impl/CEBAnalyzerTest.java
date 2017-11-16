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
import com.pay.aile.bill.service.mail.analyze.exception.AnalyzeBillException;
import com.pay.aile.bill.service.mail.analyze.model.AnalyzeParamsModel;
import com.pay.aile.bill.service.mail.analyze.util.TextExtractUtil;
import com.pay.aile.bill.utils.MongoDownloadUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BillApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CEBAnalyzerTest {

    @Resource
    private BankMailAnalyzer CEBAnalyzer;
    @Autowired
    private MongoDownloadUtil downloadUtil;

    @Test
    public void test() throws AnalyzeBillException {

        String content = "";
        try {
            content = downloadUtil.getFile("5a9dcbbd-7926-4167-b973-771439acf52b");
        } catch (MailBillException e) {
            e.printStackTrace();
        }
        content = TextExtractUtil.parseHtml(content, "td");
        System.out.println(content);
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
