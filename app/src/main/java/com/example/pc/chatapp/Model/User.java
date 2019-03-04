package com.example.pc.chatapp.Model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    public String name;
    public String email;
    public String mobile;
    public String dob;
    public String status;
    public String photo;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public User() {
    }

    public User(String name, String email, String mobile, String dob, String status ,String photo) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.dob = dob;
        this.status = status;
        this.photo=photo;
    }


}