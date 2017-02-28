package com.app.tbd.ui.Model.Request;

/**
 * Created by Dell on 8/26/2016.
 */
public class StateRequest {

    private String LanguageCode;
    private String CountryCode;
    private String presenterName;

    public String getPresenterName() {
        return presenterName;
    }

    public void setPresenterName(String presenterName) {
        this.presenterName = presenterName;
    }


    public StateRequest() {
    }

    public StateRequest(StateRequest data) {
        LanguageCode = data.getLanguageCode();
        CountryCode = data.getCountryCode();
        presenterName = data.getPresenterName();
    }

    public String getCountryCode() {
        return CountryCode;
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

}
