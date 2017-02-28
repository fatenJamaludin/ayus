package com.app.tbd.ui.Model.Request;

/**
 * Created by Dell on 11/27/2016.
 */

public class AppVersionRequest {


    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }

    public String getDevice() {
        return Device;
    }

    public void setDevice(String device) {
        Device = device;
    }

    private String Version;
    private String Device;


    public AppVersionRequest(AppVersionRequest appVersion) {
        Version = appVersion.getVersion();
        Device = appVersion.getDevice();
    }

    public AppVersionRequest() {

    }
}
