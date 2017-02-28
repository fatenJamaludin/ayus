package com.app.tbd.ui.Activity.BookingFlight.Add;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.tbd.R;
import com.app.tbd.application.AnalyticsApplication;
import com.app.tbd.ui.Activity.BookingFlight.PassengerInfoFragment;
import com.app.tbd.ui.Model.JSON.TravellerInfo;
import com.app.tbd.ui.Model.Receive.LoadInsuranceReceive;

import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TravellerInsuranceAdapter extends BaseAdapter {

    private String departureAirport;
    private String arrivalAirport;
    private String flightClass;
    private Integer selected_position = -1;
    private String flightWay;
    private Boolean active = false;
    TravellerInfo travellerInfo;

    Activity c;
    List<LoadInsuranceReceive.PassengerInsurance> loadInsuranceReceive;


    public TravellerInsuranceAdapter(Activity context, List<LoadInsuranceReceive.PassengerInsurance> obj, TravellerInfo travellerInfo) {
        this.c = context;
        this.loadInsuranceReceive = obj;
        this.travellerInfo = travellerInfo;
    }

    @Override
    public int getCount() {

        return loadInsuranceReceive.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
        //obj == null ? null : obj.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    static class ViewHolder {

        @InjectView(R.id.txtTravellerName)
        TextView txtTravellerName;

        @InjectView(R.id.txtInsurancePts)
        TextView txtInsurancePts;
    }

    @Override
    public View getView(final int i, View view, ViewGroup parent) {

        ViewHolder vh;
        if (view == null) {
            view = LayoutInflater.from(c).inflate(R.layout.traveller_insurance_passenger, parent, false);
            vh = new ViewHolder();
            ButterKnife.inject(vh, view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }

        String point = AnalyticsApplication.getContext().getString(R.string.pts);
        String plus = "+";

        if (!travellerInfo.getList().get(i).getType().equals("Infant")){
            vh.txtTravellerName.setText(loadInsuranceReceive.get(i).getFirstName() + " " + loadInsuranceReceive.get(i).getLastName());
            vh.txtInsurancePts.setText(plus + changeThousand(loadInsuranceReceive.get(i).getPoints()) + " " + point);
        }

        return view;

    }

    public String changeThousand(String newValue) {

        int x2 = Integer.parseInt(newValue);
        String str = String.format(Locale.US,"%,d", x2);
        return str;
    }

}


