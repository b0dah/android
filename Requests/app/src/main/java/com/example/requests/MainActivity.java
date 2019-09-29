package com.example.requests;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendPostRequest();
        //sendGetRequest();
    }

    // MARK: funcs
    private void sendGetRequest() {

        final TextView textView = (TextView) findViewById(R.id.textViewID);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://172.20.10.7:7777";//"http://192.168.43.115:7777"; //"http://www.google.com";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        textView.setText("Response is: "+ response.substring(0,500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("That didn't work!" + error);
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void sendPostRequest() {

        final TextView textView = (TextView) findViewById(R.id.textViewID);
        String url = "http://172.20.10.7:7777";//"http://192.168.43.115:7777";
        String weatherUrl = "http://api.openweathermap.org/data/2.10/weather";


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        JSONObject postParameters = new JSONObject();
        try {
            postParameters.put("salutation", "Beeeeeesurique");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, weatherUrl, postParameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("------>" + response);
                        textView.setText("Response is: "+ response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("------> that's error!" + error);
                        textView.setText("Response is: "+ error);

                    }
                }
        );
        queue.add(jsonObjectRequest);
    }

}
