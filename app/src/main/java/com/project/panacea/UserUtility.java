package com.project.panacea;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Objects;

public class UserUtility {

    private static final String TAG = "UserUtility";

    private static UserUtility instance;
    private AuthUtility authUtility;

    private FirebaseDatabase database;

    private FirebaseStorage storage;

    private UserUtility() {
        authUtility = AuthUtility.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
    }

    protected UserUtility(AuthUtility authUtility, FirebaseDatabase database, FirebaseStorage storage) {
        this.authUtility = authUtility;
        this.database = database;
        this.storage = storage;
    }

    public static synchronized UserUtility getInstance() {
        if (instance == null) {
            instance = new UserUtility();
        }
        return instance;
    }

    public void createUser(User user, OnUserCreatedListener listener) {
        String uid = authUtility.getAuth().getCurrentUser().getUid();
        //JSONObject userJson = user.toJSON();
        //Log.e(TAG, "createUser: " + userJson.toString());
        database.getReference().child("users").child(uid).setValue(user).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                listener.onSuccess(uid);
            } else {
                listener.onError(Objects.requireNonNull(task.getException()).getMessage());
            }
        });
    }

    public interface OnUserCreatedListener {
        void onSuccess(String uid);
        void onError(String message);
    }

    public void getUser(String uid, OnUserRetrievedListener listener) {
        database.getReference().child("users").child(uid).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                //JSONObject userJson = task.getResult().getValue(JSONObject.class);
                User user = task.getResult().getValue(User.class);
                listener.onSuccess(user);
            } else {
                listener.onError(Objects.requireNonNull(task.getException()).getMessage());
            }
        });
    }

    public interface OnUserRetrievedListener {
        void onSuccess(User user);
        void onError(String message);
    }

    public void updateUser(String uid, User user, OnUserUpdatedListener listener) {
        database.getReference("users").child(uid).setValue(user).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                listener.onSuccess();
            } else {
                listener.onError(task.getException().getMessage());
            }
        });
    }

    public interface OnUserUpdatedListener {
        void onSuccess();
        void onError(String message);
    }

    public void deleteUser(String uid, OnUserDeletedListener listener) {
        database.getReference("users").child(uid).removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                listener.onSuccess();
            } else {
                listener.onError(task.getException().getMessage());
            }
        });
    }

    public interface OnUserDeletedListener {
        void onSuccess();
        void onError(String message);
    }

    public void uploadProfilePic(String uid, Bitmap imageBitmap, OnProfilePicUploadedListener listener) {
        StorageReference profilePicRef = storage.getReference().child("profilepics").child(uid + ".jpg");
        //compress image
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        //upload image
        profilePicRef.putBytes(data).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                listener.onSuccess();
            } else {
                listener.onError(Objects.requireNonNull(task.getException()).getMessage());
            }
        });
    }

    public interface OnProfilePicUploadedListener {
        void onSuccess();
        void onError(String message);
    }

    public void downloadProfilePic(String uid, OnProfilePicDownloadedListener listener) {
        StorageReference profilePicRef = storage.getReference().child("profilepics").child(uid + ".jpg");
        profilePicRef.getBytes(Long.MAX_VALUE).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                byte[] bytes = task.getResult();
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                listener.onSuccess(bitmap);
            } else {
                listener.onError(Objects.requireNonNull(task.getException()).getMessage());
            }
        });
    }

    public interface OnProfilePicDownloadedListener {
        void onSuccess(Bitmap imageBitmap);
        void onError(String message);
    }
}
