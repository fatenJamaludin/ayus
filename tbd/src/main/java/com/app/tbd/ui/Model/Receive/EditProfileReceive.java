package com.app.tbd.ui.Model.Receive;

public class EditProfileReceive {
    private final EditProfileReceive userObj;
    private String Status;
    private String Message;

    public EditProfileReceive getUserObj() {
        return userObj;
    }

    public EditProfileReceive(EditProfileReceive param_userObj) {
        this.userObj = param_userObj;
        Status = param_userObj.getStatus();
        Message = param_userObj.getMessage();
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        this.Status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}

