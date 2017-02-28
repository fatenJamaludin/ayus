package com.app.tbd.ui.Model.Receive.TBD;

/**
 * Created by Dell on 9/5/2016.
 */
public class LogoutReceive {

    private String Status;
    private String Message;

    public LogoutReceive(LogoutReceive data) {
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

}
