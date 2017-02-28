package com.app.tbd.ui.Activity.BookingFlight.Add;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
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
import com.app.tbd.ui.Activity.BookingFlight.PassengerInfoFragment;
import com.app.tbd.ui.Model.JSON.PerRowObj;
import com.app.tbd.ui.Model.JSON.TravellerInfo;
import com.app.tbd.ui.Model.JSON.TravellerSeat;
import com.app.tbd.ui.Model.Receive.SeatInfoReceive;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static android.view.View.GONE;

public class PassengerSeatListAdapter extends BaseAdapter {

    private final Activity context;
    private TravellerInfo travellerInfo;
    private ArrayList<ArrayList<TravellerSeat>> travellerSeatsArray;
    private String seatDesignator;
    private String seatPts, compartment;
    SeatTabFragment frag;
    int segment;
    int y, x;
    String whichAdapter;

    public PassengerSeatListAdapter(Activity context, String whichAdapter, int segment, SeatTabFragment frag, ArrayList<ArrayList<TravellerSeat>> travellerSeatsArray, TravellerInfo travellerInfo, String seatDesignator, String seatPts, String compartment, int y, int x) {
        this.context = context;
        this.travellerInfo = travellerInfo;
        this.travellerSeatsArray = travellerSeatsArray;
        this.seatDesignator = seatDesignator;
        this.seatPts = seatPts;
        this.frag = frag;
        this.segment = segment;
        this.y = y;
        this.x = x;
        this.compartment = compartment;
        this.whichAdapter = whichAdapter;
    }

    @Override
    public int getCount() {

        Log.e("travellerSeatsArray", Integer.toString(travellerSeatsArray.size()));
        return travellerInfo.getList().size();

    }

    @Override
    public Object getItem(int position) {
        //return null;
        return travellerSeatsArray.get(segment).get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    static class ViewHolder {

        @InjectView(R.id.txtName)
        TextView txtName;

        @InjectView(R.id.imgChecked)
        ImageView imgChecked;

    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        final ViewHolder vh;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.pasenger_seat_list, parent, false);
            vh = new ViewHolder();
            ButterKnife.inject(vh, view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }

        Boolean checked = false;
        vh.txtName.setText(travellerSeatsArray.get(segment).get(position).getTravellerName());

        if (travellerSeatsArray.get(segment).get(position).getTravellerSeatDesignator().isEmpty()) {
            //vh.imgChecked.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_blank_circle));
            Glide.with(context).load(R.drawable.icon_blank_circle).dontAnimate().into(vh.imgChecked);
            travellerSeatsArray.get(segment).get(position).setAction("Assign");
        } else if (!travellerSeatsArray.get(segment).get(position).getTravellerSeatDesignator().isEmpty() && seatDesignator.equals(travellerSeatsArray.get(segment).get(position).getTravellerSeatDesignator())) {

            Glide.with(context).load(R.drawable.icon_red_x).dontAnimate().into(vh.imgChecked);
            //vh.imgChecked.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_red_x));

            travellerSeatsArray.get(segment).get(position).setAction("Remove");
        } else if (!travellerSeatsArray.get(segment).get(position).getTravellerSeatDesignator().isEmpty() && !seatDesignator.equals(travellerSeatsArray.get(segment).get(position).getTravellerSeatDesignator())) {
            //vh.imgChecked.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_green_tick));
            Glide.with(context).load(R.drawable.icon_green_tick).dontAnimate().into(vh.imgChecked);
            travellerSeatsArray.get(segment).get(position).setAction("Reassign");
        }

        vh.imgChecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkSelected();

                if (travellerSeatsArray.get(segment).get(position).getAction().equals("Assign")) {
                    travellerSeatsArray.get(segment).get(position).setTravellerSeatDesignator(seatDesignator);
                    travellerSeatsArray.get(segment).get(position).setTravellerSeatCompartment(compartment);
                    travellerSeatsArray.get(segment).get(position).setSeatPts(seatPts);
                    //vh.imgChecked.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_green_tick));
                    Glide.with(context).load(R.drawable.icon_green_tick).dontAnimate().into(vh.imgChecked);
                } else if (travellerSeatsArray.get(segment).get(position).getAction().equals("Remove")) {
                    Glide.with(context).load(R.drawable.icon_blank_circle).dontAnimate().into(vh.imgChecked);
                    //vh.imgChecked.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_blank_circle));
                    travellerSeatsArray.get(segment).get(position).setTravellerSeatDesignator("");
                    travellerSeatsArray.get(segment).get(position).setSeatPts("");
                    travellerSeatsArray.get(segment).get(position).setTravellerSeatCompartment("");
                } else if (travellerSeatsArray.get(segment).get(position).getAction().equals("Reassign")) {
                    travellerSeatsArray.get(segment).get(position).setTravellerSeatDesignator(seatDesignator);
                    travellerSeatsArray.get(segment).get(position).setSeatPts(seatPts);
                    travellerSeatsArray.get(segment).get(position).setTravellerSeatCompartment(compartment);
                    //vh.imgChecked.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_green_tick));
                    Glide.with(context).load(R.drawable.icon_green_tick).dontAnimate().into(vh.imgChecked);


                    //check if already selected
                }

                notifyDataSetChanged();
                frag.notifySeatAdapter(segment, whichAdapter, y, x, position, travellerSeatsArray.get(segment).get(position).getAction(), travellerSeatsArray.get(segment).get(position).getTravellerNameShortcut());
                frag.closeThisList();

            }
        });

        return view;

    }

    public void checkSelected() {
        for (int b = 0; b < travellerSeatsArray.get(segment).size(); b++) {
            if (travellerSeatsArray.get(segment).get(b).getTravellerSeatDesignator().equals(seatDesignator)) {
                travellerSeatsArray.get(segment).get(b).setTravellerSeatDesignator("");
                travellerSeatsArray.get(segment).get(b).setTravellerSeatCompartment("");
                travellerSeatsArray.get(segment).get(b).setSeatPts("");
                break;
            }
        }

        notifyDataSetChanged();
    }

    public ArrayList<ArrayList<TravellerSeat>> getAdapterObj() {
        return travellerSeatsArray;
    }

}
