package com.app.tbd.ui.Model.Receive;

/*
 * Created by ImalPasha on 11/6/2015.
 */
public class ForgotPasswordReceive {

    private String Status;
    private String Message;

    public ForgotPasswordReceive(ForgotPasswordReceive data) {
        Status = data.getStatus();
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


    /*public ForgotPasswordReceive(ForgotPasswordReceive param_userObj) {
        this.userObj = param_userObj;
    }

    public ForgotPasswordReceive getUserObj() {
        return userObj;
    }*/


}
