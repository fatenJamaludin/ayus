package com.app.tbd.ui.Activity.BigFunTrivia;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.app.tbd.R;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Realm.RealmObjectController;
import com.app.tbd.utils.SharedPrefManager;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class BigFunPendingFragment extends BaseFragment {

    SharedPrefManager pref;

    @InjectView(R.id.bf_loadingImage)
    ImageView bf_loadingImage;
    int a = 0;
    Activity act;

    public static BigFunPendingFragment newInstance() {

        BigFunPendingFragment fragment = new BigFunPendingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RealmObjectController.clearCachedResult(getActivity());
        act = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.bigfun_pending, container, false);
        ButterKnife.inject(this, view);

        bf_loadingImage.setBackgroundResource(R.drawable.ready_timer);
        AnimationDrawable splashAnimation = (AnimationDrawable) bf_loadingImage.getBackground();
        splashAnimation.start();


        int i = 0;
        new CountDownTimer(10000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                Log.e("TICK", "TOCK");
            }

            @Override
            public void onFinish() {
                Intent x = new Intent(act, BigFunQuestionActivity.class);
                act.startActivity(x);
                act.finish();
            }
        }.start();

        return view;
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
