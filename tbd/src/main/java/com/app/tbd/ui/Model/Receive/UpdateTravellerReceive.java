package com.app.tbd.ui.Model.Receive;

/*
 * Created by Metech USer
 */

 /* Response From API */

public class UpdateTravellerReceive {

    private String Status;
    private String Message;

    public UpdateTravellerReceive(UpdateTravellerReceive data) {
        Message = data.getMessage();
        Status = data.getStatus();
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
