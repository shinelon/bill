package com.pay.aile.bill.service.mail.analyze.extract;

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
public class PdfExtractor implements MailContentExtractor {

    @Override
    public boolean support(String suffix) {
        return StringUtils.hasText(suffix) && "pdf".equalsIgnoreCase(suffix);
    }

    @Override
    public String extract(String content) {
        return TextExtractUtil.parsePdf(content);
    }

}
