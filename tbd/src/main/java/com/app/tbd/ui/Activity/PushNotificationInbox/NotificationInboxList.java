package com.app.tbd.ui.Activity.PushNotificationInbox;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Dell on 2/11/2016.
 */
public class NotificationInboxList extends RealmObject {

    @PrimaryKey
    private String id;
    private String messageID;
    private String username;
    private String title;
    private String message;
    private String datetime;
    private String status;
    private String overlayImage;
    private String body;
    private String messageType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getOverlayImage() {
        return overlayImage;
    }

    public void setOverlayImage(String overlayImage) {
        this.overlayImage = overlayImage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
