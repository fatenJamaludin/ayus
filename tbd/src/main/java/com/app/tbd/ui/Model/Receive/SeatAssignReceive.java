package com.app.tbd.ui.Model.Receive;

/**
 * Created by Dell on 10/21/2016.
 */

public class SeatAssignReceive {


    private String Status;
    private String Message;

    public SeatAssignReceive(SeatAssignReceive obj) {

        this.Status = obj.getStatus();
        this.Message = obj.getMessage();

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
