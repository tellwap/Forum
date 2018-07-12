package com.fahamu.waptell.chat.forum.model;

public class Messages {

    private String senderId;
    private String message;
    private String time;

    public Messages(){

    }
    public Messages(String senderId, String message, String time) {
        this.senderId = senderId;
        this.message = message;
        this.time = time;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
