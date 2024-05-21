package com.project.panacea;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class RecordActivity extends AppCompatActivity {

    private TextInputEditText etHeartRate;
    private TextInputEditText etSystolicPressure;
    private TextInputEditText etDiastolicPressure;
    private TextInputEditText etComment;
    private Button btnRecord;
    private Button showRecord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_record);



        etHeartRate = findViewById(R.id.heartRate);
        etSystolicPressure = findViewById(R.id.systolicPressure);
        etDiastolicPressure = findViewById(R.id.diastolicPressure);
        etComment = findViewById(R.id.editTextTextMultiLine);
        btnRecord = findViewById(R.id.addBtn);
        showRecord = findViewById(R.id.showRecords);

        btnRecord.setOnClickListener(v -> {
            if (!validateInput()) {
                return;
            }
            int heartRate = Integer.parseInt(etHeartRate.getText().toString());
            int systolicPressure = Integer.parseInt(etSystolicPressure.getText().toString());
            int diastolicPressure = Integer.parseInt(etDiastolicPressure.getText().toString());
            String comment = etComment.getText().toString();
            RecordManager.getInstance().addRecord(heartRate, systolicPressure, diastolicPressure, comment);
            Toast.makeText(this, "Record added successfully", Toast.LENGTH_SHORT).show();
            finish();
        });

        showRecord.setOnClickListener(v -> {
            startActivity(new Intent(RecordActivity.this, ShowRecords.class));
        });
    }




    private boolean validateInput() {
        if (etHeartRate.getText().toString().isEmpty()) {
            etHeartRate.setError("Heart rate is required");
            etHeartRate.requestFocus();
            return false;
        }
        if (etSystolicPressure.getText().toString().isEmpty()) {
            etSystolicPressure.setError("Systolic pressure is required");
            etSystolicPressure.requestFocus();
            return false;
        }
        if (etDiastolicPressure.getText().toString().isEmpty()) {
            etDiastolicPressure.setError("Diastolic pressure is required");
            etDiastolicPressure.requestFocus();
            return false;
        }
        if (etComment.getText().toString().isEmpty()) {
            etComment.setError("Comment is required");
            etComment.requestFocus();
            return false;
        }

        if(Integer.parseInt(etHeartRate.getText().toString()) < 0 || Integer.parseInt(etHeartRate.getText().toString()) > 200){
            etHeartRate.setError("Heart rate must be between 0 and 200");
            etHeartRate.requestFocus();
            return false;
        }

        if(Integer.parseInt(etSystolicPressure.getText().toString()) < 0 || Integer.parseInt(etSystolicPressure.getText().toString()) > 240){
            etSystolicPressure.setError("Systolic pressure must be between 0 and 240");
            etSystolicPressure.requestFocus();
            return false;
        }

        if(Integer.parseInt(etDiastolicPressure.getText().toString()) < 0 || Integer.parseInt(etDiastolicPressure.getText().toString()) > 240){
            etDiastolicPressure.setError("Diastolic pressure must be between 0 and 240");
            etDiastolicPressure.requestFocus();
            return false;
        }

        if(Integer.parseInt(etSystolicPressure.getText().toString()) < Integer.parseInt(etDiastolicPressure.getText().toString())){
            etSystolicPressure.setError("Systolic pressure must be greater than diastolic pressure");
            etSystolicPressure.requestFocus();
            return false;
        }

        return true;


    }

}