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
public class ICBCAnalyzerTest {

    @Resource(name = "ICBCAnalyzer")
    private BankMailAnalyzer ICBCAnalyzer;
    @Autowired
    private MongoDownloadUtil downloadUtil;

    @Test
    public void test() throws AnalyzeBillException {
        String content = "";
        try {
            // content = downloadUtil.getFile("中国工商银行客户对账单");
            content = downloadUtil.getFile("0d6eebe8-c43f-461b-bd42-6610c1b84230");
        } catch (MailBillException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        content = TextExtractUtil.parseHtml(content, "font");
        System.out.println(content);

        AnalyzeParamsModel amp = new AnalyzeParamsModel();
        amp.setOriginContent(content);
        amp.setBankCode("ICBC");
        amp.setBankId("1");
        amp.setEmail("123@qq.com");
        amp.setEmailId(1L);
        ICBCAnalyzer.analyze(amp);
    }

}
