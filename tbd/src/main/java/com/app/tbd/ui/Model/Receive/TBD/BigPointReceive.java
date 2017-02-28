package com.app.tbd.ui.Model.Receive.TBD;

/*
 * Created by ImalPasha on 11/6/2015.
 */

 /* Response From API */

public class BigPointReceive {


    private String Status;
    private String Message;
    private String AvailablePts;
    private String AccountStatus;
    private String ReturnValue;
    private String ExpMonth1;
    private String ExpMonth2;
    private String ExpMonth3;
    private String ExpMonth4;
    private String ExpMonth5;
    private String ExpMonth6;
    private String ExpPts1;
    private String ExpPts2;
    private String ExpPts3;
    private String ExpPts4;
    private String ExpPts5;
    private String ExpPts6;


    public String getAvailablePts() {
        return AvailablePts;
    }

    public void setAvailablePts(String availablePts) {
        AvailablePts = availablePts;
    }

    public String getAccountStatus() {
        return AccountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        AccountStatus = accountStatus;
    }

    public String getReturnValue() {
        return ReturnValue;
    }

    public void setReturnValue(String returnValue) {
        ReturnValue = returnValue;
    }

    public String getExpMonth1() {
        return ExpMonth1;
    }

    public void setExpMonth1(String expMonth1) {
        ExpMonth1 = expMonth1;
    }

    public String getExpMonth2() {
        return ExpMonth2;
    }

    public void setExpMonth2(String expMonth2) {
        ExpMonth2 = expMonth2;
    }

    public String getExpMonth3() {
        return ExpMonth3;
    }

    public void setExpMonth3(String expMonth3) {
        ExpMonth3 = expMonth3;
    }

    public String getExpMonth4() {
        return ExpMonth4;
    }

    public void setExpMonth4(String expMonth4) {
        ExpMonth4 = expMonth4;
    }

    public String getExpMonth5() {
        return ExpMonth5;
    }

    public void setExpMonth5(String expMonth5) {
        ExpMonth5 = expMonth5;
    }

    public String getExpMonth6() {
        return ExpMonth6;
    }

    public void setExpMonth6(String expMonth6) {
        ExpMonth6 = expMonth6;
    }

    public String getExpPts1() {
        return ExpPts1;
    }

    public void setExpPts1(String expPts1) {
        ExpPts1 = expPts1;
    }

    public String getExpPts2() {
        return ExpPts2;
    }

    public void setExpPts2(String expPts2) {
        ExpPts2 = expPts2;
    }

    public String getExpPts3() {
        return ExpPts3;
    }

    public void setExpPts3(String expPts3) {
        ExpPts3 = expPts3;
    }

    public String getExpPts4() {
        return ExpPts4;
    }

    public void setExpPts4(String expPts4) {
        ExpPts4 = expPts4;
    }

    public String getExpPts5() {
        return ExpPts5;
    }

    public void setExpPts5(String expPts5) {
        ExpPts5 = expPts5;
    }

    public String getExpPts6() {
        return ExpPts6;
    }

    public void setExpPts6(String expPts6) {
        ExpPts6 = expPts6;
    }


    public BigPointReceive(BigPointReceive returnData) {
        Message = returnData.getMessage();
        Status = returnData.getStatus();

        AvailablePts = returnData.getAvailablePts();
        AccountStatus = returnData.getAccountStatus();

        ExpPts1 = returnData.getExpPts1();
        ExpPts2 = returnData.getExpPts2();
        ExpPts3 = returnData.getExpPts3();
        ExpPts4 = returnData.getExpPts4();
        ExpPts5 = returnData.getExpPts5();
        ExpPts6 = returnData.getExpPts6();

        ExpMonth1 = returnData.getExpMonth1();
        ExpMonth2 = returnData.getExpMonth2();
        ExpMonth3 = returnData.getExpMonth3();
        ExpMonth4 = returnData.getExpMonth4();
        ExpMonth5 = returnData.getExpMonth5();
        ExpMonth6 = returnData.getExpMonth6();

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
