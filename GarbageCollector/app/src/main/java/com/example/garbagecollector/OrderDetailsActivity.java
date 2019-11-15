package com.example.garbagecollector;

import android.annotation.SuppressLint;
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
    paymentTextView, numberOfMoversTextView, phoneNumberTextView, doneStatusTextView;
    Button changeStatusButton;

    OrderDataModel receivedOrder;

    @SuppressLint("SetTextI18n")
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
        doneStatusTextView = (TextView) findViewById(R.id.done_status);


        //MARK: Receiving Value into activity using intent   //OrderDataModel TempHolder = (OrderDataModel) getIntent().getSerializableExtra("ClickedOrder");
        Intent intent = getIntent();
        receivedOrder = (OrderDataModel) intent.getExtras().getSerializable(MainActivity.CLICKED_ORDER_EXTRA);

        //Dislaying Info in TextViews
        nameTextView.setText("\uD83D\uDC64 Заказчик:  " + receivedOrder.getCustomerName());
        originAddressTextView.setText("\uD83D\uDCCC Откуда:  " + receivedOrder.getOriginAdress());
        destinationAddressTextView.setText("\uD83C\uDFC1 Куда:  " + receivedOrder.getDestinantionAddress());
        deliveryTimeTextView.setText("\uD83D\uDD50 Ко времени:  " + receivedOrder.getDeliveryTime());
        paymentTextView.setText("\uD83D\uDCB8 Сумма к оплате:  " + String.valueOf(Double.valueOf(receivedOrder.getPayment())) + "₱");
        numberOfMoversTextView.setText("‍️\uD83E\uDD84 ️Грузчиков:  " + String.valueOf(receivedOrder.getNumberOfMovers()));
        phoneNumberTextView.setText("\uD83D\uDCF1 Номер телефона заказчика:  " + receivedOrder.getPhoneNumber());

        redrawInterface();
    }

    public void didChangeStatusButtonClick(View view) {

        // MARK: Changing local instance status and redrawing interface
        if (receivedOrder != null) {

            // Changing local copy
            int num = receivedOrder.getStatus().ordinal();
            System.out.println("--->"+num);
            int statusId = receivedOrder.getStatus().ordinal() + 2; // 2 is server shift
            receivedOrder.setStatus(statusId+1);

            // Returning result to MainActivity ( <-- )
            Intent intent = new Intent();
            intent.putExtra(MainActivity.ORDER_ID_RESPONSE, receivedOrder.getId());
            intent.putExtra(MainActivity.NEW_STATUS_ID_RESPONSE, statusId + 1);
            setResult(RESULT_OK, intent);

            // updateUI
            redrawInterface();

            // TODO Changing status on the server (POST-request)
        }




    }

    private void redrawInterface(){
        switch (receivedOrder.getStatus()) {
            case DEFAULT:
                // Style for button
                changeStatusButton.setText("Начать исполнение");
                changeStatusButton.setBackgroundColor(Color.rgb(245, 194, 66));

                // TODO: change status to SEEN

                break;

            case SEEN:
                // Style for button
                changeStatusButton.setText("Начать исполнение (п)");
                changeStatusButton.setBackgroundColor(Color.rgb(245, 194, 66));
                break;

            case STARTED:
                // Style for button
                changeStatusButton.setText("На месте!");
                changeStatusButton.setBackgroundColor(Color.rgb(255, 255, 102));
                break;

            case ARRIVED:
                // Style for button
                changeStatusButton.setText("Завершить заказ");
                changeStatusButton.setBackgroundColor(Color.rgb(72, 219, 136));
                break;

            case DONE:
                // Style for button
                changeStatusButton.setVisibility(View.GONE);
                doneStatusTextView.setVisibility(View.VISIBLE);
                break;

            default:
                Toast.makeText(this,"WRONG_STATUS", Toast.LENGTH_LONG);
        }
    }
}
