package com.project.panacea;

import java.util.ArrayList;

public class Doctor {
    private String name;
    private String gender;
    private String phoneNumber;
    private String email;
    private int yearsOfExperience;

    private String department;
    private String consultationHours;

    private boolean isAvailable;

    private ArrayList<String> expertise = new ArrayList<String>();



    public Doctor(String name, String gender, String phoneNumber, String email, int yearsOfExperience, String department, String consultationHours, boolean isAvailable, ArrayList<String> expertise) {
        this.name = name;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.yearsOfExperience = yearsOfExperience;
        this.department = department;
        this.consultationHours = consultationHours;
        this.isAvailable = isAvailable;
        this.expertise = expertise;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public String getDepartment() {
        return department;
    }

    public String getConsultationHours() {
        return consultationHours;
    }

    public boolean getIsAvailable() {
        return isAvailable;
    }

    public ArrayList<String> getExpertise() {
        return expertise;
    }


}
