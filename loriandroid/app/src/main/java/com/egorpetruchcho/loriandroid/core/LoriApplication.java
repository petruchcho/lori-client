package com.egorpetruchcho.loriandroid.core;

import android.app.Application;

public class LoriApplication extends Application {
    private static LoriApplication instance;

    public LoriApplication() {
        instance = this;
    }

    public static LoriApplication getInstance() {
        return instance;
    }
}
