package com.app.tbd.ui.Model.Request;

public class LoginInfoRequest {
    private String email; //UserName
    private String country; //Token
    private String bsid; //Token

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBsid() {
        return bsid;
    }

    public void setBsid(String bsid) {
        this.bsid = bsid;
    }

    public LoginInfoRequest(LoginInfoRequest obj) {
        email = obj.getEmail();
        country = obj.getCountry();
        bsid = obj.getBsid();
    }

    public LoginInfoRequest() {    }
}
