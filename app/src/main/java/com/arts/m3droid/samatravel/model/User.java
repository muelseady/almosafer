package com.arts.m3droid.samatravel.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private String uid, name, number, email;
    private String doneOffers, goingOnOffers;

    public User() {
    }

    public User(String uid, String name, String number, String email) {
        this.uid = uid;
        this.name = name;
        this.number = number;
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setDoneOffers(String doneOffers) {
        this.doneOffers = doneOffers;
    }

    public void setGoingOnOffers(String goingOffers) {
        this.goingOnOffers = goingOffers;
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

    public String getGoingOnOffers() {
        return goingOnOffers;
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
        dest.writeString(this.goingOnOffers);
    }

    protected User(Parcel in) {
        this.uid = in.readString();
        this.name = in.readString();
        this.number = in.readString();
        this.email = in.readString();
        this.doneOffers = in.readString();
        this.goingOnOffers = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
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
