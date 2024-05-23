package com.project.panacea;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Khulna extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khulna);

        // Find TextViews
        TextView address1TextView = findViewById(R.id.address9);
        TextView phoneNumber1TextView = findViewById(R.id.phoneNumber9);
        TextView address2TextView = findViewById(R.id.address10);
        TextView phoneNumber2TextView = findViewById(R.id.phoneNumber10);

        // Set text values
        address2TextView.setText("Infectious Diseases Hospital");
        phoneNumber2TextView.setText("01937000011");

        address1TextView.setText("Khulna Medical College");
        phoneNumber1TextView.setText("01769957236");



        // Set click listeners
        address1TextView.setOnClickListener(v -> openMap("https://maps.app.goo.gl/yV7MKgCSo1m6tkWZ9"));
        address2TextView.setOnClickListener(v -> openMap("https://www.google.com/maps/place/Infectious+Diseases+Hospital/@22.8931784,89.5025549,15z/data=!4m10!1m2!2m1!1shospital+in+khulna!3m6!1s0x39ff9a2eb69eaa55:0x18629de597d899e!8m2!3d22.8995214!4d89.5157433!15sChJob3NwaXRhbCBpbiBraHVsbmGSAQhob3NwaXRhbOABAA!16s%2Fg%2F1hjhc9ll_?entry=ttu"));


        phoneNumber1TextView.setOnClickListener(v -> dialPhoneNumber("01937000011"));
        phoneNumber2TextView.setOnClickListener(v -> dialPhoneNumber("01769957236"));

    }

    private void openMap(String address) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(  address));
        startActivity(intent);
    }

    private void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }
}