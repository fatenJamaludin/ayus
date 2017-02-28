package com.app.tbd.ui.Model.Receive;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell on 11/23/2015.
 */
public class SearchFlightReceive {

    private String Status;
    private String Message;
    private List<JourneyDateMarket> JourneyDateMarket = new ArrayList<JourneyDateMarket>();

    public class JourneyDateMarket {

        private String DepartureDate;
        private String DepartureStation;
        private String ArrivalStation;
        private List<Journey> Journey = new ArrayList<Journey>();

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

        public List<SearchFlightReceive.Journey> getJourney() {
            return Journey;
        }

        public void setJourney(List<SearchFlightReceive.Journey> journey) {
            Journey = journey;
        }

    }

    public class Journey {

        private String Selected = "N";
        private String JourneySellKey;
        private String CurrentListType;
        private String SelectedFareType;
        //private String CurrentFareType;
        private List<Segment> Segment = new ArrayList<Segment>();
        private List<Fare> Fare = new ArrayList<Fare>();

        public String getCurrentListType() {
            return CurrentListType;
        }

        public void setCurrentListType(String currentListType) {
            CurrentListType = currentListType;
        }


        /*public String getCurrentFareType() {
            return CurrentFareType;
        }

        public void setCurrentFareType(String currentFareType) {
            CurrentFareType = currentFareType;
        }*/

        public String getSelectedFareType() {
            return SelectedFareType;
        }

        public void setSelectedFareType(String fareType) {
            SelectedFareType = fareType;
        }

        public List<SearchFlightReceive.Fare> getFare() {
            return Fare;
        }

        public void setFare(List<SearchFlightReceive.Fare> fare) {
            Fare = fare;
        }

        public String getJourneySellKey() {
            return JourneySellKey;
        }

        public String getSelected() {
            return Selected;
        }

        public void setSelected(String selected) {
            Selected = selected;
        }

        public void setJourneySellKey(String journeySellKey) {
            JourneySellKey = journeySellKey;
        }

        public List<SearchFlightReceive.Segment> getSegment() {
            return Segment;
        }

        public void setSegment(List<SearchFlightReceive.Segment> segment) {
            Segment = segment;
        }

    }

    public class Segment {

        private String DepartureStation;
        private String ArrivalStation;
        private String STD;
        private String STA;
        private String CarrierCode;
        private String FlightNumber;
        private String TravelTime;

        public String getTravelTime() {
            return TravelTime;
        }

        public void setTravelTime(String travelTime) {
            TravelTime = travelTime;
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

        public String getSTD() {
            return STD;
        }

        public void setSTD(String STD) {
            this.STD = STD;
        }

        public String getSTA() {
            return STA;
        }

        public void setSTA(String STA) {
            this.STA = STA;
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

    }

    public class Fare {

        private String Type;
        private String CurrencyCode;
        private String AdultQuotedPoints;
        private String AdultQuotedAmount;
        private String ChildQuotedPoints;
        private String ChildQuotedAmount;
        private String InfantQuotedPoints;
        private String InfantQuotedAmount;
        private String FareSellKey;
        private String AvailableCount;
        private String TotalQuotedPoints;

        public String getTotalQuotedPoints() {
            return TotalQuotedPoints;
        }

        public void setTotalQuotedPoints(String totalQuotedPoints) {
            TotalQuotedPoints = totalQuotedPoints;
        }

        public String getType() {
            return Type;
        }

        public void setType(String type) {
            Type = type;
        }

        public String getCurrencyCode() {
            return CurrencyCode;
        }

        public void setCurrencyCode(String currencyCode) {
            CurrencyCode = currencyCode;
        }

        public String getAdultQuotedPoints() {
            return AdultQuotedPoints;
        }

        public void setAdultQuotedPoints(String adultQuotedPoints) {
            AdultQuotedPoints = adultQuotedPoints;
        }

        public String getAdultQuotedAmount() {
            return AdultQuotedAmount;
        }

        public void setAdultQuotedAmount(String adultQuotedAmount) {
            AdultQuotedAmount = adultQuotedAmount;
        }

        public String getChildQuotedPoints() {
            return ChildQuotedPoints;
        }

        public void setChildQuotedPoints(String childQuotedPoints) {
            ChildQuotedPoints = childQuotedPoints;
        }

        public String getChildQuotedAmount() {
            return ChildQuotedAmount;
        }

        public void setChildQuotedAmount(String childQuotedAmount) {
            ChildQuotedAmount = childQuotedAmount;
        }

        public String getInfantQuotedPoints() {
            return InfantQuotedPoints;
        }

        public void setInfantQuotedPoints(String infantQuotedPoints) {
            InfantQuotedPoints = infantQuotedPoints;
        }

        public String getInfantQuotedAmount() {
            return InfantQuotedAmount;
        }

        public void setInfantQuotedAmount(String infantQuotedAmount) {
            InfantQuotedAmount = infantQuotedAmount;
        }

        public String getFareSellKey() {
            return FareSellKey;
        }

        public void setFareSellKey(String fareSellKey) {
            FareSellKey = fareSellKey;
        }

        public String getAvailableCount() {
            return AvailableCount;
        }

        public void setAvailableCount(String availableCount) {
            AvailableCount = availableCount;
        }
    }

    public List<SearchFlightReceive.JourneyDateMarket> getJourneyDateMarket() {
        return JourneyDateMarket;
    }

    public void setJourneyDateMarket(List<SearchFlightReceive.JourneyDateMarket> journeyDateMarket) {
        JourneyDateMarket = journeyDateMarket;
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


    public class GoingFlight {

        private String arrival_station;
        private String departure_date;
        private String departure_station;
        private String status;

        public String getArrival_station() {
            return arrival_station;
        }

        public void setArrival_station(String arrival_station) {
            this.arrival_station = arrival_station;
        }

        public String getDeparture_date() {
            return departure_date;
        }

        public void setDeparture_date(String departure_date) {
            this.departure_date = departure_date;
        }

        public String getDeparture_station() {
            return departure_station;
        }

        public void setDeparture_station(String departure_station) {
            this.departure_station = departure_station;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }


    public SearchFlightReceive() {

    }

    public SearchFlightReceive(SearchFlightReceive obj) {

        Status = obj.getStatus();
        Message = obj.getMessage();
        JourneyDateMarket = obj.getJourneyDateMarket();

    }

}
