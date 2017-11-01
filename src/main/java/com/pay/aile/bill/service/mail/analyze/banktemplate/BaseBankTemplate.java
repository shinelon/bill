package com.pay.aile.bill.service.mail.analyze.banktemplate;

import java.util.Map;

import com.pay.aile.bill.service.mail.analyze.BankMailAnalyzerTemplate;

/**
 * 
 * @author Charlie
 * @description 卡种解析基础模板
 */
public abstract class BaseBankTemplate
        implements BankMailAnalyzerTemplate, Comparable<BaseBankTemplate> {

    /**
     * 统计每一种模板的调用次数
     * 用于不同卡种之间的排序,调用次数高的排位靠前
     */
    private volatile int count;
    /**
     * 模板解析邮件时需要的关键字及对应的规则
     * key:到期还款日/应还款金额.eg
     * value:规则
     * TODO 如何初始化
     */
    protected Map<String, String> keywords;

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
    public void analyze(String content) {
        count++;
        initKeywords();
        analyzeInternal(content);
    }

    protected void analyzeInternal(String content) {

    }

    /**
     * 获取模板对应的关键字
     */
    protected void initKeywords() {

    }

}
