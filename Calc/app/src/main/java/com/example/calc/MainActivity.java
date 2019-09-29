package com.example.calc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onButtonClick(View view) {
        EditText firstNumberEdit = (EditText) findViewById(R.id.FirstNumber);
        EditText secondNumberEdit = (EditText) findViewById(R.id.SecondNumber);

        TextView resultLabel = (TextView) findViewById(R.id.Result);

        int number1 = Integer.parseInt(firstNumberEdit.getText().toString());
        int number2 = Integer.parseInt(secondNumberEdit.getText().toString());

        int result = number1 + number2;

        resultLabel.setText(Integer.toString(result));
    }
}
