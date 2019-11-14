package com.example.garbagecollector;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class OrderDetailsActivity extends AppCompatActivity {

    TextView nameTextView, originAddressTextView, destinationAddressTextView, deliveryTimeTextView,
    paymentTextView, numberOfMoversTextView, phoneNumberTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.order_details_activity);

        nameTextView = (TextView) findViewById(R.id.name);
        originAddressTextView = (TextView) findViewById(R.id.origin_address);
        destinationAddressTextView = (TextView) findViewById(R.id.destination_address);
        deliveryTimeTextView = (TextView) findViewById(R.id.delivery_time);
        paymentTextView = (TextView) findViewById(R.id.payment);
        numberOfMoversTextView = (TextView) findViewById(R.id.number_of_movers);
        phoneNumberTextView = (TextView) findViewById(R.id.phone_number);


        // Receiving Value into activity using intent
        //OrderDataModel TempHolder = (OrderDataModel) getIntent().getSerializableExtra("ClickedOrder");
        Intent intent = getIntent();
        OrderDataModel receivedOrder = (OrderDataModel) intent.getExtras().getSerializable("ClickedOrder");



        nameTextView.setText(receivedOrder.getCustomerName());
        originAddressTextView.setText(receivedOrder.getOriginAdress());
        destinationAddressTextView.setText(receivedOrder.getDestinantionAddress());
        deliveryTimeTextView.setText(receivedOrder.getDeliveryTime());
        paymentTextView.setText(String.valueOf(receivedOrder.getPayment()));
        numberOfMoversTextView.setText(String.valueOf(receivedOrder.getNumberOfMovers()));
        phoneNumberTextView.setText(receivedOrder.getPhoneNumber());

        // Return New Status To Previous Activity
    }
}
