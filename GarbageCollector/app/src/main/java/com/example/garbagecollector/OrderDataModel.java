package com.example.garbagecollector;

import android.os.Parcelable;

import java.io.Serializable;

public class OrderDataModel implements Serializable {

    private String customerName;
    private String adress;

//    public OrderDataModel(String customerName, String adress) {
//        this.customerName = customerName;
//        this.adress = adress;
//    }


    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getAdress() {
        return adress;
    }
}
