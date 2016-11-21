package com.egorpetruchcho.loriandroid_api.model;

import com.egorpetruchcho.loriandroid_api.utils.DateUtils;

import java.util.Calendar;
import java.util.Date;

public class Week {
    private final Date startDate;
    private final Date endDate;

    public Week(Date startDate) {
        Calendar curCalendar = DateUtils.getCurrentCalendar();
        curCalendar.setTime(startDate);
        while (curCalendar.get(Calendar.DAY_OF_WEEK) != DateUtils.START_OF_WEEK) {
            curCalendar.add(Calendar.DAY_OF_MONTH, -1);
        }
        this.startDate = curCalendar.getTime();
        curCalendar.add(Calendar.DAY_OF_MONTH, 6);
        this.endDate = curCalendar.getTime();
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
        curCalendar.add(Calendar.DAY_OF_MONTH, 7 * k);
        return new Week(curCalendar.getTime());
    }
}