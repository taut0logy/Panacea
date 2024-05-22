package com.project.panacea;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HealthTipsActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigation;
    Button backtohome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_tips);

        bottomNavigation = findViewById(R.id.bottom_navigation);

        // Set default selected fragment
        if (savedInstanceState == null) {
            loadFragment(new ChildFragment());
        }

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                int itemId = item.getItemId();
                if (itemId == R.id.child) {
                    selectedFragment = new ChildFragment();
                } else if (itemId == R.id.diabetic) {
                    selectedFragment = new DiabeticFragment();
                } else if (itemId == R.id.elderly) {
                    selectedFragment = new ElderlyFragment();
                } else if (itemId == R.id.hypertension) {
                    selectedFragment = new HypertensionFragment();
                }

                if (selectedFragment != null) {
                    loadFragment(selectedFragment);
                }
                return true;
            }
        });

        backtohome = findViewById(R.id.back_button);
        backtohome.setOnClickListener(v -> startActivity(new Intent(HealthTipsActivity.this, HomeActivity.class)));
    }

    public void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
