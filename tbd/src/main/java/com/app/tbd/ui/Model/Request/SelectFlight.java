package com.app.tbd.ui.Model.Request;

/**
 * Created by Dell on 12/9/2015.
 */
public class SelectFlight {

    private String type;
    private String username;
    private String departure_station;
    private String arrival_station;
    private String return_date;
    private String departure_date;
    private String adult;
    private String infant;

    private String flight_number_1;
    private String departure_time_1;
    private String arrival_time_1;
    private String journey_sell_key_1;
    private String fare_sell_key_1;

    private String flight_number_2;
    private String departure_time_2;
    private String arrival_time_2;
    private String journey_sell_key_2;
    private String fare_sell_key_2;


    public SelectFlight(){

    }

    public SelectFlight(SelectFlight data){
        type = data.getType();
        username = data.getUsername();
        departure_station = data.getDeparture_station();
        arrival_station = data.getArrival_station();
        return_date = data.getReturn_date();
        departure_date = data.getDeparture_date();
        adult = data.getAdult();
        infant = data.getInfant();

        flight_number_1 = data.getFlight_number_1();
        departure_time_1 = data.getDeparture_time_1();
        arrival_time_1 = data.getArrival_time_1();
        journey_sell_key_1 = data.getJourney_sell_key_1();
        fare_sell_key_1 = data.getFare_sell_key_1();

        flight_number_2 = data.getFlight_number_2();
        departure_time_2 = data.getDeparture_time_2();
        arrival_time_2 = data.getArrival_time_2();
        journey_sell_key_2 = data.getJourney_sell_key_2();
        fare_sell_key_2 = data.getFare_sell_key_2();

    }



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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDeparture_station() {
        return departure_station;
    }

    public void setDeparture_station(String departure_station) {
        this.departure_station = departure_station;
    }

    public String getArrival_station() {
        return arrival_station;
    }

    public void setArrival_station(String arrival_station) {
        this.arrival_station = arrival_station;
    }

    public String getReturn_date() {
        return return_date;
    }

    public void setReturn_date(String return_date) {
        this.return_date = return_date;
    }

    public String getAdult() {
        return adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public String getInfant() {
        return infant;
    }

    public void setInfant(String infant) {
        this.infant = infant;
    }

    public String getFlight_number_1() {
        return flight_number_1;
    }

    public void setFlight_number_1(String flight_number_1) {
        this.flight_number_1 = flight_number_1;
    }

    public String getDeparture_time_1() {
        return departure_time_1;
    }

    public void setDeparture_time_1(String departure_time_1) {
        this.departure_time_1 = departure_time_1;
    }

    public String getArrival_time_1() {
        return arrival_time_1;
    }

    public void setArrival_time_1(String arrival_time_1) {
        this.arrival_time_1 = arrival_time_1;
    }

    public String getJourney_sell_key_1() {
        return journey_sell_key_1;
    }

    public void setJourney_sell_key_1(String journey_sell_key_1) {
        this.journey_sell_key_1 = journey_sell_key_1;
    }

    public String getFare_sell_key_1() {
        return fare_sell_key_1;
    }

    public void setFare_sell_key_1(String fare_sell_key_1) {
        this.fare_sell_key_1 = fare_sell_key_1;
    }

    public String getFlight_number_2() {
        return flight_number_2;
    }

    public void setFlight_number_2(String flight_number_2) {
        this.flight_number_2 = flight_number_2;
    }

    public String getDeparture_time_2() {
        return departure_time_2;
    }

    public void setDeparture_time_2(String departure_time_2) {
        this.departure_time_2 = departure_time_2;
    }

    public String getArrival_time_2() {
        return arrival_time_2;
    }

    public void setArrival_time_2(String arrival_time_2) {
        this.arrival_time_2 = arrival_time_2;
    }

    public String getJourney_sell_key_2() {
        return journey_sell_key_2;
    }

    public void setJourney_sell_key_2(String journey_sell_key_2) {
        this.journey_sell_key_2 = journey_sell_key_2;
    }

    public String getFare_sell_key_2() {
        return fare_sell_key_2;
    }

    public void setFare_sell_key_2(String fare_sell_key_2) {
        this.fare_sell_key_2 = fare_sell_key_2;
    }

}
