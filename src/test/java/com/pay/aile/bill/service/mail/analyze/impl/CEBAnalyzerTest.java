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
public class CEBAnalyzerTest {

	@Resource
	private BankMailAnalyzer CEBAnalyzer;
	@Autowired
	private MongoDownloadUtil downloadUtil;

	@Test
	public void test() {

		String content = "";
		try {
			content = downloadUtil.getFile("7a35c36-c5b1-4f86-8212-5bed4e5eb9f0");
		} catch (MailBillException e) {
			e.printStackTrace();
		}

		content = TextExtractUtil.parseHtml(content, "font");
		System.out.println(content);
		AnalyzeParamsModel amp = new AnalyzeParamsModel();
		amp.setContent(content);
		amp.setBankCode("PSBC");
		// amp.setCardtypeId(2l);
		amp.setEmail("czb18518679659@126.com");
		// amp.setEmailId(6L);

		CEBAnalyzer.analyze(amp);
	}

}
