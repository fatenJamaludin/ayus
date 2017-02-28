package com.app.tbd.ui.Model.Receive;

/*
 * Created by Metech USer
 */

 /* Response From API */

public class UpdateProfileReceive {

    private final UpdateProfileReceive userObj;
    private String status;
    private String message;
    private user_info user_info;

    public UpdateProfileReceive(UpdateProfileReceive param_userObj) {
        this.userObj = param_userObj;
    }

    public UpdateProfileReceive getUserObj() {
        return userObj;
    }

    public class user_info{

        private String username;
        private String password;
        private String signature;
        private String first_name;
        private String title;
        private String last_name;
        private String  DOB;
        private String contact_title;
        private String contact_first_name;
        private String contact_last_name;
        private String contact_address1;
        private String contact_address2;
        private String contact_address3;
        private String contact_country;
        private String contact_city;
        private String contact_state;
        private String contact_postcode;
        private String contact_mobile_phone;
        private String contact_alternate_phone;
        private String contact_fax;
        private String contact_email;
        private String bonuslink;

        public String getBonuslink() {
            return bonuslink;
        }

        public void setBonuslink(String bonuslink) {
            this.bonuslink = bonuslink;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
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

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public String getDOB() {
            return DOB;
        }

        public void setDOB(String DOB) {
            this.DOB = DOB;
        }

        public String getContact_title() {
            return contact_title;
        }

        public void setContact_title(String contact_title) {
            this.contact_title = contact_title;
        }

        public String getContact_first_name() {
            return contact_first_name;
        }

        public void setContact_first_name(String contact_first_name) {this.contact_first_name = contact_first_name;}

        public String getContact_last_name() {
            return contact_last_name;
        }

        public void setContact_last_name(String contact_last_name) {this.contact_last_name = contact_last_name;}

        public String getContact_address1() {
            return contact_address1;
        }

        public void setContact_address1(String contact_address1) {this.contact_address1 = contact_address1;}

        public String getContact_address2() {
            return contact_address2;
        }

        public void setContact_address2(String contact_address2) {this.contact_address2 = contact_address2;}

        public String getContact_address3() {
            return contact_address3;
        }

        public void setContact_address3(String contact_address3) {this.contact_address3 = contact_address3;}

        public String getContact_country() {
            return contact_country;
        }

        public void setContact_country(String contact_country) {this.contact_country = contact_country;}

        public String getContact_city() {
            return contact_city;
        }

        public void setContact_city(String contact_city) {
            this.contact_city = contact_city;
        }

        public String getContact_state() {
            return contact_state;
        }

        public void setContact_state(String contact_state) {
            this.contact_state = contact_state;
        }

        public String getContact_postcode() {
            return contact_postcode;
        }

        public void setContact_postcode(String contact_postcode) {this.contact_postcode = contact_postcode;}

        public String getContact_mobile_phone() {
            return contact_mobile_phone;
        }

        public void setContact_mobile_phone(String contact_mobile_phone) {
            this.contact_mobile_phone = contact_mobile_phone;
        }

        public String getContact_alternate_phone() {
            return contact_alternate_phone;
        }

        public void setContact_alternate_phone(String contact_alternate_phone) {
            this.contact_alternate_phone = contact_alternate_phone;
        }

        public String getContact_fax() {
            return contact_fax;
        }

        public void setContact_fax(String contact_fax) {
            this.contact_fax = contact_fax;
        }

        public String getContact_email() {
            return contact_email;
        }

        public void setContact_email(String contact_email) {
            this.contact_email = contact_email;
        }

    }

    public user_info getUserInfo() {
        return user_info;
    }

    public void setUserInfo(user_info userInfo) {
        this.user_info = userInfo;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {return message;}

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
