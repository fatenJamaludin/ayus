package com.app.tbd.ui.Model.JSON;

/**
 * Created by Dell on 11/7/2016.
 */

public class PromoTransaction {

    private String departureCode;
    private String arrivalCode;
    private String departureCurrencyCode;
    private String departText;
    private String arrivalText;
    private String flightType;
    private String travellingPeriodFrom;
    private String travellingPeriodTo;
    private String flightCode;

    public String getFlightCode() {
        return flightCode;
    }

    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }

    public String getTravellingPeriodFrom() {
        return travellingPeriodFrom;
    }

    public void setTravellingPeriodFrom(String travellingPeriodFrom) {
        this.travellingPeriodFrom = travellingPeriodFrom;
    }

    public String getTravellingPeriodTo() {
        return travellingPeriodTo;
    }

    public void setTravellingPeriodTo(String travellingPeriodTo) {
        this.travellingPeriodTo = travellingPeriodTo;
    }

    public String getFlightType() {
        return flightType;
    }

    public void setFlightType(String flightType) {
        this.flightType = flightType;
    }

    public String getDepartText() {
        return departText;
    }

    public void setDepartText(String departText) {
        this.departText = departText;
    }

    public String getArrivalText() {
        return arrivalText;
    }

    public void setArrivalText(String arrivalText) {
        this.arrivalText = arrivalText;
    }

    public String getDepartureCurrencyCode() {
        return departureCurrencyCode;
    }

    public void setDepartureCurrencyCode(String departureCurrencyCode) {
        this.departureCurrencyCode = departureCurrencyCode;
    }

    public String getDepartureCode() {
        return departureCode;
    }

    public void setDepartureCode(String departureCode) {
        this.departureCode = departureCode;
    }

    public String getArrivalCode() {
        return arrivalCode;
    }

    public void setArrivalCode(String arrivalCode) {
        this.arrivalCode = arrivalCode;
    }

}
