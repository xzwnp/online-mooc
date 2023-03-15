package com.example.commonutils;

import javax.swing.text.DateFormatter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * com.example.commonutils
 *
 * @author xiaozhiwei
 * 2023/3/15
 * 18:42
 */
public class DateTimeUtil {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String toDateTimeString(LocalDateTime localDateTime) {
        return localDateTime.format(formatter);
    }

    public static LocalDateTime parseDate(String dateTime) {
        return LocalDateTime.parse(dateTime, formatter);
    }
}
