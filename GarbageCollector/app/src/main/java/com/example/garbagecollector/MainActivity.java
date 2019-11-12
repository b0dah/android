package com.example.garbagecollector;

import androidx.appcompat.app.AppCompatActivity;

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

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoginDialog.LoginDialogListener {

//    View customLayout;


    private ListView ordersListView;
    ArrayList <OrderDataModel> ordersList;
    private ListAdapter listAdapter;

    private static ProgressDialog progressDialog;

    private String url = "http://212.192.144.106:7777";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ordersListView = findViewById(R.id.ordersListView);

//        ordersList = new ArrayList<>();
//        ordersList.add(new OrderDataModel("Vanusick", "Burgasskaaya, 21"));
//        ordersList.add(new OrderDataModel("Danusick", "Kalinovaya, 305"));

//        customLayout = getLayoutInflater().inflate(R.layout.login_alert_layout, null);
        showLoginDialog();

        //retrieveJSONwithAuthentification();
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

                                    currentOrder.setCustomerName(currentJsonObject.getString("name"));
                                    currentOrder.setAdress(currentJsonObject.getString("origin_address"));

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

                // Sending value to another activity

                intent.putExtra("ClickedOrder",  clickedOrder); // parcelable ??

                startActivity(intent);
            }
        });
    }

    private void retrieveJSON() {

        showSimpleProgressDialog(this, "loading", "fetching JSON", true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", ">>" + response);

                        try {
                            JSONObject json = new JSONObject(response);

                            if (json.optString("Status").equals("true")) { // ???optionallllyyy????

                                ordersList = new ArrayList<>();

                                JSONArray jsonArray = json.getJSONArray("data");

                                for (int i=0; i<jsonArray.length(); i++) {

                                    OrderDataModel currentOrder = new OrderDataModel();
                                    JSONObject currentJsonObject = jsonArray.getJSONObject(i);

                                    currentOrder.setCustomerName(currentJsonObject.getString("name"));
                                    currentOrder.setAdress(currentJsonObject.getString("address"));

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
    }


    @Override
    public void applyFilledFields(String username, String password, String socket) {
        // use u, p and s
        url = socket;

        retrieveJSONwithAuthentification();
    }
}
