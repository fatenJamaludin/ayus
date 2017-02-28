package com.app.tbd.ui.Model.Request;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell on 12/18/2015.
 */
public class SeatSetup {

    private String seatRow;
    private List<SeatInfo> seatRowArray = new ArrayList<SeatInfo>();

   // private ArrayList<SeatNumberByRow> seatNumberByRow = new ArrayList<SeatNumberByRow>();

        public String getSeatRow() {
        return seatRow;
    }

    public void setSeatRow(String seatRow) {
        this.seatRow = seatRow;
    }

    public List<SeatInfo> getSeatRowArray() {
        return seatRowArray;
    }

    public void setSeatRowArray(ArrayList<SeatInfo> seatRowArray) {
        this.seatRowArray = seatRowArray;
    }

   // public List<SeatNumberByRow> getSeatNumberByRow() {
    //    return seatNumberByRow;
   // }

   // public void setSeatNumberByRow(ArrayList<SeatNumberByRow> seatNumberByRow) {
   //     this.seatNumberByRow = seatNumberByRow;
   // }


   /* public class SeatNumberByRow{

        private String seatNumberB;

        public String getSeatNumberB() {
            return seatNumberB;
        }

        public void setSeatNumberB(String seatNumberB) {
            this.seatNumberB = seatNumberB;
        }

    }*/



}
