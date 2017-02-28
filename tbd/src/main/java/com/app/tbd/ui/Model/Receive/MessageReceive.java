package com.app.tbd.ui.Model.Receive;

import java.util.ArrayList;
import java.util.List;

public class MessageReceive {

    private String Status;
    private List<AllMessage> AllMessage = new ArrayList<AllMessage>();

    public class AllMessage {

        private String Status;
        private String MessageID;
        private String Type;
        private String Title;
        private String Message;
        private String Body;
        private String DeliveredDate;
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return Type;
        }

        public void setType(String type) {
            Type = type;
        }

        public String getBody() {
            return Body;
        }

        public void setBody(String body) {
            Body = body;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String status) {
            Status = status;
        }

        public String getMessageID() {
            return MessageID;
        }

        public void setMessageID(String messageID) {
            MessageID = messageID;
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

        public String getDeliveredDate() {
            return DeliveredDate;
        }

        public void setDeliveredDate(String deliveredDate) {
            DeliveredDate = deliveredDate;
        }
    }

    public List<MessageReceive.AllMessage> getAllMessage() {
        return AllMessage;
    }

    public void setAllMessage(List<MessageReceive.AllMessage> allMessage) {
        AllMessage = allMessage;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public MessageReceive(MessageReceive returnData) {
        Status = returnData.getStatus();
        AllMessage = returnData.getAllMessage();
    }

}
