package com.arts.m3droid.samatravel.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SpecialOfferRequest implements Parcelable {

    // will contain user id and employee id, the employee id must be checked before anything
    //because only one employee must be involved within one offer
    private String name;
    private String number;
    private String userId;
    private String notes;
    private String offerName;
    private long dateFrom, dateTo;
    private int adults, children, infants, over65;

    public SpecialOfferRequest(String name, String number, long dateFrom, long dateTo, String userId,
                               int adults, int children, int infants, int over65, String notes,
                               String offerName) {
        this.name = name;
        this.userId = userId;
        this.notes = notes;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.number = number;
        this.adults = adults;
        this.children = children;
        this.infants = infants;
        this.over65 = over65;
        this.offerName = offerName;
    }


    public String getName() {
        return name;
    }

    public String getNotes() {
        return notes;
    }

    public long getDateFrom() {
        return dateFrom;
    }

    public long getDateTo() {
        return dateTo;
    }

    public String getNumber() {
        return number;
    }

    public int getAdults() {
        return adults;
    }

    public String getUserId() {
        return userId;
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

    public String getOfferName() {
        return offerName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.number);
        dest.writeString(this.userId);
        dest.writeString(this.notes);
        dest.writeString(this.offerName);
        dest.writeLong(this.dateFrom);
        dest.writeLong(this.dateTo);
        dest.writeInt(this.adults);
        dest.writeInt(this.children);
        dest.writeInt(this.infants);
        dest.writeInt(this.over65);
    }

    protected SpecialOfferRequest(Parcel in) {
        this.name = in.readString();
        this.number = in.readString();
        this.userId = in.readString();
        this.notes = in.readString();
        this.offerName = in.readString();
        this.dateFrom = in.readLong();
        this.dateTo = in.readLong();
        this.adults = in.readInt();
        this.children = in.readInt();
        this.infants = in.readInt();
        this.over65 = in.readInt();
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
