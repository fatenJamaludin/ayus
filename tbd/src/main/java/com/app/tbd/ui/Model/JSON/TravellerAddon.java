package com.app.tbd.ui.Model.JSON;

import java.util.ArrayList;

/**
 * Created by Dell on 10/17/2016.
 */

public class TravellerAddon {

    private String travellerName;
    private ArrayList<String> ssrListPerPassenger;
    private String ssrCode;

    public String getBaggageCode() {
        return BaggageCode;
    }

    public void setBaggageCode(String baggageCode) {
        BaggageCode = baggageCode;
    }

    private String BaggageCode;
    private int segment;

    public ArrayList<String> getSsrListPerPassenger() {
        return ssrListPerPassenger;
    }

    public void setSsrListPerPassenger(ArrayList<String> ssrListPerPassenger) {
        this.ssrListPerPassenger = ssrListPerPassenger;
    }

    public String getTravellerName() {
        return travellerName;
    }

    public void setTravellerName(String travellerName) {
        this.travellerName = travellerName;
    }

    public String getSsrCode() {
        return ssrCode;
    }

    public void setSsrCode(String ssrCode) {
        this.ssrCode = ssrCode;
    }

    public int getSegment() {
        return segment;
    }

    public void setSegment(int segment) {
        this.segment = segment;
    }

}
