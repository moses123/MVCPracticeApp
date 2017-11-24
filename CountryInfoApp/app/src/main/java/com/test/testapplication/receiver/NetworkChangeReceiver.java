package com.test.testapplication.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.test.testapplication.controller.AppController;
import com.test.testapplication.utils.AppConstants;
import com.test.testapplication.utils.AppUtil;
import com.test.testapplication.utils.PreferenceManager;

/**
 * Created by moseskesavan on 11/24/17.
 * This receiver will receive the network change broadcast.
 * Checks if the App had network before, if not it will broadcast the network state to the UI using the help of APPController.
 */

public class NetworkChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (AppUtil.isNetworkConnected(context)) {
            if (PreferenceManager.getInstance(context).getBoolean(AppConstants.IS_NETWORK_OFF)) {
                AppController.getAppController(context).broadCastNetworkChangeToUI(true);
            }
        }
    }
}
