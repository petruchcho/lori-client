package com.egorpetruchcho.loriandroid.ui;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.egorpetruchcho.loriandroid.R;
import com.egorpetruchcho.loriandroid.background.tasks.BackgroundTaskListener;
import com.egorpetruchcho.loriandroid.background.tasks.GetUserTask;
import com.egorpetruchcho.loriandroid.core.LoriActivity;
import com.egorpetruchcho.loriandroid.state.ApplicationSavedState;
import com.egorpetruchcho.loriandroid.state.AuthState;
import com.egorpetruchcho.loriandroid.ui.timesheet.WeeksActivity;
import com.egorpetruchcho.loriandroid_api.model.User;

public class SplashActivity extends LoriActivity {

    private static final int SPLASH_SCREEN_DELAY_MS = 1500;
    private final Runnable delayedActionRunnable = new Runnable() {
        @Override
        public void run() {
            if (AuthState.getInstance().getCurrentUser() == null) {
                reloadCurrentUser();
            } else {
                WeeksActivity.startMe(SplashActivity.this);
                finish();
            }
        }
    };

    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_splash);
        handler = new Handler();
    }

    private void reloadCurrentUser() {
        String currentUsername = ApplicationSavedState.getInstance().getUsername();
        if (currentUsername == null) {
            LoginActivity.startMe(this);
            finish();
        } else {
            getBackgroundManager().execute(new GetUserTask(currentUsername), new BackgroundTaskListener<User>() {
                @Override
                public void onRequestFailure(Exception spiceException) {
                    // TODO Should proceed different errors in different ways, but it's ok by now
                    LoginActivity.startMe(SplashActivity.this);
                }

                @Override
                public void onRequestSuccess(User user) {
                    AuthState.getInstance().setCurrentUser(user);
                    WeeksActivity.startMe(SplashActivity.this);
                    finish();
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(delayedActionRunnable, SPLASH_SCREEN_DELAY_MS);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(delayedActionRunnable);
    }
}
