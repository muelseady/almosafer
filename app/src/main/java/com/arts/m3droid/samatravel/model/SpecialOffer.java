package com.arts.m3droid.samatravel.model;

import android.os.Parcelable;
import android.support.annotation.NonNull;


public class SpecialOffer implements Parcelable {
    private String uid, name, imageUrl, details;

    public SpecialOffer() {
    }

    public SpecialOffer(String uid, String name, String imageUrl, String details) {
        this.uid = uid;
        this.name = name;
        this.imageUrl = imageUrl;
        this.details = details;
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
    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeString(this.uid);
        dest.writeString(this.name);
        dest.writeString(this.imageUrl);
        dest.writeString(this.details);
    }

    protected SpecialOffer(android.os.Parcel in) {
        this.uid = in.readString();
        this.name = in.readString();
        this.imageUrl = in.readString();
        this.details = in.readString();
    }

    public static final Parcelable.Creator<SpecialOffer> CREATOR = new Parcelable.Creator<SpecialOffer>() {
        @NonNull
        @Override
        public SpecialOffer createFromParcel(android.os.Parcel source) {
            return new SpecialOffer(source);
        }

        @Override
        public SpecialOffer[] newArray(int size) {
            return new SpecialOffer[size];
        }
    };
}
