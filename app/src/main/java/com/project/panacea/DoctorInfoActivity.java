package com.project.panacea;

import android.os.Bundle;
import android.util.Pair;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Date;

public class DoctorInfoActivity extends AppCompatActivity {
    private ImageView doctorImage;
    private TextView doctorName;
    private TextView doctorDepartment;
    private TextView doctorExperience;
    private ImageView doctorGender;
    private TextView doctorPhoneNumber;
    private TextView doctorEmail;

    private TextView doctorAvailability;

    private Doctor doctor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doctor_info);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        doctor = (Doctor) getIntent().getSerializableExtra("doctor");

        doctorImage = findViewById(R.id.docAvatar);
        doctorName = findViewById(R.id.tvDoctorName);
        doctorDepartment = findViewById(R.id.tvDoctorDepartment);
        doctorExperience = findViewById(R.id.tvDoctorExperience);
        doctorGender = findViewById(R.id.ivDoctorGender);
        doctorPhoneNumber = findViewById(R.id.textView7);
        doctorEmail = findViewById(R.id.tvDoctorEmail);
        doctorAvailability = findViewById(R.id.tvDoctorAvailability);

        setDoctorInfo(doctor);


    }

    public void setDoctorInfo(Doctor doctor) {
        // Set the doctor's information in the activity
        if(doctor.getGender() ==Gender.FEMALE) {
            doctorGender.setImageResource(R.drawable.doctor_female);
        } else {
            doctorGender.setImageResource(R.drawable.doctor_male);
        }

        doctorName.setText(doctor.getName());
        doctorDepartment.setText(doctor.getDepartment());
        Date currentDate = new Date();
        long diff = currentDate.getTime() - doctor.getWorkStartDate().getTime();
        long diffYears = diff / (24L * 60 * 60 * 1000 * 365);
        doctorExperience.setText(diffYears + " years of experience");
        doctorPhoneNumber.setText(doctor.getPhoneNumber());
        doctorEmail.setText(doctor.getEmail());
        if(!doctor.getIsAvailable()){
            doctorAvailability.setText("Not available");
            return;
        }
        ConsultationHours consultationHours = doctor.getConsultationHours();
        String availability = "";
        for(Weekday day : Weekday.values()) {
            Pair<Double, Double> hours = consultationHours.getHours(day);
            availability += day.toString() + ": ";
            if(hours != null) {
                availability += hours.first + " - " + hours.second;
            } else {
                availability += "Not available";
            }
            availability += "\n";
        }

    }
}