package com.app.tbd.ui.Model.Receive;

import java.util.List;

public class LanguageReceive {
    private String Status;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    private String Message;

    public List<LanguageReceive.LanguageList> getLanguageList() {
        return LanguageList;
    }

    public void setLanguageList(List<LanguageReceive.LanguageList> languageList) {
        LanguageList = languageList;
    }

    private List<LanguageList> LanguageList;

    public LanguageReceive(LanguageReceive returnData) {
        Status = returnData.getStatus();
        LanguageList = returnData.getLanguageList();
        Message = returnData.getMessage();
    }

    public class LanguageList{
        private String LanguageCode;
        private String LanguageName;

        public String getLanguageCode() {
            return LanguageCode;
        }

        public void setLanguageCode(String LanguageCode) {
            this.LanguageCode = LanguageCode;
        }

        public String getLanguageName() {
            return LanguageName;
        }

        public void setLanguageName(String LanguageName) {
            this.LanguageName = LanguageName;
        }
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}