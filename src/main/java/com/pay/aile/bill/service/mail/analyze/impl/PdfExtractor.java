package com.pay.aile.bill.service.mail.analyze.impl;

import java.io.InputStream;
import java.util.List;

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
    public List<String> extract(InputStream is) {
        // TODO Auto-generated method stub
        return null;
    }

}
