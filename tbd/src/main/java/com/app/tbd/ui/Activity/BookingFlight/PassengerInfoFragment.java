package com.app.tbd.ui.Activity.BookingFlight;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.app.tbd.MainController;
import com.app.tbd.R;
import com.app.tbd.application.AnalyticsApplication;
import com.app.tbd.application.MainApplication;
import com.app.tbd.ui.Activity.BookingFlight.Add.AddOnActivity;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Activity.Picker.SelectionListFragment;
import com.app.tbd.ui.Activity.TAB.TabActivity;
import com.app.tbd.ui.Model.Adapter.PassengerListAdapter;
import com.app.tbd.ui.Model.JSON.TravellerDetailInfo;
import com.app.tbd.ui.Model.JSON.TravellerInfo;
import com.app.tbd.ui.Model.JSON.TravellerInfoToBeFilter;
import com.app.tbd.ui.Model.JSON.UserInfoJSON;
import com.app.tbd.ui.Model.Receive.TBD.LoginReceive;
import com.app.tbd.ui.Model.Receive.UpdateTravellerReceive;
import com.app.tbd.ui.Module.PassengerInfoModule;
import com.app.tbd.ui.Presenter.BookingPresenter;
import com.app.tbd.ui.Realm.RealmObjectController;
import com.app.tbd.utils.DropDownItem;
import com.app.tbd.utils.DropMenuAdapter;
import com.app.tbd.utils.SharedPrefManager;
import com.google.gson.Gson;
import com.kyleduo.switchbutton.SwitchButton;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import io.realm.Realm;
import io.realm.RealmResults;

public class PassengerInfoFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener, BookingPresenter.TravellerView {

    @Inject
    BookingPresenter presenter;

    //@InjectView(R.id.passengerList)
    //LinearLayout passengerList;

    @InjectView(R.id.addedPassengerList)
    ListView addedPassengerList;

    //@InjectView(R.id.addedPassengerListV2)
    //RecyclerView recyclerView;
    //RecyclerView.LayoutManager layoutManager;
    //RecyclerView.Adapter adapter;

    static TextView contactGivenName;
    static TextView contactFamilyName;
    static TextView contactSalutation;
    static EditText contactEmail;
    static EditText contactMobileNo;
    static TextView contactNationality;

    static PassengerListAdapter passengerListAdapter;
    private int fragmentContainerId;
    private int totalPassenger;

    private ArrayList<DropDownItem> countryList = new ArrayList<DropDownItem>();
    private ArrayList<DropDownItem> titleList = new ArrayList<DropDownItem>();
    private ArrayList<DropDownItem> genderList = new ArrayList<DropDownItem>();
    private ArrayList<DropDownItem> adultList = new ArrayList<DropDownItem>();
    private ArrayList<String> infantList;

    private static String signature;
    Integer travellerPosition;

    static int total;
    static Boolean imFlying = true;
    final Calendar calendar = Calendar.getInstance();
    TravellerInfo travellerInfo = new TravellerInfo();
    public static final String DATEPICKER_TAG = "DATEPICKER_TAG";
    private DatePickerDialog dob_picker;
    private int adult = 1, child = 0, infant = 0;
    private String departure_date;
    Boolean addFooter = true;
    Boolean addHeader = true;
    static Boolean emailValidation = false;
    static BookingPresenter presenter2;
    static String username, token;
    /*int totalSegment;*/
    int indexForState = -1;
    SharedPrefManager pref;
    Boolean statusAge = false;
    String selectedAdult;
    static TravellerInfo obj;
    static TravellerInfoToBeFilter objFilter;

    int usedAdultList = 0;

    public static PassengerInfoFragment newInstance(Bundle bundle) {

        PassengerInfoFragment fragment = new PassengerInfoFragment();
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RealmObjectController.clearCachedResult(getActivity());
        MainApplication.get(getActivity()).createScopedGraph(new PassengerInfoModule(this)).inject(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.passenger_information, container, false);
        ButterKnife.inject(this, view);
        pref = new SharedPrefManager(getActivity());

        dataSetup();
        datePickerSetting();

        return view;
    }

    public void updateFirstTraveller(Boolean flying) {

        Realm realm = RealmObjectController.getRealmInstance(getActivity());
        final RealmResults<UserInfoJSON> result2 = realm.where(UserInfoJSON.class).findAll();
        LoginReceive loginReceive = (new Gson()).fromJson(result2.get(0).getUserInfo(), LoginReceive.class);

        if (flying) {
            String title;
            if (loginReceive.getSalutation().equals("1")) {
                title = "MR";
            } else {
                title = "MS";
            }
            travellerInfo.getList().get(0).setSalutation(title);
            travellerInfo.getList().get(0).setSalutationCode(loginReceive.getSalutation());
            travellerInfo.getList().get(0).setNationality(loginReceive.getCountryCode());
            travellerInfo.getList().get(0).setDob(loginReceive.getDob());
            travellerInfo.getList().get(0).setFamily_name(loginReceive.getLastName());
            travellerInfo.getList().get(0).setGiven_name(loginReceive.getFirstName());

        } else {
            travellerInfo.getList().get(0).setSalutation(null);
            travellerInfo.getList().get(0).setSalutationCode(null);
            travellerInfo.getList().get(0).setNationality(null);
            travellerInfo.getList().get(0).setDob(null);
            travellerInfo.getList().get(0).setFamily_name(null);
            travellerInfo.getList().get(0).setGiven_name(null);
        }
    }

    public void dataSetup() {

        Bundle bundle = getArguments();
        adult = bundle.getInt("ADULT");
        child = bundle.getInt("CHILD");
        infant = bundle.getInt("INFANT");
        departure_date = bundle.getString("DEPARTURE_DATE");

        //totalSegment = bundle.getInt("TOTAL_SEGMENT");

        total = adult + child + infant;
        presenter2 = presenter;
        countryList = getCountry(getActivity());
        titleList = getTitle(getActivity());
        genderList = getGenderBase(getActivity());

        //declare Static

        Realm realm = RealmObjectController.getRealmInstance(getActivity());
        final RealmResults<UserInfoJSON> result2 = realm.where(UserInfoJSON.class).findAll();

        LoginReceive loginReceive = (new Gson()).fromJson(result2.get(0).getUserInfo(), LoginReceive.class);

        signature = loginReceive.getSignature();
        token = loginReceive.getToken();
        username = loginReceive.getUserName();
        String adult_text = getString(R.string.book_flight_adult);

        ArrayList<TravellerDetailInfo> listTravellerInfo = new ArrayList<TravellerDetailInfo>();
        for (int f = 0; f < total; f++) {
            TravellerDetailInfo travellerDetailInfo = new TravellerDetailInfo();

            if (f == 0 && imFlying) {
                travellerDetailInfo.setGiven_name(loginReceive.getFirstName());
                travellerDetailInfo.setFamily_name(loginReceive.getLastName());
                travellerDetailInfo.setNationalityCode(loginReceive.getCountryCode());
                travellerDetailInfo.setDobCode(loginReceive.getDob());
                travellerDetailInfo.setSalutationCode(loginReceive.getSalutation());
                travellerDetailInfo.setType("Adult");

                Log.e("Set Adult", "1");
                travellerDetailInfo.setType("Adult");
                DropDownItem itemPurpose = new DropDownItem();
                itemPurpose.setText(adult_text + " " + (f + 1)); //Adult 1
                itemPurpose.setCode(Integer.toString(f)); //String 0
                adultList.add(itemPurpose);
                Log.e("adultList", Integer.toString(adultList.size()));

            } else {
                //check passenger type
                HashMap<String, String> init2 = pref.getSavedCountryCode();
                String countryCode = init2.get(SharedPrefManager.SAVED_COUNTRY_CODE);

                if (f < adult) {

                    Log.e("Set Adult", "2");
                    travellerDetailInfo.setType("Adult");
                    travellerDetailInfo.setNationalityCode(countryCode);
                    DropDownItem itemPurpose = new DropDownItem();
                    itemPurpose.setText(adult_text + " " + (f + 1));
                    itemPurpose.setCode(Integer.toString(f));
                    adultList.add(itemPurpose);
                    Log.e("adultList", Integer.toString(adultList.size()));

                } else if (f < adult + child) {

                    travellerDetailInfo.setType("Child");
                    travellerDetailInfo.setNationalityCode(countryCode);
                    Log.e("Child Two ", "Y");


                } else if (f < adult + child + infant) {

                    int infantPosition = f;
                    travellerDetailInfo.setType("Infant");
                    travellerDetailInfo.setNationalityCode(countryCode);
                    Log.e("Infant Two ", "Y");
                }
            }
            listTravellerInfo.add(travellerDetailInfo);
        }

        travellerInfo.setList(listTravellerInfo);


        appendPassengerInfo();

        // String locationProvider = LocationManager.NETWORK_PROVIDER;
        // GeoCoder.getFromLocation()
        // Or use LocationManager.GPS_PROVIDER
        //Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
        /*WIFI.getMACAddress("wlan0");
        WIFI.getMACAddress("eth0");
        Log.e("ipaddress1", WIFI.getIPAddress(false));
        Log.e("ipaddress2", WIFI.getIPAddress(true));*/

    }

    public ArrayList<DropDownItem> getCountryList() {
        return countryList;
    }

    public ArrayList<DropDownItem> getTitleList() {
        return titleList;
    }

    public ArrayList<DropDownItem> getGender() {
        return genderList;
    }

    /*public ArrayList<String> getInfantList(TravellerInfo obj){

        infantList = new ArrayList<String>();

        for (int i = 0; i < totalPassenger; i++) {
            String singleInfant = obj.getList().get(i).getType();
            if (singleInfant.equalsIgnoreCase("Infant")) {
                infantList.add(singleInfant);
            }
        }

        return infantList;
    }*/

    public void autoAssignForInfant(int infantPosition) {
        //get infant size
        Log.e("Size" + Integer.toString(infantPosition), Integer.toString(usedAdultList));

        travellerInfo.getList().get(infantPosition).setTravellingWith(adultList.get(usedAdultList).getText());
        travellerInfo.getList().get(infantPosition).setTravellingWithCode(adultList.get(usedAdultList).getCode());
        travellerInfo.getList().get(infantPosition).setTravellingWithError("N");
        usedAdultList++;

    }

    public void showListAdult(final Integer tp) {

        final ArrayList<DropDownItem> a = adultList;
        Log.e("Adult List", Integer.toString(adultList.size()));
        DropMenuAdapter dropState = new DropMenuAdapter(getActivity());
        dropState.setItems(a);

        AlertDialog.Builder alertStateCode = new AlertDialog.Builder(getActivity());

        alertStateCode.setSingleChoiceItems(dropState, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String selected = a.get(which).getText();
                String selectedCode = a.get(which).getCode();

                travellerInfo.getList().get(tp).setTravellingWith(selected);
                travellerInfo.getList().get(tp).setTravellingWithCode(selectedCode);
                travellerInfo.getList().get(tp).setTravellingWithError("N");

                //modify the rest
                modifyInfantTravellingWith(tp, selectedCode);

                indexForState = which;

                dialog.dismiss();
            }
        });


        AlertDialog mDialog = alertStateCode.create();
        mDialog.show();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(mDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mDialog.getWindow().setAttributes(lp);
    }

    public void modifyInfantTravellingWith(int changedInfantTravellingWith, String selectedAdultCode) {

        ArrayList<String> previouslyAdded = new ArrayList<>();
        for (int loop = 0; loop < total; loop++) {
            if (travellerInfo.getList().get(loop).getType().equals("Infant") && loop != changedInfantTravellingWith) {
                for (int c = 0; c < adultList.size(); c++) {

                    Log.e(selectedAdultCode, previouslyAdded + " " + adultList.get(c).getCode());

                    if (!selectedAdultCode.equals(adultList.get(c).getCode()) && !previouslyAdded.contains(adultList.get(c).getCode())) {
                        //update
                        Log.e("Infant Position" + Integer.toString(loop), "Adult Code" + adultList.get(c).getCode());

                        travellerInfo.getList().get(loop).setTravellingWith(adultList.get(c).getText());
                        travellerInfo.getList().get(loop).setTravellingWithCode(adultList.get(c).getCode());
                        travellerInfo.getList().get(loop).setTravellingWithError("N");
                        previouslyAdded.add(adultList.get(c).getCode());
                        break;

                    }
                }

            }
        }
        passengerListAdapter.updateTravellerInfo(travellerInfo);

    }

    public void appendPassengerInfo() {

        usedAdultList = 0;

        Realm realm = RealmObjectController.getRealmInstance(getActivity());
        final RealmResults<UserInfoJSON> result2 = realm.where(UserInfoJSON.class).findAll();

        LoginReceive loginReceive = (new Gson()).fromJson(result2.get(0).getUserInfo(), LoginReceive.class);

        //convert to recyle view
       /* layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new PassengerListAdapterV2(getActivity(), this, total, imFlying, travellerInfo);
        recyclerView.setAdapter(adapter);*/

        //auto assign
        int flying = 0;
        if (imFlying) {
            flying = 1;
        }
        for (int f = 0; f < travellerInfo.getList().size(); f++) {
            if (travellerInfo.getList().get(f).getType().equals("Infant")) {
                autoAssignForInfant(f);
            }
        }


        passengerListAdapter = new PassengerListAdapter(getActivity(), this, total, imFlying, travellerInfo);

        if (addHeader) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View headerView = layoutInflater.inflate(R.layout.passenger_list_header, null);
            addedPassengerList.addHeaderView(headerView);
            addHeader = false;

            //TextView passenger1_dob;

            final ImageView imgUserDP = (ImageView) headerView.findViewById(R.id.imgUserDP);
            final TextView passenger1_name = (TextView) headerView.findViewById(R.id.txtPassenger1_name);
            final TextView txtMainPassenger = (TextView) headerView.findViewById(R.id.txtMainPassenger);
            final TextView passenger1_fullName = (TextView) headerView.findViewById(R.id.passenger1_fullName);
            final TextView passenger1_nationality = (TextView) headerView.findViewById(R.id.passenger1_nationality);
            final TextView passenger1_dob = (TextView) headerView.findViewById(R.id.passenger1_dob);

            //username = loginReceive.getFirstName();
            if (!loginReceive.getProfile_URL().equals("")) {
                displayImage(getActivity(), imgUserDP, loginReceive.getProfile_URL());
            }

            String dob = loginReceive.getDob(); // 1980-08-19

            Date departFormatDate = stringToDate(dob); //Thu Mar 23 00:00:00 GMT+08:00 2017
            String departDay = convertDate(departFormatDate);

            passenger1_name.setText(loginReceive.getFirstName());
            passenger1_fullName.setText(loginReceive.getFirstName() + " " + loginReceive.getLastName());
            passenger1_dob.setText(departDay);
            String nationality = getCountryName(getActivity(), loginReceive.getCountryCode());
            passenger1_nationality.setText(nationality);

            String ff = BaseFragment.getCountryName(getActivity(), loginReceive.getCountryCode());

            final Switch imFlyingSwitch = (Switch) headerView.findViewById(R.id.imFlyingSwitch);

            imFlyingSwitch.setText("");
            imFlyingSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked) {

                        imFlying = true;
                        imFlyingSwitch.setSwitchTextAppearance(getActivity(), R.style.switch_on);

                        updateFirstTraveller(imFlying);

                        appendPassengerInfo();
                        txtMainPassenger.setVisibility(View.VISIBLE);

                        passenger1_name.setTextColor(ContextCompat.getColor(getActivity(), R.color.grey));
                        passenger1_fullName.setTextColor(ContextCompat.getColor(getActivity(), R.color.grey));
                        passenger1_dob.setTextColor(ContextCompat.getColor(getActivity(), R.color.grey));
                        passenger1_nationality.setTextColor(ContextCompat.getColor(getActivity(), R.color.grey));
                        passenger1_name.setTextColor(ContextCompat.getColor(getActivity(), R.color.grey));

                    } else {

                        imFlying = false;
                        updateFirstTraveller(imFlying);
                        imFlyingSwitch.setSwitchTextAppearance(getActivity(), R.style.switch_off);

                        appendPassengerInfo();
                        txtMainPassenger.setVisibility(View.INVISIBLE);

                        passenger1_name.setTextColor(ContextCompat.getColor(getActivity(), R.color.dark_lvl5));
                        passenger1_fullName.setTextColor(ContextCompat.getColor(getActivity(), R.color.dark_lvl2));
                        passenger1_dob.setTextColor(ContextCompat.getColor(getActivity(), R.color.dark_lvl2));
                        passenger1_nationality.setTextColor(ContextCompat.getColor(getActivity(), R.color.dark_lvl2));
                        passenger1_name.setTextColor(ContextCompat.getColor(getActivity(), R.color.dark_lvl2));

                    }
                }
            });

        }


        if (addFooter) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View footerView = layoutInflater.inflate(R.layout.contact_person, null);
            addedPassengerList.addFooterView(footerView);
            addFooter = false;

            contactSalutation = (TextView) footerView.findViewById(R.id.contactSalutation);
            contactNationality = (TextView) footerView.findViewById(R.id.contactNationality);
            contactGivenName = (TextView) footerView.findViewById(R.id.contactGivenName);
            contactFamilyName = (TextView) footerView.findViewById(R.id.contactFamilyName);
            contactEmail = (EditText) footerView.findViewById(R.id.contactEmail);

            contactMobileNo = (EditText) footerView.findViewById(R.id.contactMobileNo);
            contactGivenName.setText(loginReceive.getFirstName());
            contactFamilyName.setText(loginReceive.getLastName());
            contactSalutation.setTag(loginReceive.getSalutation());

            if (loginReceive.getSalutation().equals("1")) {
                contactSalutation.setText(getString(R.string.title_mr));
            } else {
                contactSalutation.setText(getString(R.string.title_ms));
            }

            contactMobileNo.setTag("+" + loginReceive.getMobilePhone());
            contactMobileNo.setText("+" + loginReceive.getMobilePhone());

            contactEmail.setText(loginReceive.getUserName());

            /*Set Nationality*/
            HashMap<String, String> init = pref.getSavedCountry();
            String countryName = init.get(SharedPrefManager.SAVED_COUNTRY);

            HashMap<String, String> init2 = pref.getSavedCountryCode();
            String countryCode = init2.get(SharedPrefManager.SAVED_COUNTRY_CODE);

            contactNationality.setText(countryName);
            contactNationality.setTag(countryCode);


            //title
            //contactSalutation.setOnClickListener(new View.OnClickListener() {
            //    @Override
            //    public void onClick(View v) {
            //        showCountrySelector(getActivity(), getTitleList(), "TITLE", 99);
            //    }
            //});

            //title
            contactNationality.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showCountrySelector(getActivity(), getCountryList(), "NATIONALITY", 99);
                }
            });
        }

        addedPassengerList.setAdapter(passengerListAdapter);

    }

    public void datePickerSetting() {
        //datePicker setting
        dob_picker = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dob_picker.setYearRange(calendar.get(Calendar.YEAR) - 80, calendar.get(Calendar.YEAR));
        dob_picker.setAccentColor(ContextCompat.getColor(getActivity(), R.color.default_theme_colour));

        Calendar output = Calendar.getInstance();
        output.set(Calendar.YEAR, output.get(Calendar.YEAR));
        output.set(Calendar.DAY_OF_MONTH, output.get(Calendar.DAY_OF_MONTH));
        output.set(Calendar.MONTH, output.get(Calendar.MONTH));
        dob_picker.setMaxDate(output);
    }

    public void clickDate(Integer tp) {

        if (checkFragmentAdded()) {
            travellerPosition = tp;
            dob_picker.show(getActivity().getFragmentManager(), DATEPICKER_TAG);
        }
    }

    /*Country selector - > need to move to main activity*/
    public void showCountrySelector(Activity act, ArrayList constParam, String data, Integer tp) {
        if (act != null) {
            try {

                travellerPosition = tp;
                android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();

                SelectionListFragment countryListDialogFragment = SelectionListFragment.newInstance(data, "na", false);
                countryListDialogFragment.setTargetFragment(PassengerInfoFragment.this, 0);
                countryListDialogFragment.show(fm, "countryListDialogFragment");


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void backToFirst(final Activity act) {

        new SweetAlertDialog(act, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(AnalyticsApplication.getContext().getString(R.string.addons_alert))
                .setContentText(AnalyticsApplication.getContext().getString(R.string.content))
                .showCancelButton(true)
                .setCancelText(AnalyticsApplication.getContext().getString(R.string.no))
                .setConfirmText(AnalyticsApplication.getContext().getString(R.string.yes))
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {

                        Intent intent = new Intent(act, TabActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("SEARCH", "SEARCH");
                        act.startActivity(intent);
                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                    }
                })
                .show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        } else {

            if (data.getStringExtra("LIST_NAME").equals("NATIONALITY")) {
                DropDownItem selected = data.getParcelableExtra("NATIONALITY");

                if (travellerPosition == 99) {
                    contactNationality.setText(selected.getText());
                    contactNationality.setTag(splitCountryDialingCode("CountryCode", selected.getCode()));
                } else {
                    travellerInfo.getList().get(travellerPosition).setNationality(selected.getText());
                    travellerInfo.getList().get(travellerPosition).setNationalityCode(splitCountryDialingCode("CountryCode", selected.getCode()));
                    travellerInfo.getList().get(travellerPosition).setNationalityError("N");
                    passengerListAdapter.updateTravellerInfo(travellerInfo);
                }

            } else if (data.getStringExtra("LIST_NAME").equals("TITLE")) {
                DropDownItem selected = data.getParcelableExtra("TITLE");

                if (travellerPosition == 99) {
                    contactSalutation.setText(selected.getText());
                    contactSalutation.setTag(selected.getCode());
                } else {
                    travellerInfo.getList().get(travellerPosition).setSalutationCode(selected.getCode());
                    travellerInfo.getList().get(travellerPosition).setSalutation(selected.getText());
                    travellerInfo.getList().get(travellerPosition).setSalutationError("N");
                }
                passengerListAdapter.updateTravellerInfo(travellerInfo);

            } else if (data.getStringExtra("LIST_NAME").equals("GENDER")) {
                DropDownItem selected = data.getParcelableExtra("GENDER");

                travellerInfo.getList().get(travellerPosition).setGenderCode(selected.getCode());
                travellerInfo.getList().get(travellerPosition).setGender(selected.getText());
                travellerInfo.getList().get(travellerPosition).setGenderError("N");

                passengerListAdapter.updateTravellerInfo(travellerInfo);

            }
        }
    }

    public static void onSubmitPassenger(Activity act) {
        obj = passengerListAdapter.returnTravellerInfo();
        emailValidation = isEmailValid(contactEmail.getText().toString());

        if (emailValidation) {
            if (validatePassengerInfo(obj, act)) {
                HashMap<String, String> dicMap = new HashMap<String, String>();
                //user info
                int loopAdult = 0;
                int loopChild = 0;
                int loopInfant = 0;

                for (int g = 0; g < obj.getList().size(); g++) {

                    if (obj.getList().get(g).getType().equals("Adult")) {
                        dicMap.put("AdultTitle" + loopAdult, obj.getList().get(g).getSalutationCode());
                        dicMap.put("AdultGender" + loopAdult, checkGender(obj.getList().get(g).getSalutationCode()));
                        dicMap.put("AdultFirstName" + loopAdult, obj.getList().get(g).getGiven_name());
                        dicMap.put("AdultLastName" + loopAdult, obj.getList().get(g).getFamily_name());
                        dicMap.put("AdultNationality" + loopAdult, obj.getList().get(g).getNationalityCode());
                        dicMap.put("AdultDOB" + loopAdult, obj.getList().get(g).getDobCode());
                        loopAdult++;
                    }

                    if (obj.getList().get(g).getType().equals("Child")) {
                        dicMap.put("ChildTitle" + loopChild, obj.getList().get(g).getGenderCode());
                        dicMap.put("ChildGender" + loopChild, checkGender(obj.getList().get(g).getGenderCode()));
                        dicMap.put("ChildFirstName" + loopChild, obj.getList().get(g).getGiven_name());
                        dicMap.put("ChildLastName" + loopChild, obj.getList().get(g).getFamily_name());
                        dicMap.put("ChildNationality" + loopChild, obj.getList().get(g).getNationalityCode());
                        dicMap.put("ChildDOB" + loopChild, obj.getList().get(g).getDobCode());
                        loopChild++;
                    }

                    if (obj.getList().get(g).getType().equals("Infant")) {
                        dicMap.put("InfantTravelingWith" + loopInfant, obj.getList().get(g).getTravellingWithCode());
                        dicMap.put("InfantTitle" + loopInfant, obj.getList().get(g).getGenderCode());
                        dicMap.put("InfantGender" + loopInfant, obj.getList().get(g).getGenderCode());
                        dicMap.put("InfantFirstName" + loopInfant, obj.getList().get(g).getGiven_name());
                        dicMap.put("InfantLastName" + loopInfant, obj.getList().get(g).getFamily_name());
                        dicMap.put("InfantNationality" + loopInfant, obj.getList().get(g).getNationalityCode());
                        dicMap.put("InfantDOB" + loopInfant, obj.getList().get(g).getDobCode());
                        loopInfant++;
                    }
                }

                dicMap.put("ContactTitle", contactSalutation.getTag().toString());
                dicMap.put("ContactFirstName", contactGivenName.getText().toString());
                dicMap.put("ContactLastName", contactFamilyName.getText().toString());
                dicMap.put("ContactOtherPhone", contactMobileNo.getText().toString());
                dicMap.put("ContactEmailAddress", contactEmail.getText().toString());
                dicMap.put("ContactCountryCode", contactNationality.getTag().toString());
                dicMap.put("ContactAddressLine1", "-");

                //contact info
                /*if (true) {
                    dicMap.put("ContactTitle", "1");
                    dicMap.put("ContactFirstName", "ImalPasha");
                    dicMap.put("ContactLastName", "Zainal");
                    dicMap.put("ContactOtherPhone", "01131763778");
                    dicMap.put("ContactEmailAddress", "imalpasha@gmail.com");
                    dicMap.put("ContactCountryCode", "MY");
                    dicMap.put("ContactAddressLine1", "-");
                } else {

                }*/


                //signature
                dicMap.put("NotificationPreference", "None");
                dicMap.put("Signature", signature);
                dicMap.put("UserName", username);
                dicMap.put("Token", token);

                dicMap.put("FROM_WHICH", "UPDATE_TRAVELLER");


                initiateLoading(act);
                presenter2.onTravellerUpdateRequest(dicMap);
            }
        } else {
            String message_alert = AnalyticsApplication.getContext().getString(R.string.message_alert);
            croutonAlert(act, message_alert);
        }
    }

    public static String checkGender(String genderCode) {
        String gender;
        if (genderCode.equals("1")) {
            gender = "Male";
        } else {
            gender = "Female";
        }
        return gender;
    }

    @Override
    public void onSubmitTravellerReceive(UpdateTravellerReceive updateTravellerReceive) {

        dismissLoading();

        Boolean status = MainController.getRequestStatus(updateTravellerReceive.getStatus(), updateTravellerReceive.getMessage(), getActivity());
        if (status) {

            int currentSize = obj.getList().size();

            for (int c = 0; c < currentSize; c++) {
                obj = filterInfant(obj);
            }

            Gson gsonUserInfo = new Gson();
            String traveller = gsonUserInfo.toJson(obj);
            RealmObjectController.saveTravellerCache(getActivity(), traveller);

            Intent addOn = new Intent(getActivity(), AddOnActivity.class);
            addOn.putExtra("TRAVELLER", new Gson().toJson(obj));
            getActivity().startActivity(addOn);
        }

    }


    public TravellerInfo filterInfant(TravellerInfo obj) {

        TravellerInfo fitlerObj;

        for (int toRemove = 0; toRemove < obj.getList().size(); toRemove++) {
            if (obj.getList().get(toRemove).getType().equals("Infant")) {
                obj.getList().remove(toRemove);
            }
        }

        fitlerObj = obj;

        return fitlerObj;
    }

    //validate child and infant age (ADD)
    public static Boolean validatePassengerInfo(TravellerInfo obj, Activity act) {

        Boolean proceed = true;

        for (int v = 0; v < obj.getList().size(); v++) {
            if (obj.getList().get(v).getGiven_name() == null) {
                obj.getList().get(v).setGivenNameError("Y");
                proceed = false;
                Log.e("status", "1");
            }
            if (obj.getList().get(v).getFamily_name() == null) {
                proceed = false;
                obj.getList().get(v).setFamilyNameError("Y");
                Log.e("status", "2");
            }
            if (obj.getList().get(v).getType().equals("Adult")) {
                if (obj.getList().get(v).getSalutationCode() == null) {
                    proceed = false;
                    obj.getList().get(v).setSalutationError("Y");
                    Log.e("status", "3");
                }
            }
            if (obj.getList().get(v).getNationalityCode() == null) {
                proceed = false;
                obj.getList().get(v).setNationalityError("Y");
                Log.e("status", "4");
            }
            if (obj.getList().get(v).getDobCode() == null) {
                proceed = false;
                obj.getList().get(v).setDobError("Y");
                Log.e("status", "5");
            }
            if (obj.getList().get(v).getType().equals("Infant")) {
                if (obj.getList().get(v).getTravellingWithCode() == null) {
                    proceed = false;
                    obj.getList().get(v).setTravellingWithError("Y");
                }
                if (obj.getList().get(v).getGender() == null) {
                    proceed = false;
                    obj.getList().get(v).setGenderError("Y");
                }
                Log.e("status", "6");
            }

        }
        if (!proceed) {
            String alert_message2 = AnalyticsApplication.getContext().getString(R.string.alert_message2);

            croutonAlert(act, alert_message2);
            passengerListAdapter.notifyError(obj);
        }

        return proceed;
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {

        //Reconstruct DOB
        String varMonth = "";
        String varDay = "";

        if (month < 9) {
            varMonth = "0";
        }
        if (day < 10) {
            varDay = "0";
        }

        //check age
        String travellerType = travellerInfo.getList().get(travellerPosition).getType();

        int age;

        if (travellerType.equals("Adult")) {
            age = getAge(year, month, day);
            if (age > 12) {
                /*travellerInfo.getList().get(travellerPosition).setDob(day + "/" + (month + 1) + "/" + year);
                travellerInfo.getList().get(travellerPosition).setDobCode(year + "-" + varMonth + "" + (month + 1) + "-" + varDay + "" + day);
                travellerInfo.getList().get(travellerPosition).setDobError("N");
                passengerListAdapter.updateTravellerInfo(travellerInfo);*/

                statusAge = true;

            } else {
                statusAge = false;
                String error_message = getString(R.string.error_message1);
                String error_title = getString(R.string.error_title);
                setAlertDialog(getActivity(), error_message, error_title);
            }
        } else if (travellerType.equals("Child")) {
            age = getAge(year, month, day);
            if (age > 2 && age <= 12) {
                /*travellerInfo.getList().get(travellerPosition).setDob(day + "/" + (month + 1) + "/" + year);
                travellerInfo.getList().get(travellerPosition).setDobCode(year + "-" + varMonth + "" + (month + 1) + "-" + varDay + "" + day);
                travellerInfo.getList().get(travellerPosition).setDobError("N");

                passengerListAdapter.updateTravellerInfo(travellerInfo);*/
                statusAge = true;

            } else {
                statusAge = false;
                String error_message = getString(R.string.error_message2);
                String error_title = getString(R.string.error_title);
                setAlertDialog(getActivity(), error_message, error_title);
            }
        } else if (travellerType.equals("Infant")) {
            age = getAgeInDay(year, month, day);
            if (age >= 9 && age <= 730) {
                statusAge = true;
                /*travellerInfo.getList().get(travellerPosition).setDob(day + "/" + (month + 1) + "/" + year);
                travellerInfo.getList().get(travellerPosition).setDobCode(year + "-" + varMonth + "" + (month + 1) + "-" + varDay + "" + day);
                travellerInfo.getList().get(travellerPosition).setDobError("N");

                passengerListAdapter.updateTravellerInfo(travellerInfo);*/

            } else {
                statusAge = false;
                String error_message = getString(R.string.error_message3);
                String error_title = getString(R.string.error_title);
                setAlertDialog(getActivity(), error_message, error_title);
            }
        }

        if (statusAge) {
            travellerInfo.getList().get(travellerPosition).setDob(day + "/" + (month + 1) + "/" + year);
            travellerInfo.getList().get(travellerPosition).setDobCode(year + "-" + varMonth + "" + (month + 1) + "-" + varDay + "" + day);
            travellerInfo.getList().get(travellerPosition).setDobError("N");
            passengerListAdapter.updateTravellerInfo(travellerInfo);
        }
    }

    public int getAge(int _year, int _month, int _day) {

        GregorianCalendar cal = new GregorianCalendar();
        int y, m, d, a;

        //change this to flight departure date
        Log.e("departure_date", departure_date);

        y = cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH);
        d = cal.get(Calendar.DAY_OF_MONTH);

        cal.set(_year, _month, _day);

        a = y - cal.get(Calendar.YEAR);
        if ((m < cal.get(Calendar.MONTH))
                || ((m == cal.get(Calendar.MONTH)) && (d < cal
                .get(Calendar.DAY_OF_MONTH)))) {
            --a;
        }
        if (a < 0)
            throw new IllegalArgumentException("Age < 0");
        return a;
    }

    public int getAgeInDay(int y, int m, int d) {

        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();

        cal.set(y, m, d);
        Date birthday = cal.getTime();

        long dateSubtract = today.getTime() - birthday.getTime();
        long time = 1000 * 60 * 60 * 24;
        long x = dateSubtract / time;

        int age = (int) (long) x;

        Log.e("c", String.valueOf(x));

        return age;
    }

    public static boolean isEmailValid(String email) {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        return matcher.matches();
    }

    public void backToTabActivity() {

        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText(getString(R.string.addons_alert))
                .setContentText(AnalyticsApplication.getContext().getString(R.string.content))
                .showCancelButton(true)
                .setCancelText(AnalyticsApplication.getContext().getString(R.string.no))
                .setConfirmText(AnalyticsApplication.getContext().getString(R.string.yes))
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {

                        Intent intent = new Intent(getActivity(), TabActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("SEARCH", "SEARCH");
                        getActivity().startActivity(intent);
                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                    }
                })
                .show();
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
        Log.e("Date", String.valueOf(date));

        return date;
    }

    public String convertDate(Date data) {

        SimpleDateFormat reformat = new SimpleDateFormat("dd MMM yyyy");
        return reformat.format(data);

    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();

    }

}
