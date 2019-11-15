package com.example.garbagecollector;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoginDialog.LoginDialogListener {

    final int REQUEST_CODE_FOR_ORDER = 1;
    public static final String ORDER_ID_RESPONSE = "RESPONSE_WITH_ORDER_ID";
    public final static String NEW_STATUS_ID_RESPONSE = "RESPONSE_WITH_NEW_STATUS_ID";



    private ListView ordersListView;
    private ArrayList <OrderDataModel> ordersList;
    private ListAdapter listAdapter;

    private static ProgressDialog progressDialog;

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ordersListView = findViewById(R.id.ordersListView);
        showLoginDialog();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((resultCode == RESULT_OK) && (requestCode == REQUEST_CODE_FOR_ORDER)) {

            int orderToChangeId = data.getIntExtra(ORDER_ID_RESPONSE, 0);
            int newStatusID = data.getIntExtra(NEW_STATUS_ID_RESPONSE, 0);

            for (OrderDataModel order : ordersList ) {
                if (order.getId() == orderToChangeId) {

                    order.setStatus(newStatusID);

                        System.out.println("        STATUS CHANGED! ");
                        System.out.println(order.getStatus());
                }
            }
        }
    }

    // Methods
    private void retrieveJSONwithAuthentification() {

        //showSimpleProgressDialog(this, "loading", "fetching JSON", true);

        final JSONObject requestBody = new JSONObject();

        try {
            requestBody.put("login", "huawei");
            requestBody.put("password", "huawei");
            requestBody.put("request_type", "authentication");
        }
        catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response", ">>" + response);

                        try {
                            JSONObject json = response; //new JSONObject(response);

                            if (json.optString("status").equals("OK")) { // ???optionallllyyy????

                                ordersList = new ArrayList<>();

                                JSONArray jsonArray = json.getJSONArray("data");

                                for (int i=0; i<jsonArray.length(); i++) {

                                    OrderDataModel currentOrder = new OrderDataModel();
                                    JSONObject currentJsonObject = jsonArray.getJSONObject(i);

                                    currentOrder.setId(currentJsonObject.getInt("id"));
                                    currentOrder.setCustomerName(currentJsonObject.getString("customer_name"));
                                    currentOrder.setOriginAdress(currentJsonObject.getString("origin_address"));
                                    currentOrder.setDestinantionAddress(currentJsonObject.getString("destination_address"));
                                    currentOrder.setOriginAdress(currentJsonObject.getString("origin_address"));
                                    currentOrder.setDeliveryTime(currentJsonObject.getString("delivery_time"));
                                    currentOrder.setPayment(currentJsonObject.getInt("payment"));
                                    currentOrder.setStatus(currentJsonObject.getInt("status"));
                                    currentOrder.setNumberOfMovers(currentJsonObject.getInt("number_of_movers"));
                                    currentOrder.setPhoneNumber(currentJsonObject.getString("customer_phone_number"));

                                    ordersList.add(currentOrder);
                                }

                                setupListView();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // displays error in Toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        // add to request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }




    private void setupListView() {

        removeSimpleProgressDialog();

        listAdapter = new OrdersListViewAdapter(this, ordersList);
        ordersListView.setAdapter(listAdapter);

        // SEGUE
        ordersListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // getting listview click value into a OrderDataModel variable
                OrderDataModel clickedOrder = ordersList.get(i);

                Intent intent = new Intent(MainActivity.this, OrderDetailsActivity.class);

                System.out.println("        SENDING INSTANCE TO NEXT ACTIVIVTY (" + clickedOrder.getStatus());
                // Sending value to another activity
                intent.putExtra("ClickedOrder",  clickedOrder); // parcelable ??

                //startActivity(intent);
                startActivityForResult(intent, REQUEST_CODE_FOR_ORDER);
            }
        });
    }

    private static void showSimpleProgressDialog(Context context, String title, String message, boolean isCancelable) {
        try {
            if (progressDialog == null) {
                progressDialog = ProgressDialog.show(context, title, message);
                progressDialog.setCancelable(isCancelable);
            }
            if (!progressDialog.isShowing()) {
                progressDialog.show();
            }
        }
        catch (IllegalArgumentException ie){
            ie.printStackTrace();
        }
        catch (RuntimeException re) {
            re.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void removeSimpleProgressDialog() {
        try {
            if (progressDialog != null) {
                if (!progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    progressDialog = null;
                }
            }
        }
        catch (IllegalArgumentException ie){
            ie.printStackTrace();
        }
        catch (RuntimeException re) {
            re.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    private void showLoginDialog(){
        LoginDialog loginDialog = new LoginDialog();
        loginDialog.show(getSupportFragmentManager(), "loginDialog");

        // Disable OK button whilst not all the fields filled
        //((LoginDialog)loginDialog).get
    }


    @Override
    public void applyFilledFields(String username, String password, String socket) {
        // use u, p and s
        url = socket;

        retrieveJSONwithAuthentification();

        FileHolder.writeLoginDataToFile(username, password, socket, this);
    }


}
