package com.app.tbd.ui.Activity.DragDrop;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.tbd.R;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Activity.FragmentContainerActivity;
import com.app.tbd.ui.Presenter.HomePresenter;
import com.app.tbd.utils.SharedPrefManager;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

//import com.estimote.sdk.BeaconManager;

public class DragDropFragment extends BaseFragment {


    @Inject
    HomePresenter presenter;

    @InjectView(R.id.myimage1)
    ImageView myimage1;


    @InjectView(R.id.bottomright)
    LinearLayout bottomright;

    private String facebookUrl, twitterUrl, instagramUrl;
    private int fragmentContainerId;
    private static final String SCREEN_LABEL = "Home";

    private SharedPrefManager pref;
    View view;

    public static DragDropFragment newInstance() {

        DragDropFragment fragment = new DragDropFragment();
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

        View view = inflater.inflate(R.layout.demo, container, false);
        ButterKnife.inject(this, view);
        pref = new SharedPrefManager(getActivity());
        aq.recycle(view);

        // Assign the touch listener to your view which you want to move
        //imgToBeMove.setOnTouchListener(new MyTouchListener());
        myimage1.setOnLongClickListener(new TouchClass());
        bottomright.setOnDragListener(new DropClass(getActivity()));

        return view;
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

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
