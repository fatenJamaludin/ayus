package com.app.tbd.ui.Model.Request.TBD;

/**
 * Created by Dell on 11/4/2015.
 */
public class LoginFacebookRequest {

    /*Local Data Send To Server*/
    String Email;
    String Token;

    /*Initiate Class*/
    public LoginFacebookRequest() {
    }


    public LoginFacebookRequest(LoginFacebookRequest data) {
        Email = data.getEmail();
        Token = data.getToken();
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

}
