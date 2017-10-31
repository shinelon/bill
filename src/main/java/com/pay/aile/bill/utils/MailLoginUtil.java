package com.pay.aile.bill.utils;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pay.aile.bill.exception.MailBillException;

/***
 * MailLoginUtil.java
 *
 * @author shinelon
 *
 * @date 2017年10月31日
 *
 */
public class MailLoginUtil {
    private static final Logger logger = LoggerFactory.getLogger(MailLoginUtil.class);

    /***
     * 自定义登录页面 注意使用后是否store
     *
     * @param mailAddrWithoutSuffix
     * @param password
     * @return
     * @throws MailBillException
     */
    public static Store login(final String pop3ServerAddr, final String mailAddrWithoutSuffix, final String password)
            throws MailBillException {
        logger.info("begin landing user mail:{}", mailAddrWithoutSuffix);
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "pop3"); // 协议
        props.setProperty("mail.pop3.port", "110"); // 端口
        props.setProperty("mail.pop3.host", pop3ServerAddr);// 服务器地址
        // 创建Session实例对象
        Session session = Session.getInstance(props);
        // 创建pop3协议的Store对象
        try {
            Store store = session.getStore("pop3");
            store.connect(mailAddrWithoutSuffix, password);
            boolean connected = store.isConnected();
            logger.info("mail:{} \tis connected:{}", mailAddrWithoutSuffix, connected);
            if (!connected) {
                throw new MailBillException("");
            }
            return store;
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
            throw new MailBillException("");
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new MailBillException("");
        }
    }

}
