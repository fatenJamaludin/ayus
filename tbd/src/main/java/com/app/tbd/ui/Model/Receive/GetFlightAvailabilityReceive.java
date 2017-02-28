package com.app.tbd.ui.Model.Receive;

/**
 * Created by Dell on 1/21/2016.
 */
public class GetFlightAvailabilityReceive {

    private String status;
    private GetFlightAvailabilityReceive obj;


    public GetFlightAvailabilityReceive (GetFlightAvailabilityReceive data) {
        obj = data;
    }

    public GetFlightAvailabilityReceive getObj() {
        return obj;
    }

    public void setObj(GetFlightAvailabilityReceive obj) {
        this.obj = obj;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
