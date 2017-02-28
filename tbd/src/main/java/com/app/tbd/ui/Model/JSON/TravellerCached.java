package com.app.tbd.ui.Model.JSON;

import io.realm.RealmObject;

/**
 * Created by Dell on 10/17/2016.
 */

public class TravellerCached extends RealmObject {

    private String traveller;

    public String getTraveller() {
        return traveller;
    }

    public void setTraveller(String traveller) {
        this.traveller = traveller;
    }

}
