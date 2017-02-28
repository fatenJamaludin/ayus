package com.app.tbd.ui.Model.Request.TBD;

/**
 * Created by Dell on 11/4/2015.
 */
public class LogoutRequest {

    /*Local Data Send To Server*/
    String UserName;
    String Token;

    /*Initiate Class*/
    public LogoutRequest() {
    }

    public LogoutRequest(LogoutRequest data) {
        UserName = data.getUsername();
        Token = data.getToken();
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getUsername() {
        return UserName;
    }

    public void setUsername(String username) {
        this.UserName = username;
    }

}
