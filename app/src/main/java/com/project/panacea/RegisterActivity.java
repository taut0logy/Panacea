package com.project.panacea;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    private EditText etName, etEmail, etPassword, etConfirmPassword, etPhoneNumber, etDateOfBirth;
    private RadioGroup rgGender;
    private ImageView avatarImageView;
    private Button btnRegister;
    private TextView tvLogin;
    private String currentPhotoPath;

    private int avatarUploaded = 0;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;

    AuthUtility authUtility = AuthUtility.getInstance();
    UserUtility userUtility = UserUtility.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        etName = findViewById(R.id.regEtName);
        etEmail = findViewById(R.id.regEtEmail);
        etPassword = findViewById(R.id.regEtPassword);
        etConfirmPassword = findViewById(R.id.regEtPasswordConf);
        etPhoneNumber = findViewById(R.id.regEtPhone);
        etDateOfBirth = findViewById(R.id.regEtDob);
        rgGender = findViewById(R.id.regRgGender);
        avatarImageView = findViewById(R.id.regAvatar);
        btnRegister = findViewById(R.id.regBtnRegister);
        tvLogin = findViewById(R.id.regTvLogin);

        etDateOfBirth.setOnClickListener(this::showDatePickerDialog);

        avatarImageView.setOnClickListener(this::showPopup);

        btnRegister.setOnClickListener(this::onRegisterClick);

        tvLogin.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });

    }

    private void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.camera) {
                dispatchTakePictureIntent();
                return true;
            }
            if (item.getItemId() == R.id.gallery) {
                dispatchPickPictureIntent();
                return true;
            }
            return false;
        });
        popup.inflate(R.menu.menu_avatar);
        popup.show();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.e(TAG, "Error creating image file", ex);
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "com.project.panacea.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        } else {
            Toast.makeText(this, "No camera app found on your device.", Toast.LENGTH_SHORT).show();
        }
    }

    private void dispatchPickPictureIntent() {
        Intent pickPictureIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPictureIntent, REQUEST_IMAGE_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            setPic();
        } else if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                avatarImageView.setImageBitmap(bitmap);
                avatarUploaded = 1;
            } catch (IOException e) {
                Log.e(TAG, "Error loading image", e);
            }
        }
    }

    private void setPic() {
        File imgFile = new File(currentPhotoPath);
        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            Matrix matrix = new Matrix();
            matrix.postRotate(270);
            Bitmap rotatedBitmap = Bitmap.createBitmap(myBitmap, 0, 0, myBitmap.getWidth(), myBitmap.getHeight(), matrix, true);
            avatarImageView.setImageBitmap(rotatedBitmap);
            avatarUploaded = 1;
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES + "/Panacea/avatars");
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public void onAvatarClick(View view) {
        showPopup(view);
    }

    void showDatePickerDialog(View v) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
            etDateOfBirth.setText((dayOfMonth<10?("0"+dayOfMonth):dayOfMonth) + "/" + ((month1 + 1<10)?"0"+(month1 + 1):(month1 + 1)) + "/" + year1);
        }, year, month, day);
        datePickerDialog.show();
    }

    public void onRegisterClick(View view) {
        if (!validateInput()) {
            return;
        }
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String phoneNumber = etPhoneNumber.getText().toString();
        Date dateOfBirth = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            dateFormat.setLenient(true);
            dateOfBirth = dateFormat.parse(etDateOfBirth.getText().toString());
        } catch (ParseException e) {
            Log.e(TAG, "Error parsing date", e);
        }
        Log.d(TAG, "Date of birth: " + dateOfBirth);
        int genderId = rgGender.getCheckedRadioButtonId();
        if (avatarUploaded == 0) {
            if (genderId == R.id.regRbMale) {
                avatarImageView.setImageResource(R.drawable.avatar_male);
            }
            if (genderId == R.id.regRbFemale) {
                avatarImageView.setImageResource(R.drawable.avatar_female);
            }
            if (genderId == R.id.regRbOther) {
                avatarImageView.setImageResource(R.drawable.avatar_neutral);
            }
        }
        Bitmap avatar = ((BitmapDrawable) avatarImageView.getDrawable()).getBitmap();
        Gender gender = null;
        if (genderId == R.id.regRbMale) {
            gender = Gender.MALE;
        }
        if (genderId == R.id.regRbFemale) {
            gender = Gender.FEMALE;
        }
        if (genderId == R.id.regRbOther) {
            gender = Gender.OTHER;
        }
        Gender finalGender = gender;
        Date finalDateOfBirth = dateOfBirth;
        authUtility.signUp(email, password, new AuthUtility.OnUserCreatedListener() {
            @Override
            public void onSuccess(String uid) {
                User user = new User(name, finalDateOfBirth, email, phoneNumber, finalGender);
                Log.e(TAG + " User", user.toJSON().toString());
                userUtility.createUser(user, new UserUtility.OnUserCreatedListener() {
                    @Override
                    public void onSuccess(String uid) {
                        userUtility.uploadProfilePic(uid, avatar, new UserUtility.OnProfilePicUploadedListener() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(RegisterActivity.this, "Avatar uploaded successfully.", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(String message) {
                                Toast.makeText(RegisterActivity.this, "Error uploading avatar: " + message, Toast.LENGTH_SHORT).show();
                                Log.e(TAG, message);
                            }
                        });
                        Toast.makeText(RegisterActivity.this, "User created successfully.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(RegisterActivity.this, "Error creating user: " + message, Toast.LENGTH_SHORT).show();
                        Log.e(TAG, message);
                    }
                });
            }

            @Override
            public void onError(String error) {
                Toast.makeText(RegisterActivity.this, "Error creating user: " + error, Toast.LENGTH_SHORT).show();
                Log.e(TAG, error);
            }
        });
    }


    boolean validateEmail(String email) {
        return email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
    }

    boolean validatePassword(String password) {
        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
    }

    boolean validatePhoneNumber(String phoneNumber) {
        return phoneNumber.matches("^\\d{11}$");
    }

    boolean validateDateOfBirth(String dateOfBirth) {
        return dateOfBirth.matches("^\\d{2}/\\d{2}/\\d{4}$");
    }

    boolean validateGender(int genderId) {
        return genderId != -1;
    }

    boolean validateAvatar() {
        return avatarImageView.getDrawable() != null;
    }

    private boolean validateInput() {
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();
        String phoneNumber = etPhoneNumber.getText().toString();
        String dateOfBirth = etDateOfBirth.getText().toString();
        int genderId = rgGender.getCheckedRadioButtonId();
        if (name.isEmpty()) {
            etName.setError("Name is required.");
            return false;
        }
        if (email.isEmpty()) {
            etEmail.setError("Email is required.");
            return false;
        }
        if (!validateEmail(email)) {
            etEmail.setError("Invalid email.");
            return false;
        }
        if (password.isEmpty()) {
            etPassword.setError("Password is required.");
            return false;
        }
        if (!validatePassword(password)) {
            etPassword.setError("Password must contain at least 8 characters, one uppercase, one lowercase, one number and one special character.");
            return false;
        }
        if (confirmPassword.isEmpty()) {
            etConfirmPassword.setError("Confirm Password is required.");
            return false;
        }
        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Password and Confirm Password must be same.");
            return false;
        }
        if (phoneNumber.isEmpty()) {
            etPhoneNumber.setError("Phone number is required.");
            return false;
        }
        if (!validatePhoneNumber(phoneNumber)) {
            etPhoneNumber.setError("Invalid phone number.");
            return false;
        }
        if (dateOfBirth.isEmpty()) {
            etDateOfBirth.setError("Date of birth is required.");
            return false;
        }
//        if (!validateDateOfBirth(dateOfBirth)) {
//            etDateOfBirth.setError("Invalid date of birth.");
//            return false;
//        }
        return true;
    }


}