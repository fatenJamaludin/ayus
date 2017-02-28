package com.app.tbd.ui.Model.Receive;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell on 1/25/2016.
 */
public class ChangeSearchFlightReceive {

    private String status;
    private String message;
    private List<JourneyInfo> journeys = new ArrayList<JourneyInfo>();
    private ChangeSearchFlightReceive journeyObj;

    public ChangeSearchFlightReceive(){
    }

    public ChangeSearchFlightReceive(ChangeSearchFlightReceive param_obj){
        this.journeyObj = param_obj;
        status = param_obj.getStatus();
        message = param_obj.getMessage();
        journeys = param_obj.getJourneys();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<JourneyInfo> getJourneys() {
        return journeys;
    }

    public void setJourneys(ArrayList journeys) {
        this.journeys = journeys;
    }

    public ChangeSearchFlightReceive getJourneyObj() {
        return journeyObj;
    }

    public void setJourneyObj(ChangeSearchFlightReceive journeyObj) {
        this.journeyObj = journeyObj;
    }

    public String getMessage() {return message;}

    public void setMessage(String message) {this.message = message;}
}
