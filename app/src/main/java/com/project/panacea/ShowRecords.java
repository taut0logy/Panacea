package com.project.panacea;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShowRecords extends AppCompatActivity {
    private static final String TAG = "ShowRecords";

    private RecyclerView recyclerView;
    private RecordsAdapter adapter;
    private ArrayList<Record> recordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_records);

        recyclerView = findViewById(R.id.recordsRecyclerView);
        recordList = new ArrayList<>();
        adapter = new RecordsAdapter(recordList);
        loadRecords();
        recyclerView.setAdapter(adapter);
    }

    private void loadRecords() {
        AuthUtility.getInstance().getUserUid(new AuthUtility.OnUserUidRetrievedListener() {
            @Override
            public void onSuccess(String uid) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("records").child(uid);
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        recordList.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                Record record = userSnapshot.getValue(Record.class);
                                recordList.add(record);
                            }
                        }
                        Collections.reverse(recordList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        System.out.println("Database error: " + databaseError.getMessage());
                    }
                });
            }

            @Override
            public void onError(String error) {
                Log.e(TAG, "onError: " + error);
                Toast.makeText(ShowRecords.this, "Error redtieving current user", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
