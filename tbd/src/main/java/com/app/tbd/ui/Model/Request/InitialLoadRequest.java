package com.app.tbd.ui.Model.Request;

/**
 * Created by Dell on 11/9/2015.
 */
public class InitialLoadRequest {

    private String LanguageCode;
    private String UserName;
    private String FcmKey;
    private String DeviceType;
    private String CustomerNumber;
    /*private String CounLangCode;*/

    /*public String getCounLangCode() {
        return CounLangCode;
    }

    public void setCounLangCode(String counLangCode) {
        CounLangCode = counLangCode;
    }*/

    public String getUsername() {
        return UserName;
    }

    public void setUsername(String username) {
        UserName = username;
    }

    public String getFcmKey() {
        return FcmKey;
    }

    public void setFcmKey(String fcmKey) {
        FcmKey = fcmKey;
    }

    public String getDeviceType() {
        return DeviceType;
    }

    public void setDeviceType(String deviceType) {
        DeviceType = deviceType;
    }

    public String getCustomerNumber() {
        return CustomerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        CustomerNumber = customerNumber;
    }

    public String getLanguageCode() {
        return LanguageCode;
    }

    public void setLanguageCode(String languageCode) {
        LanguageCode = languageCode;
    }

    /*Initiate Class*/
    public InitialLoadRequest() {

    }

    /*Initiate Class With Data*/
    public InitialLoadRequest(InitialLoadRequest info) {
        LanguageCode = info.getLanguageCode();
        UserName = info.getUsername();
        FcmKey = info.getFcmKey();
        DeviceType = info.getDeviceType();
        CustomerNumber = info.getCustomerNumber();
        /*CounLangCode = info.getCounLangCode();*/
    }




}
