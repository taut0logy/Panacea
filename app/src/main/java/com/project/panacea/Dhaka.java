package com.project.panacea;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Dhaka extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dhaka);

        // Find TextViews
        TextView address1TextView = findViewById(R.id.address1);
        TextView phoneNumber1TextView = findViewById(R.id.phoneNumber1);
        TextView address2TextView = findViewById(R.id.address2);
        TextView phoneNumber2TextView = findViewById(R.id.phoneNumber2);
        TextView address3TextView = findViewById(R.id.address3);
        TextView phoneNumber3TextView = findViewById(R.id.phoneNumber3);
        TextView address4TextView = findViewById(R.id.address4);
        TextView phoneNumber4TextView = findViewById(R.id.phoneNumber4);

        // Set text values
        address1TextView.setText("Bangabandhu Sheikh Mujib Medical University (BSMMU)");
        phoneNumber1TextView.setText("01234567890");

        address2TextView.setText("DHAKA MEDICAL COLLEGE");
        phoneNumber2TextView.setText("0255165001");

        address3TextView.setText("Square Hospitals Ltd");
        phoneNumber3TextView.setText("09610-010616");

        address4TextView.setText("Evercare Hospitals");
        phoneNumber4TextView.setText("02-55037242");

        // Set click listeners
        address1TextView.setOnClickListener(v -> openMap("https://www.google.com/maps/place/Bangabandhu+Sheikh+Mujib+Medical+University/@23.7383718,90.3927915,17z/data=!3m1!4b1!4m6!3m5!1s0x3755b8954649cee5:0x3bdcd530be93b17a!8m2!3d23.7383718!4d90.3953664!16s%2Fm%2F027ycys?entry=ttu"));
        address2TextView.setOnClickListener(v -> openMap("https://www.google.com/maps?sca_esv=e212f428948d84a5&output=search&q=dhaka+medical+college+phone+number&source=lnms&entry=mc&ved=1t:200715&ictx=111"));
        address3TextView.setOnClickListener(v -> openMap("https://www.google.com/maps/search/square+hospital/@23.7525276,90.3788892,17z/data=!3m1!4b1?entry=ttu"));
        address4TextView.setOnClickListener(v -> openMap("https://www.google.com/maps/search/Evercare+Hospitals+bd+phone+number/@23.312817,89.3327384,9z/data=!3m1!4b1?entry=ttu"));

        phoneNumber1TextView.setOnClickListener(v -> dialPhoneNumber("0255165001"));
        phoneNumber2TextView.setOnClickListener(v -> dialPhoneNumber("02345678901"));
        phoneNumber3TextView.setOnClickListener(v -> dialPhoneNumber("09610-010616"));
        phoneNumber4TextView.setOnClickListener(v -> dialPhoneNumber("02-55037242"));
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
