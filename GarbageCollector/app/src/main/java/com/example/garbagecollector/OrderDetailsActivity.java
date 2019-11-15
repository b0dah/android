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

    OrderDataModel receivedOrder;

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
        receivedOrder = (OrderDataModel) intent.getExtras().getSerializable("ClickedOrder");

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
                break;

            case SEEN:
                // Style for button
                changeStatusButton.setText("Начать исполнение");
                changeStatusButton.setBackgroundColor(Color.rgb(245, 194, 66));
                break;

            case STARTED:
                // Style for button
                changeStatusButton.setText("На месте!");
                changeStatusButton.setBackgroundColor(Color.rgb(245, 194, 66));
                break;

            case ARRIVED:
                // Style for button
                changeStatusButton.setText("Завршить заказ");
                changeStatusButton.setBackgroundColor(Color.rgb(245, 194, 66));
                break;

            case DONE:
                // Style for button
                changeStatusButton.setVisibility(View.GONE);
                break;

            default:
                Toast.makeText(this,"WRONG_STATUS", Toast.LENGTH_LONG);
        }
    }

    public void didChangeStatusButtonClick(View view) {

        // Changing local instance status
        if (receivedOrder != null) {

            int num = receivedOrder.getStatus().ordinal();
            System.out.println("--->"+num);

            receivedOrder.setStatus(receivedOrder.getStatus().ordinal() + 3);
            //receivedOrder.setStatus(3);
        }

        //changeStatusButton.refreshDrawableState();
        //this.recreate();

        // Changing status on the server
        // POST-request
    }
}
