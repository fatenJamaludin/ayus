package com.app.tbd.ui.Model.Request;

/**
 * Created by Dell on 10/30/2016.
 */

public class PaymentStatusRequest {

    private String UserName;
    private String RecordLocator;
    private String Signature;
    private String Token;

    public PaymentStatusRequest(){

    }

    public PaymentStatusRequest(PaymentStatusRequest obj){
        UserName = obj.getUserName();
        RecordLocator = obj.getRecordLocator();
        Signature = obj.getSignature();
        Token = obj.getToken();
    }
    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getRecordLocator() {
        return RecordLocator;
    }

    public void setRecordLocator(String recordLocator) {
        RecordLocator = recordLocator;
    }

    public String getSignature() {
        return Signature;
    }

    public void setSignature(String signature) {
        Signature = signature;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

}
