package com.app.tbd.ui.Model.Receive;

import java.util.List;

/**
 * Created by Dell on 3/2/2016.
 */
public class BaggageMealReceive {

    private String Status;
    private String Message;
    private List<Segment> Segment;

    public List<BaggageMealReceive.Segment> getSegment() {
        return Segment;
    }

    public void setSegment(List<BaggageMealReceive.Segment> segment) {
        Segment = segment;
    }

    public class Baggage {

        private String SSRCode;
        private String Description;
        private String Points;
        private String Amount;

        public String getSSRCode() {
            return SSRCode;
        }

        public void setSSRCode(String SSRCode) {
            this.SSRCode = SSRCode;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String description) {
            Description = description;
        }

        public String getPoints() {
            return Points;
        }

        public void setPoints(String points) {
            Points = points;
        }

        public String getAmount() {
            return Amount;
        }

        public void setAmount(String amount) {
            Amount = amount;
        }

    }

    public class Segment {

        private String DepartureDate;
        private String DepartureStation;
        private String ArrivalStation;
        private String CarrierCode;
        private String FlightNumber;
        private List<Baggage> Baggage;
        private List<Meal> Meal;

        public List<BaggageMealReceive.Meal> getMeal() {
            return Meal;
        }

        public void setMeal(List<BaggageMealReceive.Meal> meal) {
            Meal = meal;
        }

        public String getDepartureDate() {
            return DepartureDate;
        }

        public void setDepartureDate(String departureDate) {
            DepartureDate = departureDate;
        }

        public String getDepartureStation() {
            return DepartureStation;
        }

        public void setDepartureStation(String departureStation) {
            DepartureStation = departureStation;
        }

        public String getArrivalStation() {
            return ArrivalStation;
        }

        public void setArrivalStation(String arrivalStation) {
            ArrivalStation = arrivalStation;
        }

        public String getCarrierCode() {
            return CarrierCode;
        }

        public void setCarrierCode(String carrierCode) {
            CarrierCode = carrierCode;
        }

        public String getFlightNumber() {
            return FlightNumber;
        }

        public void setFlightNumber(String flightNumber) {
            FlightNumber = flightNumber;
        }

        public List<BaggageMealReceive.Baggage> getBaggage() {
            return Baggage;
        }

        public void setBaggage(List<BaggageMealReceive.Baggage> baggage) {
            Baggage = baggage;
        }

    }

    public class Meal {

        private String SSRCode;
        private String Description;
        private String Image;
        private String Points;
        private String Amount;

        public String getSSRCode() {
            return SSRCode;
        }

        public void setSSRCode(String SSRCode) {
            this.SSRCode = SSRCode;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String description) {
            Description = description;
        }

        public String getImage() {
            return Image;
        }

        public void setImage(String image) {
            Image = image;
        }

        public String getPoints() {
            return Points;
        }

        public void setPoints(String points) {
            Points = points;
        }

        public String getAmount() {
            return Amount;
        }

        public void setAmount(String amount) {
            Amount = amount;
        }

    }


    private void AddInsuranceReceive() {
    }

    public BaggageMealReceive(BaggageMealReceive obj) {
        Status = obj.getStatus();
        Message = obj.getMessage();
        Segment = obj.getSegment();

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
