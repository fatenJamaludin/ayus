package com.app.tbd.ui.Model.Request;

/**
 * Created by Dell on 12/30/2015.
 */
public class Signature {

    String username;
    String signature;
    String deviceType;
    String personID;

    public Signature(){

    }

    public Signature(Signature obj){
        this.username = obj.getUsername();
        this.signature = obj.getSignature();
        this.deviceType = obj.getDeviceType();
        this.personID = obj.getPersonID();
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

}
