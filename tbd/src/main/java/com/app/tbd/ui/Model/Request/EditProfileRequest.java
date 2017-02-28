package com.app.tbd.ui.Model.Request;

public class EditProfileRequest {
    String UserName;
    String AddressLine1;
    String AddressLine2;
    String AddressLine3;
    String City;
    String PostalCode;
    String State;
    String Country;
    String FirstName;
    String LastName;
    String Gender;
    String DateOfBirth;
    String MobilePhone;
    String Nationality;
    String Title;
    String BusinessEmail;
    String ParentGuardianEmail;
    String ParentGuardianFullName;
    String EmergencyDialingCode;
    String EmergencyFamilyName;
    String EmergencyGivenName;
    String EmergencyPhoneNumber;
    String EmergencyRelationship;
    String ParentGuardian;
    String ParentGuardianDocNumber;
    String QuestionAns1;
    String QuestionAns2;
    String CID;
    String PID;
    String TicketId;

    public EditProfileRequest() {
    }

    public EditProfileRequest(EditProfileRequest data) {
        UserName = data.getUserName();
        TicketId = data.getTicketId();
        AddressLine1 = data.getAddressLine1();
        AddressLine2 = data.getAddressLine2();
        AddressLine3 = data.getAddressLine3();
        City = data.getCity();
        PostalCode = data.getPostalCode();
        State = data.getState();
        Country = data.getCountry();
        FirstName = data.getFirstName();
        LastName = data.getLastName();
        Gender = data.getGender();
        DateOfBirth = data.getDateOfBirth();
        MobilePhone = data.getMobilePhone();
        Nationality = data.getNationality();
        Title = data.getTitle();
        BusinessEmail = data.getBusinessEmail();
        ParentGuardianEmail = data.getParentGuardianEmail();
        ParentGuardianFullName = data.getParentGuardianFullName();
        EmergencyDialingCode = data.getEmergencyDialingCode();
        EmergencyFamilyName = data.getEmergencyFamilyName();
        EmergencyGivenName = data.getEmergencyGivenName();
        EmergencyPhoneNumber = data.getEmergencyPhoneNumber();
        EmergencyRelationship = data.getEmergencyRelationship();
        ParentGuardian = data.getParentGuardian();
        ParentGuardianDocNumber = data.getParentGuardianDocNumber();
        QuestionAns1 = data.getQuestionAns1();
        QuestionAns2 = data.getQuestionAns2();
        CID = data.getCID();
        PID = data.getPID();
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getTicketId() {
        return TicketId;
    }

    public void setToken(String ticketId) {
        TicketId = ticketId;
    }

    public String getAddressLine2() {
        return AddressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        AddressLine2 = addressLine2;
    }

    public String getAddressLine1() {
        return AddressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        AddressLine1 = addressLine1;
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

    public String getPID() {
        return PID;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }

    public String getCID() {
        return CID;
    }

    public void setCID(String CID) {
        this.CID = CID;
    }

    public String getQuestionAns2() {
        return QuestionAns2;
    }

    public void setQuestionAns2(String questionAns2) {
        QuestionAns2 = questionAns2;
    }

    public String getQuestionAns1() {
        return QuestionAns1;
    }

    public void setQuestionAns1(String questionAns1) {
        QuestionAns1 = questionAns1;
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

    public String getEmergencyRelationship() {
        return EmergencyRelationship;
    }

    public void setEmergencyRelationship(String emergencyRelationship) {
        EmergencyRelationship = emergencyRelationship;
    }

    public String getEmergencyPhoneNumber() {
        return EmergencyPhoneNumber;
    }

    public void setEmergencyPhoneNumber(String emergencyPhoneNumber) {
        EmergencyPhoneNumber = emergencyPhoneNumber;
    }

    public String getEmergencyGivenName() {
        return EmergencyGivenName;
    }

    public void setEmergencyGivenName(String emergencyGivenName) {
        EmergencyGivenName = emergencyGivenName;
    }

    public String getEmergencyFamilyName() {
        return EmergencyFamilyName;
    }

    public void setEmergencyFamilyName(String emergencyFamilyName) {
        EmergencyFamilyName = emergencyFamilyName;
    }

    public String getEmergencyDialingCode() {
        return EmergencyDialingCode;
    }

    public void setEmergencyDialingCode(String emergencyDialingCode) {
        EmergencyDialingCode = emergencyDialingCode;
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

    public String getBusinessEmail() {
        return BusinessEmail;
    }

    public void setBusinessEmail(String businessEmail) {
        BusinessEmail = businessEmail;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getNationality() {
        return Nationality;
    }

    public void setNationality(String nationality) {
        Nationality = nationality;
    }

    public String getMobilePhone() {
        return MobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        MobilePhone = mobilePhone;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    //Response Data From Server
    String status;

    public String getStatus() {
        return status;
    }
}

