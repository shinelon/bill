package com.pay.aile.bill.service.mail.analyze.impl;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pay.aile.bill.BillApplication;
import com.pay.aile.bill.service.mail.analyze.BankMailAnalyzer;
import com.pay.aile.bill.service.mail.analyze.exception.AnalyzeBillException;
import com.pay.aile.bill.service.mail.analyze.model.AnalyzeParamsModel;
import com.pay.aile.bill.service.mail.analyze.util.TextExtractUtil;
import com.pay.aile.bill.utils.MailSendUtil;
import com.pay.aile.bill.utils.MongoDownloadUtil;

/**
 *
 * @author zhibin.cui
 * @description 民生银行解析模版测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BillApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CMBCAnlyzerTest {

    @Resource(name = "CMBCAnalyzer")
    private BankMailAnalyzer CMBCAnalyzer;
    @Autowired
    private MongoDownloadUtil downloadUtil;
    @Autowired
    private MailSendUtil mailSendUtil;

    @Test
    public void test() throws AnalyzeBillException {
        String content = "";
        try {
            content = downloadUtil.getFile("INBOX|1tbiThWLWFhgzTOQiAAAsW");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // mailSendUtil.sendUtil(content, "邮件解析异常", "czb18518679659@126.com");

        content = TextExtractUtil.parseHtml(content, "font");
        System.out.println(content);
        AnalyzeParamsModel amp = new AnalyzeParamsModel();
        amp.setContent(content);
        amp.setOriginContent(content);
        amp.setBankCode("CMBC");
        amp.setBankId("1");
        amp.setEmail("czb18518679659@126.com");
        CMBCAnalyzer.analyze(amp);

    }

}
