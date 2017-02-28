package com.app.tbd.ui.Model.Receive;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell on 8/26/2016.
 */
public class StateReceive {


    private String Message;
    private String Status;
    private List<StateList> StateList = new ArrayList<StateList>();

    public List<StateReceive.StateList> getStateList() {
        return StateList;
    }

    public void setStateList(List<StateReceive.StateList> stateList) {
        StateList = stateList;
    }


    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public class StateList {

        private String CountryCode;
        private String CultureCode;
        private String ProvinceStateCode;
        private String ProvinceStateName;

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

        public String getProvinceStateCode() {
            return ProvinceStateCode;
        }

        public void setProvinceStateCode(String provinceStateCode) {
            ProvinceStateCode = provinceStateCode;
        }

        public String getProvinceStateName() {
            return ProvinceStateName;
        }

        public void setProvinceStateName(String provinceStateName) {
            ProvinceStateName = provinceStateName;
        }

    }

    public StateReceive(StateReceive returnData) {
        Message = returnData.getMessage();
        Status = returnData.getStatus();
        StateList = returnData.getStateList();
    }

}
