package com.thoughtworks.homework.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtil {
    private static final SimpleDateFormat sFormat =
            new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
    public static String getCurrentTime(){
        long time = System.currentTimeMillis();
        Date date = new Date(time);
        return sFormat.format(date);
    }
}
