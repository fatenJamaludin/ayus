package com.app.tbd.ui.Activity.HolidayShaker;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

//import com.google.android.gms.analytics.Tracker;
//import com.google.gson.Gson;
//import com.metech.bigprepaid.AnalyticsApplication;
//import com.metech.bigprepaid.Controller;

import com.app.tbd.R;
import com.app.tbd.ShakeDetector;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Activity.BookingFlight.TravellerInfo.TravellerInfoActivity;
import com.app.tbd.ui.Activity.FragmentContainerActivity;
import com.mikhaellopez.circularimageview.CircularImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class HolidayShakerFragment extends BaseFragment {

    //@Inject
    //HomePresenter presenter;
    @InjectView(R.id.dummy_shaker_1)
    LinearLayout dummy_shaker_1;
    @InjectView(R.id.dummy_shaker_2)
    LinearLayout dummy_shaker_2;
    @InjectView(R.id.dummy_shaker_3)
    LinearLayout dummy_shaker_3;
    @InjectView(R.id.dummy_shaker_4)
    LinearLayout dummy_shaker_4;
    @InjectView(R.id.dummy_shaker_fb)
    LinearLayout dummy_shaker_fb;

    @InjectView(R.id.btnReedem1)
    Button btnReedem1;

    @InjectView(R.id.btnReedem2)
    Button btnReedem2;

    @InjectView(R.id.btnReedem3)
    Button btnReedem3;

    @InjectView(R.id.btnReedem4)
    Button btnReedem4;

    //@InjectView(R.id.fbTaggingUserImage)
    //CircularImageView fbTaggingUserImage;



    ImageView shakePhone, imgTaggedPlace;
    RelativeLayout afterLoginFB, taggedPlaceLinearLayout, taggedInfoLinearLayout;
    TextView txtTaggedPlaceName, txtTaggedPlace, txtTaggedPlaceByWho, shakeDevice, txtPts, txtFlightNumber;
    // SpinKitView spinKitView;


    CircularImageView shakedUserImage;

    // The following are used for the shake detection
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
    private int fragmentContainerId;
    private boolean startShake = true;
    private int totalFriend = 5;
    private int shaked = 0;
    private String activeToken, activeFriendShaked, activeFriendShakedId;

    public static HolidayShakerFragment newInstance() {

        HolidayShakerFragment fragment = new HolidayShakerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //BigPrepaidApplication.get(getActivity()).createScopedGraph(new HomeModule(this)).inject(this);
        //RealmObjectController.deleteRealmFile(getActivity());
        final MediaPlayer mp = MediaPlayer.create(getActivity(), R.raw.gets_in_the_way);
        final MediaPlayer mp2 = MediaPlayer.create(getActivity(), R.raw.result);

        if (startShake) {
            // ShakeDetector initialization
            mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
            mAccelerometer = mSensorManager
                    .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            mShakeDetector = new ShakeDetector();
            mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

                @Override
                public void onShake(int count) {
                /*
                 * The following method, "handleShakeEvent(count):" is a stub //
				 * method you would use to setup whatever you want done once the
				 * device has been shook.
				 */
                    //handleShakeEvent(count);

                    mp.start();

                    if (totalFriend > 0 && shaked < totalFriend) {

                        //  spinKitView.setVisibility(View.VISIBLE);
                        //shakePhone.setVisibility(View.GONE);

                        //taggedInfoLinearLayout.setVisibility(View.VISIBLE);
                        //taggedPlaceLinearLayout.setVisibility(View.VISIBLE);
                        //taggedPlaceLinearLayout.setAlpha(0.5f);
                        afterLoginFB.setVisibility(View.VISIBLE);

                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {

                                startShake = false;
                                if (shaked == 0) {
                                    afterLoginFB.setVisibility(View.GONE);
                                    dummy_shaker_1.setVisibility(View.VISIBLE);
                                    dummy_shaker_2.setVisibility(View.GONE);
                                    dummy_shaker_3.setVisibility(View.GONE);
                                    dummy_shaker_4.setVisibility(View.GONE);
                                    dummy_shaker_fb.setVisibility(View.GONE);
                                    mp2.start();
                                    shaked++;


                                }else if (shaked == 1) {
                                    afterLoginFB.setVisibility(View.GONE);
                                    dummy_shaker_1.setVisibility(View.GONE);
                                    dummy_shaker_2.setVisibility(View.VISIBLE);
                                    dummy_shaker_fb.setVisibility(View.GONE);
                                    dummy_shaker_3.setVisibility(View.GONE);
                                    dummy_shaker_4.setVisibility(View.GONE);
                                    mp2.start();
                                    shaked++;

                                }

                                else if (shaked == 2) {
                                    afterLoginFB.setVisibility(View.GONE);
                                    dummy_shaker_1.setVisibility(View.GONE);
                                    dummy_shaker_2.setVisibility(View.GONE);
                                    dummy_shaker_fb.setVisibility(View.VISIBLE);
                                    dummy_shaker_3.setVisibility(View.GONE);
                                    dummy_shaker_4.setVisibility(View.GONE);
                                    mp2.start();
                                    shaked++;

                                } else if (shaked == 3) {
                                    afterLoginFB.setVisibility(View.GONE);
                                    dummy_shaker_1.setVisibility(View.GONE);
                                    dummy_shaker_2.setVisibility(View.GONE);
                                    dummy_shaker_3.setVisibility(View.VISIBLE);
                                    dummy_shaker_fb.setVisibility(View.GONE);
                                    dummy_shaker_4.setVisibility(View.GONE);
                                    mp2.start();
                                    shaked++;


                                } else if (shaked == 43) {
                                    afterLoginFB.setVisibility(View.GONE);
                                    dummy_shaker_1.setVisibility(View.GONE);
                                    dummy_shaker_2.setVisibility(View.GONE);
                                    dummy_shaker_3.setVisibility(View.GONE);
                                    dummy_shaker_4.setVisibility(View.VISIBLE);
                                    dummy_shaker_fb.setVisibility(View.GONE);
                                    mp2.start();
                                    shaked = 0;
                                }


                                startShake = true;
                            }
                        }, 3000);

                        //spin_kit.setVisibility(View.GONE);

                    /*GlobalRequest taggedPlacesObj = new GlobalRequest();
                    taggedPlacesObj.setId(inReceiveFriendList.getData().get(shaked).getId());
                    taggedPlacesObj.setToken(activeToken);
                    presenter.retrieveTaggedPlaces(taggedPlacesObj);*/

                        //activeFriendShaked = inReceiveFriendList.getData().get(shaked).getName();
                        //activeFriendShakedId = inReceiveFriendList.getData().get(shaked).getId();


                    }
                }
            });
        }
        //RealmObjectController.clearCachedResult(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.holiday_shaker, container, false);
        ButterKnife.inject(this, view);
        aq.recycle(view);
        final MediaPlayer mp = MediaPlayer.create(getActivity(), R.raw.gets_in_the_way);

        shakePhone = (ImageView) view.findViewById(R.id.shakePhone);
        afterLoginFB = (RelativeLayout) view.findViewById(R.id.afterLoginFB);

        // dummy_shaker_1.setVisibility(View.VISIBLE);

        btnReedem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profilePage = new Intent(getActivity(), TravellerInfoActivity.class);
                getActivity().startActivity(profilePage);
            }
        });

        btnReedem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profilePage = new Intent(getActivity(), TravellerInfoActivity.class);
                getActivity().startActivity(profilePage);
            }
        });

        btnReedem3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profilePage = new Intent(getActivity(), TravellerInfoActivity.class);
                getActivity().startActivity(profilePage);
            }
        });

        btnReedem4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profilePage = new Intent(getActivity(), TravellerInfoActivity.class);
                getActivity().startActivity(profilePage);
            }
        });

        //afterLoginFB.setVisibility(View.GONE);

        return view;
    }

    public void travellerInfo() {
        Intent profilePage = new Intent(getActivity(), TravellerInfoActivity.class);
        getActivity().startActivity(profilePage);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentContainerId = ((FragmentContainerActivity) getActivity()).getFragmentContainerId();
    }

    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);

    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(mShakeDetector);

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
