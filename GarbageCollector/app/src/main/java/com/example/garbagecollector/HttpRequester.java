package com.example.garbagecollector;

import android.content.Context;
import android.widget.Toast;

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

public class HttpRequester {

    public static ArrayList<OrderDataModel> fetchOrderListWithKeyWord(final Context context, String url, String keyword, String driverId) {

        final ArrayList<OrderDataModel> resultList = new ArrayList<>();

        final JSONObject requestBody = new JSONObject();

        try {
            requestBody.put("request_type", "get_order_list");
            requestBody.put("id", driverId);
            requestBody.put("keyword", keyword);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, requestBody, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    if (!response.optString("status").equals("OK")) {
                        Toast.makeText(context, "Keyword/ID hasn't applied by server", Toast.LENGTH_LONG);
                    } else {
                        JSONArray jsonArray = response.getJSONArray("data");

                        for (int i = 0; i < jsonArray.length(); i++) {

                            OrderDataModel currentOrder = new OrderDataModel();
                            JSONObject currentJsonArrayObject = jsonArray.getJSONObject(i);

                            currentOrder.setId(currentJsonArrayObject.getInt("id"));
                            currentOrder.setCustomerName(currentJsonArrayObject.getString("customer_name"));
                            currentOrder.setOriginAdress(currentJsonArrayObject.getString("origin_address"));
                            currentOrder.setDestinantionAddress(currentJsonArrayObject.getString("destination_address"));
                            currentOrder.setOriginAdress(currentJsonArrayObject.getString("origin_address"));
                            currentOrder.setDeliveryTime(currentJsonArrayObject.getString("delivery_time"));
                            currentOrder.setPayment(currentJsonArrayObject.getInt("payment"));
                            currentOrder.setStatus(currentJsonArrayObject.getInt("status"));
                            currentOrder.setNumberOfMovers(currentJsonArrayObject.getInt("number_of_movers"));
                            currentOrder.setPhoneNumber(currentJsonArrayObject.getString("customer_phone_number"));

                            resultList.add(currentOrder);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Server Response Error", Toast.LENGTH_LONG);
            }
        });

        return resultList;
    }


    public static void changeOrderStatusOnServer(final Context context, String url, int orderId, int newStatusId) {

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
                    Toast.makeText(context/*getApplicationContext()*/, "Server haven't accepted status ID!", Toast.LENGTH_LONG).show();
                }
            }
        },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context/*getApplicationContext()*/, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        // add to request queue
        RequestQueue requestQueue = Volley.newRequestQueue(/*this*/ context);
        requestQueue.add(request);
    }
}
