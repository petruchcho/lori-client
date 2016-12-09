package com.egorpetruchcho.loriandroid.ui.timesheet;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.egorpetruchcho.loriandroid.R;
import com.egorpetruchcho.loriandroid.background.results.TimeEntriesResult;
import com.egorpetruchcho.loriandroid.background.tasks.BackgroundTaskListener;
import com.egorpetruchcho.loriandroid.background.tasks.GetTimeEntriesForWeekTask;
import com.egorpetruchcho.loriandroid.core.LoriFragment;
import com.egorpetruchcho.loriandroid.model.Day;
import com.egorpetruchcho.loriandroid.model.Week;
import com.egorpetruchcho.loriandroid.utils.DateUtils;
import com.egorpetruchcho.loriandroid_api.model.TimeEntry;

import java.util.Date;
import java.util.List;

public class WeekFragment extends LoriFragment {

    private static final String START_DATE_EXTRA = "TimeEntriesWeekFragment.START_DATE_EXTRA";

    private TextView weekText;
    private ListView daysList;
    private ProgressBar progress;

    private Week week;

    public static WeekFragment build(Week week) {
        WeekFragment fragment = new WeekFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(START_DATE_EXTRA, week.getStartDate());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_week, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        injectArgs();
        initViews(view);

        weekText.setText(DateUtils.getRangeString(getContext(), week.getStartDate(), week.getEndDate(), R.string.week_range_date_format, R.string.week_range_format));
        daysList.setAdapter(new DaysAdapter(getContext(), week.getDays()));
        daysList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                DayActivity.startMe(getContext(), week.getDays()[pos].getDate());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        reloadData();
    }

    private void reloadData() {
        progress.setVisibility(View.VISIBLE);
        getBackgroundManager().execute(new GetTimeEntriesForWeekTask(week), new BackgroundTaskListener<TimeEntriesResult>() {
            @Override
            public void onRequestFailure(Exception exception) {
                // TODO Handle error
            }

            @Override
            public void onRequestSuccess(TimeEntriesResult result) {
                initList(result.result);
                progress.setVisibility(View.GONE);
            }
        });
    }

    private void injectArgs() {
        Bundle args = getArguments();
        week = new Week((Date) args.get(START_DATE_EXTRA));
    }

    private void initViews(View view) {
        daysList = (ListView) view.findViewById(R.id.days_list);
        weekText = (TextView) view.findViewById(R.id.week_text);
        progress = (ProgressBar) view.findViewById(R.id.week_progress);

        view.findViewById(R.id.week_arrow_left).setVisibility(DateUtils.isCurrentWeek(week) ? View.INVISIBLE : View.VISIBLE);
    }

    private void initList(List<TimeEntry> timeEntries) {
        for (Day day : week.getDays()) {
            day.clean();
        }
        for (TimeEntry timeEntry : timeEntries) {
            for (Day day : week.getDays()) {
                if (day.isValidTimeEntry(timeEntry)) {
                    day.addTimeEntry(timeEntry);
                }
            }
        }
        ((DaysAdapter) daysList.getAdapter()).notifyDataSetChanged();
    }

    private class DaysAdapter extends ArrayAdapter<Day> {

        private final Context context;
        private final LayoutInflater layoutInflater;

        DaysAdapter(Context context, Day[] days) {
            super(context, View.NO_ID, days);
            this.context = context;
            layoutInflater = (LayoutInflater) WeekFragment.this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @SuppressWarnings("ConstantConditions")
        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.v_day, parent, false);
            }
            TextView dayOfWeekLabel = (TextView) convertView.findViewById(R.id.day_of_week_label);
            TextView dayDateLabel = (TextView) convertView.findViewById(R.id.day_date_label);
            TextView countOfHours = (TextView) convertView.findViewById(R.id.count_of_hours_label);

            Day day = getItem(position);

            dayOfWeekLabel.setText(DateUtils.getString(context, day.getDate(), R.string.day_of_week_name_format));
            dayDateLabel.setText(DateUtils.getString(context, day.getDate(), R.string.day_date_format));
            countOfHours.setText(String.format(getString(R.string.count_of_hours_format), day.getCountOfHours()));

            return convertView;
        }
    }
}
