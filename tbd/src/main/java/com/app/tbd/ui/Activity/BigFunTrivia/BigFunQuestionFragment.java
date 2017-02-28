package com.app.tbd.ui.Activity.BigFunTrivia;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import com.app.tbd.R;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Realm.RealmObjectController;
import com.app.tbd.utils.SharedPrefManager;
import com.github.lzyzsd.circleprogress.DonutProgress;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class BigFunQuestionFragment extends BaseFragment {

    SharedPrefManager pref;

    @InjectView(R.id.donut_progress)
    DonutProgress donut_progress;

    public static BigFunQuestionFragment newInstance() {

        BigFunQuestionFragment fragment = new BigFunQuestionFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RealmObjectController.clearCachedResult(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.bigfun_question, container, false);
        ButterKnife.inject(this, view);
        pref = new SharedPrefManager(getActivity());

        donut_progress.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.default_theme_colour));

        ObjectAnimator anim = ObjectAnimator.ofInt(donut_progress, "progress", 0, 3000);
        anim.setInterpolator(new AccelerateInterpolator());
        anim.setDuration(3000);
        anim.start();

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
