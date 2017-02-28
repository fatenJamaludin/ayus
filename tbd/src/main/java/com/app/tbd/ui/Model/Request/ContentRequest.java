package com.app.tbd.ui.Model.Request;

/**
 * Created by Dell on 11/3/2016.
 */

public class ContentRequest {

    private String CountryCode;

    public String getCountryCode() {
        return CountryCode;
    }

    public ContentRequest(){

    }

    public ContentRequest(ContentRequest obj) {
        CountryCode = obj.getCountryCode();
        ContentName = obj.getContentName();
        LanguageCode = obj.getLanguageCode();
    }

    public void setCountryCode(String countryCode) {
        CountryCode = countryCode;
    }

    public String getLanguageCode() {
        return LanguageCode;
    }

    public void setLanguageCode(String languageCode) {
        LanguageCode = languageCode;
    }

    public String getContentName() {
        return ContentName;
    }

    public void setContentName(String contentName) {
        ContentName = contentName;
    }

    private String LanguageCode;
    private String ContentName;

}
