package com.app.tbd.ui.Model.Receive;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell on 11/8/2016.
 */

public class AddOnReceiveV2 {

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


    public List<AddOnReceiveV2.Journey> getJourney() {
        return Journey;
    }

    public void setJourney(List<AddOnReceiveV2.Journey> journey) {
        Journey = journey;
    }


    public class Journey {

        private List<Segment> Segment;
        private String FreeBaggage;
        private String FreeMeal;
        private String FreeSeatSelection;

        public List<AddOnReceiveV2.Segment> getSegment() {
            return Segment;
        }

        public void setSegment(List<AddOnReceiveV2.Segment> segment) {
            Segment = segment;
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
    }

    public class Passenger {

        ArrayList<String> Meal;
        private String Seat;

        public String getCompartmentDesignator() {
            return CompartmentDesignator;
        }

        public void setCompartmentDesignator(String compartmentDesignator) {
            CompartmentDesignator = compartmentDesignator;
        }

        private String CompartmentDesignator;
        private String Baggage;

        public ArrayList<String> getMeal() {
            return Meal;
        }

        public void setMeal(ArrayList<String> meal) {
            Meal = meal;
        }

        public String getSeat() {
            return Seat;
        }

        public void setSeat(String seat) {
            Seat = seat;
        }

        public String getBaggage() {
            return Baggage;
        }

        public void setBaggage(String baggage) {
            Baggage = baggage;
        }

    }

    public class Segment {

        List<Passenger> Passenger;

        public String getFreeMeal() {
            return FreeMeal;
        }

        public void setFreeMeal(String freeMeal) {
            FreeMeal = freeMeal;
        }

        String FreeMeal;

        public List<AddOnReceiveV2.Passenger> getPassenger() {
            return Passenger;
        }

        public void setPassenger(List<AddOnReceiveV2.Passenger> passenger) {
            Passenger = passenger;
        }

    }

    public AddOnReceiveV2(AddOnReceiveV2 obj) {
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
