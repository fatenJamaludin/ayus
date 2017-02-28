package com.app.tbd.ui.Model.Request;

import io.realm.RealmObject;

/**
 * Created by Dell on 2/19/2016.
 */
public class RealmFlightObj extends RealmObject {

    private String pnr;
    private String username;
    private String flightObj;

    public String getFlightObj() {
        return flightObj;
    }

    public void setFlightObj(String flightObj) {
        this.flightObj = flightObj;
    }

    public String getPnr() {
        return pnr;
    }

    public void setPnr(String pnr) {
        this.pnr = pnr;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
