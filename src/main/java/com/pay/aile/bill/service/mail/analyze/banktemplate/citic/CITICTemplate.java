package com.pay.aile.bill.service.mail.analyze.banktemplate.citic;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pay.aile.bill.entity.CreditBill;
import com.pay.aile.bill.entity.CreditTemplate;
import com.pay.aile.bill.service.mail.analyze.enums.CardTypeEnum;
import com.pay.aile.bill.service.mail.analyze.model.AnalyzeParamsModel;
import com.pay.aile.bill.service.mail.analyze.util.DateUtil;

/**
 *
 * @author Charlie
 * @description 中信银行解析模板
 */
@Service
public class CITICTemplate extends AbstractCITICTemplate {

    @Override
    public void initRules() {
        super.initRules();
        if (rules == null) {
            rules = new CreditTemplate();
            rules.setCardtypeId(1L);
            rules.setDueDate("到期还款日：\\d{4}年\\d{2}月\\d{2}日");
            rules.setCurrentAmount("到期还款日：\\d{4}年\\d{2}月\\d{2}日 [a-zA-Z]{3} \\d+\\.?\\d*");
            rules.setCredits(
                    "到期还款日：\\d{4}年\\d{2}月\\d{2}日 [a-zA-Z]{3} \\d+\\.?\\d+ [a-zA-Z]{3} \\d+\\.?\\d+ [a-zA-Z]{3} \\d+\\.?\\d+ [a-zA-Z]{3} \\d+.?\\d+ \\d*\\D* [a-zA-Z]{3} \\d+\\.?\\d*");
            rules.setPrepaidCashAmount("预借现金额度 [a-zA-Z]{3} \\d+\\.?\\d+ [a-zA-Z]{3} \\d+\\.?\\d+");
            rules.setCash("取现额度 [a-zA-Z]{3} \\d+\\.?\\d+ [a-zA-Z]{3} \\d+\\.?\\d+");
            rules.setDetails("\\d{8} \\d{8} \\d{0,4} \\S+ [A-Za-z]{3} -?\\d+\\.?\\d* [a-zA-Z]{3} -?\\d+\\.?\\d*");
            rules.setTransactionDate("0");
            rules.setBillingDate("1");
            rules.setTransactionDescription("3");
            rules.setTransactionCurrency("4");
            rules.setTransactionAmount("5");
            rules.setAccountableAmount("7");
        }
    }

    @Override
    protected void analyzeDueDate(CreditBill bill, String content, AnalyzeParamsModel apm) {
        if (StringUtils.hasText(rules.getDueDate())) {

            String date = getValueByPattern("dueDate", content, rules.getDueDate(), apm, "：");
            bill.setDueDate(DateUtil.parseDate(date));
        }
    }

    @Override
    protected void setCardType() {
        cardType = CardTypeEnum.CITIC_STANDARD;
    }
}
