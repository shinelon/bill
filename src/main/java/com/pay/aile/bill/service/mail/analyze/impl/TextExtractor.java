package com.pay.aile.bill.service.mail.analyze.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import com.pay.aile.bill.service.mail.analyze.MailContentExtractor;
import com.pay.aile.bill.service.mail.analyze.util.TextExtract;

/**
 * 
 * @author Charlie
 * @description
 */
@Service
public class TextExtractor implements MailContentExtractor {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean support(String suffix) {
        if (!StringUtils.hasText(suffix) || !"html".equalsIgnoreCase(suffix)
                || !"txt".equalsIgnoreCase(suffix)) {
            return false;
        }
        return true;
    }

    @Override
    public List<String> extract(InputStream is) {
        String content = "";
        try {
            content = StreamUtils.copyToString(is, Charset.forName("utf-8"));
            return new TextExtract().parse(content);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return new ArrayList<String>();
        }
    }

}
