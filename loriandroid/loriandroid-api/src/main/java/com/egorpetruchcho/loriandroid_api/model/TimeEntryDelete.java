package com.egorpetruchcho.loriandroid_api.model;


import java.util.ArrayList;
import java.util.List;

public class TimeEntryDelete {

    private List<TimeEntryDelete.TimeEntry> removeInstances = new ArrayList<>();
    private final boolean softDeletion = true;

    public TimeEntryDelete(String id) {
        removeInstances.add(new TimeEntry(id));
    }

    private class TimeEntry {
        private final String id;

        private TimeEntry(String id) {
            this.id = id;
        }
    }
}
