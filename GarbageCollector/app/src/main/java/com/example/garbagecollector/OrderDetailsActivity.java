package com.example.garbagecollector;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class OrderDetailsActivity extends AppCompatActivity {

    TextView nameTextView, addressTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.order_details_activity);

        nameTextView = (TextView) findViewById(R.id.name);
        addressTextView = (TextView) findViewById(R.id.address);

        // Receiving Value into activity using intent
        //OrderDataModel TempHolder = (OrderDataModel) getIntent().getSerializableExtra("ClickedOrder");
        Intent intent = getIntent();
        OrderDataModel receivedOrder = (OrderDataModel) intent.getExtras().getSerializable("ClickedOrder");

        nameTextView.setText(receivedOrder.getCustomerName());
        addressTextView.setText(receivedOrder.getOriginAdress());

    }
}
