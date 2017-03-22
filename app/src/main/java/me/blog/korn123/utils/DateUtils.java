package me.blog.korn123.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by CHO HANJOONG on 2016-09-28.
 */
public class DateUtils {

    public static final int HOURS_24 = 24;

    public static final int MINUTES_60 = 60;

    public static final int SECONDS_60 = 60;

    public static final int MILLI_SECONDS_1000 = 1000;

    private static final int UNIT_HEX = 16;

    /** Date pattern */
    public static final String DATE_PATTERN_DASH = "yyyy-MM-dd";

    /** Time pattern */
    public static final String TIME_PATTERN = "HH:mm";

    /** Date Time pattern */
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /** Date HMS pattern */
    public static final String DATE_HMS_PATTERN = "yyyyMMddHHmmss";

    /** Time stamp pattern */
    public static final String TIMESTAMP_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";

    /** year pattern (yyyy)*/
    public static final String YEAR_PATTERN = "yyyy";

    /** month pattern (MM) */
    public static final String MONTH_PATTERN = "MM";

    /** day pattern (dd) */
    public static final String DAY_PATTERN = "dd";

    /** date pattern (yyyyMMdd) */
    public static final String DATE_PATTERN = "yyyyMMdd";

    /** hour, minute, second pattern (HHmmss) */
    public static final String TIME_HMS_PATTERN = "HHmmss";

    /** hour, minute, second pattern (HH:mm:ss) */
    public static final String TIME_HMS_PATTERN_COLONE = "HH:mm:ss";

    public static String getCurrentDateAsString() {
        return getCurrentDateAsString(DATE_PATTERN_DASH);
    }

    public static String getCurrentDateAsString(String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.format(new Date());
    }

    public static void main(String[] args) {
        System.out.println(getCurrentDateTime());
    }

    public static String getCurrentDateTime() {
        return getCurrentDateTime(DATE_TIME_PATTERN);
    }

    public static String getCurrentDateTime(String pattern) {
        DateTime dt = new DateTime();
        DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
        return fmt.print(dt);
    }

}
