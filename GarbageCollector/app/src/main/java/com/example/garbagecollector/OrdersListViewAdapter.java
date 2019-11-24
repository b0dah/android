package com.example.garbagecollector;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class OrdersListViewAdapter extends BaseAdapter {

    private Context context;
    private ArrayList <OrderDataModel> dataSet;
    private String url, keyword, driverId;

    private SwipeRefreshLayout swipeRefreshLayout;

    public OrdersListViewAdapter(Context context, ArrayList dataSet, SwipeRefreshLayout swipeRefreshLayout, String url, String keyword, String driverId) {
        this.context = context;
        this.dataSet = dataSet;

        this.url = url;
        this.keyword = keyword;
        this.driverId = driverId;
//        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    // MARK: required Methods
    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public Object getItem(int i) {
        return dataSet.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        if (view == null) {
            holder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.order_cell_layout, null, true);

            holder.nameTextView = (TextView) view.findViewById(R.id.name);
            holder.addressTextView = (TextView) view.findViewById(R.id.address);
            holder.statusTextView =  (TextView) view.findViewById(R.id.status);
            holder.customerImage = (ImageView) view.findViewById(R.id.customer_image); // |•|
            holder.cellContainer = (RelativeLayout) view.findViewById(R.id.cell_container_relative_layout);

            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        //MARK: ANIMATION (?) Dunno, is it appropriate place foк animation applying
        //Apply animation for image
        holder.customerImage.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition));

        //Apply animation for the Card
        holder.cellContainer.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale));

        // TextViews Setting
        holder.nameTextView.setText("Заказчик : " + dataSet.get(i).getCustomerName());
        holder.addressTextView.setText("Адрес : " + dataSet.get(i).getOriginAdress());

        switch (dataSet.get(i).getStatus()) {
            case DEFAULT: holder.statusTextView.setText("Принят"); break;
            case SEEN: holder.statusTextView.setText("Просмотрен"); break; // 3 просмотрел
            case STARTED: holder.statusTextView.setText("Выполняется"); break; // 4 выехал в начальную точку
            case ARRIVED: holder.statusTextView.setText("На месте"); break; // 5 прибыл в начальную точку
            case DONE: holder.statusTextView.setText("Выполнен");
                holder.statusTextView.setTextColor(ContextCompat.getColor(context, R.color.done_status_color));  //holder.statusTextView.setTextColor(context.getResources().getColor(R.color.done_status_color));
                break; // 6 заврешиил
            default: holder.statusTextView.setText("Ошибка");
                holder.statusTextView.setTextColor(ContextCompat.getColor(context, R.color.error_status_color));// LEGA (String.valueOf(dataSet.get(i).getStatus()));
        }


        //IMAGE
        String uri = ("@drawable/av").concat(String.valueOf(dataSet.get(i).getId()%5 + 1));
        int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
        final Drawable resource = context.getResources().getDrawable(imageResource);

        holder.customerImage.setImageDrawable(resource); //        holder.customerImage.setImageResource(R.drawable.av1);



        //REFRESH
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
                //refreshUI();

                // ====== from site ============
//                notifyDataSetChanged();
//                dataSet.clear();
//                fetchOrderListWithKeyWord();
                // =============================

//                OrderDataModel sampleOrder = new OrderDataModel();
//                sampleOrder.setOriginAdress("******* origin address");
//                sampleOrder.setCustomerName("******* customer name");
//                dataSet.add(sampleOrder);
//                OrdersListViewAdapter.this.notifyDataSetChanged();
//                swipeRefreshLayout.setRefreshing(false);

                // SYNC REQUEST
//                final JSONObject requestBody = new JSONObject();
//
//                try {
//                    requestBody.put("request_type", "get_order_list");
//                    requestBody.put("id", driverId);
//                    requestBody.put("keyword", keyword);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                RequestFuture<JSONObject> future = RequestFuture.newFuture();
//                JsonObjectRequest request = new JsonObjectRequest(url, requestBody, future, future);
//                RequestQueue requestQueue = Volley.newRequestQueue(context);
//                requestQueue.add(request);
//
//                try {
//                    JSONObject response = future.get(); // this will block
//                    System.out.println("RESPONSE --> " + response);
//
//                } catch (InterruptedException e) {
//                    // exception handling
//                } catch (ExecutionException e) {
//                    // exception handling
//                }
//
//            }
//        });

        return view;
    }

    public void updateOrdersLists(ArrayList<OrderDataModel> newList) {
        dataSet.clear();
        dataSet.addAll(newList);
        this.notifyDataSetChanged();
    }


    private void refreshUI(){
        // TODO request here



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();

                dataSet = new ArrayList<>();
                ArrayList<OrderDataModel> newList = HttpRequester.fetchOrderListWithKeyWord(context.getApplicationContext(), url, keyword, driverId);
                dataSet.addAll(newList);
            }
        },1000);

        swipeRefreshLayout.setRefreshing(false);

    }


    public void  fetchOrderListWithKeyWord() {

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

                            dataSet.add(currentOrder);
                        }

                        notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Server Response Error", Toast.LENGTH_LONG);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(/*this*/ context);
        requestQueue.add(request);

    }



    private class ViewHolder {
        protected TextView nameTextView, addressTextView, statusTextView;
        protected ImageView customerImage;
        protected RelativeLayout cellContainer;
    }

}
