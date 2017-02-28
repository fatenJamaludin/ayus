package com.app.tbd.ui.Model.Receive;

/**
 * Created by Dell on 4/19/2016.
 */
public class ChangeSSRReceive {

    private String status;
    private String message;
    private ChangeSSRReceive obj;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ChangeSSRReceive getObj() {
        return obj;
    }

    public void setObj(ChangeSSRReceive obj) {
        this.obj = obj;
    }
    public ChangeSSRReceive(ChangeSSRReceive xx){
        status = xx.getStatus();
        message = xx.getMessage();
        obj = xx;
    }
}
