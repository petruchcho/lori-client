package com.egorpetruchcho.loriandroid.background.tasks;


import java.util.Date;

public class GetTimeEntriesForDayTask extends GetTimeEntriesTask {

    private final Date day;

    public GetTimeEntriesForDayTask(Date day) {
        super();
        this.day = day;
    }

    @Override
    protected Date getStartDate() {
        return day;
    }

    @Override
    protected Date getEndDate() {
        return day;
    }
}
