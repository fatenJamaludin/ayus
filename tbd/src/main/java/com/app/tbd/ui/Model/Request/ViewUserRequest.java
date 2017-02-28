package com.app.tbd.ui.Model.Request;

public class ViewUserRequest {

    String UserName;
    String Password;
    String Token;

    public ViewUserRequest() { }

    public ViewUserRequest(ViewUserRequest data) {
        UserName = data.getUserName();
        Password = data.getPassword();
        Token = data.getToken();
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }


    /*Response Data From Server*/
    String status;

    public String getStatus() {
        return status;
    }
}
