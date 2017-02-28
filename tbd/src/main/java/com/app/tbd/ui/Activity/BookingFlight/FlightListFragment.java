package com.app.tbd.ui.Activity.BookingFlight;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.tbd.MainController;
import com.app.tbd.R;
import com.app.tbd.application.AnalyticsApplication;
import com.app.tbd.application.MainApplication;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Activity.FragmentContainerActivity;
import com.app.tbd.ui.Activity.SlidePage.SlidingTabLayout;
import com.app.tbd.ui.Model.FlightListAdapterV2;
import com.app.tbd.ui.Model.JSON.SelectedFlightInfo;
import com.app.tbd.ui.Model.JSON.UserInfoJSON;
import com.app.tbd.ui.Model.Receive.SearchFlightReceive;
import com.app.tbd.ui.Model.Receive.SelectFlightReceive;
import com.app.tbd.ui.Model.Receive.TBD.LoginReceive;
import com.app.tbd.ui.Model.Request.SearchFlightRequest;
import com.app.tbd.ui.Model.Request.SelectFlightRequest;
import com.app.tbd.ui.Module.SelectFlightModule;
import com.app.tbd.ui.Presenter.BookingPresenter;
import com.app.tbd.ui.Realm.RealmObjectController;
import com.app.tbd.utils.SharedPrefManager;
import com.google.gson.Gson;
import com.nhaarman.listviewanimations.appearance.AnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.SwingRightInAnimationAdapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.realm.Realm;
import io.realm.RealmResults;

//import static com.app.tbd.ui.Activity.BookingFlight.FlightPriceFragment.resetAdapterFromInside;

//import static com.app.tbd.ui.Activity.BookingFlight.FlightPriceFragment.resetAdapterFromInside;

public class FlightListFragment extends BaseFragment implements BookingPresenter.ListFlightView {


    @Inject
    BookingPresenter presenter;

    @InjectView(R.id.flightLayout)
    LinearLayout flightLayout;

    @InjectView(R.id.txtDepartureStation)
    TextView txtDepartureStation;

    @InjectView(R.id.noFlightAvailable)
    LinearLayout noFlightAvailable;

    @InjectView(R.id.txtArrivalStation)
    TextView txtArrivalStation;

    @InjectView(R.id.txtDepartureDate)
    TextView txtDepartureDate;

    @InjectView(R.id.flightEcoType)
    LinearLayout flightEcoType;

    //@InjectView(R.id.flightListPrice)
    //ListView flightListPrice;

    @InjectView(R.id.btnPreviousDate)
    ImageView btnPreviousDate;

    @InjectView(R.id.btnNextDate)
    ImageView btnNextDate;

    @InjectView(R.id.pager)
    ViewPager pager;

    @InjectView(R.id.tabs)
    SlidingTabLayout tabs;

    @InjectView(R.id.flightIcon)
    ImageView flightIcon;

    @InjectView(R.id.txtDepartReturn)
    TextView txtDepartReturn;

    @InjectView(R.id.coupleAirport)
    LinearLayout coupleAirport;

    @InjectView(R.id.multipleAirportLayout)
    LinearLayout multipleAirportLayout;

    @InjectView(R.id.txtAirport1)
    TextView txtAirport1;

    @InjectView(R.id.txtAirport2)
    TextView txtAirport2;

    @InjectView(R.id.txtAirport3)
    TextView txtAirport3;

    /*@InjectView(R.id.header_one)
    LinearLayout header_one;

    @InjectView(R.id.header_two)
    LinearLayout header_two;*/

    private int fragmentContainerId;
    private static int fareType = 0;
    private FlightListPagerAdapter mAdapter;
    static BookingPresenter presenter2;
    static String username, token;

    static SearchFlightRequest searchFlightRequest = new SearchFlightRequest();
    static SelectFlightRequest selectFlightRequest = new SelectFlightRequest();
    private ArrayList<String> fareList = new ArrayList<String>();
    private SharedPrefManager pref;
    Boolean second = false;
    private static String selectedJourneySellKey = "", selectedFareKeySell = "";
    static SelectedFlightInfo selectedSegmentFare;
    static Activity classAct;
    static Boolean oneWay;
    static SearchFlightReceive flightReceive;
    FlightListAdapterV2 flightListAdapter1, flightListAdapter2, flightListAdapter3, flightListAdapter4, flightListAdapter5, flightListAdapter6;
    int tabPosition = 0;
    View view, headerInsurance, headerInsurance2;
    String selected = "";
    static Activity act;
    int totalSegment = 0;
    /*private AnimationAdapter mAnimAdapter;*/

    public static FlightListFragment newInstance(Bundle bundle) {

        FlightListFragment fragment = new FlightListFragment();
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainApplication.get(getActivity()).createScopedGraph(new SelectFlightModule(this)).inject(this);
        RealmObjectController.clearCachedResult(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.flight_detail, container, false);
        headerInsurance = inflater.inflate(R.layout.header_fare, null);
        headerInsurance2 = inflater.inflate(R.layout.header_fare2, null);

        ButterKnife.inject(this, view);
        presenter2 = presenter;
        setData();
        act = getActivity();

        RealmObjectController.clearAllRealmFileRelatedWithBooking(getActivity());

        btnPreviousDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent flightDetail = new Intent(getActivity(), FlightListDepartActivity.class);
                //getActivity().startActivity(flightDetail);

                String departureDate = searchFlightRequest.getDepartureDate0();

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat reformat = new SimpleDateFormat("dd MMM  yyyy");

                Calendar c = Calendar.getInstance();

                try {
                    c.setTime(formatter.parse(departureDate));
                    c.add(Calendar.DATE, -1);  // number of days to add
                    departureDate = formatter.format(c.getTime());  // dt is now the new date
                    searchFlightRequest.setDepartureDate0(departureDate);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Log.e("Date", searchFlightRequest.getDepartureDate0());
                second = true;
                searchFlight(searchFlightRequest);

            }
        });

        btnNextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent flightDetail = new Intent(getActivity(), FlightListDepartActivity.class);
                //getActivity().startActivity(flightDetail);
                String departureDate = searchFlightRequest.getDepartureDate0();


                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat reformat = new SimpleDateFormat("dd MMM  yyyy");

                Calendar c = Calendar.getInstance();

                try {
                    c.setTime(formatter.parse(departureDate));
                    c.add(Calendar.DATE, 1);  // number of days to add
                    departureDate = formatter.format(c.getTime());  // dt is now the new date
                    searchFlightRequest.setDepartureDate0(departureDate);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //Log.e("Date",searchFlightRequest.getDepartureDate0());
                second = true;
                searchFlight(searchFlightRequest);
            }
        });

        //Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.bottom_up);
        //flightLayout.setAnimation(animation);

        return view;
    }

    public void searchFlight(SearchFlightRequest searchFlightRequest) {

        initiateLoading(getActivity());
        presenter.onSearchFlight(searchFlightRequest);

    }

    @Override
    public void onSearchFlightReceive(SearchFlightReceive obj) {

        dismissLoading();

        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {
            Log.e("Status", obj.getStatus());
            flightReceive = obj;
            startPagination(obj);
            txtDepartureDate.setText(parseStringDate(obj.getJourneyDateMarket().get(0).getDepartureDate()));

        }
    }

    @Override
    public void onSelectFlightReceive(SelectFlightReceive obj) {

        dismissLoading();

        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {
            Log.e("Status", obj.getStatus());

            Gson gsonUserInfo = new Gson();
            String searchRequest = gsonUserInfo.toJson(searchFlightRequest);
            String searchReceive = gsonUserInfo.toJson(flightReceive);
            String selectReceive = gsonUserInfo.toJson(selectFlightRequest);
            String selectedSegment = gsonUserInfo.toJson(selectedSegmentFare);
            String total = Integer.toString(totalSegment);

            RealmObjectController.saveFlightSearchInfo(getActivity(), searchRequest, searchReceive, selectReceive, selectedSegment, total);

            Gson freeStatusOneWay = new Gson();
            String freePackageInfo = freeStatusOneWay.toJson(obj);
            RealmObjectController.saveFlightFreeInfo(getActivity(), freePackageInfo);

            Intent flightItinenary = new Intent(getActivity(), FlightItinenaryActivity.class);
            //flightItinenary.putExtra("SEARCH_FLIGHT", (new Gson()).toJson(searchFlightRequest));
            //flightItinenary.putExtra("SELECT_FLIGHT", (new Gson()).toJson(selectFlightRequest));
            //flightItinenary.putExtra("SELECT_SEGMENT", (new Gson()).toJson(selectedFragmentInfo));
            //flightItinenary.putExtra("TOTAL_SEGMENT", totalSegment);
            getActivity().startActivity(flightItinenary);

            /*Intent flightItinenary = new Intent(getActivity(), FlightItinenaryActivity.class);
            flightItinenary.putExtra("SEARCH_FLIGHT", (new Gson()).toJson(searchFlightRequest));
            flightItinenary.putExtra("SELECT_FLIGHT", (new Gson()).toJson(selectFlightRequest));
            flightItinenary.putExtra("SELECT_SEGMENT", (new Gson()).toJson(selectedSegmentFare));
            flightItinenary.putExtra("TOTAL_SEGMENT", totalSegment);

            getActivity().startActivity(flightItinenary);*/

        }

    }

    public void setData() {

        Bundle bundle = getArguments();
        String flightList = bundle.getString("FLIGHT_LIST");
        String searchFlight = bundle.getString("SEARCH_FLIGHT");
        oneWay = bundle.getBoolean("ONE_WAY");
        pref = new SharedPrefManager(getActivity());

        flightIcon.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.mipmap.icon_flight_depart));
        txtDepartReturn.setText(getResources().getString(R.string.flight_list_daparture));
        Gson gson = new Gson();

        flightReceive = gson.fromJson(flightList, SearchFlightReceive.class);
        searchFlightRequest = gson.fromJson(searchFlight, SearchFlightRequest.class);

        txtDepartureDate.setText(parseStringDateFromSearchFlight(searchFlightRequest.getDepartureDate0()));
        if (flightReceive.getJourneyDateMarket().get(0).getJourney().size() > 0) {
            if (flightReceive.getJourneyDateMarket().get(0).getJourney().get(0).getSegment().size() > 1) {
                coupleAirport.setVisibility(View.GONE);
                multipleAirportLayout.setVisibility(View.VISIBLE);
                txtAirport1.setText(flightReceive.getJourneyDateMarket().get(0).getJourney().get(0).getSegment().get(0).getDepartureStation());
                txtAirport2.setText(flightReceive.getJourneyDateMarket().get(0).getJourney().get(0).getSegment().get(0).getArrivalStation());
                txtAirport3.setText(flightReceive.getJourneyDateMarket().get(0).getJourney().get(0).getSegment().get(1).getArrivalStation());
            } else {
                coupleAirport.setVisibility(View.VISIBLE);
                multipleAirportLayout.setVisibility(View.GONE);
                txtDepartureStation.setText(searchFlightRequest.getDepartureStation0());
                txtArrivalStation.setText(searchFlightRequest.getArrivalStation0());
            }
        } else {
            coupleAirport.setVisibility(View.VISIBLE);
            multipleAirportLayout.setVisibility(View.GONE);
            txtDepartureStation.setText(searchFlightRequest.getDepartureStation0());
            txtArrivalStation.setText(searchFlightRequest.getArrivalStation0());
        }

        if (flightReceive.getJourneyDateMarket().size() == 0) {
            noFlightAvailable.setVisibility(View.VISIBLE);
        } else {
            noFlightAvailable.setVisibility(View.GONE);
            /*if (flightReceive.getJourneyDateMarket().get(0).getJourney().size() > 0) {
                totalSegment = flightReceive.getJourneyDateMarket().get(0).getJourney().get(0).getSegment().size();
            } else {
                totalSegment = 0;
            }*/
            startPagination(flightReceive);
        }

        Realm realm = RealmObjectController.getRealmInstance(getActivity());
        final RealmResults<UserInfoJSON> result2 = realm.where(UserInfoJSON.class).findAll();
        LoginReceive loginReceive = (new Gson()).fromJson(result2.get(0).getUserInfo(), LoginReceive.class);

        username = loginReceive.getUserName();
        token = loginReceive.getToken();


    }

    public void startPagination(SearchFlightReceive obj) {

        selectFlightRequest = new SelectFlightRequest();
        selectedFareKeySell = "";
        //CHECK IF FLIGHT AVAIL OR NOT ..
        if (obj.getJourneyDateMarket().get(0).getJourney().size() == 0) {
            noFlightAvailable.setVisibility(View.VISIBLE);
            pager.setAdapter(null);
            tabs.setViewPager(null);

        } else {

            noFlightAvailable.setVisibility(View.GONE);

            //check for the highest faretype
            int highest = 0;
            int takeThisPosition = 0;

            for (int v = 0; v < obj.getJourneyDateMarket().get(0).getJourney().size(); v++) {
                int h2 = obj.getJourneyDateMarket().get(0).getJourney().get(v).getFare().size();
                if (h2 > highest) {
                    highest = h2;
                    takeThisPosition = v;
                }
            }

            //dumy
            final ListView list1 = new ListView(getActivity());
            final ListView list2 = new ListView(getActivity());
            final ListView list3 = new ListView(getActivity());
            final ListView list4 = new ListView(getActivity());
            final ListView list5 = new ListView(getActivity());
            final ListView list6 = new ListView(getActivity());


            fareList = new ArrayList<String>();
            for (int i = 0; i < obj.getJourneyDateMarket().get(0).getJourney().get(takeThisPosition).getFare().size(); i++) {
                fareList.add(obj.getJourneyDateMarket().get(0).getJourney().get(takeThisPosition).getFare().get(i).getType());
            }

            list1.setDivider(null);
            list2.setDivider(null);
            list3.setDivider(null);
            list4.setDivider(null);
            list5.setDivider(null);
            list6.setDivider(null);

            Vector<View> pages = new Vector<View>();
            //pages.add(footerView);
            pages.add(list1);
            pages.add(list2);
            pages.add(list3);
            pages.add(list4);
            pages.add(list5);
            pages.add(list6);

            mAdapter = new FlightListPagerAdapter(getContext(), pages);
            mAdapter.addAll(fareList, searchFlightRequest);

            for (int y = 0; y < fareList.size(); y++) {

                ArrayList<AnotherList> listProductImages = new ArrayList<AnotherList>();
                for (int i = 0; i < obj.getJourneyDateMarket().get(0).getJourney().size(); i++) {
                    AnotherList onboard = new AnotherList();
                    for (int c = 0; c < obj.getJourneyDateMarket().get(0).getJourney().get(i).getFare().size(); c++) {
                        if (obj.getJourneyDateMarket().get(0).getJourney().get(i).getFare().get(c).getType().equals(fareList.get(y))) {
                            onboard.setList(obj.getJourneyDateMarket().get(0).getJourney().get(i));
                            listProductImages.add(onboard);
                        }
                    }
                }

                if (y == 0) {
                    if (fareList.get(y).equals("HF")) {
                        list1.addHeaderView(headerInsurance, null, false);

                    } else if (fareList.get(y).equals("PM") || fareList.get(y).equals("PP")) {
                        list1.addHeaderView(headerInsurance2, null, false);
                    }
                    flightListAdapter1 = new FlightListAdapterV2(getActivity(), listProductImages, fareList.get(y), searchFlightRequest);
                    list1.setAdapter(flightListAdapter1);

                    /*try {
                        mAnimAdapter = new SwingBottomInAnimationAdapter(new SwingRightInAnimationAdapter(flightListAdapter1));
                        mAnimAdapter.setAbsListView(list1);
                        list1.setAdapter(mAnimAdapter);
                    } catch (Exception e) {
                        list1.setAdapter(flightListAdapter1);
                    }*/


                } else if (y == 1) {
                    if (fareList.get(y).equals("HF")) {
                        list2.addHeaderView(headerInsurance, null, false);

                    } else if (fareList.get(y).equals("PM") || fareList.get(y).equals("PP")) {
                        list2.addHeaderView(headerInsurance2, null, false);
                    }
                    flightListAdapter2 = new FlightListAdapterV2(getActivity(), listProductImages, fareList.get(y), searchFlightRequest);
                    list2.setAdapter(flightListAdapter2);


                } else if (y == 2) {
                    if (fareList.get(y).equals("HF")) {
                        list3.addHeaderView(headerInsurance, null, false);
                    } else if (fareList.get(y).equals("PM") || fareList.get(y).equals("PP")) {
                        list3.addHeaderView(headerInsurance2, null, false);
                    }
                    flightListAdapter3 = new FlightListAdapterV2(getActivity(), listProductImages, fareList.get(y), searchFlightRequest);
                    list3.setAdapter(flightListAdapter3);


                } else if (y == 3) {
                    if (fareList.get(y).equals("HF")) {
                        list4.addHeaderView(headerInsurance, null, false);
                    } else if (fareList.get(y).equals("PM") || fareList.get(y).equals("PP")) {
                        list4.addHeaderView(headerInsurance2, null, false);
                    }
                    flightListAdapter4 = new FlightListAdapterV2(getActivity(), listProductImages, fareList.get(y), searchFlightRequest);
                    list4.setAdapter(flightListAdapter4);


                } else if (y == 4) {
                    if (fareList.get(y).equals("HF")) {
                        list5.addHeaderView(headerInsurance, null, false);

                    } else if (fareList.get(y).equals("PM") || fareList.get(y).equals("PP")) {
                        list5.addHeaderView(headerInsurance2, null, false);
                    }
                    flightListAdapter5 = new FlightListAdapterV2(getActivity(), listProductImages, fareList.get(y), searchFlightRequest);
                    list5.setAdapter(flightListAdapter5);


                } else if (y == 5) {
                    if (fareList.get(y).equals("HF")) {
                        list6.addHeaderView(headerInsurance, null, false);

                    } else if (fareList.get(y).equals("PM") || fareList.get(y).equals("PP")) {
                        list6.addHeaderView(headerInsurance2, null, false);
                    }
                    flightListAdapter6 = new FlightListAdapterV2(getActivity(), listProductImages, fareList.get(y), searchFlightRequest);
                    list6.setAdapter(flightListAdapter6);
                }

            }

            // ------- onClick listview ---------//

            list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    AnotherList obj = (AnotherList) list1.getItemAtPosition(position);
                    grabData(obj, position, flightListAdapter1, view);

                }
            });

            list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    AnotherList obj = (AnotherList) list2.getItemAtPosition(position);
                    grabData(obj, position, flightListAdapter2, view);

                }
            });

            list3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    AnotherList obj = (AnotherList) list3.getItemAtPosition(position);
                    grabData(obj, position, flightListAdapter3, view);


                }
            });

            list4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    AnotherList obj = (AnotherList) list4.getItemAtPosition(position);
                    grabData(obj, position, flightListAdapter4, view);

                }
            });

            list5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    AnotherList obj = (AnotherList) list5.getItemAtPosition(position);
                    grabData(obj, position, flightListAdapter5, view);

                }
            });

            list6.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    AnotherList obj = (AnotherList) list6.getItemAtPosition(position);
                    grabData(obj, position, flightListAdapter6, view);

                }
            });

            // ----------------------------------//
            pager.setAdapter(mAdapter);

            //indicator.setViewPager(pager);

            pager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    return false;
                }
            });

            pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                public void onPageScrollStateChanged(int state) {
                }

                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                public void onPageSelected(int position) {
                    fareType = position;
                    tabPosition = position;
                    //recreateAdapter(getActivity(), position, searchFlightRequest);
                }
            });


            //TAB - TYPE

            tabs.setDistributeEvenly(false); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width
            tabs.setCustomTabView(R.layout.type_tab1, 0);

            // Setting Custom Color for the Scroll bar indicator of the Tab View
            tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
                @Override
                public int getIndicatorColor(int position) {
                    return ContextCompat.getColor(getActivity(), R.color.default_theme_colour);
                    //getResources().getColor(R.color.grey);
                }
            });

            // tabs.getTabAt(0).setIcon(ICONS[0]);
            // Setting the ViewPager For the SlidingTabsLayout
            tabs.setViewPager(pager);
            //tabs.setViewPager(null);
        }

    }

    public void grabData(AnotherList obj, int position, FlightListAdapterV2 adapter, View view) {

        String type;
        int customPosition = tabPosition;
        type = fareList.get(tabPosition);

        if (obj.getList().getFare().size() != fareList.size()) {

            RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.eachFlightBlock);
            String currentType = layout.getTag().toString();

            for (int b = 0; b < obj.getList().getFare().size(); b++) {
                if (currentType.equals(obj.getList().getFare().get(b).getType())) {
                    customPosition = b;
                    type = obj.getList().getFare().get(customPosition).getType();
                    break;
                } else {
                    customPosition = tabPosition;
                }
            }

        }

        String journeySellKey = obj.getList().getJourneySellKey();
        String fareKeySell = obj.getList().getFare().get(customPosition).getFareSellKey();

        selectedJourneySellKey = journeySellKey;
        selectedFareKeySell = fareKeySell;

        //Parse to next
        selectedSegmentFare = new SelectedFlightInfo();
        selectedSegmentFare.setSelectedFlightCarrierCode(obj.getList().getSegment().get(0).getCarrierCode() + "" + obj.getList().getSegment().get(0).getFlightNumber());
        selectedSegmentFare.setSelectedFlightDuration(obj.getList().getSegment().get(0).getTravelTime());
        selectedSegmentFare.setSelectedFlightPoint(obj.getList().getFare().get(customPosition).getTotalQuotedPoints());
        selectedSegmentFare.setSelectedDepartureCode(obj.getList().getSegment().get(0).getDepartureStation());
        selectedSegmentFare.setSelectedArrivalCode(obj.getList().getSegment().get(0).getArrivalStation());
        selectedSegmentFare.setSelectedDepartDate(parseStringDate(obj.getList().getSegment().get(0).getSTD()));

        //checkMultiAirport
        if (obj.getList().getSegment().size() > 1) {
            String departCode = obj.getList().getSegment().get(1).getDepartureStation();
            String arrivalCode = obj.getList().getSegment().get(1).getArrivalStation();
            String departTime = parseStringDateToTime(obj.getList().getSegment().get(1).getSTD());
            String arrivalTime = parseStringDateToTime(obj.getList().getSegment().get(1).getSTA());
            selectedSegmentFare.setMultiDepartDate(parseStringDate(obj.getList().getSegment().get(1).getSTD()));

            String merge = departTime + " (" + departCode + ")" + " - " + arrivalTime + " (" + arrivalCode + ")";
            selectedSegmentFare.setMultiDepart(merge);
            selectedSegmentFare.setArrivalMulticode(arrivalCode);
            Log.e("Arrival Code XxX", arrivalCode);

            selectedSegmentFare.setMultiDepartCode(obj.getList().getSegment().get(1).getCarrierCode() + "" + obj.getList().getSegment().get(1).getFlightNumber());
            selectedSegmentFare.setMultiDepartDuration(obj.getList().getSegment().get(1).getTravelTime());
        }

        String departCode = obj.getList().getSegment().get(0).getDepartureStation();
        String arrivalCode = obj.getList().getSegment().get(0).getArrivalStation();
        String departTime = parseStringDateToTime(obj.getList().getSegment().get(0).getSTD());
        String arrivalTime = parseStringDateToTime(obj.getList().getSegment().get(0).getSTA());
        String merge = departTime + " (" + departCode + ")" + " - " + arrivalTime + " (" + arrivalCode + ")";
        selectedSegmentFare.setSelectedFlightInfo(merge);
        //END

        pref.setSelectedDepartFareKey(journeySellKey + "/" + fareKeySell);
        clearAllAdapter();

        if (type.equals("HF") || type.equals("PM") || type.equals("PP")) {
            position = position - 1;
        }
        adapter.onSelected(position, type);

    }

    public void clearAllAdapter() {
        if (flightListAdapter1 != null) {
            flightListAdapter1.clearAllSelected();
        }
        if (flightListAdapter2 != null) {
            flightListAdapter2.clearAllSelected();
        }
        if (flightListAdapter3 != null) {
            flightListAdapter3.clearAllSelected();
        }
        if (flightListAdapter4 != null) {
            flightListAdapter4.clearAllSelected();
        }
        if (flightListAdapter5 != null) {
            flightListAdapter5.clearAllSelected();
        }
        if (flightListAdapter6 != null) {
            flightListAdapter6.clearAllSelected();
        }

    }

    public String parseStringDateToTime(String unparseDate) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat reformat = new SimpleDateFormat("HH:mm");

        try {

            Date date = formatter.parse(unparseDate);
            return reformat.format(date).toString();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
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
            minute = diffMinutes + "M";
        }
        if (diffHours != 0) {
            hour = diffHours + "H";
        }
        return hour + "" + minute;

    }

    public static int getFareType() {
        return fareType;
    }

    public void appendFlightType(final SearchFlightReceive flightReceive) {

        //Services & Fee
        LinearLayout.LayoutParams matchParent = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1f);

        for (int x = 0; x < flightReceive.getJourneyDateMarket().get(0).getJourney().get(0).getFare().size(); x++) {

            LinearLayout flightType = new LinearLayout(getActivity());
            flightType.setWeightSum(1);
            flightType.setLayoutParams(matchParent);
            flightType.setGravity(Gravity.CENTER);

            final String seatType = flightReceive.getJourneyDateMarket().get(0).getJourney().get(0).getFare().get(x).getType();

            TextView txtSeatType = new TextView(getActivity());
            txtSeatType.setTextSize(14);
            txtSeatType.setGravity(Gravity.CENTER_VERTICAL);
            txtSeatType.setPadding(15, 15, 15, 15);

            String fixed = getString(R.string.fixed);
            String regular = getString(R.string.regular);
            String promo = getString(R.string.promo);
            String premium_flex = getString(R.string.premium_flex);
            String premium_flatbed = getString(R.string.premium_flatbed);


            if (seatType.equals("BL")) {
                flightType.setAlpha(0.5f);
                flightType.setBackgroundResource(R.color.standard_seat);
                txtSeatType.setText(fixed);
            } else if (seatType.equals("EC")) {
                flightType.setAlpha(0.5f);
                flightType.setBackgroundResource(R.color.bright_green);
                txtSeatType.setText(regular);
            } else if (seatType.equals("EP")) {
                flightType.setAlpha(0.5f);
                flightType.setBackgroundResource(R.color.preferred_seat);
                txtSeatType.setText(promo);
            } else if (seatType.equals("HF")) {
                flightType.setAlpha(0.5f);
                flightType.setBackgroundResource(R.color.desired_seat);
                txtSeatType.setText(premium_flex);
            } else if (seatType.equals("PM") || seatType.equals("PP")) {
                flightType.setAlpha(0.5f);
                flightType.setBackgroundResource(R.color.calendar_header);
                txtSeatType.setText(premium_flatbed);
            }
            flightType.setTag(seatType);
            if (x == 0) {
                flightType.setAlpha(1f);
                selected = seatType;
            }

            flightType.addView(txtSeatType);
            flightType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resetAdapter(flightReceive, seatType);

                    if (selected.equals("")) {
                        selected = seatType;
                    } else {
                        LinearLayout findTag = (LinearLayout) view.findViewWithTag(selected);
                        findTag.setAlpha(0.5f);
                        selected = seatType;
                    }

                    LinearLayout findTag = (LinearLayout) view.findViewWithTag(seatType);
                    findTag.setAlpha(1f);
                    Log.e("Selected", selected);
                }

            });

            flightEcoType.addView(flightType);

        }
    }

    public void resetAdapter(SearchFlightReceive obj, String type) {
        // flightListPrice.setAdapter(null);
        // flightListAdapter = new FlightListAdapter(getActivity(), obj.getJourneyDateMarket().get(0).getJourney(), type);
        // flightListPrice.setAdapter(flightListAdapter);
    }

    public void convertDate(String date) {

        // String date = "Sat Dec 01 00:00:00 GMT 2012";
        DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
        try {
            Date date2 = format.parse(date);
            Log.e("Date", date2.toString());
        } catch (Exception e) {

        }
    }

    public static void onSelectFlight(Activity thisAct) {

        if (selectedFareKeySell.equals("")) {
            String popup_text = AnalyticsApplication.getContext().getString(R.string.popup_text);
            popupAlertV2(popup_text);
        } else {
            if (oneWay) {

                initiateLoading(act);
                selectFlightRequest = new SelectFlightRequest();
                selectFlightRequest.setAdult(searchFlightRequest.getAdult());
                selectFlightRequest.setChild(searchFlightRequest.getChild());
                selectFlightRequest.setInfant(searchFlightRequest.getInfant());
                selectFlightRequest.setCurrencyCode(searchFlightRequest.getCurrencyCode());
                selectFlightRequest.setJourneySellKey0(selectedJourneySellKey);
                selectFlightRequest.setFareSellKey0(selectedFareKeySell);
                selectFlightRequest.setUsername(username);
                selectFlightRequest.setToken(token);
                //Log.e(FlightPriceFragment.getSelectedJourneySellKey(), FlightPriceFragment.getFareKeySell());

                if (searchFlightRequest.getDepartureStation1() != null) {
                    selectFlightRequest.setJourneySellKey1("x");
                    selectFlightRequest.setFareSellKey1("x");
                }
                selectFlightRequest.setSignature(searchFlightRequest.getSignature());

                presenter2.onSelectFlight(selectFlightRequest);

            } else {


                Intent flightDetail = new Intent(thisAct, FlightListReturnActivity.class);
                flightDetail.putExtra("FLIGHT_LIST", (new Gson()).toJson(flightReceive));
                flightDetail.putExtra("SEARCH_FLIGHT", (new Gson()).toJson(searchFlightRequest));
                flightDetail.putExtra("DEPART_SELLKEY", selectedJourneySellKey);
                flightDetail.putExtra("DEPART_FAREKEY", selectedFareKeySell);
                flightDetail.putExtra("SELECT_SEGMENT", (new Gson()).toJson(selectedSegmentFare));
                thisAct.startActivity(flightDetail);
            }
        }


    }

    public static void popupAlertV2(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(act);
        builder.setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                    }
                });

        // Create the AlertDialog object and return it
        builder.create();
        builder.show();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentContainerId = ((FragmentContainerActivity) getActivity()).getFragmentContainerId();
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
