package com.pay.aile.bill.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.pay.aile.bill.entity.CreditFile;
import com.pay.aile.bill.entity.EmailFile;
import com.pay.aile.bill.exception.MailBillException;
import com.pay.aile.bill.service.mail.relation.CreditFileRelation;

/***
 * DownloadUtil.java
 *
 * @author shinelon
 *
 * @date 2017年10月30日
 *
 */
@Component
public class MongoDownloadUtil {
    private static final Logger logger = LoggerFactory.getLogger(MongoDownloadUtil.class);
    private static final String DOC_KEY_FILE_NAME = "fileName";

    @Autowired
    private CreditFileRelation creditFileRelation;

    @Autowired
    private MongoTemplate mongoTemplate;

    public String getFile(String fileName) throws MailBillException {

        try {

            Criteria criteria = new Criteria(DOC_KEY_FILE_NAME);
            criteria.is(fileName);
            Query query = new Query(criteria);
            EmailFile ef = mongoTemplate.findOne(query, EmailFile.class);
            return ef.getContent();
        } catch (Exception e) {

            logger.error(e.getMessage());
            throw new MailBillException(e.getMessage());
        }

    }

    public void saveCreditFile(List<CreditFile> creditFileList) {
        creditFileRelation.saveNotExitsCreditFile(creditFileList);
    }

    public void saveEmailFiles(List<EmailFile> emailFileList) {
        List<String> fileNames = emailFileList.stream().map(e -> e.getFileName()).collect(Collectors.toList());
        Criteria criteria = new Criteria(DOC_KEY_FILE_NAME);
        criteria.in(fileNames);
        Query query = new Query(criteria);
        List<EmailFile> exitsEmailFileList = mongoTemplate.find(query, EmailFile.class);
        List<String> exitsfileNames = exitsEmailFileList.stream().map(e -> e.getFileName())
                .collect(Collectors.toList());
        List<EmailFile> insertList = emailFileList.stream().filter(e -> !exitsfileNames.contains(e.getFileName()))
                .collect(Collectors.toList());
        if (insertList.size() > 0) {
            mongoTemplate.insert(insertList, EmailFile.class);
        }

    }

    public String saveFile(EmailFile emailFile, CreditFile creditFile) {

        mongoTemplate.save(emailFile);
        // 保存文件关系
        creditFileRelation.saveCreditFile(creditFile);
        return emailFile.getFileName();
    }

    public void saveFile(List<EmailFile> emailFileList, List<CreditFile> creditFileList) {
        saveEmailFiles(emailFileList);
        saveCreditFile(creditFileList);

    }
}
