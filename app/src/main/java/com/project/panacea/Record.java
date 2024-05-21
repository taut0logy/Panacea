package com.project.panacea;

public class Record {
    public int heartRate;
    public int systolicPressure;
    public int diastolicPressure;
    public String comment;

    public Record() {}
    public Record(int heartRate, int systolicPressure, int diastolicPressure, String comment) {
        this.heartRate = heartRate;
        this.systolicPressure = systolicPressure;
        this.diastolicPressure = diastolicPressure;
        this.comment = comment;
    }
}
