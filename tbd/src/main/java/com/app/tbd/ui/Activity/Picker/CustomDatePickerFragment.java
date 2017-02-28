package com.app.tbd.ui.Activity.Picker;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

import com.andexert.calendarlistview.library.DatePickerController;
import com.andexert.calendarlistview.library.DayPickerView;
import com.andexert.calendarlistview.library.SimpleMonthAdapter;
import com.app.tbd.R;
import com.app.tbd.utils.Utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import dev.dworks.libs.astickyheader.SimpleSectionedListAdapter;

public class CustomDatePickerFragment extends DialogFragment implements DatePickerController {

    @InjectView(R.id.dayPickerView)
    DayPickerView dayPickerView;

    @InjectView(R.id.btnDone)
    TextView btnDone;

    @InjectView(R.id.txtSelectionTitle)
    TextView txtSelectionTitle;

    @InjectView(R.id.backbutton)
    ImageView backbutton;

    //@InjectView(R.id.simpleCalendarView)
    //CalendarView simpleCalendarView;


    String departureDate = "", returnDate = "", departureDateSingle = "";
    Date DateDeparture, DateReturn;
    Boolean oneway = true;
    Boolean blockCalendar = false;
    String from, to;
    Date departFormatDate, departFormatDate2, departFormatDate3, departFormatDate4;
    String departDay, departDay2, departDay3, departDay4;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NORMAL, R.style.MY_DIALOG);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog d = getDialog();
        if (d != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            d.getWindow().setLayout(width, height);
        }
    }

    private ArrayList<SimpleSectionedListAdapter.Section> sections = new ArrayList<SimpleSectionedListAdapter.Section>();

    public static CustomDatePickerFragment newInstance(Boolean oneWay) {
        CustomDatePickerFragment fragment = new CustomDatePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("ONE_WAY", oneWay);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static CustomDatePickerFragment newInstance(Boolean oneWay, Boolean blockThisCalendar, String from, String to) {
        CustomDatePickerFragment fragment = new CustomDatePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("ONE_WAY", oneWay);
        bundle.putBoolean("BLOCK_CALENDAR", blockThisCalendar);
        bundle.putString("FROM", from);
        bundle.putString("TO", to);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.custom_date_picker, container, false);
        ButterKnife.inject(this, view);

        //new calendar view
        //long selectedDate = simpleCalendarView.getDate(); // get selected date in milliseconds

        //simpleCalendarView.setMinDate(1463918226920L);
        //simpleCalendarView.setMaxDate(1464918226920L);
        //simpleCalendarView.setShowWeekNumber(false);
        //simpleCalendarView.setFocusedMonthDateColor(Color.RED); // set the red color for the dates of  focused month
        //simpleCalendarView.setUnfocusedMonthDateColor(Color.BLUE); // set the yellow color for the dates of an unfocused month
        //simpleCalendarView.setSelectedWeekBackgroundColor(Color.RED); // red color for the selected week's background
        //simpleCalendarView.setWeekSeparatorLineColor(Color.GREEN); // gre

        dayPickerView.setController(this);

        txtSelectionTitle.setText(getResources().getString(R.string.select_departure_date));

        Bundle bundle = getArguments();
        oneway = bundle.getBoolean("ONE_WAY", true);

        //if need to block calendar. .use this
        try {
            blockCalendar = bundle.getBoolean("BLOCK_CALENDAR", false);
            from = bundle.getString("FROM");
            to = bundle.getString("TO");
            //use this date to block - fatin 0_0
            //kalau date yg die select luar dr range from & to ni . buat kuar utils.toast promotion range.. smntara
            Log.e(from, to);

        } catch (Exception e) {
            Log.e("No need to block", "Y");
        }

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDone();
            }
        });

        return view;
    }


    @Override
    public int getMaxYear() {

        int year = Calendar.getInstance().get(Calendar.YEAR) + 1;
        return year;

    }

    @Override
    public void onDayOfMonthSelected(int year, int month, int day) {
        Log.e("Day Selected", day + " / " + month + " / " + year);
        String varDay = "";
        String varMonth = "";
        String message_text = getString(R.string.picker_message1);
        String and = getString(R.string.picker_and);

        if (day < 10) {
            varDay = "0";
        }
        if (month < 9) {
            varMonth = "0";
        }
        departureDateSingle = year + "-" + varMonth + "" + (month + 1) + "-" + varDay + "" + day;
        Log.e("departureDateSingle", departureDateSingle);

        if (oneway) {
            if (blockCalendar) {
                if (stringToDate(departureDateSingle).before(stringToDate(from)) || stringToDate(departureDateSingle).after(stringToDate(to))) {
                    departFormatDate = stringToDate(from); //Thu Mar 23 00:00:00 GMT+08:00 2017
                    departDay = convertDate2(departFormatDate);
                    departFormatDate2 = stringToDate(to); //Thu Mar 23 00:00:00 GMT+08:00 2017
                    departDay2 = convertDate2(departFormatDate2);
                    Utils.toastNotificationLong(getActivity(), message_text + " " + departDay + " " + and + " " + departDay2 + ".");
                    btnDone.setVisibility(View.GONE);
                    dayPickerView.setSelected(false);
                    reopen();
                } else {
                    checkDone();
                }
            } else {
                checkDone();
            }

        } else {
            txtSelectionTitle.setText(getResources().getString(R.string.select_return_date));
        }
    }

    public void checkDone() {
        String d1 = "";
        String r1 = "";
        String status = "";

        if (blockCalendar) {
            status = "Y";
        } else {
            status = "N";
        }

        if (!departureDate.equals("")) {
            d1 = departureDate;

        } else {
            d1 = departureDateSingle;
        }
        if (!returnDate.equals("")) {
            r1 = returnDate;
        }
        sendResult(d1, r1, status);
        Log.e("sendResult", d1 + r1 + status);
    }

    @Override
    public void onDateRangeSelected(SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> selectedDays) {
        Log.e("Date range selected", selectedDays.getFirst().getDate() + " --> " + selectedDays.getLast().getDate());

        DateDeparture = selectedDays.getFirst().getDate();
        DateReturn = selectedDays.getLast().getDate();

        departureDate = convertDate(selectedDays.getFirst().getDate());
        returnDate = convertDate(selectedDays.getLast().getDate());

        if (!oneway) {
            if (DateReturn.before(DateDeparture)) {

                departureDate = convertDate(selectedDays.getLast().getDate());
                returnDate = convertDate(selectedDays.getFirst().getDate());

                /*Utils.toastNotification(getActivity(), "Please select return date after departure date");
                btnDone.setVisibility(View.GONE);
                dayPickerView.setSelected(false);*/
            }

            btnDone.setVisibility(View.VISIBLE);
        }

        if (blockCalendar) {
            if (DateDeparture.before(stringToDate(from)) || DateDeparture.after(stringToDate(to)) || DateReturn.after(stringToDate(to))) {
                departFormatDate3 = stringToDate(from); //Thu Mar 23 00:00:00 GMT+08:00 2017
                departDay3 = convertDate2(departFormatDate3);
                departFormatDate4 = stringToDate(to); //Thu Mar 23 00:00:00 GMT+08:00 2017
                departDay4 = convertDate2(departFormatDate4);
                String message_text = getString(R.string.picker_message1);
                String and = getString(R.string.picker_and);

                Utils.toastNotificationLong(getActivity(),message_text + " " + departDay3 + " " + and + " " + departDay4 +".");

                btnDone.setVisibility(View.GONE);
                dayPickerView.setSelected(false);
                reopen();
            }

        }
    }

    public void reopen() {
        if (getTargetFragment() == null) {
            Log.e("Get Target Fragment", "NULL");
            return;
        }
        Log.e("Reopen", "Y");
        Intent intent = new Intent();
        intent.putExtra("REOPEN", "y");

        getTargetFragment().onActivityResult(9, Activity.RESULT_OK, intent);
        dismiss();
    }


    public String convertDate(Date data) {

        // SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        SimpleDateFormat reformat = new SimpleDateFormat("yyyy-MM-dd");

        return reformat.format(data);
    }

    public String convertDate2(Date data) {

        SimpleDateFormat reformat = new SimpleDateFormat("dd/MM/yyyy");
        return reformat.format(data);
    }

    public Date stringToDate(String string) {

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateInString = string;
        Date date = null;

        try {
            date = formatter.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(date);
        System.out.println(formatter.format(date));

        return date;
    }

    private void sendResult(String departureDate, String returnDate, String status) {
        if (getTargetFragment() == null) {
            Log.e("Get Target Fragment", "NULL");
            return;
        }

        Intent intent = new Intent();
        intent.putExtra("DEPARTURE_DATE_PICKER", departureDate);
        intent.putExtra("RETURN_DATE_PICKER", returnDate);
        intent.putExtra("BLOCK_STATUS", status);

        getTargetFragment().onActivityResult(1, Activity.RESULT_OK, intent);
        dismiss();
    }
}