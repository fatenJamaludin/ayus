package com.app.tbd.ui.Model.Request;

public class MessageRequest {

    private String token;
    private String languageCode;
    private String countryCode;

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public MessageRequest() {

    }

    public MessageRequest(MessageRequest obj) {
        token = obj.getToken();
        languageCode = obj.getLanguageCode();
        countryCode = obj.getCountryCode();
    }
}
