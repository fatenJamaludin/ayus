package com.app.tbd.ui.Model.Request;

/**
 * Created by Dell on 8/29/2016.
 */
public class RegisterRequest {

    private String UserName;
    private String Password;
    private String Title;
    private String FirstName;
    private String LastName;
    private String DateOfBirth;
    private String Nationality;
    private String Gender;
    private String AddressLine1;
    private String AddressLine2;
    private String AddressLine3;
    private String City;
    private String Country;
    private String PostalCode;
    private String State;
    private String MobilePhone;
    private String QuestionAns1;
    private String QuestionAns2;
    private String NickName;

    private String ParentGuardianFullName;
    private String ParentGuardianEmail;
    private String ParentGuardianDocNumber;
    private String ParentGuardian;

    /*Initiate Class*/
    public RegisterRequest() {
    }

    public RegisterRequest(RegisterRequest data) {
        UserName = data.getUserName();
        Password = data.getPassword();
        Title = data.getTitle();
        FirstName = data.getFirstName();
        LastName = data.getLastName();
        DateOfBirth = data.getDateOfBirth();
        Nationality = data.getNationality();
        Gender = data.getGender();
        AddressLine1 = data.getAddressLine1();
        AddressLine2 = data.getAddressLine2();
        AddressLine3 = data.getAddressLine3();
        City = data.getCity();
        Country = data.getCountry();
        PostalCode = data.getPostalCode();
        State = data.getState();
        MobilePhone = data.getMobilePhone();
        QuestionAns1 = data.getQuestionAns1();
        QuestionAns2 = data.getQuestionAns2();
        NickName = data.getNickName();

        ParentGuardian = data.getParentGuardian();
        ParentGuardianDocNumber = data.getParentGuardianDocNumber();
        ParentGuardianEmail = data.getParentGuardianEmail();
        ParentGuardianFullName = data.getParentGuardianFullName();
    }

    public String getParentGuardianFullName() {
        return ParentGuardianFullName;
    }

    public void setParentGuardianFullName(String parentGuardianFullName) {
        ParentGuardianFullName = parentGuardianFullName;
    }

    public String getParentGuardianEmail() {
        return ParentGuardianEmail;
    }

    public void setParentGuardianEmail(String parentGuardianEmail) {
        ParentGuardianEmail = parentGuardianEmail;
    }

    public String getParentGuardianDocNumber() {
        return ParentGuardianDocNumber;
    }

    public void setParentGuardianDocNumber(String parentGuardianDocNumber) {
        ParentGuardianDocNumber = parentGuardianDocNumber;
    }

    public String getParentGuardian() {
        return ParentGuardian;
    }

    public void setParentGuardian(String parentGuardian) {
        ParentGuardian = parentGuardian;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
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

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public String getNationality() {
        return Nationality;
    }

    public void setNationality(String nationality) {
        Nationality = nationality;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
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

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getPostalCode() {
        return PostalCode;
    }

    public void setPostalCode(String postalCode) {
        PostalCode = postalCode;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getMobilePhone() {
        return MobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        MobilePhone = mobilePhone;
    }

    public String getQuestionAns1() {
        return QuestionAns1;
    }

    public void setQuestionAns1(String questionAns1) {
        QuestionAns1 = questionAns1;
    }

    public String getQuestionAns2() {
        return QuestionAns2;
    }

    public void setQuestionAns2(String questionAns2) {
        QuestionAns2 = questionAns2;
    }

}
