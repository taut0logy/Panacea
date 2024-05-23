package com.project.panacea;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class DoctorListActivity extends AppCompatActivity {

    private DoctorManager doctorManager = DoctorManager.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doctor_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getActionBar().setTitle("Doctors");

        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("doctors");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Doctor> doctors = new ArrayList<>();
                ArrayList<HashMap<String, Object>> value = (ArrayList<HashMap<String, Object>>) snapshot.getValue();
                for(int i = 0; i < value.size(); i++) {
                    HashMap<String, Object> doctor = value.get(i);
                    Doctor newDoctor = new Doctor();
                    newDoctor.setName((String) doctor.get("name"));
                    newDoctor.setGender(Gender.valueOf((String) doctor.get("gender")));
                    long phoneNumber = (long) doctor.get("phone_number");
                    newDoctor.setPhoneNumber(String.valueOf(phoneNumber));
                    newDoctor.setEmail((String) doctor.get("email"));
                    String workStartDate = (String) doctor.get("workStartDate");
                    int day = Integer.parseInt(workStartDate.split("/")[0]);
                    int month = Integer.parseInt(workStartDate.split("/")[1]);
                    int year = Integer.parseInt(workStartDate.split("/")[2]);
                    Date date = new Date(year, month, day);
                    newDoctor.setWorkStartDate(date);
                    newDoctor.setDepartment((String) doctor.get("department"));
                    newDoctor.setAvailable((boolean) doctor.get("isAvailable"));
                    ArrayList<String> expertise =new ArrayList<>();
                    newDoctor.setExpertise(expertise);
                    ConsultationHours c=new ConsultationHours();
                    newDoctor.setConsultationHours(c);
                    doctors.add(newDoctor);
                }
                DoctorAdapter doctorAdapter = new DoctorAdapter(DoctorListActivity.this, doctors);
                RecyclerView recyclerView = findViewById(R.id.rvDoctorList);
                recyclerView.setAdapter(doctorAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("DoctorManager", "Failed to read value.", error.toException());
            }
        });

        //Log.e("DoctorListActivity", "onCreate: " + doctors.size());

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.home_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.view_profile) {
//            Intent intent = new Intent(this, ProfileActivity.class);
//            startActivity(intent);
//            return true;
//        }
//        if(item.getItemId() == R.id.logout) {
//            Intent intent = new Intent(this, LoginActivity.class);
//            FirebaseAuth.getInstance().signOut();
//            startActivity(intent);
//            finish();
//            return true;
//        }
//        if(item.getItemId() == R.id.edit_profile) {
//            Intent intent = new Intent(this, EditProfileActivity.class);
//            startActivity(intent);
//            finish();
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}