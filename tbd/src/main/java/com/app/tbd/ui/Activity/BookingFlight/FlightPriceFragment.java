package com.app.tbd.ui.Activity.BookingFlight;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.app.tbd.ui.Model.Adapter.FlightListAdapter;
import com.app.tbd.ui.Model.JSON.SelectedFlightInfo;
import com.app.tbd.ui.Model.Request.SearchFlightRequest;
import com.app.tbd.utils.SharedPrefManager;
import com.google.gson.Gson;
import com.app.tbd.R;
import com.app.tbd.base.BaseFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;

import static com.app.tbd.ui.Activity.BookingFlight.FlightListFragment.getFareType;

public class FlightPriceFragment extends BaseFragment {

    static ListView flightListPrice;
    static FlightListAdapter flightListAdapter;

    private static final String KEY_PRODUCTIMAGE = FlightPriceFragment.class.getSimpleName() + ":KEY_PRODUCTIMAGE";
    private static List<AnotherList> boardingObj;
    static SearchFlightRequest flightRequest;
    private static List<String> boardingObj2;
    private String fareType;
    private SharedPrefManager pref;
    static int currentPage;
    static FlightPriceFragment frag;
    static String selectedJourneySellKey, selectedFareKeySell;
    static SelectedFlightInfo selectedSegmentFare;

    public static FlightPriceFragment newInstance(List<String> test, List<AnotherList> listProductImages, String type, SearchFlightRequest data) {
        FlightPriceFragment fragment = new FlightPriceFragment();
        //Bundle args = new Bundle();
        //args.putString(KEY_PRODUCTIMAGE, type);
        //fragment.setArguments(args);

        boardingObj = listProductImages;
        boardingObj2 = test;
        flightRequest = data;


        Log.e("OnInstance", "True");

        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();

        fareType = args.getString(KEY_PRODUCTIMAGE);
        frag = this;
        Log.e("Oncreate", "True");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.another_flight_list, container, false);
        ButterKnife.inject(this, rootView);

        pref = new SharedPrefManager(getActivity());
        flightListPrice = (ListView) rootView.findViewById(R.id.flightListPrice);
        Log.e("Oncreate View", "True");

        return rootView;
    }

    public static String getFareKeySell() {
        return selectedFareKeySell;
    }

    public static String getSelectedJourneySellKey() {
        return selectedJourneySellKey;
    }

    public static SelectedFlightInfo getSelectedSegmentFare() {
        return selectedSegmentFare;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        flightListAdapter = new FlightListAdapter(getActivity(), FlightPriceFragment.this, boardingObj, fareType, flightRequest);
        flightListPrice.setAdapter(flightListAdapter);

        flightListPrice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AnotherList obj = (AnotherList) parent.getAdapter().getItem(position);
                String journeySellKey = obj.getList().getJourneySellKey();
                String fareKeySell = obj.getList().getFare().get(currentPage).getFareSellKey();

                selectedJourneySellKey = journeySellKey;
                selectedFareKeySell = fareKeySell;

                //Parse to next
                selectedSegmentFare = new SelectedFlightInfo();
                String durationTime = durationTime(obj.getList().getSegment().get(0).getSTD(), obj.getList().getSegment().get(0).getSTA());
                selectedSegmentFare.setSelectedFlightCarrierCode(obj.getList().getSegment().get(0).getCarrierCode() + "" + obj.getList().getSegment().get(0).getFlightNumber());
                selectedSegmentFare.setSelectedFlightDuration(durationTime);
                selectedSegmentFare.setSelectedFlightPoint(obj.getList().getFare().get(currentPage).getTotalQuotedPoints());
                selectedSegmentFare.setSelectedDepartureCode(obj.getList().getSegment().get(0).getDepartureStation());
                selectedSegmentFare.setSelectedArrivalCode(obj.getList().getSegment().get(0).getArrivalStation());

                String departCode = obj.getList().getSegment().get(0).getDepartureStation();
                String arrivalCode = obj.getList().getSegment().get(0).getArrivalStation();
                String departTime = parseStringDateToTime(obj.getList().getSegment().get(0).getSTD());
                String arrivalTime = parseStringDateToTime(obj.getList().getSegment().get(0).getSTA());
                String merge = departTime + " (" + departCode + ")" + " - " + arrivalTime + " (" + arrivalCode + ")";
                selectedSegmentFare.setSelectedFlightInfo(merge);
                //END

                currentPage = getFareType();
                String type = boardingObj2.get(currentPage);
                pref.setSelectedDepartFareKey(journeySellKey + "/" + fareKeySell);

                flightListAdapter.onSelected(position, type);

                Log.e(journeySellKey, fareKeySell);


            }
        });


    }

    public String parseStringDateToTime(String unparseDate) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat reformat = new SimpleDateFormat("HH:mm");

        try {

            Date date = formatter.parse(unparseDate);
            return reformat.format(date).toString();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String durationTime(String time1, String time2) {

        long diffMinutes = 0;
        long diffHours = 0;
        String minute = "";
        String hour = "";

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        try {
            Date date1 = formatter.parse(time1);
            Date date2 = formatter.parse(time2);
            long difference = date2.getTime() - date1.getTime();

            diffMinutes = difference / (60 * 1000) % 60;
            diffHours = difference / (60 * 60 * 1000) % 24;

        } catch (Exception e) {

        }

        if (diffMinutes != 0) {
            minute = diffMinutes + "M";
        }
        if (diffHours != 0) {
            hour = diffHours + "H";
        }
        return hour + "" + minute;

    }


    public static void recreateAdapter(Activity act, int position, SearchFlightRequest request) {

        if (position == -1) {
            position = currentPage;
        }
        String type = boardingObj2.get(position);

        flightListPrice.setAdapter(null);
        flightListAdapter.notifyDataSetChanged();

        flightListAdapter = new FlightListAdapter(act, frag, boardingObj, type, request);
        flightListPrice.setAdapter(flightListAdapter);

        Log.e(type, Integer.toString(currentPage));

    }

    public void recreateAdapterV2(List<AnotherList> arrayList) {

        String type = boardingObj2.get(currentPage);

        flightListPrice.setAdapter(null);
        flightListAdapter.notifyDataSetChanged();

        flightListAdapter = new FlightListAdapter(getActivity(), frag, arrayList, type, flightRequest);
        flightListPrice.setAdapter(flightListAdapter);


    }

   // @Override
   // public void onSaveInstanceState(Bundle outState) {
   //     super.onSaveInstanceState(outState);
        //outState.putString(KEY_PRODUCTIMAGE, (new Gson()).toJson(boardingObj));
   // }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("OnResume", "True");
    }

}
