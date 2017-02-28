package com.app.tbd.ui.Model.JSON;

import io.realm.RealmObject;

/**
 * Created by Dell on 8/24/2016.
 */
public class OverlayInfoGSON extends RealmObject {

    private String overlayInfo;

    public String getOverlayInfo() {
        return overlayInfo;
    }

    public void setOverlayInfo(String overlayInfo) {
        this.overlayInfo = overlayInfo;
    }

}
