package com.pay.aile.bill.service.mail.analyze.banktemplate.boc;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
 * @author zhibin.cui
 * @description 中国银行信用卡账单内容解析模板
 */
@Service
public class BOCTemplate extends AbstractBOCTemplate {

    @Override
    protected void initContext(AnalyzeParamsModel apm) {

        parseHtml(apm.getContent());

        // extractor.extract(apm.getContent(), "td");
    };

    @Override
    public void initRules() {
        if (rules == null) {
            rules = new CreditTemplate();
            rules.setBillingDate("Due \\d{4}-\\d{2}-\\d{2} \\d{4}-\\d{2}-\\d{2}"); // 账单日
            rules.setDueDate("Due \\d{4}-\\d{2}-\\d{2}");
            rules.setMinimum("外币FCY \\S+ \\d+.?\\d* \\d+.?\\d*");
            rules.setCardNumbers("\\d{4}-\\d{2}-\\d{2} \\d{4}-\\d{2}-\\d{2} \\d{4}");
            rules.setCurrentAmount("Due \\d{4}-\\d{2}-\\d{2} \\d{4}-\\d{2}-\\d{2} \\d+.?\\d*");
            rules.setDetails("\\d{4}-\\d{2}-\\d{2} \\d{4}-\\d{2}-\\d{2} \\d{4} \\S+ \\d+.?\\d*");
        }
    }

    private String parseHtml(String html) {
        Document document = Jsoup.parse(html);
        Elements elements = document.getElementsByClass("bill_pay_des");
        // 将收入的数字替换为-
        if (elements.size() > 2) {
            // 获取交易的table
            Element table = elements.get(1);
            // 获取tr的列表
            Elements trs = table.getElementsByTag("tr");
            for (int i = 1; i < trs.size(); i++) {
                Element tr = trs.get(i);
                Elements tds = tr.getElementsByTag("td");
                if (tds.size() > 5) {
                    Element td = trs.get(4);
                    if (StringUtils.hasText(td.text())) {
                        td.text("-" + td.text());
                    }
                }
            }

        }
        elements = document.getElementsByTag("td");

        for (int j = 0; j < elements.size(); j++) {
            Element element = elements.get(j);
            // td需要特殊处理
            if ("td".equals("td")) {

                Elements childElements = element.getElementsByTag("td");

                if (childElements != null && childElements.size() > 1) {
                    continue;
                }
            }

            String text = element.text();

            text = text.replaceAll("\\s+", "");
            element.text(text);

        }

        html = document.toString();
        html = html.replaceAll("(?is)<!DOCTYPE.*?>", ""); // remove html top
                                                          // infomation
        html = html.replaceAll("(?is)<!--.*?-->", ""); // remove html comment
        html = html.replaceAll("(?is)<script.*?>.*?</script>", ""); // remove
                                                                    // javascript
        html = html.replaceAll("(?is)<style.*?>.*?</style>", ""); // remove css
        html = html.replaceAll("(?is)<.*?>", "");
        html = html.replaceAll("&nbsp;", ""); // remove &nbsp;
        html = html.replaceAll("\n", "");// remove \n
        html = html.replaceAll("$", "");// 去掉美元符号
        html = html.replaceAll("＄", "");
        html = html.replaceAll("￥", "");// 去掉人民币符号
        html = html.replace(",", "");// 去掉金额分隔符
        html = html.replaceAll(" {2,}", " ");// 去掉多余空格，只留一个
        return html;
    }

    @Override
    protected void setCardNumbers(CreditCard card, String number) {
        String[] detailArray = number.split(" ");
        card.setNumbers(detailArray[2]);
    }

    @Override
    protected void setCardType() {
        cardType = CardTypeEnum.BOC_DEFAULT;
    }

    @Override
    protected CreditBillDetail setCreditBillDetail(String detail) {
        CreditBillDetail cbd = new CreditBillDetail();
        String[] sa = detail.split(" ");
        cbd.setTransactionDate(DateUtil.parseDate(sa[0]));
        cbd.setBillingDate(DateUtil.parseDate(sa[1]));
        cbd.setTransactionAmount(sa[sa.length - 1]);
        String desc = "";
        for (int i = 2; i < sa.length - 2; i++) {
            desc = desc + sa[i];
        }
        cbd.setTransactionDescription(desc);
        return cbd;
    }
}
