package com.test.testapplication.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * Created by moseskesavan on 11/21/17.
 * <p>
 * This class consists of all the utility methods required within the application.
 */

public class AppUtil {

    /**
     * This method checks if the device is connected to a network or not.
     *
     * @return true if connected else false.
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
