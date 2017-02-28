package com.app.tbd.ui.Model.Request;

/**
 * Created by Dell on 1/21/2016.
 */
public class GetChangeFlight {

    private String pnr;
    private String booking_id;
    private String signature;
    private ChangeFlight going_flight;
    private ChangeFlight return_flight;

    public GetChangeFlight(){

    }

    public GetChangeFlight(GetChangeFlight data){
        booking_id = data.getBooking_id();
        signature = data.getSignature();
        pnr = data.getPnr();
        going_flight = data.getGoing_flight();
        return_flight = data.getReturn_flight();
    }

    public ChangeFlight getReturn_flight() {
        return return_flight;
    }

    public void setReturn_flight(ChangeFlight return_flight) {
        this.return_flight = return_flight;
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

    public ChangeFlight getGoing_flight() {
        return going_flight;
    }

    public void setGoing_flight(ChangeFlight going_flight) {
        this.going_flight = going_flight;
    }

    public String getPnr() {
        return pnr;
    }

    public void setPnr(String pnr) {
        this.pnr = pnr;
    }


}
