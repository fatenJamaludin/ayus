package com.app.tbd.ui.Model.Receive;

import java.util.List;

/**
 * Created by Dell on 4/19/2016.
 */
public class SSRReceive {

    private SSRReceive obj;
    private String status;
    private String message;
    private List<SSRMeal> meal;

    public List<SSRMeal> getMeal() {
        return meal;
    }

    public void setMeal(List<SSRMeal> meal) {
        this.meal = meal;
    }

    public String getMessage() {
        return message;
    }

    public class SSRMeal{

        private String destination_name;
        private List<SSRPassenger> passenger;
        private List<ListMeal> list_meal;
        private String flight_status;

        public String getFlight_status() {
            return flight_status;
        }

        public void setFlight_status(String flight_status) {
            this.flight_status = flight_status;
        }


        public String getDestination_name() {
            return destination_name;
        }

        public void setType(String type) {
            this.destination_name = type;
        }

        public List<SSRPassenger> getPassenger() {
            return passenger;
        }

        public void setPassenger(List<SSRPassenger> passenger) {
            this.passenger = passenger;
        }

        public List<ListMeal> getList_meal() {
            return list_meal;
        }

        public void setList_meal(List<ListMeal> list_meal) {
            this.list_meal = list_meal;
        }

        public class SSRPassenger{

            private String pasenger_number;
            private String name;
            private String meal_code;
            private String meal_name;

            public String getMeal_code() {
                return meal_code;
            }

            public void setMeal_code(String meal_code) {
                this.meal_code = meal_code;
            }

            public String getMeal_name() {
                return meal_name;
            }

            public void setMeal_name(String meal_name) {
                this.meal_name = meal_name;
            }

            public String getPasenger_number() {
                return pasenger_number;
            }

            public void setPasenger_number(String pasenger_number) {
                this.pasenger_number = pasenger_number;
            }

            public String getPassenger_name() {
                return name;
            }

            public void setPassenger_name(String passenger_name) {
                this.name = passenger_name;
            }

        }

        public class ListMeal{

            private String meal_code;
            private String name;

            public String getMeal_code() {
                return meal_code;
            }

            public void setMeal_code(String meal_code) {
                this.meal_code = meal_code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

        }
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SSRReceive(SSRReceive xx){
        status = xx.getStatus();
        message = xx.getMessage();
        meal = xx.getMeal();
        obj = xx;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SSRReceive getObj() {
        return obj;
    }

    public void setObj(SSRReceive obj) {
        this.obj = obj;
    }


}
