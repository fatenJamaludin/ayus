package com.app.tbd.ui.Activity.BookingFlight.Add;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.tbd.MainController;
import com.app.tbd.R;
import com.app.tbd.application.MainApplication;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Activity.DragDrop.TouchClass;
import com.app.tbd.ui.Activity.SlidePage.SlidingTabLayout;
import com.app.tbd.ui.Model.JSON.AddOnInfo;
import com.app.tbd.ui.Model.JSON.AddonCached;
import com.app.tbd.ui.Model.JSON.FlightInProgressJSON;
import com.app.tbd.ui.Model.JSON.TravellerAddon;
import com.app.tbd.ui.Model.JSON.TravellerCached;
import com.app.tbd.ui.Model.JSON.TravellerInfo;
import com.app.tbd.ui.Model.JSON.UserInfoJSON;
import com.app.tbd.ui.Model.Receive.AddBaggageReceive;
import com.app.tbd.ui.Model.Receive.AddOnReceiveV2;
import com.app.tbd.ui.Model.Receive.BaggageMealReceive;
import com.app.tbd.ui.Model.Receive.SearchFlightReceive;
import com.app.tbd.ui.Model.Receive.TBD.LoginReceive;
import com.app.tbd.ui.Module.BaggageModule;
import com.app.tbd.ui.Presenter.BookingPresenter;
import com.app.tbd.ui.Realm.RealmObjectController;
import com.google.gson.Gson;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.realm.Realm;
import io.realm.RealmResults;

//import android.support.design.widget.BottomSheetDialogFragment;
//import com.flipboard.bottomsheet.BottomSheetLayout;

public class BaggageFragmentV2 extends BaseFragment implements BookingPresenter.BaggageView {

    @Inject
    BookingPresenter presenter;

    @InjectView(R.id.pager)
    ViewPager pager;

    @InjectView(R.id.tabs)
    SlidingTabLayout tabs;

    BaggageMealReceive baggageMealReceive;
    SearchFlightReceive searchFlightReceive;

    Dialog dialog;
    ArrayList<ArrayList<TravellerAddon>> travellerAddonSegment = new ArrayList<ArrayList<TravellerAddon>>();

    static BookingPresenter staticPresenter;
    static ArrayList<ArrayList<TravellerAddon>> travellerAddonDone;
    static TravellerListHorizontal adapter;

    private static Boolean multiple;

    int tab = 0;
    /*String point = getString(R.string.addons_points);*/

    public static BaggageFragmentV2 newInstance(Bundle bundle) {

        BaggageFragmentV2 fragment = new BaggageFragmentV2();
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RealmObjectController.clearCachedResult(getActivity());
        MainApplication.get(getActivity()).createScopedGraph(new BaggageModule(this)).inject(this);
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
        String obj = bundle.getString("BAGGAGE_INFO");
        String added_info = bundle.getString("ADDED_INFO");

        staticPresenter = presenter;
        dialog = new Dialog(getActivity(), R.style.MaterialDialogSheet);

        Realm realm = RealmObjectController.getRealmInstance(getActivity());

        /*final RealmResults<SeatCached> result3 = realm.where(SeatCached.class).findAll();*/

        baggageMealReceive = (new Gson()).fromJson(obj, BaggageMealReceive.class);
        AddOnReceiveV2 addOnReceive = (new Gson()).fromJson(added_info, AddOnReceiveV2.class);

        //baggageMealReceive = (new Gson()).fromJson(result3.get(0).getSeatCached(), BaggageMealReceive.class);

        final RealmResults<TravellerCached> result4 = realm.where(TravellerCached.class).findAll();
        TravellerInfo travellerInfo = (new Gson()).fromJson(result4.get(0).getTraveller(), TravellerInfo.class);

        startPagination(baggageMealReceive, travellerInfo, addOnReceive);
        //startPagination();
        //set passenger obj

    }

    public void startPagination(BaggageMealReceive obj, TravellerInfo travellerInfo, AddOnReceiveV2 addOnReceive) {

        //check highest y value
        //check for the highest faretype

        //get flight info
        ArrayList<String> seatTabList = new ArrayList<String>();

        Realm realm = RealmObjectController.getRealmInstance(getActivity());
        final RealmResults<FlightInProgressJSON> result3 = realm.where(FlightInProgressJSON.class).findAll();
        searchFlightReceive = (new Gson()).fromJson(result3.get(0).getSearchFlightReceive(), SearchFlightReceive.class);
        for (int v = 0; v < searchFlightReceive.getJourneyDateMarket().size(); v++) {
            //for (int h = 0; h < searchFlightReceive.getJourneyDateMarket().get(v).getJourney().size() ; h++) {

            String dash = " - ";
            String append = "";
            if (searchFlightReceive.getJourneyDateMarket().get(v).getJourney().get(0).getSegment().size() > 1) {

                String departStation = searchFlightReceive.getJourneyDateMarket().get(v).getJourney().get(0).getSegment().get(0).getDepartureStation();
                String stopStation = searchFlightReceive.getJourneyDateMarket().get(v).getJourney().get(0).getSegment().get(1).getDepartureStation();
                String arrivalStation = searchFlightReceive.getJourneyDateMarket().get(v).getJourney().get(0).getSegment().get(1).getArrivalStation();

                append = departStation + dash + stopStation + dash + arrivalStation;
                multiple = true;
            } else {
                String departStation = searchFlightReceive.getJourneyDateMarket().get(v).getJourney().get(0).getSegment().get(0).getDepartureStation();
                String arrivalStation = searchFlightReceive.getJourneyDateMarket().get(v).getJourney().get(0).getSegment().get(0).getArrivalStation();

                append = departStation + dash + arrivalStation;
                multiple = false;
            }

            seatTabList.add(append);
            tab++;
        }

        ArrayList<ArrayList<String>> addonLoopV2 = new ArrayList<ArrayList<String>>();
        for (int journey = 0; journey < addOnReceive.getJourney().size(); journey++) {
            for (int segment = 0; segment < addOnReceive.getJourney().get(journey).getSegment().size(); segment++) {
                ArrayList<String> addonLoop = new ArrayList<String>();
                for (int passenger = 0; passenger < addOnReceive.getJourney().get(journey).getSegment().get(segment).getPassenger().size(); passenger++) {
                    addonLoop.add(addOnReceive.getJourney().get(journey).getSegment().get(segment).getPassenger().get(passenger).getBaggage());
                }
                addonLoopV2.add(addonLoop);
            }
        }


        for (int f = 0; f < tab; f++) {

            ArrayList<TravellerAddon> travellerSeatsArray = new ArrayList<TravellerAddon>();

            for (int g = 0; g < travellerInfo.getList().size(); g++) {

                ArrayList<String> dummy = new ArrayList<String>();

                TravellerAddon travellerAddon = new TravellerAddon();
                travellerAddon.setTravellerName(travellerInfo.getList().get(g).getGiven_name() + " " + travellerInfo.getList().get(g).getFamily_name());
                travellerAddon.setSsrListPerPassenger(dummy);

                if (multiple && f == 0) {
                    Log.e("Multiple", "Tab 0");
                    if (!addonLoopV2.get(0).get(g).equals("")) {
                        travellerAddon.getSsrListPerPassenger().add(addonLoopV2.get(0).get(g));
                    }
                } else if (multiple && f == 1) {
                    Log.e("Multiple", "Tab 1");
                    if (!addonLoopV2.get(2).get(g).equals("")) {
                        travellerAddon.getSsrListPerPassenger().add(addonLoopV2.get(2).get(g));
                    }
                } else if (!multiple && f == 0) {
                    if (!addonLoopV2.get(0).get(g).equals("")) {
                        travellerAddon.getSsrListPerPassenger().add(addonLoopV2.get(0).get(g));
                    }
                    Log.e("!Multiple", "Tab 0");
                    Log.e("addonLoot(0).get(g)", addonLoopV2.get(0).get(g));
                } else if (!multiple && f == 1) {
                    Log.e("!Multiple", "Tab 1");
                    Log.e("addonLoot(0).get(g)", addonLoopV2.get(1).get(g));
                    if (!addonLoopV2.get(1).get(g).equals("")) {
                        travellerAddon.getSsrListPerPassenger().add(addonLoopV2.get(1).get(g));
                    }
                }

                travellerSeatsArray.add(travellerAddon);

            }

            travellerAddonSegment.add(travellerSeatsArray);
        }

        Log.e("travellerAddonSegment", Integer.toString(travellerAddonSegment.size()));
        //travellerAddonSegment.get(segment).get(Integer.parseInt(onAddAddon.getTag().toString())).getSsrListPerPassenger().add(clipData);

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        ArrayList<Integer> pagerPosition = new ArrayList<>();

        View view1 = layoutInflater.inflate(R.layout.baggage_meal_list, null);
        pagerPosition.add(0);
        setPager(view1, obj, 0);
        setHorizontalUser(view1, travellerInfo, 0);

        View view2 = layoutInflater.inflate(R.layout.baggage_meal_list, null);
        if (tab > 1) {
            if (multiple) {
                setPager(view2, obj, 2);
                setHorizontalUser(view2, travellerInfo, 1);
            } else {
                setPager(view2, obj, 1);
                setHorizontalUser(view2, travellerInfo, 1);
            }

        }

        /*View view3 = layoutInflater.inflate(R.layout.baggage_meal_list, null);
        if (tab > 2) {
            //setPager(view3, obj, 2);
            //setHorizontalUser(view3, travellerInfo, 2);
        }

        /*View view4 = layoutInflater.inflate(R.layout.baggage_meal_list, null);
        pagerPosition.add(0);
        if (tab > 3) {
            //setPager(view4, obj, 3);
            //setHorizontalUser(view4, travellerInfo, 3);
        }*/

        // View view5 = layoutInflater.inflate(R.layout.seat_list, null);
        // View view6 = layoutInflater.inflate(R.layout.seat_list, null);

        Vector<View> pages = new Vector<View>();
        pages.add(view1);
        pages.add(view2);
        //pages.add(view3);
        //  pages.add(view5);
        //  pages.add(view6);

        BaggageListAdapter sAdapter = new BaggageListAdapter(getContext(), pages);
        sAdapter.addAll(seatTabList);

        pager.setAdapter(sAdapter);
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

    }

    public void setHorizontalUser(View view, TravellerInfo travellerInfo, int segment) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        RecyclerView myList = (RecyclerView) view.findViewById(R.id.horizontalList);
        myList.setLayoutManager(layoutManager);
        myList.setHasFixedSize(false);

        //change later
        int max = 1;
        adapter = new TravellerListHorizontal(BaggageFragmentV2.this, view, getActivity(), travellerInfo, segment, travellerAddonSegment, max/*, baggageMealReceive*/);
        myList.setAdapter(adapter);

    }

    public void showBottomFlipper(Activity act, View mainView, TravellerInfo travellerInfo, ArrayList<ArrayList<TravellerAddon>> travellerAddonSegment, int segment, int travellerPosition) {
        //R.style.MaterialDialogSheet

        LayoutInflater li = LayoutInflater.from(act);
        final View myView = li.inflate(R.layout.addon_added_list, null);

        //append view for added baggage ssr
        appendBottomView(myView, mainView, travellerInfo, travellerAddonSegment, segment, travellerPosition);

        dialog.setContentView(myView);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4DFFFFFF")));
        dialog.setCancelable(false);
        dialog.getWindow().setGravity(Gravity.BOTTOM);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.BOTTOM;
        dialog.getWindow().setAttributes(lp);
        dialog.show();

    }

    public void appendBottomView(View view, final View mainView, final TravellerInfo info, final ArrayList<ArrayList<TravellerAddon>> travellerAddonSegment, final int segment, final int travellerPosition) {

        LinearLayout.LayoutParams half06 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 0.5f);
        LinearLayout.LayoutParams half04 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 0.5f);
        LinearLayout.LayoutParams matchParent2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1f);

        TextView txtAddonName = (TextView) view.findViewById(R.id.txtAddonName);
        TextView txtClose = (TextView) view.findViewById(R.id.txtClose);
        LinearLayout added_addon = (LinearLayout) view.findViewById(R.id.added_addon);

        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                setHorizontalUser(mainView, info, segment);
            }
        });

        txtAddonName.setText(travellerAddonSegment.get(segment).get(travellerPosition).getTravellerName());

        if (multiple) {
            Log.e("Multiple", "Y");
        } else {

            for (int f = 0; f < baggageMealReceive.getSegment().size(); f++) {

                if (travellerAddonSegment.get(f).get(travellerPosition).getSsrListPerPassenger().size() > 0) {

                    LinearLayout servicesRow = new LinearLayout(getActivity());
                    servicesRow.setOrientation(LinearLayout.HORIZONTAL);
                    servicesRow.setPadding(2, 20, 2, 2);
                    servicesRow.setWeightSum(1);
                    servicesRow.setLayoutParams(matchParent2);

                    TextView txtServicesName = new TextView(getActivity());
                    txtServicesName.setText(baggageMealReceive.getSegment().get(f).getDepartureStation() + " - " + baggageMealReceive.getSegment().get(f).getArrivalStation());
                    txtServicesName.setLayoutParams(matchParent2);
                    txtServicesName.setGravity(Gravity.LEFT);
                    txtServicesName.setTextSize(18);
                    txtServicesName.setTypeface(null, Typeface.BOLD);
                    txtServicesName.setTextColor(ContextCompat.getColor(getActivity(), R.color.default_theme_colour));
                    servicesRow.addView(txtServicesName);

                    added_addon.addView(servicesRow);

                    for (int xx = 0; xx < travellerAddonSegment.get(f).get(travellerPosition).getSsrListPerPassenger().size(); xx++) {

                        LinearLayout.LayoutParams main = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
                        main.setMargins(0, 10, 0, 10);

                        final LinearLayout ssrRowMain = new LinearLayout(getActivity());
                        ssrRowMain.setOrientation(LinearLayout.HORIZONTAL);
                        ssrRowMain.setPadding(2, 2, 2, 2);
                        ssrRowMain.setWeightSum(1);
                        ssrRowMain.setLayoutParams(main);
                        ssrRowMain.setLayoutParams(matchParent2);

                        //no time to change .. just check back with the list
                        LinearLayout ssrRow = new LinearLayout(getActivity());
                        ssrRow.setOrientation(LinearLayout.HORIZONTAL);
                        ssrRow.setPadding(2, 10, 2, 10);
                        ssrRow.setWeightSum(1);
                        ssrRow.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bottom_line_border));
                        ssrRow.setLayoutParams(matchParent2);

                        String servicesName = "";
                        String servicePrice = "";

                        for (int c = 0; c < baggageMealReceive.getSegment().get(segment).getBaggage().size(); c++) {
                            if (travellerAddonSegment.get(f).get(travellerPosition).getSsrListPerPassenger().size() > 0) {
                                if (travellerAddonSegment.get(f).get(travellerPosition).getSsrListPerPassenger().get(xx).equals(baggageMealReceive.getSegment().get(segment).getBaggage().get(c).getSSRCode())) {
                                    servicesName = baggageMealReceive.getSegment().get(f).getBaggage().get(c).getDescription();
                                    servicePrice = baggageMealReceive.getSegment().get(f).getBaggage().get(c).getPoints();

                                    Log.e("name", servicesName);
                                    Log.e("point", servicePrice);
                                }
                            }
                        }

                        final ImageView removeSSR = new ImageView(getActivity());
                        removeSSR.setTag(Integer.toString(f) + "/-" + Integer.toString(travellerPosition)+"/-"+Integer.toString(xx));
                        //removeSSR.setLayoutParams(half04);
                        removeSSR.setPadding(0, 0, 10, 0);
                        removeSSR.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.mipmap.addon_remove));

                        ssrRowMain.addView(removeSSR);

                        removeSSR.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                String string = removeSSR.getTag().toString();
                                String[] parts = string.split("/-");

                                Log.e("Segment" + parts[0], "Passenger" + parts[1]);
                                travellerAddonSegment.get(Integer.parseInt(parts[0])).get(Integer.parseInt(parts[1])).getSsrListPerPassenger().remove(Integer.parseInt(parts[2]));

                                dialog.dismiss();
                                setHorizontalUser(mainView, info, segment);
                                adapter.customNotify(travellerAddonSegment);
                            }
                        });

                        TextView ssrName = new TextView(getActivity());
                        ssrName.setText(servicesName);
                        ssrName.setTextSize(16);
                        ssrName.setLayoutParams(half04);
                        ssrName.setGravity(Gravity.START);

                        String points = getString(R.string.addons_points);

                        TextView txtServicePrice = new TextView(getActivity());
                        String newPrice = changeThousand(servicePrice);
                        txtServicePrice.setText("+" + newPrice + " " + points);
                        txtServicePrice.setLayoutParams(half06);
                        txtServicePrice.setTextSize(16);
                        txtServicePrice.setTypeface(null, Typeface.BOLD);
                        txtServicePrice.setGravity(Gravity.END);

                        ssrRow.addView(ssrName);
                        ssrRow.addView(txtServicePrice);
                        ssrRowMain.addView(ssrRow);

                        added_addon.addView(ssrRowMain);

                    }

                }
            }
        }
    }

    public void setPager(final View view, BaggageMealReceive obj, int segment) {

        LayoutInflater innerLayout = LayoutInflater.from(getActivity());

        final Vector<View> innerPages = new Vector<View>();
        for (int c = 0; c < obj.getSegment().get(segment).getBaggage().size(); c++) {
            View insideBaggage = innerLayout.inflate(R.layout.inside_baggage_addon, null);

            final ImageView baggage = (ImageView) insideBaggage.findViewById(R.id.baggage);
            final CircularImageView baggageIcon = (CircularImageView) insideBaggage.findViewById(R.id.baggageIcon);
            TextView txtDesription = (TextView) insideBaggage.findViewById(R.id.txtDesription);
            TextView txtBaggagePts = (TextView) insideBaggage.findViewById(R.id.txtBaggagePts);

            txtDesription.setText(obj.getSegment().get(segment).getBaggage().get(c).getDescription());
            String newPts = changeThousand(obj.getSegment().get(segment).getBaggage().get(c).getPoints());
            String points = getString(R.string.addons_points);

            txtBaggagePts.setText("+" + newPts + " " + points);
            baggageIcon.setTag(obj.getSegment().get(segment).getBaggage().get(c).getSSRCode());

            innerPages.add(insideBaggage);


            baggage.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    Log.e("Drag", "Y");
                    baggageIcon.performLongClick();
                    return false;
                }
            });
            baggageIcon.setOnLongClickListener(new TouchClass());
        }

        BaggageInnerListAdapter innerAdapter = new BaggageInnerListAdapter(getContext(), innerPages);

        final ViewPager innerPager = (ViewPager) view.findViewById(R.id.innerPager);
        innerPager.setAdapter(innerAdapter);
        innerPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return true;
            }
        });

        final ImageView btnNext = (ImageView) view.findViewById(R.id.btnNext);
        final ImageView btnPrevious = (ImageView) view.findViewById(R.id.btnPrevious);
        btnNext.setTag(Integer.toString(0));
        btnPrevious.setVisibility(View.INVISIBLE);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    btnNext.setVisibility(View.VISIBLE);
                    int nextTag = Integer.parseInt(btnNext.getTag().toString());
                    if (nextTag < innerPages.size() - 1) {

                        btnPrevious.setVisibility(View.VISIBLE);
                        innerPager.setCurrentItem(nextTag + 1, true);
                        if (nextTag == innerPages.size() - 2) {
                            btnNext.setVisibility(View.INVISIBLE);
                        } else {
                            btnNext.setTag(Integer.toString(nextTag + 1));
                        }
                    }
                } catch (Exception e) {
                    Log.e("Limit", "True");
                    btnNext.setVisibility(View.GONE);
                }
            }
        });


        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int nextTag = Integer.parseInt(btnNext.getTag().toString());
                    if (nextTag >= 0) {
                        btnPrevious.setVisibility(View.VISIBLE);
                        innerPager.setCurrentItem(nextTag - 1, true);
                        btnNext.setTag(Integer.toString(nextTag - 1));
                        btnPrevious.setVisibility(View.VISIBLE);
                        btnNext.setVisibility(View.VISIBLE);
                        if (nextTag == 1) {
                            btnPrevious.setVisibility(View.INVISIBLE);
                        }
                    }

                } catch (Exception e) {
                    Log.e("Limit", "True");
                    btnPrevious.setVisibility(View.GONE);
                }
            }
        });

        innerPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(final int position) {
            }
        });

    }

    public static void onRequestBaggage(Activity act) {

        initiateLoading(act);

        Realm realm = RealmObjectController.getRealmInstance(act);
        final RealmResults<UserInfoJSON> result2 = realm.where(UserInfoJSON.class).findAll();
        LoginReceive loginReceive = (new Gson()).fromJson(result2.get(0).getUserInfo(), LoginReceive.class);

        travellerAddonDone = adapter.getAdapterObj();

        HashMap<String, String> dicMap = new HashMap<String, String>();


        for (int x = 0; x < travellerAddonDone.size(); x++) {

            for (int v = 0; v < travellerAddonDone.get(x).size(); v++) {
                String appendSSRCode = "";

                for (int y = 0; y < travellerAddonDone.get(x).get(v).getSsrListPerPassenger().size(); y++) {

                    String ssrCode = travellerAddonDone.get(x).get(v).getSsrListPerPassenger().get(y);
                    Log.e("Y", ssrCode);

                    String quote;
                    if (y == 0) {
                        quote = "";
                    } else {
                        quote = ",";
                    }
                    appendSSRCode = appendSSRCode + quote + ssrCode;
                    Log.e("appendSSRCode", appendSSRCode);

                }
                if (!appendSSRCode.equals("")) {
                    if (multiple && x == 0) {
                        dicMap.put("Segment" + x + "Passenger" + v, appendSSRCode.trim());
                        dicMap.put("Segment" + (x + 1) + "Passenger" + v, appendSSRCode.trim());
                    } else if (multiple && x == 1) {
                        dicMap.put("Segment" + (x + 1) + "Passenger" + v, appendSSRCode.trim());
                        dicMap.put("Segment" + (x + 2) + "Passenger" + v, appendSSRCode.trim());
                    } else {
                        dicMap.put("Segment" + (x) + "Passenger" + v, appendSSRCode.trim());
                    }
                }


            }
        }

        dicMap.put("UserName", loginReceive.getUserName());
        dicMap.put("Token", loginReceive.getToken());
        dicMap.put("Signature", loginReceive.getSignature());
        dicMap.put("FROM_WHICH", "BAGGAGE_ASSIGN");

        staticPresenter.onAddMealRequest(dicMap);
    }

    @Override
    public void onBaggageAddReceive(AddBaggageReceive obj) {

        dismissLoading();
        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {

            Gson gsonUserInfo = new Gson();
            Realm realm = RealmObjectController.getRealmInstance(getActivity());
            final RealmResults<AddonCached> result2 = realm.where(AddonCached.class).findAll();

            AddOnInfo addOnInfo = (new Gson()).fromJson(result2.get(0).getAddonInfo(), AddOnInfo.class);
            addOnInfo.setSetBaggageSelected("Y");

            /*if (baggagePoint!="" && baggageWeight!=""){

                addOnInfo.setBaggagePoint(baggagePoint);
                addOnInfo.setBaggageWeight(baggageWeight);

                Log.e("Point Luggage", baggagePoint);
                Log.e("Weight Luggage", baggageWeight);
            }*/

            /*List<AddOnInfo.MealList> all = new ArrayList<AddOnInfo.MealList>();
            AddOnInfo.MealList mItem = new AddOnInfo().new MealList();

            mItem.setMealName("");
            mItem.setMealPassengerName("");
            mItem.setMealPoint("");
            all.add(mItem);*/

            String addOnInfoString = gsonUserInfo.toJson(addOnInfo);

            //save in Realm
            RealmObjectController.saveAddOnInfo(getActivity(), addOnInfoString);
            getActivity().finish();
            //getActivity().moveTaskToBack(true);

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
        Log.e("Resume", "Page");

    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();

    }

}
