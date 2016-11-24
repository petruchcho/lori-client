package com.egorpetruchcho.loriandroid_api.utils;


import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    private DateUtils() {
    }

    public static String apiString(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }
}
