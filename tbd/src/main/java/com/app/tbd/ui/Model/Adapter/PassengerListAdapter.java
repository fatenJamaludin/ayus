package com.app.tbd.ui.Model.Adapter;

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
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Activity.BookingFlight.PassengerInfoFragment;
import com.app.tbd.ui.Model.JSON.Traveller;
import com.app.tbd.ui.Model.JSON.TravellerInfo;
import com.app.tbd.utils.SharedPrefManager;
//import com.estimote.sdk.internal.utils.L;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.app.tbd.utils.Utils.trim;

public class PassengerListAdapter extends BaseAdapter {

    private final Activity context;
    private String departureAirport;
    private String arrivalAirport;
    private String flightClass;
    private Integer selected_position = -1;
    private int p = 0;
    private String flightWay;
    private static String dialCode;
    private Boolean active = false;
    TravellerInfo travellerInfo;
    SharedPrefManager pref;
    BaseFragment base;

    PassengerInfoFragment frag;
    int totalPassenger = 0;
    private Boolean first;
    Boolean traveller1;
    @Nullable
    String nationalityCode;

    ArrayList<String> infantList, existList;

    public PassengerListAdapter(Activity context, PassengerInfoFragment fragment, int total, Boolean traveller1Fly, TravellerInfo info) {
        this.context = context;
        this.totalPassenger = total;
        this.traveller1 = traveller1Fly;
        this.frag = fragment;
        this.travellerInfo = info;
    }

    @Override
    public int getCount() {

        if (traveller1) {
            return totalPassenger - 1;
        } else {
            return totalPassenger;
        }
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

        @InjectView(R.id.txtPassenger)
        TextView txtPassenger;

        @InjectView(R.id.txtSalutation)
        TextView txtSalutation;

        @InjectView(R.id.salutationBlock)
        LinearLayout salutationBlock;

        @InjectView(R.id.nationalityBlock)
        LinearLayout nationalityBlock;

        @InjectView(R.id.txtNationality)
        TextView txtNationality;

        @InjectView(R.id.txtFamilyName)
        TextView txtFamilyName;

        @InjectView(R.id.txtGivenName)
        TextView txtGivenName;

        @InjectView(R.id.dobLayout)
        LinearLayout dobLayout;

        @InjectView(R.id.txtDOB)
        TextView txtDOB;

        @InjectView(R.id.infantBlock)
        LinearLayout infantBlock;

        @InjectView(R.id.txtTravellingWith)
        TextView txtTravellingWith;

        @InjectView(R.id.txtInfantGender)
        TextView txtInfantGender;

        @InjectView(R.id.infantGender)
        LinearLayout infantGender;

        @InjectView(R.id.travellingWithBlock)
        LinearLayout travellingWithBlock;

        @InjectView(R.id.bottomLayout)
        LinearLayout bottomLayout;

        @InjectView(R.id.salutationTitle)
        LinearLayout salutationTitle;

        /*@InjectView(R.id.txtDay)
        TextView txtDay;

        @InjectView(R.id.txtMonth)
        TextView txtMonth;

        @InjectView(R.id.txtYear)
        TextView txtYear;*/
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        pref = new SharedPrefManager(context);

        final ViewHolder vh;
        // if (view == null) {
        view = LayoutInflater.from(context).inflate(R.layout.flight_passenger_add, parent, false);
        vh = new ViewHolder();
        ButterKnife.inject(vh, view);
        view.setTag(vh);
        //} else {

        //    vh = (ViewHolder) view.getTag();
        /// }


        /*Set Nationality*/
        HashMap<String, String> init = pref.getSavedCountry();
        String countryName = init.get(SharedPrefManager.SAVED_COUNTRY);

        vh.txtNationality.setText(countryName);

        //position = 1

        /*int totalAll = travellerInfo.getList().size();*/
        //int totalInfant = infantInfo(travellerInfo).size();
        int i = 0;

        final int useThisPosition;
        String textGuest = AnalyticsApplication.getContext().getString(R.string.guest);

        if (traveller1) { //im fly ---- position start 0-3
            useThisPosition = (position + 1); //position 0 in child : useThisPosition 1 in whole list
            vh.txtPassenger.setText(textGuest + " " + ((position + 1) + 1) + " " + "( " + travellerInfo.getList().get(useThisPosition).getType() + " )");

            if (travellerInfo.getList().get(useThisPosition).getType().equals("Infant")) {
                vh.infantBlock.setVisibility(View.VISIBLE);

                /*for (; i < totalInfant;){

                    if (first = true){
                        vh.txtTravellingWith.setText("Adult " + (i)); //Adult 1    Adult 2   Adult 3   Adult 4
                        vh.txtTravellingWith.setTag(String.valueOf(i - 1));  //Code 0  1  2  3
                        vh.txtTravellingWith.setError(null);
                        first = false;
                        break;

                    }else if (first = false){

                        vh.txtTravellingWith.setText("Adult " + (i)); //Adult 1    Adult 2   Adult 3   Adult 4
                        vh.txtTravellingWith.setTag(String.valueOf(i - 1));  //Code 0  1  2  3
                        vh.txtTravellingWith.setError(null);
                        break;
                    }i++;
                }*/

            } else if (travellerInfo.getList().get(useThisPosition).getType().equals("Adult")) {
                vh.infantBlock.setVisibility(View.GONE);
                vh.salutationTitle.setVisibility(View.VISIBLE);
                vh.salutationBlock.setVisibility(View.VISIBLE);
                vh.infantGender.setVisibility(View.GONE);
                vh.travellingWithBlock.setVisibility(View.GONE);
            } else if (travellerInfo.getList().get(useThisPosition).getType().equals("Child")) {
                vh.travellingWithBlock.setVisibility(View.GONE);
                vh.infantGender.setVisibility(View.VISIBLE);
            }//not
        }

        //i'm not fly
        else {
            useThisPosition = (position);
            vh.txtPassenger.setText(textGuest + " " + ((position + 1)) + " " + "( " + travellerInfo.getList().get(useThisPosition).getType() + " )");

            if (travellerInfo.getList().get(useThisPosition).getType().equals("Infant")) {  //N N Y N

                vh.infantBlock.setVisibility(View.VISIBLE);

                /*p++; //1 2 3
                for (; p < x; ) {//1<4  2<4 3<4

                    vh.txtTravellingWith.setText("Adult " + (p)); //Adult 1    Adult 2   Adult 3   Adult 4
                    vh.txtTravellingWith.setTag(String.valueOf(p - 1));  //Code 0  1  2  3
                    vh.txtTravellingWith.setError(null);
                    break;
                }*/

            } else if (travellerInfo.getList().get(useThisPosition).getType().equals("Adult")) {
                vh.infantBlock.setVisibility(View.GONE);
                vh.salutationTitle.setVisibility(View.VISIBLE);
                vh.salutationBlock.setVisibility(View.VISIBLE);
                vh.infantGender.setVisibility(View.GONE);
                vh.travellingWithBlock.setVisibility(View.GONE);

            } else if (travellerInfo.getList().get(useThisPosition).getType().equals("Child")) {
                vh.travellingWithBlock.setVisibility(View.GONE);
                vh.infantGender.setVisibility(View.VISIBLE);

            }//not
        }

/*
        for (travellerInfo.getList())
*/


        if (travellerInfo.getList().get(useThisPosition).getType().equals("Infant")) {
            vh.infantBlock.setVisibility(View.VISIBLE);
        } else {
            vh.infantBlock.setVisibility(View.GONE);
        }

        //check if error
        if (travellerInfo.getList().get(useThisPosition).getSalutationError() != null) {
            if (travellerInfo.getList().get(useThisPosition).getSalutationError().equals("Y")) {
                String error = AnalyticsApplication.getContext().getString(R.string.adapter_salutation_required);
                vh.txtSalutation.setError(error);
            }
        }

        if (travellerInfo.getList().get(useThisPosition).getNationalityError() != null) {
            if (travellerInfo.getList().get(useThisPosition).getNationalityError().equals("Y")) {
                String error = AnalyticsApplication.getContext().getString(R.string.adapter_nationality_required);
                vh.txtNationality.setError(error);
            }
        }
        if (travellerInfo.getList().get(useThisPosition).getFamilyNameError() != null) {
            if (travellerInfo.getList().get(useThisPosition).getFamilyNameError().equals("Y")) {
                String error = AnalyticsApplication.getContext().getString(R.string.adapter_family_name_required);
                vh.txtFamilyName.setError(error);
            }
        }
        if (travellerInfo.getList().get(useThisPosition).getGivenNameError() != null) {
            if (travellerInfo.getList().get(useThisPosition).getGivenNameError().equals("Y")) {
                String error = AnalyticsApplication.getContext().getString(R.string.adapter_given_name_required);
                vh.txtGivenName.setError(error);
            }
        }
        if (travellerInfo.getList().get(useThisPosition).getDobError() != null) {
            if (travellerInfo.getList().get(useThisPosition).getDobError().equals("Y")) {
                String error = AnalyticsApplication.getContext().getString(R.string.adapter_dob_required);
                vh.txtDOB.setError(error);
            }/* else if (travellerInfo.getList().get(useThisPosition).getDobError().equals("E")) {
                vh.txtDOB.setError("Date of Birth of Infant must be > 9 days and < 2 years old from the first departure");
            }*/
        }
        if (travellerInfo.getList().get(useThisPosition).getTravellingWithError() != null) {
            if (travellerInfo.getList().get(useThisPosition).getTravellingWithError().equals("Y")) {
                String error = AnalyticsApplication.getContext().getString(R.string.adapter_travelling_required);
                vh.txtTravellingWith.setError(error);
            }
        }
        if (travellerInfo.getList().get(useThisPosition).getGenderError() != null) {
            if (travellerInfo.getList().get(useThisPosition).getGenderError().equals("Y")) {
                String error = AnalyticsApplication.getContext().getString(R.string.adapter_gender_required);
                vh.txtInfantGender.setError(error);
            }
        }

        //SET DATA FIRST
        if (travellerInfo.getList().get(useThisPosition).getNationality() != null) {
            vh.txtNationality.setText(travellerInfo.getList().get(useThisPosition).getNationality());
            vh.txtNationality.setTag(travellerInfo.getList().get(useThisPosition).getNationalityCode());
            vh.txtNationality.setError(null);
        }

        //SET DATA FIRST
        if (travellerInfo.getList().get(useThisPosition).getSalutation() != null) {
            vh.txtSalutation.setText(travellerInfo.getList().get(useThisPosition).getSalutation());
            vh.txtSalutation.setTag(travellerInfo.getList().get(useThisPosition).getSalutationCode());
            vh.txtSalutation.setError(null);
        }


        if (travellerInfo.getList().get(useThisPosition).getDob() != null) {
            vh.txtDOB.setText(travellerInfo.getList().get(useThisPosition).getDob());
            vh.txtDOB.setTag(travellerInfo.getList().get(useThisPosition).getDob());
            vh.txtDOB.setError(null);
        }

        if (travellerInfo.getList().get(useThisPosition).getTravellingWith() != null) {
            vh.txtTravellingWith.setText(travellerInfo.getList().get(useThisPosition).getTravellingWith());
            vh.txtTravellingWith.setTag(travellerInfo.getList().get(useThisPosition).getTravellingWithCode());
            vh.txtTravellingWith.setError(null);
        }
        if (travellerInfo.getList().get(useThisPosition).getGender() != null) {
            vh.txtInfantGender.setText(travellerInfo.getList().get(useThisPosition).getGender());
            vh.txtInfantGender.setTag(travellerInfo.getList().get(useThisPosition).getGenderCode());
            vh.txtInfantGender.setError(null);
        }


        //SET DATA FIRST
       /* if (travellerInfo.getList().get(useThisPosition).getDay() != null) {
            vh.txtDay.setText(travellerInfo.getList().get(useThisPosition).getDay());
        }

        //SET DATA FIRST
        if (travellerInfo.getList().get(useThisPosition).getMonth() != null) {
            vh.txtMonth.setTag(travellerInfo.getList().get(useThisPosition).getMonth());
        }

        //SET DATA FIRST
        if (travellerInfo.getList().get(useThisPosition).getYear() != null) {
            vh.txtYear.setTag(travellerInfo.getList().get(useThisPosition).getYear());
        }*/
        /* Document No Update */


        //final EditText txtGivenNameNH = (EditText) view.findViewById(R.id.txtGivenName);
        //if (txtGivenNameNH.hasFocus()) {
        //Log.e("CurrentView Has Focus", txtGivenNameNH.getTag().toString() + " " + txtGivenNameNH.getText().toString());
        //if (vh.txtGivenName.hasFocus()) {
        //    Log.e("CurrentView Has Focus", vh.txtGivenName.getTag().toString() + " " + vh.txtGivenName.getText().toString());
        //}
        //if (vh.txtGivenName.hasFocus()) {
        vh.txtGivenName.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                Log.e("vh.txtGivenName " + useThisPosition, arg0.toString());
                travellerInfo.getList().get(useThisPosition).setGiven_name(arg0.toString().trim());
                travellerInfo.getList().get(useThisPosition).setGivenNameError("N");
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub

            }

        });
        //
        // }
        //}

        //SET DATA FIRST
        vh.txtGivenName.setTag(Integer.toString(useThisPosition));
        if (travellerInfo.getList().get(useThisPosition).getGiven_name() != null) {
            if (!travellerInfo.getList().get(useThisPosition).getGiven_name().equals("")) {
                vh.txtGivenName.setText(travellerInfo.getList().get(useThisPosition).getGiven_name());
            } else {
                vh.txtGivenName.setText("");
            }
        } else {
            vh.txtGivenName.setText("");
        }

        //final EditText txtFamilyNameNH = (EditText) view.findViewById(R.id.txtFamilyName);
        //if (vh.txtFamilyName.hasFocus()) {
        vh.txtFamilyName.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                travellerInfo.getList().get(useThisPosition).setFamily_name(arg0.toString().trim());
                travellerInfo.getList().get(useThisPosition).setFamilyNameError("N");
                Log.e("Char2", arg0.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
                // TODO Auto-generated method stub

            }

        });

        //SET DATA FIRST
        if (travellerInfo.getList().get(useThisPosition).getFamily_name() != null) {
            if (!travellerInfo.getList().get(useThisPosition).getFamily_name().equals("")) {
                vh.txtFamilyName.setText(travellerInfo.getList().get(useThisPosition).getFamily_name());
            } else {
                vh.txtFamilyName.setText("");
            }
        } else {
            vh.txtFamilyName.setText("");
        }
        //}

        vh.nationalityBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frag.showCountrySelector(context, frag.getCountryList(), "NATIONALITY", useThisPosition);
            }
        });

        vh.salutationBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frag.showCountrySelector(context, frag.getTitleList(), "TITLE", useThisPosition);
            }
        });

        vh.dobLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frag.clickDate(useThisPosition);
            }
        });

        vh.travellingWithBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frag.showListAdult(useThisPosition);
            }
        });

        vh.infantGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frag.showCountrySelector(context, frag.getGender(), "GENDER", useThisPosition);
            }
        });

        return view;

    }

    public void updateTravellerInfo(TravellerInfo obj) {
        travellerInfo = obj;
        notifyDataSetChanged();
    }

    public void notifyError(TravellerInfo obj) {
        travellerInfo = obj;
        notifyDataSetChanged();
    }

    public void invalidateSelected() {
        selected_position = -1;
        notifyDataSetChanged();
    }

   /* public ArrayList<String> infantInfo(TravellerInfo obj) {
        infantList = new ArrayList<String>();

        for (int i = 0; i < totalPassenger; i++) {
            String singleInfant = obj.getList().get(i).getType();
            if (singleInfant.equalsIgnoreCase("Infant")) {
                infantList.add(singleInfant);
            }
        }

        return infantList;
    } */

    public TravellerInfo returnTravellerInfo() {
        return travellerInfo;
    }
}
