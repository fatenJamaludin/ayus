package com.app.tbd.ui.Model.Request;

public class LanguageCountryRequest {

    /*Local Data Send To Server*/
    String Data = "GetCountry";

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    /*Initiate Class*/
    public LanguageCountryRequest() {
    }

    public LanguageCountryRequest(LanguageCountryRequest data) {
        Data = data.getData();
    }


}


