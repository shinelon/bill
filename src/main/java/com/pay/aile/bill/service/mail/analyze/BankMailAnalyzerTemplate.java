package com.pay.aile.bill.service.mail.analyze;

/**
 * 
 * @author Charlie
 * @description 银行不同卡对应的解析模板
 */
public interface BankMailAnalyzerTemplate {

    /**
     * 
     * @param content
     */
    public void analyze(String content);
}
