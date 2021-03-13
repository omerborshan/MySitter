package com.example.mysitter.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mysitter.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

public class EmployeeCardAdapter extends FirestoreRecyclerAdapter<EmployeeModel,EmployeeCardAdapter.EmployeeHolder> {

    public EmployeeCardAdapter(@NonNull FirestoreRecyclerOptions<EmployeeModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull EmployeeHolder holder, int position, @NonNull EmployeeModel model) {
        holder.textViewName.setText( model.getFullName());
        holder.textViewAge.setText("Age: " + String.valueOf(model.getAge()));
        holder.textViewPrice.setText("Price: " + String.valueOf(model.getHourlyPrice()));
        holder.textViewPhone.setText(String.valueOf(model.getPhoneNumber()));
        Picasso.get().load(model.getProfileImage()).into(holder.profileImage);
    }

    @NonNull
    @Override
    public EmployeeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_card,parent,false);
        return new EmployeeHolder(v);
    }

    class EmployeeHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewAge, textViewPrice, textViewPhone;
        ImageView profileImage;

        public EmployeeHolder(@NonNull View itemView) {
            super(itemView);
            textViewPhone = itemView.findViewById(R.id.phone_text_card);
            textViewName = itemView.findViewById(R.id.name_text_card);
            textViewAge = itemView.findViewById(R.id.age_text_card);
            textViewPrice = itemView.findViewById(R.id.price_text_card);
            profileImage = itemView.findViewById(R.id.image_card);
        }
    }
}
