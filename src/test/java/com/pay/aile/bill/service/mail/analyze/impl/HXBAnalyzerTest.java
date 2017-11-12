package com.pay.aile.bill.service.mail.analyze.impl;

import java.util.Date;

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
public class HXBAnalyzerTest {

    @Autowired
    private MongoDownloadUtil downloadUtil;
    @Resource(name = "HBXAnalyzer")
    private BankMailAnalyzer HBXAnalyzer;

    @Autowired
    CreditTemplateMapper creditTemplateMapper;

    @Test
    public void test() {
        String content = "";
        try {
            content = downloadUtil.getFile("ae2012f6-98d2-4513-bceb-cbd8dd4664b0");
        } catch (MailBillException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // CreditTemplate rules = creditTemplateMapper.selectById(3);
        content = TextExtractUtil.parseHtml(content, "td");

        //
        // System.out.println(rules.getDueDate().equals(ruleValue));
        // List<String> list = PatternMatcherUtil.getMatcher(ruleValue,
        // content);
        //
        // System.out.print(list.size());
        System.out.println(content);
        AnalyzeParamsModel amp = new AnalyzeParamsModel();
        amp.setContent(content);
        amp.setBankCode("HXB");
        amp.setCardtypeId(2l);

        amp.setEmail("jinjing_0316@outlook.com");
        amp.setEmailId(6L);
        amp.setSentDate(new Date());
        amp.setBankId("26");
        HBXAnalyzer.analyze(amp);
    }

}
