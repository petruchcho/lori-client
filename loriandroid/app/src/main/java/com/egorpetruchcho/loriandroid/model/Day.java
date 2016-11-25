package com.egorpetruchcho.loriandroid.model;


import com.egorpetruchcho.loriandroid_api.model.TimeEntry;

import org.apache.commons.lang3.time.DateUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Day {
    private final Date date;
    private final List<TimeEntry> timeEntries = new ArrayList<>();

    private BigDecimal countOfHours = BigDecimal.ZERO;
    private int countOfMinutes = 0;

    public Day(Date date) {
        this.date = date;
    }

    public void addTimeEntry(TimeEntry timeEntry) {
        timeEntries.add(timeEntry);
        countOfMinutes += timeEntry.getTimeInMinutes();
        countOfHours = countOfHours.add(timeEntry.getTimeInHours());
    }

    public boolean isValidTimeEntry(TimeEntry timeEntry) {
        return DateUtils.isSameDay(getDate(), timeEntry.getDate());
    }

    public List<TimeEntry> getTimeEntries() {
        return new ArrayList<>(timeEntries);
    }

    public Date getDate() {
        return date;
    }

    public int getCountOfMinutes() {
        return countOfMinutes;
    }

    public double getCountOfHours() {
        return countOfHours.doubleValue();
    }

    public void clean() {
        timeEntries.clear();
        countOfMinutes = 0;
        countOfHours = BigDecimal.ZERO;
    }
}
