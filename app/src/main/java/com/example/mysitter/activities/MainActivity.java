package com.example.mysitter.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mysitter.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onBabySitterClick(View view) {
        Intent intent = new Intent(this,HomeActivity.class);
        intent.putExtra("type", "baby");
        startActivity(intent);

    }

    public void onDogSitterClick(View view) {
        Intent intent = new Intent(this,HomeActivity.class);
        intent.putExtra("type", "dog");
        startActivity(intent);
    }
}