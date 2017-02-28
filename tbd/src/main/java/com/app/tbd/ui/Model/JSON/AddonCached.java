package com.app.tbd.ui.Model.JSON;

import io.realm.RealmObject;

/**
 * Created by Dell on 10/21/2016.
 */

public class AddonCached extends RealmObject {

    private String addonInfo;

    public String getAddonInfo() {
        return addonInfo;
    }

    public void setAddonInfo(String addonInfo) {
        this.addonInfo = addonInfo;
    }


}
