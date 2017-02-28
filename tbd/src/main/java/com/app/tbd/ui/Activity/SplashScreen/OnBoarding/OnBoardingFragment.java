package com.app.tbd.ui.Activity.SplashScreen.OnBoarding;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.app.tbd.R;
import com.app.tbd.application.AnalyticsApplication;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Activity.FragmentContainerActivity;
import com.app.tbd.ui.Activity.SplashScreen.AfterBoardingActivity;
import com.app.tbd.ui.Model.Receive.OnBoardingReceive;
import com.app.tbd.ui.Model.Receive.TBD.LoginReceive;
import com.app.tbd.ui.Presenter.LanguagePresenter;
import com.app.tbd.utils.SharedPrefManager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.relex.circleindicator.CircleIndicator;

public class OnBoardingFragment extends BaseFragment {

    @InjectView(R.id.pager)
    ViewPager pager;

    @InjectView(R.id.indicator)
    CircleIndicator indicator;

    @InjectView(R.id.onboardingCtn)
    Button onboardingCtn;

    @InjectView(R.id.onboardingSkip)
    Button onboardingSkip;

    @InjectView(R.id.onNext)
    ImageView onNext;

    @InjectView(R.id.onPrev)
    ImageView onPrev;


    private int fragmentContainerId;
    private SharedPrefManager pref;

    public static OnBoardingFragment newInstance() {

        OnBoardingFragment fragment = new OnBoardingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.on_boarding, container, false);
        ButterKnife.inject(this, view);
        pref = new SharedPrefManager(getActivity());
        pref.setFirstTimeUser("N");
        /*AnalyticsApplication.sendScreenView("OnBoarding loaded");*/

        HashMap<String, String> init = pref.getBoardingList();
        String onBoard = init.get(SharedPrefManager.ON_BOARDING_IMAGE);
        OnBoardingReceive onBoardingReceive = (new Gson()).fromJson(onBoard, OnBoardingReceive.class);

        onboardingCtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent afterBoarding = new Intent(getActivity(), AfterBoardingActivity.class);
                getActivity().startActivity(afterBoarding);
                getActivity().finish();
            }
        });

        onboardingSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent afterBoarding = new Intent(getActivity(), AfterBoardingActivity.class);
                getActivity().startActivity(afterBoarding);
                getActivity().finish();
            }
        });

        //retrieve onboardingImage.
        //presenter.onRequestOnBoardingImage();
        startPagination(onBoardingReceive);
        return view;
    }

    public void startPagination(final OnBoardingReceive onBoardingReceive) {

        String[] myImageList = new String[]{};

        ArrayList<OnBoarding> listProductImages = new ArrayList<OnBoarding>();
        for (int i = 0; i < onBoardingReceive.getImageList().size(); i++) {
            OnBoarding onboard = new OnBoarding();
            onboard.setImagePosition(Integer.toString(i));
            onboard.setURL(onBoardingReceive.getImageList().get(i));
            listProductImages.add(onboard);
        }

        OnBoardingAdapter mAdapter = new OnBoardingAdapter(getFragmentManager());
        mAdapter.addAll(listProductImages);
        pager.setAdapter(mAdapter);

        indicator.setViewPager(pager);

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

            public void onPageSelected(final int position) {


                if (position == onBoardingReceive.getImageList().size() - 1) {  //5,4,3,2,1
                    onboardingCtn.setVisibility(View.VISIBLE);
                    onboardingSkip.setVisibility(View.GONE);
                    //onNext.setVisibility(View.GONE);
                    //onPrev.setVisibility(View.VISIBLE);
                } else {
                    onboardingCtn.setVisibility(View.GONE);
                    onboardingSkip.setVisibility(View.VISIBLE);
                    //onPrev.setVisibility(View.VISIBLE);
                    //onNext.setVisibility(View.VISIBLE);
                }


                onPrev.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int newPosition = position - 1;
                        pager.setCurrentItem(newPosition, true);
                    }
                });

                onNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int newPosition = position + 1;
                        pager.setCurrentItem(newPosition, true);
                    }
                });

            }

        });

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentContainerId = ((FragmentContainerActivity) getActivity()).getFragmentContainerId();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
