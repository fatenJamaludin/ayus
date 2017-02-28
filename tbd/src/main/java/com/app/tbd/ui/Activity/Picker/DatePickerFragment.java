package com.app.tbd.ui.Activity.Picker;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;

import com.app.tbd.ui.Model.Request.DatePickerObj;

import java.util.Calendar;

/**
 * Created by Dell on 11/4/2015.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {


    DatePickerObj datePickerObj;
    public static final String EXTRA_DATE = "date";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        datePickerObj = new DatePickerObj();

        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        datePickerObj.setYear(year);
        datePickerObj.setMonth(month + 1);

        Log.e("Day",Integer.toString(day));

        datePickerObj.setDay(day);

        sendResult(datePickerObj);

    }

    private void sendResult(DatePickerObj dateParam) {
        if (getTargetFragment() == null) {
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, dateParam);
        int result;
        if(getTag().equals("datepicker")){
            result = 2;
        }
        else{
            result = 4;
        }

        getTargetFragment().onActivityResult(result, Activity.RESULT_OK, intent);
        dismiss();
    }
}
