package com.pay.aile.bill.service.mail.analyze.banktemplate.gdb;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pay.aile.bill.entity.CreditBill;
import com.pay.aile.bill.entity.CreditCard;
import com.pay.aile.bill.entity.CreditTemplate;
import com.pay.aile.bill.service.mail.analyze.enums.CardTypeEnum;
import com.pay.aile.bill.service.mail.analyze.model.AnalyzeParamsModel;
import com.pay.aile.bill.service.mail.analyze.util.DateUtil;
import com.pay.aile.bill.service.mail.analyze.util.PatternMatcherUtil;

/**
 *
 * @author Charlie
 * @description 广发银行解析模板
 */
@Service
public class GDBTemplate extends AbstractGDBTemplate {

    @Override
    public void initRules() {
        super.initRules();
        if (rules == null) {
            rules = new CreditTemplate();
            rules.setCardtypeId(8L);
            rules.setCardNumbers("\\d{4}\\*+\\d{4}");
            rules.setYearMonth("\\d{4}年\\d{2}月的信用卡电子账单");
            rules.setCardholder("尊敬的[\\u4e00-\\u9fa5]+");
            rules.setCycle("账单周期：\\d{4}/\\d{2}/\\d{2}至\\d{4}/\\d{2}/\\d{2}");
            rules.setDueDate("\\d{4}\\*+\\d{4} \\d+\\.?\\d* \\d+\\.?\\d* \\d{4}/\\d{2}/\\d{2}");
            rules.setCurrentAmount("\\d{4}\\*+\\d{4} \\d+\\.?\\d*");
            rules.setMinimum("\\d{4}\\*+\\d{4} \\d+\\.?\\d* \\d+\\.?\\d*");
            rules.setCredits("\\d{4}\\*+\\d{4} \\d+\\.?\\d* \\d+\\.?\\d* \\d{4}/\\d{2}/\\d{2} \\S+ \\d+\\.?\\d*");
        }
    }

    @Override
    protected void analyzeCardNo(CreditCard card, String content, AnalyzeParamsModel apm) {
        if (StringUtils.hasText(rules.getCardNumbers())) {
            String value = getValueByPattern("cardNumbers", content, rules.getCardNumbers(), apm, " ");
            List<String> list = PatternMatcherUtil.getMatcher("\\d{4}", value);
            card.setNumbers(list.get(1));
        }
    }

    @Override
    protected void analyzeCycle(CreditBill bill, String content, AnalyzeParamsModel apm) {
        if (StringUtils.hasText(rules.getCycle())) {

            String cycle = getValueByPattern("cycle", content, rules.getCycle(), apm, "：");
            String[] sa = cycle.split("至");
            bill.setBeginDate(DateUtil.parseDate(sa[0]));
            bill.setEndDate(DateUtil.parseDate(sa[1]));
        }
    }

    @Override
    protected void analyzeYearMonth(CreditBill bill, String content, AnalyzeParamsModel apm) {
        if (StringUtils.hasText(rules.getYearMonth())) {

            String yearMonth = getValueByPattern("yearMonth", content, rules.getYearMonth(), apm, "");
            if (StringUtils.hasText(yearMonth)) {
                String year = yearMonth.substring(0, 4);
                String month = yearMonth.substring(5, 7);
                bill.setYear(year);
                bill.setMonth(month);
            }
        }
    }

    @Override
    protected void setCardType() {
        cardType = CardTypeEnum.GDB_DEFAULT;
    }

}
