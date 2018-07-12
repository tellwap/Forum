package com.fahamu.waptell.chat.forum.model;

public class User {

    private String name;
    private String email;
    private String id;
    private String photo;

    public User(){

    }

    public User(String name, String email, String id, String photo) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
