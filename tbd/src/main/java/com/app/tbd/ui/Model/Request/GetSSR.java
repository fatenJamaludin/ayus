package com.app.tbd.ui.Model.Request;

/**
 * Created by Dell on 4/19/2016.
 */
public class GetSSR {

    private String signature;

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    /*Initiate Class*/
    public GetSSR(){
    }

    public GetSSR(GetSSR data){
        signature = data.getSignature();
    }

}
