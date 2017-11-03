package com.pay.aile.bill.utils;

import java.io.IOException;

import javax.activation.DataSource;
import javax.mail.internet.MimeMessage;

import org.apache.commons.mail.util.MimeMessageParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public static StringBuffer downLoadAttachment(MimeMessageParser parser) throws IOException {
        StringBuffer content = new StringBuffer(20480);
        long startTime = System.currentTimeMillis();
        for (DataSource dataSource : parser.getAttachmentList()) {
            if (dataSource.getContentType().equals("application/pdf")) {
                content.append(PDFReader.readContent(dataSource.getInputStream()));
            }
        }
        long endTime = System.currentTimeMillis();

        logger.debug("=============downLoadAttachment time:{},length:{}", endTime - startTime, content.length());
        return content;
    }

    public static StringBuffer getContent(final MimeMessage message) {
        StringBuffer rethtmlContent = new StringBuffer(20480);
        long startTime = System.currentTimeMillis();
        MimeMessageParser parser = new MimeMessageParser(message);
        try {
            parser.parse();
            rethtmlContent.append(MailDecodeUtil.getUtf8(new StringBuffer(parser.getHtmlContent())));
            // logger.debug(rethtmlContent.toString());
            if (parser.hasAttachments()) {
                downLoadAttachment(parser);
                // rethtmlContent.append(downLoadAttachment(parser,
                // rethtmlContent));
            }
        } catch (Exception e) {
            logger.error("downLoadAttachment error");
            logger.error(e.getMessage(), e);
        }
        long endTime = System.currentTimeMillis();

        logger.debug("=======format String time:{},length:{}", endTime - startTime, rethtmlContent.length());
        return rethtmlContent;
    }

}
