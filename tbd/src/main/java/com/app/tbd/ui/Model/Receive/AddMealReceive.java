package com.app.tbd.ui.Model.Receive;

/**
 * Created by Dell on 3/2/2016.
 */
public class AddMealReceive {

    private String Status;
    private String Message;

    private void AddMealReceive() {
    }

    public AddMealReceive(AddMealReceive obj) {
        Status = obj.getStatus();
        Message = obj.getMessage();

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
