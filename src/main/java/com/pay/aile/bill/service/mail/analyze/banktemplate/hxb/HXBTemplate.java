package com.pay.aile.bill.service.mail.analyze.banktemplate.hxb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pay.aile.bill.entity.CreditBill;
import com.pay.aile.bill.entity.CreditCard;
import com.pay.aile.bill.mapper.CreditTemplateMapper;
import com.pay.aile.bill.service.mail.analyze.enums.CardTypeEnum;
import com.pay.aile.bill.service.mail.analyze.model.AnalyzeParamsModel;
import com.pay.aile.bill.service.mail.analyze.util.PatternMatcherUtil;

/**
 *
 * @author Charlie
 * @description 交通银行信用卡账单内容解析模板
 */
@Service
public class HXBTemplate extends AbstractHXBTemplate {

    @Autowired
    CreditTemplateMapper creditTemplateMapper;

    @Override
    public void initRules() {
        // super.initRules();
        if (rules == null) {
            rules = creditTemplateMapper.selectById(4);
        }
        super.initDetail();

    }

    /**
     *
     * @Title: analyzeDueDate
     * @Description: 解析参数
     * @param card
     * @param content
     * @param apm
     * @return void 返回类型 @throws
     */
    @Override
    protected void analyzeCardholder(CreditCard card, String content, AnalyzeParamsModel apm) {
        if (StringUtils.hasText(rules.getCardholder())) {

            String cardholder = getValueByPattern("cardholder", content, rules.getCardholder(), apm,
                    "");
            cardholder = cardholder.substring(cardholder.indexOf("的") + 1, cardholder.length() - 3);
            card.setCardholder(cardholder);
        }
    }

    // /**
    // *
    // * @Title: analyzeDueDate
    // * @Description: 解析参数
    // * @param card
    // * @param content
    // * @param apm
    // * @return void 返回类型 @throws
    // */
    // @Override
    // protected void analyzeBillDate(CreditCard card, String content,
    // AnalyzeParamsModel apm) {
    // if (StringUtils.hasText(rules.getBillDay())) {
    //
    // String billDay = getValueByPattern("billDay", content,
    // rules.getBillDay(), apm, " ");
    // card.setBillDay(billDay);
    // }
    // }

    @Override
    protected void analyzeCycle(CreditBill bill, String content, AnalyzeParamsModel apm) {
        if (StringUtils.hasText(rules.getCycle())) {

            String cycle = getValueByPattern("cycle", content, rules.getCycle(), apm, " ");
            cycle = PatternMatcherUtil.getMatcherString("\\d+.?\\d", cycle);
            // bill.setBeginDate(beginDate);
            // bill.setEndDate(endDate);
        }
    }

    @Override
    protected void analyzeInternal(AnalyzeParamsModel apm) {
        super.analyzeInternal(apm);
    }

    @Override
    protected void analyzeYearMonth(CreditBill bill, String content, AnalyzeParamsModel apm) {
        if (StringUtils.hasText(rules.getYearMonth())) {

            String yearMonth = getValueByPattern("yearMonth", content, rules.getYearMonth(), apm,
                    "");
            bill.setYear(yearMonth.substring(0, 4));
            bill.setMonth(yearMonth.substring(5, 7));
            // bill.setMonth(month);
            // bill.setBeginDate(beginDate);
            // bill.setEndDate(endDate);
        }
    }

    @Override
    protected void setCardNumbers(CreditCard card, String number) {
        String[] detailArray = number.split(" ");
        card.setNumbers(detailArray[4]);
    }

    @Override
    protected void setCardType() {
        cardType = CardTypeEnum.BCM_DEFAULT;
    }
}
