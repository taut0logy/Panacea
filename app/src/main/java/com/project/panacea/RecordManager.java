package com.project.panacea;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RecordManager {
    private static final String TAG = "RecordManager";
    private static RecordManager instance;
    private DatabaseReference databaseReference;

    private RecordManager() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public RecordManager(DatabaseReference databaseReference) {
        this.databaseReference = databaseReference;
    }

    //Public method to extract the value of the single instance of the singleton class
    public static synchronized RecordManager getInstance() {
        if (instance == null) {
            instance = new RecordManager();
        }
        return instance;
    }

    public void addRecord(int heartRate, int systolicPressure, int diastolicPressure, String comment) {
        AuthUtility.getInstance().getUserUid(new AuthUtility.OnUserUidRetrievedListener() {
            @Override
            public void onSuccess(String uid) {
                DatabaseReference recordRef = databaseReference.child("records").child(uid);
                String key = recordRef.push().getKey();
                Record record = new Record(heartRate, systolicPressure, diastolicPressure, comment);
                recordRef.child(key).setValue(record);
            }

            @Override
            public void onError(String error) {
                Log.e(TAG, "onError: " + error);
            }
        });

    }
}
