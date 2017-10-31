package com.pay.aile.bill.service.mail.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pay.aile.bill.service.mail.BaseMailOperation;
import com.pay.aile.bill.service.mail.DownloadMail;
import com.pay.aile.bill.utils.MailTypeUtil;
import com.pay.aile.bill.utils.SpringContextUtil;

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

    @Override
    public void execute(final String mailAddr, final String password) throws Exception {
        String[] mailParms = StringUtils.split(mailAddr, "@");
        BaseMailOperation mailOperation = (BaseMailOperation) SpringContextUtil
                .getBean(MailTypeUtil.getMailType(mailParms[1]).getClzz());
        mailOperation.downloadMail(mailAddr, password);

    }

}
