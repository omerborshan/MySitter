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

public class Login extends AppCompatActivity implements View.OnClickListener {

    private Button moveToSignUpFromLoginBtn;
    private Button loginBtn;

    private EditText emailEditText;
    private EditText passwordEditText;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = (Button) findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(this);

        moveToSignUpFromLoginBtn = (Button) findViewById(R.id.signUpBtn);
        moveToSignUpFromLoginBtn.setOnClickListener(this);

        emailEditText = (EditText) findViewById(R.id.emailLogin);
        passwordEditText = (EditText) findViewById(R.id.passwordLogin);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.signUpBtn:
                startActivity(new Intent(this,SignUp.class));
                break;
            case R.id.loginBtn:
                userLogin();
                break;
        }
    }

    private void userLogin() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

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

        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this, "Login successfully", Toast.LENGTH_SHORT).show();
//                            SharedPreferences.Editor editor = sharedPreferences.edit();
//                            editor.putString("keyUser",emailEditText.getText().toString());
//                            editor.putString("keyPass",passwordEditText.getText().toString());
//                            editor.apply();
                            startActivity(new Intent(Login.this,MainActivity.class).putExtra("uid", mAuth.getCurrentUser().getUid()));

                        }else {
                            Toast.makeText(Login.this, "Failed to login", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}