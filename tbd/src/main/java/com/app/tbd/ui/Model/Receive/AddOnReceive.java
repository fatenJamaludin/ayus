package com.app.tbd.ui.Model.Receive;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell on 11/8/2016.
 */

public class AddOnReceive {

    private String Status;
    private String Message;
    private String Insurance;
    private String FreeBaggage;
    private String FreeMeal;
    private String FreeSeatSelection;

    private String BaggageTotalWeight;
    private String BaggageTotalPoints;
    private String MealTotalPoints;
    private String SeatSelectionTotalPoints;
    private String InsuranceTotalPoints;


    public String getBaggageTotalWeight() {
        return BaggageTotalWeight;
    }

    public void setBaggageTotalWeight(String baggageTotalWeight) {
        BaggageTotalWeight = baggageTotalWeight;
    }

    public String getBaggageTotalPoints() {
        return BaggageTotalPoints;
    }

    public void setBaggageTotalPoints(String baggageTotalPoints) {
        BaggageTotalPoints = baggageTotalPoints;
    }

    public String getMealTotalPoints() {
        return MealTotalPoints;
    }

    public void setMealTotalPoints(String mealTotalPoints) {
        MealTotalPoints = mealTotalPoints;
    }

    public String getSeatSelectionTotalPoints() {
        return SeatSelectionTotalPoints;
    }

    public void setSeatSelectionTotalPoints(String seatSelectionTotalPoints) {
        SeatSelectionTotalPoints = seatSelectionTotalPoints;
    }

    public String getInsuranceTotalPoints() {
        return InsuranceTotalPoints;
    }

    public void setInsuranceTotalPoints(String insuranceTotalPoints) {
        InsuranceTotalPoints = insuranceTotalPoints;
    }

    private List<Journey> Journey;

    public String getFreeSeatSelection() {
        return FreeSeatSelection;
    }

    public void setFreeSeatSelection(String freeSeatSelection) {
        FreeSeatSelection = freeSeatSelection;
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

    public String getInsurance() {
        return Insurance;
    }

    public void setInsurance(String insurance) {
        Insurance = insurance;
    }


    public List<AddOnReceive.Journey> getJourney() {
        return Journey;
    }

    public void setJourney(List<AddOnReceive.Journey> journey) {
        Journey = journey;
    }


    public class Journey {
        public List<AddOnReceive.Passenger> getPassenger() {
            return Passenger;
        }

        public void setPassenger(List<AddOnReceive.Passenger> passenger) {
            Passenger = passenger;
        }

        private List<Passenger> Passenger;
    }

    public class Passenger {

        public String getBaggage() {
            return Baggage;
        }

        public void setBaggage(String baggage) {
            Baggage = baggage;
        }

        public List<AddOnReceive.Segment> getSegment() {
            return Segment;
        }

        public void setSegment(List<AddOnReceive.Segment> segment) {
            Segment = segment;
        }

        private String Baggage;
        private List<Segment> Segment;
    }

    public class Segment {


        public ArrayList<String> getMeal() {
            return Meal;
        }

        public void setMeal(ArrayList<String> meal) {
            Meal = meal;
        }

        private ArrayList<String> Meal;
        private String Seat;

        public String getSeat() {
            return Seat;
        }

        public void setSeat(String seat) {
            Seat = seat;
        }

    }

    public class Meal {

    }

    public AddOnReceive(AddOnReceive obj) {
        this.Status = obj.getStatus();
        this.Message = obj.getMessage();
        this.Journey = obj.getJourney();
        this.Insurance = obj.getInsurance();

        this.FreeBaggage = obj.getFreeBaggage();
        this.FreeMeal = obj.getFreeMeal();
        this.FreeSeatSelection = obj.getFreeSeatSelection();

        this.BaggageTotalPoints = obj.getBaggageTotalPoints();
        this.BaggageTotalWeight = obj.getBaggageTotalWeight();
        this.MealTotalPoints = obj.getMealTotalPoints();
        this.SeatSelectionTotalPoints = obj.getSeatSelectionTotalPoints();
        this.InsuranceTotalPoints = obj.getInsuranceTotalPoints();

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


}
