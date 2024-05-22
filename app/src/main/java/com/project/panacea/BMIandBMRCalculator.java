package com.project.panacea;

import java.util.ArrayList;

public class BMIandBMRCalculator {
    private ArrayList  <BMIandBMRObserver> observers = new ArrayList<>();
    private double bmi;
    private double bmr;
    public void addObserver(BMIandBMRObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(BMIandBMRObserver observer) {
        observers.remove(observer);
    }

    public void calculateBMIAndBMR(int age, float weight, float heightFeet, float heightInches) {
        float height = (heightFeet * 12) + heightInches;
        float heightMeters = height * 0.0254f; // convert to meters
        bmi = weight / (heightMeters * heightMeters);

        float heightCm = height * 2.54f; // convert to cm
        bmr = 10 * weight + (6.25 * heightCm) - (5 * age) + 5; // Adjust for gender if needed

        notifyObservers();
    }

    private void notifyObservers() {
        for (BMIandBMRObserver observer : observers) {
            observer.update(bmi, bmr);
        }
    }
}
