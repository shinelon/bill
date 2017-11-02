package com.pay.aile.bill.service.mail.analyze.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pay.aile.bill.service.mail.analyze.BankMailAnalyzer;
import com.pay.aile.bill.service.mail.analyze.IParseMail;
import com.pay.aile.bill.service.mail.analyze.MailContentExtractor;
import com.pay.aile.bill.service.mail.analyze.model.AnalyzeParamsModel;

/**
 * 
 * @author Charlie
 * @description 
 */
@Service("parseMail")
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
        File file = new File("D:\\中信银行信用卡电子账单1.html");
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String name = file.getName();//文件内容
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
            throw new RuntimeException("no extractors found");
        }
        //TODO 读取文件
        String content = extractor.extract(fis);
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
            throw new RuntimeException("no parsers found");
        }
        //解析
        try {
            //TODO 组装AnalyzeParamsModel
            AnalyzeParamsModel apm = new AnalyzeParamsModel();
            apm.setContent(content);
            parser.analyze(apm);
        } catch (Exception e) {
            //TODO 解析错误,发送信息告知
            logger.error(e.getMessage());
        }
    }

}
