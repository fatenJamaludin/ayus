package com.app.tbd.ui.Model.Receive;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell on 11/24/2015.
 */
public class JourneyInfo {

    public String departure_date;
    public String type;
    public String departure_station_code;
    public String departure_station_name;
    public String arrival_station_code;
    public String arrival_station_name;
    public String flight_status;
    public String mh_flight_number;
    public String departure_station;

    public String getMh_flight_number() {
        return mh_flight_number;
    }

    public void setMh_flight_number(String mh_flight_number) {
        this.mh_flight_number = mh_flight_number;
    }

    public String getFlight_status() {
        return flight_status;
    }

    public void setFlight_status(String flight_status) {
        this.flight_status = flight_status;
    }

    //changeFlight

    public String getArrival_station() {
        return arrival_station;
    }

    public void setArrival_station(String arrival_station) {
        this.arrival_station = arrival_station;
    }

    public String getDeparture_station() {
        return departure_station;
    }

    public void setDeparture_station(String departure_station) {
        this.departure_station = departure_station;
    }

    public String arrival_station;

    private List<FlightInfo> flights = new ArrayList<FlightInfo>();

    public String getDeparture_date() {
        return departure_date;
    }

    public void setDeparture_date(String departure_date) {
        this.departure_date = departure_date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDeparture_station_code() {
        return departure_station_code;
    }

    public void setDeparture_station_code(String departure_station_code) {
        this.departure_station_code = departure_station_code;
    }

    public String getDeparture_station_name() {
        return departure_station_name;
    }

    public void setDeparture_station_name(String departure_station_name) {
        this.departure_station_name = departure_station_name;
    }

    public String getArrival_station_code() {
        return arrival_station_code;
    }

    public void setArrival_station_code(String arrival_station_code) {
        this.arrival_station_code = arrival_station_code;
    }

    public String getArrival_station_name() {
        return arrival_station_name;
    }

    public void setArrival_station_name(String arrival_station_name) {
        this.arrival_station_name = arrival_station_name;
    }

    public List<FlightInfo> getFlights() {
        return flights;
    }

    public void setFlights(List<FlightInfo> flights) {
        this.flights = flights;
    }

    @Override
    public String toString() {
        return "JourneyInfo{" +
                "departure_station='" + departure_station + '\'' +
                ", arrival_station='" + arrival_station + '\'' +
                '}';
    }
}
