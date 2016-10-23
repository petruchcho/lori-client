package com.egorpetruchcho.loriandroid.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.egorpetruchcho.loriandroid.R;
import com.egorpetruchcho.loriandroid.background.tasks.LoginTask;
import com.egorpetruchcho.loriandroid.core.LoriActivity;
import com.egorpetruchcho.loriandroid_api.model.Locale;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

public class LoginActivity extends LoriActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_login);

        findViewById(R.id.email_sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

    }

    private void login() {
        getBackgroundManager().execute(new LoginTask("admin", "admin", Locale.RU), new RequestListener<String>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {
                Toast.makeText(LoginActivity.this, "fail:))", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRequestSuccess(String s) {
                Toast.makeText(LoginActivity.this, s, Toast.LENGTH_LONG).show();
            }
        });
    }
}

