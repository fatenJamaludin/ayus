package com.app.tbd.ui.Model.Request;

/**
 * Created by Dell on 12/18/2015.
 */
public class SeatInfo {

        private String seat_type;
        private String status;
        private String seat_number;
        private String compartment_designator;

        public String getSeat_type() {
            return seat_type;
        }

        public void setSeat_type(String seat_type) {
            this.seat_type = seat_type;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getSeat_number() {
            return seat_number;
        }

        public void setSeat_number(String seat_number) {
            this.seat_number = seat_number;
        }

        public String getCompartment_designator() {
            return compartment_designator;
        }

        public void setCompartment_designator(String compartment_designator) {
            this.compartment_designator = compartment_designator;
        }

}
