package com.pay.aile.bill.service.mail.analyze.banktemplate;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.util.ReflectionUtils;
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
    protected CreditBillDetailService creditBillDetailService;
    @Resource
    protected CreditBillService creditBillService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 信用卡类型 由子类去初始化自己是什么信用卡类型
     */
    protected CardTypeEnum cardType;

    /**
     * 存放明细规则的map
     */
    protected Map<Integer, String> detailMap = new HashMap<Integer, String>();
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
        initDetail();
        if (rules != null) {
            apm.setCardtypeId(rules.getCardtypeId());
        }
        beforeAnalyze(apm);
        analyzeInternal(apm);
        afterAnalyze(apm);
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

            String cash = getValueByPattern("cash", content, rules.getCash(),
                    apm, " ");
            cash = PatternMatcherUtil.getMatcherString("\\d+\\.?\\d*", cash);
            if (StringUtils.hasText(cash)) {

                bill.setCash(new BigDecimal(cash));
            }
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

            String credits = getValueByPattern("credits", content,
                    rules.getCredits(), apm, " ");
            credits = PatternMatcherUtil.getMatcherString("\\d+\\.?\\d",
                    credits);
            if (StringUtils.hasText(credits)) {
                bill.setCredits(new BigDecimal(credits));
            }
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

            String currentAmount = getValueByPattern("currentAmount", content,
                    rules.getCurrentAmount(), apm, " ");
            currentAmount = PatternMatcherUtil
                    .getMatcherString("-?\\d+\\.?\\d*", currentAmount);
            if (StringUtils.hasText(currentAmount)) {
                if (currentAmount.startsWith("-")) {
                    currentAmount = currentAmount.replaceAll("-", "");
                }
                bill.setCurrentAmount(new BigDecimal(currentAmount));
            }
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

            String date = getValueByPattern("dueDate", content,
                    rules.getDueDate(), apm, " ");
            bill.setDueDate(DateUtil.parseDate(date));
        }
    }

    protected void analyzeInternal(AnalyzeParamsModel apm) {
        logger.info("模板:{},模板解析参数：{}", this.getClass().getSimpleName(), apm);
        logger.debug("账单内容:{}", apm.getContent());
        String content = apm.getContent();
        AnalyzeResult ar = new AnalyzeResult();
        CreditBill bill = ar.getBill();
        List<CreditBillDetail> detail = ar.getDetail();
        if (rules == null) {
            throw new RuntimeException("账单模板规则未初始化");
        }

        analyzeDueDate(bill, content, apm);
        analyzeCurrentAmount(bill, content, apm);
        analyzeCredits(bill, content, apm);
        analyzeCash(bill, content, apm);

        analyzeDetails(detail, content, apm);
        apm.setResult(ar);
    }

    protected void analyzeDetails(List<CreditBillDetail> detail, String content,
            AnalyzeParamsModel apm) {
        List<String> list = null;
        if (StringUtils.hasText(rules.getDetails())) {
            // 交易明细
            list = PatternMatcherUtil.getMatcher(rules.getDetails(), content);
            if (list.isEmpty()) {
                handleNotMatch("details", rules.getDetails(), apm);
            }
            for (int i = 0; i < list.size(); i++) {
                String s = list.get(i);
                detail.add(setCreditBillDetail(s));
            }
        }
    }

    /**
     *
     * @param apm
     */
    protected void beforeAnalyze(AnalyzeParamsModel apm) {

    }

    /**
    *
    * @param apm
    */
    protected void afterAnalyze(AnalyzeParamsModel apm) {

    }

    protected String getValueByPattern(String key, String content,
            String ruleValue, AnalyzeParamsModel apm, String splitSign) {

        if (StringUtils.hasText(ruleValue)) {

            List<String> list = PatternMatcherUtil.getMatcher(ruleValue,
                    content);
            if (list.isEmpty()) {
                handleNotMatch(key, ruleValue, apm);
            }
            String result = list.get(0);
            String[] sa = result.split(splitSign);
            String value = sa[sa.length - 1];
            return value;
        }
        return "";
    }

    protected void handleNotMatch(String key, String reg,
            AnalyzeParamsModel apm) {
        apm.setResult(null);
        throw new RuntimeException(
                String.format("未找到匹配值,bank=%s,cardType=%s,key=%s,reg=%s",
                        cardType.getBankCode().getBankCode(),
                        cardType.getCardCode(), key, reg));
    }

    @Transactional
    protected void handleResultInternal(AnalyzeParamsModel apm) {
        // 解析成功，保存账单到数据库
        CreditBill bill = apm.getResult().getBill();
        List<CreditBillDetail> billDetails = apm.getResult().getDetail();
        Long billId = null;
        if (bill != null) {
            bill.setEmailId(apm.getEmailId());
            bill.setCardtypeId(apm.getCardtypeId());
            bill.setSentDate(apm.getSentDate());
            bill.setBankCode(apm.getBankCode());
            billId = creditBillService.saveOrUpdateCreditBill(bill);
            billId = bill.getId();
        }

        if (billDetails != null && !billDetails.isEmpty()) {
            for (CreditBillDetail creditBillDetail : billDetails) {
                try {
                    creditBillDetail.setBillId(billId);
                    creditBillDetailService
                            .saveCreditBillDetail(creditBillDetail);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }

    }

    protected void initDetail() {
        if (rules != null && StringUtils.hasText(rules.getDetails())) {
            if (StringUtils.hasText(rules.getTransactionDate())) {
                try {
                    detailMap.put(Integer.parseInt(rules.getTransactionDate()),
                            "transactionDate");
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
            if (StringUtils.hasText(rules.getBillingDate())) {
                try {
                    detailMap.put(Integer.parseInt(rules.getBillingDate()),
                            "billingDate");
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
            if (StringUtils.hasText(rules.getTransactionDescription())) {
                try {
                    detailMap.put(
                            Integer.parseInt(rules.getTransactionDescription()),
                            "transactionDescription");
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
            if (StringUtils.hasText(rules.getTransactionCurrency())) {
                try {
                    detailMap.put(
                            Integer.parseInt(rules.getTransactionCurrency()),
                            "transactionCurrency");
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
            if (StringUtils.hasText(rules.getTransactionAmount())) {
                try {
                    detailMap.put(
                            Integer.parseInt(rules.getTransactionAmount()),
                            "transactionAmount");
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
            if (StringUtils.hasText(rules.getAccountableAmount())) {
                try {
                    detailMap.put(
                            Integer.parseInt(rules.getAccountableAmount()),
                            "accountableAmount");
                } catch (Exception e) {
                    logger.error(e.getMessage());
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
        String[] detailArray = detail.split(" ");
        for (Integer i = 0; i < detailArray.length; i++) {
            if (detailMap.containsKey(i)) {
                Field field;
                try {
                    field = CreditBillDetail.class
                            .getDeclaredField(detailMap.get(i));
                    if (field.getType() == Date.class) {
                        ReflectionUtils.setField(field, cbd,
                                DateUtil.parseDate(detailArray[i]));
                    } else {
                        ReflectionUtils.setField(field, cbd, detailArray[i]);
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }

            } else {
                setField(cbd, i, detailArray[i]);
            }

        }
        return cbd;

    }

    protected void setField(CreditBillDetail cbd, int index, String value) {

    }
}
