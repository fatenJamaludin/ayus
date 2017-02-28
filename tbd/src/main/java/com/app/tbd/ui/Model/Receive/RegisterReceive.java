package com.app.tbd.ui.Model.Receive;

/*
 * Created by ImalPasha on 11/6/2015.
 */

 /* Response From API */

public class RegisterReceive {

    private final RegisterReceive thisObj;
    private String Status;
    private String Message;

    public RegisterReceive(RegisterReceive return_data) {
        this.thisObj = return_data;
        Status = return_data.getStatus();
        Message = return_data.getMessage();
    }

    public RegisterReceive getUserObj() {
        return thisObj;
    }

    public String getStatus() {
        return Status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        this.Message = message;
    }

    public void setStatus(String status) {
        this.Status = status;
    }

}
