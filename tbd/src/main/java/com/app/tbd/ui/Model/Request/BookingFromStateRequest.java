package com.app.tbd.ui.Model.Request;

/**
 * Created by Dell on 10/18/2016.
 */

public class BookingFromStateRequest {

    private String UserName;
    private String Token;
    private String Signature;


    public BookingFromStateRequest() {

    }

    public BookingFromStateRequest(BookingFromStateRequest obj) {
        this.UserName = obj.getUserName();
        this.Token = obj.getToken();
        this.Signature = obj.getSignature();
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

}
