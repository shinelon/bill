package com.pay.aile.bill.service.mail.analyze.banktemplate.cmb;

import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pay.aile.bill.entity.CreditBill;
import com.pay.aile.bill.entity.CreditBillDetail;
import com.pay.aile.bill.entity.CreditCard;
import com.pay.aile.bill.entity.CreditTemplate;
import com.pay.aile.bill.service.mail.analyze.enums.CardTypeEnum;
import com.pay.aile.bill.service.mail.analyze.model.AnalyzeParamsModel;
import com.pay.aile.bill.service.mail.analyze.model.AnalyzeResult;
import com.pay.aile.bill.service.mail.analyze.util.DateUtil;
import com.pay.aile.bill.service.mail.analyze.util.PatternMatcherUtil;

/**
 *
 * @author Charlie
 * @description 招商银行YOUNG卡解析模板
 */
@Service
public class CMBYoungTemplate extends AbstractCMBTemplate {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void initRules() {
        super.initRules();
        if (rules == null) {
            rules = new CreditTemplate();
            rules.setCardtypeId(2L);
            rules.setDueDate("\\d{2}/\\d{2}");
            rules.setCurrentAmount("\\d{2}/\\d{2} \\d+\\.?\\d*");
            rules.setMinimum("\\d{2}/\\d{2} \\d+\\.?\\d* \\d+\\.?\\d* \\d+\\.?\\d*");
            rules.setDetails("\\d{4} \\d{8} \\d{2}:\\d{2}:\\d{2} \\S+ \\S+ \\d+\\.?\\d*");
            rules.setTransactionDate("1");
            rules.setTransactionCurrency("3");
            rules.setTransactionAmount("5");
        }
    }

    @Override
    protected void afterAnalyze(AnalyzeParamsModel apm) {
        AnalyzeResult result = apm.getResult();
        if (result.getBill().getDueDate() == null) {
            result.setBill(null);
        }
        if (result.getBill() == null && (result.getDetail() == null || result.getDetail().isEmpty())) {
            logger.error("CMBYoungTemplate 解析失败,apm={}", apm);
            apm.setResult(null);
        }
    }

    @Override
    protected void analyzeDetails(List<CreditBillDetail> detail, String content, AnalyzeParamsModel apm,
            CreditCard card) {
        List<String> list = null;
        if (StringUtils.hasText(rules.getDetails())) {
            // 交易明细
            list = PatternMatcherUtil.getMatcher(rules.getDetails(), content);
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    String s = list.get(i);
                    detail.add(setCreditBillDetail(s));
                    setCardNumbers(card, s);
                }
            }
        }
    }

    @Override
    protected void analyzeDueDate(CreditBill bill, String content, AnalyzeParamsModel apm) {
        if (StringUtils.hasText(rules.getDueDate())) {
            try {
                String ruleValue = rules.getDueDate();
                List<String> list = PatternMatcherUtil.getMatcher(ruleValue, content);
                if (!list.isEmpty()) {

                    String date = list.get(0);
                    String monthStr = date.split("/")[0];
                    int billMonth = Integer.valueOf(monthStr);
                    Calendar c = Calendar.getInstance();
                    int nowMonth = c.get(Calendar.MONTH) + 1;
                    int year = c.get(Calendar.YEAR);
                    if (nowMonth == 12 && billMonth == 1) {
                        year++;
                    }
                    date = String.valueOf(year) + "/" + date;
                    bill.setDueDate(DateUtil.parseDate(date));
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    @Override
    protected String getValueByPattern(String key, String content, String ruleValue, AnalyzeParamsModel apm,
            String splitSign) {

        if (StringUtils.hasText(ruleValue)) {

            List<String> list = PatternMatcherUtil.getMatcher(ruleValue, content);
            if (!list.isEmpty()) {
                String result = list.get(0);
                String[] sa = result.split(splitSign);
                String value = sa[sa.length - 1];
                return value;
            }
        }
        return "";
    }

    @Override
    protected void setCardType() {
        cardType = CardTypeEnum.CMB_YOUNG;
    }
}
