package com.app.tbd.ui.Model.Receive;

import java.util.ArrayList;
import java.util.List;

public class NewsletterLanguageReceive {

    private String Status;
    private String Message;


    private List<NewsletterLanguage> CultureList = new ArrayList<NewsletterLanguage>();

    public class NewsletterLanguage {

        private String CountryCode;
        private String CultureCode;
        private String Name;

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getCountryCode() {
            return CountryCode;
        }

        public void setCountryCode(String countryCode) {
            CountryCode = countryCode;
        }

        public String getCultureCode() {
            return CultureCode;
        }

        public void setCultureCode(String cultureCode) {
            CultureCode = cultureCode;
        }

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

    public NewsletterLanguageReceive(NewsletterLanguageReceive returnData) {

        Status = returnData.getStatus();
        Message = returnData.getMessage();
        CultureList = returnData.getCultureList();
    }

    public List<NewsletterLanguage> getCultureList() {
        return CultureList;
    }

    public void setCultureList(List<NewsletterLanguage> cultureList) {
        CultureList = cultureList;
    }

}
