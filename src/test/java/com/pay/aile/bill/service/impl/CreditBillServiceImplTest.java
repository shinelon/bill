package com.pay.aile.bill.service.impl;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pay.aile.bill.BillApplication;
import com.pay.aile.bill.entity.CreditBill;
import com.pay.aile.bill.service.CreditBillService;
import com.pay.aile.bill.service.mail.analyze.util.DateUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BillApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CreditBillServiceImplTest {

    @Resource
    private CreditBillService creditBillService;

    @Test
    public void testSaveOrUpdateCreditBill() {
        CreditBill bill = new CreditBill();
        bill.setDueDate(DateUtil.parseDateFromString("2017-09-06", null));
        bill.setEmailId(1L);
        bill.setStatus(1);
        creditBillService.saveOrUpdateCreditBill(bill);
    }

}
