package com.app.tbd.ui.Model.Receive;

/**
 * Created by Dell on 3/2/2016.
 */
public class AboutUsReceive {

    private String status;
    private String message;
    private String title;
    private String data;
    public AboutUsReceive obj;

    private void AboutUsReceive(){}

    public  AboutUsReceive(AboutUsReceive xx){
        status = xx.getStatus();
        message = xx.getMessage();
        title = xx.getTitle();
        data = xx.getData();
    }

    public AboutUsReceive getObj() {
        return obj;
    }

    public void setObj(AboutUsReceive obj) {
        this.obj = obj;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
