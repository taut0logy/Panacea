package com.project.panacea;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ContactActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        TextView phoneNumberTextView = findViewById(R.id.phoneNumber);
        TextView emailTextView = findViewById(R.id.email);
        TextView addressTextView = findViewById(R.id.address);

        // Set text values
        phoneNumberTextView.setText("01575633177");
        emailTextView.setText("sanzana526@gmail.com");
        addressTextView.setText("Khulna University Of Engineering and Technology, Khulna");

        // Set click listeners
        phoneNumberTextView.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:01575633177"));
            startActivity(intent);
        });

        emailTextView.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:sanzana526@gmail.com"));
            startActivity(intent);
        });

        addressTextView.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.google.com/maps/search/KUET+map/@22.8992533,89.5018612,17z/data=!3m1!4b1?entry=ttu"));
            startActivity(intent);
        });
    }
}
