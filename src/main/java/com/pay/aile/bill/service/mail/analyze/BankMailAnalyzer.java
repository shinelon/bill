package com.pay.aile.bill.service.mail.analyze;

import java.util.List;

/**
 * 
 * @author Charlie
 * @description 银行账单解析器
 */
public interface BankMailAnalyzer {

    /**
     * 
     * @param name 文件名称
     * @return 
     * 根据一定规则(比如按照文件名是否包含该银行名称)判断是否支持解析
     */
    public boolean support(String name);

    /**
     * 
     * @param content
     * 解析邮件内容
     */
    public void analyze(List<String> content);
}
