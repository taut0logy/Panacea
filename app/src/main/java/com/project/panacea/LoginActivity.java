package com.project.panacea;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView dont;
    private final AuthUtility authUtility=AuthUtility.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etEmail=findViewById(R.id.logEtEmail);
        etPassword=findViewById(R.id.logEtPassword);
        btnLogin=findViewById(R.id.logBtnLogin);
        dont=findViewById(R.id.logTvReg);

        btnLogin.setOnClickListener(v->login());
        dont.setOnClickListener(v->startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));
    }

    private void login() {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        authUtility.signIn(email, password, new AuthUtility.OnUserAuthenticatedListener() {
            @Override
            public void onSuccess(String uid) {
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();
            }

            @Override
            public void onError(String message) {
                // Show error message
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                Log.e(TAG, message);
            }
        });
    }
}