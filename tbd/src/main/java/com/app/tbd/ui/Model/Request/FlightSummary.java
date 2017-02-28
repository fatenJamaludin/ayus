package com.app.tbd.ui.Model.Request;

/**
 * Created by Dell on 1/6/2016.
 */
public class FlightSummary {

    String signature;
    String deviceType;

    public FlightSummary(){

    }

    public FlightSummary(Signature obj){
        this.signature = obj.getSignature();
        this.deviceType = obj.getDeviceType();

    }
}
