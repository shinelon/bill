package com.pay.aile.bill.service.mail.analyze.banktemplate.spdb;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pay.aile.bill.entity.CreditBill;
import com.pay.aile.bill.entity.CreditTemplate;
import com.pay.aile.bill.service.mail.analyze.enums.CardTypeEnum;
import com.pay.aile.bill.service.mail.analyze.model.AnalyzeParamsModel;
import com.pay.aile.bill.service.mail.analyze.util.DateUtil;
import com.pay.aile.bill.service.mail.analyze.util.PatternMatcherUtil;

/**
 * @author ji
 * @description 浦发银行信用卡
 */
@Service
public class SPDBCreditTemplate extends AbstractSPDBTemplate {
    @Override
    public void initRules() {
        super.initRules();
        if (rules == null) {
            rules = new CreditTemplate();
            rules.setCardtypeId(15L);
            // 目前邮箱中浦发信用卡账单只有这两项/应还款额-还款日期
            rules.setDueDate("到期\\s?还\\s?款日[:|：]\\s?\\d{4}/\\d{1,2}/\\d{1,2}");
            rules.setCurrentAmount("本期\\s?应还\\s?款\\s?总额\\s?[:|：]\\s?(-?\\d+,?){0,9}.?\\d{0,2}");
        }
    }

    /**
     * 应还款额
     */
    @Override
    protected void analyzeCurrentAmount(CreditBill bill, String content, AnalyzeParamsModel apm) {
        if (StringUtils.hasText(rules.getCurrentAmount())) {
            String currentAmount = getValueByPattern("currentAmount", content, rules.getCurrentAmount(), apm, "：");
            currentAmount = PatternMatcherUtil.getMatcherString("\\d+.?\\d*", currentAmount);
            bill.setCurrentAmount(new BigDecimal(currentAmount));
        }
    }

    /**
     * 还款日期
     */
    @Override
    protected void analyzeDueDate(CreditBill bill, String content, AnalyzeParamsModel apm) {
        if (StringUtils.hasText(rules.getDueDate())) {
            String date = getValueByPattern("dueDate", content, rules.getDueDate(), apm, "：");
            bill.setDueDate(DateUtil.parseDate(date));
        }
    }

    /**
     * 设置信用卡类型
     */
    @Override
    protected void setCardType() {
        cardType = CardTypeEnum.SPDB;
    }
}
