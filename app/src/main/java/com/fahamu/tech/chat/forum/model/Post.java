package com.fahamu.tech.chat.forum.model;

public class Post {

    private String title;
    private String description;
    private String userId;
    private String time;
    private String docId;
    private String userPhoto;

    public Post(){

    }
    public Post(String title, String description, String userId, String time,String userPhoto) {
        this.title = title;
        this.description = description;
        this.userId = userId;
        this.time = time;
        this.userPhoto=userPhoto;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getDocId() {
        return docId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
