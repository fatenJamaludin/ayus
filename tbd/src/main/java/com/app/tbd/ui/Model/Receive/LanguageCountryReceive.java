package com.app.tbd.ui.Model.Receive;

import java.util.List;

public class LanguageCountryReceive {

    private String Status;
    private String Message;
    private List<CountryList> CountryList;


    public LanguageCountryReceive() {
    }

    public LanguageCountryReceive(LanguageCountryReceive returnData) {
        Status = returnData.getStatus();
        CountryList = returnData.getCountryList();
        Message = returnData.getMessage();
    }

    public List<CountryList> getCountryList() {
        return CountryList;
    }

    public void setCountryList(List<LanguageCountryReceive.CountryList> countryList) {
        CountryList = countryList;
    }


    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }


    public class CountryList {

        private String CountryName;
        private String CountryCode;
        private List<LanguageList> LanguageList;

        public List<LanguageCountryReceive.CountryList.LanguageList> getLanguageList() {
            return LanguageList;
        }

        public void setLanguageList(List<LanguageCountryReceive.CountryList.LanguageList> languageList) {
            LanguageList = languageList;
        }

        public String getCountryCode() {
            return CountryCode;
        }

        public void setCountryCode(String countryCode) {
            CountryCode = countryCode;
        }

        public String getCountryName() {
            return CountryName;
        }

        public void setCountryName(String countryName) {
            CountryName = countryName;
        }

        public class LanguageList{

            private String LanguageCode;
            private String LanguageName;

            public String getLanguageCode() {
                return LanguageCode;
            }

            public void setLanguageCode(String languageCode) {
                LanguageCode = languageCode;
            }

            public String getLanguageName() {
                return LanguageName;
            }

            public void setLanguageName(String languageName) {
                LanguageName = languageName;
            }

        }
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}