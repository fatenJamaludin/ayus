package com.app.tbd.ui.Model;

import java.util.ArrayList;

public class TransactionHistory {

    private String Year;
    private ArrayList<TransactionItem> Items;

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        this.Year = year;
    }

    public ArrayList<TransactionItem> getItems() {
        return Items;
    }

    public void setItems(ArrayList<TransactionItem> Items) {
        this.Items = Items;
    }

}
