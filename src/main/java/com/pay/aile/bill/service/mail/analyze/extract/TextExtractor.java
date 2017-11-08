package com.pay.aile.bill.service.mail.analyze.extract;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pay.aile.bill.service.mail.analyze.MailContentExtractor;
import com.pay.aile.bill.service.mail.analyze.util.TextExtractUtil;

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
        if (!StringUtils.hasText(suffix) || (!"html".equalsIgnoreCase(suffix)
                && !"txt".equalsIgnoreCase(suffix))) {
            return false;
        }
        return true;
    }

    @Override
    public String extract(String content) {
        try {
            return TextExtractUtil.parseHtml(content, "td");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

}
