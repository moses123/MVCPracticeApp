package com.test.testapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by moseskesavan on 11/22/17.
 *
 * Stores the list of information we get from the network response.
 */

public class CountryInfo {

    /* Stores the array title*/
    @SerializedName("title")
    private String infoTitle;

    /* Stores list of information*/
    @SerializedName("rows")
    private List<Information> informationList;


    /* Getter and setter for the above field goes here:*/

    public List<Information> getInformationList() {
        return informationList;
    }

    public void setInformationList(List<Information> informationList) {
        this.informationList = informationList;
    }

    public String getInfoTitle() {
        return infoTitle;
    }

    public void setInfoTitle(String infoTitle) {
        this.infoTitle = infoTitle;
    }


}
