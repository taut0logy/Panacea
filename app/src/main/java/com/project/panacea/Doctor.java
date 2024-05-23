package com.project.panacea;

import android.util.Pair;

import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Doctor implements Serializable {
    private String name;
    private Gender gender;
    private String phoneNumber;
    private String email;
    private Date workStartDate;

    private String department;
    private ConsultationHours consultationHours;

    private boolean isAvailable;

    private ArrayList<String> expertise = new ArrayList<String>();


    public Doctor() {}

    public Doctor(String name, Gender gender, String phoneNumber, String email, Date workStartDate, String department, ConsultationHours consultationHours, boolean isAvailable, ArrayList<String> expertise) {
        this.name = name;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.workStartDate = workStartDate;
        this.department = department;
        this.consultationHours = consultationHours;
        this.isAvailable = isAvailable;
        this.expertise = expertise;
    }

    public Doctor(JsonObject doctorJson) {
        this.name = doctorJson.get("name").getAsString();
        this.department = doctorJson.get("department").getAsString();
        this.email = doctorJson.get("email").getAsString();
        this.phoneNumber = doctorJson.get("phone_number").getAsString();
        String gender = doctorJson.get("gender").getAsString();
        this.gender = Gender.valueOf(gender);
        this.isAvailable = doctorJson.get("is_available").getAsBoolean();
        String workStartDate = doctorJson.get("work_start_date").getAsString();
        Date date = new Date(workStartDate);
        this.workStartDate = date;
        JsonObject consultationHoursJson = doctorJson.get("consultation_hours").getAsJsonObject();
        ConsultationHours consultationHours = new ConsultationHours();
        for(String key : consultationHoursJson.keySet()) {
            Weekday weekday = Weekday.valueOf(key);
            String value = consultationHoursJson.get(key).getAsString();
            consultationHours.setHours(weekday, Double.parseDouble(value.split("-")[0]), Double.parseDouble(value.split("-")[1]));
        }
        this.consultationHours = consultationHours;
        ArrayList<String> expertise = new ArrayList<String>();
        for(int i = 0; i < doctorJson.get("expertise").getAsJsonArray().size(); i++) {
            expertise.add(doctorJson.get("expertise").getAsJsonArray().get(i).getAsString());
        }
        this.expertise = expertise;
    }

    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public Date getWorkStartDate() {
        return workStartDate;
    }

    public int getYearsOfExperience() {
        Date currentDate = new Date();
        long diff = currentDate.getTime() - workStartDate.getTime();
        long diffYears = diff / (24L * 60 * 60 * 1000 * 365);
        return (int) diffYears;
    }

    public String getDepartment() {
        return department;
    }

    public ConsultationHours getConsultationHours() {
        return consultationHours;
    }

    public boolean getIsAvailable() {
        return isAvailable;
    }

    public ArrayList<String> getExpertise() {
        return expertise;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setWorkStartDate(Date workStartDate) {
        this.workStartDate = workStartDate;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setConsultationHours(ConsultationHours consultationHours) {
        this.consultationHours = consultationHours;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public void setExpertise(ArrayList<String> expertise) {
        this.expertise = expertise;
    }
}
