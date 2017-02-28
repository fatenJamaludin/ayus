package com.app.tbd.ui.Model.Receive;

import java.util.List;

/**
 * Created by Dell on 10/13/2016.
 */

public class SeatInfoReceive {

    private String Status;
    private String Message;
    private String ArrivalStation;
    private String DepartureStation;
    private String EquipmentType;
    private String EquipmentTypeSuffix;
    private String AvailableUnit;

    public List<CompartmentInfoClass> getCompartmentInfo() {
        return CompartmentInfo;
    }

    public void setCompartmentInfo(List<CompartmentInfoClass> compartmentInfo) {
        CompartmentInfo = compartmentInfo;
    }

    private List<CompartmentInfoClass> CompartmentInfo;
    private List<SeatInfo> SeatInfo;
    private List<SeatGroupPassengerFee> SeatGroupPassengerFee;
    private List<PaxSeat> PaxSeat;

    public class CompartmentInfoClass{

        private String CompartmentDesignator;
        private String AvailableUnits;

        public String getAvailableUnits() {
            return AvailableUnits;
        }

        public void setAvailableUnits(String availableUnits) {
            AvailableUnits = availableUnits;
        }

        public String getCompartmentDesignator() {
            return CompartmentDesignator;
        }

        public void setCompartmentDesignator(String compartmentDesignator) {
            CompartmentDesignator = compartmentDesignator;
        }




    }

    public class SeatInfo {

        private String CompartmentDesignator;
        private String SeatAvailability;
        private String SeatDesignator;
        private String SeatType;
        private String SeatGroup;
        private String X;
        private String Y;

        public String getCompartmentDesignator() {
            return CompartmentDesignator;
        }

        public void setCompartmentDesignator(String compartmentDesignator) {
            CompartmentDesignator = compartmentDesignator;
        }

        public String getSeatAvailability() {
            return SeatAvailability;
        }

        public void setSeatAvailability(String seatAvailability) {
            SeatAvailability = seatAvailability;
        }

        public String getSeatDesignator() {
            return SeatDesignator;
        }

        public void setSeatDesignator(String seatDesignator) {
            SeatDesignator = seatDesignator;
        }

        public String getSeatType() {
            return SeatType;
        }

        public void setSeatType(String seatType) {
            SeatType = seatType;
        }

        public String getSeatGroup() {
            return SeatGroup;
        }

        public void setSeatGroup(String seatGroup) {
            SeatGroup = seatGroup;
        }

        public String getX() {
            return X;
        }

        public void setX(String x) {
            X = x;
        }

        public String getY() {
            return Y;
        }

        public void setY(String y) {
            Y = y;
        }

    }

    public class SeatGroupPassengerFee {

        private String SeatGroup;
        private String QuotedPoints;
        private String QuotedAmount;

        public String getSeatGroup() {
            return SeatGroup;
        }

        public void setSeatGroup(String seatGroup) {
            SeatGroup = seatGroup;
        }

        public String getQuotedPoints() {
            return QuotedPoints;
        }

        public void setQuotedPoints(String quotedPoints) {
            QuotedPoints = quotedPoints;
        }

        public String getQuotedAmount() {
            return QuotedAmount;
        }

        public void setQuotedAmount(String quotedAmount) {
            QuotedAmount = quotedAmount;
        }

    }

    public class PaxSeat {

    }


    public String getArrivalStation() {
        return ArrivalStation;
    }

    public void setArrivalStation(String arrivalStation) {
        ArrivalStation = arrivalStation;
    }

    public String getDepartureStation() {
        return DepartureStation;
    }

    public void setDepartureStation(String departureStation) {
        DepartureStation = departureStation;
    }

    public String getEquipmentType() {
        return EquipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        EquipmentType = equipmentType;
    }

    public String getEquipmentTypeSuffix() {
        return EquipmentTypeSuffix;
    }

    public void setEquipmentTypeSuffix(String equipmentTypeSuffix) {
        EquipmentTypeSuffix = equipmentTypeSuffix;
    }

    public String getAvailableUnits() {
        return AvailableUnit;
    }

    public void setAvailableUnits(String availavleUnits) {
        AvailableUnit = availavleUnits;
    }

    public List<SeatInfoReceive.SeatInfo> getSeatInfo() {
        return SeatInfo;
    }

    public void setSeatInfo(List<SeatInfoReceive.SeatInfo> seatInfo) {
        SeatInfo = seatInfo;
    }

    public List<SeatInfoReceive.SeatGroupPassengerFee> getSeatGroupPassengerFee() {
        return SeatGroupPassengerFee;
    }

    public void setSeatGroupPassengerFee(List<SeatInfoReceive.SeatGroupPassengerFee> seatGroupPassengerFee) {
        SeatGroupPassengerFee = seatGroupPassengerFee;
    }

    public List<SeatInfoReceive.PaxSeat> getPaxSeat() {
        return PaxSeat;
    }

    public void setPaxSeat(List<SeatInfoReceive.PaxSeat> paxSeat) {
        PaxSeat = paxSeat;
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

    public SeatInfoReceive(SeatInfoReceive obj) {

        this.Status = obj.getStatus();
        this.Message = obj.getMessage();
        this.ArrivalStation = obj.getArrivalStation();
        this.DepartureStation = obj.getDepartureStation();
        this.EquipmentType = obj.getEquipmentType();
        this.EquipmentTypeSuffix = obj.getEquipmentTypeSuffix();
        this.AvailableUnit = obj.getAvailableUnits();
        this.SeatInfo = obj.getSeatInfo();
        this.SeatGroupPassengerFee = obj.getSeatGroupPassengerFee();
        this.PaxSeat = obj.getPaxSeat();
        this.CompartmentInfo = obj.getCompartmentInfo();

    }
}
