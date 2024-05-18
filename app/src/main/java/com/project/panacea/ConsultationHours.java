package com.project.panacea;

import android.util.Pair;

import java.io.Serializable;
import java.util.HashMap;

public class ConsultationHours implements Serializable {

    private final HashMap<Weekday, Pair<Double, Double>> consultationHours;

    public ConsultationHours() {
        this.consultationHours = new HashMap<Weekday, Pair<Double, Double>>();
    }

    public void setHours(Weekday day, double start, double end) {
        if(day == null)
            throw new IllegalArgumentException("Invalid day");
        if(start < 0 || start > 24 || end < 0 || end > 24 || start > end)
            throw new IllegalArgumentException("Invalid hours");
        this.consultationHours.put(day, new Pair<Double, Double>(start, end));
    }

    public Pair<Double, Double> getHours(Weekday day) {
        return this.consultationHours.get(day);
    }

    public boolean isAvailable(Weekday day, double time) {
        Pair<Double, Double> hours = this.consultationHours.get(day);
        if (hours == null) {
            return false;
        }
        return time >= hours.first && time <= hours.second;
    }



}
