package com.pay.aile.bill.service.mail.analyze.banktemplate.ceb;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pay.aile.bill.entity.CreditBillDetail;
import com.pay.aile.bill.entity.CreditCard;
import com.pay.aile.bill.entity.CreditTemplate;
import com.pay.aile.bill.service.mail.analyze.enums.CardTypeEnum;
import com.pay.aile.bill.service.mail.analyze.model.AnalyzeParamsModel;
import com.pay.aile.bill.service.mail.analyze.util.DateUtil;

/**
 *
 * @author Charlie
 * @description 光大银行信用卡账单内容解析模板
 */
@Service
public class CEBGoldTemplate extends AbstractCEBTemplate {

    @Override
    public void initRules() {
        super.initRules();
        if (rules == null) {
            rules = new CreditTemplate();
            rules.setCardtypeId(12L);
            rules.setCardholder("尊敬的[\\u4e00-\\u9fa5]+");
            // 账单日
            rules.setBillingDate("积分余额RewardsPointsBalance \\d{4}/\\d{2}/\\d{2}");
            // 到期还款日
            rules.setDueDate("积分余额RewardsPointsBalance \\d{4}/\\d{2}/\\d{2} \\d{4}/\\d{2}/\\d{2}");
            // 额度
            rules.setCredits("积分余额RewardsPointsBalance \\d{4}/\\d{2}/\\d{2} \\d{4}/\\d{2}/\\d{2} \\d+\\.?\\d*");
            // 应还款额
            rules.setCurrentAmount(
                    "积分余额RewardsPointsBalance \\d{4}/\\d{2}/\\d{2} \\d{4}/\\d{2}/\\d{2} \\d+\\.?\\d* \\d+\\.?\\d*");
            rules.setMinimum(
                    "积分余额RewardsPointsBalance \\d{4}/\\d{2}/\\d{2} \\d{4}/\\d{2}/\\d{2} \\d+\\.?\\d* \\d+\\.?\\d* \\d+\\.?\\d*");

            rules.setDetails("\\d{4}/\\d{2}/\\d{2} \\d{4}/\\d{2}/\\d{2} \\d{4} \\S+ -?\\d+\\.?\\d*");
            rules.setCardNumbers("2");
        }
    }

    @Override
    protected void analyzeCardholder(CreditCard card, String content, AnalyzeParamsModel apm) {
        if (StringUtils.hasText(rules.getCardholder())) {
            String cardholder = getValueByPattern("cardholder", content, rules.getCardholder(), apm, " ");
            cardholder = cardholder.replaceAll("尊敬的", "").replaceAll("先生", "").replaceAll("女士", "").replaceAll("您好",
                    "");
            card.setCardholder(cardholder);
        }
    }

    @Override
    protected void setCardType() {
        cardType = CardTypeEnum.CEB_GOLD;
    }

    @Override
    protected CreditBillDetail setCreditBillDetail(String detail) {
        detail = detail.replaceAll("\\n", "");
        CreditBillDetail cbd = new CreditBillDetail();
        String[] sa = detail.split(" ");
        cbd.setTransactionDate(DateUtil.parseDate(sa[0]));
        cbd.setBillingDate(DateUtil.parseDate(sa[1]));
        cbd.setTransactionAmount(sa[sa.length - 1]);
        String desc = "";
        for (int i = 3; i < sa.length - 1; i++) {
            desc = desc + sa[i];
        }
        cbd.setTransactionDescription(desc);
        return cbd;
    }
}
