package com.app.tbd.ui.Activity.Homepage;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.tbd.MainController;
import com.app.tbd.R;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Activity.BookingFlight.AnotherList;
import com.app.tbd.ui.Activity.BookingFlight.PushNotificationActivity;
import com.app.tbd.ui.Activity.BookingFlight.SearchFlightFragment;
import com.app.tbd.ui.Activity.Picker.SelectionListFragment;
import com.app.tbd.ui.Activity.PushNotificationInbox.RealmInboxNotification;
import com.app.tbd.ui.Activity.TAB.TabActivity;
import com.app.tbd.ui.Model.JSON.GCMClass;
import com.app.tbd.ui.Model.JSON.OverlayInfoGSON;
import com.app.tbd.ui.Model.JSON.Promo;
import com.app.tbd.ui.Model.JSON.PromoTransaction;
import com.app.tbd.ui.Model.JSON.PromotionCache;
import com.app.tbd.ui.Model.Receive.OverlayReceive;
import com.app.tbd.ui.Model.Receive.PromotionReceive;
import com.app.tbd.ui.Presenter.HomePresenter;
import com.app.tbd.ui.Realm.RealmObjectController;
import com.app.tbd.utils.DropDownItem;
import com.app.tbd.utils.ExpandAbleGridView;
import com.app.tbd.utils.SharedPrefManager;
//import com.google.firebase.messaging.FirebaseMessaging;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
//import com.makeramen.roundedimageview.RoundedImageView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nhaarman.listviewanimations.appearance.AnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.SwingRightInAnimationAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import io.realm.Realm;
import io.realm.RealmResults;

public class HomeFragment extends BaseFragment {

    @Inject
    HomePresenter presenter;


    @InjectView(R.id.homePromoPager)
    ViewPager homePromoPager;

    @InjectView(R.id.home_layout)
    ScrollView home_layout;


    static ExpandAbleGridView fcListView;
    static ImageView promotionBanner;
    static TextView fcSE;
    static TextView fixedPoint;
    static TextView txtFinalCallSearch;
    static LinearLayout finalCallLayout;
    static LinearLayout fcNoRoute;
    static TextView txtPromoSearch;
    static LinearLayout fgNoRoute;
    static RelativeLayout fgLayout;
    static ArrayList<Promo> finalCall = new ArrayList<Promo>();
    static ArrayList<Promo> getPromo = new ArrayList<Promo>();
    static Boolean scheduleExtension = true;
    static Boolean listAvailable = false;

    static PromotionReceive promotionReceive;
    static OverlayReceive overlayReceive;

    //@InjectView(R.id.recycler_view)
    //RecyclerView recyclerView;
    //RecyclerView.LayoutManager layoutManager;
    //RecyclerView.Adapter adapter;


    private int fragmentContainerId;
    ArrayList<DropDownItem> promotionList = new ArrayList<DropDownItem>();
    static SharedPrefManager pref;
    private static AnimationAdapter mAnimAdapter;

    View view;
    View promoHeader;

    String CURRENT_PICKER;
    String selectedDepartureCode;
    Activity act;

    int icon;

    public static HomeFragment newInstance() {

        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //MainApplication.get(getActivity()).createScopedGraph(new HomeModule(this)).inject(this);
        RealmObjectController.clearCachedResult(getActivity());
        act = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.home, container, false);
        ButterKnife.inject(this, view);
        aq.recycle(view);
        pref = new SharedPrefManager(getActivity());

        MainController.setHomeStatus();

        /*sendNotification("Message","Title", "0007");*/
        //setOverlay("https://cms-assets.tutsplus.com/uploads/users/346/posts/16184/image/abstractportrait_final.jpg");

        overlayFunction(act);

        //Need to use static
        fcListView = (ExpandAbleGridView) view.findViewById(R.id.finalCallListView);
        promotionBanner = (ImageView) view.findViewById(R.id.promotionBanner);
        fcSE = (TextView) view.findViewById(R.id.fcSE);
        fixedPoint = (TextView) view.findViewById(R.id.fixedPoint);
        txtFinalCallSearch = (TextView) view.findViewById(R.id.txtFinalCallSearch);
        finalCallLayout = (LinearLayout) view.findViewById(R.id.finalCallLayout);
        fcNoRoute = (LinearLayout) view.findViewById(R.id.fcNoRoute);
        fgLayout = (RelativeLayout) view.findViewById(R.id.fgLayout);
        txtPromoSearch = (TextView) view.findViewById(R.id.txtPromoSearch);
        fgNoRoute = (LinearLayout) view.findViewById(R.id.fgNoRoute);

        txtFinalCallSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CURRENT_PICKER = "FINAL_CALL";
                showCountrySelector(getActivity(), CURRENT_PICKER, "NA");

            }
        });

        txtPromoSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CURRENT_PICKER = "PROMO_SEARCH";
                showCountrySelector(getActivity(), CURRENT_PICKER, "NA");

            }
        });

        ///FirebaseMessaging.getInstance().subscribeToTopic("news");
        //String token = FirebaseInstanceId.getInstance().getToken();
        //Toast.makeText(getActivity(), token, Toast.LENGTH_SHORT).show();

        home_layout.smoothScrollTo(0, view.getTop());
        dataSetup(act);

        fcListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Promo obj = (Promo) fcListView.getItemAtPosition(position);

                //with information
                PromoTransaction promoTransaction = new PromoTransaction();
                promoTransaction.setArrivalCode(obj.getArrivalStationCode());
                promoTransaction.setDepartureCode(obj.getDepartCode());
                promoTransaction.setDepartText(obj.getFlightDepartText());
                promoTransaction.setArrivalText(obj.getFlightArrivalText());
                promoTransaction.setFlightType(obj.getFlightType());
                promoTransaction.setTravellingPeriodFrom(obj.getTravellingPeriodFrom());
                promoTransaction.setTravellingPeriodTo(obj.getTravellingPeriodTo());
                promoTransaction.setFlightCode(obj.getFlightCode());

                //check currency
                String currencyCode = getCurrencyCode(getActivity(), selectedDepartureCode);
                promoTransaction.setDepartureCurrencyCode(currencyCode);

                TabActivity.setPager(2);
                SearchFlightFragment.triggerPromoInfo(getActivity(), promoTransaction);
            }
        });

        return view;
    }

    public void showCountrySelector(Activity act, String data, String code) {
        if (act != null) {
            try {
                android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
                SelectionListFragment routeListDialogFragment = SelectionListFragment.newInstance(data, code, false);
                routeListDialogFragment.setTargetFragment(HomeFragment.this, 0);
                routeListDialogFragment.show(fm, "countryListDialogFragment");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        } else {
            if (requestCode == 1) {
                DropDownItem selectedFlight = data.getParcelableExtra(CURRENT_PICKER);

                if (CURRENT_PICKER.equals("PROMO_SEARCH")) {
                    txtPromoSearch.setText(selectedFlight.getText());
                    txtPromoSearch.setTag(selectedFlight.getCode());

                    getPromo = promotionLocalSearchV2(selectedFlight.getCode(), "FG", promotionReceive);

                    if (getPromo.size() == 0) {
                        fgNoRoute.setVisibility(View.VISIBLE);
                        fgLayout.setVisibility(View.GONE);
                    } else {
                        fgNoRoute.setVisibility(View.GONE);
                        fgLayout.setVisibility(View.VISIBLE);
                    }

                    setPromoPager(getPromo);
                    //localSearch();
                } else {
                    txtFinalCallSearch.setText(selectedFlight.getText());
                    txtFinalCallSearch.setTag(selectedFlight.getCode());

                    if (!scheduleExtension) {
                        finalCall = promotionLocalSearchV2(selectedFlight.getCode(), "FC", promotionReceive);
                    } else {
                        finalCall = promotionLocalSearchV2(selectedFlight.getCode(), "SE", promotionReceive);
                    }

                    if (finalCall.size() > 0) {
                        fcNoRoute.setVisibility(View.GONE);
                    } else {
                        fcNoRoute.setVisibility(View.VISIBLE);
                    }

                    setFinalCallList(finalCall);
                }
            }
        }
    }

    public void resetPager(ArrayList<Promo> promoInfo) {

    }

    public static void overlayFunction(Activity act){
        //check overlay
        Realm realm = RealmObjectController.getRealmInstance(act);
        final RealmResults<OverlayInfoGSON> result3 = realm.where(OverlayInfoGSON.class).findAll();

        if (result3.size() > 0) {

            overlayReceive = (new Gson()).fromJson(result3.get(0).getOverlayInfo(), OverlayReceive.class);
            if (overlayReceive.getOverlay().getImage() != null) {

                Log.e("Overlay not null", overlayReceive.getOverlay().getImage());

                HashMap<String, String> initOverlay = pref.getOverlay();
                String overlay = initOverlay.get(SharedPrefManager.OVERLAY);

                //String interval = overlayReceive.getOverlay().getRepeatTime();
                //Boolean popupOverlay = compareOverlay(interval);
                Boolean popupOverlay = compareOverlay();

                Calendar c = Calendar.getInstance();
                int minute = c.get(Calendar.MINUTE);

                //Log.e(Boolean.toString(popupOverlay),overlay);

                if (popupOverlay && overlay == null) {
                    setOverlay(overlayReceive.getOverlay().getImage(), act);
                    pref.setOverlay(Integer.toString(minute));
                    Log.e("Popup", "1");
                } else if(popupOverlay){
                    setOverlay(overlayReceive.getOverlay().getImage(), act);
                    pref.setOverlay(Integer.toString(minute));
                    Log.e("Popup", "2");
                }else {
                    Log.e("Popup", "3");
                }
            }
        }
    }

   /* public ArrayList<Promo> promotionLocalSearch(String departureCode, String type) {

        int test = 0;
        selectedDepartureCode = departureCode;

        ArrayList<Promo> promo = new ArrayList<Promo>();
        if (promotionReceive.getPromoList().size() > 0) {
            for (int g = 0; g < promotionReceive.getPromoList().size(); g++) {
                if (promotionReceive.getPromoList().get(g).getCategoryCode().equals(type)) {
                    for (int h = 0; h < promotionReceive.getPromoList().get(g).getDetails().size(); h++) {
                        if (promotionReceive.getPromoList().get(g).getDetails().get(h).getDepartureCityCode().equals(departureCode)) {
                            test++;

                            Promo promo1 = new Promo();
                            promo1.setDepartureStation(promotionReceive.getPromoList().get(g).getDetails().get(h).getDeparture());
                            promo1.setArrivalStation(promotionReceive.getPromoList().get(g).getDetails().get(h).getDestination());
                            promo1.setArrivalStationCode(promotionReceive.getPromoList().get(g).getDetails().get(h).getDestinationCityCode());
                            promo1.setPromoFrom(promotionReceive.getPromoList().get(g).getDetails().get(h).getTravelPeriodFrom());
                            promo1.setPromoTo(promotionReceive.getPromoList().get(g).getDetails().get(h).getTravelPeriodTo());
                            promo1.setFlightType(promotionReceive.getPromoList().get(g).getDetails().get(h).getFlightType());
                            promo1.setCountryName(promotionReceive.getPromoList().get(g).getDetails().get(h).getCountryName());
                            promo1.setBeforeDiscount(promotionReceive.getPromoList().get(g).getDetails().get(h).getPoint());
                            promo1.setFlightDepartText(promotionReceive.getPromoList().get(g).getDetails().get(h).getDeparture() + " (" + promotionReceive.getPromoList().get(g).getDetails().get(h).getDepartureCityCode() + ")");
                            promo1.setFlightArrivalText(promotionReceive.getPromoList().get(g).getDetails().get(h).getDestination() + " (" + promotionReceive.getPromoList().get(g).getDetails().get(h).getDestinationCityCode() + ")");
                            promo1.setPromotionImage(promotionReceive.getPromoList().get(g).getDetails().get(h).getImage());
                            promo1.setPoint(promotionReceive.getPromoList().get(g).getDetails().get(h).getPoint());
                            promo1.setDepartCode(departureCode);
                            promo1.setTravellingPeriodFrom(promotionReceive.getPromoList().get(g).getDetails().get(h).getTravelPeriodFrom());
                            promo1.setTravellingPeriodTo(promotionReceive.getPromoList().get(g).getDetails().get(h).getTravelPeriodTo());
                            promo1.setFlightCode(type);

                            promo.add(promo1);
                        }
                    }
                }

            }
        }

        return promo;
    }*/

    public ArrayList<Promo> promotionLocalSearchV2(String departureCode, String type, PromotionReceive promotionReceive) {

        int test = 0;
        selectedDepartureCode = departureCode;

        ArrayList<Promo> promo = new ArrayList<Promo>();
        if (promotionReceive.getPromoList().size() > 0) {
            for (int g = 0; g < promotionReceive.getPromoList().size(); g++) {
                if (promotionReceive.getPromoList().get(g).getCategoryCode().equals(type)) {
                    for (int h = 0; h < promotionReceive.getPromoList().get(g).getDetails().size(); h++) {
                        if (promotionReceive.getPromoList().get(g).getDetails().get(h).getDepartureCityCode().equals(departureCode)) {
                            test++;

                            Promo promo1 = new Promo();
                            promo1.setDepartureStation(promotionReceive.getPromoList().get(g).getDetails().get(h).getDeparture());
                            promo1.setArrivalStation(promotionReceive.getPromoList().get(g).getDetails().get(h).getDestination());
                            promo1.setArrivalStationCode(promotionReceive.getPromoList().get(g).getDetails().get(h).getDestinationCityCode());
                            promo1.setPromoFrom(promotionReceive.getPromoList().get(g).getDetails().get(h).getTravelPeriodFrom());
                            promo1.setPromoTo(promotionReceive.getPromoList().get(g).getDetails().get(h).getTravelPeriodTo());
                            promo1.setFlightType(promotionReceive.getPromoList().get(g).getDetails().get(h).getFlightType());
                            promo1.setCountryName(promotionReceive.getPromoList().get(g).getDetails().get(h).getCountryName());
                            promo1.setBeforeDiscount(promotionReceive.getPromoList().get(g).getDetails().get(h).getPoint());
                            promo1.setFlightDepartText(promotionReceive.getPromoList().get(g).getDetails().get(h).getDeparture() + " (" + promotionReceive.getPromoList().get(g).getDetails().get(h).getDepartureCityCode() + ")");
                            promo1.setFlightArrivalText(promotionReceive.getPromoList().get(g).getDetails().get(h).getDestination() + " (" + promotionReceive.getPromoList().get(g).getDetails().get(h).getDestinationCityCode() + ")");
                            promo1.setPromotionImage(promotionReceive.getPromoList().get(g).getDetails().get(h).getImage());
                            promo1.setPoint(promotionReceive.getPromoList().get(g).getDetails().get(h).getPoint());
                            promo1.setDepartCode(departureCode);
                            promo1.setTravellingPeriodFrom(promotionReceive.getPromoList().get(g).getDetails().get(h).getTravelPeriodFrom());
                            promo1.setTravellingPeriodTo(promotionReceive.getPromoList().get(g).getDetails().get(h).getTravelPeriodTo());
                            promo1.setFlightCode(type);

                            promo.add(promo1);
                        }
                    }
                }

            }
        }

        return promo;
    }

    public void dataSetup(Activity act) {

        Realm realm = RealmObjectController.getRealmInstance(act);
        final RealmResults<PromotionCache> result2 = realm.where(PromotionCache.class).findAll();
        promotionReceive = (new Gson()).fromJson(result2.get(0).getPromotion(), PromotionReceive.class);


        if (promotionReceive.getBannerName() != null) {

            Glide.with(act).load(promotionReceive.getBannerName())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    //.placeholder(ContextCompat.getDrawable(getActivity(), R.drawable.promo_home))
                    .into(promotionBanner);

        }

        if (promotionReceive.getPromotionHeader() != null) {
            fcSE.setText(promotionReceive.getPromotionHeader());
        }

        if (promotionReceive.getFixedPoint() != null) {
            fixedPoint.setText(promotionReceive.getFixedPoint());
        }

        String airportName = "NA";

        if (promotionReceive.getPromoList().size() > 0) {

            //default to KL
            for (int loop = 0; loop < promotionReceive.getPromoList().size(); loop++) {
                if (promotionReceive.getPromoList().get(loop).getCategoryCode().equals("FC")) {
                    if (promotionReceive.getPromoList().get(loop).getDetails().size() > 0) {
                        //finalCall = promotionLocalSearch(promotionReceive.getPromoList().get(loop).getDetails().get(0).getDepartureCityCode(), "FC");
                        //txtFinalCallSearch.setText(promotionReceive.getPromoList().get(loop).getDetails().get(0).getDeparture() + " (" + promotionReceive.getPromoList().get(loop).getDetails().get(0).getDepartureCityCode() + ")");

                        //fcSE.setText(finalCallText);

                        finalCall = promotionLocalSearchV2(promotionReceive.getDefault_Airport(), "FC", promotionReceive);
                        airportName = promotionReceive.getPromoList().get(loop).getDetails().get(0).getDeparture();
                        scheduleExtension = false;
                        listAvailable = true;

                        break;
                    }
                }
            }

            //SCHEDULE EXTENSION
            if (scheduleExtension) {
                for (int loop = 0; loop < promotionReceive.getPromoList().size(); loop++) {
                    if (promotionReceive.getPromoList().get(loop).getCategoryCode().equals("SE")) {
                        if (promotionReceive.getPromoList().get(loop).getDetails().size() > 0) {
                            finalCall = promotionLocalSearchV2(promotionReceive.getDefault_Airport(), "SE", promotionReceive);
                            airportName = promotionReceive.getPromoList().get(loop).getDetails().get(0).getDeparture();
                            //fcSE.setText(scheduledExtensionText);

                            listAvailable = true;

                            break;
                        }
                    }
                }
            }

        }

        txtFinalCallSearch.setText(promotionReceive.getDefault_AirportName() + " (" + promotionReceive.getDefault_Airport() + ")");

        if (!listAvailable) {
            finalCallLayout.setVisibility(View.GONE);
        }

        if (finalCall.size() == 0) {
            fcNoRoute.setVisibility(View.VISIBLE);
        } else {
            fcNoRoute.setVisibility(View.GONE);
        }

        getPromo = promotionLocalSearchV2(promotionReceive.getDefault_Airport(), "FG", promotionReceive);
        txtPromoSearch.setText(promotionReceive.getDefault_AirportName() + " (" + promotionReceive.getDefault_Airport() + ")");

        if (getPromo.size() == 0) {
            fgNoRoute.setVisibility(View.VISIBLE);
            fgLayout.setVisibility(View.GONE);
        } else {
            fgNoRoute.setVisibility(View.GONE);
            fgLayout.setVisibility(View.VISIBLE);
        }

        setPromoPager(getPromo);
        setFinalCallList(finalCall);

    }

    public static Boolean compareOverlay() {

        Boolean status;

        HashMap<String, String> initOverlay = pref.getOverlay();
        String overlay = initOverlay.get(SharedPrefManager.OVERLAY);

        if (overlay == null) {
            status = true;

        } else {


            Calendar c = Calendar.getInstance();
            int currentMinute = c.get(Calendar.MINUTE);

            Log.e("xx " + overlay, "yy" + Integer.toString(currentMinute));

            if (Math.abs(currentMinute - Integer.parseInt(overlay)) > 2) {
                Log.e("P", "Y");
                status = true;
            } else {
                Log.e("P", "N");
                status = false;
            }

        }

        return status;
    }

    public static void setOverlay(String imagePath, Activity act) {

        final Dialog dialog = new Dialog(act, R.style.DialogTheme);

        LayoutInflater li = LayoutInflater.from(act);
        final View myView = li.inflate(R.layout.overlay, null);

        TextView txtCloseOverlay = (TextView) myView.findViewById(R.id.txtCloseOverlay);
        RoundedImageView overlayImage = (RoundedImageView) myView.findViewById(R.id.overlay_image);

        displayImage(act, overlayImage, imagePath);
        txtCloseOverlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //run this function back
                        //pref.setOverlay("Y");
                    }
                }, 20000);
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(act);
        builder.setView(myView);

        dialog.setContentView(myView);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#CCffffff")));
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(lp);
        dialog.show();

    }


    public void setStaticPromo(Activity act) {

        Realm realm = RealmObjectController.getRealmInstance(act);
        final RealmResults<PromotionCache> result2 = realm.where(PromotionCache.class).findAll();
        promotionReceive = (new Gson()).fromJson(result2.get(0).getPromotion(), PromotionReceive.class);

        HashMap<String, String> initPromoLastUpdate = pref.getPromoLastUpdate();
        String promoLastUpdate = initPromoLastUpdate.get(SharedPrefManager.PROMO_LAST_UPDATE);

        if (promoLastUpdate == null || promoLastUpdate.equals("")) {
            Log.e("PROMO", "PROMO");
        } else {
            if (!promotionReceive.getLastUpdated().equals(promoLastUpdate)) {
                if (promotionReceive.getBannerName() != null) {

                    Glide.with(act).load(promotionReceive.getBannerName())
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            //.placeholder(ContextCompat.getDrawable(getActivity(), R.drawable.promo_home))
                            .into(promotionBanner);
                }

                if (promotionReceive.getPromotionHeader() != null) {
                    fcSE.setText(promotionReceive.getPromotionHeader());
                }

                if (promotionReceive.getFixedPoint() != null) {
                    fixedPoint.setText(promotionReceive.getFixedPoint());
                }

                String airportName = "NA";

                if (promotionReceive.getPromoList().size() > 0) {

                    //default to KL
                    for (int loop = 0; loop < promotionReceive.getPromoList().size(); loop++) {
                        if (promotionReceive.getPromoList().get(loop).getCategoryCode().equals("FC")) {
                            if (promotionReceive.getPromoList().get(loop).getDetails().size() > 0) {

                                finalCall = promotionLocalSearchV2(promotionReceive.getDefault_Airport(), "FC", promotionReceive);
                                airportName = promotionReceive.getPromoList().get(loop).getDetails().get(0).getDeparture();
                                scheduleExtension = false;
                                listAvailable = true;

                                break;
                            }
                        }
                    }

                    //SCHEDULE EXTENSION
                    if (scheduleExtension) {
                        for (int loop = 0; loop < promotionReceive.getPromoList().size(); loop++) {
                            if (promotionReceive.getPromoList().get(loop).getCategoryCode().equals("SE")) {
                                if (promotionReceive.getPromoList().get(loop).getDetails().size() > 0) {
                                    finalCall = promotionLocalSearchV2(promotionReceive.getDefault_Airport(), "SE", promotionReceive);
                                    airportName = promotionReceive.getPromoList().get(loop).getDetails().get(0).getDeparture();

                                    listAvailable = true;

                                    break;
                                }
                            }
                        }
                    }
                }

                txtFinalCallSearch.setText(promotionReceive.getDefault_AirportName() + " (" + promotionReceive.getDefault_Airport() + ")");

                if (!listAvailable) {
                    finalCallLayout.setVisibility(View.GONE);
                }

                if (finalCall.size() == 0) {
                    fcNoRoute.setVisibility(View.VISIBLE);
                } else {
                    fcNoRoute.setVisibility(View.GONE);
                }

                getPromo = promotionLocalSearchV2(promotionReceive.getDefault_Airport(), "FG", promotionReceive);
                txtPromoSearch.setText(promotionReceive.getDefault_AirportName() + " (" + promotionReceive.getDefault_Airport() + ")");

                if (getPromo.size() == 0) {
                    fgNoRoute.setVisibility(View.VISIBLE);
                    fgLayout.setVisibility(View.GONE);
                } else {
                    fgNoRoute.setVisibility(View.GONE);
                    fgLayout.setVisibility(View.VISIBLE);
                }

                //setPromoPager(getPromo);
                setFinalCallListStatic(finalCall, act);
            }
        }

    }

    public void refresh(Activity act) {

        setStaticPromo(act);

    }

    /*public void setImageBanner(PromotionReceive promo){
        Glide.with(this)
                .load(promo.getBannerName())
                .dontAnimate()
                .thumbnail(0.4f)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(promoBanner);
    }*/

    public void setFinalCallList(final ArrayList<Promo> finalCallList) {

        FinalCallAdapter fcAdapter = new FinalCallAdapter(getActivity(), finalCallList);

        mAnimAdapter = new SwingBottomInAnimationAdapter(new SwingBottomInAnimationAdapter(fcAdapter));
        mAnimAdapter.setAbsListView(fcListView);
        fcListView.setAdapter(mAnimAdapter);

    }

    public static void setFinalCallListStatic(final ArrayList<Promo> finalCallList, Activity act) {

        FinalCallAdapter fcAdapter = new FinalCallAdapter(act, finalCallList);

        mAnimAdapter = new SwingBottomInAnimationAdapter(new SwingBottomInAnimationAdapter(fcAdapter));
        mAnimAdapter.setAbsListView(fcListView);
        fcListView.setAdapter(mAnimAdapter);

    }

    public void setPromoPager(final ArrayList<Promo> arrayPromo) {

        final Vector<View> innerPages = new Vector<View>();
        LayoutInflater promoPagerLayout = LayoutInflater.from(getActivity());

        for (int h = 0; h < arrayPromo.size(); h++) {
            View insideMeal = promoPagerLayout.inflate(R.layout.home_promo_pager, null);

            final TextView txtArrivalStation = (TextView) insideMeal.findViewById(R.id.txtArrivalStation);
            txtArrivalStation.setText(arrayPromo.get(h).getArrivalStation() + ", " + arrayPromo.get(h).getCountryName());
            txtArrivalStation.setTag(arrayPromo.get(h).getFlightArrivalText());

            final TextView flightType = (TextView) insideMeal.findViewById(R.id.flightType);
            flightType.setText(arrayPromo.get(h).getFlightType());
            flightType.setTag(arrayPromo.get(h).getFlightType());

            final ImageView promotionImage = (ImageView) insideMeal.findViewById(R.id.txtPromotionImage);
            Glide.with(getActivity()).load(arrayPromo.get(h).getPromotionImage())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(ContextCompat.getDrawable(getActivity(), R.drawable.promo_home))
                    .into(promotionImage);

            String now = getString(R.string.home_now);
            String pts_text = getString(R.string.pts);
            final TextView txtCurrentPtsForPromo = (TextView) insideMeal.findViewById(R.id.txtCurrentPtsForPromo);
            txtCurrentPtsForPromo.setText(now + " " + changeThousand(arrayPromo.get(h).getBeforeDiscount()) + " " + pts_text);
            txtCurrentPtsForPromo.setTag(arrayPromo.get(h).getFlightDepartText());

            final TextView txtBeforePoints = (TextView) insideMeal.findViewById(R.id.txtBeforePoints);
            txtBeforePoints.setPaintFlags(txtBeforePoints.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            txtBeforePoints.setText(arrayPromo.get(h).getBeforeDiscount());
            txtBeforePoints.setTag(arrayPromo.get(h).getDepartCode());

            final LinearLayout redeemBtn = (LinearLayout) insideMeal.findViewById(R.id.txtBtnRedeem);
            redeemBtn.setTag(arrayPromo.get(h).getArrivalStationCode());
            redeemBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    TabActivity.setPager(2);
                    //with information
                    PromoTransaction promoTransaction = new PromoTransaction();
                    promoTransaction.setArrivalCode(redeemBtn.getTag().toString());
                    promoTransaction.setDepartureCode(txtBeforePoints.getTag().toString());
                    promoTransaction.setDepartText(txtCurrentPtsForPromo.getTag().toString());
                    promoTransaction.setArrivalText(txtArrivalStation.getTag().toString());
                    promoTransaction.setFlightType(flightType.getTag().toString());
                    promoTransaction.setFlightCode("FG");

                    //check currency
                    String currencyCode = getCurrencyCode(getActivity(), selectedDepartureCode);
                    promoTransaction.setDepartureCurrencyCode(currencyCode);

                    SearchFlightFragment.triggerPromoInfo(getActivity(), promoTransaction);

                }
            });

            innerPages.add(insideMeal);
        }

        HomePromoPager innerAdapter = new HomePromoPager(getContext(), innerPages);
        homePromoPager.setAdapter(innerAdapter);

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
                        homePromoPager.setCurrentItem(nextTag + 1, true);
                        if (nextTag == innerPages.size() - 2) {
                            btnNext.setVisibility(View.INVISIBLE);
                        } else {
                            btnNext.setTag(Integer.toString(nextTag + 1));
                        }
                    } else if (nextTag == innerPages.size() - 1) {
                        btnNext.setVisibility(View.INVISIBLE);
                    }
                } catch (Exception e) {

                    btnNext.setVisibility(View.GONE);
                }
            }
        });

        //btnPrevious
        btnPrevious.setVisibility(View.INVISIBLE);
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int nextTag = Integer.parseInt(btnNext.getTag().toString());
                    if (nextTag > 0) {
                        homePromoPager.setCurrentItem(nextTag - 1, true);
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
    }

    private void sendNotification(String messageBody, String messageTitle, String messageId) {

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            icon = R.drawable.test_icon;
        } else {
            icon = R.mipmap.latest_icon;
        }

        //change icon
        String dateString = getCurrentTimeStamp();
        saveNotificationInbox("-", messageBody, messageTitle, dateString, messageId);

        //save message to realm object
        RealmObjectController.clearNotificationMessage(getActivity());
        RealmObjectController.saveNotificationMessage(getActivity(), messageBody, messageTitle);

        Intent intent = new Intent(getActivity(), PushNotificationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0  /*Request code*/ , intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(act)
        .setSmallIcon(icon)
        .setContentTitle(messageTitle)
        .setContentText(messageBody)
        .setAutoCancel(true)
        .setSound(defaultSoundUri)
        .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0  /*ID of notification*/ , notificationBuilder.build());
    }

    public void saveNotificationInbox(String username, String message, String title, String date, String messageId) {

        //save push notification message
        GCMClass gcmClass = new GCMClass();
        gcmClass.setTitle(title);
        gcmClass.setMessage(message);
        gcmClass.setDate(date);
        gcmClass.setMessageID(messageId);
        RealmInboxNotification.notificationAddList(act, gcmClass, "-");

    }

    @Override
    public void onResume() {
        super.onResume();
        //presenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //presenter.onPause();
    }


    public void exitApp() {

        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText(getString(R.string.home_exit))
                .setContentText(getString(R.string.home_confirm_exit))
                .showCancelButton(true)
                .setCancelText(getString(R.string.home_cancel))
                .setConfirmText(getString(R.string.home_close))
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {

                        getActivity().finish();
                        System.exit(0);

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
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
