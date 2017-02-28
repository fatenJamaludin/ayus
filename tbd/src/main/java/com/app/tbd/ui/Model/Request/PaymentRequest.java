package com.app.tbd.ui.Model.Request;

/**
 * Created by Dell on 10/22/2016.
 */

public class PaymentRequest {


    //http://airasiabig.me-tech.com.my/Booking/AddPayment?
    private String CardType;
    private String CardNumber;
    private String CardHolderName;
    private String CVV;
    private String CardIssuer;
    private String CardIssuerCountry;
    private String BigPoint;
    private String PaymentAmount;
    private String ExpirationDate;
    private String BillingAddress1;
    private String BillingAddress2;
    private String BillingCity;
    private String BillingCountry;
    private String BillingStateOrProvince;
    private String BillingPostalCode;
    /*Local Data Send To Server*/
    private String Signature;
    private String UserName;
    private String Token;

    public String getCardType() {
        return CardType;
    }

    public void setCardType(String cardType) {
        CardType = cardType;
    }

    public String getCardNumber() {
        return CardNumber;
    }

    public void setCardNumber(String cardNumber) {
        CardNumber = cardNumber;
    }

    public String getCardHolderName() {
        return CardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        CardHolderName = cardHolderName;
    }

    public String getCVV() {
        return CVV;
    }

    public void setCVV(String CVV) {
        this.CVV = CVV;
    }

    public String getCardIssuer() {
        return CardIssuer;
    }

    public void setCardIssuer(String cardIssuer) {
        CardIssuer = cardIssuer;
    }

    public String getCardIssuerCountry() {
        return CardIssuerCountry;
    }

    public void setCardIssuerCountry(String cardIssuerCountry) {
        CardIssuerCountry = cardIssuerCountry;
    }

    public String getBigPoint() {
        return BigPoint;
    }

    public void setBigPoint(String bigPoint) {
        BigPoint = bigPoint;
    }

    public String getPaymentAmount() {
        return PaymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        PaymentAmount = paymentAmount;
    }

    public String getExpirationDate() {
        return ExpirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        ExpirationDate = expirationDate;
    }

    public String getBillingAddress1() {
        return BillingAddress1;
    }

    public void setBillingAddress1(String billingAddress1) {
        BillingAddress1 = billingAddress1;
    }

    public String getBillingAddress2() {
        return BillingAddress2;
    }

    public void setBillingAddress2(String billingAddress2) {
        BillingAddress2 = billingAddress2;
    }

    public String getBillingCity() {
        return BillingCity;
    }

    public void setBillingCity(String billingCity) {
        BillingCity = billingCity;
    }

    public String getBillingCountry() {
        return BillingCountry;
    }

    public void setBillingCountry(String billingCountry) {
        BillingCountry = billingCountry;
    }

    public String getBillingStateOrProvince() {
        return BillingStateOrProvince;
    }

    public void setBillingStateOrProvince(String billingStateOrProvince) {
        BillingStateOrProvince = billingStateOrProvince;
    }

    public String getBillingPostalCode() {
        return BillingPostalCode;
    }

    public void setBillingPostalCode(String billingPostalCode) {
        BillingPostalCode = billingPostalCode;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }


    public String getSignature() {
        return Signature;
    }

    public void setSignature(String signature) {
        Signature = signature;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    /*Initiate Class*/
    public PaymentRequest() {
    }

    public PaymentRequest(PaymentRequest data) {

        Signature = data.getSignature();
        UserName = data.getUserName();
        Token = data.getToken();
        CardType = data.getCardType();
        CardNumber = data.getCardNumber();
        CardHolderName = data.getCardHolderName();
        CVV = data.getCVV();
        CardIssuer = data.getCardIssuer();
        CardIssuerCountry = data.getCardIssuerCountry();
        BigPoint = data.getBigPoint();
        PaymentAmount = data.getPaymentAmount();
        ExpirationDate = data.getExpirationDate();
        BillingAddress1 = data.getBillingAddress1();
        BillingAddress2 = data.getBillingAddress2();
        BillingCity = data.getBillingCity();
        BillingCountry = data.getBillingCountry();
        BillingStateOrProvince = data.getBillingStateOrProvince();
        BillingPostalCode = data.getBillingPostalCode();

    }
}
