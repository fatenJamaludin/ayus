package com.app.tbd.ui.Model.Request;

/**
 * Created by Dell on 10/24/2016.
 */

public class LoadBaggageRequest {

    private String UserName;
    private String Token;
    private String Signature;
    private String LanguageCode;

    /*public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }*/

    /*private String Version;*/

    public String getLanguageCode() {
        return LanguageCode;
    }

    public void setLanguageCode(String languageCode) {
        LanguageCode = languageCode;
    }


    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getSignature() {
        return Signature;
    }

    public void setSignature(String signature) {
        Signature = signature;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    /*Initiate Class*/
    public LoadBaggageRequest() {
    }

    public LoadBaggageRequest(LoadBaggageRequest data) {
        UserName = data.getUserName();
        Token = data.getToken();
        Signature = data.getSignature();
        LanguageCode = data.getLanguageCode();
        /*Version = data.getVersion();*/
    }

}
