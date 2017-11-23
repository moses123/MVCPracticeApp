package com.test.testapplication.network;

import com.test.testapplication.model.CountryInfo;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by moseskesavan on 11/22/17.
 *
 * Holds the different API to be used throughout the application.
 */

public interface ApiInterface {

    /*get call for country information*/
    @GET("facts.json")
    Call<CountryInfo> getCountryData();
}
