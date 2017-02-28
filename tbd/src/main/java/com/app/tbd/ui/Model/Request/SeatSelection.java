package com.app.tbd.ui.Model.Request;

import java.util.ArrayList;

/**
 * Created by Dell on 12/22/2015.
 */
public class SeatSelection {


    private String booking_id;
    private String signature;
    private ArrayList<SeatSelect> going_flight = new ArrayList<SeatSelect>();
    private ArrayList<SeatSelect> return_flight = new ArrayList<SeatSelect>();

    public SeatSelection(){

    }

    public SeatSelection(SeatSelection data){
        booking_id = data.getBooking_id();
        signature = data.getSignature();
        going_flight = data.getGoing();
        return_flight = data.getReturnFlight();

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

    public ArrayList<SeatSelect> getReturnFlight() {
        return return_flight;
    }

    public void setReturnFlight(ArrayList<SeatSelect> returnFlight) {
        this.return_flight = returnFlight;
    }

    public ArrayList<SeatSelect> getGoing() {
        return going_flight;
    }

    public void setGoing(ArrayList<SeatSelect> going) {
        this.going_flight = going;
    }


}
