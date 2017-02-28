package com.app.tbd.ui.Activity.BigFunTrivia;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.app.tbd.R;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Realm.RealmObjectController;
import com.app.tbd.utils.SharedPrefManager;
import com.github.lzyzsd.circleprogress.DonutProgress;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TestingPageFragment extends BaseFragment {

    CountDownTimer countDownTimer;
    private Timer timer;

    @InjectView(R.id.donut_progress)
    DonutProgress donut_progress;

    Activity act;

    public static TestingPageFragment newInstance() {

        TestingPageFragment fragment = new TestingPageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        act = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.bigfun_testing_page, container, false);
        ButterKnife.inject(this, view);

        /*ObjectAnimator anim = ObjectAnimator.ofInt(donut_progress, "progress", 0, 100);
        anim.setInterpolator(new DecelerateInterpolator());
        anim.setDuration(10000);
        anim.start();*/

        donut_progress.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.default_theme_colour));

        ObjectAnimator anim = ObjectAnimator.ofInt(donut_progress, "progress", 0, 3000);
        anim.setInterpolator(new DecelerateInterpolator());
        anim.setDuration(3000);
        anim.start();

        /*ProgressBar v = (ProgressBar) findViewById(R.id.progress);
        donut_progress.getIndeterminateDrawable().setColorFilter(0xFFcc0000,
                android.graphics.PorterDuff.Mode.MULTIPLY);
*/
        /*mDilog.setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                ProgressBar v = (ProgressBar)mDilog.findViewById(android.R.id.progress);
                v.getIndeterminateDrawable().setColorFilter(0xFFcc0000,
                        android.graphics.PorterDuff.Mode.MULTIPLY);

            }
        });*/


       /* timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                act.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        boolean a = true;
                        if (a) {
                            ObjectAnimator anim = ObjectAnimator.ofInt(donut_progress, "progress", 0, 1000);
                            anim.setInterpolator(new DecelerateInterpolator());
                            anim.setDuration(1000);
                            anim.start();
                        } else {
                            AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.progress_anim);
                            set.setInterpolator(new DecelerateInterpolator());
                            set.setTarget(donut_progress);
                            set.start();
                        }
                    }
                });
            }
        }, 0, 1000);*/

        //startTimer(1);
        return view;
    }

    /*private void startTimer(final int minuti) {
        countDownTimer = new CountDownTimer(10000, 500) {
            // 500 means, onTick function will be called at every 500 milliseconds

            @Override
            public void onTick(long leftTimeInMilliseconds) {
                long seconds = leftTimeInMilliseconds / 1000;
                barTimer.setProgress((int)seconds);
                //textTimer.setText(String.format("%02d", seconds/60) + ":" + String.format("%02d", seconds%60));
                // format the textview to show the easily readable format

            }
            @Override
            public void onFinish() {
                if(textTimer.getText().equals("00:00")){
                    textTimer.setText("STOP");
                }
                else{
                    textTimer.setText("2:00");
                    barTimer.setProgress(60*minuti);
                }
    barTimer.setProgress(60*minuti);

}
}.start();

        }*/

@Override
public void onResume(){
        super.onResume();
        }

@Override
public void onPause(){
        super.onPause();
        }

        }
