package com.app.tbd.ui.Model.JSON;

import io.realm.RealmObject;

public class FreeItem extends RealmObject {

    private String FreeInfo;

    public String getFreeInfo() {
        return FreeInfo;
    }

    public void setFreeInfo(String FreeInfo) {
        this.FreeInfo = FreeInfo;
    }

}
