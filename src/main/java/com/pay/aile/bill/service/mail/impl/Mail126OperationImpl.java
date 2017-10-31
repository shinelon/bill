package com.pay.aile.bill.service.mail.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pay.aile.bill.service.mail.BaseMailOperation;

/***
 * MailOperationImpl.java
 *
 * @author shinelon
 *
 * @date 2017年10月30日
 *
 */
@Service
public class Mail126OperationImpl extends BaseMailOperation {
    private static final Logger logger = LoggerFactory.getLogger(Mail126OperationImpl.class);

    /***
     * 获取搜索邮件关键字
     *
     * @return
     */
    @Override
    public String getKeywords() {
        return "中信银行";
    }

    @Override
    public String getPop3ServerAddr() {
        return "pop.126.com";
    }

}
