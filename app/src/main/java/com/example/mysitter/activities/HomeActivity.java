package com.example.mysitter.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.mysitter.R;
import com.example.mysitter.fragments.AboutFragment;
import com.example.mysitter.fragments.EmployeeFragment;
import com.example.mysitter.fragments.LoggedInUserFragment;
import com.example.mysitter.fragments.RegisterFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private FirebaseAuth mAuth;
    public String type;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent i = getIntent();
        type = i.getExtras().getString("type","");

        bundle = new Bundle();
        bundle.putString("type", type);

        Fragment startFragment = new EmployeeFragment();
        startFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                startFragment).commit();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        mAuth = FirebaseAuth.getInstance();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()){
                        case R.id.nav_list:
                            selectedFragment = new EmployeeFragment();
                            selectedFragment.setArguments(bundle);
                            break;
                        case R.id.nav_register:
                            if(mAuth.getCurrentUser() != null){
                                selectedFragment = new LoggedInUserFragment();
                            } else {
                                selectedFragment = new RegisterFragment();
                            }
                            break;

                        case R.id.nav_about:
                            selectedFragment = new AboutFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };

    public void loadRegisterFragment() {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new RegisterFragment()).addToBackStack(null).commit();
    }
}