package com.pay.aile.bill.service.mail.analyze.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pay.aile.bill.service.CreditFileService;
import com.pay.aile.bill.service.mail.analyze.BankMailAnalyzer;
import com.pay.aile.bill.service.mail.analyze.IParseMail;
import com.pay.aile.bill.service.mail.analyze.MailContentExtractor;
import com.pay.aile.bill.service.mail.analyze.model.AnalyzeParamsModel;
import com.pay.aile.bill.service.mail.analyze.model.CreditFileModel;
import com.pay.aile.bill.utils.MongoDownloadUtil;

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

    @Resource
    private CreditFileService creditFileService;

    @Autowired
    private MongoDownloadUtil mongoDownloadUtil;

    @Override
    public void execute() {
        List<CreditFileModel> fileList = creditFileService.findUnAnalyzedList();
        if (fileList == null || fileList.isEmpty()) {
            logger.info("未解析邮件账单为空");
            return;
        }
        fileList.forEach(creditFile -> {
            try {
                Long id = creditFile.getId();
                String fileName = creditFile.getFileName();
                String email = creditFile.getEmail();
                String bankCode = "";
                String suffix = fileName.substring(fileName.indexOf(".") + 1);
                String prefix = fileName.substring(0, fileName.indexOf("."));
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
                //从mongodb中获取邮件内容
                String content = mongoDownloadUtil.getFile(fileName);
                //邮件内容
                content = extractor.extract(content); //解析文件
                if (!StringUtils.hasText(content)) {
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
                Exception error = null;
                try {
                    AnalyzeParamsModel apm = new AnalyzeParamsModel();
                    apm.setEmail(email);
                    apm.setContent(content);
                    parser.analyze(apm);
                } catch (Exception e) {
                    //TODO 解析错误,发送信息告知
                    error = e;
                    logger.error(e.getMessage(), e);
                }
                if (error == null) {
                    //更新解析状态
                    creditFileService.updateProcessResult(1, id);
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        });
    }

}
