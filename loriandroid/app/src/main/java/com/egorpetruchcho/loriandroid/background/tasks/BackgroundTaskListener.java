package com.egorpetruchcho.loriandroid.background.tasks;


import com.egorpetruchcho.loriandroid.core.LoriApplication;
import com.egorpetruchcho.loriandroid.state.AuthState;
import com.egorpetruchcho.loriandroid.ui.LoginActivity;
import com.egorpetruchcho.loriandroid_api.exceptions.NotAuthorizedException;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

public abstract class BackgroundTaskListener<T> implements RequestListener<T> {
    @Override
    public final void onRequestFailure(SpiceException spiceException) {
        if (spiceException.getCause() instanceof NotAuthorizedException) {
            AuthState.getInstance().logout();
            LoginActivity.startMe(LoriApplication.getInstance());
        } else {
            onRequestFailure((Exception) spiceException.getCause());
        }
    }

    public abstract void onRequestFailure(Exception exception);
}
