package com.test.testapplication.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.test.testapplication.model.Information;
import com.test.testapplication.ui.adapter.CountryInfoAdapter;


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
    public static boolean isNetworkConnected() {
        //TODO:  write the code:
        return false;
    }

    /**
     * This method will return the view type to be rendered based on the information.
     * @param info the information object.
     * @return type
     */
    public static int getItemViewType(Information info) {
        int viewType=-1;
        if (info != null ) {
                if (TextUtils.isEmpty(info.getImageUrl()) && !(TextUtils.isEmpty(info.getDescription()) && TextUtils.isEmpty(info.getTitle()))) {
                    viewType=CountryInfoAdapter.VIEW_TYPE_NO_IMAGE;
                } else {
                    viewType= CountryInfoAdapter.VIEW_TYPE_DEFAULT;
                }
            }
        return viewType;
    }
}
