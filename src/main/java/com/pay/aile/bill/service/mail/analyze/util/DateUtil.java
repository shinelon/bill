package com.pay.aile.bill.service.mail.analyze.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * 
 * @author Charlie
 * @description
 */
public class DateUtil {

    private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

    private static String defaultDatePattern = "yyyy-MM-dd";
    private static String defaultDateTimePattern = "yyyy-MM-dd HH:mm:ss";

    public static Date parseDateFromString(String date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(
                StringUtils.hasText(pattern) ? pattern : defaultDatePattern);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            logger.error("parse date error", e);
            return null;
        }
    }

    public static String formatDate(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(
                StringUtils.hasText(pattern) ? pattern : defaultDatePattern);
        return sdf.format(date);
    }

    public static String formatDateToDefaultPattern(String date,
            String original) {
        Date d = parseDateFromString(date, original);
        if (d == null) {
            return date;
        } else {
            return formatDate(d, defaultDatePattern);
        }
    }

    public static Date parseDateTimeFromString(String date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(StringUtils.hasText(pattern)
                ? pattern : defaultDateTimePattern);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            logger.error("parse date error", e);
            return null;
        }
    }

    public static String formatDateTime(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(StringUtils.hasText(pattern)
                ? pattern : defaultDateTimePattern);
        return sdf.format(date);
    }

    public static String formatDateTimeToDefaultPattern(String date,
            String original) {
        Date d = parseDateFromString(date, original);
        if (d == null) {
            return date;
        } else {
            return formatDate(d, defaultDateTimePattern);
        }
    }
}
