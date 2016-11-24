package com.egorpetruchcho.loriandroid.ui.timesheet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.egorpetruchcho.loriandroid.R;
import com.egorpetruchcho.loriandroid.core.LoriActivity;


public class TimesheetActivity extends LoriActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_timesheet);
        initViews();
        reloadData();
    }

    private void reloadData() {
    }

    void initViews() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(10);
        viewPager.setAdapter(new WeeksAdapter(getSupportFragmentManager()));
    }

    public static void startMe(Context context) {
        context.startActivity(new Intent(context, TimesheetActivity.class));
    }
}
