package com.arts.m3droid.samatravel.model;

import android.os.Parcel;
import android.os.Parcelable;

public class FavOffer implements Parcelable {

    private String favOfferName, favOfferId;

    public String getFavOfferName() {
        return favOfferName;
    }

    public String getFavOfferId() {
        return favOfferId;
    }

    public void setFavOfferName(String favOfferName) {
        this.favOfferName = favOfferName;
    }

    public void setFavOfferId(String favOfferId) {
        this.favOfferId = favOfferId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.favOfferName);
        dest.writeString(this.favOfferId);
    }

    public FavOffer() {
    }

    protected FavOffer(Parcel in) {
        this.favOfferName = in.readString();
        this.favOfferId = in.readString();
    }

    public static final Parcelable.Creator<FavOffer> CREATOR = new Parcelable.Creator<FavOffer>() {
        @Override
        public FavOffer createFromParcel(Parcel source) {
            return new FavOffer(source);
        }

        @Override
        public FavOffer[] newArray(int size) {
            return new FavOffer[size];
        }
    };
}
