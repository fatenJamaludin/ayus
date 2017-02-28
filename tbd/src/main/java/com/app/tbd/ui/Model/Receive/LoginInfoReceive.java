package com.app.tbd.ui.Model.Receive;

public class LoginInfoReceive {

    private String Status;

    public LoginInfoReceive(LoginInfoReceive data) {
        Status = data.getStatus();
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
