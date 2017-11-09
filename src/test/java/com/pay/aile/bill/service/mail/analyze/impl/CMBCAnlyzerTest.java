package com.pay.aile.bill.service.mail.analyze.impl;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pay.aile.bill.BillApplication;
import com.pay.aile.bill.service.mail.analyze.BankMailAnalyzer;
import com.pay.aile.bill.service.mail.analyze.model.AnalyzeParamsModel;
import com.pay.aile.bill.service.mail.analyze.util.TextExtractUtil;
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

	@Test
	public void test() {
		String content = "";
		try {
			content = downloadUtil.getFile("INBOX|1tbiThWLWFhgzTOQiAAAsW");
		} catch (Exception e) {
			e.printStackTrace();
		}
		content = TextExtractUtil.parseHtml(content, "font");
		System.out.println(content);
		AnalyzeParamsModel amp = new AnalyzeParamsModel();
		amp.setContent(content);
		amp.setBankCode("CMBC");
		amp.setEmail("czb18518679659@126.com");
		CMBCAnalyzer.analyze(amp);

	}

}
