package com.app.tbd.ui.Realm.Cached;

import io.realm.RealmObject;

/**
 * Created by Dell on 3/7/2016.
 */
public class CachedResult extends RealmObject {

    private String cachedResult;
    private String cachedAPI;

    public String getCachedAPI() {
        return cachedAPI;
    }

    public void setCachedAPI(String cachedAPI) {
        this.cachedAPI = cachedAPI;
    }

    public String getCachedResult() {
        return cachedResult;
    }

    public void setCachedResult(String cachedResult) {
        this.cachedResult = cachedResult;
    }

}
