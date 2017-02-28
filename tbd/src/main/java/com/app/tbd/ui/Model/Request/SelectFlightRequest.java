package com.app.tbd.ui.Model.Request;

/**
 * Created by Dell on 8/26/2016.
 */
public class SelectFlightRequest {

    private String CurrencyCode;
    private String Adult;
    private String Child;
    private String Infant;
    private String JourneySellKey0;
    private String FareSellKey0;
    private String JourneySellKey1;
    private String FareSellKey1;
    private String Signature;
    private String UserName;
    private String Token;

    public String getUsername() {
        return UserName;
    }

    public void setUsername(String username) {
        UserName = username;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getJourneySellKey1() {
        return JourneySellKey1;
    }

    public void setJourneySellKey1(String journeySellKey1) {
        JourneySellKey1 = journeySellKey1;
    }

    public String getFareSellKey1() {
        return FareSellKey1;
    }

    public void setFareSellKey1(String fareSellKey1) {
        FareSellKey1 = fareSellKey1;
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

    public String getChild() {
        return Child;
    }

    public void setChild(String child) {
        Child = child;
    }

    public String getInfant() {
        return Infant;
    }

    public void setInfant(String infant) {
        Infant = infant;
    }

    public String getJourneySellKey0() {
        return JourneySellKey0;
    }

    public void setJourneySellKey0(String journeySellKey0) {
        JourneySellKey0 = journeySellKey0;
    }

    public String getFareSellKey0() {
        return FareSellKey0;
    }

    public void setFareSellKey0(String fareSellKey0) {
        FareSellKey0 = fareSellKey0;
    }

    public String getSignature() {
        return Signature;
    }

    public void setSignature(String signature) {
        Signature = signature;
    }


    public SelectFlightRequest() {
    }

    public SelectFlightRequest(SelectFlightRequest data) {
        CurrencyCode = data.getCurrencyCode();
        Adult = data.getAdult();
        Child = data.getChild();
        Infant = data.getInfant();
        JourneySellKey0 = data.getJourneySellKey0();
        FareSellKey0 = data.getFareSellKey0();
        JourneySellKey1 = data.getJourneySellKey1();
        FareSellKey1 = data.getFareSellKey1();
        Signature = data.getSignature();
        UserName = data.getUsername();
        Token = data.getToken();
    }


}
