package com.app.tbd.ui.Model.Request;

public class PasssengerInfoV2 {



    private String title;
    private String first_name;
    private String last_name;
    private String dob;
    private String passenger_below_182;
    private String with_infant;
    private boolean selected;
    private boolean active;
    private String seat;
    private String compartment;
    private String compartmentReturn;
    private String seatReturn;
    private String checkedIn;
    private String originalSeatType;
    private String flightStatus;

    public String getFlightStatus() {
        return flightStatus;
    }

    public void setFlightStatus(String flightStatus) {
        this.flightStatus = flightStatus;
    }

    public String getOriginalSeatType() {
        return originalSeatType;
    }

    public void setOriginalSeatType(String originalSeatType) {
        this.originalSeatType = originalSeatType;
    }

    public String getCheckedIn() {
        return checkedIn;
    }

    public void setCheckedIn(String checkedIn) {
        this.checkedIn = checkedIn;
    }

    public String getUnit_designator() {
        return unit_designator;
    }

    public void setUnit_designator(String unit_designator) {
        this.unit_designator = unit_designator;
    }

    private String unit_designator;


       /* private PassengerSeatStatus seatStatus;

        public PassengerSeatStatus getSeatStatus() {
            return seatStatus;
        }

        public void setSeatStatus(PassengerSeatStatus seatStatus) {
            this.seatStatus = seatStatus;
        }*/



    public String getCompartmentReturn() {
        return compartmentReturn;
    }

    public void setCompartmentReturn(String compartmentReturn) {
        this.compartmentReturn = compartmentReturn;
    }

    public String getSeatReturn() {
        return seatReturn;
    }

    public void setSeatReturn(String seatReturn) {
        this.seatReturn = seatReturn;
    }



    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCompartment() {
        return compartment;
    }

    public void setCompartment(String compartment) {
        this.compartment = compartment;
    }


    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPassenger_below_18() {
        return passenger_below_182;
    }

    public void setPassenger_below_18(String passenger_below_18) {
        this.passenger_below_182 = passenger_below_18;
    }

    public String getWith_infant() {
        return with_infant;
    }

    public void setWith_infant(String with_infant) {
        this.with_infant = with_infant;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
