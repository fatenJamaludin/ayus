package com.app.tbd.ui.Model.Request.TBD;

/**
 * Created by Dell on 11/4/2015.
 */
public class LoginRequest {

    /*Local Data Send To Server*/
    String UserName;
    String Password;

    /*Initiate Class*/
    public LoginRequest() {
    }


    public LoginRequest(LoginRequest data) {
        UserName = data.getUsername();
        Password = data.getPassword();
    }

    public String getPassword() {

        return Password;
    }

    public void setPassword(String password) {

        this.Password = password;
    }

    public String getUsername() {

        return UserName;
    }

    public void setUsername(String username) {

        this.UserName = username;
    }


    /*Response Data From Server*/
    String status;

    public String getStatus() {
        return status;
    }


}
