package com.project.panacea;

import java.util.Date;

//gender enum


public class User {
    private String name;
    private Date dateOfBirth;
    private String email;
    private String phoneNumber;
    private Gender gender;

    public User() {}

    public User(String name, Date dateOfBirth, String email, String phoneNumber) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
