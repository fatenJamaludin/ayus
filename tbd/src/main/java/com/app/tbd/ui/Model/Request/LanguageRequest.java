package com.app.tbd.ui.Model.Request;

public class LanguageRequest {

    /*Local Data Send To Server*/
    String CountryCode;

    /*Initiate Class*/
    public LanguageRequest() {    }

    public LanguageRequest(LanguageRequest data) {
        CountryCode = data.getCountryCode();
    }

    public String getCountryCode() {

        return CountryCode;
    }

    public void setCountryCode(String CountryCode) {

        this.CountryCode = CountryCode;
    }

    /*Response Data From Server*/
    String status;

    public String getStatus() {
        return status;
    }
}


