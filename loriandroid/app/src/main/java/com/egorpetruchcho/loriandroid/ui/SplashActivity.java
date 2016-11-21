package com.egorpetruchcho.loriandroid.ui;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.egorpetruchcho.loriandroid.R;
import com.egorpetruchcho.loriandroid.core.LoriActivity;
import com.egorpetruchcho.loriandroid.state.ApplicationSavedState;

public class SplashActivity extends LoriActivity {

    private static final int SPLASH_SCREEN_DELAY_MS = 1500;
    private Handler handler;

    private final Runnable delayedActionRunnable = new Runnable() {
        @Override
        public void run() {
            if (ApplicationSavedState.getInstance().getAuthKey() == null) {
                LoginActivity.startMe(SplashActivity.this);
            } else {
                // TODO move to main activity
                LoginActivity.startMe(SplashActivity.this);
            }
            finish();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_splash);
        handler = new Handler();
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
