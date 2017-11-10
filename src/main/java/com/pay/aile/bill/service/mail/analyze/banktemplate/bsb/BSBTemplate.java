package com.pay.aile.bill.service.mail.analyze.banktemplate.bsb;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pay.aile.bill.entity.CreditBill;
import com.pay.aile.bill.entity.CreditTemplate;
import com.pay.aile.bill.service.mail.analyze.enums.CardTypeEnum;
import com.pay.aile.bill.service.mail.analyze.model.AnalyzeParamsModel;
import com.pay.aile.bill.service.mail.analyze.util.PatternMatcherUtil;

/**
 *
 * @author Charlie
 * @description 包商银行解析模板
 */
@Service
public class BSBTemplate extends AbstractBSBTemplate {

    @Override
    public void initRules() {
        super.initRules();
        if (rules == null) {
            rules = new CreditTemplate();
            rules.setCardtypeId(9L);
            rules.setCurrentAmount("本期应还款额：\\d+\\.?\\d*");
            rules.setMinimum("最低还款额：\\d+\\.?\\d*");
        }
    }

    @Override
    protected void analyzeCurrentAmount(CreditBill bill, String content, AnalyzeParamsModel apm) {
        if (StringUtils.hasText(rules.getCurrentAmount())) {

            String currentAmount = getValueByPattern("currentAmount", content, rules.getCurrentAmount(), apm, "：");
            currentAmount = PatternMatcherUtil.getMatcherString("-?\\d+\\.?\\d*", currentAmount);
            if (StringUtils.hasText(currentAmount)) {
                if (currentAmount.startsWith("-")) {
                    currentAmount = currentAmount.replaceAll("-", "");
                }
                bill.setCurrentAmount(new BigDecimal(currentAmount));
            }
        }
    }

    @Override
    protected void setCardType() {
        cardType = CardTypeEnum.BSB_DEFAULT;
    }

}
