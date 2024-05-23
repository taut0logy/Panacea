package com.project.panacea;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Barisal extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bari);

        // Find TextViews
        TextView address1TextView = findViewById(R.id.addressb1);
        TextView phoneNumber1TextView = findViewById(R.id.phoneNumberb1);
        TextView address2TextView = findViewById(R.id.addressb2);
        TextView phoneNumber2TextView = findViewById(R.id.phoneNumberb2);


        // Set text values
        address1TextView.setText("Sher-e-Bangla Medical College, Barisal (SBMC)");
        phoneNumber1TextView.setText("01715613445 ");

        address2TextView.setText("Grameen GC Eye Hospital, Barishal");
        phoneNumber2TextView.setText("0431-71740");



        // Set click listeners
        address1TextView.setOnClickListener(v -> openMap("https://www.google.com/maps?sca_esv=bd0d05e7905db8d3&output=search&q=barisal+medical+college+phone+number&source=lnms&entry=mc&ved=1t:200715&ictx=111"));
        address2TextView.setOnClickListener(v -> openMap("https://www.google.com/maps?sca_esv=bd0d05e7905db8d3&output=search&q=grameen+gc+eye+hospital+barisal+phone+number&source=lnms&entry=mc&ved=1t:200715&ictx=111"));

        phoneNumber1TextView.setOnClickListener(v -> dialPhoneNumber("01715613445 "));
        phoneNumber2TextView.setOnClickListener(v -> dialPhoneNumber("0431-71740"));

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
