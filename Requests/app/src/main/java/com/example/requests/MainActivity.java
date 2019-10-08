package com.example.requests;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class MainActivity extends AppCompatActivity {

    String url = "http://176.59.71.245:7777"; // home ip "http://95.30.31.115:7777"; //;//;////"http://google.com";//"http://172.20.10.7:7777";//"http://192.168.43.115:7777";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendPostRequest();
        //sendGetRequest();
    }

    public void onSendButtonClick(View view) {



    }




    // MARK: funcs
    private void sendGetRequest() {

        final TextView textView = (TextView) findViewById(R.id.textViewID);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

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

        EditText ipEdit = (EditText) findViewById(R.id.ipEdit);

        final TextView textView = (TextView) findViewById(R.id.textViewID);


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        JSONObject postParameters = new JSONObject();
        try {
            postParameters.put("command", "get_all_about_order");
            postParameters.put("number_order", "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String postString = "privet:beslan";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postParameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {



//                        try
//                        {
//                            String decoded = URLDecoder.decode(response, "UTF-8");
//                            Log.e("UTF 8",decoded );
//                        }
//                        catch (UnsupportedEncodingException e)
//                        {
//                            Log.e("utf8", "conversion", e);
//                        }


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
