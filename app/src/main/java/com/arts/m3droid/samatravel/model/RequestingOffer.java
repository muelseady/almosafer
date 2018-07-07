package com.arts.m3droid.samatravel.model;

import android.os.Parcel;
import android.os.Parcelable;

public class RequestingOffer implements Parcelable {
    private String userUID, offerId, offerName, userName, offerImage, employeeKey;

    public String getUserUID() {
        return userUID;
    }

    public String getOfferId() {
        return offerId;
    }

    public String getOfferName() {
        return offerName;
    }

    public String getUserName() {
        return userName;
    }

    public String getOfferImage() {
        return offerImage;
    }

    public String getEmployeeKey() {
        return employeeKey;
    }

    public RequestingOffer(String userUID, String offerId, String offerName, String userName,
                           String offerImage, String empKey) {
        this.userUID = userUID;
        this.offerId = offerId;
        this.offerName = offerName;
        this.userName = userName;
        this.offerImage = offerImage;
        this.employeeKey = empKey;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userUID);
        dest.writeString(this.offerId);
        dest.writeString(this.offerName);
        dest.writeString(this.userName);
        dest.writeString(this.offerImage);
        dest.writeString(this.employeeKey);
    }

    protected RequestingOffer(Parcel in) {
        this.userUID = in.readString();
        this.offerId = in.readString();
        this.offerName = in.readString();
        this.userName = in.readString();
        this.offerImage = in.readString();
        this.employeeKey = in.readString();
    }

    public static final Parcelable.Creator<RequestingOffer> CREATOR = new Parcelable.Creator<RequestingOffer>() {
        @Override
        public RequestingOffer createFromParcel(Parcel source) {
            return new RequestingOffer(source);
        }

        @Override
        public RequestingOffer[] newArray(int size) {
            return new RequestingOffer[size];
        }
    };
}
