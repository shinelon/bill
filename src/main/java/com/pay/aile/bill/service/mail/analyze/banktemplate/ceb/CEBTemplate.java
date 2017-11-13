package com.pay.aile.bill.service.mail.analyze.banktemplate.ceb;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pay.aile.bill.entity.CreditBillDetail;
import com.pay.aile.bill.entity.CreditCard;
import com.pay.aile.bill.entity.CreditTemplate;
import com.pay.aile.bill.service.mail.analyze.MailContentExtractor;
import com.pay.aile.bill.service.mail.analyze.enums.CardTypeEnum;
import com.pay.aile.bill.service.mail.analyze.model.AnalyzeParamsModel;
import com.pay.aile.bill.service.mail.analyze.util.DateUtil;

/**
 *
 * @author Charlie
 * @description 光大银行信用卡账单内容解析模板
 */
@Service
public class CEBTemplate extends AbstractCEBTemplate {

    @Resource
    private MailContentExtractor pdfExtractor;

    @Override
    public void initRules() {
        super.initRules();
        if (rules == null) {
            rules = new CreditTemplate();
            rules.setCardtypeId(4L);
            rules.setCardholder("[\\u4e00-\\u9fa5]+\\(收\\)");
            rules.setBillingDate("账单日 \\d{4}/\\d{2}/\\d{2}");// 账单日
            rules.setDueDate("到期还款日 \\d{4}/\\d{2}/\\d{2}");
            rules.setCurrentAmount("人民币本期应还款额 \\d+\\.?\\d*");
            rules.setCredits("信用额度 \\d+\\.?\\d*");
            rules.setMinimum("人民币最低还款额 \\d+\\.?\\d*");
            rules.setDetails("\\d{4}/\\d{2}/\\d{2} \\d{4}/\\d{2}/\\d{2} \\d{4} .*\\n");
            rules.setCardNumbers("2");
        }
    }

    @Override
    protected void analyzeCardholder(CreditCard card, String content, AnalyzeParamsModel apm) {
        if (StringUtils.hasText(rules.getCardholder())) {
            String cardholder = getValueByPattern("cardholder", content, rules.getCardholder(), apm, "");
            cardholder = cardholder.replaceAll("\\(收\\)", "");
            card.setCardholder(cardholder);
        }
    }

    @Override
    protected void initContext(AnalyzeParamsModel apm) {
        String content = pdfExtractor.extract(apm.getOriginContent());
        apm.setContent(content);
    }

    @Override
    protected void setCardType() {
        cardType = CardTypeEnum.CEB_DEFAULT;
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
