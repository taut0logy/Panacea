package com.project.panacea;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class InfoActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infoactivity);

        // Find the CardViews by their IDs
        CardView dhakaCard = findViewById(R.id.Dhaka);
        CardView khulnaCard = findViewById(R.id.Khulna);
        CardView barisalCard = findViewById(R.id.Barisal);

        CardView chattagramCard = findViewById(R.id.Chattagram);


        // Set OnClickListener for each CardView
        dhakaCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InfoActivity.this, Chattogram.class));
            }
        });

        khulnaCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InfoActivity.this, Khulna.class));
            }
        });

        barisalCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InfoActivity.this, Barisal.class));
            }
        });



        chattagramCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InfoActivity.this, Chattogram.class));
            }
        });


    }
}