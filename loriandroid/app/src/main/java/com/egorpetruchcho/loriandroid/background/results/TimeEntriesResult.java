package com.egorpetruchcho.loriandroid.background.results;


import com.egorpetruchcho.loriandroid_api.model.TimeEntry;

import java.util.List;

public class TimeEntriesResult {
    public final List<TimeEntry> result;

    public TimeEntriesResult(List<TimeEntry> result) {
        this.result = result;
    }
}