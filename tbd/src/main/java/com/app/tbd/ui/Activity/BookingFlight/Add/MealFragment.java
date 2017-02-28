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
import android.widget.Space;
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
import com.app.tbd.ui.Model.JSON.SeatCached;
import com.app.tbd.ui.Model.JSON.TravellerAddon;
import com.app.tbd.ui.Model.JSON.TravellerCached;
import com.app.tbd.ui.Model.JSON.TravellerInfo;
import com.app.tbd.ui.Model.JSON.UserInfoJSON;
import com.app.tbd.ui.Model.Receive.AddMealReceive;
import com.app.tbd.ui.Model.Receive.AddOnReceiveV2;
import com.app.tbd.ui.Model.Receive.BaggageMealReceive;
import com.app.tbd.ui.Model.Receive.SearchFlightReceive;
import com.app.tbd.ui.Model.Receive.TBD.LoginReceive;
import com.app.tbd.ui.Module.MealModule;
import com.app.tbd.ui.Presenter.BookingPresenter;
import com.app.tbd.ui.Realm.RealmObjectController;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.realm.Realm;
import io.realm.RealmResults;

public class MealFragment extends BaseFragment implements BookingPresenter.MealView {

    @Inject
    BookingPresenter presenter;

    @InjectView(R.id.pager)
    ViewPager pager;

    @InjectView(R.id.tabs)
    SlidingTabLayout tabs;

    ArrayList<ArrayList<TravellerAddon>> travellerAddonSegment = new ArrayList<ArrayList<TravellerAddon>>();
    static ArrayList<ArrayList<TravellerAddon>> travellerAddonDone;
    static BookingPresenter staticPresenter;
    static TravellerListHorizontalMeal adapter;
    static Boolean noMeal = true;

    String s1 = "null", s2 = "null";
    int tab = 0;

    Dialog dialog;
    BaggageMealReceive baggageMealReceive;

    public static MealFragment newInstance(Bundle bundle) {

        MealFragment fragment = new MealFragment();
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RealmObjectController.clearCachedResult(getActivity());
        MainApplication.get(getActivity()).createScopedGraph(new MealModule(this)).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.addon_meal_baggage, container, false);
        ButterKnife.inject(this, view);

        dataSetup();

        return view;
    }

    public void dataSetup() {

        Bundle bundle = getArguments();
        String obj = bundle.getString("MEAL_INFO");
        String added_info = bundle.getString("ADDED_INFO");

        staticPresenter = presenter;
        dialog = new Dialog(getActivity(), R.style.MaterialDialogSheet);

        Realm realm = RealmObjectController.getRealmInstance(getActivity());

        final RealmResults<SeatCached> result3 = realm.where(SeatCached.class).findAll();
        //baggageMealReceive = (new Gson()).fromJson(result3.get(0).getSeatCached(), BaggageMealReceive.class);
        baggageMealReceive = (new Gson()).fromJson(obj, BaggageMealReceive.class);
        AddOnReceiveV2 addOnReceive = (new Gson()).fromJson(added_info, AddOnReceiveV2.class);

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
        SearchFlightReceive searchFlightReceive = (new Gson()).fromJson(result3.get(0).getSearchFlightReceive(), SearchFlightReceive.class);

        for (int v = 0; v < searchFlightReceive.getJourneyDateMarket().size(); v++) {

            for (int h = 0; h < searchFlightReceive.getJourneyDateMarket().get(v).getJourney().get(0).getSegment().size(); h++) {
                /* if(searchFlightReceive.getJourneyDateMarket().get(v).getJourney().size() > 2){*/
                String departCode = searchFlightReceive.getJourneyDateMarket().get(v).getJourney().get(0).getSegment().get(h).getDepartureStation();
                String arrivalCode = searchFlightReceive.getJourneyDateMarket().get(v).getJourney().get(0).getSegment().get(h).getArrivalStation();
                seatTabList.add(departCode + " - " + arrivalCode);

                tab++;
            }
        }

        ArrayList<ArrayList<ArrayList<String>>> addonLoopV2 = new ArrayList<ArrayList<ArrayList<String>>>();
        for (int journey = 0; journey < addOnReceive.getJourney().size(); journey++) {
            for (int segment = 0; segment < addOnReceive.getJourney().get(journey).getSegment().size(); segment++) {
                ArrayList<ArrayList<String>> addonLoop = new ArrayList<ArrayList<String>>();
                for (int passenger = 0; passenger < addOnReceive.getJourney().get(journey).getSegment().get(segment).getPassenger().size(); passenger++) {
                    addonLoop.add(addOnReceive.getJourney().get(journey).getSegment().get(segment).getPassenger().get(passenger).getMeal());

                }
                addonLoopV2.add(addonLoop);
            }
        }

        Log.e("addonLoopV2", Integer.toString(addonLoopV2.size()));


        //addOnReceive

        // int countSegment = addOnReceive.getJourney().get(0).getPassenger().get(0).getSegment().size();
        // int returnCountSegment = addOnReceive.getJourney().get(1).getPassenger().get(0).getSegment().size();

        // int countJourney = addOnReceive.getJourney().size();
        //if (countJourney > 1) {
        //    countSegment = countSegment + addOnReceive.getJourney().get(1).getPassenger().get(0).getSegment().size();
        //}
        // int countPassenger = addOnReceive.getJourney().get(0).getPassenger().size();

      /*  ArrayList<ArrayList<ArrayList<String>>> x3 = new ArrayList<ArrayList<ArrayList<String>>>();
        ArrayList<ArrayList<ArrayList<String>>> x4Final = new ArrayList<ArrayList<ArrayList<String>>>();

        for (int passenger = 0; passenger < countPassenger; passenger++) {
            ArrayList<ArrayList<String>> x1 = new ArrayList<ArrayList<String>>();
            for (int segment = 0; segment < countSegment; segment++) {
                ArrayList<String> dummy = new ArrayList<String>();
                if (addOnReceive.getJourney().get(0).getPassenger().get(passenger).getSegment().get(segment).getMeal().size() > 0) {
                    dummy = addOnReceive.getJourney().get(0).getPassenger().get(passenger).getSegment().get(segment).getMeal();
                    x1.add(dummy);
                    //}

                }
            }
            if (countJourney > 1) {
                for (int segment2 = 0; segment2 < returnCountSegment; segment2++) {
                    ArrayList<String> dummy2 = new ArrayList<String>();
                    //first trip (segment 0 & 1)
                    if (addOnReceive.getJourney().get(1).getPassenger().get(passenger).getSegment().get(segment2).getMeal().size() > 0) {
                        dummy2 = addOnReceive.getJourney().get(1).getPassenger().get(passenger).getSegment().get(segment2).getMeal();
                        x1.add(dummy2);
                    }
                }
            }

            //need to revert to s1 - p1,p2,p3
            x3.add(x1);

        }*/

        //get passenger
       /* int passengerV2 = x3.size();
        Log.e("SegmentV2", Integer.toString(passengerV2));

        //get segment
        int SegmentV2 = 0;
        for (int b = 0; b < x3.size(); b++) {
            SegmentV2 = SegmentV2 + x3.get(b).size();
        }
*/
        //recompile
        /*for(int segmentXX = 0 ; segmentXX < SegmentV2 ; segmentXX++){
            for(int passengerXX = 0 ; passengerXX < passengerV2 ; passengerV2++){

                ArrayList<String> passenger1 = x3.get(passengerXX).get(0);
                x4Final.add(x3.get(segmentXX));
            }
        }*/


        /*     if (countJourney > 1) {
                    dummy = addOnReceive.getJourney().get(1).getPassenger().get(passenger).getSegment().get(segment).getMeal();
                    x2.add(dummy);
                }*/

       /* for (int segment = 0; segment < countSegment; segment++) {
            ArrayList<ArrayList<String>> x2 = new ArrayList<ArrayList<String>>();

            for (int passenger = 0; passenger < countPassenger; passenger++) {
                ArrayList<String> dummy = new ArrayList<String>();
                ArrayList<String> dummy2 = new ArrayList<String>();

            }
            x3.add(x2);
        }*/

        for (int f = 0; f < tab; f++) {

            ArrayList<TravellerAddon> travellerSeatsArray = new ArrayList<TravellerAddon>();

            for (int g = 0; g < travellerInfo.getList().size(); g++) {

                ArrayList<String> dummy = new ArrayList<String>();
                TravellerAddon travellerAddon = new TravellerAddon();
                travellerAddon.setTravellerName(travellerInfo.getList().get(g).getGiven_name() + " " + travellerInfo.getList().get(g).getFamily_name());
                travellerAddon.setSsrListPerPassenger(addonLoopV2.get(f).get(g));
                travellerSeatsArray.add(travellerAddon);
            }

            travellerAddonSegment.add(travellerSeatsArray);
        }

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        ArrayList<Integer> pagerPosition = new ArrayList<>();

        View view1 = layoutInflater.inflate(R.layout.baggage_meal_list, null);
        LinearLayout noMealInfo = (LinearLayout) view1.findViewById(R.id.noMealInfo);
        LinearLayout mealLayout = (LinearLayout) view1.findViewById(R.id.mealLayout);

        pagerPosition.add(0);
        if (obj.getSegment().get(0).getMeal().size() > 0) {
            setPager(view1, obj, 0);
            setHorizontalUser(view1, travellerInfo, 0);
            noMealInfo.setVisibility(View.GONE);
            mealLayout.setVisibility(View.VISIBLE);
            noMeal = false;
        } else {
            noMealInfo.setVisibility(View.VISIBLE);
            mealLayout.setVisibility(View.GONE);
        }


        View view2 = layoutInflater.inflate(R.layout.baggage_meal_list, null);
        LinearLayout noMealInfo2 = (LinearLayout) view2.findViewById(R.id.noMealInfo);
        LinearLayout mealLayout2 = (LinearLayout) view2.findViewById(R.id.mealLayout);

        pagerPosition.add(0);

        if (tab > 1) {
            if (obj.getSegment().get(1).getMeal().size() > 0) {
                setPager(view2, obj, 1);
                setHorizontalUser(view2, travellerInfo, 1);
                noMealInfo2.setVisibility(View.GONE);
                mealLayout2.setVisibility(View.VISIBLE);
                noMeal = false;
            } else {
                noMealInfo2.setVisibility(View.VISIBLE);
                mealLayout2.setVisibility(View.GONE);
            }
        }


        View view3 = layoutInflater.inflate(R.layout.baggage_meal_list, null);
        LinearLayout noMealInfo3 = (LinearLayout) view3.findViewById(R.id.noMealInfo);
        LinearLayout mealLayout3 = (LinearLayout) view3.findViewById(R.id.mealLayout);

        pagerPosition.add(0);
        if (tab > 2) {
            if (obj.getSegment().get(2).getMeal().size() > 0) {
                setPager(view3, obj, 2);
                setHorizontalUser(view3, travellerInfo, 2);
                noMealInfo3.setVisibility(View.GONE);
                mealLayout3.setVisibility(View.VISIBLE);
                noMeal = false;

            } else {
                noMealInfo3.setVisibility(View.VISIBLE);
                mealLayout3.setVisibility(View.GONE);
            }
        }

        View view4 = layoutInflater.inflate(R.layout.baggage_meal_list, null);
        LinearLayout noMealInfo4 = (LinearLayout) view4.findViewById(R.id.noMealInfo);
        LinearLayout mealLayout4 = (LinearLayout) view4.findViewById(R.id.mealLayout);

        pagerPosition.add(0);
        if (tab > 3) {
            if (obj.getSegment().get(3).getMeal().size() > 0) {
                setPager(view4, obj, 3);
                setHorizontalUser(view4, travellerInfo, 3);
                noMealInfo4.setVisibility(View.GONE);
                mealLayout4.setVisibility(View.VISIBLE);
                noMeal = false;
            } else {
                noMealInfo4.setVisibility(View.VISIBLE);
                mealLayout4.setVisibility(View.GONE);
            }
        }

        // View view5 = layoutInflater.inflate(R.layout.seat_list, null);
        // View view6 = layoutInflater.inflate(R.layout.seat_list, null);

        Vector<View> pages = new Vector<View>();
        pages.add(view1);
        pages.add(view2);
        pages.add(view3);
        pages.add(view4);
        // pages.add(view5);
        // pages.add(view6);

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

                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
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

        int max = 2;
        adapter = new TravellerListHorizontalMeal(MealFragment.this, view, getActivity(), travellerInfo, segment, travellerAddonSegment, max);
        myList.setAdapter(adapter);

    }

    public void setPager(final View view, BaggageMealReceive obj, int segment) {

        LayoutInflater innerLayout = LayoutInflater.from(getActivity());

        final Vector<View> innerPages = new Vector<View>();
        for (int c = 0; c < obj.getSegment().get(segment).getMeal().size(); c++) {
            View insideMeal = innerLayout.inflate(R.layout.inside_meal_selection, null);
            CircularImageView mealImage = (CircularImageView) insideMeal.findViewById(R.id.mealImage);
            final CircularImageView mealIcon = (CircularImageView) insideMeal.findViewById(R.id.mealIcon);

            TextView txtMealName = (TextView) insideMeal.findViewById(R.id.txtMealName);
            TextView txtMealPts = (TextView) insideMeal.findViewById(R.id.txtMealPts);

            Glide.with(this)
                    .load(obj.getSegment().get(segment).getMeal().get(c).getImage())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .thumbnail(1)
                    .into(mealImage);

            Log.e("MealCode:" + segment, obj.getSegment().get(segment).getMeal().get(c).getSSRCode());

            String pts = getString(R.string.pts);

            txtMealName.setText(obj.getSegment().get(segment).getMeal().get(c).getDescription());
            String point = changeThousand(obj.getSegment().get(segment).getMeal().get(c).getPoints());
            txtMealPts.setText("+" + point + " " + pts);
            mealIcon.setTag(obj.getSegment().get(segment).getMeal().get(c).getSSRCode());

            innerPages.add(insideMeal);

            mealImage.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    Log.e("Drag", "Y");
                    mealIcon.performLongClick();
                    return false;
                }
            });
            mealIcon.setOnLongClickListener(new TouchClass());
        }

        /*ImageView img1 = new ImageView(getActivity());
        img1.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.arrow_in_icon));
        ImageView img2 = new ImageView(getActivity());
        img2.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.arrow_out_icon));
        ImageView img3 = new ImageView(getActivity());
        img3.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.arrow_in_icon));*/

        //pass object here

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

        final int totalMeal = obj.getSegment().get(segment).getMeal().size();

        final ImageView btnNext = (ImageView) view.findViewById(R.id.btnNext);
        final ImageView btnPrevious = (ImageView) view.findViewById(R.id.btnPrevious);
        btnNext.setTag(Integer.toString(0));
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

                   /* } else if (nextTag < 0) {
                        btnNext.setVisibility(View.INVISIBLE);
                    }*/
                    } else if (nextTag == innerPages.size() - 1) {
                        btnNext.setVisibility(View.INVISIBLE);
                    }


                } catch (Exception e) {
                    Log.e("Limit", "True");
                    btnNext.setVisibility(View.GONE);
                }
            }
        });


//<<<<<<< HEAD
        //final ImageView btnPrevious = (ImageView) view.findViewById(R.id.btnPrevious);
//=======


        btnPrevious.setVisibility(View.INVISIBLE);
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int nextTag = Integer.parseInt(btnNext.getTag().toString());
                    if (nextTag > 0) {
                        innerPager.setCurrentItem(nextTag - 1, true);
                        btnNext.setTag(Integer.toString(nextTag - 1));
                        btnPrevious.setVisibility(View.VISIBLE);
                        btnNext.setVisibility(View.VISIBLE);
                        if (nextTag == 1) {
                            btnPrevious.setVisibility(View.INVISIBLE);
                        }
                    } else if (nextTag == 0) {
                        btnPrevious.setVisibility(View.INVISIBLE);
                    }
                } catch (Exception e) {
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
                //need to notify other adapter
                //adapter.customNotify(position);

            }
        });

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

        LinearLayout.LayoutParams half06 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 0.7f);
        LinearLayout.LayoutParams half04 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 0.3f);
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

        for (int f = 0; f < baggageMealReceive.getSegment().size(); f++) {

            if (travellerAddonSegment.get(f).get(travellerPosition).getSsrListPerPassenger().size() > 0) {

                LinearLayout servicesRow = new LinearLayout(getActivity());
                servicesRow.setOrientation(LinearLayout.HORIZONTAL);
                servicesRow.setPadding(0, 15, 15, 20);
                servicesRow.setWeightSum(1);
                servicesRow.setLayoutParams(matchParent2);

                TextView txtServicesName = new TextView(getActivity());
                txtServicesName.setText(baggageMealReceive.getSegment().get(f).getDepartureStation() + " - " + baggageMealReceive.getSegment().get(f).getArrivalStation());
                //txtServicesName.setLayoutParams(matchParent2);
                txtServicesName.setGravity(Gravity.LEFT);
                txtServicesName.setTextSize(18);
                txtServicesName.setMarqueeRepeatLimit(1);
                txtServicesName.setMaxLines(2);
                txtServicesName.setTypeface(null, Typeface.BOLD);
                txtServicesName.setTextColor(ContextCompat.getColor(getActivity(), R.color.default_theme_colour));
                servicesRow.addView(txtServicesName);

                added_addon.addView(servicesRow);

                for (int xx = 0; xx < travellerAddonSegment.get(f).get(travellerPosition).getSsrListPerPassenger().size(); xx++) {

                    LinearLayout.LayoutParams main = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1f);
                    main.setMargins(10, 5, 10, 5);

                    final LinearLayout ssrRowMain = new LinearLayout(getActivity());
                    ssrRowMain.setOrientation(LinearLayout.HORIZONTAL);
                    servicesRow.setPadding(20, 5, 15, 5);
                    ssrRowMain.setWeightSum(1);
                    //ssrRowMain.setLayoutParams(main);
                    //ssrRowMain.setLayoutParams(matchParent2);

                    //no time to change .. just check back with the list
                    LinearLayout ssrRow = new LinearLayout(getActivity());
                    ssrRow.setOrientation(LinearLayout.HORIZONTAL);
                    ssrRow.setPadding(2, 2, 2, 2);
                    ssrRow.setWeightSum(1);
                    ssrRow.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bottom_line_border));
                    ssrRow.setLayoutParams(matchParent2);

                    String servicesName = "";
                    String servicePrice = "";

                    for (int c = 0; c < baggageMealReceive.getSegment().get(f).getMeal().size(); c++) {
                        if (travellerAddonSegment.get(f).get(travellerPosition).getSsrListPerPassenger().size() > 0) {
                            Log.e("Segment" + f, travellerAddonSegment.get(f).get(travellerPosition).getSsrListPerPassenger().get(xx) + " - " + baggageMealReceive.getSegment().get(f).getMeal().get(c).getSSRCode());

                            if (travellerAddonSegment.get(f).get(travellerPosition).getSsrListPerPassenger().get(xx).equals(baggageMealReceive.getSegment().get(f).getMeal().get(c).getSSRCode())) {
                                servicesName = baggageMealReceive.getSegment().get(f).getMeal().get(c).getDescription();
                                servicePrice = baggageMealReceive.getSegment().get(f).getMeal().get(c).getPoints();
                                //when click
                                Log.e("SN" + servicesName, "SP" + servicePrice);
                                s1 = servicesName;
                                s2 = servicePrice;
                            }
                        }

                    }

                    final ImageView removeSSR = new ImageView(getActivity());
                    removeSSR.setTag(Integer.toString(f) + "/-" + Integer.toString(travellerPosition) + "/-" + Integer.toString(xx));
                    //removeSSR.setLayoutParams(half04);
                    removeSSR.setPadding(0, 0, 0, 0);
                    removeSSR.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_addon_remove));

                    ssrRowMain.addView(removeSSR);
                    ssrRowMain.setTag(Integer.toString(f));

                    removeSSR.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            String string = removeSSR.getTag().toString();
                            String[] parts = string.split("/-");

                            ssrRowMain.setVisibility(View.GONE);
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
                    ssrName.setGravity(Gravity.LEFT);
                    ssrName.setSingleLine();

                    TextView txtServicePrice = new TextView(getActivity());
                    String pts = getString(R.string.pts);

                    txtServicePrice.setText(servicePrice + " " + pts);
                    txtServicePrice.setLayoutParams(half06);
                    txtServicePrice.setTextSize(16);
                    txtServicePrice.setTypeface(null, Typeface.BOLD);
                    txtServicePrice.setGravity(Gravity.RIGHT);

                    Space space = new Space(getActivity());
                    space.setPadding(0, 15, 0, 15);
                    ssrRow.addView(ssrName);
                    ssrRow.addView(txtServicePrice);

                    //ssrRow.addView(view);

                    ssrRowMain.addView(ssrRow);

                    added_addon.addView(ssrRowMain);
                    added_addon.addView(space);

                }

            }

        }
    }

    public static void onRequestMealSSR(Activity act) {

        if (noMeal) {
            act.finish();
        } else {
            initiateLoading(act);

            Realm realm = RealmObjectController.getRealmInstance(act);
            final RealmResults<UserInfoJSON> result2 = realm.where(UserInfoJSON.class).findAll();
            LoginReceive loginReceive = (new Gson()).fromJson(result2.get(0).getUserInfo(), LoginReceive.class);

            travellerAddonDone = adapter.getAdapterObj();


            //AddMealRequest addMealRequest = new AddMealRequest();


            HashMap<String, String> dicMap = new HashMap<String, String>();
            for (int x = 0; x < travellerAddonDone.size(); x++) {

                for (int v = 0; v < travellerAddonDone.get(x).size(); v++) {
                    String appendSSRCode = "";

                    for (int y = 0; y < travellerAddonDone.get(x).get(v).getSsrListPerPassenger().size(); y++) {

                        String ssrCode = travellerAddonDone.get(x).get(v).getSsrListPerPassenger().get(y);
                        Log.e("Y", ssrCode);
                        //Log.e("Segment" + Integer.toString(x) + ":" + "Passenger" + Integer.toString(y), ssrCode);

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
                        dicMap.put("Segment" + x + "Passenger" + v, appendSSRCode.trim());
                    }
                }
            }

            dicMap.put("UserName", loginReceive.getUserName());
            dicMap.put("Token", loginReceive.getToken());
            dicMap.put("Signature", loginReceive.getSignature());
            dicMap.put("FROM_WHICH", "MEAL_ASSIGN");

            staticPresenter.onAddMealRequest(dicMap);
        }
    }

    @Override
    public void onMealAddReceive(AddMealReceive obj) {

        dismissLoading();
        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {

            Gson gsonUserInfo = new Gson();
            Realm realm = RealmObjectController.getRealmInstance(getActivity());
            final RealmResults<AddonCached> result2 = realm.where(AddonCached.class).findAll();
            AddOnInfo addOnInfo = (new Gson()).fromJson(result2.get(0).getAddonInfo(), AddOnInfo.class);

            addOnInfo.setSetMealSelected("Y");


            List<AddOnInfo.MealList> all = new ArrayList<AddOnInfo.MealList>();
            AddOnInfo.MealList mItem = new AddOnInfo().new MealList();

            mItem.setMealName(s1);
            mItem.setMealPassengerName("");
            mItem.setMealPoint(s2);
            all.add(mItem);

            String addOnInfoString = gsonUserInfo.toJson(addOnInfo);
            RealmObjectController.saveAddOnInfo(getActivity(), addOnInfoString);
            getActivity().finish();

        }
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
