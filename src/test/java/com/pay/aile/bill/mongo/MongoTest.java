package com.pay.aile.bill.mongo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pay.aile.bill.BillApplication;
import com.pay.aile.bill.exception.MailBillException;
import com.pay.aile.bill.utils.MongoDownloadUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BillApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class MongoTest {

	@Autowired
	private MongoDownloadUtil downloadUtil;

	@Test
	public void downFile() {
		try {
			String s = downloadUtil.getFile("光大银行信用卡");
			System.out.println(s);
		} catch (MailBillException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
