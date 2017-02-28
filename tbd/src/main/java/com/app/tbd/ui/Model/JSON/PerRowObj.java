package com.app.tbd.ui.Model.JSON;

/**
 * Created by Dell on 10/16/2016.
 */

public class PerRowObj {


    private Boolean flightType;
    private String seatDesignator;
    private String seatType;
    private String seatAvailability;
    private String x;
    private String y;
    private String seatGroup;
    private int selectedBy;
    private String compartment;
    private String selectedByName;

    public Boolean getFlightType() {
        return flightType;
    }

    public void setFlightType(Boolean flightType) {
        this.flightType = flightType;
    }

    public String getSeatAvailability() {
        return seatAvailability;
    }

    public void setSeatAvailability(String seatAvailability) {
        this.seatAvailability = seatAvailability;
    }

    public String getSelectedByName() {
        return selectedByName;
    }

    public void setSelectedByName(String selectedByName) {
        this.selectedByName = selectedByName;
    }

    public String getCompartment() {
        return compartment;
    }

    public void setCompartment(String compartment) {
        this.compartment = compartment;
    }

    public int getSelectedBy() {
        return selectedBy;
    }

    public void setSelectedBy(int selectedBy) {
        this.selectedBy = selectedBy;
    }

    public String getSeatGroup() {
        return seatGroup;
    }

    public void setSeatGroup(String seatGroup) {
        this.seatGroup = seatGroup;
    }


    public String getRowID() {
        return rowID;
    }

    public void setRowID(String rowID) {
        this.rowID = rowID;
    }

    private String rowID;

    public PerRowObj(){

    }
    public String getSeatDesignator() {
        return seatDesignator;
    }

    public void setSeatDesignator(String seatDesignator) {
        this.seatDesignator = seatDesignator;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }



}
