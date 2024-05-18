package com.project.panacea;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;

//gender enum


public class User implements Serializable {
    private String name;
    private Date dateOfBirth;
    private String email;
    private String phoneNumber;
    private Gender gender;

    public User() {
    }

    public User(String name, Date dateOfBirth, String email, String phoneNumber, Gender gender) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public JSONObject toJSON() {
        try {
            JSONObject birthDate = new JSONObject();
            birthDate.put("year", dateOfBirth.getYear() + 1900);
            birthDate.put("month", dateOfBirth.getMonth() + 1);
            birthDate.put("day", dateOfBirth.getDate());
            JSONObject user = new JSONObject();
            user.put("name", name);
            user.put("dateOfBirth", birthDate);
            user.put("email", email);
            user.put("phoneNumber", phoneNumber);
            user.put("gender", gender);
            return user;
        } catch (JSONException e) {
            Log.e("User", "Error creating JSON object");
        }
        return null;
    }
}
