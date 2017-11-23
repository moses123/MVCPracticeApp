package com.test.testapplication.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by moseskesavan on 11/21/17.
 * <p>
 * This class holds the data about the country:
 * a) Title
 * b) Description
 * c) Image URL
 */

public class Information  {

    /* holds the title*/
    @SerializedName("title")
    private String title;

    /*holds the description*/
    @SerializedName("description")
    private String description;

    /*holds the image URL*/
    @SerializedName("imageHref")
    private String imageUrl;



    /* Getter and setter for the above fields goes here:*/

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


}
