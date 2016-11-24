package com.egorpetruchcho.loriandroid.utils;


import android.content.Context;
import android.support.annotation.StringRes;

import com.egorpetruchcho.loriandroid.model.Week;

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

    public static Calendar getCurrentCalendar() {
        return Calendar.getInstance();
    }

    public static String getString(Context context, Date date, @StringRes int resId) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(context.getString(resId));
        return simpleDateFormat.format(date);
    }

    public static String getRangeString(Context context, Date date1, Date date2, @StringRes int dateFormat, @StringRes int rangeFormat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(context.getString(dateFormat));
        return String.format(context.getString(rangeFormat), simpleDateFormat.format(date1), simpleDateFormat.format(date2));
    }
}
