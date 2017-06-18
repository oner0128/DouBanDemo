package com.android.oner0128.doubandemo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by rrr on 2017/6/6.
 */

public class DateUtil {

    public static final String PATTERN_DATE = "MM月dd日";
    public static final String PATTERN_WEEKDAY = "EEEE";
    public static final String PATTERN_SPLIT = " ";
    public static final String PATTERN = PATTERN_DATE + PATTERN_SPLIT + PATTERN_WEEKDAY;

    public static String date2str(Date date) {
        return date2str(date, PATTERN);
    }

    public static String date2str(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
        return sdf.format(date);
    }

    public static Date str2date(String str) {
        return str2date(str, PATTERN);
    }

    public static Date str2date(String str, String format) {
        Date date = null;
        try {
            if (str != null)
            {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                date = sdf.parse(str);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    public static String getYesterday(String dateStr){
        Date date=str2date(dateStr,"yyyyMMdd");
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        date=calendar.getTime();
        return new SimpleDateFormat("yyyyMMdd").format(date);
    }
}
