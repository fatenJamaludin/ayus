package com.app.tbd.ui.Model.Request;

/**
 * Created by Metch user
 */
public class ChangePasswordRequest  {

    private String username;
    private String password;
    private String new_password;
    /*Initiate Class*/
    public ChangePasswordRequest(){
    }

    public ChangePasswordRequest(ChangePasswordRequest data){
        username = data.getEmail();
        password = data.getCurrentPassword();
        new_password = data.getNewPassword();

    }
    public String getEmail() {

        return username;
    }

    public void setEmail(String email) {

        this.username = email;
    }


    public String getCurrentPassword() {

        return password;
    }

    public void setCurrentPassword(String password) {

        this.password = password;
    }
    public String getNewPassword() {

        return new_password ;
    }

    public void setNewPassword(String new_password ) {

        this.new_password  = new_password ;
    }


    /*Response Data From Server*/
    String status;

    public String getStatus() {
        return status;
    }


}
