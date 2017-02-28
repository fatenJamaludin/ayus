package com.app.tbd.ui.Model.JSON;

import java.util.List;

/**
 * Created by Dell on 10/21/2016.
 */

public class AddOnInfo {

    private List<MealList> MealList;
    private List<SeatList> SeatList;
    private List<TravelInsuranceList> TravelInsuranceList;
    private String seatSelected;
    private String insuranceIncluded;
    private String setMealSelected;
    private String setBaggageSelected;
    private String BaggageWeight;
    private String BaggagePoint;

    public class MealList{

        private String MealName;
        private String MealPoint;
        private String MealPassengerName;
        private String Flight;

        public String getMealName() {
            return MealName;
        }

        public void setMealName(String mealName) {
            MealName = mealName;
        }

        public String getMealPoint() {
            return MealPoint;
        }

        public void setMealPoint(String mealPoint) {
            MealPoint = mealPoint;
        }

        public String getMealPassengerName() {
            return MealPassengerName;
        }

        public void setMealPassengerName(String mealPassengerName) {
            MealPassengerName = mealPassengerName;
        }

        public String getFlight() {
            return Flight;
        }

        public void setFlight(String flight) {
            Flight = flight;
        }

    }

    public class SeatList{

        private String SeatNumber;
        private String SeatPoint;
        private String SeatPassengerName;

        public String getSeatNumber() {
            return SeatNumber;
        }

        public void setSeatNumber(String seatNumber) {
            SeatNumber = seatNumber;
        }

        public String getSeatPoint() {
            return SeatPoint;
        }

        public void setSeatPoint(String seatPoint) {
            SeatPoint = seatPoint;
        }

        public String getSeatPassengerName() {
            return SeatPassengerName;
        }

        public void setSeatPassengerName(String seatPassengerName) {
            SeatPassengerName = seatPassengerName;
        }
    }

    public class TravelInsuranceList{

        private String TravelInsurancePassengerName;
        private String TravelInsurancePoint;
        private String TravelInsuranceTotal;

        public String getTravelInsurancePassengerName() {
            return TravelInsurancePassengerName;
        }

        public void setTravelInsurancePassengerName(String travelInsurancePassengerName) {
            TravelInsurancePassengerName = travelInsurancePassengerName;
        }

        public String getTravelInsurancePoint() {
            return TravelInsurancePoint;
        }

        public void setTravelInsurancePoint(String travelInsurancePoint) {
            TravelInsurancePoint = travelInsurancePoint;
        }

        public String getTravelInsuranceTotal() {
            return TravelInsuranceTotal;
        }

        public void setTravelInsuranceTotal(String travelInsuranceTotal) {
            TravelInsuranceTotal = travelInsuranceTotal;
        }
    }

    public String getBaggageWeight() {
        return BaggageWeight;
    }

    public void setBaggageWeight(String baggageWeight) {
        BaggageWeight = baggageWeight;
    }

    public String getBaggagePoint() {
        return BaggagePoint;
    }

    public void setBaggagePoint(String baggagePoint) {
        BaggagePoint = baggagePoint;
    }

    public List<AddOnInfo.MealList> getMealList() {
        return MealList;
    }

    public void setMealList(List<AddOnInfo.MealList> mealList) {
        MealList = mealList;
    }

    public List<AddOnInfo.SeatList> getSeatList() {
        return SeatList;
    }

    public void setSeatList(List<AddOnInfo.SeatList> seatList) {
        SeatList = seatList;
    }

    public List<AddOnInfo.TravelInsuranceList> getTravelInsuranceList() {
        return TravelInsuranceList;
    }

    public void setTravelInsuranceList(List<AddOnInfo.TravelInsuranceList> travelInsuranceList) {
        TravelInsuranceList = travelInsuranceList;
    }

    public String getSetBaggageSelected() {
        return setBaggageSelected;
    }

    public void setSetBaggageSelected(String setBaggageSelected) {
        this.setBaggageSelected = setBaggageSelected;
    }

    public String getSetMealSelected() {
        return setMealSelected;
    }

    public void setSetMealSelected(String setMealSelected) {
        this.setMealSelected = setMealSelected;
    }

    public String getInsuranceIncluded() {
        return insuranceIncluded;
    }

    public void setInsuranceIncluded(String insuranceIncluded) {
        this.insuranceIncluded = insuranceIncluded;
    }

    public String getSeatSelected() {
        return seatSelected;
    }

    public void setSeatSelected(String seatSelected) {
        this.seatSelected = seatSelected;
    }


}
