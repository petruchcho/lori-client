package com.egorpetruchcho.loriandroid_api.model;

import java.util.Date;

public class TimeEntry {

    private String id;

    private String taskName;

    private String timeInMinutes;

    private Date date;

    public String getId() {
        return id;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTimeInMinutes() {
        return timeInMinutes;
    }

    public Date getDate() {
        return date;
    }
}
