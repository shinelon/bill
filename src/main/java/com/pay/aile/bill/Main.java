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
        String s = "兴业银行信用卡电子账单 信用卡首页 ‖ 信用卡申请 ‖ 用卡指南 ‖ 缤纷优惠 ‖ 积分计划 ‖ 特色专区 ‖ 商旅中心 ‖ 分期业务 ‖ 信贷理财 ‖ 最新资讯 ‖ 尊敬的 巨天琪 女士 您好! 　　感谢您选择兴业银行信用卡，以下是您新星座银联人民币信用卡金卡（卡号末四位 6105）的2017年10月账单，请勿错过您的还款期限。 　　账单也可分期还，3、6、12、18、24、36期随兴选！请登录信用卡网上银行、手机银行、致电客服热线95561或绑定信用卡官方微信（cib_creditcard）办理。 账单周期 Statement Cycle 2017/09/25-2017/10/24 信用额度(人民币) Credit Limit(RMB) 31000 预借现金额度(人民币) Cash Advance Limit(RMB) 31000 到期还款日 Payment Due Date 2017年11月13日 本期应还款总额 New Balance RMB 9325.93 本期最低还款额 Minimum Payment RMB 2111.89 您本月新增积分为1292分，当前积分余额为73609分。 积分不够您还可以选择预借积分的方式兑换您心仪的积分商品，赶快登录积分商城或致电客服热线兑换吧！ 人民币账户 RMB A/C 本期应缴余额:9325.93元 New Balance 本期最低还款额:2111.89元 Minimum Payment 本期应缴余额 New Balance 9325.93 = 上期账单余额 Previous Balance 6729.89 - 已还金额 Payment &amp; Credit 1799.80 + 本期账单金额 New Activity 4263.86 + 本期调整金额 Adjustment 0.00 + 循环利息 Finance Charge 131.98 积分账户 Bonus Point Account 本期积分余额 Bonus Point Balance 73609 = 上期积分余额 Previous Bonus Point 72317 + 本期新增积分 New Bonus Point 1292 + 本期调整积分(含到期失效积分) Adjusted Bonus Point 0 - 本期兑换积分(含消费积分) Used Bonus Point 0 交易日期 Trans Date 记账日期 Post Date 交易摘要 Trans Description 交易地金额 Trans Amount 人民币金额 Amount(RMB) **** 主卡交易(卡号末四位 6105) **** 2017-09-25 2017-09-25 财付通--北京小桔科技有限公司 35.30 2017-09-25 2017-09-25 财付通--北京哈米科技有限公司 3.95 2017-09-25 2017-09-25 财付通--上海浦东发展银行股份有限公司广州 8.50 2017-09-25 2017-09-25 财付通--手机充值 10.00 2017-09-25 2017-09-25 财付通--手机充值 99.90 2017-09-25 2017-09-25 财付通--美团/大众点评 148.00 2017-09-25 2017-09-25 财付通--北京哈米科技有限公司 3.78 2017-09-25 2017-09-25 财付通--北京哈米科技有限公司 4.52 2017-09-25 2017-09-25 财付通--北京哈米科技有限公司 4.46 2017-09-25 2017-09-25 财付通--北京哈米科技有限公司 2.22 2017-09-24 2017-09-25 财付通--滴滴出行 17.72 2017-09-28 2017-09-28 财付通--北京小桔科技有限公司 35.30 2017-09-28 2017-09-28 财付通--每日优鲜便利购 4.20 2017-09-27 2017-09-28 财付通--滴滴出行 80.25 2017-09-29 2017-09-29 财付通--北京小桔科技有限公司 35.30 2017-09-29 2017-09-29 财付通--北京哈米科技有限公司 3.61 2017-09-29 2017-09-29 财付通--邻里家（北京）商贸有限公司 4.50 2017-09-30 2017-09-30 财付通--北京小桔科技有限公司 35.30 2017-09-30 2017-09-30 财付通--北京小桔科技有限公司退货20170930 -31.80 2017-09-30 2017-09-30 财付通--百度外卖 125.00 2017-09-30 2017-09-30 财付通--上海浦东发展银行股份有限公司广州 8.00 2017-09-30 2017-09-30 财付通--北京哈米科技有限公司 4.63 2017-09-29 2017-09-30 财付通--尧都农商银行 233.00 2017-09-29 2017-09-30 财付通--滴滴出行 20.36 2017-10-01 2017-10-01 财付通还款 -588.00 2017-10-01 2017-10-01 财付通--百度外卖 145.50 2017-09-30 2017-10-01 财付通--美团/大众点评 588.00 2017-10-02 2017-10-02 财付通--中信银行股份有限公司 179.00 2017-10-02 2017-10-02 财付通--光大银行 11.00 2017-10-02 2017-10-02 财付通--北京京东叁佰陆拾度电子商务有限公 158.00 2017-10-03 2017-10-03 财付通--百度外卖 133.01 2017-10-02 2017-10-03 财付通--上海浦东发展银行股份有限公司广州 30.00 2017-10-02 2017-10-03 财付通--北京满餐饮（通州） 262.67 2017-10-06 2017-10-06 财付通--美团/大众点评 10.00 2017-10-06 2017-10-06 财付通--美团/大众点评 73.70 2017-10-07 2017-10-07 财付通--滴滴出行 13.00 2017-10-09 2017-10-09 财付通--滴滴出行 13.40 2017-10-11 2017-10-11 财付通--北京小桔科技有限公司 35.30 2017-10-13 2017-10-13 财付通--滴滴出行 13.92 2017-10-16 2017-10-16 财付通--北京小桔科技有限公司 35.00 2017-10-19 2017-10-19 支付宝还款 -1211.80 2017-10-20 2017-10-20 财付通--滴滴出行 14.62 2017-10-20 2017-10-20 财付通--滴滴出行 33.28 2017-10-20 2017-10-20 财付通--滴滴出行 18.24 2017-10-24 2017-10-24 电销现分按月收24期 第12期共24期 1291.66 2017-10-24 2017-10-24 分期付款费用 第12期共24期 248.00 2017-10-24 2017-10-24 信用卡违约金 60.56 2017-10-24 2017-10-24 利息 131.98 分期及随借金说明：截至2017年10月24日您的分期及随借金未还总金额为18475.98元，其中随借金未还总金额为0.00。 账单说明：“本期应缴余额”栏目显示为“-***”(***为金额)，是指您在还款时多缴的或您预先存放在信用卡账户内的资金，该资金可抵扣您 的刷卡消费金额。如您本账单周期内的交易在本账单周期内发生退货，我行会为您直接抵扣对应的交易款项，不显示在“本期调整金额”中。 Statement description: If the column “New Balance” displays “-*** (where ***represents account balance) it indicates that there is a credit balance maintained in your account. No payment is required. Should any transactions be refunded within the statement cyclethe same amount shall be set against the original transaction.It will not appear in your adjust balance. ★ 兴业银行提醒您:请注意按时还款（至少还足最低还款额），避免影响个人信用记录。如您未能按时还款，相关逾期情况会上报至金融信用信息基础数据库以及其他依法设立的征信机构。 快捷选兴业，积分翻三倍 尽兴刷，“分”享全世界 【玩转积分】积分抽大奖，iPad Pro等你拿——兴业银行积分抽奖赢大礼2017年第4期 用卡顺畅与安全小贴士 支付信息安全指引 关于调整东航联名卡白金卡（标准版）权益的公告";
        //        String s = "2017/09/05 2017/09/05 8840 网上支付 财付通 14.00 2017/09/05 2017/09/05";
        String reg = "\\d{4}-\\d{2}-\\d{2} \\d{4}-\\d{2}-\\d{2} \\S+ \\d+.?\\d*";

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
