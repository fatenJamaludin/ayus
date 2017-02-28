package com.app.tbd.ui.Model.Request;

import java.io.Serializable;

/**
 * Created by Dell on 11/4/2015.
 */
public class DatePickerObj implements Serializable {

    int year;
    int month;
    int day;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

}
