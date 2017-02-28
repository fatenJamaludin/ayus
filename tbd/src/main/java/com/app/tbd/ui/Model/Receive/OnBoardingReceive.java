package com.app.tbd.ui.Model.Receive;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell on 11/3/2016.
 */

public class OnBoardingReceive {


    private String Status;
    private String Message;
    private ArrayList<String> ImageList;

    public OnBoardingReceive(OnBoardingReceive obj) {
        Status = obj.getStatus();
        Message = obj.getMessage();
        ImageList = obj.getImageList();
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<String> getImageList() {
        return ImageList;
    }

    public void setImageList(ArrayList<String> imageList) {
        ImageList = imageList;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

}
