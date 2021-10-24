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

    public static String getFormattedTimeFromSec(int sumSecond){
        if(sumSecond <= 0){
            return "00:00:00";
        }
        int h = sumSecond/3600;
        int m = (sumSecond-h*3600)/60;
        int s = sumSecond - h*3600-m*60;
        String time = "%02d:%02d:%02d";
        time = String.format(time,h,m,s);
        return time;
    }
}
