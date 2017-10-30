package com.pay.aile.bill.service.mail.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pay.aile.bill.enums.MailType;
import com.pay.aile.bill.service.mail.DownloadMail;
import com.pay.aile.bill.service.mail.MailOperation;
import com.pay.aile.bill.utils.MailTypeUtil;

/***
 * DownloadMailImpl.java
 *
 * @author shinelon
 *
 * @date 2017年10月30日
 *
 */
@Service
public class DownloadMailImpl implements DownloadMail {

    private static final Logger logger = LoggerFactory.getLogger(DownloadMailImpl.class);

    private static Map<MailType, Class<? extends MailOperation>> mailOperationMap = new HashMap<>(32);
    static {
        mailOperationMap.put(MailType.Mail_126, Mail126OperationImpl.class);
    }

    @Override
    public void execute(final String mailAddr, final String password) throws Exception {
        String[] mailParms = StringUtils.split(mailAddr, "@");
        Class<? extends MailOperation> clzz = mailOperationMap.get(MailTypeUtil.getMailType(mailParms[1]));
        MailOperation mailOperation = clzz.newInstance();
        mailOperation.downloadMail(mailAddr, password);

    }

}
