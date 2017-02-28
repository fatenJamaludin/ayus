package com.app.tbd.ui.Model.Request;

/**
 * Created by Dell on 9/2/2016.
 */
public class NewsletterLanguageRequest {

    private String CountryCode;

    public String getCountryCode() {
        return CountryCode;
    }

    public void setCountryCode(String countryCode) {
        CountryCode = countryCode;
    }

    /*Initiate Class*/
    public NewsletterLanguageRequest() {
    }

    public NewsletterLanguageRequest(NewsletterLanguageRequest data) {
        CountryCode = data.getCountryCode();
    }

}
