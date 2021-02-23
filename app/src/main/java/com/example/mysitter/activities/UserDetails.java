package com.example.mysitter.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mysitter.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserDetails extends AppCompatActivity implements View.OnClickListener {

    private EditText fullName, facebookLink, age, hourlyPrice;
    private String userID;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;

    private Button updateUserProfileBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        fullName = findViewById(R.id.fullNameText);
        facebookLink = findViewById(R.id.facebookLinkText);
        age = findViewById(R.id.ageText);
        hourlyPrice = findViewById(R.id.hourlyPriceText);
        updateUserProfileBtn = (Button)findViewById(R.id.updateUserProfileBtn);
        updateUserProfileBtn.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
    }



    @Override
    public void onClick(View view) {
        updateUserProfile();
    }

    private void updateUserProfile() {

        String fullNameStr = fullName.getText().toString().trim();
        String facebookLinkStr = facebookLink.getText().toString().trim();
        String ageStr = age.getText().toString().trim();
        String hourlyPriceStr = hourlyPrice.getText().toString().trim();

        userID = mAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("users").document(userID);
        Map<String,Object> user = new HashMap<>();
        user.put("fullName", fullNameStr);
        user.put("facebookLink", facebookLinkStr);
        user.put("age", ageStr);
        user.put("hourlyPrice", hourlyPriceStr);
        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(UserDetails.this, "Set Successfully" + userID, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(UserDetails.this,MainActivity.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UserDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}