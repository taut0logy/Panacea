package com.project.panacea;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.project.panacea.databinding.ActivityMainBinding;

public class HealthTipsActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_health_tips);



//        bottomNavigation = findViewById(R.id.bottom_navigation);
//
//        //By default selecting a fragment
//        getSupportFragmentManager().beginTransaction().replace(R.id.children, new ChildrenFragment()).commit();
//        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                int id = menuItem.getItemId();
//                if (id == R.id.children) {
//                    Toast.makeText(HealthTipsActivity.this, "Children", Toast.LENGTH_SHORT).show();
//                } else if (id == R.id.pregnant_women) {
//                    Toast.makeText(HealthTipsActivity.this, "Pregnant", Toast.LENGTH_SHORT).show();
//                } else if (id == R.id.mental_health) {
//                    Toast.makeText(HealthTipsActivity.this, "Mental", Toast.LENGTH_SHORT).show();
//                } else if (id == R.id.hypertension) {
//                    Toast.makeText(HealthTipsActivity.this, "Hypertension", Toast.LENGTH_SHORT).show();
//                }
//                return true;
//            }
//        });


    }
}