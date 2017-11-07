package com.pay.aile.bill.utils;

import javax.mail.Message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.pay.aile.bill.entity.CreditEmail;
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
    @Autowired
    private CreditFileRelation creditFileRelation;

    @Autowired
    private MongoTemplate mongoTemplate;

    public String getFile(String fileName) throws MailBillException {

        try {

            Criteria criteria = new Criteria("fileName");
            criteria.is(fileName);
            Query query = new Query(criteria);
            EmailFile ef = mongoTemplate.findOne(query, EmailFile.class);
            return ef.getContent();
        } catch (Exception e) {

            logger.error(e.getMessage());
            throw new MailBillException(e.getMessage());
        }

    }

    public String saveFile(Message message, CreditEmail creditEmail) throws MailBillException {
        EmailFile emailFile = new EmailFile();
        try {
            emailFile = ApacheMailUtil.getEmailFile(message, creditEmail);
            mongoTemplate.insert(emailFile);
            // 保存文件关系
            CreditFile creditFile = new CreditFile();
            creditFile.setEmailId(creditEmail.getId());
            creditFile.setFileName(emailFile.getFileName());
            creditFile.setSubject(emailFile.getSubject());
            creditFile.setMailType(emailFile.getMailType());
            creditFile.setSentDate(message.getSentDate());
            creditFile.setProcessResult(0);
            creditFileRelation.saveCreditFile(creditFile);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return emailFile.getFileName();

    }
}
