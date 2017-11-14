package com.pay.aile.bill.service.mail.analyze.banktemplate.cmbc;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pay.aile.bill.entity.CreditBill;
import com.pay.aile.bill.entity.CreditBillDetail;
import com.pay.aile.bill.entity.CreditCard;
import com.pay.aile.bill.entity.CreditTemplate;
import com.pay.aile.bill.service.mail.analyze.enums.CardTypeEnum;
import com.pay.aile.bill.service.mail.analyze.model.AnalyzeParamsModel;
import com.pay.aile.bill.service.mail.analyze.util.DateUtil;

/**
 *
 * @author zhibin.cui
 * @description 民生银行信用卡账单内容解析模板
 */
@Service
public class CMBCTemplate extends AbstractCMBCTemplate {

    private String date = "";

    @Override
    protected void analyzeDueDate(CreditBill bill, String content, AnalyzeParamsModel apm) {
        if (StringUtils.hasText(rules.getDueDate())) {

            date = getValueByPattern("dueDate", content, rules.getDueDate(), apm, " ");
            bill.setDueDate(DateUtil.parseDate(date));
        }
    }

    @Override
    public void initRules() {
        if (rules == null) {
            rules = new CreditTemplate();
            rules.setCardtypeId(17L);
            rules.setBillingDate("StatementDate \\d{4}/\\d{2}/\\d{2}"); // 账单日
            rules.setDueDate("PaymentDueDate \\d{4}/\\d{2}/\\d{2}");
            rules.setCurrentAmount("NewBalance \\d+.?\\d*");
            rules.setMinimum("Min.Payment: [\\u4e00-\\u9fa5][\\u4e00-\\u9fa5][\\u4e00-\\u9fa5] \\d+.?\\d*");
            rules.setCardNumbers("\\d+.?\\d* \\d{4}");
            rules.setDetails("\\d{2}/\\d{2} \\d{2}/\\d{2} \\S+ \\d+.?\\d* \\d{4}");
        }
    }

    @Override
    protected void setCardNumbers(CreditCard card, String number) {
        String[] detailArray = number.split(" ");
        card.setNumbers(detailArray[4]);
    };

    @Override
    protected void setCardType() {
        cardType = CardTypeEnum.CMBC_DEFAULT;
    }

    @Override
    protected CreditBillDetail setCreditBillDetail(String detail) {
        CreditBillDetail cbd = new CreditBillDetail();
        String[] sa = detail.split(" ");

        String year = date.substring(0, 5);
        cbd.setTransactionDate(DateUtil.parseDate(year + sa[0]));
        cbd.setBillingDate(DateUtil.parseDate(year + sa[1]));
        cbd.setTransactionAmount(sa[sa.length - 2].replaceAll("\\n", ""));
        String desc = "";
        for (int i = 2; i < sa.length - 2; i++) {
            desc = desc + sa[i];
        }
        cbd.setTransactionDescription(desc);
        return cbd;
    }

}
