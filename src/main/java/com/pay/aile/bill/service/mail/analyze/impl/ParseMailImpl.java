package com.pay.aile.bill.service.mail.analyze.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pay.aile.bill.service.mail.analyze.BankMailAnalyzer;
import com.pay.aile.bill.service.mail.analyze.IParseMail;
import com.pay.aile.bill.service.mail.analyze.MailContentExtractor;

/**
 * 
 * @author Charlie
 * @description 
 */
@Service
public class ParseMailImpl implements IParseMail {
    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 邮件内容提取
     */
    @Autowired
    private List<MailContentExtractor> extractors;

    /**
     * 提取后的邮件内容解析
     */
    @Autowired
    private List<BankMailAnalyzer> parsers;

    @Override
    public void execute() {
        //TODO 获取文件/文件名称
        String name = "test.pdf";//文件内容
        String suffix = name.substring(name.indexOf(".") + 1);
        String prefix = name.substring(0, name.indexOf("."));
        MailContentExtractor extractor = null;
        for (MailContentExtractor mailContentExtractor : extractors) {
            if (mailContentExtractor.support(suffix)) {
                extractor = mailContentExtractor;
                break;
            }
        }
        if (extractor == null) {

        }
        //TODO 读取文件
        List<String> content = extractor.extract(System.in);
        if (content == null || content.isEmpty()) {
            logger.error("extract error");
        }
        BankMailAnalyzer parser = null;
        for (BankMailAnalyzer p : parsers) {
            if (p.support(prefix)) {
                parser = p;
                break;
            }
        }
        if (parser == null) {

        }
        //解析
        parser.analyze(content);
    }

}
