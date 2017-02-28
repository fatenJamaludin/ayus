package com.app.tbd.ui.Model.Request;

/**
 * Created by Dell on 11/3/2016.
 */

public class OnBoardingRequest {


    private String CountryCode;
    private String LanguageCode;

    public String getLanguageCode() {
        return LanguageCode;
    }

    public void setLanguageCode(String languageCode) {
        LanguageCode = languageCode;
    }


    public String getCountryCode() {
        return CountryCode;
    }


    public void setCountryCode(String countryCode) {
        CountryCode = countryCode;
    }


    /*Initiate Class*/
    public OnBoardingRequest() {

    }

    public OnBoardingRequest(OnBoardingRequest data) {
        CountryCode = data.getCountryCode();
        LanguageCode = data.getLanguageCode();
    }

}
