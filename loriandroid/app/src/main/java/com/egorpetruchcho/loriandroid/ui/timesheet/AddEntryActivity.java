package com.egorpetruchcho.loriandroid.ui.timesheet;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.egorpetruchcho.loriandroid.R;
import com.egorpetruchcho.loriandroid.background.results.ProjectsResult;
import com.egorpetruchcho.loriandroid.background.tasks.BackgroundTaskListener;
import com.egorpetruchcho.loriandroid.background.tasks.CreateTimeEntryTask;
import com.egorpetruchcho.loriandroid.background.tasks.GetProjectsTask;
import com.egorpetruchcho.loriandroid.core.LoriActivity;
import com.egorpetruchcho.loriandroid.state.AuthState;
import com.egorpetruchcho.loriandroid.ui.LoginActivity;
import com.egorpetruchcho.loriandroid.utils.DateUtils;
import com.egorpetruchcho.loriandroid_api.model.Project;
import com.egorpetruchcho.loriandroid_api.model.Task;
import com.egorpetruchcho.loriandroid_api.model.TimeEntryCommit;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddEntryActivity extends LoriActivity {
    private static final String DATE_EXTRA = "AddEntryActivity.DATE_EXTRA";

    private ProgressBar progress;
    private TextView dateText;
    private TextView workHoursText;
    private Spinner projectsSpinner;
    private Spinner tasksSpinner;
    private Button addButton;

    private List<Project> projects;

    private Date date = new Date();
    private int hours = 0;
    private int minutes = 0;

    public static void startMeForResult(Activity activity, Date day, int requestCode) {
        Intent intent = new Intent(activity, AddEntryActivity.class);
        intent.putExtra(DATE_EXTRA, day);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_add_entry);

        if (getIntent().getExtras().containsKey(DATE_EXTRA)) {
            date = (Date) getIntent().getSerializableExtra(DATE_EXTRA);
        }

        initViews();
        reloadData();
    }

    private void initViews() {
        progress = (ProgressBar) findViewById(R.id.add_entry_progress);
        dateText = (TextView) findViewById(R.id.entry_date);
        workHoursText = (TextView) findViewById(R.id.work_hours);
        projectsSpinner = (Spinner) findViewById(R.id.projects_spinner);
        tasksSpinner = (Spinner) findViewById(R.id.tasks_spinner);
        addButton = (Button) findViewById(R.id.add_entry_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEntry();
            }
        });

        dateText.setText(DateUtils.getString(this, date, R.string.day_date_format));
        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        workHoursText.setText(String.format(getString(R.string.work_hours_format), hours, minutes));
        workHoursText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog();
            }
        });

        projectsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                Project project = projects.get(pos);
                onTasksLoaded(project.getTasks());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void addEntry() {
        Project project = projects.get(projectsSpinner.getSelectedItemPosition());
        Task task = project.getTasks().get(tasksSpinner.getSelectedItemPosition());

        progress.setVisibility(View.VISIBLE);
        TimeEntryCommit timeEntry = new TimeEntryCommit(date, task.getId(), hours * 60 + minutes, AuthState.getInstance().getCurrentUser());
        getBackgroundManager().execute(new CreateTimeEntryTask(timeEntry), new BackgroundTaskListener<Void>() {
            @Override
            public void onRequestFailure(Exception spiceException) {
            }

            @Override
            public void onRequestSuccess(Void aVoid) {
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    private void reloadData() {
        getBackgroundManager().execute(new GetProjectsTask(), new BackgroundTaskListener<ProjectsResult>() {
            @Override
            public void onRequestFailure(Exception exception) {
            }

            @Override
            public void onRequestSuccess(ProjectsResult projectsResult) {
                if (projectsResult.projects != null && projectsResult.projects.size() > 0) {
                    projects = projectsResult.projects;
                    List<String> projectsNames = new ArrayList<>();
                    for (Project project : projects) {
                        projectsNames.add(project.getName());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(AddEntryActivity.this, android.R.layout.simple_spinner_dropdown_item, projectsNames);
                    projectsSpinner.setAdapter(adapter);
                    progress.setVisibility(View.GONE);
                }
            }
        });
    }

    private void onTasksLoaded(List<Task> tasks) {
        if (tasks != null && tasks.size() > 0) {
            List<String> tasksNames = new ArrayList<>();
            for (Task task : tasks) {
                tasksNames.add(task.getName());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(AddEntryActivity.this, android.R.layout.simple_spinner_dropdown_item, tasksNames);
            tasksSpinner.setAdapter(adapter);

            addButton.setEnabled(true);
        } else {
            tasksSpinner.setAdapter(null);
            addButton.setEnabled(false);
        }
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

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog pickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);
                date = calendar.getTime();
                dateText.setText(DateUtils.getString(AddEntryActivity.this, date, R.string.day_date_format));
            }
        }, year, month, day);
        pickerDialog.show();
    }

    private void showTimePickerDialog() {
        TimePickerDialog dialog = new TimePickerDialog(AddEntryActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
                AddEntryActivity.this.hours = hours;
                AddEntryActivity.this.minutes = minutes;
                workHoursText.setText(String.format(getString(R.string.work_hours_format), hours, minutes));
            }
        }, hours, minutes, false);
        dialog.show();
    }
}
