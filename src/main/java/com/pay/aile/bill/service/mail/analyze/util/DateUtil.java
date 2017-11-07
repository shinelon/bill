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

	private static String defaultDatePattern = "yyyy-MM-dd";

	private static String defaultDateTimePattern = "yyyy-MM-dd HH:mm:ss";
	private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

	public static String formatDate(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(StringUtils.hasText(pattern) ? pattern : defaultDatePattern);
		return sdf.format(date);
	}

	public static String formatDateTime(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(StringUtils.hasText(pattern) ? pattern : defaultDateTimePattern);
		return sdf.format(date);
	}

	public static String formatDateTimeToDefaultPattern(String date, String original) {
		Date d = parseDateFromString(date, original);
		if (d == null) {
			return date;
		} else {
			return formatDate(d, defaultDateTimePattern);
		}
	}

	public static String formatDateToDefaultPattern(String date, String original) {
		Date d = parseDateFromString(date, original);
		if (d == null) {
			return date;
		} else {
			return formatDate(d, defaultDatePattern);
		}
	}

	public static Date parseDate(String datestr) {
		if (null == datestr || "".equals(datestr)) {
			return null;
		}
		try {
			String fmtstr = null;
			if (datestr.indexOf('-') > 0) {
				fmtstr = "yyyy-MM-dd";
			} else if (datestr.indexOf('年') > 0) {
				fmtstr = "yyyy年MM月dd日";
			} else if (datestr.indexOf('/') > 0) {
				fmtstr = "yyyy/MM/dd/";
			} else {
				return null;
			}
			SimpleDateFormat sdf = new SimpleDateFormat(fmtstr);
			return sdf.parse(datestr);
		} catch (Exception e) {
			return null;
		}
	}

	public static Date parseDateFromString(String date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(StringUtils.hasText(pattern) ? pattern : defaultDatePattern);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			logger.error("parse date error", e);
			return null;
		}
	}

	public static Date parseDateTimeFromString(String date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(StringUtils.hasText(pattern) ? pattern : defaultDateTimePattern);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			logger.error("parse date error", e);
			return null;
		}
	}

}
