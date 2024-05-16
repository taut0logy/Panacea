package com.project.panacea;

public class Appointment {
    private String patientName;
    private String doctorName;

    private String date;
    private String time;
    private int status;

    public Appointment(String patientName, String doctorName, String date, String time, int status) {
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public int getStatus() {
        return status;
    }
}
