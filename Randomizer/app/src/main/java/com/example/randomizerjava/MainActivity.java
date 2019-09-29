package com.example.randomizerjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void generateNumber(View view) {
        Random random = new Random();
        int number = random.nextInt(100);
        TextView randomTextView = (TextView)findViewById(R.id.randomTextView);

        String randomNumberString = String.valueOf(number);
        randomTextView.setText(randomNumberString);
    }
}
