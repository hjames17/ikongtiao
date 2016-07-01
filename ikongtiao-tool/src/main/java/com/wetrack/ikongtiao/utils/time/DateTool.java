package com.wetrack.ikongtiao.utils.time;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by zhanghong on 16/6/15.
 */
public class DateTool {

    /**
     * 解析时间字符串， 并根据指定的时区来处理偏移
     * @param timeString
     * @param format
     * @param timeZone 时间所在的时区, 如果不提供，则直接按照系统时区解析
     * @return
     */
    public static Date parseWithTimezone(String timeString, String format, TimeZone timeZone) throws ParseException {

        Date date = DateUtils.parseDate(timeString, format);

        if(timeZone != null) {
            /**
             * DateUtils是根据当前系统时区来解析时间字符串的
             * 当前的系统时区和目标时间的时区如果不同的话，把两个时区的时间差加到date上
             */
            date = DateUtils.addMilliseconds(date, TimeZone.getDefault().getRawOffset() - timeZone.getRawOffset());
        }
        return date;
    }
}
