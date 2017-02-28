package com.app.tbd.ui.Model.JSON;

/**
 * Created by Dell on 10/31/2016.
 */

public class RecentSearchClass {


    private String DepartureCode;
    private String DepartureName;
    private String DepartureCountry;
    private String DepartureCurrency;
    private String Type;

    public String getDepartureCurrency() {
        return DepartureCurrency;
    }

    public void setDepartureCurrency(String departureCurrency) {
        DepartureCurrency = departureCurrency;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getDepartureCode() {
        return DepartureCode;
    }

    public void setDepartureCode(String departureCode) {
        DepartureCode = departureCode;
    }

    public String getDepartureName() {
        return DepartureName;
    }

    public void setDepartureName(String departureName) {
        DepartureName = departureName;
    }

    public String getDepartureCountry() {
        return DepartureCountry;
    }

    public void setDepartureCountry(String departureCountry) {
        DepartureCountry = departureCountry;
    }
}
