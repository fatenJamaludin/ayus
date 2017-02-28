package com.app.tbd.ui.Model.Request;

import java.util.List;

/**
 * Created by Dell on 12/16/2015.
 */
public class ChangeSSR {

    private String booking_id;

    private String signature;
    private String pnr;
    private List<PassengerMeal> going_flight;
    private List<PassengerMeal> return_flight;

    public List<PassengerMeal> getGoing_flight() {
        return going_flight;
    }

    public void setGoing_flightMeal(List<PassengerMeal> going_flight) {
        this.going_flight = going_flight;
    }

    public List<PassengerMeal> getReturn_flight() {
        return return_flight;
    }

    public void setReturn_flightMeal(List<PassengerMeal> return_flight) {
        this.return_flight = return_flight;
    }

    public ChangeSSR(){

    }

    public ChangeSSR(ChangeSSR data){

        booking_id = data.getBooking_id();
        signature = data.getSignature();
        going_flight = data.getGoing_flight();
        return_flight = data.return_flight;
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
