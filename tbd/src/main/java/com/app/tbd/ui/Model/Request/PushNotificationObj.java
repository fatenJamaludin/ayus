package com.app.tbd.ui.Model.Request;

/**
 * Created by Dell on 1/29/2016.
 */
public class PushNotificationObj {


    private String cmd;
    private String user_id;
    private String token;
    private String name;
    private String code;

    public PushNotificationObj() {
    }

    /*Initiate Class With Data*/
    public PushNotificationObj(PushNotificationObj info){
        cmd = info.getCmd();
        user_id = info.getUser_id();
        token = info.getToken();
        name = info.getName();
        code = info.getCode();
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
