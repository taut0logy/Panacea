package com.project.panacea;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RecordsAdapter extends RecyclerView.Adapter<RecordsAdapter.ViewHolder> {

    private List<Record> recordList;

    public RecordsAdapter(List<Record> recordList) {
        this.recordList = recordList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Record record = recordList.get(position);
        holder.tvHeartRate.setText("Heart Rate: " + record.heartRate);
        holder.tvSystolicPressure.setText("Systolic Pressure: " + record.systolicPressure);
        holder.tvDiastolicPressure.setText("Diastolic Pressure: " + record.diastolicPressure);
        holder.tvComment.setText("Comment: " + record.comment);
    }

    @Override
    public int getItemCount() {
        return recordList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvHeartRate, tvSystolicPressure, tvDiastolicPressure, tvComment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHeartRate = itemView.findViewById(R.id.tvHeartRate);
            tvSystolicPressure = itemView.findViewById(R.id.tvSystolicPressure);
            tvDiastolicPressure = itemView.findViewById(R.id.tvDiastolicPressure);
            tvComment = itemView.findViewById(R.id.tvComment);
        }
    }
}
