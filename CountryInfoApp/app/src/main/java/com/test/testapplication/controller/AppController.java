package com.test.testapplication.controller;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.test.testapplication.App;
import com.test.testapplication.database.DBHelper;
import com.test.testapplication.logger.AppLog;
import com.test.testapplication.model.CountryInfo;
import com.test.testapplication.model.Information;
import com.test.testapplication.network.ApiInterface;
import com.test.testapplication.network.NetworkManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by moseskesavan on 11/22/17.
 * <p>
 * Singleton class, which controls which APi and database call to be triggered by the UI .
 */

public class AppController {

    /* Tag to log info, warning, errors.*/
    private static final String TAG = AppController.class.getSimpleName();

    /*Holds the list of callbacks registered by the UI components*/
    private final List<ResponseCallback.InfoCallback> mConnectionColl = Collections.synchronizedList(new ArrayList<ResponseCallback.InfoCallback>());

    /* Holds controller instance*/
    private static AppController sController;

    /* Holds the db helper instance to be used in this class */
    private DBHelper mDbHelper;

    /*Public constructor*/
    private AppController() {

    }

    /**
     * Returns the app controller instance
     *
     * @return controller instance
     */
    public static AppController getAppController() {

        if (sController == null) {
            sController = new AppController();
        }
        return sController;
    }

    /**
     * Register the callback, so that the controller returns the required data.
     *
     * @param callback callback
     */
    public void registerCallback(ResponseCallback.InfoCallback callback) {
        mConnectionColl.add(callback);
    }

    /**
     * Deregister the callback.
     *
     * @param callback callback
     */
    public void unRegisterCallback(ResponseCallback.InfoCallback callback) {
        mConnectionColl.remove(callback);
    }

    /**
     * This method will give call back to UI with the response
     *
     * @param info list of information
     */
    private void broadcastInfoResponse(List<Information> info, String title) {
        int size = mConnectionColl != null ? mConnectionColl.size() : 0;
        for (int i = 0; i < size; i++) {
            if (mConnectionColl.get(i) != null) {
                mConnectionColl.get(i).onResponse(info, title);
            }
        }
    }

    /**
     * This method will give error back to UI with the response
     *
     * @param t error object
     */
    private void broadcastError(Throwable t) {
        int size = mConnectionColl != null ? mConnectionColl.size() : 0;
        for (int i = 0; i < size; i++) {
            if (mConnectionColl.get(i) != null) {
                mConnectionColl.get(i).onError(t);
            }
        }
    }

    /**
     * This methdo teels the ui for any network change.
     *
     * @param status
     */
    public void broadCastNetworkChangeToUI(boolean status) {
        int size = mConnectionColl != null ? mConnectionColl.size() : 0;
        for (int i = 0; i < size; i++) {
            if (mConnectionColl.get(i) != null) {
                mConnectionColl.get(i).onNetworkChange(status);
            }
        }
    }


                                             /*All the calls related to API goes here:*/

    /**
     * Fetch the data from the server and give call back to the UI.
     */
    public void fetchInformationList() {

        ApiInterface apiService =
                NetworkManager.getClient().create(ApiInterface.class);

        Call<CountryInfo> call = apiService.getCountryData();

        call.enqueue(getNetworkCallback());
    }

    @NonNull
    private Callback<CountryInfo> getNetworkCallback() {
        return new Callback<CountryInfo>() {

            @Override
            public void onResponse(Call<CountryInfo> call, Response<CountryInfo> response) {
                if (response != null && response.body() != null) {
                    String title = response.body().getInfoTitle();
                    List<Information> filteredList = new ArrayList<>();
                    List<Information> info = response.body().getInformationList();

                    if (info != null && info.size() > 0) {
                        for (Information infoObject :
                                info) {
                            if (!TextUtils.isEmpty(infoObject.getTitle()) && !TextUtils.isEmpty(infoObject.getTitle()) && !TextUtils.isEmpty(infoObject.getTitle())) {
                                filteredList.add(infoObject);
                            }
                        }
                    }

                    // Clear db data first
                    getDbHelper().clearAppInfoData();

                    // insert new values
                    getDbHelper().insertInfoData(filteredList);

                    //Send the response to back to UI
                    broadcastInfoResponse(filteredList, title);

                } else {
                    broadcastError(new Throwable("Something went wrong, please try again later."));
                }

            }

            @Override
            public void onFailure(Call<CountryInfo> call, Throwable t) {
                // Log error here since request failed
                AppLog.e(TAG, t.toString());
                broadcastError(t);
            }
        };
    }

                                        /*All the calls related to database goes here:*/


    /**
     * Get the db helper instance to be used.
     *
     * @return dbHelper instance
     */
    private DBHelper getDbHelper() {
        if (mDbHelper == null) {
            mDbHelper = DBHelper.getInstance(App.getContext());
        }
        return mDbHelper;
    }

    /**
     * Fetch the app info data from the db.
     *
     * @return list of Information.
     */
    public List<Information> fetchInformationListFromDB() {
        return getDbHelper().fetchInfoDataFromDb();
    }


}
