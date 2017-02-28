package com.app.tbd.ui.Model.Receive;

 /* Response From API */

public class ChangePasswordReceive {

    private final ChangePasswordReceive userObj;
    private String status;
    private String message;
    private UserInfo userInfo;


    public ChangePasswordReceive(ChangePasswordReceive param_userObj) {
        this.userObj = param_userObj;
    }

    public ChangePasswordReceive getUserObj() {
        return userObj;
    }

   public class UserInfo{

       private String username;
       private String new_password;
       private String password;

        public String getEmail() {
            return username;
        }
        public void setEmail(String email) {
            this.username = email;
        }
        public String getNewPassword() {
            return new_password;
        }
        public void setNewPassword(String newpassword) {
            this.new_password = newpassword;
        }
        public String getCurrentPassword() {
           return password;
       }
        public void setCurrentPassword(String currentpassword) {this.password = currentpassword;}

    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
