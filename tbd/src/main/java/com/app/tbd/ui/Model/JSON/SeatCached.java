package com.app.tbd.ui.Model.JSON;

import io.realm.RealmObject;

/**
 * Created by Dell on 10/15/2016.
 */

public class SeatCached extends RealmObject {

    private String seatCached;

    public String getSeatCached() {
        return seatCached;
    }

    public void setSeatCached(String seatCached) {
        this.seatCached = seatCached;
    }

}
