package com.app.tbd.ui.Model.Receive;

import java.util.List;

/**
 * Created by Metech user
 */
public class ItineraryInfoReceive {

    private String status;
    private ItineraryInfoReceive Obj;
    private insurance insurance;
    private String booking_id;
    private  String total_taxes_or_fees;

    private List<FlightDetails> flight_details;
    private List<PriceDetails> price_details;
    private List<Services> services;


    public List<FlightDetails> getFlight_details() {
        return flight_details;
    }

    public void setFlight_details(List<FlightDetails> flight_details) {
        this.flight_details = flight_details;
    }



    public List<PriceDetails> getPrice_details() {
        return price_details;
    }

    public void setPrice_details(List<PriceDetails> price_details) {
        this.price_details = price_details;
    }



    public List<Services> getServices() {
        return services;
    }

    public void setServices(List<Services> services) {
        this.services = services;
    }

    public class FlightDetails {

        private String date;
        private String station;
        private String flight_number;
        private String time;
        private String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getStation() {
            return station;
        }

        public void setStation(String station) {
            this.station = station;
        }

        public String getFlight_number() {
            return flight_number;
        }

        public void setFlight_number(String flight_number) {
            this.flight_number = flight_number;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }



    }

    public class PriceDetails{


        private String title;
        private String guest;
        private String total_guest;
        private String infant;
        private String total_infant;
        private String taxes_or_fees;
        private String admin_fee;
        private String airport_tax;
        private String fuel_surcharge;
        private String goods_and_services_tax;
        private String total;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getGuest() {
            return guest;
        }

        public void setGuest(String guest) {
            this.guest = guest;
        }

        public String getTotal_guest() {
            return total_guest;
        }

        public void setTotal_guest(String total_guest) {
            this.total_guest = total_guest;
        }

        public String getInfant() {
            return infant;
        }

        public void setInfant(String infant) {
            this.infant = infant;
        }

        public String getTotal_infant() {
            return total_infant;
        }

        public void setTotal_infant(String total_infant) {
            this.total_infant = total_infant;
        }

        public String getTaxes_or_fees() {
            return taxes_or_fees;
        }

        public void setTaxes_or_fees(String taxes_or_fees) {
            this.taxes_or_fees = taxes_or_fees;
        }

        public String getAdmin_fee() {
            return admin_fee;
        }

        public void setAdmin_fee(String admin_fee) {
            this.admin_fee = admin_fee;
        }

        public String getAirport_tax() {
            return airport_tax;
        }

        public void setAirport_tax(String airport_tax) {
            this.airport_tax = airport_tax;
        }

        public String getFuel_surcharge() {
            return fuel_surcharge;
        }

        public void setFuel_surcharge(String fuel_surcharge) {
            this.fuel_surcharge = fuel_surcharge;
        }

        public String getGoods_and_services_tax() {
            return goods_and_services_tax;
        }

        public void setGoods_and_services_tax(String goods_and_services_tax) {
            this.goods_and_services_tax = goods_and_services_tax;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }
    }


    public class Services{

        private String service_name;
        private  String service_price;

        public String getService_name() {
            return service_name;
        }

        public void setService_name(String service_name) {
            this.service_name = service_name;
        }

        public String getService_price() {
            return service_price;
        }

        public void setService_price(String service_price) {
            this.service_price = service_price;
        }
    }



    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }

    public insurance getInsuranceObj() {return insurance;}

    public void setInsuranceObj(insurance insuranceObj) {
        this.insurance = insuranceObj;
    }


    public class insurance{

        private String status;
        private String code;

    }

    public ItineraryInfoReceive(ItineraryInfoReceive param_obj){
        this.Obj = param_obj;
    }

    public ItineraryInfoReceive getObj() {return Obj;}

    public void setObj(ItineraryInfoReceive obj) {
        Obj = obj;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
