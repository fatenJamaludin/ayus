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
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andexert.calendarlistview.library.DatePickerController;
import com.andexert.calendarlistview.library.DayPickerView;
import com.andexert.calendarlistview.library.SimpleMonthAdapter;
import com.app.tbd.R;
import com.app.tbd.utils.Utils;
import com.squareup.timessquare.CalendarPickerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;
import dev.dworks.libs.astickyheader.SimpleSectionedListAdapter;

import static android.R.attr.format;
import static com.squareup.timessquare.CalendarPickerView.SelectionMode.RANGE;
import static com.squareup.timessquare.CalendarPickerView.SelectionMode.SINGLE;

public class NewDatePickerFragment extends DialogFragment {

    @InjectView(R.id.dayPickerView)
    CalendarPickerView dayPickerView;

    @InjectView(R.id.btnSearch)
    Button btnDone;

    @InjectView(R.id.txtSelectionTitle)
    TextView txtSelectionTitle;

    @InjectView(R.id.backbutton)
    ImageView backbutton;

    @InjectView(R.id.departureDateCalendarBlock)
    LinearLayout departureDateCalendarBlock;

    @InjectView(R.id.returnDateCalendarBlock)
    LinearLayout returnDateCalendarBlock;

    @InjectView(R.id.txtDepartDate)
    TextView txtDepartDate;

    @InjectView(R.id.txtReturnDate)
    TextView txtReturnDate;

    @InjectView(R.id.btnSearch)
    Button btnSearch;

    String departureDate = "", returnDate = "", departureDateSingle = "";
    Date singleDate;
    Date DateDeparture, DateReturn;
    //Boolean oneway = true;
    Boolean blockCalendar = false;
    String from, to;
    Date departFormatDate, departFormatDate2, departFormatDate3, departFormatDate4;
    String departDay, departDay2, departDay3, departDay4;
    int click;
    Date fromDate, toDate;
    Boolean oneWay;

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

    public static NewDatePickerFragment newInstance(Boolean oneWay) {
        NewDatePickerFragment fragment = new NewDatePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("ONE_WAY", oneWay);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static NewDatePickerFragment newInstance(Boolean oneWay, Boolean blockThisCalendar, String from, String to) {
        NewDatePickerFragment fragment = new NewDatePickerFragment();
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

        Bundle bundle = getArguments();
        //oneway = bundle.getBoolean("ONE_WAY", true);

        //if need to block calendar. .use this
        //if need to block calendar. .use this
        try {
            blockCalendar = bundle.getBoolean("BLOCK_CALENDAR", false);
            from = bundle.getString("FROM");
            to = bundle.getString("TO");

            fromDate = convertToDate(from);
            toDate = convertToDate(to);
            //use this date to block
            //kalau date yg die select luar dr range from & to ni . buat kuar utils.toast promotion range.. smntara
            Log.e(from, to);

        } catch (Exception e) {
            Log.e("No need to block", "Y");
        }

        Date today = new Date();
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);

        if (blockCalendar) {
            Log.e("BLOCK", "Y");
            departureDateCalendarBlock.setLayoutParams(param);
            /*if (oneway) {
                dayPickerView.init(fromDate, toDate).inMode(SINGLE);
                returnDateCalendarBlock.setVisibility(View.GONE);
            } else {
                dayPickerView.init(fromDate, toDate).inMode(RANGE);
            }*/
            dayPickerView.init(fromDate, toDate).inMode(RANGE);

        } else {
            //dayPickerView.init(fromDate, toDate).inMode(RANGE);
            dayPickerView.init(today, nextYear.getTime()).inMode(RANGE);
            //dayPickerView.init(today, nextYear.getTime());

            /*if (oneway) {
                dayPickerView.init(today, nextYear.getTime()).inMode(SINGLE);
                departureDateCalendarBlock.setLayoutParams(param);
                returnDateCalendarBlock.setVisibility(View.GONE);
            } else {
                dayPickerView.init(today, nextYear.getTime()).inMode(RANGE);
            }*/
        }

        txtSelectionTitle.setText(getResources().getString(R.string.select_departure_date));

        //if one way - one click only
        /*if (oneway) {
            dayPickerView.setCellClickInterceptor(new CalendarPickerView.CellClickInterceptor() {
                @Override
                public boolean onCellClicked(Date date) {

                    singleDate = date;
                    txtDepartDate.setVisibility(View.VISIBLE);
                    txtDepartDate.setText(convertDate(date));
                    Log.e("Date1", date.toString());
                    //checkSingleDone();
                    return true;
                }
            });
        } else {*/
        click = 0;
        dayPickerView.setCellClickInterceptor(new CalendarPickerView.CellClickInterceptor() {
            @Override
            public boolean onCellClicked(Date date) {

                //single
                try {
                    Log.e("Dates size 1", dayPickerView.getSelectedDate().toString());
                    Log.e("Dates size 11", dayPickerView.getSelectedItem().toString());

                } catch (Exception e) {

                }

                //multiple
                try {
                    Log.e("Dates size 2", Integer.toString(dayPickerView.getSelectedDates().size()));
                } catch (Exception e) {

                }

                btnSearch.setVisibility(View.VISIBLE);
                if (dayPickerView.getSelectedDates().size() == 1) {
                    txtReturnDate.setText(convertDate(date));
                    txtReturnDate.setVisibility(View.VISIBLE);
                    //txtReturnDate.setVisibility(View.INVISIBLE);
                    btnSearch.setText("Search");
                    oneWay = false;
                }
                //txtReturnDate.setText(convertDate(dayPickerView.getSelectedDates().get(dayPickerView.getSelectedDates().size() - 1)));

                if (dayPickerView.getSelectedDates().size() == 0) {
                    txtDepartDate.setText(convertDate(date));
                    txtDepartDate.setVisibility(View.VISIBLE);
                    txtReturnDate.setVisibility(View.INVISIBLE);
                    btnSearch.setText("Im flying one way");
                    oneWay = true;
                }

                if (dayPickerView.getSelectedDates().size() > 1) {
                    txtDepartDate.setText(convertDate(date));
                    txtDepartDate.setVisibility(View.VISIBLE);
                    txtReturnDate.setVisibility(View.INVISIBLE);
                    btnSearch.setText("Im flying one way");
                    oneWay = true;
                }

                return false;
            }
        });
        //dayPickerView.setOnDateSelectedListener();
        //}


        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (oneWay) {
                    checkSingleDone();
                } else {
                    checkReturnDone();
                }

            }
        });

        return view;
    }

    public Date convertToDate(String unFormatDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date convertedDate = null;
        try {
            convertedDate = formatter.parse(unFormatDate);
        } catch (Exception e) {

        }
        return convertedDate;

    }

    public void checkSingleDone() {

        departureDate = convertDate(singleDate);
        returnDate = "";

        checkDone();

        Log.e("D1" + departureDate, "X");
    }


    public void checkReturnDone() {

        Log.e("Both Date", dayPickerView.getSelectedDates().toString());

        departureDate = convertDate(dayPickerView.getSelectedDates().get(0));
        returnDate = convertDate(dayPickerView.getSelectedDates().get(dayPickerView.getSelectedDates().size() - 1));

        checkDone();

        Log.e("D1" + departureDate, "D2" + returnDate);
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

    /*@Override
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

                Utils.toastNotificationLong(getActivity(), message_text + " " + departDay3 + " " + and + " " + departDay4 + ".");

                btnDone.setVisibility(View.GONE);
                dayPickerView.setSelected(false);
                reopen();
            }
        }
    }*/

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