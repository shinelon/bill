package com.pay.aile.bill.service.mail.analyze.banktemplate;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.pay.aile.bill.entity.CreditBill;
import com.pay.aile.bill.entity.CreditBillDetail;
import com.pay.aile.bill.entity.CreditTemplate;
import com.pay.aile.bill.service.CreditBillDetailService;
import com.pay.aile.bill.service.CreditBillService;
import com.pay.aile.bill.service.mail.analyze.BankMailAnalyzerTemplate;
import com.pay.aile.bill.service.mail.analyze.constant.Constant;
import com.pay.aile.bill.service.mail.analyze.enums.CardTypeEnum;
import com.pay.aile.bill.service.mail.analyze.model.AnalyzeParamsModel;
import com.pay.aile.bill.service.mail.analyze.model.AnalyzeResult;
import com.pay.aile.bill.service.mail.analyze.util.DateUtil;
import com.pay.aile.bill.service.mail.analyze.util.JedisClusterUtils;
import com.pay.aile.bill.service.mail.analyze.util.PatternMatcherUtil;

/**
 *
 * @author Charlie
 * @description 卡种解析基础模板
 */
public abstract class BaseBankTemplate implements BankMailAnalyzerTemplate,
        Comparable<BaseBankTemplate>, InitializingBean {

    /**
     * 统计每一种模板的调用次数 用于不同卡种之间的排序,调用次数高的排位靠前
     */
    private volatile int count;
    @Resource
    private CreditBillDetailService creditBillDetailService;
    @Resource
    private CreditBillService creditBillService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 信用卡类型 由子类去初始化自己是什么信用卡类型
     */
    protected CardTypeEnum cardType;

    /**
     * 模板解析邮件时需要的关键字及对应的规则 key:到期还款日/应还款金额.eg value:规则 根据银行和信用卡类型,从缓存中初始化
     */
    protected CreditTemplate rules;

    @Override
    public void afterPropertiesSet() throws Exception {
        setCardType();
    }

    @Override
    public void analyze(AnalyzeParamsModel apm) {
        count++;
        initRules();
        if (rules != null) {
            apm.setCardtypeId(rules.getCardtypeId());
        }
        beforeAnalyze(apm);
        analyzeInternal(apm);
    }

    /**
     * 
     * @param apm
     */
    protected void beforeAnalyze(AnalyzeParamsModel apm) {

    }

    /**
     * 用于不同卡种之间的排序,调用次数高的排位靠前
     */
    @Override
    public int compareTo(BaseBankTemplate o) {
        if (o == null) {
            return 1;
        }
        return count > o.count ? 1 : -1;
    }

    @Override
    public void handleResult(AnalyzeParamsModel apm) {
        handleResultInternal(apm);
    }

    /**
     *
     * @Title: analyzeCash
     * @Description:预借现金
     * @param bill
     * @param content
     * @param apm
     * @return void 返回类型 @throws
     */
    protected void analyzeCash(CreditBill bill, String content,
            AnalyzeParamsModel apm) {
        if (StringUtils.hasText(rules.getCash())) {

            String currentAmount = getValueByPattern(content, rules.getCash(),
                    apm, "");
            bill.setCurrentAmount(new BigDecimal(currentAmount));
        }
    }

    /**
     *
     * @Title: analyzeCredits
     * @Description: 信用额度
     * @param bill
     * @param content
     * @param apm
     * @return void 返回类型 @throws
     */
    protected void analyzeCredits(CreditBill bill, String content,
            AnalyzeParamsModel apm) {
        if (StringUtils.hasText(rules.getCredits())) {

            String credits = getValueByPattern(content, rules.getCredits(), apm,
                    "");
            bill.setCredits(new BigDecimal(credits));
        }
    }

    /**
     *
     * @Title: analyzeCurrentAmount
     * @Description: 应还款额
     * @param bill
     * @param content
     * @param apm
     * @return void 返回类型 @throws
     */
    protected void analyzeCurrentAmount(CreditBill bill, String content,
            AnalyzeParamsModel apm) {
        if (StringUtils.hasText(rules.getCurrentAmount())) {

            String currentAmount = getValueByPattern(content,
                    rules.getCurrentAmount(), apm, "");
            bill.setCurrentAmount(new BigDecimal(currentAmount));
        }
    }

    protected void analyzeDetail(List<CreditBillDetail> detailList,
            String content, AnalyzeParamsModel apm) {
        List<String> list = PatternMatcherUtil.getMatcher(rules.getDetails(),
                content);
        if (list.isEmpty()) {
            handleNotMatch("details", rules.getDetails(), apm);
        }
        String[] sa = null;
        for (int i = 0; i < list.size(); i++) {

            detailList.add(setCreditBillDetail(list.get(i)));
        }

    }

    /**
     *
     * @Title: analyzeDueDate
     * @Description: 解析参数
     * @param bill
     * @param content
     * @param apm
     * @return void 返回类型 @throws
     */
    protected void analyzeDueDate(CreditBill bill, String content,
            AnalyzeParamsModel apm) {
        if (StringUtils.hasText(rules.getDueDate())) {

            String date = getValueByPattern(content, rules.getDueDate(), apm,
                    " ");
            bill.setDueDate(DateUtil.parseDateFromString(date, null));
        }
    }

    protected void analyzeInternal(AnalyzeParamsModel apm) {
        logger.info("账单内容：{}", apm);
        String content = apm.getContent();
        AnalyzeResult ar = new AnalyzeResult();
        CreditBill bill = ar.getBill();
        List<CreditBillDetail> detail = ar.getDetail();
        if (rules == null) {
            throw new RuntimeException("账单模板规则未初始化");
        }
        List<String> list = null;
        String result = "";
        String[] sa = null;

        analyzeDueDate(bill, content, apm);
        analyzeCurrentAmount(bill, content, apm);
        analyzeCredits(bill, content, apm);
        analyzeCash(bill, content, apm);
        // analyzeDueDate(bill, content, apm);
        // if (StringUtils.hasText(rules.getCurrentAmount())) {
        // // 应还款额
        // list = PatternMatcherUtil.getMatcher(rules.getCurrentAmount(),
        // content);
        // if (list.isEmpty()) {
        // handleNotMatch("currentAmount", rules.getCurrentAmount(), apm);
        // }
        // result = list.get(0);
        // sa = result.split(" ");
        // String currentAmount = sa[sa.length - 1];
        // bill.setCurrentAmount(new BigDecimal(currentAmount));
        // }
        // if (StringUtils.hasText(rules.getCredits())) {
        // // 信用额度
        // list = PatternMatcherUtil.getMatcher(rules.getCredits(), content);
        // if (list.isEmpty()) {
        // handleNotMatch("credits", rules.getCredits(), apm);
        // }
        // result = list.get(0);
        // sa = result.split(" ");
        // String credits = sa[sa.length - 1];
        // bill.setCredits(new BigDecimal(credits));
        // }
        if (StringUtils.hasText(rules.getPrepaidCashAmount())) {
            // 预借现金
            // list =
            // PatternMatcherUtil.getMatcher(rules.getPrepaidCashAmount(),
            // content);
            // if(!list.isEmpty()){
            // result = list.get(0);
            // sa = result.split(" ");
            // String preCash = sa[sa.length - 1];
            //
            // }
        }
        // if (StringUtils.hasText(rules.getCash())) {
        // // 取现
        // list = PatternMatcherUtil.getMatcher(rules.getCredits(), content);
        // if (!list.isEmpty()) {
        // result = list.get(0);
        // sa = result.split(" ");
        // String cash = sa[sa.length - 1];
        // bill.setCash(new BigDecimal(cash));
        // }
        // }
        if (StringUtils.hasText(rules.getDetails())) {
            // 交易明细
            list = PatternMatcherUtil.getMatcher(rules.getDetails(), content);
            if (list.isEmpty()) {
                handleNotMatch("details", rules.getDetails(), apm);
            }
            for (int i = 0; i < list.size(); i++) {
                String s = list.get(i);
                sa = s.split(" ");
                CreditBillDetail cbd = new CreditBillDetail();
                cbd.setTransactionDate(
                        DateUtil.parseDateFromString(sa[0], "yyyyMMdd"));// 交易日期
                cbd.setBillingDate(
                        DateUtil.parseDateFromString(sa[1], "yyyyMMdd"));// 记账日期
                cbd.setTransactionDescription(sa[3]);// 交易描述
                cbd.setAccountType(sa[4]);
                cbd.setTransactionAmount(sa[5]);// 交易货币/金额
                cbd.setAccountableAmount(sa[7]);// 记账货币/金额
                detail.add(cbd);
            }
        }
        apm.setResult(ar);
    }

    protected String getValueByPattern(String content, String ruleValue,
            AnalyzeParamsModel apm, String splitSign) {

        if (StringUtils.hasText(ruleValue)) {

            List<String> list = PatternMatcherUtil.getMatcher(ruleValue,
                    content);
            if (list.isEmpty()) {
                handleNotMatch(ruleValue, rules.getDueDate(), apm);
            }
            String result = list.get(0);
            String[] sa = result.split(splitSign);
            String date = sa[sa.length - 1];
            return date;
        }
        return "";
    }

    protected void handleNotMatch(String key, String reg,
            AnalyzeParamsModel apm) {
        apm.setResult(null);
        throw new RuntimeException(String.format(
                "未找到匹配值,bank=CITIC,cardType=CITIC_STANDARD,key=%s,reg=%s", key,
                reg));
    }

    @Transactional
    protected void handleResultInternal(AnalyzeParamsModel apm) {
        Long emailId = apm.getEmailId();
        // 解析成功，保存账单到数据库
        CreditBill bill = apm.getResult().getBill();
        List<CreditBillDetail> billDetails = apm.getResult().getDetail();
        Long billId = null;
        if (bill != null) {
            bill.setEmailId(emailId);
            bill.setCardtypeId(apm.getCardtypeId());
            bill.setReceiveDate(apm.getReceiveDate());
            creditBillService.saveOrUpdateCreditBill(bill);
            billId = bill.getId();
        }
        billId = bill.getId();
        if (billDetails != null && !billDetails.isEmpty()) {
            for (CreditBillDetail creditBillDetail : billDetails) {
                try {
                    if (billId == null) {
                        bill.setReceiveDate(
                                creditBillDetail.getTransactionDate());
                        bill = creditBillService
                                .findCreditBillByTransDate(bill);
                        if (bill == null) {
                            logger.warn("未查询到明细对应的账单,result={}", apm);
                            throw new RuntimeException("未查询到明细对应的账单");
                        }
                    }
                    creditBillDetail.setBillId(billId);
                    creditBillDetailService
                            .saveCreditBillDetail(creditBillDetail);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }

    }

    /**
     * 获取模板对应的关键字
     */
    protected void initRules() {
        // 根据cardCode从缓存中获取对应的规则
        String cardCode = cardType.getCardCode();
        rules = JedisClusterUtils.getBean(
                Constant.redisTemplateRuleCache + cardCode,
                CreditTemplate.class);

    }

    /**
     * 设置信用卡类型
     */
    protected void setCardType() {

    }

    protected CreditBillDetail setCreditBillDetail(String detail) {
        CreditBillDetail cbd = new CreditBillDetail();

        return cbd;

    }
}
