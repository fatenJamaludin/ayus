package com.app.tbd.ui.Model.Request;

/**
 * Created by Dell on 1/20/2016.
 */
public class SendItinenaryObj {


    private String signature;
    private String pnr;
    private String bookind_Id;

    public SendItinenaryObj(){

    }

    public SendItinenaryObj(SendItinenaryObj data){
        bookind_Id = data.getBookind_Id();
        signature = data.getSignature();
        pnr = data.getPnr();
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getPnr() {
        return pnr;
    }

    public void setPnr(String pnr) {
        this.pnr = pnr;
    }

    public String getBookind_Id() {
        return bookind_Id;
    }

    public void setBookind_Id(String bookind_Id) {
        this.bookind_Id = bookind_Id;
    }

}
