package com.egorpetruchcho.loriandroid.model;

import com.egorpetruchcho.loriandroid.utils.DateUtils;

import java.util.Calendar;
import java.util.Date;

public class Week {
    private static final int COUNT_OF_DAYS_IN_WEEK = 7;

    private final Date startDate;
    private final Date endDate;

    private final Day[] days;

    public Week(Date date) {
        Calendar curCalendar = DateUtils.getCurrentCalendar();
        curCalendar.setTime(date);
        while (curCalendar.get(Calendar.DAY_OF_WEEK) != DateUtils.START_OF_WEEK) {
            curCalendar.add(Calendar.DAY_OF_MONTH, -1);
        }
        this.startDate = curCalendar.getTime();
        curCalendar.add(Calendar.DAY_OF_MONTH, COUNT_OF_DAYS_IN_WEEK - 1);
        this.endDate = curCalendar.getTime();

        this.days = initDays();
    }


    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Week getKthWeek(int k) {
        Calendar curCalendar = DateUtils.getCurrentCalendar();
        curCalendar.setTime(getStartDate());
        curCalendar.add(Calendar.DAY_OF_MONTH, COUNT_OF_DAYS_IN_WEEK * k);
        return new Week(curCalendar.getTime());
    }

    private Day[] initDays() {
        Day[] days = new Day[COUNT_OF_DAYS_IN_WEEK];
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        for (int i = 0; i < COUNT_OF_DAYS_IN_WEEK; i++) {
            days[i] = new Day(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return days;
    }

    public Day[] getDays() {
        return days;
    }
}