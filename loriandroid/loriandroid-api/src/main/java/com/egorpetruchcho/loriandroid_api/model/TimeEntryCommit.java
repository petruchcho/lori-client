package com.egorpetruchcho.loriandroid_api.model;


import com.egorpetruchcho.loriandroid_api.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TimeEntryCommit {

    private List<TimeEntryCommit.TimeEntry> commitInstances = new ArrayList<>();

    public TimeEntryCommit(Date date, String taskId, int timeInMinutes, User user) {
        commitInstances.add(new TimeEntry(date, taskId, timeInMinutes, user));
    }

    private class TimeEntry {

        private final String id = "NEW-ts$TimeEntry";
        private final String status = "new";
        private final String date;
        private final Task task;
        private final int timeInMinutes;
        private final double timeInHours;
        private final User user;

        TimeEntry(Date date, String taskId, int timeInMinutes, User user) {
            this.date = DateUtils.apiString(date);
            this.task = new Task(taskId);
            this.timeInMinutes = timeInMinutes;
            this.timeInHours = timeInMinutes / 60.0;
            this.user = user;
        }

        class Task {
            private String id;

            Task(String id) {
                this.id = id;
            }
        }
    }
}
