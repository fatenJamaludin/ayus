package com.app.tbd.ui.Model.JSON;

import io.realm.RealmObject;

/**
 * Created by User on 10/31/2016.
 */

public class countryLanguageJSON extends RealmObject {
    private String countryLanguageReceive;

    public String getCountryLanguageReceive() {
        return countryLanguageReceive;
    }

    public void setCountryLanguageReceive(String countryLanguageReceive) {
        this.countryLanguageReceive = countryLanguageReceive;
    }

}