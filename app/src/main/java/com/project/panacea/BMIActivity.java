package com.project.panacea;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class BMIActivity extends AppCompatActivity implements BMIandBMRObserver {

    private EditText edAge, edKg, edFeet, edIns;
    private TextView error;
    private TextView bmiResult, bmrResult;
    private BMIandBMRCalculator bmiCalculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmiactivity);

        edAge = findViewById(R.id.edAge);
        edKg = findViewById(R.id.edKg);
        edFeet = findViewById(R.id.edFeet);
        edIns = findViewById(R.id.edIns);
        bmiResult = findViewById(R.id.bmiResult);
        bmrResult = findViewById(R.id.bmrResult);
        error = findViewById(R.id.errorMessage);


        bmiCalculator = new BMIandBMRCalculator();
        bmiCalculator.addObserver(this);

        findViewById(R.id.cardBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate();
            }
        });
    }

    private void calculate() {
        String ageStr = edAge.getText().toString();
        String weightStr = edKg.getText().toString();
        String feetStr = edFeet.getText().toString();
        String inchesStr = edIns.getText().toString();

        if (!ageStr.isEmpty() && !weightStr.isEmpty() && !feetStr.isEmpty() && !inchesStr.isEmpty()) {
            int age = Integer.parseInt(ageStr);
            float weight = Float.parseFloat(weightStr);
            float heightFeet = Float.parseFloat(feetStr);
            float heightInches = Float.parseFloat(inchesStr);

            if (age <= 0 || weight <= 0 || heightFeet <= 0 || heightInches < 0) {
                error.setText("Values cannot be negative");
                error.setVisibility(View.VISIBLE);
                return;
            }

            if (heightInches >= 12) {
                error.setText("Inches must be less than 12");
                error.setVisibility(View.VISIBLE);
                return;
            }

            bmiCalculator.calculateBMIAndBMR(age, weight, heightFeet, heightInches);
        }
    }

    @Override
    public void update(double bmi, double bmr) {
        bmiResult.setText(String.format("%.2f", bmi));
        bmrResult.setText(String.format("%.2f", bmr));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bmiCalculator.removeObserver(this);
    }

    public void negativeValue(){

    }

}
