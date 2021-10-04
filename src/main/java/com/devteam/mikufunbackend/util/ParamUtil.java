package com.devteam.mikufunbackend.util;

import java.util.Collection;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * @author Jackisome
 * @date 2021/9/27
 */
public class ParamUtil {
    public static boolean isNotEmpty(String string) {
        return string != null && string.length() != 0;
    }

    public static boolean isNotEmpty(Collection collection) {
        return collection != null && collection.size() != 0;
    }

    public static boolean isLegalDate(String date) {
        String DATE_REGEX = "^((19|20)[0-9]{2})-((0?2-((0?[1-9])|([1-2][0-9])))|(0?(1|3|5|7|8|10|12)-((0?[1-9])|([1-2][0-9])|(3[0-1])))|(0?(4|6|9|11)-((0?[1-9])|([1-2][0-9])|30)))$";
        return Pattern.matches(DATE_REGEX, date);
    }

    public static boolean isLegalTime(String time) {
        String TIME_REGEX = "^([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";
        return Pattern.matches(TIME_REGEX, time);
    }

    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    public static boolean validateType(String type) {
        return (type != null) && (type.equals("mkv") || type.equals("mp4"));
    }
}
