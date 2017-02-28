package com.app.tbd.ui.Model.Request;

/**
 * Created by Dell on 10/22/2016.
 */

public class AddMealRequest {

    /*Local Data Send To Server*/
    private String Signature;
    private String UserName;
    private String Token;

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

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    /*Initiate Class*/
    public AddMealRequest() {
    }

    public AddMealRequest(AddMealRequest data) {
        Signature = data.getSignature();
        UserName = data.getUserName();
        Token = data.getToken();
    }
}
