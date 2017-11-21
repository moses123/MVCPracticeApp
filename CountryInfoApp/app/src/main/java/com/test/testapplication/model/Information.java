package com.test.testapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by moseskesavan on 11/21/17.
 * <p>
 * This class holds the data about the country:
 * a) Title
 * b) Description
 * c) Image URL
 */

public class Information implements Parcelable {

    /* holds the title*/
    private String title;

    /*holds the description*/
    private String description;

    /*holds the image URL*/
    private String imageUrl;

     /* Parcelable implementation for this class goes here:*/

    protected Information(Parcel in) {
        title=in.readString();
        description=in.readString();
        imageUrl=in.readString();
    }

    public static final Creator<Information> CREATOR = new Creator<Information>() {
        @Override
        public Information createFromParcel(Parcel in) {
            return new Information(in);
        }

        @Override
        public Information[] newArray(int size) {
            return new Information[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(imageUrl);
    }


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
