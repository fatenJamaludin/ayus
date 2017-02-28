package com.app.tbd.ui.Model.Request;

public class MessageStatusRequest {

    private String token;
    private String module;
    private String messageId;
    private String messageType;

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public MessageStatusRequest() {

    }

    public MessageStatusRequest(MessageStatusRequest obj) {
        token = obj.getToken();
        module = obj.getModule();
        messageId = obj.getMessageId();
        messageType = obj.getMessageType();
    }

}
