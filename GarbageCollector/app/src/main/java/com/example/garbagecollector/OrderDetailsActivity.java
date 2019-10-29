package com.example.garbagecollector;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class OrderDetailsActivity extends AppCompatActivity {

    TextView nameTextView, addressTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        setContentView(R.layout.order_details_activity);

        nameTextView = findViewById(R.id.name);
        addressTextView = findViewById(R.id.address);

        // Receiving Value into activity using intent
        OrderDataModel TempHolder = getIntent().getParcelableExtra("ClickedOrder");

        nameTextView.setText(TempHolder.getCustomerName());
        addressTextView.setText(TempHolder.getAdress());

    }
}
