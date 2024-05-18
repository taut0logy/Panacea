package com.project.panacea;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class AuthUtility {
    private static final String TAG = "AuthUtility";
    private static AuthUtility instance;
    private final FirebaseAuth mAuth;
    private final FirebaseDatabase database;

    private AuthUtility() {
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
    }

    protected AuthUtility(FirebaseAuth auth, FirebaseDatabase database) {
        this.mAuth = auth;
        this.database = database;
    }

    public static synchronized AuthUtility getInstance() {
        if (instance == null) {
            instance = new AuthUtility();
        }
        return instance;
    }

    public FirebaseAuth getAuth() {
        return mAuth;
    }

    public FirebaseDatabase getDatabase() {
        return database;
    }

    public void signUp(String email, String password, OnUserCreatedListener listener) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (mAuth.getCurrentUser() == null) {
                            listener.onError("User not found");
                            return;
                        }
                        String uid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                        listener.onSuccess(uid);
                    } else {
                        listener.onError(Objects.requireNonNull(task.getException()).getMessage());
                    }
                });
    }

    public void signIn(String email, String password, OnUserAuthenticatedListener listener) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String uid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                        listener.onSuccess(uid);
                    } else {
                        listener.onError(Objects.requireNonNull(task.getException()).getMessage());
                    }
                });
    }

    public void getUserUid(OnUserUidRetrievedListener listener) {
        String uid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        listener.onSuccess(uid);
    }

    public void signOut(OnUserSignedOutListener listener) {
        mAuth.signOut();
        listener.onSuccess();
    }

    public void deleteAccount(OnUserDeletedListener listener) {
        Objects.requireNonNull(mAuth.getCurrentUser()).delete()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        listener.onSuccess();
                    } else {
                        listener.onError(Objects.requireNonNull(task.getException()).getMessage());
                    }
                });
    }

    public interface OnUserCreatedListener {
        void onSuccess(String uid);
        void onError(String error);
    }

    public interface OnUserAuthenticatedListener {
        void onSuccess(String uid);
        void onError(String error);
    }

    public interface OnUserDeletedListener {
        void onSuccess();
        void onError(String error);
    }

    public interface OnUserSignedOutListener {
        void onSuccess();
        void onError(String error);
    }

    public interface OnUserUidRetrievedListener {
        void onSuccess(String uid);
        void onError(String error);
    }

}
