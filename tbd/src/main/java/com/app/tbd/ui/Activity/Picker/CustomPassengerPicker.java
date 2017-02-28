package com.app.tbd.ui.Activity.Picker;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.tbd.R;
import com.app.tbd.utils.SharedPrefManager;
import com.app.tbd.utils.Utils;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import dev.dworks.libs.astickyheader.SimpleSectionedListAdapter;

public class CustomPassengerPicker extends DialogFragment {

    @InjectView(R.id.btnDone)
    TextView btnDone;

    @InjectView(R.id.txtSelectionTitle)
    TextView txtSelectionTitle;

    @InjectView(R.id.txtAdultQty)
    TextView txtAdultQty;

    @InjectView(R.id.txtChildQty)
    TextView txtChildQty;

    @InjectView(R.id.txtInfantQty)
    TextView txtInfantQty;

    @InjectView(R.id.btnAdultPlus)
    ImageView btnAdultPlus;

    @InjectView(R.id.btnAdultMinus)
    ImageView btnAdultMinus;

    @InjectView(R.id.btnChildPlus)
    ImageView btnChildPlus;

    @InjectView(R.id.btnChildMinus)
    ImageView btnChildMinus;

    @InjectView(R.id.btnInfantPlus)
    ImageView btnInfantPlus;

    @InjectView(R.id.btnInfantMinus)
    ImageView btnInfantMinus;

    @InjectView(R.id.backbutton)
    ImageView backbutton;


    int adult = 1, infant = 0, child = 0;
    SharedPrefManager pref;

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

    public static CustomPassengerPicker newInstance(String adultN, String childN, String infantN) {
        CustomPassengerPicker fragment = new CustomPassengerPicker();

        Bundle bundle = new Bundle();
        bundle.putString("ADULT", adultN);
        bundle.putString("CHILD", childN);
        bundle.putString("INFANT", infantN);


        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        final String e_max = getResources().getString(R.string.error_max);
        final String e_min = getResources().getString(R.string.error_min);
        final String e_infant = getResources().getString(R.string.error_infant_morethan_adult);

        View view = inflater.inflate(R.layout.custom_passenger_picker, container, false);
        ButterKnife.inject(this, view);

        pref = new SharedPrefManager(getActivity());

        adult = Integer.parseInt(getArguments().getString("ADULT"));
        child = Integer.parseInt(getArguments().getString("CHILD"));
        infant = Integer.parseInt(getArguments().getString("INFANT"));

        txtAdultQty.setText(Integer.toString(adult));
        txtChildQty.setText(Integer.toString(child));
        txtInfantQty.setText(Integer.toString(infant));

        Log.e("adult", String.valueOf(adult));
        Log.e("child", String.valueOf(child));
        Log.e("infant", String.valueOf(infant));


        txtSelectionTitle.setText(R.string.passenger_title);
        btnDone.setText(R.string.passenger_title_done);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendResult(adult, child, infant);
            }
        });

        btnAdultPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkTotal("ADULT")) {
                    adult++;
                    txtAdultQty.setText(Integer.toString(adult));

                } else {
                    /*btnAdultPlus.setImageResource(R.drawable.disable_plus_icon);
                    Utils.toastNotification(getActivity(), e_max);*/
                }
            }
        });

        btnAdultMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adult > 1) {
                    if (infant > adult) {
                        Utils.toastNotification(getActivity(), e_infant);
                    } else {
                        adult--;
                        if (adult == infant){
                            infant--;
                        }
                        txtAdultQty.setText(Integer.toString(adult));
                        txtInfantQty.setText(Integer.toString(infant));
                    }
                } else {
                    Utils.toastNotification(getActivity(), e_min);
                }
            }
        });

        btnChildPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkTotal("CHILD")) {
                    child++;
                    txtChildQty.setText(Integer.toString(child));

                } else {
                    /*Utils.toastNotification(getActivity(), e_max);*/
                }

            }
        });

        btnChildMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (child > 0) {
                    child--;
                    txtChildQty.setText(Integer.toString(child));
                }
            }
        });

        btnInfantPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (infant < adult) {
                    if (checkTotal("INFANT")) {
                        infant++;
                        txtInfantQty.setText(Integer.toString(infant));
                    }
                } else {
                        Utils.toastNotification(getActivity(), e_infant);
                }

            }
        });

        btnInfantMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (infant > 0) {
                    infant--;
                    txtInfantQty.setText(Integer.toString(infant));
                }
            }
        });

        checkIcon();
        return view;
    }

    public void checkIcon() {

        int totalAll = adult + infant + child;
        int totalAdult = adult;
        int totalChild = child;
        int totalInfant = infant;

        if (totalAll >= 9) {
            btnInfantPlus.setImageResource(R.drawable.disable_plus_icon);
            btnChildPlus.setImageResource(R.drawable.disable_plus_icon);
            btnAdultPlus.setImageResource(R.drawable.disable_plus_icon);

        } else {
            btnInfantPlus.setImageResource(R.drawable.ic_plus);
            btnChildPlus.setImageResource(R.drawable.ic_plus);
            btnAdultPlus.setImageResource(R.drawable.ic_plus);
        }

        if (totalAdult >= 9) {
            btnInfantPlus.setImageResource(R.drawable.disable_plus_icon);
            btnChildPlus.setImageResource(R.drawable.disable_plus_icon);
            btnAdultPlus.setImageResource(R.drawable.disable_plus_icon);

        } else {
            btnInfantPlus.setImageResource(R.drawable.ic_plus);
            btnChildPlus.setImageResource(R.drawable.ic_plus);
            btnAdultPlus.setImageResource(R.drawable.ic_plus);
        }

        if (totalChild >= 8) {
            btnChildPlus.setImageResource(R.drawable.disable_plus_icon);

        } else {
            btnInfantPlus.setImageResource(R.drawable.ic_plus);
            btnChildPlus.setImageResource(R.drawable.ic_plus);
            btnAdultPlus.setImageResource(R.drawable.ic_plus);
        }

        if (totalInfant >= 4) {
            btnInfantPlus.setImageResource(R.drawable.disable_plus_icon);
        }else if (totalAdult == 0){
            btnInfantMinus.setImageResource(R.drawable.disable_minus_icon);

        }else{
            btnInfantPlus.setImageResource(R.drawable.ic_plus);
        }
    }

    public Boolean checkTotal(String passengerType) {

        String errorMsg_maxPassenger1 = getResources().getString(R.string.error_max_passenger_booking);
        String errorMsg_maxPassenger2 = getResources().getString(R.string.error_max_passenger_adult);
        String errorMsg_maxPassenger3 = getResources().getString(R.string.error_max_passenger_child);
        String errorMsg_maxPassenger4 = getResources().getString(R.string.error_max_passenger_infant);

        int totalPassenger = adult + infant + child; //1

        if (totalPassenger > 8) {

            Utils.toastNotification(getActivity(), errorMsg_maxPassenger1);
            return false;

        }else if (passengerType.equals("INFANT") && infant > 3){
            if (infant>=4){

            }
            Utils.toastNotification(getActivity(), errorMsg_maxPassenger4);
            return false;

        } else if (passengerType.equals("ADULT") && adult > 8) {
            Utils.toastNotification(getActivity(), errorMsg_maxPassenger2);
            return false;

        } else if (passengerType.equals("CHILD") && child > 7){
            Utils.toastNotification(getActivity(), errorMsg_maxPassenger3);
            return false;

        } else {

            return true;
        }
    }

    private void sendResult(int adult2, int child2, int infant2) {
        if (getTargetFragment() == null) {
            Log.e("Get Target Fragment", "NULL");
            return;
        }

        Intent intent = new Intent();
        intent.putExtra("ADULT", Integer.toString(adult2));
        intent.putExtra("INFANT", Integer.toString(infant2));
        intent.putExtra("CHILD", Integer.toString(child2));

        getTargetFragment().onActivityResult(1, Activity.RESULT_OK, intent);
        dismiss();
    }
}
