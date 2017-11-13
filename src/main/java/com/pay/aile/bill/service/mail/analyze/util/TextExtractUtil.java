package com.pay.aile.bill.service.mail.analyze.util;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 抽取网页的正文。 为保持通用性没有针对特定网站编写规则。
 *
 * @author Charlie
 */
public class TextExtractUtil {

    /**
     * 抽取网页正文
     *
     * @param html
     *            网页HTML字符串
     *
     * @return 网页正文string
     */
    public static String parseHtml(String html, String... tagName) {
        Document document = Jsoup.parse(html);

        if (tagName != null && tagName.length > 0) {
            for (int i = 0; i < tagName.length; i++) {
                Elements elements = document.getElementsByTag(tagName[i]);

                for (int j = 0; j < elements.size(); j++) {
                    Element element = elements.get(j);
                    // elements.forEach(e -> {

                    // td需要特殊处理
                    if ("td".equals(tagName[i])) {

                        Elements childElements = element.getElementsByTag(tagName[i]);

                        if (childElements != null && childElements.size() > 1) {
                            continue;
                        }
                    }

                    String text = element.text();

                    text = text.replaceAll("\\s+", "");
                    element.text(text);

                    // });
                }
            }

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
        html = html.replaceAll(",", "");// 去掉金额分隔符
        html = html.replaceAll(" {2,}", " ");// 去掉多余空格，只留一个
        return html;
    }

    public static String parseHtmlBoc(String html, String... tagName) {
        List<String> list = null;
        Document document = Jsoup.parse(html);
        Elements element = document.select("table.bill_pay_des").get(1).select("td");
        for (int i = 0; i < element.size(); i++) {
            Elements tds = element.get(i).select("td");
            for (int j = 0; j < tds.size(); j++) {
                String text = tds.get(j).text();
                System.out.print(text);
            }
        }
        System.out.println(element.toString());

        return "";
    }

    /**
     * 去除pdf正文中的无用字符 格式化pdf正文
     *
     * @param pdf
     * @return
     */
    public static String parsePdf(String pdf) {
        pdf = pdf.replaceAll(",", "");// 去掉金额分隔符
        pdf = pdf.replaceAll("￥", "");// 去掉人民币符号
        pdf = pdf.replaceAll("$", "");// 去掉美元符号
        pdf = pdf.replaceAll("([\\u4e00-\\u9fa5]+) +([\\u4e00-\\u9fa5]+)", "$1$2");// 去掉中文之间空格
        pdf = pdf.replaceAll("\r", " ");// remove \r
        pdf = pdf.replaceAll(" {2,}", " ");// 去掉多余空格，只留一个
        return pdf;
    }

    // public static void removeSpace(Element element) {
    //
    // if (element.getel.children() != null && element.childNodeSize() > 0) {
    // element.children().forEach(e -> {
    // removeSpace(e);
    // });
    // } else {
    // String text = element.text();
    // text = text.replaceAll("\\s+", "");
    // element.text(text);
    // }
    //
    // }

}