package com.app.tbd.ui.Model.Request.TBD;

/**
 * Created by Dell on 11/4/2015.
 */
public class BigPointRequest {

    String UserName;
    String Token;

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String ticketId) {
        Token = ticketId;
    }

    /*Initiate Class*/
    public BigPointRequest() {
    }

    public BigPointRequest(BigPointRequest data) {
        UserName = data.getUserName();
        Token = data.getToken();
    }

}
