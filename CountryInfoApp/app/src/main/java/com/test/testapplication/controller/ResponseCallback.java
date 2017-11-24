package com.test.testapplication.controller;

import com.test.testapplication.model.Information;

import java.util.List;

import retrofit2.Response;

/**
 * Created by moseskesavan on 11/22/17.
 *
 * A class which will ahve the callbacks for the network calls.
 */

public class ResponseCallback {

    public static abstract class InfoCallback{

        public void onResponse(List<Information> response, String title){}

        public void onError(Throwable t){}

        public void onNetworkChange(boolean status){}
    }


}
