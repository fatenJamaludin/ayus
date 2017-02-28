package com.app.tbd.ui.Activity.BigFunTrivia;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import com.app.tbd.R;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Realm.RealmObjectController;
import com.app.tbd.utils.SharedPrefManager;
import butterknife.ButterKnife;

public class BigFunTriviaFragment extends BaseFragment {

    private ImageView mScanner;
    private Animation mAnimation;

    /*@InjectView(R.id.viewflipper)
    ViewFlipper viewflipper;

    @InjectView(R.id.planeFlipper)
    ViewFlipper planeFlipper;

    @InjectView(R.id.smallPlaneFlipper)
    ViewFlipper smallPlaneFlipper;

    @InjectView(R.id.sunFlipper)
    ViewFlipper sunFlipper;

    @InjectView(R.id.bigfun_setting_btn)
    ImageButton bigfun_setting_btn;

    @InjectView(R.id.bigfun_quest_btn)
    ImageButton bigfun_quest_btn;

    @InjectView(R.id.bigfun_star_btn)
    ImageButton bigfun_star_btn;

    @InjectView(R.id.bigFunBtnPlay)
    Button bigFunBtnPlay;*/

    SharedPrefManager pref;

    public static BigFunTriviaFragment newInstance() {

        BigFunTriviaFragment fragment = new BigFunTriviaFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RealmObjectController.clearCachedResult(getActivity());
        Log.e("OnCreate", "Y");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab4, container, false);
        ButterKnife.inject(this, view);
        pref = new SharedPrefManager(getActivity());

        /*HashMap<String, String> initPassword = pref.getMusic();
        String music = initPassword.get(SharedPrefManager.MUSIC);

        *//*mAnimation = new TranslateAnimation(0, 400, 0, 0);
        mAnimation.setDuration(10000);
        mAnimation.setFillAfter(true);
        mAnimation.setRepeatCount(-1);
        mAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
        //movingImage.setAnimation(mAnimation);
        //movingImage.setVisibility(View.VISIBLE);*//*

        Animation animIn = AnimationUtils.loadAnimation(getActivity(), R.anim.game_banner_slide_out_reverse);
        animIn.setDuration(10000);
        Animation animOut = AnimationUtils.loadAnimation(getActivity(), R.anim.game_banner_slide_in_reverse);
        animOut.setDuration(10000);

        viewflipper.setFlipInterval(9800);
        viewflipper.startFlipping();
        viewflipper.setInAnimation(animIn);
        viewflipper.setOutAnimation(animOut);

        //Big Plane Flipper

        Animation planeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.game_banner_slide_out);
        planeIn.setDuration(7500);
        Animation planeOut = AnimationUtils.loadAnimation(getActivity(), R.anim.game_banner_slide_in);
        planeOut.setDuration(7500);

        planeFlipper.setFlipInterval(7300);
        planeFlipper.startFlipping();
        planeFlipper.setInAnimation(planeIn);
        planeFlipper.setOutAnimation(planeOut);


        Animation smallplaneIn = AnimationUtils.loadAnimation(getActivity(), R.anim.game_banner_slide_out);
        smallplaneIn.setDuration(6500);
        Animation smallplaneOut = AnimationUtils.loadAnimation(getActivity(), R.anim.game_banner_slide_in);
        smallplaneOut.setDuration(6500);

        smallPlaneFlipper.setFlipInterval(6300);
        smallPlaneFlipper.startFlipping();
        smallPlaneFlipper.setInAnimation(smallplaneIn);
        smallPlaneFlipper.setOutAnimation(smallplaneOut);

        Animation sunIn = AnimationUtils.loadAnimation(getActivity(), R.anim.game_banner_slide_out_reverse);
        sunIn.setDuration(20000);
        Animation sunOut = AnimationUtils.loadAnimation(getActivity(), R.anim.game_banner_slide_in_reverse);
        sunOut.setDuration(20000);

        sunFlipper.setFlipInterval(19800);
        sunFlipper.startFlipping();
        sunFlipper.setInAnimation(sunIn);
        sunFlipper.setOutAnimation(sunOut);


        *//*Animation.AnimationListener mAnimationListener = new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
                //animation started event
            }

            public void onAnimationRepeat(Animation animation) {

            }

            public void onAnimationEnd(Animation animation) {
                //TODO animation stopped event
            }
        };*//*

        bigfun_setting_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comic_btnClicked(getActivity());
                Intent flightDetail = new Intent(getActivity(), BigFunSettingActivity.class);
                getActivity().startActivity(flightDetail);
            }
        });

        bigfun_star_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comic_btnClicked(getActivity());
            }
        });

        bigfun_quest_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comic_btnClicked(getActivity());
                Intent flightDetail = new Intent(getActivity(), TestingPageActivity.class);
                getActivity().startActivity(flightDetail);
            }
        });

        bigFunBtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comic_btnClicked(getActivity());
                Intent flightDetail = new Intent(getActivity(), BigFunReadyActivity.class);
                getActivity().startActivity(flightDetail);
            }
        });*/



        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.e("OnResume", "Y");
    }

    @Override
    public void onPause() {
        super.onPause();
    }

}
