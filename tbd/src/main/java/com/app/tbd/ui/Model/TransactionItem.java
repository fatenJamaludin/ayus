package com.app.tbd.ui.Model;

public class TransactionItem {

    private String TransactionDate;
    private String Merchant;
    private String ItemYear;
    private String ItemPoint;

    public String getTransactionDate() {
        return TransactionDate;
    }

    public void setTransactionDate(String TransactionDate) {
        this.TransactionDate = TransactionDate;
    }

    public String getItemPoint() {
        return ItemPoint;
    }

    public void setItemPoint(String ItemPoint) {
        this.ItemPoint = ItemPoint;
    }

    public String getItemYear() {
        return ItemYear;
    }

    public void setItemYear(String ItemYear) {
        this.ItemYear = ItemYear;
    }

    public String getMerchant() {
        return Merchant;
    }

    public void setMerchant(String Merchant) {
        this.Merchant = Merchant;
    }

}
