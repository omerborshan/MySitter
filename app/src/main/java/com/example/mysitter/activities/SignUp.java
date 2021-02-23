package com.example.mysitter.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mysitter.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;

    private Button moveToLoginFromRegisterBtn;
    private Button signUpBtn;

    private EditText emailEditText, passwordEditText, rePasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        moveToLoginFromRegisterBtn = (Button) findViewById(R.id.moveToLoginFromRegisterBtn);
        signUpBtn = (Button) findViewById(R.id.signUpBtn);

        emailEditText = (EditText) findViewById(R.id.emailSignUp);
        passwordEditText = (EditText) findViewById(R.id.passwordSignUp);
        rePasswordEditText = (EditText) findViewById(R.id.rePasswordSignUp);

        moveToLoginFromRegisterBtn.setOnClickListener(this);
        signUpBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.moveToLoginFromRegisterBtn:
                startActivity(new Intent(this, Login.class));
                break;
            case R.id.signUpBtn:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        final String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String rePassword = rePasswordEditText.getText().toString().trim();

        if(email.isEmpty()){
            emailEditText.setError("Email is required");
            emailEditText.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Email must be valid");
            emailEditText.requestFocus();
            return;
        }
        if(password.isEmpty()){
            passwordEditText.setError("Password is required");
            passwordEditText.requestFocus();
            return;
        }

        if(password.length()<6){
            passwordEditText.setError("Password must be at least 6 characters");
            passwordEditText.requestFocus();
            return;
        }
        if(rePassword.isEmpty()){
            rePasswordEditText.setError("Re-Password is required");
            rePasswordEditText.requestFocus();
            return;
        }

        if(!password.equals(rePassword)){
            rePasswordEditText.setError("Passwords do not match");
            rePasswordEditText.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignUp.this, "User created successfuly", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), UserDetails.class));
                } else {
                    Toast.makeText(SignUp.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
