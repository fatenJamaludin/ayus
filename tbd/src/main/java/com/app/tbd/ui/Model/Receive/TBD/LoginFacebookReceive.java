package com.app.tbd.ui.Model.Receive.TBD;

/**
 * Created by Dell on 9/2/2016.
 */
public class LoginFacebookReceive {

    private String Status;
    private String Message;

    public LoginFacebookReceive(LoginFacebookReceive returnData) {
        Status = returnData.getStatus();
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
}
