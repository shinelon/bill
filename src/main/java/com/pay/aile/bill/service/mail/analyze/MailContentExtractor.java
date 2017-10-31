package com.pay.aile.bill.service.mail.analyze;

import java.io.InputStream;
import java.util.List;

/**
 * 
 * @author Charlie
 * @description 邮件内容提取
 */
public interface MailContentExtractor {
    /**
     * 
     * @param suffix 文件后缀
     * @return 根据文件后缀判断是否支持
     */
    public boolean support(String suffix);

    /**
     * 
     * @param is
     * @return 提取邮件内容
     */
    public List<String> extract(InputStream is);
}
