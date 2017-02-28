package com.app.tbd.ui.Model.Request;

/**
 * Created by Dell on 9/13/2016.
 */
public class SignatureRequest {

    private String UserName;
    private String Token;

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

    public SignatureRequest(SignatureRequest obj) {
        UserName = obj.getUserName();
        Token = obj.getToken();
    }

    public SignatureRequest() {

    }
}
