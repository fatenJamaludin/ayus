package com.app.tbd.ui.Model.Receive;

/**
 * Created by Dell on 1/26/2016.
 */
public class SelectChangeFlightReceive {

    private String status;
    private String message;
    private SelectChangeFlightReceive obj;


    public SelectChangeFlightReceive getObj() {
        return obj;
    }

    public void setObj(SelectChangeFlightReceive obj) {
        this.obj = obj;
    }

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

    public SelectChangeFlightReceive(){
    }

    public SelectChangeFlightReceive(SelectChangeFlightReceive param_obj){
        this.obj = param_obj;
    }

}
