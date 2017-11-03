package com.pay.aile.bill.service.mail.analyze.banktemplate;

import org.springframework.beans.factory.InitializingBean;

import com.pay.aile.bill.entity.CreditTemplate;
import com.pay.aile.bill.service.mail.analyze.BankMailAnalyzerTemplate;
import com.pay.aile.bill.service.mail.analyze.enums.CardTypeEnum;
import com.pay.aile.bill.service.mail.analyze.model.AnalyzeParamsModel;

/**
 * 
 * @author Charlie
 * @description 卡种解析基础模板
 */
public abstract class BaseBankTemplate implements BankMailAnalyzerTemplate,
        Comparable<BaseBankTemplate>, InitializingBean {

    /**
     * 统计每一种模板的调用次数
     * 用于不同卡种之间的排序,调用次数高的排位靠前
     */
    private volatile int count;
    /**
     * 模板解析邮件时需要的关键字及对应的规则
     * key:到期还款日/应还款金额.eg
     * value:规则
     * 根据银行和信用卡类型,从缓存中初始化
     */
    protected CreditTemplate rules;

    /**
     * 信用卡类型
     * 由子类去初始化自己是什么信用卡类型
     */
    protected CardTypeEnum cardType;

    /**
     * 用于不同卡种之间的排序,调用次数高的排位靠前
     */
    @Override
    public int compareTo(BaseBankTemplate o) {
        if (o == null)
            return 1;
        return count > o.count ? 1 : -1;
    }

    @Override
    public void analyze(AnalyzeParamsModel apm) {
        count++;
        initRules();
        analyzeInternal(apm);
    }

    protected void analyzeInternal(AnalyzeParamsModel apm) {

    }

    /**
     * 获取模板对应的关键字
     */
    protected void initRules() {
        //        //TODO 根据cardCode从缓存中获取对应的规则
        //        String cardCode = cardType.getCardCode();
        //        rules = JedisClusterUtils.getBean(
        //                Constant.redisTemplateRuleCache + cardCode,
        //                CreditTemplate.class);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        setCardType();
    }

    /**
     * 设置信用卡类型
     */
    protected void setCardType() {

    }

}
