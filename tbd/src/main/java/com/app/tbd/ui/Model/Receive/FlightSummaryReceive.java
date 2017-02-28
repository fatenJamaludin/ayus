package com.app.tbd.ui.Model.Receive;

import java.util.List;

/**
 * Created by Dell on 12/16/2015.
 */
public class FlightSummaryReceive {

    private String status;
    private String signature;
    private ItinenaryInformation itinerary_information;
    private ContactInformation contact_information;
    private String message;
    private Insurance insurance_details;
    private List<PaymentDetail> payment_details;
    private List<FlightDetails> flight_details;
    private List<PriceDetails> price_details;
    private List<PassengerList> passenger_information;
    private List<SpecialServicesRequest> special_services_request;
    private String total_price;
    private String total_paid;
    private String total_due;
    private String booking_id;
    private String flight_type;

    private String ssr_status;

    private FlightSummaryReceive obj;

    public FlightSummaryReceive(FlightSummaryReceive param_obj){
        this.obj = param_obj;
        status = param_obj.getStatus();
        signature = param_obj.getSignature();
        itinerary_information = param_obj.getItenerary_information();
        contact_information = param_obj.getContact_information();
        message = param_obj.getMessage();
        insurance_details = param_obj.getInsurance_details();
        payment_details = param_obj.getPayment_details();
        flight_details = param_obj.getFlight_details();
        price_details = param_obj.getPrice_details();
        passenger_information = param_obj.getPassenger_information();
        total_price = param_obj.getTotal_price();
        total_paid = param_obj.getTotal_paid();
        total_due = param_obj.getTotal_due();
        booking_id = param_obj.getBooking_id();
        flight_type = param_obj.getFlight_type();
        special_services_request = param_obj.getSpecial_services_request();
        ssr_status = param_obj.getSsr_status();
    }



    public String getSsr_status() {
        return ssr_status;
    }

    public void setSsr_status(String ssr_status) {
        this.ssr_status = ssr_status;
    }


    public List<SpecialServicesRequest> getSpecial_services_request() {
        return special_services_request;
    }

    public void setSpecial_services_request(List<SpecialServicesRequest> special_services_request) {
        this.special_services_request = special_services_request;
    }

    public String getFlight_type() {
        return flight_type;
    }

    public void setFlight_type(String flight_type) {
        this.flight_type = flight_type;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }

    public String getPnr() {
        return pnr;
    }

    public void setPnr(String pnr) {
        this.pnr = pnr;
    }

    private String pnr;

    public Insurance getInsurance_details() {
        return insurance_details;
    }

    public void setInsurance_details(Insurance insurance_details) {
        this.insurance_details = insurance_details;
    }
    public class Insurance{

        private String status;
        private String conf_number;
        private String rate;

        public String getConf_number() {
            return conf_number;
        }

        public void setConf_number(String conf_number) {
            this.conf_number = conf_number;
        }

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }

    public class SpecialServicesRequest{


        private String type;
        private List<PassengerSSR> passenger;

        public List<PassengerSSR> getPassenger() {
            return passenger;
        }

        public void setPassenger(List<PassengerSSR> passenger) {
            this.passenger = passenger;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public class PassengerSSR{

            private String name;
            private List<ListSSR> list_ssr;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<ListSSR> getList_ssr() {
                return list_ssr;
            }

            public void setList_ssr(List<ListSSR> list_ssr) {
                this.list_ssr = list_ssr;
            }

        }

        public class ListSSR{

            private String ssr_name;

            public String getSsr_name() {
                return ssr_name;
            }

            public void setSsr_name(String ssr_name) {
                this.ssr_name = ssr_name;
            }

        }

    }

    public List<PaymentDetail> getPayment_details() {
        return payment_details;
    }

    public void setPayment_details(List<PaymentDetail> payment_details) {
        this.payment_details = payment_details;
    }

    public class PaymentDetail{

        private String payment_method;
        private String payment_status;
        private String payment_amount;


        public String getPayment_method() {
            return payment_method;
        }

        public void setPayment_method(String payment_method) {
            this.payment_method = payment_method;
        }

        public String getPayment_status() {
            return payment_status;
        }

        public void setPayment_status(String payment_status) {
            this.payment_status = payment_status;
        }

        public String getPayment_amount() {
            return payment_amount;
        }

        public void setPayment_amount(String payment_amount) {
            this.payment_amount = payment_amount;
        }

    }




















    public List<PassengerList> getPassenger_information() {
        return passenger_information;
    }

    public void setPassenger_information(List<PassengerList> passenger_information) {
        this.passenger_information = passenger_information;
    }

    public String getTotal_paid() {
        return total_paid;
    }

    public void setTotal_paid(String total_paid) {
        this.total_paid = total_paid;
    }

    public String getTotal_due() {
        return total_due;
    }

    public void setTotal_due(String total_due) {
        this.total_due = total_due;
    }


    public class FlightDetails {

        private String date;
        private String station;
        private String flight_number;
        private String time;
        private String type;
        private String flight_status;
        private String flight_segment_status;
        private String departure_time;
        private String arrival_time;

        public String getDeparture_time() {
            return departure_time;
        }

        public void setDeparture_time(String departure_time) {
            this.departure_time = departure_time;
        }

        public String getArrival_time() {
            return arrival_time;
        }

        public void setArrival_time(String arrival_time) {
            this.arrival_time = arrival_time;
        }

        public String getFlight_segment_status() {
            return flight_segment_status;
        }

        public void setFlight_segment_status(String flight_segment_status) {
            this.flight_segment_status = flight_segment_status;
        }

        public String getFlight_status() {
            return flight_status;
        }

        public void setFlight_status(String flight_status) {
            this.flight_status = flight_status;
        }

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
        private TaxesOrFees taxes_or_fees;
        private String total_taxes_or_fees;
        private String status;
        private List<Services> services;
        private List<TaxOrFee> taxes_or_fees_array;

        public List<TaxOrFee> getTaxes_or_fees_array() {
            return taxes_or_fees_array;
        }

        public void setTaxes_or_fees_array(List<TaxOrFee> taxes_or_fees_array) {
            this.taxes_or_fees_array = taxes_or_fees_array;
        }

        public class TaxOrFee{

            private String tax_fee_name;
            private String tax_fee_price;

            public String getTax_fee_price() {
                return tax_fee_price;
            }

            public void setTax_fee_price(String tax_fee_price) {
                this.tax_fee_price = tax_fee_price;
            }

            public String getTax_fee_name() {
                return tax_fee_name;
            }

            public void setTax_fee_name(String tax_fee_name) {
                this.tax_fee_name = tax_fee_name;
            }

        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<Services> getServices() {
            return services;
        }

        public void setServices(List<Services> services) {
            this.services = services;
        }

        public TaxesOrFees getTaxes_or_fees() {
            return taxes_or_fees;
        }

        public void setTaxes_or_fees(TaxesOrFees taxes_or_fees) {
            this.taxes_or_fees = taxes_or_fees;
        }

        public String getTotal_taxes_or_fees() {
            return total_taxes_or_fees;
        }

        public void setTotal_taxes_or_fees(String total_taxes_or_fees) {
            this.total_taxes_or_fees = total_taxes_or_fees;
        }

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

        public class TaxesOrFees{
            private String admin_fee;
            private String airport_tax;
            private String fuel_surcharge;
            private String goods_and_services_tax;
            private String total;


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
            private String service_price;

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


    }

    public class PassengerList{

        private String type;
        private String passenger_number;
        private String name;
        private String title;
        private String first_name;
        private String last_name;
        private String dob;
        private String travel_document;
        private String issuing_country;
        private String document_number;
        private String expiration_date;
        private String enrich_loyalty_number;
        private String gender;
        private String traveling_with;
        private String bonuslink;

        public String getBonuslink() {
            return bonuslink;
        }

        public void setBonuslink(String bonuslink) {
            this.bonuslink = bonuslink;
        }

        public String getTravelling_with() {
            return traveling_with;
        }

        public void setTravelling_with(String travelling_with) {
            this.traveling_with = travelling_with;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPassenger_number() {
            return passenger_number;
        }

        public void setPassenger_number(String passenger_number) {
            this.passenger_number = passenger_number;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getTravel_document() {
            return travel_document;
        }

        public void setTravel_document(String travel_document) {
            this.travel_document = travel_document;
        }

        public String getIssuing_country() {
            return issuing_country;
        }

        public void setIssuing_country(String issuing_country) {
            this.issuing_country = issuing_country;
        }

        public String getDocument_number() {
            return document_number;
        }

        public void setDocument_number(String document_number) {
            this.document_number = document_number;
        }

        public String getExpiration_date() {
            return expiration_date;
        }

        public void setExpiration_date(String expiration_date) {
            this.expiration_date = expiration_date;
        }

        public String getEnrich_loyalty_number() {
            return enrich_loyalty_number;
        }

        public void setEnrich_loyalty_number(String enrich_loyalty_number) {
            this.enrich_loyalty_number = enrich_loyalty_number;
        }

        public String  getPassengerName() {
            return name;
        }

        public void setPassengerName(String passengerName) {
            this.name = passengerName;
        }

    }


    public ContactInformation getContact_information() {
        return contact_information;
    }

    public void setContact_information(ContactInformation contact_information) {
        this.contact_information = contact_information;
    }


    public ItinenaryInformation getItenerary_information() {
        return itinerary_information;
    }

    public void setItenerary_information(ItinenaryInformation itenerary_information) {
        this.itinerary_information = itenerary_information;
    }

    public class ContactInformation{

        private String title;
        private String first_name;
        private String last_name;
        private String country;
        private String mobile_phone;
        private String alternate_phone;
        private String email;
        private String travel_purpose;
        private String company_name;
        private String address1;
        private String address2;
        private String address3;
        private String city;
        private String state;
        private String postcode;

        public String getPostcode() {
            return postcode;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }

        public String getTravel_purpose() {
            return travel_purpose;
        }

        public void setTravel_purpose(String travel_purpose) {
            this.travel_purpose = travel_purpose;
        }

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        public String getAddress1() {
            return address1;
        }

        public void setAddress1(String address1) {
            this.address1 = address1;
        }

        public String getAddress2() {
            return address2;
        }

        public void setAddress2(String address2) {
            this.address2 = address2;
        }

        public String getAddress3() {
            return address3;
        }

        public void setAddress3(String address3) {
            this.address3 = address3;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
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

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getMobile_phone() {
            return mobile_phone;
        }

        public void setMobile_phone(String mobile_phone) {
            this.mobile_phone = mobile_phone;
        }

        public String getAlternate_phone() {
            return alternate_phone;
        }

        public void setAlternate_phone(String alternate_phone) {
            this.alternate_phone = alternate_phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

    }

    public class ItinenaryInformation{

        private String booking_status;
        private String pnr;
        private String booking_date;
        private String itinerary_note;

        public String getItinerary_note() {
            return itinerary_note;
        }

        public void setItinerary_note(String itinerary_note) {
            this.itinerary_note = itinerary_note;
        }

        public String getBooking_status() {
            return booking_status;
        }

        public void setBooking_status(String booking_status) {
            this.booking_status = booking_status;
        }

        public String getPnr() {
            return pnr;
        }

        public void setPnr(String pnr) {
            this.pnr = pnr;
        }

        public String getBooking_date() {
            return booking_date;
        }

        public void setBooking_date(String booking_date) {
            this.booking_date = booking_date;
        }

    }


    public List<PassengerList> getPassenger_lists() {
        return passenger_information;
    }

    public void setPassenger_lists(List<PassengerList> passenger_lists) {
        this.passenger_information = passenger_lists;
    }



    public String getTotal_price() {return total_price;}

    public void setTotal_price(String total_price) {this.total_price = total_price;}

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




    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public FlightSummaryReceive getObj() {
        return obj;
    }

    public void setObj(FlightSummaryReceive obj) {
        this.obj = obj;
    }

}


