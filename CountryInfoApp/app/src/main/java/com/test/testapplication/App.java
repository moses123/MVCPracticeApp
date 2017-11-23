package com.test.testapplication;

import android.app.Application;
import android.content.Context;

/**
 * Created by moseskesavan on 11/22/17.
 *
 * The application class.
 */

public class App extends Application {

    /* Holds the app context*/
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this.getApplicationContext();
    }

    /**
     *  Get the application context throughout the application
     * @return app context
     */
    public static Context getContext() {
        return sContext;
    }
}
