package com.fahamu.tech.chat.forum.model;

public class Receipt {
    private String date;
    private String amount;
    private String userId;
    private String reference;

    public Receipt(){

    }

    public Receipt(String date, String amount, String userId, String reference) {
        this.date = date;
        this.amount = amount;
        this.userId = userId;
        this.reference = reference;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
