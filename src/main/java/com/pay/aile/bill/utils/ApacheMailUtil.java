package com.pay.aile.bill.utils;

import java.util.UUID;

import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.mail.util.MimeMessageParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pay.aile.bill.entity.CreditEmail;
import com.pay.aile.bill.entity.EmailFile;
import com.pay.aile.bill.enums.DwonloadMailType;
import com.pay.aile.bill.exception.MailBillException;

/***
 * ApacheMailUtil.java
 *
 * @author shinelon
 *
 * @date 2017年11月3日
 *
 */
public class ApacheMailUtil {
    private static final Logger logger = LoggerFactory.getLogger(ApacheMailUtil.class);

    public static EmailFile getEmailFile(Message message, CreditEmail creditEmail)
            throws MailBillException, MessagingException {
        EmailFile emailFile = new EmailFile();
        String fileName = UUID.randomUUID().toString();
        MimeMessage msg = (MimeMessage) message;
        String subject = MailDecodeUtil.getSubject(msg);
        String receiveAdd = MailDecodeUtil.getReceiveAddress(msg, null);
        String senderAdd = MailDecodeUtil.getFrom(msg);
        String sentDate = MailDecodeUtil.getSentDate(msg, "yyyyMMddHHmmss");
        logger.debug("subject:{} receiveAdd:{} senderAdd:{} sentData:{}", subject, receiveAdd, senderAdd, sentDate);
        emailFile.setSubject(message.getSubject());
        emailFile.setSentDate(sentDate);
        // 随机生成文件名
        emailFile.setFileName(fileName);
        emailFile.setEmail(creditEmail.getEmail());
        emailFile.setMailType(DwonloadMailType.HTML.toString());
        emailFile = getContentAndAttachment(msg, emailFile);
        return emailFile;
    }

    /**
     * 获取内容和附件，支持一个PDF附件，若有PDF则修改mailType
     * 
     * @param message
     * @param emailFile
     * @return
     */
    private static EmailFile getContentAndAttachment(MimeMessage message, EmailFile emailFile) {
        StringBuffer rethtmlContent = new StringBuffer(20480);
        long startTime = System.currentTimeMillis();
        MimeMessageParser parser = new MimeMessageParser(message);
        try {
            parser.parse();
            rethtmlContent.append(MailDecodeUtil.getUtf8(new StringBuffer(parser.getHtmlContent())));
            if (parser.hasAttachments()) {
                if (parser.getAttachmentList().size() > 1) {
                    logger.error("不支持多个附件！！！！！！");
                }
                for (DataSource dataSource : parser.getAttachmentList()) {
                    if (dataSource.getContentType().equals("application/pdf")) {
                        emailFile.setAttachment(PDFReader.readContent(dataSource.getInputStream()));
                        emailFile.setMailType(DwonloadMailType.PDF.toString());
                    }
                }
            }
        } catch (Exception e) {
            logger.error("downLoadAttachment error");
            logger.error(e.getMessage(), e);
        }
        emailFile.setContent(rethtmlContent.toString());
        long endTime = System.currentTimeMillis();

        logger.debug("=======format String time:{},length:{}", endTime - startTime, rethtmlContent.length());

        return emailFile;
    }
}
