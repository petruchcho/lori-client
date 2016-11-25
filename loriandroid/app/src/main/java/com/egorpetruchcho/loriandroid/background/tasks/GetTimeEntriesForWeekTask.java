package com.egorpetruchcho.loriandroid.background.tasks;


import com.egorpetruchcho.loriandroid.background.results.TimeEntriesResult;
import com.egorpetruchcho.loriandroid.model.Week;

import java.util.Date;

public class GetTimeEntriesForWeekTask extends GetTimeEntriesTask {

    private final Week week;

    public GetTimeEntriesForWeekTask(Week week) {
        super();
        this.week = week;
    }

    @Override
    protected Date getStartDate() {
        return week.getStartDate();
    }

    @Override
    protected Date getEndDate() {
        return week.getEndDate();
    }
}
