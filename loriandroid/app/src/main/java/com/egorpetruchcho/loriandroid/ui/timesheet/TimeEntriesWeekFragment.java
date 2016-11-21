package com.egorpetruchcho.loriandroid.ui.timesheet;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.egorpetruchcho.loriandroid.R;
import com.egorpetruchcho.loriandroid.background.results.TimeEntriesResult;
import com.egorpetruchcho.loriandroid.background.tasks.GetTimeEntriesTask;
import com.egorpetruchcho.loriandroid.core.LoriFragment;
import com.egorpetruchcho.loriandroid_api.model.TimeEntry;
import com.egorpetruchcho.loriandroid_api.model.Week;
import com.egorpetruchcho.loriandroid_api.utils.DateUtils;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.Date;

public class TimeEntriesWeekFragment extends LoriFragment {

    private static final String START_DATE_EXTRA = "TimeEntriesWeekFragment.START_DATE_EXTRA";

    private TextView weekText;
    private Week week;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_time_entries_week, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        injectArgs();
        weekText = (TextView) view.findViewById(R.id.week_text);
        weekText.setText(DateUtils.apiString(week.getStartDate()) + " " + DateUtils.apiString(week.getEndDate()));
        reloadData();
    }

    private void reloadData() {
        getBackgroundManager().execute(new GetTimeEntriesTask(week), new RequestListener<TimeEntriesResult>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {

            }

            @Override
            public void onRequestSuccess(TimeEntriesResult result) {
                for (TimeEntry timeEntry : result.result) {
                    weekText.setText(weekText.getText() + "\r\n" + timeEntry.getTaskName() + " " + timeEntry.getTimeInMinutes());
                }
            }
        });
    }

    private void injectArgs() {
        Bundle args = getArguments();
        week = new Week((Date) args.get(START_DATE_EXTRA));
    }

    public static TimeEntriesWeekFragment build(Week week) {
        TimeEntriesWeekFragment fragment = new TimeEntriesWeekFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(START_DATE_EXTRA, week.getStartDate());
        fragment.setArguments(bundle);
        return fragment;
    }
}
