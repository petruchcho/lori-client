package com.egorpetruchcho.loriandroid.state;


import com.egorpetruchcho.loriandroid_api.model.TimeEntry;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeEntriesProvider {

    private static TimeEntriesProvider instance;

    public static TimeEntriesProvider getInstance() {
        if (instance == null) {
            instance = new TimeEntriesProvider();
        }
        return instance;
    }

    private final Map<Date, List<TimeEntry>> timeEntriesByDateMap = new HashMap<>();

    private TimeEntriesProvider() {
    }
}
