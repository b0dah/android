package com.example.garbagecollector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {

    private final int SPLASH_SCREEN_TIME_OUT = 2000;
    //TextView appTitleTextView;
    ImageView logoTitleImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        logoTitleImageView = findViewById(R.id.logo_title);

        logoTitleImageView.setImageResource(R.drawable.logo_title);

        logoTitleImageView.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_transition));

        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intentToMainScreen = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intentToMainScreen);
                finish();
            }
        }, SPLASH_SCREEN_TIME_OUT);

    }
}
