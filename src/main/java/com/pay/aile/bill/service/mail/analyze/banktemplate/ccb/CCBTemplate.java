package com.pay.aile.bill.service.mail.analyze.banktemplate.ccb;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pay.aile.bill.entity.CreditBill;
import com.pay.aile.bill.entity.CreditCard;
import com.pay.aile.bill.entity.CreditTemplate;
import com.pay.aile.bill.mapper.CreditTemplateMapper;
import com.pay.aile.bill.service.mail.analyze.MailContentExtractor;
import com.pay.aile.bill.service.mail.analyze.enums.CardTypeEnum;
import com.pay.aile.bill.service.mail.analyze.model.AnalyzeParamsModel;
import com.pay.aile.bill.service.mail.analyze.util.DateUtil;
import com.pay.aile.bill.service.mail.analyze.util.PatternMatcherUtil;
import com.pay.aile.bill.service.mail.analyze.util.TextExtractUtil;

/**
 *
 * @author Charlie
 * @description 建设银行信用卡账单内容解析模板
 */
@Service
public class CCBTemplate extends AbstractCCBTemplate {

    @Resource(name = "pdfExtractor")
    private MailContentExtractor pextractor;
    @Autowired
    CreditTemplateMapper creditTemplateMapper;

    @Override
    public void initRules() {
        super.initRules();
        if (rules == null) {
            rules = new CreditTemplate();
            rules.setCardtypeId(13L);
            rules.setCardholder("尊敬的[\\u4e00-\\u9fa5]+");
            rules.setCycle("账单周期：\\d{4}年\\d{2}月\\d{2}日-\\d{4}年\\d{2}月\\d{2}日");
            rules.setBillDay("StatementDate \\d{4}-\\d{2}-\\d{2}");
            rules.setDueDate("DueDate \\d{4}-\\d{2}-\\d{2}");
            rules.setCredits("CreditLimit [a-zA-Z]+\\d+\\.?\\d*");
            rules.setCash("CashAdvanceLimit [a-zA-Z]+\\d+\\.?\\d*");
            rules.setCurrentAmount("争议款/笔数DisputeAmt/Nbr [\\u4e00-\\u9fa5]+（[a-zA-Z]+） \\d+\\.?\\d*");
            rules.setMinimum("争议款/笔数DisputeAmt/Nbr [\\u4e00-\\u9fa5]+（[a-zA-Z]+） \\d+\\.?\\d* \\d+\\.?\\d*");
            rules.setDetails(
                    "\\d{4}-\\d{2}-\\d{2} \\d{4}-\\d{2}-\\d{2} \\d{4}/?(\\d{4})? \\S+ [A-Za-z]+ -?\\d+\\.?\\d* [A-Za-z]+ -?\\d+\\.?\\d*");
            rules.setTransactionDate("0");
            rules.setBillingDate("1");
            rules.setCardNumbers("2");
            rules.setTransactionDescription("3");
            rules.setTransactionCurrency("4");
            rules.setTransactionAmount("5");
            rules.setAccountableAmount("7");
        }
    }

    @Override
    protected void analyzeCycle(CreditBill bill, String content, AnalyzeParamsModel apm) {
        if (StringUtils.hasText(rules.getCycle())) {
            String cycle = getValueByPattern("cycle", content, rules.getCycle(), apm, "：");
            String[] sa = cycle.split("-");
            bill.setBeginDate(DateUtil.parseDate(sa[0]));
            bill.setEndDate(DateUtil.parseDate(sa[1]));
        }
    }

    @Override
    protected void initContext(AnalyzeParamsModel apm) {
        String content = TextExtractUtil.parseHtml(apm.getContent(), "font");
        apm.setContent(content);

    }

    @Override
    protected void setCardNumbers(CreditCard card, String number) {
        if (StringUtils.hasText(rules.getCardNumbers()) && !StringUtils.hasText(card.getNumbers())) {
            try {
                int n = Integer.parseInt(rules.getCardNumbers());
                String[] detailArray = number.split(" ");
                String numbers = detailArray[n];
                String no = PatternMatcherUtil.getMatcherString("\\d{4}", numbers);
                card.setNumbers(no);
            } catch (Exception e) {

            }
        }
    }

    @Override
    protected void setCardType() {
        cardType = CardTypeEnum.CCB_LK;
    }
}
