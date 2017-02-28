package com.app.tbd.ui.Model.Receive;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell on 11/9/2015.
 */
public class InitialLoadReceive {

    private final InitialLoadReceive userObj;
    private String Status;
    private String Message;
    private List<TitleList> TitleList = new ArrayList<TitleList>();
    private List<CountryList> CountryList = new ArrayList<CountryList>();
    private List<RouteList> RouteList = new ArrayList<RouteList>();
    private List<BookingCountryStateList> BookingCountryStateList;

    public class TitleList {

        private String CultureCode;
        private String Description;
        private String GenderCode;
        private String Title;

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public String getCultureCode() {
            return CultureCode;
        }

        public void setCultureCode(String cultureCode) {
            CultureCode = cultureCode;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String description) {
            Description = description;
        }

        public String getGenderCode() {
            return GenderCode;
        }

        public void setGenderCode(String genderCode) {
            GenderCode = genderCode;
        }

    }

    public class CountryList {

        private String CountryCode;
        private String CountryName;
        private String CultureCode;
        private String DialingCode;

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

        public String getCultureCode() {
            return CultureCode;
        }

        public void setCultureCode(String cultureCode) {
            CultureCode = cultureCode;
        }

        public String getDialingCode() {
            return DialingCode;
        }

        public void setDialingCode(String dialingCode) {
            DialingCode = dialingCode;
        }

    }

    public class RouteList {

        private String DepartureCountryName;
        private String DepartureStation;
        private String DepartureStationName;
        private String DepartureStationCurrencyCode;
        private String ArrivalCountryName;
        private String ArrivalStation;
        private String ArrivalStationName;

        public String getArrivalStationCurrencyCode() {
            return ArrivalStationCurrencyCode;
        }

        public void setArrivalStationCurrencyCode(String arrivalStationCurrencyCode) {
            ArrivalStationCurrencyCode = arrivalStationCurrencyCode;
        }

        private String ArrivalStationCurrencyCode;

        public String getDepartureStationName() {
            return DepartureStationName;
        }

        public void setDepartureStationName(String departureStationName) {
            DepartureStationName = departureStationName;
        }

        public String getArrivalStationName() {
            return ArrivalStationName;
        }

        public void setArrivalStationName(String arrivalStationName) {
            ArrivalStationName = arrivalStationName;
        }

        public String getDepartureCountryName() {
            return DepartureCountryName;
        }

        public void setDepartureCountryName(String departureCountryName) {
            DepartureCountryName = departureCountryName;
        }

        public String getDepartureStation() {
            return DepartureStation;
        }

        public void setDepartureStation(String departureStation) {
            DepartureStation = departureStation;
        }

        public String getDepartureStationCurrencyCode() {
            return DepartureStationCurrencyCode;
        }

        public void setDepartureStationCurrencyCode(String departureStationCurrencyCode) {
            DepartureStationCurrencyCode = departureStationCurrencyCode;
        }

        public String getArrivalCountryName() {
            return ArrivalCountryName;
        }

        public void setArrivalCountryName(String arrivalCountryName) {
            ArrivalCountryName = arrivalCountryName;
        }

        public String getArrivalStation() {
            return ArrivalStation;
        }

        public void setArrivalStation(String arrivalStation) {
            ArrivalStation = arrivalStation;
        }

    }

    public class BookingCountryStateList {

        private String CountryCode;
        private String CountryName;
        private String StateCode;
        private String StateName;

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

        public String getStateCode() {
            return StateCode;
        }

        public void setStateCode(String stateCode) {
            StateCode = stateCode;
        }

        public String getStateName() {
            return StateName;
        }

        public void setStateName(String stateName) {
            StateName = stateName;
        }

    }

    public List<InitialLoadReceive.BookingCountryStateList> getBookingCountryStateList() {
        return BookingCountryStateList;
    }

    public void setBookingCountryStateList(List<InitialLoadReceive.BookingCountryStateList> bookingCountryStateList) {
        BookingCountryStateList = bookingCountryStateList;
    }

    public List<InitialLoadReceive.RouteList> getRouteList() {
        return RouteList;
    }

    public void setRouteList(List<InitialLoadReceive.RouteList> routeList) {
        RouteList = routeList;
    }

    public List<InitialLoadReceive.TitleList> getTitleList() {
        return TitleList;
    }

    public void setTitleList(List<InitialLoadReceive.TitleList> titleList) {
        TitleList = titleList;
    }

    public List<InitialLoadReceive.CountryList> getCountryList() {
        return CountryList;
    }

    public void setCountryList(List<InitialLoadReceive.CountryList> countryList) {
        CountryList = countryList;
    }

    public List<CountryList> getData_country() {
        return CountryList;
    }

    public void setData_country(List<CountryList> data_country) {
        this.CountryList = data_country;
    }

    public List<TitleList> getData_title() {
        return TitleList;
    }

    public void setData_title(List<TitleList> data_title) {
        this.TitleList = data_title;
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
        this.Message = message;
    }

    public InitialLoadReceive(InitialLoadReceive param_obj) {
        this.userObj = param_obj;
    }

    public InitialLoadReceive getObj() {
        return userObj;
    }

}


