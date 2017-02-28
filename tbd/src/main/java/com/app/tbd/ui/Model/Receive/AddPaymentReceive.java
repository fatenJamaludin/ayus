package com.app.tbd.ui.Model.Receive;

/**
 * Created by Dell on 10/28/2016.
 */

public class AddPaymentReceive {


    private String Message;
    private String Status;
    private String RecordLocator;
    private String PaymentURL;

    public String getPaymentURL() {
        return PaymentURL;
    }

    public void setPaymentURL(String paymentURL) {
        PaymentURL = paymentURL;
    }

    public String getRecordLocator() {
        return RecordLocator;
    }

    public void setRecordLocator(String recordLocator) {
        RecordLocator = recordLocator;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public AddPaymentReceive() {

    }

    public AddPaymentReceive(AddPaymentReceive obj) {
        Message = obj.getMessage();
        Status = obj.getStatus();
        RecordLocator = obj.getRecordLocator();
        PaymentURL = obj.getPaymentURL();
    }


}
