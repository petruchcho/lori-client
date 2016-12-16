package com.egorpetruchcho.loriandroid_api.model;

import java.math.BigDecimal;
import java.util.Date;

public class TimeEntry {

    private String id;

    private Task task;

    private String taskName;

    private int timeInMinutes;

    private BigDecimal timeInHours;

    private Date date;

    public String getId() {
        return id;
    }

    public String getTaskName() {
        if (taskName != null) {
            return taskName;
        }
        if (task != null) {
            return task.getName();
        }
        return null;
    }

    public String getProjectName() {
        if (task != null) {
            return task.getProjectName();
        }
        return null;
    }

    public int getTimeInMinutes() {
        return timeInMinutes;
    }

    public Date getDate() {
        return date;
    }

    public BigDecimal getTimeInHours() {
        return timeInHours;
    }
}
