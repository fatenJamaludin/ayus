package com.app.tbd.ui.Model.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.tbd.R;
import com.app.tbd.application.AnalyticsApplication;
import com.app.tbd.ui.Activity.BookingFlight.AnotherList;
import com.app.tbd.ui.Activity.BookingFlight.FlightPriceFragment;
import com.app.tbd.ui.Model.Receive.SearchFlightReceive;
import com.app.tbd.ui.Model.Receive.TransactionHistoryReceive;
import com.app.tbd.ui.Model.Request.SearchFlightRequest;
import com.app.tbd.utils.SharedPrefManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

//import static com.app.tbd.ui.Activity.BookingFlight.FlightListFragment.getFareType;
//import static com.app.tbd.ui.Activity.BookingFlight.FlightPriceFragment.resetAdapterFromInside;

public class FlightListAdapter extends BaseAdapter {

    private final Activity context;
    private final List<AnotherList> obj;
    private Integer selected_position = -1;
    private String TYPE;
    private SharedPrefManager pref;
    private String Fare;
    List<String> boardingObj2;
    FlightPriceFragment frag;
    private SearchFlightRequest flightRequest;

    public FlightListAdapter(Activity context, FlightPriceFragment frag, List<AnotherList> arrayList, String defaultValue, SearchFlightRequest data) {
        this.context = context;
        this.obj = arrayList;
        this.TYPE = defaultValue;
        this.frag = frag;
        this.flightRequest = data;
        // this.boardingObj2 = boardingObj3;
        pref = new SharedPrefManager(context);
    }

    @Override
    public int getCount() {
        return obj == null ? 0 : obj.size();
    }

    @Override
    public Object getItem(int position) {
        return obj == null ? null : obj.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    static class ViewHolder {

        @InjectView(R.id.txtDepartureTime)
        TextView txtDepartureTime;

        @InjectView(R.id.txtArrivalTime)
        TextView txtArrivalTime;

        @InjectView(R.id.txtDepatureStation)
        TextView txtDepatureStation;

        @InjectView(R.id.txtArrivalStation)
        TextView txtArrivalStation;

        @InjectView(R.id.txtFlightNumber)
        TextView txtFlightNumber;

        @InjectView(R.id.txtChildPrice)
        TextView txtChildPrice;

        @InjectView(R.id.txtAdultPrice)
        TextView txtAdultPrice;

        @InjectView(R.id.txtInfantPrice)
        TextView txtInfantPrice;

        @InjectView(R.id.txtFlightDuration)
        TextView txtFlightDuration;

        @InjectView(R.id.selectedFlight)
        ImageView selectedFlight;

        @InjectView(R.id.txtAvailSeat)
        TextView txtAvailSeat;

        @InjectView(R.id.listLayout)
        LinearLayout listLayout;

        @InjectView(R.id.childLayout)
        LinearLayout childLayout;

        @InjectView(R.id.infantLayout)
        LinearLayout infantLayout;

    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        ViewHolder vh;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.flight_detail_list, parent, false);
            vh = new ViewHolder();
            ButterKnife.inject(vh, view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }

        vh.txtDepartureTime.setText(parseStringDateToTime(obj.get(position).getList().getSegment().get(0).getSTD()));
        vh.txtArrivalTime.setText(parseStringDateToTime(obj.get(position).getList().getSegment().get(0).getSTA()));

        vh.txtDepatureStation.setText(obj.get(position).getList().getSegment().get(0).getDepartureStation());
        vh.txtArrivalStation.setText(obj.get(position).getList().getSegment().get(0).getArrivalStation());
        vh.txtFlightNumber.setText(obj.get(position).getList().getSegment().get(0).getCarrierCode() + obj.get(position).getList().getSegment().get(0).getFlightNumber());

        vh.selectedFlight.setTag(obj.get(position).getList().getJourneySellKey());
        vh.selectedFlight.setVisibility(View.GONE);

        String durationTime = durationTime(obj.get(position).getList().getSegment().get(0).getSTD(), obj.get(position).getList().getSegment().get(0).getSTA());
        vh.txtFlightDuration.setText(durationTime);

        //get economy first.
        for (int x = 0; x < obj.get(position).getList().getFare().size(); x++) {

            String seatLeft = obj.get(position).getList().getFare().get(x).getAvailableCount();
            int seat = Integer.parseInt(seatLeft);
            String pts_text = AnalyticsApplication.getContext().getString(R.string.pts);
            /*String seat_text = AnalyticsApplication.getContext().getString(R.string.adapter_seat_left);*/

            if (obj.get(position).getList().getFare().get(x).getType().equals(TYPE)) {
                //obj.get(position).getList().setCurrentFareType(TYPE);
                vh.txtAdultPrice.setText(obj.get(position).getList().getFare().get(x).getAdultQuotedPoints() + pts_text);

                //if (obj.get(position).getList().getFare().get(x).getChildQuotedPoints() != null) {
                if (!flightRequest.getChild().equals("0")) {
                    Log.e("Child", "Z" + flightRequest.getChild());
                    vh.txtChildPrice.setText(obj.get(position).getList().getFare().get(x).getChildQuotedPoints() + pts_text);
                    vh.childLayout.setVisibility(View.VISIBLE);

                } else {
                    vh.childLayout.setVisibility(View.GONE);

                }
                //if (obj.get(position).getList().getFare().get(x).getInfantQuotedPoints() != null) {
                if (!flightRequest.getInfant().equals("0")) {
                    vh.txtInfantPrice.setText(obj.get(position).getList().getFare().get(x).getInfantQuotedPoints() + pts_text);
                    vh.infantLayout.setVisibility(View.VISIBLE);
                } else {
                    vh.infantLayout.setVisibility(View.GONE);
                }

                /*if (seat <6){
                    vh.txtAvailSeat.setText(seatLeft + seat_text);
                }*/


                /*String seat = obj.get(position).getList().getFare().get(x).getAvailableCount();
                int seatLeft = Integer.parseInt(seat);*/
                String seatSingle = String.format(AnalyticsApplication.getContext().getResources().getString(R.string.adapter_seat_left2),seatLeft);
                String seatText = String.format(AnalyticsApplication.getContext().getResources().getString(R.string.adapter_seat_left1),seatLeft);

                if (seat < 6) {
                    vh.txtAvailSeat.setVisibility(View.VISIBLE);
                    vh.txtAvailSeat.setText(seatText);
                } else if (seat == 1){
                    vh.txtAvailSeat.setVisibility(View.VISIBLE);
                    vh.txtAvailSeat.setText(seatSingle);
                } else {
                    vh.txtAvailSeat.setVisibility(View.GONE);
                }

                break;
            }
        }


        Log.e(obj.get(position).getList().getSelected(), "?X?X?X");

        if (obj.get(position).getList().getSelected() != null) {
            if (obj.get(position).getList().getSelected().equals("Y")) {
                vh.selectedFlight.setVisibility(View.VISIBLE);
                vh.listLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.primary_green));
                Log.e("v", "v");
            } else {
                vh.selectedFlight.setVisibility(View.GONE);
                vh.listLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
                Log.e("n", "n");
            }
        }

      /*  HashMap<String, String> initPassword = pref.getSelectedDepartKey();
        String departKey = initPassword.get(SharedPrefManager.SELECTED_DEPART_FARE_KEY);

        if (vh.selectedFlight.getTag().toString().equals(departKey)) {
            vh.selectedFlight.setVisibility(View.VISIBLE);
        } else {
            vh.selectedFlight.setVisibility(View.GONE);
        }
        Log.e(departKey, "departKey");
        */

// && obj.get(position).getList().getSelectedFareType().equals(obj.get(position).getList().getJourneySellKey()
        /*CheckBox*/
        /*if (obj.get(position).getList().getSelected() != null) {
            Log.e("Not Null", "True");
            if (obj.get(position).getList().getSelected().equals("Y")) {

                //check what type now.. TYPE not working properly.
                //take from outside
                //int currentPage = getFareType();
                //String type = boardingObj2.get(currentPage);

                //Log.e("Current Type", type);
                //Log.e("Selected Type", obj.get(position).getList().getSelectedFareType());

                if (obj.get(position).getList().getSelectedFareType().equals(type)) {
                    vh.selectedFlight.setVisibility(View.VISIBLE);
                } else {
                    vh.selectedFlight.setVisibility(View.GONE);
                }

            } else {
                vh.selectedFlight.setVisibility(View.GONE);
            }
        } else {
            vh.selectedFlight.setVisibility(View.GONE);
        }*/

        return view;

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
            minute = diffMinutes + "m";
        }
        if (diffHours != 0) {
            hour = diffHours + "h";
        }
        return hour + "" + minute;

    }


    public String parseStringDateToTime(String unparseDate) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat reformat = new SimpleDateFormat("HHmm");

        try {

            Date date = formatter.parse(unparseDate);
            return reformat.format(date).toString();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void onSelected(int position, String fare) {

        int x = 0;
        for (AnotherList pic : obj) {
            obj.get(x).getList().setSelected("N");
            obj.get(x).getList().setSelectedFareType("NA");

            x++;
            Log.e("x", "X");
        }

        //Log.e("OnSelectedFare", fare);

        obj.get(position).getList().setSelected("Y");
        obj.get(position).getList().setSelectedFareType(fare);
        //Log.e("Selected","ok");
        //resetAdapterFromInside(context);
        notifyDataSetChanged();
        //frag.recreateAdapterV2(obj);

    }
}
