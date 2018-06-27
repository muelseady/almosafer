package com.arts.m3droid.samatravel.model;

public class Message {

    private String senderUID;
    private String messageBody;
    private String imageUrl;
    private String senderCategory;
    private String senderName;

    public Message() {
    }

    public Message(String senderUID, String messageBody, String imageUrl, String senderCategory, String senderName) {
        this.senderUID = senderUID;
        this.messageBody = messageBody;
        this.imageUrl = imageUrl;
        this.senderCategory = senderCategory;
        this.senderName = senderName;
    }

    public String getSenderCategory() {
        return senderCategory;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getSenderUID() {
        return senderUID;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
