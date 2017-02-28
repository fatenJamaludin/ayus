package com.app.tbd.ui.Model.Receive;

/**
 * Created by Dell on 10/6/2016.
 */

public class SelectFlightReceive {

    private String Status;
    private String Message;
    private String FreeBaggage;
    private String FreeMeal;
    private String FreeSeatSelection;
    private String AdultDOBMin;
    private String AdultDOBMax;
    private String ChildDOBMin;
    private String ChildDOBMax;
    private String InfantDOBMin;
    private String InfantDOBMax;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }


    public SelectFlightReceive(SelectFlightReceive data) {
        Status = data.getStatus();
        Message = data.getMessage();
        FreeBaggage = data.getFreeBaggage();
        FreeMeal = data.getFreeMeal();
        FreeSeatSelection = data.getFreeSeatSelection();

        AdultDOBMin = data.getAdultDOBMin();
        AdultDOBMax = data.getAdultDOBMax();
        ChildDOBMin = data.getChildDOBMin();
        ChildDOBMax = data.getChildDOBMax();
        InfantDOBMin = data.getInfantDOBMin();
        InfantDOBMax = data.getInfantDOBMax();
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getFreeBaggage() {
        return FreeBaggage;
    }

    public void setFreeBaggage(String freeBaggage) {
        FreeBaggage = freeBaggage;
    }

    public String getFreeMeal() {
        return FreeMeal;
    }

    public void setFreeMeal(String freeMeal) {
        FreeMeal = freeMeal;
    }

    public String getFreeSeatSelection() {
        return FreeSeatSelection;
    }

    public void setFreeSeatSelection(String freeSeatSelection) {
        FreeSeatSelection = freeSeatSelection;
    }

    public String getAdultDOBMin() {
        return AdultDOBMin;
    }

    public void setAdultDOBMin(String adultDOBMin) {
        AdultDOBMin = adultDOBMin;
    }

    public String getAdultDOBMax() {
        return AdultDOBMax;
    }

    public void setAdultDOBMax(String adultDOBMax) {
        AdultDOBMax = adultDOBMax;
    }

    public String getChildDOBMin() {
        return ChildDOBMin;
    }

    public void setChildDOBMin(String childDOBMin) {
        ChildDOBMin = childDOBMin;
    }

    public String getChildDOBMax() {
        return ChildDOBMax;
    }

    public void setChildDOBMax(String childDOBMax) {
        ChildDOBMax = childDOBMax;
    }

    public String getInfantDOBMin() {
        return InfantDOBMin;
    }

    public void setInfantDOBMin(String infantDOBMin) {
        InfantDOBMin = infantDOBMin;
    }

    public String getInfantDOBMax() {
        return InfantDOBMax;
    }

    public void setInfantDOBMax(String infantDOBMax) {
        InfantDOBMax = infantDOBMax;
    }

    /*{
      "Status": "OK",
      "Message": "",
      "FreeBaggage": "Y",
      "FreeMeal": "Y",
      "FreeSeatSelection": "Y"
    }*/
}
