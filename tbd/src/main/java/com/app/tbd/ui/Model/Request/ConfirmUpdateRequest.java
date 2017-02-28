package com.app.tbd.ui.Model.Request;

/**
 * Created by Dell on 1/18/2016.
 */
public class ConfirmUpdateRequest {

    private String signature;
    private String booking_id;
    private String pnr;

    /*Initiate Class*/
    public ConfirmUpdateRequest(){
    }

    public ConfirmUpdateRequest(ConfirmUpdateRequest data){
        booking_id = data.getBooking_id();
        signature = data.getSignature();
        pnr = data.getPnr();
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }

    public String getPnr() {
        return pnr;
    }

    public void setPnr(String pnr) {
        this.pnr = pnr;
    }

}
