package com.arts.m3droid.samatravel.model;

import android.os.Parcel;
import android.os.Parcelable;


public class SpecialOffer implements Parcelable {
    private String uid, name, imageUrl, details;

    public SpecialOffer() {
    }

    public SpecialOffer(String name, String imageUrl, String details) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.details = details;
    }


    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDetails() {
        return details;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uid);
        dest.writeString(this.name);
        dest.writeString(this.imageUrl);
        dest.writeString(this.details);
    }

    protected SpecialOffer(Parcel in) {
        this.uid = in.readString();
        this.name = in.readString();
        this.imageUrl = in.readString();
        this.details = in.readString();
    }

    public static final Creator<SpecialOffer> CREATOR = new Creator<SpecialOffer>() {
        @Override
        public SpecialOffer createFromParcel(Parcel source) {
            return new SpecialOffer(source);
        }

        @Override
        public SpecialOffer[] newArray(int size) {
            return new SpecialOffer[size];
        }
    };
}
