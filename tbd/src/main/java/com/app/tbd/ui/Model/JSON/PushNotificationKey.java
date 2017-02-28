package com.app.tbd.ui.Model.JSON;

import io.realm.RealmObject;

/**
 * Created by Dell on 2/23/2017.
 */

public class PushNotificationKey  extends RealmObject {

    private String cachedKey;

    public String getCachedKey() {
        return cachedKey;
    }

    public void setCachedKey(String cachedKey) {
        this.cachedKey = cachedKey;
    }

}
