package com.app.tbd.ui.Model.Request;

/**
 * Created by Dell on 11/4/2015.
 */
public class RegisterObj {


    private String username;
    private String password;
    private String title;
    private String first_name;
    private String last_name;
    private String dob;
    private String address_1;
    private String address_2;
    private String address_3;
    private String country;
    private String city;
    private String state;
    private String postcode;
    private String mobile_phone;
    private String alternate_phone;
    private String fax;
    private String newsletter;
    private String signature;
    private String bonuslink;

    public String getBonuslink() {
        return bonuslink;
    }

    public void setBonuslink(String bonuslink) {
        this.bonuslink = bonuslink;
    }

    //private String registerAddressLine2;

    public RegisterObj(RegisterObj data){
        country = data.getCountry();
        state = data.getState();
        username = data.getUsername();
        password = data.getPassword();
        first_name = data.getFirst_name();
        dob = data.getDob();
        last_name = data.getLast_name();
        address_1 = data.getAddress_1();
        address_2 = data.getAddress_2();
        address_3 = data.getAddress_3();
        title = data.getTitle();
        city = data.getCity();
        //registerAddressLine2 = data.getRegisterAddressLine2();
        postcode = data.getPostcode();
        mobile_phone = data.getMobile_phone();
        alternate_phone = data.getAlternate_phone();
        fax = data.getFax();
        signature = data.getSignature();
        newsletter = data.getNewsletter();
        bonuslink = data.getBonuslink();
        //"OTZSeE9vSkVNV1k9fE5LbVBwb2NjM3VwcnZJZERvYlZlRzNVWkdwWG5GNTBvaHRaUjd5RGd6YXV0SDI0ZmhFd3lLVzdwem5TMDh1Qk9pbFRxYWE1bjJTeElVV0NyYk13bmd2cktJRzNXR0JlYTk4MEtIR3RRclA4MURUYTNRUFJjTTV4Skt3bzZmWXdKQ0ZTNmNoajJhUlk9";
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getNewsletter() {
        return newsletter;
    }

    public void setNewsletter(String newsletter) {
        this.newsletter = newsletter;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }


    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
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

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getAddress_1() {
        return address_1;
    }

    public void setAddress_1(String address_1) {
        this.address_1 = address_1;
    }

    public String getAddress_2() {
        return address_2;
    }

    public void setAddress_2(String address_2) {
        this.address_2 = address_2;
    }

    public String getAddress_3() {
        return address_3;
    }

    public void setAddress_3(String address_3) {
        this.address_3 = address_3;
    }

    /*Initiate Class*/
    public RegisterObj(){
    }

    //public LoginRequest(String username123){
    //    this.username = username123;
    //}




}
