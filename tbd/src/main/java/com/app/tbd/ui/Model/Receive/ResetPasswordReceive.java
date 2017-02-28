package com.app.tbd.ui.Model.Receive;

/**
 * Created by Dell on 9/9/2016.
 */
public class ResetPasswordReceive {

    private String Status;
    private String Message;

    public ResetPasswordReceive(ResetPasswordReceive data) {
        Status = data.getStatus();
        Message = data.getMessage();
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
