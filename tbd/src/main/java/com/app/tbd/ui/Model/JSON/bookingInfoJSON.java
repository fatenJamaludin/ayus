package com.app.tbd.ui.Model.JSON;

import io.realm.RealmObject;

public class bookingInfoJSON extends RealmObject {

    private String bookingInfo;

    public String getBookingInfo() {
        return bookingInfo;
    }

    public void setBookingInfo(String bookingInfo) {
        this.bookingInfo = bookingInfo;
    }

}