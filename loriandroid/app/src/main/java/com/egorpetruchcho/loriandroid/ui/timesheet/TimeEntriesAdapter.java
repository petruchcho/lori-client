package com.egorpetruchcho.loriandroid.ui.timesheet;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.egorpetruchcho.loriandroid_api.utils.DateUtils;


public class TimeEntriesAdapter extends FragmentStatePagerAdapter {

    public TimeEntriesAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        return TimeEntriesWeekFragment.build(DateUtils.getCurrentWeek().getKthWeek(-position));
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }
}
