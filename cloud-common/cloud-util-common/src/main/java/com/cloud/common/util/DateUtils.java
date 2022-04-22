package com.cloud.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author 60003404
 * @Description TODO
 * @Date 2022/4/22 11:31
 * @Version 1.0
 */

public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    private static final Logger log = LoggerFactory.getLogger(DateUtils.class);

    public static final DateTimeFormatter FORMAT_DAY = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter FORMAT_DAY2 = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static final DateTimeFormatter FORMAT_DAY_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};
    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }
}
