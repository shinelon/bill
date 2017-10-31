package com.pay.aile.bill.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.mail.Message;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pay.aile.bill.exception.MailBillException;

/***
 * DownloadUtil.java
 *
 * @author shinelon
 *
 * @date 2017年10月30日
 *
 */
public class DownloadUtil {
    private static final Logger logger = LoggerFactory.getLogger(DownloadUtil.class);

    public static void createFile(String fileName, StringBuffer buffer) {
        File newFile = new File(fileName);
        if (newFile.exists()) {
            logger.info("文件[{}]已经存在！", fileName);
            return;
        }
        try {
            if (newFile.createNewFile()) {// 创建成功，则写入文件内容
                try (PrintWriter p = new PrintWriter(new FileOutputStream(newFile.getAbsolutePath()))) {
                    p.write(buffer.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                logger.info("创建文件：[{}]成功", newFile);
            } else {
                logger.info("创建文件：[{}]失败", newFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveFile(Message message) throws MailBillException {
        MimeMessage msg = (MimeMessage) message;
        String subject = MailDecodeUtil.getSubject(msg);
        String receiveAdd = MailDecodeUtil.getReceiveAddress(msg, null);
        String senderAdd = MailDecodeUtil.getFrom(msg);
        String sentDate = MailDecodeUtil.getSentDate(msg, "yyyyMMddHHmmss");
        logger.debug("subject:{} receiveAdd:{} senderAdd:{} sentData:{}", subject, receiveAdd, senderAdd, sentDate);
        StringBuffer content = new StringBuffer(20480);
        MailDecodeUtil.getMailTextContent(msg, content);
        content = MailDecodeUtil.getUtf8(content);
        createFile(formatFileName(subject, sentDate), content);
        logger.info("=========================================");
    }

    private static String formatFileName(String subject, String sentDate) {
        String fileNamePrefix = "C:\\Users\\syq\\Desktop\\downMailTest\\";
        StringBuffer fileNameBuffer = new StringBuffer(64);
        fileNameBuffer.append(fileNamePrefix);
        fileNameBuffer.append(StringUtils.remove(subject, ":"));
        fileNameBuffer.append('-');
        fileNameBuffer.append(sentDate);
        fileNameBuffer.append(".html");
        return fileNameBuffer.toString();
    }

}
