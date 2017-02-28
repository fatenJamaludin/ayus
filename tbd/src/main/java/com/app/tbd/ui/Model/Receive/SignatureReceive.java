package com.app.tbd.ui.Model.Receive;

/*
 * Created by Metech USer
 */

 /* Response From API */

public class SignatureReceive {

    private String Status;
    private String Message;
    private String Signature;

    public SignatureReceive(SignatureReceive data) {
        Message = data.getMessage();
        Status = data.getStatus();
        Signature = data.getSignature();
    }

    public String getSignature() {
        return Signature;
    }

    public void setSignature(String URL) {
        this.Signature = URL;
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
