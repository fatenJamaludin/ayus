package com.app.tbd.ui.Model.Request;

/**
 * Created by Dell on 1/21/2016.
 */
public class ChangeFlight {


        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDeparture_station() {
            return departure_station;
        }

        public void setDeparture_station(String departure_station) {
            this.departure_station = departure_station;
        }

        public String getArrival_station() {
            return arrival_station;
        }

        public void setArrival_station(String arrival_station) {
            this.arrival_station = arrival_station;
        }

        public String getDeparture_date() {
            return departure_date;
        }

        public void setDeparture_date(String departure_date) {
            this.departure_date = departure_date;
        }

        private String status;
        private String departure_station;
        private String arrival_station;
        private String departure_date;


}
