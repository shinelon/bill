package com.pay.aile.bill.service.mail.analyze.banktemplate.psbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pay.aile.bill.entity.CreditBillDetail;
import com.pay.aile.bill.entity.CreditCard;
import com.pay.aile.bill.entity.CreditTemplate;
import com.pay.aile.bill.service.mail.analyze.enums.CardTypeEnum;
import com.pay.aile.bill.service.mail.analyze.model.AnalyzeParamsModel;
import com.pay.aile.bill.service.mail.analyze.util.DateUtil;

/**
 *
 * @author zhibin.cui
 * @description 邮储银行信用卡账单内容解析模板
 */
@Service
public class PSBCTemplate extends AbstractPSBCTemplate {
    private Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    public void initRules() {
        super.initRules();
        if (rules == null) {
            rules = new CreditTemplate();
            rules.setCardtypeId(16L);
            rules.setBillDay("账单日 \\d{2}");
            rules.setDueDate("到期还款日 \\d{4}年\\d{2}月\\d{2}日");
            rules.setCurrentAmount("本期应还款总额 \\d+.?\\d*");
            rules.setCredits("信用额度 \\d+.?\\d*");
            rules.setPrepaidCashAmount("预借现金额度 \\d+.?\\d*");
            rules.setCardNumbers("\\d+.?\\d* \\d{4}");
            rules.setMinimum("最低还款额 \\d+.?\\d*");
            rules.setDetails("\\d{4}/\\d{2}/\\d{2} \\d{4}/\\d{2}/\\d{2} \\S+ \\d+.?\\d* \\d{4}");
        }
    }

    @Override
    protected void setCardNumbers(CreditCard card, String number) {
        String[] detailArray = number.split(" ");
        card.setNumbers(detailArray[4]);
    }

    @Override
    protected void setCardType() {
        cardType = CardTypeEnum.PSBC_DEFAULT;
    }

    @Override
    protected CreditBillDetail setCreditBillDetail(String detail) {
        CreditBillDetail cbd = new CreditBillDetail();
        String[] sa = detail.split(" ");
        cbd.setTransactionDate(DateUtil.parseDate(sa[0]));
        cbd.setBillingDate(DateUtil.parseDate(sa[1]));
        cbd.setTransactionAmount(sa[sa.length - 2].replaceAll("\\n", ""));
        String desc = "";
        for (int i = 2; i < sa.length - 2; i++) {
            desc = desc + sa[i];
        }
        cbd.setTransactionDescription(desc);
        return cbd;
    }
}
