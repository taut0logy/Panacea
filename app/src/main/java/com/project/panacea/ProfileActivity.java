package com.project.panacea;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvName, tvEmail, tvPhoneNumber, tvDateOfBirth, tvGender;
    private ImageView avatarImageView;
    private UserUtility userUtility;
    private AuthUtility authUtility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initializeViews();
        initializeUtilities();
        loadUserData();
    }

    private void initializeViews() {
        tvName = findViewById(R.id.profileTvName);
        tvEmail = findViewById(R.id.profileTvEmail);
        tvPhoneNumber = findViewById(R.id.profileTvPhone);
        tvDateOfBirth = findViewById(R.id.profileTvDob);
        tvGender = findViewById(R.id.profileTvGender);
        avatarImageView = findViewById(R.id.profileAvatar);
    }

    private void initializeUtilities() {
        userUtility = UserUtility.getInstance();
        authUtility = AuthUtility.getInstance();
    }

    private void loadUserData() {
        authUtility.getUserUid(new AuthUtility.OnUserUidRetrievedListener() {
            @Override
            public void onSuccess(String uid) {
                userUtility.getUser(uid, new UserUtility.OnUserRetrievedListener() {
                    @Override
                    public void onSuccess(User user) {
                        populateUserDetails(user);
                        loadProfilePicture(uid);
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(ProfileActivity.this, "Error loading user data: " + message, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onError(String error) {
                Toast.makeText(ProfileActivity.this, "Error getting user UID: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateUserDetails(User user) {
        tvName.setText(user.getName());
        tvEmail.setText(user.getEmail());
        tvPhoneNumber.setText(user.getPhoneNumber());
        tvDateOfBirth.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(user.getDateOfBirth()));
        tvGender.setText(user.getGender().toString());
    }

    private void loadProfilePicture(String uid) {
        userUtility.downloadProfilePic(uid, new UserUtility.OnProfilePicDownloadedListener() {
            @Override
            public void onSuccess(Bitmap imageBitmap) {
                avatarImageView.setImageBitmap(imageBitmap);
            }

            @Override
            public void onError(String message) {
                Toast.makeText(ProfileActivity.this, "Error loading avatar: " + message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}