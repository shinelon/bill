package com.pay.aile.bill.service.mail.analyze.banktemplate.ceb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pay.aile.bill.entity.CreditBillDetail;
import com.pay.aile.bill.entity.CreditTemplate;
import com.pay.aile.bill.service.mail.analyze.enums.CardTypeEnum;
import com.pay.aile.bill.service.mail.analyze.util.DateUtil;

/**
 *
 * @author Charlie
 * @description 光大银行信用卡账单内容解析模板
 */
@Service
public class CEBGoldTemplate extends AbstractCEBTemplate {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void initRules() {
        super.initRules();
        if (rules == null) {
            rules = new CreditTemplate();
            // 账单日
            rules.setBillingDate(
                    "积分余额 Rewards Points Balance \\d{4}/\\d{2}/\\d{2}");
            //到期还款日
            rules.setDueDate(
                    "积分余额 Rewards Points Balance \\d{4}/\\d{2}/\\d{2} \\d{4}/\\d{2}/\\d{2}");
            //额度
            rules.setCredits(
                    "积分余额 Rewards Points Balance \\d{4}/\\d{2}/\\d{2} \\d{4}/\\d{2}/\\d{2} \\d+");
            //应还款额
            rules.setCurrentAmount(
                    "积分余额 Rewards Points Balance \\d{4}/\\d{2}/\\d{2} \\d{4}/\\d{2}/\\d{2} \\d+ \\d+.?\\d*");
            rules.setDetails(
                    "\\d{4}/\\d{2}/\\d{2} \\d{4}/\\d{2}/\\d{2} \\d{4} .*\\n");
        }
    }

    @Override
    protected void setCardType() {
        cardType = CardTypeEnum.CEB_GOLD;
    }    
    @Override
    protected CreditBillDetail setCreditBillDetail(String detail) {
        CreditBillDetail cbd = new CreditBillDetail();
        String[] sa = detail.split(" ");
        cbd.setTransactionDate(DateUtil.parseDate(sa[0]));
        cbd.setBillingDate(DateUtil.parseDate(sa[1]));
        cbd.setTransactionAmount(sa[sa.length - 1].replaceAll("\\n", ""));
        String desc = "";
        for (int i = 3; i < sa.length - 1; i++) {
            desc = desc + sa[i];
        }
        cbd.setTransactionDescription(desc);
        return cbd;
    }
}
