package com.fahamu.tech.chat.forum.model;

public class User {

    private String name;
    private String email;
    private String id;
    private String photo;
    private String phoneNumber;
    private String address;

    public User() {

    }

    public User(String name, String email, String id, String photo, String phoneNumber, String address) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.photo = photo;
        this.phoneNumber = phoneNumber;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
