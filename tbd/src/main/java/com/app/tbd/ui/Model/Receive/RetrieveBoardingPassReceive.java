package com.app.tbd.ui.Model.Receive;

import java.util.List;

/**
 * Created by Dell on 2/5/2016.
 */
public class RetrieveBoardingPassReceive {

    private String status;
    private String message;
    private List<BoardingPass> boarding_pass;
    private RetrieveBoardingPassReceive obj;

    public List<BoardingPass> getBoarding_pass() {
        return boarding_pass;
    }

    public void setBoarding_pass(List<BoardingPass> boarding_pass) {
        this.boarding_pass = boarding_pass;
    }

    public class BoardingPass{

        private String RecordLocator;
        private String Name;
        private String BarCodeData;
        private String BarCodeURL;
        private String QRCodeURL;
        private String QRCode;
        private String DepartureGate;
        private String DepartureDate;
        private String DepartureTime;
        private String BoardingTime;
        private String ArrivalTime;
        private String DepartureStation;
        private String ArrivalStation;
        private String BoardingSequence;
        private String Seat;
        private String FlightNumber;
        private String SSR;
        private String Fare;
        private String DepartureStationCode;
        private String ArrivalStationCode;
        private String DepartureDateTime;
        private String ArrivalDateTime;
        private String DepartureDayDate;

        public String getQRCode() {
            return QRCode;
        }

        public void setQRCode(String QRCode) {
            this.QRCode = QRCode;
        }

        public String getDepartureDayDate() {
            return DepartureDayDate;
        }

        public void setDepartureDayDate(String departureDayDate) {
            DepartureDayDate = departureDayDate;
        }

        public String getRecordLocator() {
            return RecordLocator;
        }

        public void setRecordLocator(String recordLocator) {
            RecordLocator = recordLocator;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getBarCodeData() {
            return BarCodeData;
        }

        public void setBarCodeData(String barCodeData) {
            BarCodeData = barCodeData;
        }

        public String getBarCodeURL() {
            return BarCodeURL;
        }

        public void setBarCodeURL(String barCodeURL) {
            BarCodeURL = barCodeURL;
        }

        public String getQRCodeURL() {
            return QRCodeURL;
        }

        public void setQRCodeURL(String QRCodeURL) {
            this.QRCodeURL = QRCodeURL;
        }

        public String getDepartureGate() {
            return DepartureGate;
        }

        public void setDepartureGate(String departureGate) {
            DepartureGate = departureGate;
        }

        public String getDepartureDate() {
            return DepartureDate;
        }

        public void setDepartureDate(String departureDate) {
            DepartureDate = departureDate;
        }

        public String getDepartureTime() {
            return DepartureTime;
        }

        public void setDepartureTime(String departureTime) {
            DepartureTime = departureTime;
        }

        public String getBoardingTime() {
            return BoardingTime;
        }

        public void setBoardingTime(String boardingTime) {
            BoardingTime = boardingTime;
        }

        public String getArrivalTime() {
            return ArrivalTime;
        }

        public void setArrivalTime(String arrivalTime) {
            ArrivalTime = arrivalTime;
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

        public String getBoardingSequence() {
            return BoardingSequence;
        }

        public void setBoardingSequence(String boardingSequence) {
            BoardingSequence = boardingSequence;
        }

        public String getSeat() {
            return Seat;
        }

        public void setSeat(String seat) {
            Seat = seat;
        }

        public String getFlightNumber() {
            return FlightNumber;
        }

        public void setFlightNumber(String flightNumber) {
            FlightNumber = flightNumber;
        }

        public String getSSR() {
            return SSR;
        }

        public void setSSR(String SSR) {
            this.SSR = SSR;
        }

        public String getFare() {
            return Fare;
        }

        public void setFare(String fare) {
            Fare = fare;
        }

        public String getDepartureStationCode() {
            return DepartureStationCode;
        }

        public void setDepartureStationCode(String departureStationCode) {
            DepartureStationCode = departureStationCode;
        }

        public String getArrivalStationCode() {
            return ArrivalStationCode;
        }

        public void setArrivalStationCode(String arrivalStationCode) {
            ArrivalStationCode = arrivalStationCode;
        }

        public String getDepartureDateTime() {
            return DepartureDateTime;
        }

        public void setDepartureDateTime(String departureDateTime) {
            DepartureDateTime = departureDateTime;
        }

        public String getArrivalDateTime() {
            return ArrivalDateTime;
        }

        public void setArrivalDateTime(String arrivalDateTime) {
            ArrivalDateTime = arrivalDateTime;
        }
    }

    public RetrieveBoardingPassReceive(RetrieveBoardingPassReceive dataObj) {
        obj = dataObj;
        status = dataObj.getStatus();
        message = dataObj.getMessage();
        boarding_pass = dataObj.getBoarding_pass();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RetrieveBoardingPassReceive getObj() {
        return obj;
    }

    public void setObj(RetrieveBoardingPassReceive obj) {
        this.obj = obj;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
