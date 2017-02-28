package com.app.tbd.ui.Model.Request;

public class AddPassengerRequest {

    /*Local Data Send To Server*/
    String AdultTitle;

    /*Initiate Class*/
    public AddPassengerRequest() {    }

    public AddPassengerRequest(LanguageRequest data) {
        AdultTitle = data.getCountryCode();
    }

    public String getCountryCode() {

        return AdultTitle;
    }

    public void setCountryCode(String CountryCode) {

        this.AdultTitle = CountryCode;
    }

}


