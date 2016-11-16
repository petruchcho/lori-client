package com.egorpetruchcho.loriandroid.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.egorpetruchcho.loriandroid.core.LoriActivity;


public class TimesheetActivity extends LoriActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static void startMe(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }
}
