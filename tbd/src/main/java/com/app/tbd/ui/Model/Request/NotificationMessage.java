package com.app.tbd.ui.Model.Request;

import io.realm.RealmObject;

/**
 * Created by Dell on 5/10/2016.
 */
public class NotificationMessage extends RealmObject {

    private String message;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
