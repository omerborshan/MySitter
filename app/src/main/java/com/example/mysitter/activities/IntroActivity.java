package com.example.mysitter.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.mysitter.R;

public class IntroActivity extends AppCompatActivity {
    ImageView logo;
    LottieAnimationView dog_animation, baby_animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i=new Intent(IntroActivity.this,MainActivity.class);
                startActivity(i);
            }
        }, 7600);

        logo = findViewById(R.id.splash_logo);
        dog_animation = findViewById(R.id.splash_animation_dog);
        baby_animation = findViewById(R.id.splash_animation_baby);

        dog_animation.setRepeatCount(Animation.INFINITE);
        baby_animation.setRepeatCount(Animation.INFINITE);

        //Animations set Logo
        ObjectAnimator logo_in = ObjectAnimator.ofFloat(logo,"translationY",0);
        logo_in.setDuration(1000);
        logo_in.setStartDelay(600);

        ObjectAnimator logo_out = ObjectAnimator.ofFloat(logo,"alpha",0);
        logo_out.setDuration(1000);
        logo_out.setStartDelay(6600);

        AnimatorSet logo_set = new AnimatorSet();
        logo_set.playTogether(logo_in,logo_out);
        logo_set.start();

        //Animations set dog
        ObjectAnimator dog_in = ObjectAnimator.ofFloat(dog_animation,"alpha",1);
        dog_in.setDuration(1000);
        dog_in.setStartDelay(600);

        ObjectAnimator dog_out = ObjectAnimator.ofFloat(dog_animation,"translationX",800);
        dog_out.setDuration(1000);
        dog_out.setStartDelay(3000);

        AnimatorSet dog_set = new AnimatorSet();
        dog_set.playTogether(dog_in,dog_out);
        dog_set.start();

        //Animations set baby
        ObjectAnimator baby_in = ObjectAnimator.ofFloat(baby_animation,"translationX",0);
        baby_in.setDuration(1000);
        baby_in.setStartDelay(3000);

        ObjectAnimator baby_out = ObjectAnimator.ofFloat(baby_animation,"alpha",0);
        baby_out.setDuration(1000);
        baby_out.setStartDelay(6600);

        AnimatorSet baby_set = new AnimatorSet();
        baby_set.playTogether(baby_in,baby_out);
        baby_set.start();
    }
}