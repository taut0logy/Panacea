package com.project.panacea;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowRecords extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecordsAdapter adapter;
    private List<Record> recordList;
    private String currentUserUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_records);
        recyclerView = findViewById(R.id.recordsRecyclerView);
        recordList = new ArrayList<>();
        adapter = new RecordsAdapter(recordList);
        recyclerView.setAdapter(adapter);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            currentUserUid = currentUser.getUid();
            loadRecords();
        } else {
            // Handle the case when no user is logged in
            System.out.println("No user is logged in");
        }
    }

    private void loadRecords() {
        if (currentUserUid == null) {
            return;
        }

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("records").child(currentUserUid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                recordList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Record record = snapshot.getValue(Record.class);
                    if (record != null) {
                        recordList.add(record);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("Database error: " + databaseError.getMessage());
            }
        });
    }
}
