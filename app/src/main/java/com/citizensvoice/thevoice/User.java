package com.citizensvoice.thevoice;

public class User {
    String user_type;
    String first_name;
    String last_name;
    String email;
    String phoneNumber;
    String password;
    String nin;
    String location;
    String city;
    String state;
    String country;
    String profile_picture;
    String user_id;

    public User(String user_type, String email, String user_id) {
        this.user_type = user_type;
        this.email = email;
        this.user_id = user_id;
    }
    public User(String user_type, String email, String user_id, String first_name, String last_name, String phoneNumber) {
        this.user_type = user_type;
        this.email = email;
        this.user_id = user_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phoneNumber = phoneNumber;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
