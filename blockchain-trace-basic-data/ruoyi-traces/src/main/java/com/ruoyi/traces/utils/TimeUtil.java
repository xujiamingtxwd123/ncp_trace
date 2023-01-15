package com.ruoyi.traces.utils;

import java.text.SimpleDateFormat;

public class TimeUtil {
//    public static String getSecondTimestamp(){
//        Date date = new Date();
//        String timestamp = String.valueOf(date.getTime()/1000);
//        return timestamp;
//    }

    public static String getMillSecondTimestamp(){
        return String.valueOf(System.currentTimeMillis());
    }

    public static String millSecondTimestampToFormat(String millSecondTimestamp) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(Long.parseLong(millSecondTimestamp));
    }

    public static String millSecondTimestampToFormat() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(Long.parseLong(getMillSecondTimestamp()));
    }


}
