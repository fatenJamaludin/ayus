package com.app.tbd.ui.Activity.CustomDateAdapter;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class CustomAdapter implements Serializable {
    private static final long serialVersionUID = -5456695978688356202L;
    private Calendar calendar;

    int day;
    int month;
    int year;

    public CustomAdapter() {
        setTime(System.currentTimeMillis());
    }

    public CustomAdapter(int year, int month, int day) {
        setDay(year, month, day);
    }

    public CustomAdapter(long timeInMillis) {
        setTime(timeInMillis);
    }

    public CustomAdapter(Calendar calendar) {
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    private void setTime(long timeInMillis) {
        if (calendar == null) {
            calendar = Calendar.getInstance();
        }
        calendar.setTimeInMillis(timeInMillis);
        month = this.calendar.get(Calendar.MONTH);
        year = this.calendar.get(Calendar.YEAR);
        day = this.calendar.get(Calendar.DAY_OF_MONTH);
    }

    public void set(CustomAdapter calendarDay) {
        year = calendarDay.year;
        month = calendarDay.month;
        day = calendarDay.day;
    }

    public void setDay(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public Date getDate() {
        if (calendar == null) {
            calendar = Calendar.getInstance();
        }
        calendar.set(year, month, day);
        return calendar.getTime();
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{ year: ");
        stringBuilder.append(year);
        stringBuilder.append(", month: ");
        stringBuilder.append(month);
        stringBuilder.append(", day: ");
        stringBuilder.append(day);
        stringBuilder.append(" }");

        return stringBuilder.toString();
    }
}