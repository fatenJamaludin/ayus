package com.app.tbd.ui.Activity.BookingFlight.Add;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.app.tbd.MainController;
import com.app.tbd.R;
import com.app.tbd.application.MainApplication;
import com.app.tbd.ui.Activity.BookingFlight.AnotherList;
import com.app.tbd.ui.Activity.BookingFlight.Checkout.CheckoutActivity;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Activity.BookingFlight.FlightListDepartActivity;
import com.app.tbd.ui.Activity.BookingFlight.FlightListPagerAdapter;
import com.app.tbd.ui.Activity.BookingFlight.PassengerInfoActivity;
import com.app.tbd.ui.Activity.SlidePage.SlidingTabLayout;
import com.app.tbd.ui.Activity.TAB.ViewPagerAdapter;
import com.app.tbd.ui.Model.Adapter.PassengerListAdapter;
import com.app.tbd.ui.Model.FlightListAdapterV2;
import com.app.tbd.ui.Model.JSON.AddOnInfo;
import com.app.tbd.ui.Model.JSON.AddonCached;
import com.app.tbd.ui.Model.JSON.FlightInProgressJSON;
import com.app.tbd.ui.Model.JSON.PerColum;
import com.app.tbd.ui.Model.JSON.PerRowObj;
import com.app.tbd.ui.Model.JSON.SeatCached;
import com.app.tbd.ui.Model.JSON.SelectedSeatInfoGSON;
import com.app.tbd.ui.Model.JSON.TravellerCached;
import com.app.tbd.ui.Model.JSON.TravellerInfo;
import com.app.tbd.ui.Model.JSON.TravellerSeat;
import com.app.tbd.ui.Model.JSON.TravellerSeatsSegmentClass;
import com.app.tbd.ui.Model.JSON.UserInfoJSON;
import com.app.tbd.ui.Model.Receive.AddOnReceiveV2;
import com.app.tbd.ui.Model.Receive.SearchFlightReceive;
import com.app.tbd.ui.Model.Receive.SeatAssignReceive;
import com.app.tbd.ui.Model.Receive.SeatInfoReceive;
import com.app.tbd.ui.Model.Receive.TBD.LoginReceive;
import com.app.tbd.ui.Model.Receive.ViewUserReceive;
import com.app.tbd.ui.Model.Request.SeatInfo;
import com.app.tbd.ui.Model.Request.SeatInfoRequest;
import com.app.tbd.ui.Model.Request.SeatSetup;
import com.app.tbd.ui.Module.SeatModule;
import com.app.tbd.ui.Presenter.BookingPresenter;
import com.app.tbd.ui.Realm.RealmObjectController;
import com.app.tbd.utils.ExpandAbleGridView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.inject.Inject;

import butterknife.ButterKnife;

import butterknife.InjectView;
import io.realm.Realm;
import io.realm.RealmResults;


public class SeatTabFragment extends BaseFragment implements BookingPresenter.SeatView {

    @Inject
    BookingPresenter presenter;

    @InjectView(R.id.pager)
    ViewPager pager;

    @InjectView(R.id.tabs)
    SlidingTabLayout tabs;

    static BookingPresenter staticPresenter;
    private String signature, token, username;
    private ViewPagerAdapter adapter;
    private SeatListAdapter sAdapter;
    private SeatInfoReceive seatInfoReceive1, seatInfoReceive2, seatInfoReceive3, seatInfoReceive4;
    private ArrayList<String> seatTabList;
    private AlertDialog dialog;
    private SeatAdapter seatAdapter1, seatAdapter2, seatAdapter3, seatAdapter4;
    private SeatAdapter2 seatAdapter11, seatAdapter22, seatAdapter33, seatAdapter44;
    TravellerInfo travellerInfo;
    ArrayList<ArrayList<TravellerSeat>> travellerSeatsSegment = new ArrayList<ArrayList<TravellerSeat>>();
    static PassengerSeatListAdapter passengerSeatListAdapter;

    int currentSeatToLoad = 0;
    int totalSegment = 0;

    ArrayList<String> flight3X2 = new ArrayList<String>(Arrays.asList("A", "F", "E"));
    ArrayList<Integer> seatX = new ArrayList<Integer>(Arrays.asList(1, 3, 5, 9, 12, 15, 17, 19, 21));

    ListView seatList3;
    ListView seatList4;
    Boolean loaded2 = false;
    Boolean loaded3 = false;
    ArrayList<String> flightType = new ArrayList<String>();

    public static SeatTabFragment newInstance(Bundle bundle) {

        SeatTabFragment fragment = new SeatTabFragment();
        fragment.setArguments(bundle);
        return fragment;

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RealmObjectController.clearCachedResult(getActivity());
        MainApplication.get(getActivity()).createScopedGraph(new SeatModule(this)).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.seat_page, container, false);
        ButterKnife.inject(this, view);

        dataSetup();

        return view;
    }

    public void dataSetup() {

        Bundle bundle = getArguments();
        String obj = bundle.getString("TRAVELLER");
        String added_info = bundle.getString("ADDED_INFO");

        travellerInfo = (new Gson()).fromJson(obj, TravellerInfo.class);
        AddOnReceiveV2 addOnReceive = (new Gson()).fromJson(added_info, AddOnReceiveV2.class);

        staticPresenter = presenter;

        if (bundle.containsKey("SEAT_INFO_0")) {
            String seatInfo1 = bundle.getString("SEAT_INFO_0");
            seatInfoReceive1 = (new Gson()).fromJson(seatInfo1, SeatInfoReceive.class);
        }

        if (bundle.containsKey("SEAT_INFO_1")) {
            String seatInfo2 = bundle.getString("SEAT_INFO_1");
            seatInfoReceive2 = (new Gson()).fromJson(seatInfo2, SeatInfoReceive.class);
        }

        Realm realm = RealmObjectController.getRealmInstance(getActivity());
        final RealmResults<UserInfoJSON> result2 = realm.where(UserInfoJSON.class).findAll();
        //final RealmResults<SeatCached> seatCacheds = realm.where(SeatCached.class).findAll();
        //final RealmResults<TravellerCached> travellerCaches = realm.where(TravellerCached.class).findAll();

        LoginReceive loginReceive = (new Gson()).fromJson(result2.get(0).getUserInfo(), LoginReceive.class);
        //seatInfoReceive1 = (new Gson()).fromJson(seatCacheds.get(0).getSeatCached(), SeatInfoReceive.class);
        //travellerInfo = (new Gson()).fromJson(travellerCaches.get(0).getTraveller(), TravellerInfo.class);

        token = loginReceive.getToken();
        signature = loginReceive.getSignature();
        username = loginReceive.getUserName();

        startPagination(addOnReceive);
    }

    public void startPagination(AddOnReceiveV2 addOnReceiveV2) {

        initiateLoading(getActivity());

        //check highest y value
        //check for the highest faretype

        //get flight info
        seatTabList = new ArrayList<String>();

        Realm realm = RealmObjectController.getRealmInstance(getActivity());
        final RealmResults<FlightInProgressJSON> result3 = realm.where(FlightInProgressJSON.class).findAll();
        SearchFlightReceive searchFlightReceive = (new Gson()).fromJson(result3.get(0).getSearchFlightReceive(), SearchFlightReceive.class);
        for (int v = 0; v < searchFlightReceive.getJourneyDateMarket().size(); v++) {
            for (int h = 0; h < searchFlightReceive.getJourneyDateMarket().get(v).getJourney().get(0).getSegment().size(); h++) {

                String departCode = searchFlightReceive.getJourneyDateMarket().get(v).getJourney().get(0).getSegment().get(h).getDepartureStation();
                String arrivalCode = searchFlightReceive.getJourneyDateMarket().get(v).getJourney().get(0).getSegment().get(h).getArrivalStation();

                seatTabList.add(departCode + " - " + arrivalCode);
            }
        }

        ArrayList<ArrayList<String>> addonLoopV2 = new ArrayList<ArrayList<String>>();
        for (int journey = 0; journey < addOnReceiveV2.getJourney().size(); journey++) {
            for (int segment = 0; segment < addOnReceiveV2.getJourney().get(journey).getSegment().size(); segment++) {
                ArrayList<String> addonLoop = new ArrayList<String>();
                for (int passenger = 0; passenger < addOnReceiveV2.getJourney().get(journey).getSegment().get(segment).getPassenger().size(); passenger++) {
                    addonLoop.add(addOnReceiveV2.getJourney().get(journey).getSegment().get(segment).getPassenger().get(passenger).getSeat() + "/" + addOnReceiveV2.getJourney().get(journey).getSegment().get(segment).getPassenger().get(passenger).getCompartmentDesignator());
                }
                addonLoopV2.add(addonLoop);
            }
        }

        for (int f = 0; f < seatTabList.size(); f++) {

            ArrayList<TravellerSeat> travellerSeatsArray = new ArrayList<TravellerSeat>();

            for (int g = 0; g < travellerInfo.getList().size(); g++) {

                TravellerSeat travellerSeat = new TravellerSeat();
                travellerSeat.setTravellerName(travellerInfo.getList().get(g).getGiven_name() + " " + travellerInfo.getList().get(g).getFamily_name());
                travellerSeat.setTravellerNameShortcut(getFirstCharacter(travellerInfo.getList().get(g).getGiven_name().trim()) + getFirstCharacter(travellerInfo.getList().get(g).getFamily_name().trim()));
                travellerSeat.setTravellerSalutation(travellerInfo.getList().get(g).getSalutationCode());

                if (addonLoopV2.get(f).get(g) == null) {
                    travellerSeat.setTravellerSeatDesignator("");
                    travellerSeat.setTravellerSeatCompartment("");

                } else {

                    if (addonLoopV2.get(f).get(g).length() > 1) {
                        String[] tokens = addonLoopV2.get(f).get(g).split("/");

                        Log.e("What inside?", addonLoopV2.get(f).get(g));
                        //travellerSeat.setTravellerSeatDesignator("");
                        //travellerSeat.setTravellerSeatCompartment("");
                        travellerSeat.setTravellerSeatDesignator(tokens[0]);
                        travellerSeat.setTravellerSeatCompartment(tokens[1]);

                    } else {
                        travellerSeat.setTravellerSeatDesignator("");
                        travellerSeat.setTravellerSeatCompartment("");
                    }
                }
                travellerSeatsArray.add(travellerSeat);
            }

            travellerSeatsSegment.add(travellerSeatsArray);

        }


        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        //ExpandAbleGridView seatLayout1 = new ExpandAbleGridView(getContext());
        //ExpandAbleGridView seatLayout2 = new ExpandAbleGridView(getContext());
        ExpandAbleGridView seatLayout3 = new ExpandAbleGridView(getContext());
        ExpandAbleGridView seatLayout4 = new ExpandAbleGridView(getContext());

        View seatLayout11 = layoutInflater.inflate(R.layout.seat_list, null);
        ListView seatList1 = (ListView) seatLayout11.findViewById(R.id.seatList1);
        seatList1.setFastScrollEnabled(false);
        seatList1.setVerticalScrollBarEnabled(false);
        seatList1.setSelector(new ColorDrawable(Color.TRANSPARENT));
        seatList1.setDivider(null);

        View seatLayout22 = layoutInflater.inflate(R.layout.seat_list, null);
        ListView seatList2 = (ListView) seatLayout22.findViewById(R.id.seatList1);
        seatList2.setFastScrollEnabled(false);
        seatList2.setVerticalScrollBarEnabled(false);
        seatList2.setSelector(new ColorDrawable(Color.TRANSPARENT));
        seatList2.setDivider(null);

        View seatLayout33 = layoutInflater.inflate(R.layout.seat_list, null);
        seatList3 = (ListView) seatLayout33.findViewById(R.id.seatList1);
        seatList3.setVerticalScrollBarEnabled(false);
        seatList3.setFastScrollEnabled(false);
        seatList3.setSelector(new

                ColorDrawable(Color.TRANSPARENT)

        );
        seatList3.setDivider(null);

        View seatLayout44 = layoutInflater.inflate(R.layout.seat_list, null);
        seatList4 = (ListView) seatLayout44.findViewById(R.id.seatList1);
        seatList4.setVerticalScrollBarEnabled(false);
        seatList4.setFastScrollEnabled(false);
        seatList4.setSelector(new

                ColorDrawable(Color.TRANSPARENT)

        );
        seatList4.setDivider(null);

        Vector<View> pages = new Vector<View>();
        //pages.add(footerView);
        pages.add(seatLayout11);
        pages.add(seatLayout22);
        pages.add(seatLayout33);
        pages.add(seatLayout44);


        for (int y = 0; y < seatTabList.size(); y++) {


            if (y == 0) {

                ArrayList<ArrayList<PerRowObj>> perColum1 = intiateSeat(seatInfoReceive1, y);
                if (perColum1.get(0).get(0).getFlightType()) {
                    seatAdapter1 = new SeatAdapter(getActivity(), seatInfoReceive1.getEquipmentType(), seatInfoReceive1, perColum1, SeatTabFragment.this, 0);
                    seatList1.setAdapter(seatAdapter1);
                } else {
                    seatAdapter11 = new SeatAdapter2(getActivity(), seatInfoReceive1.getEquipmentType(), seatInfoReceive1, perColum1, SeatTabFragment.this, 0);
                    seatList1.setAdapter(seatAdapter11);
                }


            } else if (y == 1) {

                ArrayList<ArrayList<PerRowObj>> perColum2 = intiateSeat(seatInfoReceive2, y);
                if (perColum2.get(0).get(0).getFlightType()) {
                    seatAdapter2 = new SeatAdapter(getActivity(), seatInfoReceive2.getEquipmentType(), seatInfoReceive2, perColum2, SeatTabFragment.this, 1);
                    seatList2.setAdapter(seatAdapter2);

                } else {
                    seatAdapter22 = new SeatAdapter2(getActivity(), seatInfoReceive2.getEquipmentType(), seatInfoReceive2, perColum2, SeatTabFragment.this, 1);
                    seatList2.setAdapter(seatAdapter22);
                }

            }
        }


        sAdapter = new

                SeatListAdapter(getContext(), pages

        );
        sAdapter.addAll(seatTabList);

        pager.setAdapter(sAdapter);
        pager.setOnTouchListener(new View.OnTouchListener()

                                 {
                                     @Override
                                     public boolean onTouch(View v, MotionEvent event) {
                                         v.getParent().requestDisallowInterceptTouchEvent(true);
                                         return false;
                                     }
                                 }

        );

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()

        {
            public void onPageScrollStateChanged(int state) {
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {

                if (position == 2) {

                    if (!loaded2) {
                        loadSeatInfo(position);
                        currentSeatToLoad = position;
                        loaded2 = true;
                    }

                } else if (position == 3) {

                    if (!loaded3) {
                        loadSeatInfo(position);
                        currentSeatToLoad = position;
                        loaded3 = true;
                    }

                }

                //fareType = position;
                //tabPosition = position;
                //recreateAdapter(getActivity(), position, searchFlightRequest);
            }
        });

        //TAB - TYPE
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width
        tabs.setCustomTabView(R.layout.type_tab2, 0);

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return ContextCompat.getColor(getActivity(), R.color.white);
            }
        });

        tabs.setViewPager(pager);

        //set passenger obj
        //auto fill here

        dismissLoading();

    }

    public String getFirstCharacter(String givenString) {

        try {
            String toBeCapped = givenString.trim();

            String[] tokens = toBeCapped.split(" "); // equal to white space
            toBeCapped = "";

            //loop each string - convert first letter to capital letter
            for (int i = 0; i < 1; i++) {
                char capLetter = Character.toUpperCase(tokens[i].charAt(0));
                toBeCapped += capLetter;
            }

            //remove white space at start & end
            toBeCapped = toBeCapped.trim();

            return toBeCapped;
        } catch (Exception e) {

        }
        return "NA";
    }

    public void loadSeatInfo(int flightSegment) {
        initiateLoading(getActivity());

        SeatInfoRequest seatInfoRequest = new SeatInfoRequest();
        seatInfoRequest.setToken(token);
        seatInfoRequest.setFlight(Integer.toString(flightSegment));
        seatInfoRequest.setSignature(signature);
        seatInfoRequest.setUserName(username);

        presenter.onSeatInfoRequest(seatInfoRequest);
    }

    @Override
    public void onSeatInfoReceive(SeatInfoReceive obj) {

        dismissLoading();
        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {
            startPagination3And4(obj, currentSeatToLoad);
        }
    }

    public void startPagination3And4(SeatInfoReceive obj, int position) {

        ArrayList<ArrayList<PerRowObj>> perColum3 = intiateSeat(obj, position);

        if (position == 2) {
            if (perColum3.get(0).get(0).getFlightType()) {
                seatAdapter3 = new SeatAdapter(getActivity(), obj.getEquipmentType(), obj, perColum3, SeatTabFragment.this, 2);
                seatList3.setAdapter(seatAdapter3);
            } else {
                seatAdapter33 = new SeatAdapter2(getActivity(), obj.getEquipmentType(), obj, perColum3, SeatTabFragment.this, 2);
                seatList3.setAdapter(seatAdapter33);
            }
        } else {
            if (perColum3.get(0).get(0).getFlightType()) {
                seatAdapter4 = new SeatAdapter(getActivity(), obj.getEquipmentType(), obj, perColum3, SeatTabFragment.this, 3);
                seatList4.setAdapter(seatAdapter4);
            } else {
                seatAdapter44 = new SeatAdapter2(getActivity(), obj.getEquipmentType(), obj, perColum3, SeatTabFragment.this, 3);
                seatList4.setAdapter(seatAdapter44);
            }
        }

    }

    public ArrayList<ArrayList<PerRowObj>> intiateSeat(SeatInfoReceive seatInfo, int segment) {

        boolean seat6Row = false;

        ArrayList<ArrayList<PerRowObj>> perColum2 = new ArrayList<ArrayList<PerRowObj>>();

        String compartment = "";
        if (seatInfo.getCompartmentInfo().size() > 0) {
            if (seatInfo.getCompartmentInfo().get(0).getCompartmentDesignator().equals("C")) {
                if (seatInfo.getCompartmentInfo().get(0).getAvailableUnits().equals("0")) {
                    // normal flight
                    compartment = "Y";
                    //use compartment Y
                } else {
                    // flatbed
                    compartment = "C";
                    //user compartment C
                }
            } else {
                compartment = "Y";
            }
        }

        int getYnegative2 = 0;

        for (int v = 0; v < seatInfo.getSeatInfo().size(); v++) {

            if (seatInfo.getSeatInfo().get(v).getY().equals("-2")) {
                getYnegative2++;
            }
        }

        if (getYnegative2 < 7) {
            seat6Row = true;
        }

        /*String[] alphabet6 = {"A", "B", "C", "D", "E", "F"};
        String[] alphabet9 = {"A", "B", "C", "D", "F", "G", "H", "J", "K"};
*/
        int inc;
        if (seat6Row) {
            inc = 0;
        } else {
            inc = 6;
        }

        //lowest & highest Y
        for (int v = -2; v < 300 + 1; v++) {

            try {
                ArrayList<PerRowObj> perRowObjsArray = new ArrayList<PerRowObj>();
                Boolean stopInc = false;
                ArrayList<Integer> seatAdded = new ArrayList<Integer>();


                for (int u = 0; u < seatInfo.getSeatInfo().size(); u++) {

                    if (seatInfo.getSeatInfo().get(u).getY().equals(Integer.toString(v))) {

                        Boolean done = false;
                        //lowest & highest X
                        for (int x = 0; x < 50 + 1; x++) {
                            //COLUMM SET TO 6 & 9
                            try {
                                String whatX = seatInfo.getSeatInfo().get(u).getX();
                                String currentXLoop = Integer.toString(x);

                                if (whatX.equals(currentXLoop) && seatInfo.getSeatInfo().get(u).getSeatType().equals("NS") && seatInfo.getSeatInfo().get(u).getCompartmentDesignator().equals(compartment)) {

                                    int loop = 0;
                                    if (x == 3 && seatAdded.size() == 0) {
                                        loop = 1;
                                    } else if (x == 5 && seatAdded.size() < 2) {
                                        loop = 2 - seatAdded.size();
                                    } else if (x == 9 && seatAdded.size() < 3) {
                                        loop = 3 - seatAdded.size();
                                    } else if (x == 11 && seatAdded.size() < 4) {
                                        loop = 4 - seatAdded.size();
                                    } else if (x == 13 && seatAdded.size() < 5) {
                                        loop = 5 - seatAdded.size();
                                    } else if (x == 17 && seatAdded.size() < 6) {
                                        loop = 6 - seatAdded.size();
                                    } else if (x == 19 && seatAdded.size() < 7) {
                                        loop = 7 - seatAdded.size();
                                    } else if (x == 21 && seatAdded.size() < 8) {
                                        loop = 8 - seatAdded.size();
                                    }

                                    for (int h = 0; h < loop; h++) {
                                        PerRowObj perRowObj2 = new PerRowObj();
                                        perRowObj2.setSeatType("ES");
                                        perRowObj2.setSeatDesignator("X");
                                        perRowObj2.setSeatGroup("X");
                                        perRowObj2.setFlightType(seat6Row);
                                        perRowObj2.setSeatAvailability("X");
                                        perRowObj2.setCompartment("X");
                                        perRowObj2.setSelectedByName("");

                                        if (seatInfo.getSeatInfo().get(u).getSeatAvailability().equals("Reserved") || seatInfo.getSeatInfo().get(u).getSeatAvailability().equals("HeldForAnotherSession")) {
                                            perRowObj2.setSelectedBy(999);
                                        } else if (seatInfo.getSeatInfo().get(u).getSeatAvailability().equals("Restricted")) {
                                            perRowObj2.setSelectedBy(9);
                                        } else {
                                            perRowObj2.setSelectedBy(99);
                                        }
                                        //perRowObj2.setSelectedBy(99);
                                        perRowObjsArray.add(perRowObj2);
                                        seatAdded.add(x);
                                    }

                                    PerRowObj perRowObj = new PerRowObj();
                                    perRowObj.setSeatType(seatInfo.getSeatInfo().get(u).getSeatType());
                                    perRowObj.setSeatGroup(seatInfo.getSeatInfo().get(u).getSeatGroup());
                                    if (seatInfo.getSeatInfo().get(u).getSeatAvailability().equals("Reserved")) {
                                        perRowObj.setSelectedBy(999);

                                    } else if (seatInfo.getSeatInfo().get(u).getSeatAvailability().equals("Restricted")) {
                                        perRowObj.setSelectedBy(9);
                                    } else {
                                        for (int passenger = 0; passenger < travellerSeatsSegment.get(segment).size(); passenger++) {
                                            if (travellerSeatsSegment.get(segment).get(passenger).getTravellerSeatDesignator().equals(seatInfo.getSeatInfo().get(u).getSeatDesignator())) {
                                                perRowObj.setSelectedByName(travellerSeatsSegment.get(segment).get(passenger).getTravellerNameShortcut());
                                                perRowObj.setSelectedBy(passenger);
                                                break;
                                            } else {
                                                perRowObj.setSelectedBy(99);
                                            }
                                        }
                                    }

                                    perRowObj.setSeatAvailability(seatInfo.getSeatInfo().get(u).getSeatAvailability());
                                    perRowObj.setCompartment(seatInfo.getSeatInfo().get(u).getCompartmentDesignator());
                                    perRowObj.setSeatDesignator(seatInfo.getSeatInfo().get(u).getSeatDesignator());

                                    String lID = "";
                                    if (seatInfo.getSeatInfo().get(u).getSeatDesignator() != null && seatInfo.getSeatInfo().get(u).getSeatDesignator().length() != 0) {
                                        lID = seatInfo.getSeatInfo().get(u).getSeatDesignator().substring(0, seatInfo.getSeatInfo().get(u).getSeatDesignator().length() - 1);
                                    }
                                    perRowObj.setFlightType(seat6Row);
                                    perRowObj.setRowID(lID);
                                    perRowObj.setY(Integer.toString(v));
                                    perRowObj.setSeatType(seatInfo.getSeatInfo().get(u).getSeatType());
                                    perRowObjsArray.add(perRowObj);

                                    //get seat designator
                                    if (!stopInc) {
                                        inc++;
                                        stopInc = true;
                                    }

                                    seatAdded.add(x);

                                } else if (seatInfo.getSeatInfo().get(u).getCompartmentDesignator().equals(compartment) && whatX.equals(currentXLoop) && seatInfo.getSeatInfo().get(u).getSeatType().equals("EX") && !done) {

                                    PerRowObj perRowObj = new PerRowObj();
                                    perRowObj.setSeatType(seatInfo.getSeatInfo().get(u).getSeatType());
                                    perRowObj.setSeatDesignator("X");
                                    perRowObj.setFlightType(seat6Row);
                                    perRowObj.setSeatAvailability("X");
                                    perRowObj.setSeatGroup("X");
                                    perRowObj.setCompartment("X");
                                    perRowObj.setSelectedBy(99);
                                    perRowObjsArray.add(perRowObj);

                                    int loop = 0;
                                    if (seat6Row) {
                                        loop = 4;
                                    } else {
                                        loop = 7;
                                    }
                                    for (int h = 0; h < loop; h++) {

                                        PerRowObj perRowObj2 = new PerRowObj();
                                        perRowObj2.setSeatType("ES");
                                        perRowObj2.setSeatDesignator("X");
                                        perRowObj2.setFlightType(seat6Row);
                                        perRowObj2.setSeatGroup("X");
                                        perRowObj2.setSeatAvailability("X");
                                        perRowObj2.setCompartment("X");
                                        perRowObj2.setSelectedBy(99);
                                        perRowObjsArray.add(perRowObj2);

                                        done = true;

                                    }

                                } else if (seatInfo.getSeatInfo().get(u).getCompartmentDesignator().equals(compartment) && compartment.equals("Y") && whatX.equals(currentXLoop) && seatInfo.getSeatInfo().get(u).getSeatType().equals("LV") && !done) {

                                    PerRowObj perRowObj = new PerRowObj();
                                    perRowObj.setSeatType(seatInfo.getSeatInfo().get(u).getSeatType());
                                    perRowObj.setSeatDesignator("X");
                                    perRowObj.setSeatGroup("X");
                                    perRowObj.setFlightType(seat6Row);
                                    perRowObj.setSeatAvailability("X");
                                    perRowObj.setCompartment("X");
                                    perRowObj.setSelectedBy(99);
                                    perRowObjsArray.add(perRowObj);

                                    int loop = 0;
                                    if (seat6Row) {
                                        loop = 4;
                                    } else {
                                        loop = 7;
                                    }
                                    for (int h = 0; h < loop; h++) {

                                        PerRowObj perRowObj2 = new PerRowObj();
                                        perRowObj2.setSeatType("ES");
                                        perRowObj2.setSeatDesignator("X");
                                        perRowObj2.setFlightType(seat6Row);
                                        perRowObj2.setSeatGroup("X");
                                        perRowObj2.setSeatAvailability("X");
                                        perRowObj2.setCompartment("X");
                                        perRowObj2.setSelectedBy(99);
                                        perRowObjsArray.add(perRowObj2);

                                        done = true;

                                    }

                                } else if (whatX.equals(currentXLoop) && seatInfo.getSeatInfo().get(u).getSeatType().equals("LR")) {

                                    PerRowObj perRowObj = new PerRowObj();
                                    perRowObj.setSeatType(seatInfo.getSeatInfo().get(u).getSeatType());
                                    perRowObj.setSeatDesignator("LR");
                                    perRowObj.setFlightType(seat6Row);
                                    perRowObj.setSeatGroup("88");
                                    perRowObj.setSeatAvailability("X");
                                    perRowObj.setCompartment("X");
                                    perRowObj.setSelectedBy(99);
                                    perRowObjsArray.add(perRowObj);
                                }


                            } catch (Exception e) {
                                break;
                            }

                        }
                    }
                }


                if (perRowObjsArray.size() > 0) {
                    perColum2.add(perRowObjsArray);
                } else {
                    PerRowObj perRowObj = new PerRowObj();
                    perRowObj.setFlightType(seat6Row);
                    perRowObj.setSeatType("EMPTY");
                    perRowObj.setSeatAvailability("X");
                    perRowObj.setSelectedBy(99);
                    perRowObjsArray.add(perRowObj);
                    perColum2.add(perRowObjsArray);
                }

            } catch (Exception e) {
                break;
            }
        }

        return perColum2;

    }

    @Override
    public void onSeatAssignReceive(SeatAssignReceive obj) {

        dismissLoading();

        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {

            Gson gsonUserInfo = new Gson();
            Realm realm = RealmObjectController.getRealmInstance(getActivity());
            final RealmResults<AddonCached> result2 = realm.where(AddonCached.class).findAll();
            AddOnInfo addOnInfo = (new Gson()).fromJson(result2.get(0).getAddonInfo(), AddOnInfo.class);
            addOnInfo.setSeatSelected("Y");

            String addOnInfoString = gsonUserInfo.toJson(addOnInfo);
            RealmObjectController.saveAddOnInfo(getActivity(), addOnInfoString);

            //Intent intent = new Intent(getActivity(), AddOnActivity.class);
            //intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            //getActivity().startActivity(intent);
            //getActivity().moveTaskToBack(true);
            getActivity().finish();

        }
    }

    public static void onSeatDone(Activity act) {

        initiateLoading(act);

        Realm realm = RealmObjectController.getRealmInstance(act);
        final RealmResults<UserInfoJSON> result2 = realm.where(UserInfoJSON.class).findAll();
        LoginReceive loginReceive = (new Gson()).fromJson(result2.get(0).getUserInfo(), LoginReceive.class);

        ArrayList<ArrayList<TravellerSeat>> travellerSeatsSegmentDone = new ArrayList<ArrayList<TravellerSeat>>();

        try {

            travellerSeatsSegmentDone = passengerSeatListAdapter.getAdapterObj();

            //need to add to class first
            TravellerSeatsSegmentClass travellerSeatsSegmentClass = new TravellerSeatsSegmentClass();
            travellerSeatsSegmentClass.setTravellerSeatsSegmentDone(travellerSeatsSegmentDone);

            //save selected seat to realm - cant use data from api because included with auto assign seat
            Gson gsonUserInfo = new Gson();
            String selectedSeatInfo = gsonUserInfo.toJson(travellerSeatsSegmentClass);
            RealmObjectController.saveSelectedSeatInfo(act, selectedSeatInfo);

            HashMap<String, String> dicMap = new HashMap<String, String>();
            for (int x = 0; x < travellerSeatsSegmentDone.size(); x++) {
                for (int v = 0; v < travellerSeatsSegmentDone.get(x).size(); v++) {

                    String seatDesignator = travellerSeatsSegmentDone.get(x).get(v).getTravellerSeatDesignator();
                    String seatCompartment = travellerSeatsSegmentDone.get(x).get(v).getTravellerSeatCompartment();
                    if (seatDesignator == null) {
                    } else {
                        if (!seatDesignator.equals("")) {
                            dicMap.put("Segment" + x + "Passenger" + v, seatDesignator + "," + seatCompartment);
                        } else {
                        }
                    }
                }
            }
            dicMap.put("UserName", loginReceive.getUserName());
            dicMap.put("Token", loginReceive.getToken());
            dicMap.put("Signature", loginReceive.getSignature());
            dicMap.put("FROM_WHICH", "SEAT_ASSIGN");

            staticPresenter.onSeatAssignRequest(dicMap);

        } catch (Exception e) {
            dismissLoading();
            act.finish();
        }


    }

    public void openUserSeatImage(String designator, String whichAdapter, String pts, int segment, int y, int x, String compartment) {


        LayoutInflater li = LayoutInflater.from(getActivity());
        final View myView = li.inflate(R.layout.user_list_sear, null);

        final TextView txtDesignator = (TextView) myView.findViewById(R.id.txtDesignator);
        final TextView txtSeatPts = (TextView) myView.findViewById(R.id.txtSeatPts);
        // final LinearLayout travellerListLayout = (LinearLayout) myView.findViewById(R.id.travellerListLayout);

        txtDesignator.setText(designator);

        double newPts = Double.parseDouble(pts);
        int intPts = (int) newPts;

        txtSeatPts.setText(Integer.toString(intPts));

        appendPassengerList(myView, whichAdapter, travellerInfo, designator, pts, segment, y, x, compartment);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(myView);

        dialog = builder.create();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //lp.height = 570;
        dialog.getWindow().setAttributes(lp);
        dialog.show();

    }

    public void notifySeatAdapter(int segment, String whichAdapter, int Y, int X, int travellerPosition, String action, String name) {

        if (segment == 0) {
            if (whichAdapter.equals("1")) {
                seatAdapter1.updateSeatAdapter(Y, X, travellerPosition, action, name);
            } else {
                seatAdapter11.updateSeatAdapter(Y, X, travellerPosition, action, name);
            }
        } else if (segment == 1) {
            if (whichAdapter.equals("1")) {
                seatAdapter2.updateSeatAdapter(Y, X, travellerPosition, action, name);
            } else {
                seatAdapter22.updateSeatAdapter(Y, X, travellerPosition, action, name);
            }
        } else if (segment == 2) {
            if (whichAdapter.equals("1")) {
                seatAdapter3.updateSeatAdapter(Y, X, travellerPosition, action, name);
            } else {
                seatAdapter33.updateSeatAdapter(Y, X, travellerPosition, action, name);
            }
        } else if (segment == 3) {
            if (whichAdapter.equals("1")) {
                seatAdapter4.updateSeatAdapter(Y, X, travellerPosition, action, name);
            } else {
                seatAdapter44.updateSeatAdapter(Y, X, travellerPosition, action, name);
            }
        }
    }

    public void closeThisList() {
        dialog.dismiss();
    }

    public void appendPassengerList(View view, String whichAdapter, TravellerInfo travellerInfo, String seatDesignator, String seatPts, int segment, int y, int x, String compartment) {

        final Boolean checked = false;

        ExpandAbleGridView expandPassengerList = (ExpandAbleGridView) view.findViewById(R.id.addedPassengerList);
        passengerSeatListAdapter = new PassengerSeatListAdapter(getActivity(), whichAdapter, segment, SeatTabFragment.this, travellerSeatsSegment, travellerInfo, seatDesignator, seatPts, compartment, y, x);
        expandPassengerList.setAdapter(passengerSeatListAdapter);

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
