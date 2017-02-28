package com.app.tbd.ui.Model.Receive;

/**
 * Created by Dell on 3/2/2016.
 */
public class ContentReceive {


    public String getWhichContent() {
        return WhichContent;
    }

    public void setWhichContent(String whichContent) {
        WhichContent = whichContent;
    }

    private String WhichContent;
    private String Message;

    public String getStatus() {
        return Status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public ContentReceive(ContentReceive obj){
        Status = obj.getStatus();
        Content = obj.getContent();
        Message = obj.getMessage();
        WhichContent = obj.getWhichContent();
    }

    private String Status;
    private String Content;

    /*private String status;
    private String message;
    private String title;
    private String data;
    public ContentReceive obj;

    private void ContentReceive(){}

    public  ContentReceive(ContentReceive xx){
        status = xx.getStatus();
        message = xx.getMessage();
        title = xx.getTitle();
        data = xx.getData();
    }

    public ContentReceive getObj() {
        return obj;
    }

    public void setObj(ContentReceive obj) {
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
    }*/

}
