package com.app.tbd.ui.Model.Receive;

public class MessageStatusReceive {
    private String Status;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public MessageStatusReceive(MessageStatusReceive returnData) {
        Status = returnData.getStatus();
    }

}

