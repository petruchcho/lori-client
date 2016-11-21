package com.egorpetruchcho.loriandroid_api.utils;


import com.egorpetruchcho.loriandroid_api.model.Week;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static final int START_OF_WEEK = Calendar.MONDAY;

    private DateUtils() {
    }

    public static Date getCurrentDate() {
        return new Date();
    }

    public static Week getCurrentWeek() {
        return new Week(getCurrentDate());
    }

    public static String apiString(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public static Calendar getCurrentCalendar() {
        return Calendar.getInstance();
    }


}
