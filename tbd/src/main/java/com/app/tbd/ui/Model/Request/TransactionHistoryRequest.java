package com.app.tbd.ui.Model.Request;

/**
 * Created by Dell on 9/13/2016.
 */
public class TransactionHistoryRequest {


    private String UserName;
    private String Token;
    private String StartDate;
    private String EndDate;

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public TransactionHistoryRequest() {

    }

    public TransactionHistoryRequest(TransactionHistoryRequest data) {
        UserName = data.getUserName();
        Token = data.getToken();
        StartDate = data.getStartDate();
        EndDate = data.getEndDate();
    }

}
