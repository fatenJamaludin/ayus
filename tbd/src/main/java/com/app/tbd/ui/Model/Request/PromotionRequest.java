package com.app.tbd.ui.Model.Request;

/**
 * Created by Dell on 11/3/2016.
 */

public class PromotionRequest {

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

    public PromotionRequest() {

    }

    public PromotionRequest(PromotionRequest obj) {
        CountryCode = obj.getCountryCode();
        LanguageCode = obj.getLanguageCode();
    }

}
