package com.arts.m3droid.samatravel.model;

public class SpecialOffer {
    private String uid, name, imageUrl, details;

    public SpecialOffer() {
    }

    public SpecialOffer(String uid, String name, String imageUrl, String details) {
        this.uid = uid;
        this.name = name;
        this.imageUrl = imageUrl;
        this.details = details;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDetails() {
        return details;
    }
}
