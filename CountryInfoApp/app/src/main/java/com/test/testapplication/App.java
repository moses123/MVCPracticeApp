package com.test.testapplication;

import android.app.Application;
import android.content.Context;

/**
 * Created by moseskesavan on 11/22/17.
 *
 * The application class.
 */

public class App extends Application {

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this.getApplicationContext();
    }

    public static Context getContext() {
        return sContext;
    }
}
