package com.pay.aile.bill.service.mail.analyze.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pay.aile.bill.entity.CreditEmail;
import com.pay.aile.bill.entity.EmailFile;
import com.pay.aile.bill.exception.MailBillException;
import com.pay.aile.bill.service.CreditFileService;
import com.pay.aile.bill.service.mail.analyze.BankMailAnalyzer;
import com.pay.aile.bill.service.mail.analyze.IParseMail;
import com.pay.aile.bill.service.mail.analyze.MailContentExtractor;
import com.pay.aile.bill.service.mail.analyze.config.TemplateCache;
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
        List<CreditFileModel> fileList = creditFileService.findUnAnalyzedListByEmail(creditEmail);
        if (fileList == null || fileList.isEmpty()) {
            logger.info("未解析邮件账单为空");
            return;
        }
        executeParseFileList(fileList);

    }

    /**
     *
     * @Title: executeParseFile
     * @Description:解析单个文件
     * @param creditFile
     * @return void 返回类型 @throws
     */
    @Override
    public void executeParseFile(CreditFileModel creditFile) {
        // 解析
        Exception error = null;
        Long id = creditFile.getId();
        try {
            try {

                // String fileName = creditFile.getFileName();
                // String email = creditFile.getEmail();
                // Long emailId = creditFile.getEmailId();
                // Date sentDate = creditFile.getSentDate();// 邮件日期
                // String subject = creditFile.getSubject();// 邮件主题
                // String bankCode = getBankCode(subject);
                // String type = creditFile.getMailType();
                // MailContentExtractor extractor = null;
                // for (MailContentExtractor mailContentExtractor : extractors)
                // {
                // if (mailContentExtractor.support(type)) {
                // extractor = mailContentExtractor;
                // break;
                // }
                // }
                // if (extractor == null) {
                // throw new RuntimeException("no extractors found");
                // }
                // // 获取邮件内容，并做处理，默认是去除td里面的空格
                // String content = getFileContent(creditFile, extractor);
                // if (!StringUtils.hasText(content)) {
                // logger.error("extract error");
                // }
                // 获取解析器

                AnalyzeParamsModel apm = setModel(creditFile);
                BankMailAnalyzer parser = null;
                for (BankMailAnalyzer p : parsers) {
                    if (p.support(apm.getBankCode())) {
                        parser = p;
                        break;
                    }
                }
                if (parser == null) {
                    throw new RuntimeException("no parsers found");
                }

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
        } catch (

        Exception e) {
            logger.error(e.getMessage(), e);
        }

    }

    /**
     *
     * @Title: executeParseFileList
     * @Description:批量解析文件
     * @param fileList
     * @return void 返回类型 @throws
     */
    public void executeParseFileList(List<CreditFileModel> fileList) {
        fileList.forEach(creditFile -> {
            try {
                executeParseFile(creditFile);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        });
    }

    public String getFileContent(CreditFileModel creditFile, MailContentExtractor extractor)
            throws MailBillException {

        // 从mongodb中获取邮件内容
        String content = mongoDownloadUtil.getFile(creditFile.getFileName());
        // 邮件内容
        content = extractor.extract(content); // 解析文件

        return content;
    }

    /**
     *
     * @Title: setFileContent
     * @Description: 设置邮件需要解析的内容
     * @param creditFile
     * @param apm
     * @return void 返回类型 @throws
     */
    public void setFileContent(CreditFileModel creditFile, AnalyzeParamsModel apm) {

        try {
            // 从mongodb中获取邮件内容
            EmailFile emailFile = mongoDownloadUtil.getFile(creditFile.getFileName(),
                    creditFile.getEmail());
            apm.setAttachment(emailFile.getAttachment());
            apm.setContent(emailFile.getContent());
            logger.info(emailFile.getContent());
        } catch (MailBillException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private String getBankCode(String subject) {
        BankCodeEnum bank = BankCodeEnum.getByString(subject);
        if (bank == null) {
            throw new RuntimeException("未查到银行,name=" + subject);
        }
        return bank.getBankCode();
    }

    private AnalyzeParamsModel setModel(CreditFileModel creditFile) {
        AnalyzeParamsModel apm = new AnalyzeParamsModel();
        String subject = creditFile.getSubject();// 邮件主题
        String bankCode = getBankCode(subject);

        apm.setEmail(creditFile.getEmail());
        apm.setBankCode(bankCode);
        // 根据bankCode设置id
        apm.setBankId(String.valueOf(TemplateCache.bankCache.get(bankCode)));
        apm.setEmailId(creditFile.getEmailId());
        apm.setSentDate(creditFile.getSentDate());
        //
        setFileContent(creditFile, apm);
        return apm;
    }
}
