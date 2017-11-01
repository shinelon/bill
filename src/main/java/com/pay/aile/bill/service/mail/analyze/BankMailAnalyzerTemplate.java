package com.pay.aile.bill.service.mail.analyze;

/**
 * 
 * @author Charlie
 * @description 银行不同卡对应的解析模板
 * BankMailAnalyzer中使用具体的卡种对应模板进行解析账单内容
 */
public interface BankMailAnalyzerTemplate {

    /**
     * 
     * @param content
     * 解析账单内容
     */
    public void analyze(String content);
}
