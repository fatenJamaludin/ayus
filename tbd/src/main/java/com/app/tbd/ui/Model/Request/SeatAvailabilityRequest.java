package com.app.tbd.ui.Model.Request;

/**
 * Created by Dell on 1/19/2016.
 */
public class SeatAvailabilityRequest {

    private String pnr;
    private String booking_id;
    private String signature;

    public SeatAvailabilityRequest(){

    }

    public SeatAvailabilityRequest(SeatAvailabilityRequest data){
        booking_id = data.getBooking_id();
        signature = data.getSignature();
        pnr = data.getPnr();
    }

    public String getPnr() {
        return pnr;
    }

    public void setPnr(String pnr) {
        this.pnr = pnr;
    }

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
