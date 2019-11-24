package com.example.garbagecollector;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoginDialog.LoginDialogListener {

    // DEFINE section
    final int REQUEST_CODE_FOR_ORDER = 1;
    public static final String ORDER_ID_RESPONSE = "RESPONSE_WITH_ORDER_ID";
    public final static String NEW_STATUS_ID_RESPONSE = "RESPONSE_WITH_NEW_STATUS_ID";
    public final static String CLICKED_ORDER_EXTRA = "ClickedOrder";
    public final static String SERVER_URL_EXTRA = "SERVER_URL_EXTRA";

    //Fields
    private ListView ordersListView;
    private ArrayList <OrderDataModel> ordersList;
    private /*ListAdapter*/ OrdersListViewAdapter listAdapter;

    private static ProgressDialog progressDialog;
    SwipeRefreshLayout swipeToRefreshWidget;

    private String username, password, url, driverId, keyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"grey\">" + "Ордерс" + "</font>")); // getSupportActionBar().setTitle("Ордеры");

        ordersListView = findViewById(R.id.ordersListView);
        ordersListView.setDivider(null);
        swipeToRefreshWidget = findViewById(R.id.pullToRefreshWidget);

        // ??????
        //ordersListView.setAdapter(listAdapter);

        //Configuring Swipe to refresh
//        swipeToRefreshWidget = findViewById(R.id.pullToRefreshWidget);
//        swipeToRefreshWidget.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//                //retrieveOrdersWithKeyword();
//                ArrayList<OrderDataModel> a = new ArrayList<>();
//                updateOrdersList(a);
//                swipeToRefreshWidget.setRefreshing(false);
//            }
//        });

//        swipeToRefreshWidget.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                OrderDataModel sampleOrder = new OrderDataModel();
//                sampleOrder.setOriginAdress("origin address");
//                sampleOrder.setCustomerName("customer name");
//                ordersList.add(sampleOrder);
//                listAdapter.notifyDatasetChanged();
//            }
//        });

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

            // REQUEST WITH KEYBOARD
//            final Context context = this;
//
//            //ordersList = new ArrayList<>();
//                    //ordersList.clear();
//                   //final ArrayList<OrderDataModel> newOrderList = new ArrayList<>();
//
//            final JSONObject requestBody = new JSONObject();
//
//            try {
//                requestBody.put("request_type", "get_order_list");
//                requestBody.put("id", driverId);
//                requestBody.put("keyword", keyword);
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, requestBody, new Response.Listener<JSONObject>() {
//
//                @Override
//                public void onResponse(JSONObject response) {
//
//                    try {
//                        if (!response.optString("status").equals("OK")) {
//                            Toast.makeText(context, "Keyword/ID hasn't applied by server", Toast.LENGTH_LONG);
//                        } else {
//                            JSONArray jsonArray = response.getJSONArray("data");
//
//                            for (int i = 0; i < jsonArray.length(); i++) {
//
//                                OrderDataModel currentOrder = new OrderDataModel();
//                                JSONObject currentJsonArrayObject = jsonArray.getJSONObject(i);
//
//                                currentOrder.setId(currentJsonArrayObject.getInt("id"));
//                                currentOrder.setCustomerName(currentJsonArrayObject.getString("customer_name"));
//                                currentOrder.setOriginAdress(currentJsonArrayObject.getString("origin_address"));
//                                currentOrder.setDestinantionAddress(currentJsonArrayObject.getString("destination_address"));
//                                currentOrder.setOriginAdress(currentJsonArrayObject.getString("origin_address"));
//                                currentOrder.setDeliveryTime(currentJsonArrayObject.getString("delivery_time"));
//                                currentOrder.setPayment(currentJsonArrayObject.getInt("payment"));
//                                currentOrder.setStatus(currentJsonArrayObject.getInt("status"));
//                                currentOrder.setNumberOfMovers(currentJsonArrayObject.getInt("number_of_movers"));
//                                currentOrder.setPhoneNumber(currentJsonArrayObject.getString("customer_phone_number"));
//
//                                //newOrderList.add(currentOrder);
//                            }
//
//                        }
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Toast.makeText(context, "Server Response Error", Toast.LENGTH_LONG);
//                }
//            });
//
//            RequestQueue requestQueue = Volley.newRequestQueue(/*this*/ context);
//            //requestQueue.add(request);
//
//            //listAdapter.notifyDataSetChanged();
//            //listAdapter.updateOrdersLists(newOrderList);

            listAdapter.notifyDataSetChanged();
        }
    }

    // Methods
    private void retrieveJSONwithAuthentification() {

        //showSimpleProgressDialog(this, "loading", "fetching JSON", false);

        final JSONObject requestBody = new JSONObject();

        try {
            requestBody.put("login", username);
            requestBody.put("password", password);
            requestBody.put("request_type", "authentication");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response", ">>" + response);

                        try {
                            JSONObject json = response; //new JSONObject(response);

                            driverId = json.optString("id");
                            keyword = json.optString("keyword");


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
                            else Toast.makeText(getApplicationContext(), "WRONG LOGIN DATA!", Toast.LENGTH_LONG).show();

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

        listAdapter = new OrdersListViewAdapter(this, ordersList, swipeToRefreshWidget, url, keyword, driverId);
        ordersListView.setAdapter(listAdapter);

        // SEGUE
        ordersListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // getting listview click value into a OrderDataModel variable
                OrderDataModel clickedOrder = ordersList.get(i);
                Intent intent = new Intent(MainActivity.this, OrderDetailsActivity.class);

                // Sending value to another activity
                intent.putExtra(CLICKED_ORDER_EXTRA,  clickedOrder); // parcelable ??
                intent.putExtra(SERVER_URL_EXTRA, url);

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
        this.username = username;
        this.password = password;
        this.url = socket;

        retrieveJSONwithAuthentification();

        FileHolder.writeLoginDataToFile(username, password, socket, this);
    }

}
