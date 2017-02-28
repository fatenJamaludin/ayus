package com.app.tbd.ui.Model.Receive;

/**
 * Created by Dell on 3/2/2016.
 */
public class AddInsuranceReceive {

    private String Status;
    private String Message;

    private void AddInsuranceReceive(){}

    public  AddInsuranceReceive(AddInsuranceReceive obj){
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
