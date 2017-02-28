package com.app.tbd.ui.Model.Request;

/**
 * Created by Dell on 10/22/2016.
 */

public class AddInsuranceRequest {

    /*Local Data Send To Server*/
    private String Insurance;
    private String Signature;
    private String UserName;
    private String Token;

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getInsurance() {
        return Insurance;
    }

    public void setInsurance(String insurance) {
        Insurance = insurance;
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
    public AddInsuranceRequest() {
    }

    public AddInsuranceRequest(AddInsuranceRequest data) {
        Insurance = data.getInsurance();
        Signature = data.getSignature();
        UserName = data.getUserName();
        Token = data.getToken();
    }
}
