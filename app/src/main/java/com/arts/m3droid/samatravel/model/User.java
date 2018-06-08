package com.arts.m3droid.samatravel.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private String uid, name, number, email;
    private String doneOffers, goingOffers;

    public User(String uid, String name, String number, String email) {
        this.uid = uid;
        this.name = name;
        this.number = number;
        this.email = email;
    }

    public void setDoneOffers(String doneOffers) {
        this.doneOffers = doneOffers;
    }

    public void setGoingOffers(String goingOffers) {
        this.goingOffers = goingOffers;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getEmail() {
        return email;
    }

    public String getDoneOffers() {
        return doneOffers;
    }

    public String getGoingOffers() {
        return goingOffers;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uid);
        dest.writeString(this.name);
        dest.writeString(this.number);
        dest.writeString(this.email);
        dest.writeString(this.doneOffers);
        dest.writeString(this.goingOffers);
    }

    protected User(Parcel in) {
        this.uid = in.readString();
        this.name = in.readString();
        this.number = in.readString();
        this.email = in.readString();
        this.doneOffers = in.readString();
        this.goingOffers = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
