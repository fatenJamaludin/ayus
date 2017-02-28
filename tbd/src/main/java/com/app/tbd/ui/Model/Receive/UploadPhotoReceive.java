package com.app.tbd.ui.Model.Receive;

/*
 * Created by Metech USer
 */

 /* Response From API */

public class UploadPhotoReceive {

    private String Status;
    private String Message;
    private String URL;

    public UploadPhotoReceive(UploadPhotoReceive data) {
        Message = data.getMessage();
        Status = data.getStatus();
        URL = data.getURL();
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
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
