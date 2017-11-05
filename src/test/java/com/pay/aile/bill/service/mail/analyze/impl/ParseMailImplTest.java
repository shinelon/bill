package com.pay.aile.bill.service.mail.analyze.impl;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pay.aile.bill.BillApplication;
import com.pay.aile.bill.entity.CreditTemplate;
import com.pay.aile.bill.service.mail.analyze.IParseMail;
import com.pay.aile.bill.service.mail.analyze.constant.Constant;
import com.pay.aile.bill.service.mail.analyze.util.JedisClusterUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BillApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ParseMailImplTest {

    @Resource
    private IParseMail parseMail;

    @Test
    public void testExecute() {
        parseMail.execute();
    }

    @Test
    public void test() {

        CreditTemplate rules = new CreditTemplate();
        rules.setCardtypeId(1L);
        rules.setDueDate("到期还款日：\\d{4}年\\d{2}月\\d{2}日");
        rules.setCurrentAmount("到期还款日：\\d{4}年\\d{2}月\\d{2}日 RMB \\d+.?\\d+");
        rules.setCredits(
                "到期还款日：\\d{4}年\\d{2}月\\d{2}日 RMB \\d+.?\\d+ USD \\d+.?\\d+ RMB \\d+.?\\d+ USD \\d+.?\\d+ \\d*\\D* RMB \\d+.?\\d+");
        rules.setPrepaidCashAmount("预借现金额度 RMB \\d+.?\\d+ RMB \\d+.?\\d+");
        rules.setCash("取现额度 RMB \\d+.?\\d+ RMB \\d+.?\\d+");
        rules.setDetails(
                "\\d{8} \\d{8} \\d{0,4} \\d*\\D* [A-Za-z]+ -?\\d+.?\\d+ RMB -?\\d+.?\\d+");

        JedisClusterUtils.saveBean(
                Constant.redisTemplateRuleCache + "CITIC_STANDARD", rules);
        CreditTemplate r = JedisClusterUtils.getBean(
                Constant.redisTemplateRuleCache + "CITIC_STANDARD",
                CreditTemplate.class);

        System.out.println(r);
    }

}
