package com.app.tbd.ui.Model.JSON;

import io.realm.RealmObject;

/**
 * Created by Dell on 11/13/2016.
 */

public class SelectedSeatInfoGSON extends RealmObject {

    private String selectedSeat;

    public String getSelectedSeat() {
        return selectedSeat;
    }

    public void setSelectedSeat(String selectedSeat) {
        this.selectedSeat = selectedSeat;
    }

}
