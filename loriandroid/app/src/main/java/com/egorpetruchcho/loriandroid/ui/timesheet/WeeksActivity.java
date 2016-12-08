package com.egorpetruchcho.loriandroid.ui.timesheet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.egorpetruchcho.loriandroid.R;
import com.egorpetruchcho.loriandroid.core.LoriActivity;
import com.egorpetruchcho.loriandroid.state.AuthState;
import com.egorpetruchcho.loriandroid.ui.LoginActivity;


public class WeeksActivity extends LoriActivity {

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
        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(new WeeksAdapter(getSupportFragmentManager()));
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

    public static void startMe(Context context) {
        context.startActivity(new Intent(context, WeeksActivity.class));
    }
}
