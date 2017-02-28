package com.app.tbd.ui.Model.Request;

/**
 * Created by Dell on 11/4/2015.
 */
public class ForgotPasswordRequest {

    String UserName;

    /*Initiate Class*/
    public ForgotPasswordRequest() {
    }

    public ForgotPasswordRequest(ForgotPasswordRequest data) {
        UserName = data.getUserName();
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

}
