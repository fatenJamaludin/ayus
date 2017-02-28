package com.app.tbd.ui.Model.Receive;

import java.util.List;

/**
 * Created by Dell on 11/3/2016.
 */

public class PromotionReceive {

    private String Status;
    private String Message;
    private String DefaultAirport;
    private String DefaultAirportName;
    private String BannerImage;
    private String PromotionHeader;
    private String FixedPointHeader;

    public String getLastUpdated() {
        return LastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        LastUpdated = lastUpdated;
    }

    private String LastUpdated;
    private List<PromoList> PromoList;

    public PromotionReceive(PromotionReceive obj) {
        Status = obj.getStatus();
        Message = obj.getMessage();
        PromoList = obj.getPromoList();
        DefaultAirport = obj.getDefault_Airport();
        DefaultAirportName = obj.getDefault_AirportName();
        BannerImage = obj.getBannerName();
        PromotionHeader = obj.getPromotionHeader();
        FixedPointHeader = obj.getFixedPoint();
        LastUpdated = obj.getLastUpdated();
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

    public List<PromotionReceive.PromoList> getPromoList() {
        return PromoList;
    }

    public void setPromoList(List<PromotionReceive.PromoList> promoList) {
        PromoList = promoList;
    }

    public String getBannerName() {
        return BannerImage;
    }

    public void setBannerName(String bannerName) {
        BannerImage = bannerName;
    }

    public String getFixedPoint() {
        return FixedPointHeader;
    }

    public void setFixedPoint(String fixedPoint) {
        FixedPointHeader = fixedPoint;
    }

    public String getPromotionHeader() {
        return PromotionHeader;
    }

    public void setPromotionHeader(String promotionHeader) {
        PromotionHeader = promotionHeader;
    }

    public String getDefault_AirportName() {
        return DefaultAirportName;
    }

    public void setDefault_AirportName(String default_AirportName) {
        DefaultAirportName = default_AirportName;
    }

    public String getDefault_Airport() {
        return DefaultAirport;
    }

    public void setDefault_Airport(String default_Airport) {
        DefaultAirport = default_Airport;
    }

    public class PromoList {

        private String CategoryName;
        private String CategoryCode;
        private List<Details> Details;

        public String getCategoryName() {
            return CategoryName;
        }

        public void setCategoryName(String categoryName) {
            CategoryName = categoryName;
        }

        public String getCategoryCode() {
            return CategoryCode;
        }

        public void setCategoryCode(String categoryCode) {
            CategoryCode = categoryCode;
        }

        public List<PromotionReceive.Details> getDetails() {
            return Details;
        }

        public void setDetails(List<PromotionReceive.Details> details) {
            Details = details;
        }

    }

    public class Details {

        private String Point;
        private String FlightType;
        private String TravelPeriodFrom;
        private String TravelPeriodTo;
        private String ActivationFrom;
        private String ActivationTo;
        private String Departure;
        private String Destination;
        private String BlockHours;
        private String DepartureCityCode;
        private String DestinationCityCode;

        public String getPoint() {
            return Point;
        }

        public void setPoint(String point) {
            Point = point;
        }

        public String getFlightType() {
            return FlightType;
        }

        public void setFlightType(String flightType) {
            FlightType = flightType;
        }

        public String getTravelPeriodFrom() {
            return TravelPeriodFrom;
        }

        public void setTravelPeriodFrom(String travelPeriodFrom) {
            TravelPeriodFrom = travelPeriodFrom;
        }

        public String getTravelPeriodTo() {
            return TravelPeriodTo;
        }

        public void setTravelPeriodTo(String travelPeriodTo) {
            TravelPeriodTo = travelPeriodTo;
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

        public String getDeparture() {
            return Departure;
        }

        public void setDeparture(String departure) {
            Departure = departure;
        }

        public String getDestination() {
            return Destination;
        }

        public void setDestination(String destination) {
            Destination = destination;
        }

        public String getBlockHours() {
            return BlockHours;
        }

        public void setBlockHours(String blockHours) {
            BlockHours = blockHours;
        }

        public String getImage() {
            return Image;
        }

        public void setImage(String image) {
            Image = image;
        }

        private String Image;

        public String getCountryName() {
            return CountryName;
        }

        public void setCountryName(String countryName) {
            CountryName = countryName;
        }

        private String CountryName;

        public String getDepartureCityCode() {
            return DepartureCityCode;
        }

        public void setDepartureCityCode(String departureCityCode) {
            DepartureCityCode = departureCityCode;
        }

        public String getDestinationCityCode() {
            return DestinationCityCode;
        }

        public void setDestinationCityCode(String destinationCityCode) {
            DestinationCityCode = destinationCityCode;
        }
    }
}
