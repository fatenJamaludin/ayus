package com.app.tbd.ui.Model.JSON;

import io.realm.RealmObject;

/**
 * Created by Dell on 10/31/2016.
 */

public class RecentSearch extends RealmObject {

    private String recentSearch;

    public String getRecentSearch() {
        return recentSearch;
    }

    public void setRecentSearch(String recentSearch) {
        this.recentSearch = recentSearch;
    }

}
