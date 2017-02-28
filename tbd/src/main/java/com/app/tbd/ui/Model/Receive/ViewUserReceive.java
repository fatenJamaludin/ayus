package com.app.tbd.ui.Model.Receive;

public class ViewUserReceive {

    private final ViewUserReceive userObj;
    private String Status;
    private String Message;
    private String AddressLine1;
    private String AddressLine2;
    private String AddressLine3;
    private String City;
    private String PostalCode;
    private String ProvinceStateCode;
    private String CountryCode;
    private String username;
    private String Password;
    private String FirstName;
    private String MiddleName;
    private String LastName;
    private String DOB;
    private String Gender;
    private String MobilePhone;
    private String Nationality;
    private String Title;
    private String BusinessEmail;
    private String EmergencyDialingCode;
    private String EmergencyFamilyName;
    private String EmergencyGivenName;
    private String EmergencyPhoneNumber;
    private String EmergencyRelationship;
    private String ParentGuardian;
    private String ParentGuardianDocNumber;
    private String ParentGuardianEmail;
    private String ParentGuardianFullName;
    private String NotificationPreference;
    private String CultureCode;
    private String BigCustomerYN;
    private String CID;
    private String PID;
    private String PersonalEmail;

    public ViewUserReceive(ViewUserReceive returnData) {
        this.userObj = returnData;
        Message = returnData.getMessage();
        Status = returnData.getStatus();
        AddressLine1 = returnData.getAddressLine1();
        AddressLine2 = returnData.getAddressLine2();
        AddressLine3 = returnData.getAddressLine3();
        City = returnData.getCity();
        PostalCode = returnData.getPostalCode();
        ProvinceStateCode = returnData.getProvinceStateCode();
        CountryCode = returnData.getCountryCode();
        username = returnData.getUsername();
        Password = returnData.getPassword();
        FirstName = returnData.getFirstName();
        MiddleName = returnData.getMiddleName();
        LastName = returnData.getLastName();
        DOB = returnData.getDOB();
        Gender = returnData.getGender();
        MobilePhone = returnData.getMobilePhone();
        Nationality = returnData.getNationality();
        Title = returnData.getTitle();
        BusinessEmail = returnData.getBusinessEmail();
        EmergencyDialingCode = returnData.getEmergencyDialingCode();
        EmergencyFamilyName = returnData.getEmergencyFamilyName();
        EmergencyGivenName = returnData.getEmergencyGivenName();
        EmergencyPhoneNumber = returnData.getEmergencyPhoneNumber();
        EmergencyRelationship = returnData.getEmergencyRelationship();
        ParentGuardian = returnData.getParentGuardian();
        ParentGuardianDocNumber = returnData.getParentGuardianDocNumber();
        ParentGuardianEmail = returnData.getParentGuardianEmail();
        ParentGuardianFullName = returnData.getParentGuardianFullName();
        NotificationPreference = returnData.getNotificationPreference();
        CultureCode = returnData.getCultureCode();
        BigCustomerYN = returnData.getBigCustomerYN();
        CID = returnData.getCID();
        PID = returnData.getPID();
        PersonalEmail = returnData.getPersonalEmail();
    }

    public ViewUserReceive getUserObj() {
        return userObj;
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

    public String getPostalCode() {
        return PostalCode;
    }

    public void setPostalCode(String postalCode) {
        PostalCode = postalCode;
    }

    public String getProvinceStateCode() {
        return ProvinceStateCode;
    }

    public void setProvinceStateCode(String provinceStateCode) {
        ProvinceStateCode = provinceStateCode;
    }

    public String getCountryCode() {
        return CountryCode;
    }

    public void setCountryCode(String countryCode) {
        CountryCode = countryCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
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

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getMobilePhone() {
        return MobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        MobilePhone = mobilePhone;
    }

    public String getNationality() {
        return Nationality;
    }

    public void setNationality(String nationality) {
        Nationality = nationality;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getBusinessEmail() {
        return BusinessEmail;
    }

    public void setBusinessEmail(String businessEmail) {
        BusinessEmail = businessEmail;
    }

    public String getEmergencyDialingCode() {
        return EmergencyDialingCode;
    }

    public void setEmergencyDialingCode(String emergencyDialingCode) {
        EmergencyDialingCode = emergencyDialingCode;
    }

    public String getEmergencyFamilyName() {
        return EmergencyFamilyName;
    }

    public void setEmergencyFamilyName(String emergencyFamilyName) {
        EmergencyFamilyName = emergencyFamilyName;
    }

    public String getEmergencyGivenName() {
        return EmergencyGivenName;
    }

    public void setEmergencyGivenName(String emergencyGivenName) {
        EmergencyGivenName = emergencyGivenName;
    }

    public String getEmergencyPhoneNumber() {
        return EmergencyPhoneNumber;
    }

    public void setEmergencyPhoneNumber(String emergencyPhoneNumber) {
        EmergencyPhoneNumber = emergencyPhoneNumber;
    }

    public String getEmergencyRelationship() {
        return EmergencyRelationship;
    }

    public void setEmergencyRelationship(String emergencyRelationship) {
        EmergencyRelationship = emergencyRelationship;
    }

    public String getParentGuardian() {
        return ParentGuardian;
    }

    public void setParentGuardian(String parentGuardian) {
        ParentGuardian = parentGuardian;
    }

    public String getParentGuardianDocNumber() {
        return ParentGuardianDocNumber;
    }

    public void setParentGuardianDocNumber(String parentGuardianDocNumber) {
        ParentGuardianDocNumber = parentGuardianDocNumber;
    }

    public String getParentGuardianEmail() {
        return ParentGuardianEmail;
    }

    public void setParentGuardianEmail(String parentGuardianEmail) {
        ParentGuardianEmail = parentGuardianEmail;
    }

    public String getParentGuardianFullName() {
        return ParentGuardianFullName;
    }

    public void setParentGuardianFullName(String parentGuardianFullName) {
        ParentGuardianFullName = parentGuardianFullName;
    }

    public String getNotificationPreference() {
        return NotificationPreference;
    }

    public void setNotificationPreference(String notificationPreference) {
        NotificationPreference = notificationPreference;
    }

    public String getCultureCode() {
        return CultureCode;
    }

    public void setCultureCode(String cultureCode) {
        CultureCode = cultureCode;
    }

    public String getBigCustomerYN() {
        return BigCustomerYN;
    }

    public void setBigCustomerYN(String bigCustomerYN) {
        BigCustomerYN = bigCustomerYN;
    }

    public String getCID() {
        return CID;
    }

    public void setCID(String CID) {
        this.CID = CID;
    }

    public String getPID() {
        return PID;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }

    public String getPersonalEmail() {
        return PersonalEmail;
    }

    public void setPersonalEmail(String personalEmail) {
        PersonalEmail = personalEmail;
    }

}
