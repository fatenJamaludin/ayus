package com.app.tbd.ui.Activity.BookingFlight.TravellerInfo;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.tbd.R;
import com.app.tbd.application.AnalyticsApplication;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Activity.BookingFlight.PassengerInfoFragment;
import com.app.tbd.ui.Activity.DragDrop.DropClass;
import com.app.tbd.ui.Activity.Homepage.HomePromoFragment;
import com.app.tbd.ui.Model.Adapter.RecyclerAdapter;
import com.app.tbd.ui.Model.JSON.SeatCached;
import com.app.tbd.ui.Model.JSON.TravellerAddon;
import com.app.tbd.ui.Model.JSON.TravellerInfo;
import com.app.tbd.ui.Model.JSON.UserInfoJSON;
import com.app.tbd.ui.Model.Receive.BaggageMealReceive;
import com.app.tbd.ui.Model.Receive.LoadInsuranceReceive;
import com.app.tbd.ui.Model.Receive.TBD.LoginReceive;
import com.app.tbd.ui.Realm.RealmObjectController;
import com.app.tbd.utils.SharedPrefManager;
import com.app.tbd.utils.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.realm.Realm;
import io.realm.RealmResults;

public class PassengerListAdapterV2 extends RecyclerView.Adapter<PassengerListAdapterV2.ViewHolder> {

    private final Activity context;
    private Integer selected_position = -1;
    private int p = 0;
    TravellerInfo travellerInfo;
    SharedPrefManager pref;

    PassengerInfoFragment frag;
    int totalPassenger = 0;
    Boolean traveller1;

    ArrayList<String> infantList;

    public PassengerListAdapterV2(Activity context, PassengerInfoFragment fragment, int total, Boolean traveller1Fly, TravellerInfo info) {
        this.context = context;
        this.totalPassenger = total;
        this.traveller1 = traveller1Fly;
        this.frag = fragment;
        this.travellerInfo = info;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

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

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.flight_passenger_add, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder vh, int position) {

        pref = new SharedPrefManager(context);

         /*Set Nationality*/
        /*HashMap<String, String> init = pref.getSavedCountry();
        String countryName = init.get(SharedPrefManager.SAVED_COUNTRY);

        HashMap<String, String> init2 = pref.getSavedCountryCode();
        String countryCode = init2.get(SharedPrefManager.SAVED_COUNTRY_CODE);*/

        //dialCode = base.getDialingCode(context, countryName);

        // vh.txtNationality.setText(countryName);
        //vh.txtNationality.setTag(countryCode + "/" + dialCode);
        // vh.txtNationality.setTag(countryCode);


        //position = 1

        int totalInfant = infantInfo(travellerInfo).size();
        final int useThisPosition;
        int x = totalInfant + 1;
        String guest = AnalyticsApplication.getContext().getString(R.string.guest);
        String adult = AnalyticsApplication.getContext().getString(R.string.adult);

        if (traveller1) { //im fly ---- position start 0-3
            useThisPosition = (position + 1); //position 0 in child : useThisPosition 1 in whole list
            vh.txtPassenger.setText(guest + " " + ((position + 1) + 1) + " " + "( " + travellerInfo.getList().get(useThisPosition).getType() + " )");

            if (travellerInfo.getList().get(useThisPosition).getType().equals("Infant")) {
                vh.infantBlock.setVisibility(View.VISIBLE);

                for (int z = 0; z < totalInfant; z++) { //0 1 2

                    p++; //1 2 3
                    for (; p < x; ) {//1<4  2<4 3<4
                        p++;
                        vh.txtTravellingWith.setText(adult + " " + (p)); //Adult 1    Adult 2   Adult 3   Adult 4
                        vh.txtTravellingWith.setTag(String.valueOf(p - 1));  //Code 0  1  2  3
                        vh.txtTravellingWith.setError(null);
                        break;
                    }
                }
            } else {
                vh.infantBlock.setVisibility(View.GONE);
            } //not
        }

        //i'm not fly
        else {
            useThisPosition = (position);
            vh.txtPassenger.setText(guest + " " + ((position + 1)) + " " + "( " + travellerInfo.getList().get(useThisPosition).getType() + " )");

            if (travellerInfo.getList().get(useThisPosition).getType().equals("Infant")) {  //N N Y N

                vh.infantBlock.setVisibility(View.VISIBLE);

                p++; //1 2 3
                for (; p < x; ) {//1<4  2<4 3<4

                    vh.txtTravellingWith.setText(adult + " " + (p)); //Adult 1    Adult 2   Adult 3   Adult 4
                    vh.txtTravellingWith.setTag(String.valueOf(p - 1));  //Code 0  1  2  3
                    vh.txtTravellingWith.setError(null);
                    break;
                }

            } else {
                vh.infantBlock.setVisibility(View.GONE);
            }
        }

        /*if (travellerInfo.getList().get(useThisPosition).getType().equals("Infant")) {
            vh.infantBlock.setVisibility(View.GONE);
        }else
*/
        /*else {
            vh.infantBlock.setVisibility(View.GONE);
        }*/

        //check if error
        if (travellerInfo.getList().get(useThisPosition).getSalutationError() != null) {
            if (travellerInfo.getList().get(useThisPosition).getSalutationError().equals("Y")) {
                String salutation_text = AnalyticsApplication.getContext().getString(R.string.salutation_text);
                vh.txtSalutation.setError(salutation_text);
            }
        }
        if (travellerInfo.getList().get(useThisPosition).getNationalityError() != null) {
            if (travellerInfo.getList().get(useThisPosition).getNationalityError().equals("Y")) {
                String nationality_required = AnalyticsApplication.getContext().getString(R.string.nationality_required);
                vh.txtNationality.setError(nationality_required);
            }
        }
        if (travellerInfo.getList().get(useThisPosition).getFamilyNameError() != null) {
            if (travellerInfo.getList().get(useThisPosition).getFamilyNameError().equals("Y")) {
                String family_name_required = AnalyticsApplication.getContext().getString(R.string.family_name_required);
                vh.txtFamilyName.setError(family_name_required);
            }
        }
        if (travellerInfo.getList().get(useThisPosition).getGivenNameError() != null) {
            if (travellerInfo.getList().get(useThisPosition).getGivenNameError().equals("Y")) {
                String given_name_required = AnalyticsApplication.getContext().getString(R.string.given_name_required);
                vh.txtGivenName.setError(given_name_required);
            }
        }
        if (travellerInfo.getList().get(useThisPosition).getDobError() != null) {
            if (travellerInfo.getList().get(useThisPosition).getDobError().equals("Y")) {
                String dob_required = AnalyticsApplication.getContext().getString(R.string.dob_required);
                vh.txtDOB.setError(dob_required);
            }/* else if (travellerInfo.getList().get(useThisPosition).getDobError().equals("E")) {
                vh.txtDOB.setError("Date of Birth of Infant must be > 9 days and < 2 years old from the first departure");
            }*/
        }
        if (travellerInfo.getList().get(useThisPosition).getTravellingWithError() != null) {
            if (travellerInfo.getList().get(useThisPosition).getTravellingWithError().equals("Y")) {
                String travelling_required = AnalyticsApplication.getContext().getString(R.string.travelling_required);
                vh.txtTravellingWith.setError(travelling_required);
            }
        }
        if (travellerInfo.getList().get(useThisPosition).getGenderError() != null) {
            if (travellerInfo.getList().get(useThisPosition).getGenderError().equals("Y")) {
                String gender_required = AnalyticsApplication.getContext().getString(R.string.gender_required);
                vh.txtInfantGender.setError(gender_required);
            }
        }

        //SET DATA FIRST
        Log.e("Userthis", Integer.toString(useThisPosition));
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

        //SET DATA FIRST
        if (travellerInfo.getList().get(useThisPosition).getGiven_name() != null) {
            vh.txtGivenName.setText(travellerInfo.getList().get(useThisPosition).getGiven_name());
        }

        //SET DATA FIRST
        if (travellerInfo.getList().get(useThisPosition).getFamily_name() != null) {
            vh.txtFamilyName.setText(travellerInfo.getList().get(useThisPosition).getFamily_name());
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

        //final EditText txtGivenNameNH = (EditText) view.findViewById(R.id.txtGivenName);
        vh.txtGivenName.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                travellerInfo.getList().get(useThisPosition).setGiven_name(vh.txtGivenName.getText().toString());
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

        //final EditText txtFamilyNameNH = (EditText) view.findViewById(R.id.txtFamilyName);
        vh.txtFamilyName.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                travellerInfo.getList().get(useThisPosition).setFamily_name(vh.txtFamilyName.getText().toString());
                travellerInfo.getList().get(useThisPosition).setFamilyNameError("N");

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

    }

    @Override
    public int getItemCount() {
        return travellerInfo.getList().size();
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

    public ArrayList<String> infantInfo(TravellerInfo obj) {
        infantList = new ArrayList<String>();

        for (int i = 0; i < totalPassenger; i++) {
            String singleInfant = obj.getList().get(i).getType();
            if (singleInfant.equalsIgnoreCase("Infant")) {
                infantList.add(singleInfant);
                Log.e("Infant Size", String.valueOf(infantList.size()));
            }
        }

        return infantList;
    }

    public TravellerInfo returnTravellerInfo() {
        return travellerInfo;
    }
}