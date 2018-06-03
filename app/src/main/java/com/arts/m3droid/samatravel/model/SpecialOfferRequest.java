package com.arts.m3droid.samatravel.model;

public class SpecialOfferRequest {

    // will contain user id and employee id, the employee id must be checked before anything
    //because only one employee must be involved within one offer
    private String name,number;
    private long dateFrom,dateTo;
    boolean packadgeOnly, airplaneOnly, both;

}
