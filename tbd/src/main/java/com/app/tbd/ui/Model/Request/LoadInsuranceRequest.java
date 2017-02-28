package com.app.tbd.ui.Model.Request;

public class LoadInsuranceRequest {

    /*Local Data Send To Server*/
    String UserName;
    String Token;
    String Signature;

    /*Initiate Class*/
    public LoadInsuranceRequest() {
    }

    public LoadInsuranceRequest(LoadInsuranceRequest data) {
        UserName = data.getUserName();
        Token = data.getToken();
        Signature = data.getSignature();
    }

    /*Response Data From Server*/
    String status;

    public String getStatus() {
        return status;
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


