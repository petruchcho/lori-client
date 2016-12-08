package com.egorpetruchcho.loriandroid.ui.timesheet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.egorpetruchcho.loriandroid.R;
import com.egorpetruchcho.loriandroid.background.results.TimeEntriesResult;
import com.egorpetruchcho.loriandroid.background.tasks.GetTimeEntriesForDayTask;
import com.egorpetruchcho.loriandroid.core.LoriActivity;
import com.egorpetruchcho.loriandroid.state.AuthState;
import com.egorpetruchcho.loriandroid.ui.LoginActivity;
import com.egorpetruchcho.loriandroid.utils.DateUtils;
import com.egorpetruchcho.loriandroid_api.model.TimeEntry;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DayActivity extends LoriActivity {

    private static final String DATE_EXTRA = "DayActivity.DATE_EXTRA";
    private static final int ADD_ENTRY_ACTIVITY_REQUEST_CODE = 99;

    private Date date;

    private ProgressBar progress;
    private TextView dayLabel;
    private ListView entriesList;
    private FloatingActionButton addEntryButton;

    private TimeEntriesAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_day);

        initViews();

        date = (Date) getIntent().getSerializableExtra(DATE_EXTRA);
        dayLabel.setText(DateUtils.getString(this, date, R.string.day_date_format));

        adapter = new TimeEntriesAdapter(this);
        entriesList.setAdapter(adapter);

        reloadData();
    }

    private void reloadData() {
        progress.setVisibility(View.VISIBLE);
        getBackgroundManager().execute(new GetTimeEntriesForDayTask(date), new RequestListener<TimeEntriesResult>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {
            }

            @Override
            public void onRequestSuccess(TimeEntriesResult timeEntriesResult) {
                adapter.updateTimeEntries(timeEntriesResult.result);
                progress.setVisibility(View.GONE);
            }
        });
    }

    private void initViews() {
        progress = (ProgressBar) findViewById(R.id.day_progress);
        dayLabel = (TextView) findViewById(R.id.day_label);
        entriesList = (ListView) findViewById(R.id.entries_list);
        addEntryButton = (FloatingActionButton) findViewById(R.id.add_entry_button);
        addEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddEntryActivity.startMeForResult(DayActivity.this, date, ADD_ENTRY_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_ENTRY_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            reloadData();
        }
    }

    public static void startMe(Context context, Date date) {
        Intent intent = new Intent(context, DayActivity.class);
        intent.putExtra(DATE_EXTRA, date);
        context.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                AuthState.getInstance().logout();
                LoginActivity.startMe(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class TimeEntriesAdapter extends ArrayAdapter<TimeEntry> {

        private final LayoutInflater layoutInflater;
        private final List<TimeEntry> timeEntries;

        TimeEntriesAdapter(Context context) {
            this(context, new ArrayList<TimeEntry>());
        }

        TimeEntriesAdapter(Context context, List<TimeEntry> timeEntries) {
            super(context, View.NO_ID, timeEntries);
            this.timeEntries = timeEntries;
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public void updateTimeEntries(List<TimeEntry> timeEntries) {
            this.timeEntries.clear();
            this.timeEntries.addAll(timeEntries);
            notifyDataSetChanged();
        }

        @SuppressWarnings("ConstantConditions")
        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.v_time_entry, parent, false);
            }
            TextView projectNameLabel = (TextView) convertView.findViewById(R.id.project_name_label);
            TextView taskNameLabel = (TextView) convertView.findViewById(R.id.task_name_label);
            TextView countOfHours = (TextView) convertView.findViewById(R.id.count_of_hours_label);

            TimeEntry timeEntry = getItem(position);

            taskNameLabel.setText(timeEntry.getTaskName());
            countOfHours.setText(String.format(getString(R.string.count_of_hours_format), timeEntry.getTimeInHours()));

            return convertView;
        }
    }
}
