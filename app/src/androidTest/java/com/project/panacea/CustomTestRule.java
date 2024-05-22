package com.project.panacea;

import androidx.test.rule.ActivityTestRule;
import android.app.Activity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Field;

public class CustomTestRule<T extends Activity> extends ActivityTestRule<T> {
    private FirebaseAuth mockFirebaseAuth;
    private FirebaseDatabase mockFirebaseDatabase;

    public CustomTestRule(Class<T> activityClass, FirebaseAuth mockFirebaseAuth, FirebaseDatabase mockFirebaseDatabase) {
        super(activityClass);
        this.mockFirebaseAuth = mockFirebaseAuth;
        this.mockFirebaseDatabase = mockFirebaseDatabase;
    }

    @Override
    protected void beforeActivityLaunched() {
        super.beforeActivityLaunched();
        AuthUtility authUtility = AuthUtility.getInstance();
        try {
            Field mAuthField = AuthUtility.class.getDeclaredField("mAuth");
            Field databaseField = AuthUtility.class.getDeclaredField("database");

            mAuthField.setAccessible(true);
            databaseField.setAccessible(true);

            mAuthField.set(authUtility, mockFirebaseAuth);
            databaseField.set(authUtility, mockFirebaseDatabase);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set mock instances", e);
        }
    }
}