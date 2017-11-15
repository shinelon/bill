package com.pay.aile.bill.service.mail.analyze.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Charlie
 * @description
 */
public class DateUtil {

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
                fmtstr = "yyyy/MM/dd";
            } else {
                fmtstr = "yyyyMMdd";
            }
            SimpleDateFormat sdf = new SimpleDateFormat(fmtstr);
            return sdf.parse(datestr);
        } catch (Exception e) {
            return null;
        }
    }
}
