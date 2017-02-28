package com.app.tbd.ui.Model.Request;

/**
 * Created by Dell on 10/13/2016.
 */

public class SeatInfoRequest {

    private String Segment;
    private String UserName;
    private String Token;
    private String Signature;

    public String getFlight() {
        return Segment;
    }

    public void setFlight(String flight) {
        Segment = flight;
    }

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

    public String getSignature() {
        return Signature;
    }

    public void setSignature(String signature) {
        Signature = signature;
    }

    public SeatInfoRequest() {

    }

    public SeatInfoRequest(SeatInfoRequest data) {
        UserName = data.getUserName();
        Token = data.getToken();
        Segment = data.getFlight();
        Signature = data.getSignature();
    }
}
