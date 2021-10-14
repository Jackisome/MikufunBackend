package com.devteam.mikufunbackend.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Jackisome
 * @date 2021/10/14
 */
public class TimeUtil {
    public static String getFormattedTimeToDay(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }
}
