package com.example.garbagecollector;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView ordersListView;
    ArrayList <OrderDataModel> ordersList;
    private ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ordersListView = findViewById(R.id.ordersListView);

        ordersList = new ArrayList<>();
        ordersList.add(new OrderDataModel("Vanusick", "Burgasskaaya, 21"));
        ordersList.add(new OrderDataModel("Danusick", "Kalinovaya, 305"));

        setupListView();
    }

    private void setupListView() {

        listAdapter = new OrdersListViewAdapter(this, ordersList);
        ordersListView.setAdapter(listAdapter);
    }
}
