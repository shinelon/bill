package com.pay.aile.bill;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.io.RandomAccessBufferedFileInputStream;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import com.pay.aile.bill.service.mail.analyze.util.TextExtractUtil;

public class Main {

    public static void main(String[] args) throws IOException {
        testReg();
    }

    public static void testReg() {
        String s = "尊敬的 王利军 先生 您好! 感谢您使用中国光大银行信用卡！特为您呈上2017年09月09日至2017年10月08日信用卡账户变动情况。您最晚于2017年10月27日还款，请勿错过您的到期还款日。 如果您持有我行多张信用卡，需分别进行还款。【 温馨提示 】 犯罪分子多以“卡片逾期冻结”、“调额”、“积分兑换年费”等为由，发送诈骗短信。请您切勿泄露卡片有效期、交易验证码等重要信息！ 您可以通过中国光大银行柜台、网上银行、自助设备进行还款，也可以通过其它银行柜台、网上银行转账或汇款方式还款，如果采用跨行还款方式，建议您早于到期还款日前三个工作日还款。 根据银联要求，当持卡人发起有卡自助消费业务时，为确保交易真实，须在每笔交易中输入交易密码。 如您未按要求进行还款，您的账户将会逾期，逾期信息会上报到中国人民银行个人信用信息基础数据库，建议您及时还款，保持良好的个人信用信息状态。 我行的分期还款业务，可为您缓解还款压力。 尊敬的客户，银行从不会向客户索要任何密码！因此，为了您的用卡安全，切勿将您的手机动态密码、信用卡交易密码告诉任何人，谨防上当受骗。 中国光大银行信用卡电子账单(2017年10月) 信用卡账户信息 Account Summary 账单分期 &gt;&gt;&gt;&gt;&gt; 账单日 Statement Date 到期还款日 Payment Due Date 信用额度(人民币) Credit Limit(RMB) 人民币本期应还款额 RMB Current Amount Due 人民币最低还款额 RMB Minimum Amount Due 积分余额 Rewards Points Balance 2017/10/08 2017/10/27 21000 666.00 666.00 60759 您的信用额度为您名下多张信用卡共享。 您需按不同账户、不同币种分别还款。如有外币欠款，可用同币种外币还款或人民币购汇还款。 0001111111117863248 本期账户交易明细　Transaction Details 人民币账户（单位：元）RMB Account 现在还款 &gt;&gt;&gt;&gt;&gt; 账户 Account Number 本期余额 Total Balance 本期应还款额 Statement Balance 本期最小还款额 Minimum Payment Due 00040625****6207330 福信用卡 666.00 666.00 666.00 总计 666.00 666.00 666.00 积分统计 Bonus Point 积分天地 &gt;&gt;&gt;&gt;&gt; 本期积分余额 Current total points = 期初积分余额 Previous total points + 本期新增积分 Earned － 本期兑换积分 Exchanged + 调整积分 Adjusted 2017年12月31日即将失效积分Points expiring on December 31st 60759 = 60759 + 0 － 0 + 0 0 注： 由于各商户的银行入帐日有所不同，您本期交易的部分积分可能将在下月账单中显示。 人民币账户交易明细（单位：元）RMB Account Details 账号 Account Number：00040625****6207330福信用卡上期欠款 Opening Balance：1332.00 交易日期 Trans Date 记账日期 Post Date 卡号末四位 Card No.(Last 4 Digits) 交易说明 Description of Transaction 金额 Amount(RMB) 2017/09/19 2017/09/20 7330 已收妥您的款项 666.00 2017/10/08 2017/10/08 7330 8月份账单取现分期3期：本期应还款666.00，余额0.00，余期为00期 本期欠款 Closing Balance：666.00 精彩活动 欢迎登录光大银行信用卡地带&gt;&gt;&gt;&gt;&gt; 更多活动 &gt;&gt;";
        //        String s = "2017/09/05 2017/09/05 8840 网上支付 财付通 14.00 2017/09/05 2017/09/05";
        String reg = "\\d{4}/\\d{2}/\\d{2} \\d{4}/\\d{2}/\\d{2} \\d{4} \\S+ \\d+.?\\d*";

        Pattern pattern = Pattern.compile(reg);

        Matcher matcher = pattern.matcher(s);

        while (matcher.find()) {
            String e = matcher.group(0);
            System.out.println(e);
        }
    }

    public static void parsePdf() throws IOException {
        //        FileInputStream fis = new FileInputStream(new File("D:\\guangda.pdf"));
        //        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //        int read = -1;
        //        while ((read = fis.read()) != -1) {
        //            bos.write(read);
        //        }
        PDFParser parser = new PDFParser(
                new RandomAccessBufferedFileInputStream("D:\\guangda.pdf"));
        parser.parse();
        PDDocument document = parser.getPDDocument();

        PDFTextStripper stripper = new PDFTextStripper();
        String result = stripper.getText(document);
        //        System.out.println(result);
        result = TextExtractUtil.parsePdf(result);
        System.out.println(result);

    }

    public static void parseHtmlzhongxin() throws IOException {
        FileInputStream fis = new FileInputStream(
                new File("D:\\中信银行信用卡电子账单1.html"));

        String sb = copyToString(fis, Charset.forName("utf-8"));
        System.out.println(sb);

        sb = TextExtractUtil.parseHtml(sb);
        System.out.println(sb);
    }

    public static void parseCMBHtml() throws IOException {
        FileInputStream fis = new FileInputStream(new File(
                "D:\\下载邮件html版本\\招商银行信用卡电子账单――账单分期最高送100元还款金！-20151017051832.html"));

        String sb = copyToString(fis, Charset.forName("utf-8"));
        System.out.println(sb);

        sb = TextExtractUtil.parseHtml(sb);

        System.out.println(sb);
    }

    public static void parseBCMHtml() throws IOException {
        FileInputStream fis = new FileInputStream(
                new File("D:\\下载邮件html版本\\交通银行信用卡电子账单-20171025191414.html"));

        String sb = copyToString(fis, Charset.forName("utf-8"));
        System.out.println(sb);

        sb = TextExtractUtil.parseHtml(sb);

        System.out.println(sb);
    }

    public static String copyToString(InputStream in, Charset charset)
            throws IOException {
        if (in == null) {
            return "";
        }

        StringBuilder out = new StringBuilder();
        InputStreamReader reader = new InputStreamReader(in, charset);
        char[] buffer = new char[4096];
        int bytesRead = -1;
        while ((bytesRead = reader.read(buffer)) != -1) {
            out.append(buffer, 0, bytesRead);
        }
        return out.toString();
    }

}
