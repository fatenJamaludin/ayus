package com.app.tbd.ui.Model;

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
import android.widget.RelativeLayout;
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
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;

//import static com.app.tbd.ui.Activity.BookingFlight.FlightListFragment.getFareType;
//import static com.app.tbd.ui.Activity.BookingFlight.FlightPriceFragment.resetAdapterFromInside;

public class FlightListAdapterV2 extends BaseAdapter {

    private final Activity context;
    private final List<AnotherList> obj;
    private Integer selected_position = -1;
    private String TYPE;
    private SharedPrefManager pref;
    private String Fare;
    List<String> boardingObj2;
    FlightPriceFragment frag;
    private SearchFlightRequest flightRequest;

    public FlightListAdapterV2(Activity context, List<AnotherList> arrayList, String defaultValue, SearchFlightRequest data) {
        this.context = context;
        this.obj = arrayList;
        this.TYPE = defaultValue;
        this.frag = frag;
        this.flightRequest = data;
        // this.boardingObj2 = boardingObj3;
        pref = new SharedPrefManager(context);

        Log.e("Recreate adapter", "Yes");
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

        @InjectView(R.id.listLayout2)
        LinearLayout listLayout2;

        @InjectView(R.id.listLayout3)
        LinearLayout listLayout3;

        @InjectView(R.id.childLayout)
        LinearLayout childLayout;

        @InjectView(R.id.infantLayout)
        LinearLayout infantLayout;

        @InjectView(R.id.adultImage)
        ImageView adultImage;

        @InjectView(R.id.txtPricePts)
        TextView txtPricePts;

        @InjectView(R.id.eachFlightBlock)
        RelativeLayout eachFlightBlock;

        ///

        @InjectView(R.id.multipleFlight)
        LinearLayout multipleFlight;

        @InjectView(R.id.txtDepartureTime2)
        TextView txtDepartureTime2;

        @InjectView(R.id.txtArrivalTime2)
        TextView txtArrivalTime2;

        @InjectView(R.id.txtDepatureStation2)
        TextView txtDepatureStation2;

        @InjectView(R.id.txtArrivalStation2)
        TextView txtArrivalStation2;

        @InjectView(R.id.txtFlightNumber2)
        TextView txtFlightNumber2;

        @InjectView(R.id.txtFlightDuration2)
        TextView txtFlightDuration2;

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
        vh.txtFlightNumber.setText(obj.get(position).getList().getSegment().get(0).getCarrierCode() + " " + obj.get(position).getList().getSegment().get(0).getFlightNumber());

        vh.selectedFlight.setTag(obj.get(position).getList().getJourneySellKey());
        vh.selectedFlight.setVisibility(View.GONE);

        String Str = obj.get(position).getList().getSegment().get(0).getTravelTime();
        String H = AnalyticsApplication.getContext().getString(R.string.hr);
        String M = AnalyticsApplication.getContext().getString(R.string.min);
        String newDate = Str.replace("h",H).replace("m", M);

        Log.e("HHMM", newDate);
        vh.txtFlightDuration.setText(newDate);


        if (obj.get(position).getList().getSegment().size() > 1) {
            //3 airport involve.

            String Str2 = obj.get(position).getList().getSegment().get(1).getTravelTime();
            String newDate2 = Str2.replace("h",H).replace("m", M);

            vh.multipleFlight.setVisibility(View.VISIBLE);

            vh.txtDepartureTime2.setText(parseStringDateToTime(obj.get(position).getList().getSegment().get(1).getSTD()));
            vh.txtArrivalTime2.setText(parseStringDateToTime(obj.get(position).getList().getSegment().get(1).getSTA()));

            vh.txtDepatureStation2.setText(obj.get(position).getList().getSegment().get(1).getDepartureStation());
            vh.txtArrivalStation2.setText(obj.get(position).getList().getSegment().get(1).getArrivalStation());

            vh.txtFlightNumber2.setText(obj.get(position).getList().getSegment().get(1).getCarrierCode() + obj.get(position).getList().getSegment().get(1).getFlightNumber());

            vh.txtFlightDuration2.setText(newDate2);
        }


        //get economy first.
        for (int x = 0; x < obj.get(position).getList().getFare().size(); x++) {
            if (obj.get(position).getList().getFare().get(x).getType().equals(TYPE)) {
                //obj.get(position).getList().setCurrentFareType(TYPE);
                vh.eachFlightBlock.setTag(TYPE);
                vh.txtAdultPrice.setText(changeDouble(obj.get(position).getList().getFare().get(x).getAdultQuotedPoints()));

                if (obj.get(position).getList().getFare().get(x).getAdultQuotedAmount() != null) {
                    vh.adultImage.setVisibility(View.VISIBLE);
                    vh.txtPricePts.setVisibility(View.VISIBLE);
                } else {
                    vh.adultImage.setVisibility(View.GONE);
                    vh.txtPricePts.setVisibility(View.GONE);
                }

                //if (obj.get(position).getList().getFare().get(x).getChildQuotedPoints() != null) {
                if (!flightRequest.getChild().equals("0")) {
                    vh.txtChildPrice.setText(changeDouble(obj.get(position).getList().getFare().get(x).getChildQuotedPoints()));
                    vh.childLayout.setVisibility(View.VISIBLE);
                } else {
                    vh.childLayout.setVisibility(View.GONE);

                }
                if (!flightRequest.getInfant().equals("0")) {
                    vh.txtInfantPrice.setText(changeDouble(obj.get(position).getList().getFare().get(x).getInfantQuotedPoints()));
                    vh.infantLayout.setVisibility(View.VISIBLE);
                } else {
                    vh.infantLayout.setVisibility(View.GONE);

                }

                String seat = obj.get(position).getList().getFare().get(x).getAvailableCount();
                int seatLeft = Integer.parseInt(seat);
                String seatSingle = String.format(AnalyticsApplication.getContext().getResources().getString(R.string.adapter_seat_left2),seat);
                String seatText = String.format(AnalyticsApplication.getContext().getResources().getString(R.string.adapter_seat_left1),seat);

                if (seatLeft < 6) {
                    vh.txtAvailSeat.setVisibility(View.VISIBLE);
                    vh.txtAvailSeat.setText(seatText);
                } else if (seatLeft == 1){
                    vh.txtAvailSeat.setVisibility(View.VISIBLE);
                    vh.txtAvailSeat.setText(seatSingle);
                } else {
                    vh.txtAvailSeat.setVisibility(View.GONE);
                }

                break;
            }
        }

        if (obj.get(position).getList().getSelected() != null) {
            if (obj.get(position).getList().getSelected().equals("Y")) {
                if (obj.get(position).getList().getSelectedFareType().equals(TYPE)) {
                    vh.selectedFlight.setVisibility(View.VISIBLE);
                    //vh.listLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.selected_flight));
                    vh.listLayout2.setBackgroundColor(ContextCompat.getColor(context, R.color.selected_flight));
                    vh.listLayout3.setBackgroundColor(ContextCompat.getColor(context, R.color.selected_flight));

                } else {
                    vh.selectedFlight.setVisibility(View.GONE);
                    //vh.listLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
                    vh.listLayout2.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
                    vh.listLayout3.setBackgroundColor(ContextCompat.getColor(context, R.color.white));

                }
            } else {
                vh.selectedFlight.setVisibility(View.GONE);
                //vh.listLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
                vh.listLayout2.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
                vh.listLayout3.setBackgroundColor(ContextCompat.getColor(context, R.color.white));

            }
        }

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

    public String changeDouble(String db) {

        String x;
        double newPts1 = Double.parseDouble((db));
        int x2 = (int) newPts1;
        x = String.format(Locale.US,"%,d", x2);

        return x;
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

    public void clearAllSelected() {

        int x = 0;
        for (AnotherList pic : obj) {
            obj.get(x).getList().setSelected("N");
            obj.get(x).getList().setSelectedFareType("NA");

            x++;
        }

        notifyDataSetChanged();

    }
}
