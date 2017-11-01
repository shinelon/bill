package com.pay.aile.bill.service.mail.analyze.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author Charlie
 * @description 获取内容
 */
public class PatternMatcherUtil {
    /**
     * 
     * @param reg
     * @param content
     * @return
     * @description 获取匹配的字符串
     */
    public static List<String> getMatcher(String key, String bank, String reg,
            String content) {
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(content);
        List<String> result = new ArrayList<String>();
        while (matcher.find()) {
            result.add(matcher.group(0));
        }
        return result;
    }
}
