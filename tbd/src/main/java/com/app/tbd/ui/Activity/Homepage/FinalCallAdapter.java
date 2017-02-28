package com.app.tbd.ui.Activity.Homepage;

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
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Activity.BookingFlight.AnotherList;
import com.app.tbd.ui.Activity.BookingFlight.FlightPriceFragment;
import com.app.tbd.ui.Model.JSON.Promo;
import com.app.tbd.ui.Model.Receive.SearchFlightReceive;
import com.app.tbd.ui.Model.Receive.TransactionHistoryReceive;
import com.app.tbd.ui.Model.Request.SearchFlightRequest;
import com.app.tbd.utils.SharedPrefManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

//import static com.app.tbd.ui.Activity.BookingFlight.FlightListFragment.getFareType;
//import static com.app.tbd.ui.Activity.BookingFlight.FlightPriceFragment.resetAdapterFromInside;

public class FinalCallAdapter extends BaseAdapter {

    private final Activity context;
    private final List<Promo> obj;
    private Integer selected_position = -1;
    private String TYPE;
    private SharedPrefManager pref;
    private String Fare;
    List<String> boardingObj2;
    FlightPriceFragment frag;
    private SearchFlightRequest flightRequest;

    public FinalCallAdapter(Activity context, ArrayList<Promo> finalCallList) {
        this.context = context;
        this.obj = finalCallList;

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

        @InjectView(R.id.txtDeparture)
        TextView txtDeparture;

        @InjectView(R.id.txtArrival)
        TextView txtArrival;

        @InjectView(R.id.txtPts)
        TextView txtPts;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        ViewHolder vh;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.promotion_final_call, parent, false);
            vh = new ViewHolder();
            ButterKnife.inject(vh, view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }

        String pts_text = AnalyticsApplication.getContext().getString(R.string.pts);

        vh.txtDeparture.setText(obj.get(position).getDepartureStation() /*+ " (" + obj.get(position).getDepartCode()+ ")"*/);
        vh.txtArrival.setText(obj.get(position).getArrivalStation() /*+ " (" + obj.get(position).getArrivalStationCode()+ ")"*/);
        String point = BaseFragment.changeThousand(obj.get(position).getPoint());
        vh.txtPts.setText(point + " " + pts_text);

        return view;

    }

}
