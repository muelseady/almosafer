package com.arts.m3droid.samatravel.model;

import android.os.Parcel;
import android.os.Parcelable;

public class RequestingOfferDetails implements Parcelable {

    // will contain user id and employee id, the employee id must be checked before anything
    //because only one employee must be involved within one offer
    private String userName;
    private String userNumber;
    private String userId;
    private String userNotes;
    private String offerName;
    private String dateFrom, dateTo;
    private String placeFrom, placeTo;
    private int adults, children, infants, over65;
    private String offerDetails, offerImageUrl;
    private String packages, hotels, budget, budgetCurrency;
    private int state;
    private String offerKey;
    private String userIdToken, empIdToken;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public RequestingOfferDetails() {
        //Many functions require empty constructor
    }

    public RequestingOfferDetails(String name, String number, String dateFrom, String dateTo, String userId,
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

    public RequestingOfferDetails(String userName, String userNumber, String dateFrom, String dateTo, String userId,
                                  int adults, int children, int infants, int over65, String userNotes,
                                  String offerName,
                                  String packages, String hotels, String placeFrom, String placeTo,
                                  String budget, String budgetCurrency) {
        this.userName = userName;
        this.userNumber = userNumber;
        this.userId = userId;
        this.userNotes = userNotes;
        this.offerName = offerName;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.adults = adults;
        this.children = children;
        this.infants = infants;
        this.over65 = over65;
        this.packages = packages;
        this.hotels = hotels;
        this.budget = budget;
        this.budgetCurrency = budgetCurrency;
        this.placeFrom = placeFrom;
        this.placeTo = placeTo;
    }

    public String getOfferKey() {
        return offerKey;

    }

    public void setOfferKey(String offerKey) {
        this.offerKey = offerKey;
    }

    public String getPlaceFrom() {
        return placeFrom;
    }

    public String getPlaceTo() {
        return placeTo;
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

    public String getPackages() {
        return packages;
    }

    public String getHotels() {
        return hotels;
    }

    public String getBudget() {
        return budget;
    }

    public String getBudgetCurrency() {
        return budgetCurrency;
    }

    public String getOfferDetails() {
        return offerDetails;
    }

    public String getOfferImageUrl() {
        return offerImageUrl;
    }

    public void setEmpIdToken(String empIdToken) {
        this.empIdToken = empIdToken;
    }

    public String getEmpIdToken() {
        return empIdToken;
    }

    public void setUserIdToken(String userIdToken) {
        this.userIdToken = userIdToken;
    }

    public String getUserIdToken() {
        return userIdToken;
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
        dest.writeString(this.placeFrom);
        dest.writeString(this.placeTo);
        dest.writeInt(this.adults);
        dest.writeInt(this.children);
        dest.writeInt(this.infants);
        dest.writeInt(this.over65);
        dest.writeString(this.offerDetails);
        dest.writeString(this.offerImageUrl);
        dest.writeString(this.packages);
        dest.writeString(this.hotels);
        dest.writeString(this.budget);
        dest.writeString(this.budgetCurrency);
        dest.writeInt(this.state);
        dest.writeString(this.offerKey);
        dest.writeString(this.userIdToken);
        dest.writeString(this.empIdToken);
    }

    protected RequestingOfferDetails(Parcel in) {
        this.userName = in.readString();
        this.userNumber = in.readString();
        this.userId = in.readString();
        this.userNotes = in.readString();
        this.offerName = in.readString();
        this.dateFrom = in.readString();
        this.dateTo = in.readString();
        this.placeFrom = in.readString();
        this.placeTo = in.readString();
        this.adults = in.readInt();
        this.children = in.readInt();
        this.infants = in.readInt();
        this.over65 = in.readInt();
        this.offerDetails = in.readString();
        this.offerImageUrl = in.readString();
        this.packages = in.readString();
        this.hotels = in.readString();
        this.budget = in.readString();
        this.budgetCurrency = in.readString();
        this.state = in.readInt();
        this.offerKey = in.readString();
        this.userIdToken = in.readString();
        this.empIdToken = in.readString();
    }

    public static final Creator<RequestingOfferDetails> CREATOR = new Creator<RequestingOfferDetails>() {
        @Override
        public RequestingOfferDetails createFromParcel(Parcel source) {
            return new RequestingOfferDetails(source);
        }

        @Override
        public RequestingOfferDetails[] newArray(int size) {
            return new RequestingOfferDetails[size];
        }
    };
}
