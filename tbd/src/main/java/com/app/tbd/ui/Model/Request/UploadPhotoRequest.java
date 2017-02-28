package com.app.tbd.ui.Model.Request;

/**
 * Created by Dell on 9/13/2016.
 */
public class UploadPhotoRequest {

    private String UserName;
    private String Token;
    private String Extension;
    private String Data;

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getExtension() {
        return Extension;
    }

    public void setExtension(String extension) {
        Extension = extension;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }


    public UploadPhotoRequest() {

    }

    public UploadPhotoRequest(UploadPhotoRequest data) {
        UserName = data.getUserName();
        Token = data.getToken();
        Extension = data.getExtension();
        Data = data.getData();
    }

}
