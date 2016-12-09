package com.egorpetruchcho.loriandroid.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.egorpetruchcho.loriandroid.R;
import com.egorpetruchcho.loriandroid.background.tasks.BackgroundTaskListener;
import com.egorpetruchcho.loriandroid.background.tasks.LoginTask;
import com.egorpetruchcho.loriandroid.core.LoriActivity;
import com.egorpetruchcho.loriandroid.state.ApplicationSavedState;
import com.egorpetruchcho.loriandroid.ui.timesheet.WeeksActivity;
import com.egorpetruchcho.loriandroid_api.model.Locale;

public class LoginActivity extends LoriActivity {

    private ProgressBar progress;
    private TextView login;
    private TextView password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_login);
        initViews();
    }

    private void initViews() {
        progress = (ProgressBar) findViewById(R.id.login_progress);
        login = (TextView) findViewById(R.id.login_login);
        password = (TextView) findViewById(R.id.login_password);
        findViewById(R.id.email_sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void login() {
        progress.setVisibility(View.VISIBLE);
        getBackgroundManager().execute(new LoginTask(login.getText().toString(), password.getText().toString(), Locale.RU), new BackgroundTaskListener<String>() {
            @Override
            public void onRequestFailure(Exception exception) {
                progress.setVisibility(View.INVISIBLE);
                // TODO Refactor errors and its messages (huge task actually)
                Toast.makeText(LoginActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRequestSuccess(String s) {
                ApplicationSavedState.getInstance().setAuthKey(s);
                WeeksActivity.startMe(LoginActivity.this);
                finish();
            }
        });
    }

    public static void startMe(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }
}

