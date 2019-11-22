package com.example.garbagecollector;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.textclassifier.TextLinks;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import javax.xml.datatype.Duration;

public class OrderDetailsActivity extends AppCompatActivity {

    TextView nameTextView, originAddressTextView, destinationAddressTextView, deliveryTimeTextView,
    paymentTextView, numberOfMoversTextView, phoneNumberTextView, doneStatusTextView;
    Button changeStatusButton;
    ImageView customerImageView;

    private OrderDataModel receivedOrder;
    private String serverUrl;

    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.order_details_activity);
        getSupportActionBar().hide();

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
        customerImageView = (ImageView) findViewById(R.id.customer_image);


        //MARK: Receiving Value into activity using intent   //OrderDataModel TempHolder = (OrderDataModel) getIntent().getSerializableExtra("ClickedOrder");
        Intent intent = getIntent();
        receivedOrder = (OrderDataModel) intent.getExtras().getSerializable(MainActivity.CLICKED_ORDER_EXTRA);
        serverUrl = (String) intent.getStringExtra(MainActivity.SERVER_URL_EXTRA);


        //Dislaying Info in TextViews
        nameTextView.setText("\uD83D\uDC64 Заказчик:  " + receivedOrder.getCustomerName());
        originAddressTextView.setText("\uD83D\uDCCC Откуда:  " + receivedOrder.getOriginAdress());
        destinationAddressTextView.setText("\uD83C\uDFC1 Куда:  " + receivedOrder.getDestinantionAddress());
        deliveryTimeTextView.setText("\uD83D\uDD50 Ко времени:  " + receivedOrder.getDeliveryTime());
        paymentTextView.setText("\uD83D\uDCB8 Сумма к оплате:  " + String.valueOf(Double.valueOf(receivedOrder.getPayment())) + "₱");
        numberOfMoversTextView.setText("‍️\uD83E\uDD84 ️Грузчиков:  " + String.valueOf(receivedOrder.getNumberOfMovers()));
        phoneNumberTextView.setText("\uD83D\uDCF1 Номер телефона заказчика:  " + receivedOrder.getPhoneNumber());


        Context context = getApplicationContext();

        String uri = ("@drawable/av").concat(String.valueOf(receivedOrder.getId()%5 + 1));
        int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
        Drawable resource = context.getResources().getDrawable(imageResource);
        this.customerImageView.setImageDrawable(resource); //        holder.customerImage.setImageResource(R.drawable.av1);

        redrawInterface();
    }

    public void didChangeStatusButtonClick(View view/*del?*/) {

        // MARK: Changing local instance status and redrawing interface
        if (receivedOrder != null) {

            // Changing local copy
            int num = receivedOrder.getStatus().ordinal();
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
            changeOrderStatusOnServer(serverUrl, receivedOrder.getId(), statusId+1);
        }




    }

    private void redrawInterface(){
        switch (receivedOrder.getStatus()) {
            case DEFAULT:
                // Style for button
                changeStatusButton.setText("Начать исполнение");
                changeStatusButton.setBackgroundColor(Color.rgb(245, 194, 66));

                // TODO: change status to SEEN
//                    didChangeStatusButtonClick();
//                    dispatchQueue with delay 700ms
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
                Toast.makeText(this,"WRONG_STATUS", Toast.LENGTH_LONG).show();
        }
    }

    private void changeOrderStatusOnServer(String url, int orderId, int newStatusId) {

        final JSONObject requestBody = new JSONObject();

        try {
            requestBody.put("request_type", "change_status");
            requestBody.put("order_id", orderId);
            requestBody.put("new_status", newStatusId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, requestBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    if (!response.optString("status").equals("OK")) {
                        Toast.makeText(getApplicationContext(), "Server haven't accepted status ID!", Toast.LENGTH_LONG).show();
                    }
                }
            },

            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        // add to request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}
