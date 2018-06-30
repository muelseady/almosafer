package com.arts.m3droid.samatravel.model;

public class RequestingOffer {
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

}
