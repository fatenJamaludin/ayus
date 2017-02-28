package com.app.tbd.ui.Model.Receive;

/*
 * Created by ImalPasha on 11/6/2015.
 */

 /* Response From API */

import java.util.List;

public class PaymentStatusReceive {

    private String Status;
    private String Message;
    private String RecordLocator;
    private String BookingStatus;
    private String CurrencyCode;
    private CurrencyClass Currency;
    private String Adult;
    private String Child;
    private String Infant;

    private String TotalPoints;
    private String TotalAmount;
    private String TransactionNumber;
    private List<Contact> Contact;
    private List<PassengerClass> Passenger;
    private List<JourneyClass> Journey;

    public PaymentStatusReceive(PaymentStatusReceive return_data) {

        Status = return_data.getStatus();
        Message = return_data.getMessage();
        RecordLocator = return_data.getRecordLocator();
        BookingStatus = return_data.getBookingStatus();
        CurrencyCode = return_data.getCurrencyCode();
        Currency = return_data.getCurrency();
        Adult = return_data.getAdult();
        Child = return_data.getChild();
        Infant = return_data.getInfant();

        TotalPoints = return_data.getTotalPoints();
        TotalAmount = return_data.getTotalAmount();
        TransactionNumber = return_data.getTransactionNumber();

        Contact = return_data.getContact();
        Passenger = return_data.getPassenger();
        Journey = return_data.getJourney();

    }

    public class Contact {

        private String Title;
        private String FirstName;
        private String MiddleName;
        private String LastName;
        private String EmailAddress;
        private String HomePhone;
        private String WorkPhone;
        private String OtherPhone;
        private String Fax;
        private String CompanyName;
        private String AddressLine1;
        private String AddressLine2;
        private String AddressLine3;
        private String City;
        private String ProvinceState;
        private String PostalCode;
        private String CountryCode;
        private String CustomerNumber;

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public String getFirstName() {
            return FirstName;
        }

        public void setFirstName(String firstName) {
            FirstName = firstName;
        }

        public String getMiddleName() {
            return MiddleName;
        }

        public void setMiddleName(String middleName) {
            MiddleName = middleName;
        }

        public String getLastName() {
            return LastName;
        }

        public void setLastName(String lastName) {
            LastName = lastName;
        }

        public String getEmailAddress() {
            return EmailAddress;
        }

        public void setEmailAddress(String emailAddress) {
            EmailAddress = emailAddress;
        }

        public String getHomePhone() {
            return HomePhone;
        }

        public void setHomePhone(String homePhone) {
            HomePhone = homePhone;
        }

        public String getWorkPhone() {
            return WorkPhone;
        }

        public void setWorkPhone(String workPhone) {
            WorkPhone = workPhone;
        }

        public String getOtherPhone() {
            return OtherPhone;
        }

        public void setOtherPhone(String otherPhone) {
            OtherPhone = otherPhone;
        }

        public String getFax() {
            return Fax;
        }

        public void setFax(String fax) {
            Fax = fax;
        }

        public String getCompanyName() {
            return CompanyName;
        }

        public void setCompanyName(String companyName) {
            CompanyName = companyName;
        }

        public String getAddressLine1() {
            return AddressLine1;
        }

        public void setAddressLine1(String addressLine1) {
            AddressLine1 = addressLine1;
        }

        public String getAddressLine2() {
            return AddressLine2;
        }

        public void setAddressLine2(String addressLine2) {
            AddressLine2 = addressLine2;
        }

        public String getAddressLine3() {
            return AddressLine3;
        }

        public void setAddressLine3(String addressLine3) {
            AddressLine3 = addressLine3;
        }

        public String getCity() {
            return City;
        }

        public void setCity(String city) {
            City = city;
        }

        public String getProvinceState() {
            return ProvinceState;
        }

        public void setProvinceState(String provinceState) {
            ProvinceState = provinceState;
        }

        public String getPostalCode() {
            return PostalCode;
        }

        public void setPostalCode(String postalCode) {
            PostalCode = postalCode;
        }

        public String getCountryCode() {
            return CountryCode;
        }

        public void setCountryCode(String countryCode) {
            CountryCode = countryCode;
        }

        public String getCustomerNumber() {
            return CustomerNumber;
        }

        public void setCustomerNumber(String customerNumber) {
            CustomerNumber = customerNumber;
        }


    }

    public class JourneyClass {

        public List<SegmentClass> getSegment() {
            return Segment;
        }

        public void setSegment(List<SegmentClass> segment) {
            Segment = segment;
        }

        private List<SegmentClass> Segment;

        public class SegmentClass {

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

            public List<PaxSeat> getPaxSeat() {
                return PaxSeat;
            }

            public void setPaxSeat(List<PaxSeat> paxSeat) {
                PaxSeat = paxSeat;
            }

            public List<PaxSSR> getPaxSSR() {
                return PaxSSR;
            }

            public void setPaxSSR(List<PaxSSR> paxSSR) {
                PaxSSR = paxSSR;
            }

            private String DepartureStation;
            private String ArrivalStation;
            private String STD;
            private String STA;
            private String CarrierCode;
            private String FlightNumber;
            private List<PaxSeat> PaxSeat;
            private List<PaxSSR> PaxSSR;

            public class PaxSeat {

            }

            public class PaxSSR {

            }
        }


    }

    public class PassengerClass {

        private String FirstName;
        private String LastName;
        private String Gender;
        private String Nationality;
        private String DOB;
        private String PaxType;
        private String Title;

        public String getPaxType() {
            return PaxType;
        }

        public void setPaxType(String paxType) {
            PaxType = paxType;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public String getFirstName() {
            return FirstName;
        }

        public void setFirstName(String firstName) {
            FirstName = firstName;
        }

        public String getLastName() {
            return LastName;
        }

        public void setLastName(String lastName) {
            LastName = lastName;
        }

        public String getGender() {
            return Gender;
        }

        public void setGender(String gender) {
            Gender = gender;
        }

        public String getNationality() {
            return Nationality;
        }

        public void setNationality(String nationality) {
            Nationality = nationality;
        }

        public String getDOB() {
            return DOB;
        }

        public void setDOB(String DOB) {
            this.DOB = DOB;
        }

    }

    public class CurrencyClass {

        private String CurrencyCode;
        private String Description;
        private String RoundFactor;
        private String DisplayDigits;

        public String getCurrencyCode() {
            return CurrencyCode;
        }

        public void setCurrencyCode(String currencyCode) {
            CurrencyCode = currencyCode;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String description) {
            Description = description;
        }

        public String getRoundFactor() {
            return RoundFactor;
        }

        public void setRoundFactor(String roundFactor) {
            RoundFactor = roundFactor;
        }

        public String getDisplayDigits() {
            return DisplayDigits;
        }

        public void setDisplayDigits(String displayDigits) {
            DisplayDigits = displayDigits;
        }


    }

    public List<JourneyClass> getJourney() {
        return Journey;
    }

    public void setJourney(List<JourneyClass> journey) {
        Journey = journey;
    }

    public List<PassengerClass> getPassenger() {
        return Passenger;
    }

    public void setPassenger(List<PassengerClass> passenger) {
        Passenger = passenger;
    }

    public List<PaymentStatusReceive.Contact> getContact() {
        return Contact;
    }

    public void setContact(List<PaymentStatusReceive.Contact> contact) {
        Contact = contact;
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

    public String getRecordLocator() {
        return RecordLocator;
    }

    public void setRecordLocator(String recordLocator) {
        RecordLocator = recordLocator;
    }

    public String getBookingStatus() {
        return BookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        BookingStatus = bookingStatus;
    }

    public String getCurrencyCode() {
        return CurrencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        CurrencyCode = currencyCode;
    }

    public CurrencyClass getCurrency() {
        return Currency;
    }

    public void setCurrency(CurrencyClass currency) {
        Currency = currency;
    }

    public String getAdult() {
        return Adult;
    }

    public void setAdult(String adult) {
        Adult = adult;
    }

    public String getChild() {
        return Child;
    }

    public void setChild(String child) {
        Child = child;
    }

    public String getInfant() {
        return Infant;
    }

    public void setInfant(String infant) {
        Infant = infant;
    }

    public String getTotalPoints() {
        return TotalPoints;
    }

    public void setTotalPoints(String totalPoints) {
        TotalPoints = totalPoints;
    }

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        TotalAmount = totalAmount;
    }

    public String getTransactionNumber() {
        return TransactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        TransactionNumber = transactionNumber;
    }

}
