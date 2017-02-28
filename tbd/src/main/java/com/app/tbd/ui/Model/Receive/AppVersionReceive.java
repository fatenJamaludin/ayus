package com.app.tbd.ui.Model.Receive;

/**
 * Created by Dell on 11/27/2016.
 */

public class AppVersionReceive {

    private String Status;
    private String Message;
    private String Update;

    public String getUpdate() {
        return Update;
    }

    public void setUpdate(String update) {
        Update = update;
    }


    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }


    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public AppVersionReceive(AppVersionReceive appVersionReceive) {
        Status = appVersionReceive.getStatus();
        Message = appVersionReceive.getMessage();
        Update = appVersionReceive.getUpdate();
    }

}
