package com.example.garbagecollector;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class OrdersListViewAdapter extends BaseAdapter {

    private Context context;
    private ArrayList <OrderDataModel> dataSet;

    public OrdersListViewAdapter(Context context, ArrayList dataSet) {
        this.context = context;
        this.dataSet = dataSet;
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
        return 0;
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

            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        holder.nameTextView.setText("Name : " + dataSet.get(i).getCustomerName());
        holder.addressTextView.setText("Adress : " + dataSet.get(i).getAdress());

        return view;
    }

    private class ViewHolder {
        protected TextView nameTextView, addressTextView;
    }
}
