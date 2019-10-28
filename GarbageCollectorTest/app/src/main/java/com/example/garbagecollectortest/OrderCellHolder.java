package com.example.garbagecollectortest;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrderCellHolder extends RecyclerView.ViewHolder {

    TextView titleLabel;

    public OrderCellHolder(@NonNull View itemView) {
        super(itemView);

        titleLabel = itemView.findViewById(R.id.OrderTitle);
    }
}
