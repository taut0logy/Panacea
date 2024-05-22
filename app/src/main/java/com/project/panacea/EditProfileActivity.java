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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditProfileActivity extends AppCompatActivity {

    private static final String TAG = "EditProfileActivity";
    EditText etName;
    EditText etEmail;
    EditText etPhoneNumber;
    EditText etDateOfBirth;
    RadioGroup rgGender;
    ImageView avatarImageView;
    private Button btnSave;
    private String currentPhotoPath;

    private int avatarUploaded = 0;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;

    AuthUtility authUtility = AuthUtility.getInstance();
    UserUtility userUtility = UserUtility.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        etName = findViewById(R.id.editEtName);
        etEmail = findViewById(R.id.editEtEmail);
        etPhoneNumber = findViewById(R.id.editEtPhone);
        etDateOfBirth = findViewById(R.id.editEtDob);
        rgGender = findViewById(R.id.editRgGender);
        avatarImageView = findViewById(R.id.editAvatar);
        btnSave = findViewById(R.id.editBtnSave);

        // Load existing user data
        loadUserData();

        etDateOfBirth.setOnClickListener(this::showDatePickerDialog);
        avatarImageView.setOnClickListener(this::showPopup);
        btnSave.setOnClickListener(this::onSaveClick);
    }

    public void setUtilities(AuthUtility authUtility, UserUtility userUtility) {
        this.authUtility = authUtility;
        this.userUtility = userUtility;
    }

    public void loadUserData() {
        authUtility.getUserUid(new AuthUtility.OnUserUidRetrievedListener() {
            @Override
            public void onSuccess(String uid) {
                userUtility.getUser(uid, new UserUtility.OnUserRetrievedListener() {
                    @Override
                    public void onSuccess(User user) {
                        Date dateOfBirth = user.getDateOfBirth();
                        int month1 = dateOfBirth.getMonth();
                        int dayOfMonth = dateOfBirth.getDate();
                        int year1 = dateOfBirth.getYear();
                        String dateOfBirthText = (dayOfMonth<10?("0"+dayOfMonth):dayOfMonth) + "/" + ((month1 + 1<10)?"0"+(month1 + 1):(month1 + 1)) + "/" + year1;
                        etName.setText(user.getName());
                        etEmail.setText(user.getEmail());
                        etPhoneNumber.setText(user.getPhoneNumber());
                        etDateOfBirth.setText(dateOfBirthText);
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(EditProfileActivity.this, "Error getting user: " + message, Toast.LENGTH_SHORT).show();
                        Log.e(TAG, message);
                        EditProfileActivity.this.finish();
                    }
                });
            }

            @Override
            public void onError(String error) {
                Toast.makeText(EditProfileActivity.this, "Error getting current User id: " + error, Toast.LENGTH_SHORT).show();
                Log.e(TAG, error);
                EditProfileActivity.this.finish();
            }
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

    void showDatePickerDialog(View v) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
            etDateOfBirth.setText((dayOfMonth < 10 ? ("0" + dayOfMonth) : dayOfMonth) + "/" + ((month1 + 1 < 10) ? "0" + (month1 + 1) : (month1 + 1)) + "/" + year1);
        }, year, month, day);
        datePickerDialog.show();
    }

    public void onSaveClick(View view) {
        if (!validateInput()) {
            return;
        }
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
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

        Gender gender = null;
        if (genderId == R.id.editRbMale) {
            gender = Gender.MALE;
        }
        if (genderId == R.id.editRbFemale) {
            gender = Gender.FEMALE;
        }
        if (genderId == R.id.editRbOther) {
            gender = Gender.OTHER;
        }
        Gender finalGender = gender;
        Date finalDateOfBirth = dateOfBirth;

        // Update user data
        User user = new User(name, finalDateOfBirth, email, phoneNumber, finalGender);
        authUtility.getUserUid(new AuthUtility.OnUserUidRetrievedListener() {
            @Override
            public void onSuccess(String uid) {
                userUtility.updateUser(uid, user, new UserUtility.OnUserUpdatedListener() {
                    @Override
                    public void onSuccess() {
                        if (avatarUploaded == 1) {
                            Bitmap avatar = ((BitmapDrawable) avatarImageView.getDrawable()).getBitmap();
                            userUtility.uploadProfilePic(uid, avatar, new UserUtility.OnProfilePicUploadedListener() {
                                @Override
                                public void onSuccess() {
                                    Toast.makeText(EditProfileActivity.this, "Profile updated successfully.", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onError(String message) {
                                    Toast.makeText(EditProfileActivity.this, "Error uploading avatar: " + message, Toast.LENGTH_SHORT).show();
                                    Log.e(TAG, message);
                                }
                            });
                        }
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(EditProfileActivity.this, "Error updating profile: " + message, Toast.LENGTH_SHORT).show();
                        Log.e(TAG, message);
                    }
                });
            }
            @Override
            public void onError(String message) {
                Toast.makeText(EditProfileActivity.this, "Error getting current User id: " + message, Toast.LENGTH_SHORT).show();
                Log.e(TAG, message);
            }
        });
    }

    public boolean validateInput() {
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
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

    boolean validateEmail(String email) {
        return email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
    }

    boolean validatePhoneNumber(String phoneNumber) {
        return phoneNumber.matches("^\\d{11}$");
    }

    boolean validateDateOfBirth(String dateOfBirth) {
        return dateOfBirth.matches("^\\d{2}/\\d{2}/\\d{4}$");
    }

}
