package com.project.panacea;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.ViewHolder> {
    private Context context;

    private List<Doctor> doctorList;

    public DoctorAdapter(Context context, List<Doctor> doctorList) {
        this.context = context;
        this.doctorList = doctorList;
    }

    @NonNull
    @Override
    public DoctorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorAdapter.ViewHolder holder, int position) {
        Doctor doctor = doctorList.get(position);
        holder.tvDoctorName.setText(doctor.getName());
        holder.tvDoctorDepartment.setText(doctor.getDepartment());
        holder.tvDoctorAvailable.setText(doctor.getIsAvailable()? "Available" : "Not Available");
        Gender gender = doctor.getGender();
        if (gender ==Gender.FEMALE) {
            holder.ivDoctorImage.setImageResource(R.drawable.doctor_female);
        } else {
            holder.ivDoctorImage.setImageResource(R.drawable.doctor_male);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DoctorInfoActivity.class);
            intent.putExtra("doctor", doctor);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvDoctorName;
        public TextView tvDoctorDepartment;
        public TextView tvDoctorAvailable;
        public ImageView ivDoctorImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivDoctorImage = itemView.findViewById(R.id.doctorImage);
            tvDoctorName = itemView.findViewById(R.id.doctorName);
            tvDoctorDepartment = itemView.findViewById(R.id.doctorDepartment);
            tvDoctorAvailable = itemView.findViewById(R.id.doctorAvailability);
        }
    }
}
