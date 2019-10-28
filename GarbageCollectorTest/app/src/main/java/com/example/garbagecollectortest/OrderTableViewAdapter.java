package com.example.garbagecollectortest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderTableViewAdapter extends RecyclerView.Adapter <OrderCellHolder> {

    Context currentContext;
    List<String> dataSet;

    // Constructor
    public OrderTableViewAdapter(Context currentContext, List<String> dataSet) {
        this.currentContext = currentContext;
        this.dataSet = dataSet;
    }

    @NonNull
    @Override
    public OrderCellHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(currentContext).inflate(R.layout.order_cell, parent, false);
        return new OrderCellHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderCellHolder holder, int position) {
        holder.titleLabel.setText(dataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    // MARK: stuff for transition


    private onItemClickListener listener;

    public interface onItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }
}
