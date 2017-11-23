package com.test.testapplication.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by moseskesavan on 11/21/17.
 *
 * This class manages the network calls triggered form the applications UI.
 */

public class NetworkManager {

    /*Constant holding the Base url for our network operations*/
    private static final String BASE_URL = "https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/";

    /*Holds the retrofit instance */
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
