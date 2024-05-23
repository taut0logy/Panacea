package com.project.panacea;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class DoctorManager {

    private static DoctorManager instance = null;
    private DatabaseReference mDatabaseReference;

    private DoctorManager() {
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("doctors");
    }

    public static DoctorManager getInstance() {
        if(instance == null) {
            instance = new DoctorManager();
        }
        return instance;
    }



}
