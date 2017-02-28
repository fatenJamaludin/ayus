package com.app.tbd.ui.Model.Receive;

import java.util.List;

/**
 * Created by Dell on 10/22/2016.
 */

public class LoadInsuranceReceive {

    private String Status;
    private String Message;
    private List<PassengerInsurance> Passenger;

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

    public List<PassengerInsurance> getPassenger() {
        return Passenger;
    }

    public void setPassenger(List<PassengerInsurance> passenger) {
        Passenger = passenger;
    }

    public class PassengerInsurance {

        private String FirstName;
        private String LastName;
        private String Points;

        public String getFirstName() {
            return FirstName;
        }

        public void setFirstName(String firstName) {
            FirstName = firstName;
        }

        public String getPoints() {
            return Points;
        }

        public void setPoints(String points) {
            Points = points;
        }

        public String getLastName() {
            return LastName;
        }

        public void setLastName(String lastName) {
            LastName = lastName;
        }


    }

    public LoadInsuranceReceive(LoadInsuranceReceive returnData) {

        Status = returnData.getStatus();
        Message = returnData.getMessage();
        Passenger = returnData.getPassenger();
    }

}
