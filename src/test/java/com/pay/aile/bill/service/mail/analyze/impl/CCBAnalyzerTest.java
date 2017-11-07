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
public class CCBAnalyzerTest {

	@Resource(name = "CCBAnalyzer")
	private BankMailAnalyzer CCBAnalyzer;
	@Autowired
	private MongoDownloadUtil downloadUtil;

	@Autowired
	CreditTemplateMapper creditTemplateMapper;

	@Test
	public void test() {
		String content = "";
		try {
			content = downloadUtil.getFile("f9d570f2-fe30-4f05-9d59-f96574fdde84");
		} catch (MailBillException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// CreditTemplate rules = creditTemplateMapper.selectById(3);
		content = TextExtractUtil.parseHtml(content, "font");
		String ruleValue = "CreditLimit CNY\\d+.?\\d";
		//
		// System.out.println(rules.getDueDate().equals(ruleValue));
		// List<String> list = PatternMatcherUtil.getMatcher(ruleValue,
		// content);
		//
		// System.out.print(list.size());
		System.out.println(content);
		AnalyzeParamsModel amp = new AnalyzeParamsModel();
		amp.setContent(content);
		amp.setBankCode("CCB");
		amp.setCardtypeId(2l);
		amp.setEmail("jinjing_0316@outlook.com");
		amp.setEmailId(6L);
		CCBAnalyzer.analyze(amp);
	}

}
