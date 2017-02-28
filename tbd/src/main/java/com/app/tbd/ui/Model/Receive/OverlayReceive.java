package com.app.tbd.ui.Model.Receive;

import java.util.ArrayList;

/**
 * Created by Dell on 2/13/2017.
 */

public class OverlayReceive {

    private String Status;
    private String Message;
    private Overlay Overlay;

    public OverlayReceive.Overlay getOverlay() {
        return Overlay;
    }

    public void setOverlay(OverlayReceive.Overlay overlay) {
        Overlay = overlay;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }


    public OverlayReceive(OverlayReceive obj) {
        Status = obj.getStatus();
        Message = obj.getMessage();
        Overlay = obj.getOverlay();
    }

    public class Overlay {

        private String Title;
        private String Message;
        private String ActivationFrom;
        private String ActivationTo;
        private String RepeatTime;
        private String Image;
        private String Overlay_Status;

        public String getOverlay_Status() {
            return Overlay_Status;
        }

        public void setOverlay_Status(String overlay_Status) {
            Overlay_Status = overlay_Status;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public String getMessage() {
            return Message;
        }

        public void setMessage(String message) {
            Message = message;
        }

        public String getActivationFrom() {
            return ActivationFrom;
        }

        public void setActivationFrom(String activationFrom) {
            ActivationFrom = activationFrom;
        }

        public String getActivationTo() {
            return ActivationTo;
        }

        public void setActivationTo(String activationTo) {
            ActivationTo = activationTo;
        }

        public String getRepeatTime() {
            return RepeatTime;
        }

        public void setRepeatTime(String repeatTime) {
            RepeatTime = repeatTime;
        }

        public String getImage() {
            return Image;
        }

        public void setImage(String image) {
            Image = image;
        }

    }

}
