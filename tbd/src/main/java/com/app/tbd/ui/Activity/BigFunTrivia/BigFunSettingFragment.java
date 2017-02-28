package com.app.tbd.ui.Activity.BigFunTrivia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import com.app.tbd.R;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Realm.RealmObjectController;
import com.app.tbd.utils.SharedPrefManager;
import java.util.HashMap;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class BigFunSettingFragment extends BaseFragment {

    SharedPrefManager pref;

    @InjectView(R.id.musicSwitch)
    Switch musicSwitch;

    public static BigFunSettingFragment newInstance() {

        BigFunSettingFragment fragment = new BigFunSettingFragment();
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

        View view = inflater.inflate(R.layout.bigfun_setting, container, false);
        ButterKnife.inject(this, view);
        pref = new SharedPrefManager(getActivity());

        HashMap<String, String> initPassword = pref.getMusic();
        String music = initPassword.get(SharedPrefManager.MUSIC);

        if (music != null) {
            if (music.equals("Y")) {
                musicSwitch.setChecked(true);
            } else {
                musicSwitch.setChecked(false);
            }
        }

        musicSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    comic_backgroundMusic(getActivity());
                    pref.setMusic("Y");
                } else {
                    comic_stopBackgroundMusic();
                    pref.setMusic("N");

                }
            }
        });

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
