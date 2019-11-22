package com.example.garbagecollector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {

    private final int SPLASH_SCREEN_TIME_OUT = 2000;
    TextView appTitleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        appTitleTextView = (TextView) findViewById(R.id.app_title);
        appTitleTextView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_scale));

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
