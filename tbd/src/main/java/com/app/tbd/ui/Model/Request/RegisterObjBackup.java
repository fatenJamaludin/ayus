package com.app.tbd.ui.Model.Request;

/**
 * Created by Dell on 11/4/2015.
 */
public class RegisterObjBackup {

    private String registerCountry;
    private String registerState;
    private String registerUsername;
    private String registerPassword;
    private String registerConfirmPassword;
    private String registerFirstname;
    private String registerDOB;
    private String registerLastname;
    private String registerAddressLine1;
    private String registerAddressLine2;
    private String registerPostcode;
    private String registerMobilePhone;
    private String registerAlternatePhone;
    private String registerFax;


    /*Initiate Class*/
    public RegisterObjBackup(){
    }

    //public LoginRequest(String username123){
    //    this.username = username123;
    //}

    public RegisterObjBackup(RegisterObjBackup data){
        registerCountry = data.getRegisterCountry();
        registerState = data.getRegisterState();
        registerUsername = data.getRegisterUsername();
        registerPassword = data.getRegisterPassword();
        registerConfirmPassword = data.getRegisterConfirmPassword();
        registerFirstname = data.getRegisterFirstname();
        registerDOB = data.getRegisterDOB();
        registerLastname = data.getRegisterLastname();
        registerAddressLine1 = data.getRegisterAddressLine1();
        registerAddressLine2 = data.getRegisterAddressLine2();
        registerPostcode = data.getRegisterPostcode();
        registerMobilePhone = data.getRegisterMobilePhone();
        registerAlternatePhone = data.getRegisterAlternatePhone();
        registerFax = data.getRegisterFax();
    }



    public String getRegisterCountry() {
        return registerCountry;
    }

    public void setRegisterCountry(String registerCountry) {
        this.registerCountry = registerCountry;
    }

    public String getRegisterState() {
        return registerState;
    }

    public void setRegisterState(String registerState) {
        this.registerState = registerState;
    }

    public String getRegisterUsername() {
        return registerUsername;
    }

    public void setRegisterUsername(String registerUsername) {
        this.registerUsername = registerUsername;
    }

    public String getRegisterPassword() {
        return registerPassword;
    }

    public void setRegisterPassword(String registerPassword) {
        this.registerPassword = registerPassword;
    }

    public String getRegisterConfirmPassword() {
        return registerConfirmPassword;
    }

    public void setRegisterConfirmPassword(String registerConfirmPassword) {
        this.registerConfirmPassword = registerConfirmPassword;
    }

    public String getRegisterFirstname() {
        return registerFirstname;
    }

    public void setRegisterFirstname(String registerFirstname) {
        this.registerFirstname = registerFirstname;
    }

    public String getRegisterDOB() {
        return registerDOB;
    }

    public void setRegisterDOB(String registerDOB) {
        this.registerDOB = registerDOB;
    }

    public String getRegisterLastname() {
        return registerLastname;
    }

    public void setRegisterLastname(String registerLastname) {
        this.registerLastname = registerLastname;
    }

    public String getRegisterAddressLine1() {
        return registerAddressLine1;
    }

    public void setRegisterAddressLine1(String registerAddressLine1) {
        this.registerAddressLine1 = registerAddressLine1;
    }

    public String getRegisterAddressLine2() {
        return registerAddressLine2;
    }

    public void setRegisterAddressLine2(String registerAddressLine2) {
        this.registerAddressLine2 = registerAddressLine2;
    }

    public String getRegisterPostcode() {
        return registerPostcode;
    }

    public void setRegisterPostcode(String registerPostcode) {
        this.registerPostcode = registerPostcode;
    }

    public String getRegisterMobilePhone() {
        return registerMobilePhone;
    }

    public void setRegisterMobilePhone(String registerMobilePhone) {
        this.registerMobilePhone = registerMobilePhone;
    }

    public String getRegisterAlternatePhone() {
        return registerAlternatePhone;
    }

    public void setRegisterAlternatePhone(String registerAlternatePhone) {
        this.registerAlternatePhone = registerAlternatePhone;
    }

    public String getRegisterFax() {
        return registerFax;
    }

    public void setRegisterFax(String registerFax) {
        this.registerFax = registerFax;
    }




}
