package com.app.tbd.ui.Model.Request;

/**
 * Created by Dell on 11/8/2016.
 */

public class AddOnRequest {

    private String Token;
    private String UserName;
    private String Signature;

    public AddOnRequest() {

    }

    public AddOnRequest(AddOnRequest obj) {
        this.Token = obj.getToken();
        this.UserName = obj.getUserName();
        this.Signature = obj.getSignature();
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getSignature() {
        return Signature;
    }

    public void setSignature(String signature) {
        Signature = signature;
    }

}


