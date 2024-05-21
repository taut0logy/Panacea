package com.project.panacea;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_records);

        recyclerView = findViewById(R.id.recordsRecyclerView);
        recordList = new ArrayList<>();
        adapter = new RecordsAdapter(recordList);
        recyclerView.setAdapter(adapter);
        loadRecords();
    }

    private void loadRecords() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("records");
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
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("Database error: " + databaseError.getMessage());
            }
        });
    }
}
