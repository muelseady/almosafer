package com.arts.m3droid.samatravel.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SpecialOfferRequest implements Parcelable {

    // will contain user id and employee id, the employee id must be checked before anything
    //because only one employee must be involved within one offer
    private String userName;
    private String userNumber;
    private String userId;
    private String userNotes;
    private String offerName;
    private String dateFrom, dateTo;
    private int adults, children, infants, over65;
    private String empPriKey;
    private String offerDetails, offerImageUrl;

    public SpecialOfferRequest() {
        //Many functions require empty constructor
    }

    public SpecialOfferRequest(String name, String number, String dateFrom, String dateTo, String userId,
                               int adults, int children, int infants, int over65, String notes,
                               String offerName, String offerDetails, String offerImageUrl) {
        this.userName = name;
        this.userId = userId;
        this.userNotes = notes;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.userNumber = number;
        this.adults = adults;
        this.children = children;
        this.infants = infants;
        this.over65 = over65;
        this.offerName = offerName;
        this.offerDetails = offerDetails;
        this.offerImageUrl = offerImageUrl;
    }

    public void setEmpPriKey(String empPriKey) {
        this.empPriKey = empPriKey;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserNotes() {
        return userNotes;
    }

    public String getOfferName() {
        return offerName;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public int getAdults() {
        return adults;
    }

    public int getChildren() {
        return children;
    }

    public int getInfants() {
        return infants;
    }

    public int getOver65() {
        return over65;
    }

    public String getEmpPriKey() {
        return empPriKey;
    }

    public String getOfferDetails() {
        return offerDetails;
    }

    public String getOfferImageUrl() {
        return offerImageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userName);
        dest.writeString(this.userNumber);
        dest.writeString(this.userId);
        dest.writeString(this.userNotes);
        dest.writeString(this.offerName);
        dest.writeString(this.dateFrom);
        dest.writeString(this.dateTo);
        dest.writeInt(this.adults);
        dest.writeInt(this.children);
        dest.writeInt(this.infants);
        dest.writeInt(this.over65);
        dest.writeString(this.empPriKey);
        dest.writeString(this.offerDetails);
        dest.writeString(this.offerImageUrl);
    }

    protected SpecialOfferRequest(Parcel in) {
        this.userName = in.readString();
        this.userNumber = in.readString();
        this.userId = in.readString();
        this.userNotes = in.readString();
        this.offerName = in.readString();
        this.dateFrom = in.readString();
        this.dateTo = in.readString();
        this.adults = in.readInt();
        this.children = in.readInt();
        this.infants = in.readInt();
        this.over65 = in.readInt();
        this.empPriKey = in.readString();
        this.offerDetails = in.readString();
        this.offerImageUrl = in.readString();
    }

    public static final Creator<SpecialOfferRequest> CREATOR = new Creator<SpecialOfferRequest>() {
        @Override
        public SpecialOfferRequest createFromParcel(Parcel source) {
            return new SpecialOfferRequest(source);
        }

        @Override
        public SpecialOfferRequest[] newArray(int size) {
            return new SpecialOfferRequest[size];
        }
    };
}
