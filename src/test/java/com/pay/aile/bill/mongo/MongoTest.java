package com.pay.aile.bill.mongo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pay.aile.bill.BillApplication;
import com.pay.aile.bill.exception.MailBillException;
import com.pay.aile.bill.service.mail.analyze.util.TextExtractUtil;
import com.pay.aile.bill.utils.MongoDownloadUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BillApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class MongoTest {

	@Autowired
	private MongoDownloadUtil downloadUtil;

	@Test
	public void downFile() {
		try {
			// List<String> rList = new ArrayList<String>();
			// rList.add("font,账单周期,0");
			// rList.add("font,账单周期,0");
			// rList.add("font,Statement Date,0");
			String s = downloadUtil.getFile("f9d570f2-fe30-4f05-9d59-f96574fdde84");
			// String s =
			// downloadUtil.getFile("ae2012f6-98d2-4513-bceb-cbd8dd4664b0");

			System.out.println(TextExtractUtil.parseHtml(s));
			// System.out.println(s);
			// Document doc = Jsoup.parse(s);
			// Elements fontElements = doc.getElementsByTag("td");
			// fontElements.forEach(e -> {
			// System.out.println(e.text());
			// System.out.println("-----------------------------------------");
			// });
		} catch (MailBillException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
