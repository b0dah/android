package com.example.garbagecollector;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import javax.xml.datatype.Duration;

public class OrderDetailsActivity extends AppCompatActivity {

    TextView nameTextView, originAddressTextView, destinationAddressTextView, deliveryTimeTextView,
    paymentTextView, numberOfMoversTextView, phoneNumberTextView;
    Button changeStatusButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.order_details_activity);

        // Assigning TextViews with IDs
        nameTextView = (TextView) findViewById(R.id.name);
        originAddressTextView = (TextView) findViewById(R.id.origin_address);
        destinationAddressTextView = (TextView) findViewById(R.id.destination_address);
        deliveryTimeTextView = (TextView) findViewById(R.id.delivery_time);
        paymentTextView = (TextView) findViewById(R.id.payment);
        numberOfMoversTextView = (TextView) findViewById(R.id.number_of_movers);
        phoneNumberTextView = (TextView) findViewById(R.id.phone_number);

        changeStatusButton = (Button) findViewById(R.id.change_status_button);

        // Receiving Value into activity using intent
        //OrderDataModel TempHolder = (OrderDataModel) getIntent().getSerializableExtra("ClickedOrder");
        Intent intent = getIntent();
        OrderDataModel receivedOrder = (OrderDataModel) intent.getExtras().getSerializable("ClickedOrder");

        // Dislaying Info in TextViews
        nameTextView.setText(receivedOrder.getCustomerName());
        originAddressTextView.setText(receivedOrder.getOriginAdress());
        destinationAddressTextView.setText(receivedOrder.getDestinantionAddress());
        deliveryTimeTextView.setText(receivedOrder.getDeliveryTime());
        paymentTextView.setText(String.valueOf(receivedOrder.getPayment()));
        numberOfMoversTextView.setText(String.valueOf(receivedOrder.getNumberOfMovers()));
        phoneNumberTextView.setText(receivedOrder.getPhoneNumber());

        // Return New Status To Previous Activity
        switch (receivedOrder.getStatus()) {
            case DEFAULT:
                // Style for button
                changeStatusButton.setText("Начать исполнение");
                changeStatusButton.setBackgroundColor(Color.rgb(245, 194, 66));

                // Changing status local instance status



                // Changing status on the server



                break;

            case SEEN:
                // Style for button
                changeStatusButton.setText("Начать исполнение");
                changeStatusButton.setBackgroundColor(Color.rgb(245, 194, 66));

                // Changing status local instance status
                receivedOrder.setStatus(receivedOrder.getStatus().ordinal() + 1);

                //changeStatusButton.refreshDrawableState();

                // Changing status on the server

                break;

            case STARTED:
                // Style for button
                changeStatusButton.setText("На месте!");
                changeStatusButton.setBackgroundColor(Color.rgb(245, 194, 66));

                // Changing status local instance status
                receivedOrder.setStatus(receivedOrder.getStatus().ordinal() + 1);
                //changeStatusButton.refreshDrawableState();

                // Changing status on the server

                break;

            case ARRIVED:
                // Style for button
                changeStatusButton.setText("Завршить заказ");
                changeStatusButton.setBackgroundColor(Color.rgb(245, 194, 66));

                // Changing status local instance status
                receivedOrder.setStatus(receivedOrder.getStatus().ordinal() + 1);
                //changeStatusButton.refreshDrawableState();

                // Changing status on the server

                break;

            case DONE:
                // Style for button
                changeStatusButton.setVisibility(View.GONE);
//                changeStatusButton.refreshDrawableState();

                break;
            default:
                Toast.makeText(this,"WRONG_STATUS", Toast.LENGTH_LONG);
        }
    }

    void didChangeStatusButtonClick(View view) {

        // Changing status local instance status
        receivedOrder.setStatus(receivedOrder.getStatus().ordinal() + 1);

        //changeStatusButton.refreshDrawableState();

        // Changing status on the server
    }
}
