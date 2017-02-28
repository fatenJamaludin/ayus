package com.app.tbd.ui.Model.Request;

/**
 * Created by Dell on 9/13/2016.
 */
public class SearchFlightRequest {

    private String CurrencyCode;
    private String Adult;
    private String Infant;
    private String Child;
    private String DepartureStation0;
    private String ArrivalStation0;
    private String DepartureDate0;
    private String DepartureStation1;
    private String ArrivalStation1;
    private String DepartureDate1;
    private String UserName;
    private String Token;
    private String Signature;

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getDepartureStation1() {
        return DepartureStation1;
    }

    public void setDepartureStation1(String departureStation1) {
        DepartureStation1 = departureStation1;
    }

    public String getDepartureDate1() {
        return DepartureDate1;
    }

    public void setDepartureDate1(String departureDate1) {
        DepartureDate1 = departureDate1;
    }

    public String getArrivalStation1() {
        return ArrivalStation1;
    }

    public void setArrivalStation1(String arrivalStation1) {
        ArrivalStation1 = arrivalStation1;
    }

    public SearchFlightRequest() {

    }

    public SearchFlightRequest(SearchFlightRequest data) {
        CurrencyCode = data.getCurrencyCode();
        Adult = data.getAdult();
        Infant = data.getInfant();
        Child = data.getChild();
        DepartureStation0 = data.getDepartureStation0();
        ArrivalStation0 = data.getArrivalStation0();
        DepartureDate0 = data.getDepartureDate0();
        DepartureStation1 = data.getDepartureStation1();
        ArrivalStation1 = data.getArrivalStation1();
        DepartureDate1 = data.getDepartureDate1();
        Signature = data.getSignature();
        UserName = data.getUserName();
        Token = data.getToken();
    }

    public String getCurrencyCode() {
        return CurrencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        CurrencyCode = currencyCode;
    }

    public String getAdult() {
        return Adult;
    }

    public void setAdult(String adult) {
        Adult = adult;
    }

    public String getInfant() {
        return Infant;
    }

    public void setInfant(String infant) {
        Infant = infant;
    }

    public String getChild() {
        return Child;
    }

    public void setChild(String child) {
        Child = child;
    }

    public String getDepartureStation0() {
        return DepartureStation0;
    }

    public void setDepartureStation0(String departureStation0) {
        DepartureStation0 = departureStation0;
    }

    public String getArrivalStation0() {
        return ArrivalStation0;
    }

    public void setArrivalStation0(String arrivalStation0) {
        ArrivalStation0 = arrivalStation0;
    }

    public String getDepartureDate0() {
        return DepartureDate0;
    }

    public void setDepartureDate0(String departureDate0) {
        DepartureDate0 = departureDate0;
    }

    public String getSignature() {
        return Signature;
    }

    public void setSignature(String signature) {
        Signature = signature;
    }


}
