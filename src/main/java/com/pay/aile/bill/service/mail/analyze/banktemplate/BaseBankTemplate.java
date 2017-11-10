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
import com.pay.aile.bill.entity.CreditCard;
import com.pay.aile.bill.entity.CreditTemplate;
import com.pay.aile.bill.service.CreditBillDetailService;
import com.pay.aile.bill.service.CreditBillService;
import com.pay.aile.bill.service.CreditCardService;
import com.pay.aile.bill.service.mail.analyze.BankMailAnalyzerTemplate;
import com.pay.aile.bill.service.mail.analyze.config.TemplateCache;
import com.pay.aile.bill.service.mail.analyze.enums.CardTypeEnum;
import com.pay.aile.bill.service.mail.analyze.exception.AnalyzeBillException;
import com.pay.aile.bill.service.mail.analyze.model.AnalyzeParamsModel;
import com.pay.aile.bill.service.mail.analyze.model.AnalyzeResult;
import com.pay.aile.bill.service.mail.analyze.util.DateUtil;
import com.pay.aile.bill.service.mail.analyze.util.PatternMatcherUtil;

/**
 *
 * @author Charlie
 * @description 卡种解析基础模板
 */
public abstract class BaseBankTemplate
        implements BankMailAnalyzerTemplate, Comparable<BaseBankTemplate>, InitializingBean {

    /**
     * 统计每一种模板的调用次数 用于不同卡种之间的排序,调用次数高的排位靠前
     */
    private volatile int count;
    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 信用卡类型 由子类去初始化自己是什么信用卡类型
     */
    protected CardTypeEnum cardType;

    @Resource
    protected CreditBillDetailService creditBillDetailService;

    @Resource
    protected CreditBillService creditBillService;

    @Resource
    protected CreditCardService creditCardService;

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
    public void analyze(AnalyzeParamsModel apm) throws AnalyzeBillException {
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
     * @param apm
     * @throws AnalyzeBillException
     */
    protected void afterAnalyze(AnalyzeParamsModel apm) throws AnalyzeBillException {
        checkCardAndBill(apm);
    }

    protected void analyzeBillDate(CreditCard card, String content, AnalyzeParamsModel apm) {
        if (StringUtils.hasText(rules.getBillDay())) {

            String billDay = getValueByPattern("billDay", content, rules.getBillDay(), apm, " ");
            card.setBillDay(billDay);
        }
    }

    /**
     *
     * @Title: analyzeDueDate
     * @Description: 解析参数
     * @param card
     * @param content
     * @param apm
     * @return void 返回类型 @throws
     */
    protected void analyzeCardholder(CreditCard card, String content, AnalyzeParamsModel apm) {
        if (StringUtils.hasText(rules.getCardholder())) {

            String cardholder = getValueByPattern("cardholder", content, rules.getCardholder(), apm,
                    " ");
            card.setCardholder(cardholder);
        }
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
    protected void analyzeCash(CreditBill bill, String content, AnalyzeParamsModel apm) {
        if (StringUtils.hasText(rules.getCash())) {

            String cash = getValueByPattern("cash", content, rules.getCash(), apm, " ");
            cash = PatternMatcherUtil.getMatcherString("\\d+.?\\d*", cash);
            bill.setCash(new BigDecimal(cash));
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
    protected void analyzeCredits(CreditBill bill, String content, AnalyzeParamsModel apm) {
        if (StringUtils.hasText(rules.getCredits())) {

            String credits = getValueByPattern("credits", content, rules.getCredits(), apm, " ");
            credits = PatternMatcherUtil.getMatcherString("\\d+.?\\d*", credits);
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
    protected void analyzeCurrentAmount(CreditBill bill, String content, AnalyzeParamsModel apm) {
        if (StringUtils.hasText(rules.getCurrentAmount())) {

            String currentAmount = getValueByPattern("currentAmount", content,
                    rules.getCurrentAmount(), apm, " ");
            currentAmount = PatternMatcherUtil.getMatcherString("-?\\d+\\.?\\d*", currentAmount);
            if (StringUtils.hasText(currentAmount)) {
                if (currentAmount.startsWith("-")) {
                    currentAmount = currentAmount.replaceAll("-", "");
                }
                bill.setCurrentAmount(new BigDecimal(currentAmount));
            }
        }
    }

    protected void analyzeCycle(CreditBill bill, String content, AnalyzeParamsModel apm) {
        if (StringUtils.hasText(rules.getCycle())) {

            String cycle = getValueByPattern("cycle", content, rules.getCycle(), apm, " ");
            cycle = PatternMatcherUtil.getMatcherString("\\d+.?\\d*", cycle);
            // bill.setBeginDate(beginDate);
            // bill.setEndDate(endDate);
        }
    }

    protected void analyzeDetails(List<CreditBillDetail> detail, String content,
            AnalyzeParamsModel apm, CreditCard card) {
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
                setCardNumbers(card, s);
            }
        }
    }

    /**
     *
     * @Title: analyzeDueDate
     * @Description: 还款日
     * @param bill
     * @param content
     * @param apm
     * @return void 返回类型 @throws
     */
    protected void analyzeDueDate(CreditBill bill, String content, AnalyzeParamsModel apm) {
        if (StringUtils.hasText(rules.getDueDate())) {

            String date = getValueByPattern("dueDate", content, rules.getDueDate(), apm, " ");
            bill.setDueDate(DateUtil.parseDate(date));
        }
    }

    protected void analyzeInternal(AnalyzeParamsModel apm) {
        logger.info("账单内容：{}", apm);
        String content = apm.getContent();
        AnalyzeResult ar = new AnalyzeResult();
        // ka
        CreditCard card = ar.getCard();
        // 账单
        CreditBill bill = ar.getBill();

        List<CreditBillDetail> detail = ar.getDetail();
        if (rules == null) {
            throw new RuntimeException("账单模板规则未初始化");
        }

        // 年月
        analyzeYearMonth(bill, content, apm);
        // 周期
        analyzeCycle(bill, content, apm);
        // 持卡人
        analyzeCardholder(card, content, apm);
        // 还款日
        analyzeBillDate(card, content, apm);
        // 还款日
        analyzeDueDate(bill, content, apm);
        // 本期账单金额
        analyzeCurrentAmount(bill, content, apm);
        // 最低还款额
        analyzeMinimum(bill, content, apm);
        // 信用二度
        analyzeCredits(bill, content, apm);
        // 取取现金额
        analyzeCash(bill, content, apm);

        analyzeDetails(detail, content, apm, card);
        // 设置卡片
        setCard(card, bill, apm);
        apm.setResult(ar);
    }

    protected void analyzeMinimum(CreditBill bill, String content, AnalyzeParamsModel apm) {
        if (StringUtils.hasText(rules.getMinimum())) {

            String minimum = getValueByPattern("minimum", content, rules.getMinimum(), apm, " ");
            minimum = PatternMatcherUtil.getMatcherString("\\d+.?\\d*", minimum);
            bill.setMinimum(new BigDecimal(minimum));
        }
    }

    protected void analyzeYearMonth(CreditBill bill, String content, AnalyzeParamsModel apm) {
        if (StringUtils.hasText(rules.getYearMonth())) {

            String yearMonth = getValueByPattern("yearMonth", content, rules.getYearMonth(), apm,
                    " ");
            yearMonth = PatternMatcherUtil.getMatcherString("\\d+.?\\d*", yearMonth);
            // bill.setYear(year);
            // bill.setMonth(month);
            // bill.setBeginDate(beginDate);
            // bill.setEndDate(endDate);
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
     * @throws AnalyzeBillException
     * @Title: checkCardAndBill @Description: 检查数据合法性 @param apm @return void
     *         返回类型 @throws
     */
    protected void checkCardAndBill(AnalyzeParamsModel apm) throws AnalyzeBillException {
        // 检查是否包含卡号和持卡人
        CreditCard card = apm.getResult().getCard();
        if (!StringUtils.hasText(card.getNumbers())) {
            throw new AnalyzeBillException("无法获取卡号");
        }
    }

    protected String getValueByPattern(String key, String content, String ruleValue,
            AnalyzeParamsModel apm, String splitSign) {

        if (StringUtils.hasText(ruleValue)) {

            List<String> list = PatternMatcherUtil.getMatcher(ruleValue, content);
            if (list.isEmpty()) {
                // handleNotMatch(key, rules.getDueDate(), apm);
                return "";
            }
            String result = list.get(0);
            if ("".equals(splitSign)) {
                return result;
            } else {
                String[] sa = result.split(splitSign);
                String value = sa[sa.length - 1];
                return value;
            }

        }
        return "";
    }

    protected void handleNotMatch(String key, String reg, AnalyzeParamsModel apm) {
        apm.setResult(null);
        throw new RuntimeException(String.format("未找到匹配值,bank=%s,cardType=%s,key=%s,reg=%s",
                cardType.getBankCode().getBankCode(), cardType.getCardCode(), key, reg));
    }

    @Transactional
    protected void handleResultInternal(AnalyzeParamsModel apm) {
        Long emailId = apm.getEmailId();
        CreditCard card = apm.getResult().getCard();
        Long cardId = creditCardService.saveOrUpateCreditCard(card);
        // 解析成功，保存账单到数据库
        CreditBill bill = apm.getResult().getBill();
        // 设置卡id
        bill.setCardId(cardId);
        List<CreditBillDetail> billDetails = apm.getResult().getDetail();
        Long billId = null;
        if (bill != null) {
            bill.setEmailId(emailId);
            bill.setSentDate(apm.getSentDate());
            billId = creditBillService.saveOrUpdateCreditBill(bill);
            // billId = bill.getId();
        }

        if (billDetails != null && !billDetails.isEmpty()) {
            for (CreditBillDetail creditBillDetail : billDetails) {
                try {
                    if (billId == null) {
                        bill.setSentDate(creditBillDetail.getTransactionDate());
                        bill = creditBillService.findCreditBillByTransDate(bill);
                        if (bill == null) {
                            logger.warn("未查询到明细对应的账单,result={}", apm);
                            throw new RuntimeException("未查询到明细对应的账单");
                        }
                    }
                    creditBillDetail.setBillId(billId);
                    creditBillDetailService.saveCreditBillDetail(creditBillDetail);
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
                    detailMap.put(Integer.parseInt(rules.getTransactionDate()), "transactionDate");
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
            if (StringUtils.hasText(rules.getBillingDate())) {
                try {
                    detailMap.put(Integer.parseInt(rules.getBillingDate()), "billingDate");
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
            if (StringUtils.hasText(rules.getTransactionDescription())) {
                try {
                    detailMap.put(Integer.parseInt(rules.getTransactionDescription()),
                            "transactionDescription");
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
            if (StringUtils.hasText(rules.getTransactionCurrency())) {
                try {
                    detailMap.put(Integer.parseInt(rules.getTransactionCurrency()),
                            "transactionCurrency");
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
            if (StringUtils.hasText(rules.getTransactionAmount())) {
                try {
                    detailMap.put(Integer.parseInt(rules.getTransactionAmount()),
                            "transactionAmount");
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
            if (StringUtils.hasText(rules.getAccountableAmount())) {
                try {
                    detailMap.put(Integer.parseInt(rules.getAccountableAmount()),
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
        // 从缓存中找模板
        // rules = JedisClusterUtils.getBean(Constant.redisTemplateRuleCache +
        // cardCode,
        // CreditTemplate.class);
        rules = TemplateCache.templateCache.get(cardCode);

    }

    /**
     *
     * @Title: setCard
     * @Description: 设置行用卡
     * @param card
     * @param bill
     * @param apm
     * @return void 返回类型 @throws
     */
    protected void setCard(CreditCard card, CreditBill bill, AnalyzeParamsModel apm) {

        card.setBankId(new Long(apm.getBankId()));
        // 查询
        // CreditCard tempCard = creditCardService.findCreditCard(card);

        card.setCash(String.valueOf(bill.getCash()));
        card.setCredits(String.valueOf(bill.getCredits()));

        // creditCardService.saveOrUpateCreditCard(card);
    }

    /**
     *
     * @Title: setCardNumbers @Description: 卡号
     * @param card
     * @param number
     * @return void 返回类型 @throws
     */
    protected void setCardNumbers(CreditCard card, String number) {
        if (StringUtils.hasText(rules.getCardNumbers())) {
            try {
                int n = Integer.parseInt(rules.getCardNumbers());
                String[] detailArray = number.split(" ");
                card.setNumbers(detailArray[n]);
            } catch (Exception e) {

            }
        }
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
                    field = CreditBillDetail.class.getDeclaredField(detailMap.get(i));
                    if (field.getType() == Date.class) {
                        ReflectionUtils.setField(field, cbd, DateUtil.parseDate(detailArray[i]));
                    } else {
                        ReflectionUtils.setField(field, cbd, detailArray[i]);
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }

            } else {
                ///
                setField(cbd, i, detailArray[i]);
            }

        }
        return cbd;

    }

    protected void setField(CreditBillDetail cbd, int index, String value) {

    }
}
