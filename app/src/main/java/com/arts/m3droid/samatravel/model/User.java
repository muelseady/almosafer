package com.arts.m3droid.samatravel.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class User implements Parcelable {

    private String uid, name, number, email;

    private List<RequestingOfferDetails> goingOnOffers;
    private ArrayList<String> favoritedOffers;

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

    public void setGoingOnOffers(RequestingOfferDetails goingOnOffers) {
        if (this.goingOnOffers == null) {
            this.goingOnOffers = new ArrayList<>();
        }
        this.goingOnOffers.add(goingOnOffers);
    }

    public void setFavOffers(String doneOffers) {
        if (favoritedOffers == null) {
            favoritedOffers = new ArrayList<>();
        }
        favoritedOffers.add(doneOffers);
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

    public List<RequestingOfferDetails> getGoinOnOffers() {
        return goingOnOffers;
    }

    public ArrayList<String> getFavoritedOffers() {
        return favoritedOffers;
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
        dest.writeTypedList(this.goingOnOffers);
    }

    protected User(Parcel in) {
        this.uid = in.readString();
        this.name = in.readString();
        this.number = in.readString();
        this.email = in.readString();
        this.goingOnOffers = in.createTypedArrayList(RequestingOfferDetails.CREATOR);
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
