package com.app.tbd.ui.Model.Receive;

import java.util.List;

/**
 * Created by Dell on 10/18/2016.
 */

public class BookingFromStateReceive {

    private String Status;
    private String Message;
    private String Adult;
    private String Child;
    private String Infant;
    private String TotalQuotedPoints;
    private String TotalQuotedAmount;
    private List<TaxFee> TaxesAndFees;
    private List<Passenger> Passenger;
    private List<AddOn> AddOns;
    private String AdultQuotedPoints;
    private String ChildQuotedPoints;
    private String InfantQuotedPoints;
    private List<Journey> Journey;
    private String ProcessingFee;
    private List<CardType> CardType;
    private Currency Currency;

    public BookingFromStateReceive.Currency getCurrency() {
        return Currency;
    }

    public void setCurrency(BookingFromStateReceive.Currency currency) {
        Currency = currency;
    }

    public class CardType{

        public String getCode() {
            return Code;
        }

        public void setCode(String code) {
            Code = code;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String description) {
            Description = description;
        }

        public String getDefault() {
            return Default;
        }

        public void setDefault(String aDefault) {
            Default = aDefault;
        }

        public String getProcessingFee() {
            return ProcessingFee;
        }

        public void setProcessingFee(String processingFee) {
            ProcessingFee = processingFee;
        }

        private String Code;
        private String Description;
        private String Default;
        private String ProcessingFee;

    }

    public class Journey {

        private String AdultQuotedPoints;
        private String ChildQuotedPoints;
        private String InfantQuotedPoints;
        private String AdultQuotedAmount;
        private String ChildQuotedAmount;
        private String InfantQuotedAmount;
        private String TotalQuotedPoints;
        private String TotalQuotedAmount;
        private List<TaxFee> TaxesAndFees;
        private List<AddOn> AddOns;
        private List<Segment> Segment;

        public String getTotalQuotedAmount() {
            return TotalQuotedAmount;
        }

        public void setTotalQuotedAmount(String totalQuotedAmount) {
            TotalQuotedAmount = totalQuotedAmount;
        }

        public String getTotalQuotedPoints() {
            return TotalQuotedPoints;
        }

        public void setTotalQuotedPoints(String totalQuotedPoints) {
            TotalQuotedPoints = totalQuotedPoints;
        }

        public String getAdultQuotedPoints() {
            return AdultQuotedPoints;
        }

        public void setAdultQuotedPoints(String adultQuotedPoints) {
            AdultQuotedPoints = adultQuotedPoints;
        }

        public String getChildQuotedPoints() {
            return ChildQuotedPoints;
        }

        public void setChildQuotedPoints(String childQuotedPoints) {
            ChildQuotedPoints = childQuotedPoints;
        }

        public String getInfantQuotedPoints() {
            return InfantQuotedPoints;
        }

        public void setInfantQuotedPoints(String infantQuotedPoints) {
            InfantQuotedPoints = infantQuotedPoints;
        }

        public String getAdultQuotedAmount() {
            return AdultQuotedAmount;
        }

        public void setAdultQuotedAmount(String adultQuotedAmount) {
            AdultQuotedAmount = adultQuotedAmount;
        }

        public String getChildQuotedAmount() {
            return ChildQuotedAmount;
        }

        public void setChildQuotedAmount(String childQuotedAmount) {
            ChildQuotedAmount = childQuotedAmount;
        }

        public String getInfantQuotedAmount() {
            return InfantQuotedAmount;
        }

        public void setInfantQuotedAmount(String infantQuotedAmount) {
            InfantQuotedAmount = infantQuotedAmount;
        }

        public List<TaxFee> getTaxesAndFees() {
            return TaxesAndFees;
        }

        public void setTaxesAndFees(List<TaxFee> taxesAndFees) {
            TaxesAndFees = taxesAndFees;
        }

        public List<AddOn> getAddOns() {
            return AddOns;
        }

        public void setAddOns(List<AddOn> addOns) {
            AddOns = addOns;
        }

        public List<BookingFromStateReceive.Segment> getSegment() {
            return Segment;
        }

        public void setSegment(List<BookingFromStateReceive.Segment> segment) {
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
        private List<PaxSeat> PaxSeat;
        private List<PaxSSR> PaxSSR;

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

        public List<BookingFromStateReceive.PaxSeat> getPaxSeat() {
            return PaxSeat;
        }

        public void setPaxSeat(List<BookingFromStateReceive.PaxSeat> paxSeat) {
            PaxSeat = paxSeat;
        }

        public List<BookingFromStateReceive.PaxSSR> getPaxSSR() {
            return PaxSSR;
        }

        public void setPaxSSR(List<BookingFromStateReceive.PaxSSR> paxSSR) {
            PaxSSR = paxSSR;
        }

    }

    public class PaxSSR {

        private String PassengerNumber;
        private String SSRCode;

        public String getPassengerNumber() {
            return PassengerNumber;
        }

        public void setPassengerNumber(String passengerNumber) {
            PassengerNumber = passengerNumber;
        }

        public String getSSRCode() {
            return SSRCode;
        }

        public void setSSRCode(String SSRCode) {
            this.SSRCode = SSRCode;
        }

    }

    public class PaxSeat {

    }

    public class Currency {

        private String CurrencyCode;
        private String Description;
        private String RoundFactor;
        private String DisplayDigits;

        public String getDisplayDigits() {
            return DisplayDigits;
        }

        public void setDisplayDigits(String displayDigits) {
            DisplayDigits = displayDigits;
        }

        public String getRoundFactor() {
            return RoundFactor;
        }

        public void setRoundFactor(String roundFactor) {
            RoundFactor = roundFactor;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String description) {
            Description = description;
        }

        public String getCurrencyCode() {
            return CurrencyCode;
        }

        public void setCurrencyCode(String currencyCode) {
            CurrencyCode = currencyCode;
        }
    }

    public List<BookingFromStateReceive.CardType> getCardType() {
        return CardType;
    }

    public void setCardType(List<BookingFromStateReceive.CardType> cardType) {
        CardType = cardType;
    }

    public String getProcessingFee() {
        return ProcessingFee;
    }

    public void setProcessingFee(String processingFee) {
        ProcessingFee = processingFee;
    }

    public List<BookingFromStateReceive.Journey> getJourney() {
        return Journey;
    }

    public void setJourney(List<BookingFromStateReceive.Journey> journey) {
        Journey = journey;
    }

    public InitialSlider getInitialSliderLogic() {
        return InitialSliderLogic;
    }

    public void setInitialSliderLogic(InitialSlider initialSliderLogic) {
        InitialSliderLogic = initialSliderLogic;
    }

    private InitialSlider InitialSliderLogic;

    public class InitialSlider {

        private String BookingCurrencyCode;
        private String BookingTotalAmount;
        private String PointConversionRate;
        private String MaxMargin;
        private String PaymentMinBigPoint;
        private String BookingQuotedAmount;
        private String BookingQuotedPoints;
        private String BookingMaxNoOfBucket;
        private String MarginPerBucket;
        private String PaymentMaxBigPoint;
        private String PaymentNoOfBucket;
        private String PaymentMinCashPayment;

        public String getMinimumBigPointSlider() {
            return MinimumBigPointForSlider;
        }

        public void setMinimumBigPointSlider(String minimumBigPointSlider) {
            MinimumBigPointForSlider = minimumBigPointSlider;
        }

        private String MinimumBigPointForSlider;

        public String getBookingCurrencyCode() {
            return BookingCurrencyCode;
        }

        public void setBookingCurrencyCode(String bookingCurrencyCode) {
            BookingCurrencyCode = bookingCurrencyCode;
        }

        public String getBookingTotalAmount() {
            return BookingTotalAmount;
        }

        public void setBookingTotalAmount(String bookingTotalAmount) {
            BookingTotalAmount = bookingTotalAmount;
        }

        public String getPointConversionRate() {
            return PointConversionRate;
        }

        public void setPointConversionRate(String pointConversionRate) {
            PointConversionRate = pointConversionRate;
        }

        public String getMaxMargin() {
            return MaxMargin;
        }

        public void setMaxMargin(String maxMargin) {
            MaxMargin = maxMargin;
        }

        public String getPaymentMinBigPoint() {
            return PaymentMinBigPoint;
        }

        public void setPaymentMinBigPoint(String paymentMinBigPoint) {
            PaymentMinBigPoint = paymentMinBigPoint;
        }

        public String getBookingQuotedAmount() {
            return BookingQuotedAmount;
        }

        public void setBookingQuotedAmount(String bookingQuotedAmount) {
            BookingQuotedAmount = bookingQuotedAmount;
        }

        public String getBookingQuotedPoints() {
            return BookingQuotedPoints;
        }

        public void setBookingQuotedPoints(String bookingQuotedPoints) {
            BookingQuotedPoints = bookingQuotedPoints;
        }

        public String getBookingMaxNoOfBucket() {
            return BookingMaxNoOfBucket;
        }

        public void setBookingMaxNoOfBucket(String bookingMaxNoOfBucket) {
            BookingMaxNoOfBucket = bookingMaxNoOfBucket;
        }

        public String getMarginPerBucket() {
            return MarginPerBucket;
        }

        public void setMarginPerBucket(String marginPerBucket) {
            MarginPerBucket = marginPerBucket;
        }

        public String getPaymentMaxBigPoint() {
            return PaymentMaxBigPoint;
        }

        public void setPaymentMaxBigPoint(String paymentMaxBigPoint) {
            PaymentMaxBigPoint = paymentMaxBigPoint;
        }

        public String getPaymentNoOfBucket() {
            return PaymentNoOfBucket;
        }

        public void setPaymentNoOfBucket(String paymentNoOfBucket) {
            PaymentNoOfBucket = paymentNoOfBucket;
        }

        public String getPaymentMinCashPayment() {
            return PaymentMinCashPayment;
        }

        public void setPaymentMinCashPayment(String paymentMinCashPayment) {
            PaymentMinCashPayment = paymentMinCashPayment;
        }

    }

    public class AddOn {

        private String Description;

        public String getPoints() {
            return Points;
        }

        public void setPoints(String points) {
            Points = points;
        }

        private String Points;

        public String getDescription() {
            return Description;
        }

        public void setDescription(String description) {
            Description = description;
        }

    }

    public List<AddOn> getAddOns() {
        return AddOns;
    }

    public String getTotalQuotedAmount() {
        return TotalQuotedAmount;
    }

    public void setTotalQuotedAmount(String totalQuotedAmount) {
        TotalQuotedAmount = totalQuotedAmount;
    }

    public void setAddOns(List<AddOn> addOns) {
        AddOns = addOns;
    }

    public String getAdultQuotedPoints() {
        return AdultQuotedPoints;
    }

    public void setAdultQuotedPoints(String adultQuotedPoints) {
        AdultQuotedPoints = adultQuotedPoints;
    }

    public String getChildQuotedPoints() {
        return ChildQuotedPoints;
    }

    public void setChildQuotedPoints(String childQuotedPoints) {
        ChildQuotedPoints = childQuotedPoints;
    }

    public String getInfantQuotedPoints() {
        return InfantQuotedPoints;
    }

    public void setInfantQuotedPoints(String infantQuotedPoints) {
        InfantQuotedPoints = infantQuotedPoints;
    }

    public List<BookingFromStateReceive.Passenger> getPassenger() {
        return Passenger;
    }

    public void setPassenger(List<BookingFromStateReceive.Passenger> passenger) {
        Passenger = passenger;
    }

    public String getTotalQuotedPoints() {
        return TotalQuotedPoints;
    }

    public void setTotalQuotedPoints(String totalQuotedPoints) {
        TotalQuotedPoints = totalQuotedPoints;
    }

    public class Passenger {

        private String PaxType;
        private String Title;
        private String FirstName;
        private String LastName;
        private String Gender;
        private String Nationality;
        private String DOB;

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

        public String getPaxType() {
            return PaxType;
        }

        public void setPaxType(String paxType) {
            PaxType = paxType;
        }
    }

    public BookingFromStateReceive(BookingFromStateReceive obj) {
        this.Status = obj.getStatus();
        this.Message = obj.getMessage();
        this.TaxesAndFees = obj.getTaxesAndFees();
        this.Passenger = obj.getPassenger();
        this.TotalQuotedPoints = obj.getTotalQuotedPoints();
        this.TotalQuotedAmount = obj.getTotalQuotedAmount();
        this.AdultQuotedPoints = obj.getAdultQuotedPoints();
        this.ChildQuotedPoints = obj.getChildQuotedPoints();
        this.InfantQuotedPoints = obj.getInfantQuotedPoints();
        this.AddOns = obj.getAddOns();
        this.InitialSliderLogic = obj.getInitialSliderLogic();
        this.Journey = obj.getJourney();
        this.ProcessingFee = obj.getProcessingFee();
        this.CardType = obj.getCardType();
        this.Currency = obj.getCurrency();
        this.Adult = obj.getAdult();
        this.Child = obj.getChild();
        this.Infant = obj.getInfant();
    }

    public List<TaxFee> getTaxesAndFees() {
        return TaxesAndFees;
    }

    public void setTaxesAndFees(List<TaxFee> taxesAndFees) {
        TaxesAndFees = taxesAndFees;
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

    public class TaxFee {

        private String Description;
        private String Amount;

        public String getDescription() {
            return Description;
        }

        public void setDescription(String description) {
            Description = description;
        }

        public String getAmount() {
            return Amount;
        }

        public void setAmount(String amount) {
            Amount = amount;
        }

    }


}
