package com.example.mysitter.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mysitter.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class UserDetails extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private EditText fullName, facebookLink, age, hourlyPrice;
    private String userID;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;
    private Spinner spinner;
    private Button updateUserProfileBtn, uploadImageBtn;
    private String type;
    private ImageView profileImage, doneIcon;
    private StorageReference storageReference;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        fullName = findViewById(R.id.fullNameText);
        facebookLink = findViewById(R.id.facebookLinkText);
        age = findViewById(R.id.ageText);
        hourlyPrice = findViewById(R.id.hourlyPriceText);

        uploadImageBtn = findViewById(R.id.uploadUserImageBtn);
        profileImage = findViewById(R.id.profileImageView);
        doneIcon = findViewById(R.id.doneIcon);

        spinner = findViewById(R.id.spinner_types);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        updateUserProfileBtn = (Button)findViewById(R.id.updateUserProfileBtn);
        updateUserProfileBtn.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        uploadImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent, 1000);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            if(resultCode == Activity.RESULT_OK){
                imageUri = data.getData();
                profileImage.setImageURI(imageUri);

                uploadImageToFireBase();
            }
        }
    }

    private void uploadImageToFireBase() {
        StorageReference fileRef = storageReference.child("users/"+mAuth.getCurrentUser().getUid() + "/profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                doneIcon.animate().alpha(1).setDuration(1000);
                Toast.makeText(UserDetails.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UserDetails.this, "Upload Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onClick(View view) {
        updateUserProfile();
    }

    private void updateUserProfile() {

        String fullNameStr = fullName.getText().toString().trim();
        String facebookLinkStr = facebookLink.getText().toString().trim();
        int ageStr = Integer.parseInt(age.getText().toString().trim());
        int hourlyPriceStr = Integer.parseInt(hourlyPrice.getText().toString().trim());

        userID = mAuth.getCurrentUser().getUid();

        StorageReference imageFileRef = storageReference.child("users/"+mAuth.getCurrentUser().getUid() + "/profile.jpg");
        imageFileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                DocumentReference documentReference = fStore.collection(type).document(userID);
                Map<String,Object> user = new HashMap<>();
                user.put("fullName", fullNameStr);
                user.put("facebookLink", facebookLinkStr);
                user.put("age", ageStr);
                user.put("hourlyPrice", hourlyPriceStr);
                user.put("profileImage", uri.toString());
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
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UserDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });





//        DocumentReference documentReference = fStore.collection(type).document(userID);
//        Map<String,Object> user = new HashMap<>();
//        user.put("fullName", fullNameStr);
//        user.put("facebookLink", facebookLinkStr);
//        user.put("age", ageStr);
//        user.put("hourlyPrice", hourlyPriceStr);
//        user.put("profileImage", imageUri);
//        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                Toast.makeText(UserDetails.this, "Set Successfully" + userID, Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(UserDetails.this,MainActivity.class));
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(UserDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String typeText = adapterView.getItemAtPosition(i).toString();
        this.type = typeText;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}