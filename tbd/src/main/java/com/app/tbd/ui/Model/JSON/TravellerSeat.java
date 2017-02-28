package com.app.tbd.ui.Model.JSON;

/**
 * Created by Dell on 10/17/2016.
 */

public class TravellerSeat {

    private String travellerName;

    public String getTravellerNameShortcut() {
        return travellerNameShortcut;
    }

    public void setTravellerNameShortcut(String travellerNameShortcut) {
        this.travellerNameShortcut = travellerNameShortcut;
    }

    private String travellerNameShortcut;

    public String getTravellerSalutation() {
        return travellerSalutation;
    }

    public void setTravellerSalutation(String travellerSalutation) {
        this.travellerSalutation = travellerSalutation;
    }

    private String travellerSalutation;
    private String type;
    private String travellerSeatDesignator;
    private String travellerSeatCompartment;
    private String seatPts;
    private String action;

    public int getSegment() {
        return segment;
    }

    private int segment;

    public void setSegment(int segment) {
        this.segment = segment;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }


    public String getSeatPts() {
        return seatPts;
    }

    public void setSeatPts(String seatPts) {
        this.seatPts = seatPts;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTravellerSeatCompartment() {
        return travellerSeatCompartment;
    }

    public void setTravellerSeatCompartment(String travellerSeatCompartment) {
        this.travellerSeatCompartment = travellerSeatCompartment;
    }

    public String getTravellerName() {
        return travellerName;
    }

    public void setTravellerName(String travellerName) {
        this.travellerName = travellerName;
    }

    public String getTravellerSeatDesignator() {
        return travellerSeatDesignator;
    }

    public void setTravellerSeatDesignator(String travellerSeatDesignator) {
        this.travellerSeatDesignator = travellerSeatDesignator;
    }

}
