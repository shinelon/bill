package com.pay.aile.bill.service.mail.analyze.extract;

import org.springframework.stereotype.Service;

import com.pay.aile.bill.service.mail.analyze.MailContentExtractor;

/**
 * 
 * @author Charlie
 * @description
 */
@Service
public class PdfExtractor implements MailContentExtractor {

    @Override
    public boolean support(String suffix) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String extract(String content) {
        // TODO Auto-generated method stub
        return null;
    }

}