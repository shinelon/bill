package com.pay.aile.bill.service.mail.analyze.banktemplate;

import java.util.List;

import com.pay.aile.bill.service.mail.analyze.BankMailAnalyzerTemplate;

/**
 * 
 * @author Charlie
 * @description 基础模板
 */
public abstract class BaseBankTemplate
        implements BankMailAnalyzerTemplate, Comparable<BaseBankTemplate> {

    /**
     * 统计每一种模板的调用次数
     */
    private volatile int count;

    @Override
    public int compareTo(BaseBankTemplate o) {
        if (o == null)
            return 1;
        return count > o.count ? 1 : -1;
    }

    @Override
    public void analyze(List<String> content) {
        count++;
        analyzeInternal(content);
    }

    protected void analyzeInternal(List<String> content) {

    }

}
