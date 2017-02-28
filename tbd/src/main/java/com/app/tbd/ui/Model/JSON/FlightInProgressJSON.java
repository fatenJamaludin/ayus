package com.app.tbd.ui.Model.JSON;

import io.realm.RealmObject;

/**
 * Created by Dell on 10/14/2016.
 */

public class FlightInProgressJSON extends RealmObject {


    private String searchFlightRequest;
    private String searchFlightReceive;
    private String selectReceive;
    private String selectedSegment;
    private String total;

    public String getSelectReceive() {
        return selectReceive;
    }

    public void setSelectReceive(String selectReceive) {
        this.selectReceive = selectReceive;
    }

    public String getSearchFlightRequest() {
        return searchFlightRequest;
    }

    public void setSearchFlightRequest(String searchFlightRequest) {
        this.searchFlightRequest = searchFlightRequest;
    }

    public String getSearchFlightReceive() {
        return searchFlightReceive;
    }

    public void setSearchFlightReceive(String searchFlightReceive) {
        this.searchFlightReceive = searchFlightReceive;
    }

    public String getSelectedSegment() {
        return selectedSegment;
    }

    public void setSelectedSegment(String selectedSegment) {
        this.selectedSegment = selectedSegment;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

}
