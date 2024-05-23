package com.project.panacea;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Chattogram extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cha);

        // Find TextViews
        TextView address1TextView = findViewById(R.id.address5);
        TextView phoneNumber1TextView = findViewById(R.id.phoneNumber5);
        TextView address2TextView = findViewById(R.id.address6);
        TextView phoneNumber2TextView = findViewById(R.id.phoneNumber6);
        TextView address3TextView = findViewById(R.id.address7);
        TextView phoneNumber3TextView = findViewById(R.id.phoneNumber7);
        TextView address4TextView = findViewById(R.id.address8);
        TextView phoneNumber4TextView = findViewById(R.id.phoneNumber8);

        // Set text values
        address1TextView.setText("National Hospital Chattogram");
        phoneNumber1TextView.setText("01855-677500");

        address2TextView.setText("Chittagong Medical College Hospital");
        phoneNumber2TextView.setText("+8802333350180");

        address3TextView.setText("Evercare Hospital Chattogram");
        phoneNumber3TextView.setText("09612-310663");

        address4TextView.setText("Memon Maternity Hospital");
        phoneNumber4TextView.setText("031-617169");

        // Set click listeners
        address1TextView.setOnClickListener(v -> openMap("https://www.google.com/maps/place/National+Hospital+Chattogram+%26+Sigma+Lab+Ltd./@22.3549227,91.8224243,17z/data=!3m1!4b1!4m6!3m5!1s0x30acd89bc84c503b:0xb95b0623d8dff91!8m2!3d22.3549227!4d91.8249992!16s%2Fg%2F1vnrhght?entry=ttu"));
        address2TextView.setOnClickListener(v -> openMap("https://www.google.com/maps/search/chittagong+medical+college+phone+number/@22.3599595,91.8287737,17z/data=!3m1!4b1?entry=ttu"));
        address3TextView.setOnClickListener(v -> openMap("https://www.google.com/maps/place/Evercare+Hospital+Chattogram/@22.4018988,91.8469777,17z/data=!3m1!4b1!4m6!3m5!1s0x30ad2782e6299a61:0x459db4c66339430c!8m2!3d22.4018988!4d91.8495526!16s%2Fg%2F11j90mfh9j?entry=ttu"));
        address4TextView.setOnClickListener(v -> openMap("https://www.google.com/maps/search/memon+maternity+hospital+chittagong+phone+number/@22.3322405,91.8300384,17z/data=!3m1!4b1?entry=ttu"));

        phoneNumber1TextView.setOnClickListener(v -> dialPhoneNumber("01855-677500"));
        phoneNumber2TextView.setOnClickListener(v -> dialPhoneNumber("+8802333350180"));
        phoneNumber3TextView.setOnClickListener(v -> dialPhoneNumber("09612-310663"));
        phoneNumber4TextView.setOnClickListener(v -> dialPhoneNumber("031-617169"));
    }

    private void openMap(String address) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse( address));
        startActivity(intent);
    }

    private void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }
}