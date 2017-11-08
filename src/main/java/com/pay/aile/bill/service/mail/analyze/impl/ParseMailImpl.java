package com.pay.aile.bill.service.mail.analyze.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pay.aile.bill.entity.CreditEmail;
import com.pay.aile.bill.service.CreditFileService;
import com.pay.aile.bill.service.mail.analyze.BankMailAnalyzer;
import com.pay.aile.bill.service.mail.analyze.IParseMail;
import com.pay.aile.bill.service.mail.analyze.MailContentExtractor;
import com.pay.aile.bill.service.mail.analyze.enums.BankCodeEnum;
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
    @Resource
    private CreditFileService creditFileService;
    /**
     * 邮件内容提取
     */
    @Autowired
    private List<MailContentExtractor> extractors;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MongoDownloadUtil mongoDownloadUtil;

    /**
     * 提取后的邮件内容解析
     */
    @Autowired
    private List<BankMailAnalyzer> parsers;

    @Override
    public void execute() {
        List<CreditFileModel> fileList = creditFileService.findUnAnalyzedList();
        if (fileList == null || fileList.isEmpty()) {
            logger.info("未解析邮件账单为空");
            return;
        }
        executeParseFileList(fileList);
    }

    @Override
    public void execute(CreditEmail creditEmail) {
        List<CreditFileModel> fileList = creditFileService
                .findUnAnalyzedListByEmail(creditEmail);
        if (fileList == null || fileList.isEmpty()) {
            logger.info("未解析邮件账单为空");
            return;
        }
        executeParseFileList(fileList);

    }

    public void executeParseFileList(List<CreditFileModel> fileList) {
        fileList.forEach(creditFile -> {
            try {
                Long id = creditFile.getId();
                String fileName = creditFile.getFileName();
                String email = creditFile.getEmail();
                Long emailId = creditFile.getEmailId();
                Date sentDate = creditFile.getSentDate();// 邮件日期
                String subject = creditFile.getSubject();// 邮件主题
                String bankCode = getBankCode(subject);
                String type = creditFile.getMailType();
                MailContentExtractor extractor = null;
                for (MailContentExtractor mailContentExtractor : extractors) {
                    if (mailContentExtractor.support(type)) {
                        extractor = mailContentExtractor;
                        break;
                    }
                }
                if (extractor == null) {
                    throw new RuntimeException("no extractors found");
                }
                // 从mongodb中获取邮件内容
                String content = mongoDownloadUtil.getFile(fileName);
                // 邮件内容
                content = extractor.extract(content); // 解析文件
                if (!StringUtils.hasText(content)) {
                    logger.error("extract error");
                }
                BankMailAnalyzer parser = null;
                for (BankMailAnalyzer p : parsers) {
                    if (p.support(bankCode)) {
                        parser = p;
                        break;
                    }
                }
                if (parser == null) {
                    throw new RuntimeException("no parsers found");
                }
                // 解析
                Exception error = null;
                try {
                    AnalyzeParamsModel apm = new AnalyzeParamsModel();
                    apm.setEmail(email);
                    apm.setContent(content);
                    apm.setBankCode(bankCode);
                    apm.setEmailId(emailId);
                    apm.setSentDate(sentDate);
                    parser.analyze(apm);
                } catch (Exception e) {
                    // TODO 解析错误,发送信息告知
                    error = e;
                    logger.error(e.getMessage(), e);
                }
                if (error == null) {
                    // 更新解析状态
                    creditFileService.updateProcessResult(1, id);
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        });
    }

    private String getBankCode(String subject) {
        BankCodeEnum bank = BankCodeEnum.getByString(subject);
        if (bank == null) {
            throw new RuntimeException("未查到银行,name=" + subject);
        }
        return bank.getBankCode();
    }
}
