package com.example.garbagecollector;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;

import org.json.JSONObject;

import java.util.ArrayList;

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
        this.swipeRefreshLayout = swipeRefreshLayout;
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
        Drawable resource = context.getResources().getDrawable(imageResource);

        holder.customerImage.setImageDrawable(resource); //        holder.customerImage.setImageResource(R.drawable.av1);



        //REFRESH
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshUI();

//                OrderDataModel sampleOrder = new OrderDataModel();
//                sampleOrder.setOriginAdress("******* origin address");
//                sampleOrder.setCustomerName("******* customer name");
//                dataSet.add(sampleOrder);
//                OrdersListViewAdapter.this.notifyDataSetChanged();
//                swipeRefreshLayout.setRefreshing(false);

                // SYNC REQUEST
                RequestFuture<JSONObject> future = RequestFuture.newFuture();
                JsonObjectRequest request = new JsonObjectRequest(URL, new JSONObject(), future, future);
                requestQueue.add(request);

                try {
                    JSONObject response = future.get(); // this will block
                } catch (InterruptedException e) {
                    // exception handling
                } catch (ExecutionException e) {
                    // exception handling
                }

            }
        });

        return view;
    }

    public InputStream runInputStreamRequest(int method, String url, Response.ErrorListener errorListener) {
        RequestFuture<InputStream> future = RequestFuture.newFuture();
        InputStreamRequest request = new InputStreamRequest(method, url, future, errorListener);
        getQueue().add(request);
        try {
            return future.get(REQUEST_TIMEOUT, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Log.e("Retrieve cards api call interrupted.", e);
            errorListener.onErrorResponse(new VolleyError(e));
        } catch (ExecutionException e) {
            Log.e("Retrieve cards api call failed.", e);
            errorListener.onErrorResponse(new VolleyError(e));
        } catch (TimeoutException e) {
            Log.e("Retrieve cards api call timed out.", e);
            errorListener.onErrorResponse(new VolleyError(e));
        }
        return null;
    }


    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }


    private void refreshUI(){
        // TODO request here

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dataSet.clear();
                ArrayList<OrderDataModel> newList = HttpRequester.fetchOrderListWithKeyWord(context.getApplicationContext(), url, keyword, driverId);
                dataSet.addAll(newList);

                //swipeRefreshLayout.setRefreshing(true);
            }
        },1000);

        OrdersListViewAdapter.this.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);

    }



    private class ViewHolder {
        protected TextView nameTextView, addressTextView, statusTextView;
        protected ImageView customerImage;
        protected RelativeLayout cellContainer;
    }

}
