package com.arts.m3droid.samatravel.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class SpecialOfferRequest implements Parcelable {

    // will contain user id and employee id, the employee id must be checked before anything
    //because only one employee must be involved within one offer
    private String name, notes;
    private long dateFrom, dateTo;
    private int number, adults, children, infants, over65;
    boolean tourismOnly, airplaneOnly, both;

    public SpecialOfferRequest(String name, int number, long dateFrom, long dateTo,
                               int adults, int children, int infants, int over65,
                               boolean tourismOnly, boolean airplaneOnly, boolean both,
                               String notes) {
        this.name = name;
        this.notes = notes;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.number = number;
        this.adults = adults;
        this.children = children;
        this.infants = infants;
        this.over65 = over65;
        this.tourismOnly = tourismOnly;
        this.airplaneOnly = airplaneOnly;
        this.both = both;
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

    public int getNumber() {
        return number;
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

    public boolean isTourismOnly() {
        return tourismOnly;
    }

    public boolean isAirplaneOnly() {
        return airplaneOnly;
    }

    public boolean isBoth() {
        return both;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.notes);
        dest.writeLong(this.dateFrom);
        dest.writeLong(this.dateTo);
        dest.writeInt(this.number);
        dest.writeInt(this.adults);
        dest.writeInt(this.children);
        dest.writeInt(this.infants);
        dest.writeInt(this.over65);
        dest.writeByte(this.tourismOnly ? (byte) 1 : (byte) 0);
        dest.writeByte(this.airplaneOnly ? (byte) 1 : (byte) 0);
        dest.writeByte(this.both ? (byte) 1 : (byte) 0);
    }

    protected SpecialOfferRequest(Parcel in) {
        this.name = in.readString();
        this.notes = in.readString();
        this.dateFrom = in.readLong();
        this.dateTo = in.readLong();
        this.number = in.readInt();
        this.adults = in.readInt();
        this.children = in.readInt();
        this.infants = in.readInt();
        this.over65 = in.readInt();
        this.tourismOnly = in.readByte() != 0;
        this.airplaneOnly = in.readByte() != 0;
        this.both = in.readByte() != 0;
    }

    public static final Parcelable.Creator<SpecialOfferRequest> CREATOR = new Parcelable.Creator<SpecialOfferRequest>() {
        @NonNull
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
