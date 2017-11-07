package com.pay.aile.bill.service.mail.analyze.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * 抽取网页的正文。 为保持通用性没有针对特定网站编写规则。
 * 
 * @author Charlie
 */
public class TextExtractUtil {

    /**
     * 抽取网页正文
     * 
     * @param html	网页HTML字符串
     * 
     * @return 网页正文string
     */
    public static String parseHtml(String html) {
        Document document = Jsoup.parse(html);
        html = document.toString();
        html = html.replaceAll("(?is)<!DOCTYPE.*?>", ""); // remove html top infomation
        html = html.replaceAll("(?is)<!--.*?-->", ""); // remove html comment
        html = html.replaceAll("(?is)<script.*?>.*?</script>", ""); // remove javascript
        html = html.replaceAll("(?is)<style.*?>.*?</style>", ""); // remove css
        html = html.replaceAll("(?is)<.*?>", "");
        html = html.replaceAll("&nbsp;", ""); // remove &nbsp;
        html = html.replaceAll("\n", "");//remove \n
        html = html.replaceAll("$", "");//去掉美元符号
        html = html.replaceAll("＄", "");
        html = html.replaceAll("￥", "");//去掉人民币符号
        html = html.replace(",", "");//去掉金额分隔符
        html = html.replaceAll(" {2,}", " ");//去掉多余空格，只留一个
        return html;
    }

    /**
     * 去除pdf正文中的无用字符
     * 格式化pdf正文
     * @param pdf
     * @return
     */
    public static String parsePdf(String pdf) {
        pdf = pdf.replaceAll(",", "");//去掉金额分隔符
        pdf = pdf.replaceAll("￥", "");//去掉人民币符号
        pdf = pdf.replaceAll("$", "");//去掉美元符号
        pdf = pdf.replaceAll("([\\u4e00-\\u9fa5]+) +([\\u4e00-\\u9fa5]+)",
                "$1$2");//去掉中文之间空格 
        pdf = pdf.replaceAll("\r", " ");//remove \r
        pdf = pdf.replaceAll("\n", " ");//remove \n
        pdf = pdf.replaceAll(" {2,}", " ");//去掉多余空格，只留一个
        return pdf;
    }

}