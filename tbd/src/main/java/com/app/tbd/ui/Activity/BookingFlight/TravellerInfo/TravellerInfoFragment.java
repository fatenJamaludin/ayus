package com.app.tbd.ui.Activity.BookingFlight.TravellerInfo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.app.tbd.R;
import com.app.tbd.ui.Activity.BookingFlight.SSR.BaggageActivity;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Activity.FragmentContainerActivity;
import com.app.tbd.ui.Presenter.LoginPresenter;
import com.app.tbd.ui.Realm.RealmObjectController;
import com.mobsandgeeks.saripaar.Validator;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TravellerInfoFragment extends BaseFragment{

    // Validator Attributes
    private Validator mValidator;

    @Inject
    LoginPresenter presenter;

    @InjectView(R.id.btnContinueToSSR)
    Button btnContinueToSSR;

    @InjectView(R.id.imgUser)
    ImageView imgUser;

    @InjectView(R.id.btnAddUser1)
    ImageView btnAddUser1;

    @InjectView(R.id.btnAddUser2)
    ImageView btnAddUser2;


    private int fragmentContainerId;

    public static TravellerInfoFragment newInstance() {

        TravellerInfoFragment fragment = new TravellerInfoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //MainApplication.get(getActivity()).createScopedGraph(new LoginModule(this)).inject(this);
        RealmObjectController.clearCachedResult(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.traveller_info, container, false);
        ButterKnife.inject(this, view);

        //maskUserDP();

        btnContinueToSSR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baggage();
            }
        });

        btnAddUser1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUserInfo("USER1");
            }
        });

        btnAddUser2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUserInfo("USER2");
            }
        });


        return view;
    }

    public void maskUserDP(){

        //mask dp profile
        Bitmap original = BitmapFactory.decodeResource(getResources(),R.drawable.tbd_dp2);
        Bitmap mask = BitmapFactory.decodeResource(getResources(),R.drawable.dp_mask);
        Bitmap result = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Bitmap.Config.ARGB_8888);

        Bitmap resized = Bitmap.createScaledBitmap(original, mask.getWidth(), mask.getHeight(), true);


        Canvas mCanvas = new Canvas(result);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mCanvas.drawBitmap(resized, 0, 0, null);
        mCanvas.drawBitmap(mask, 0, 0, paint);
        paint.setXfermode(null);
        imgUser.setImageBitmap(result);
        imgUser.setScaleType(ImageView.ScaleType.CENTER);
        //imgUserDP.setBackgroundResource(R.drawable.tbd_image_border);

    }

    /*Public-Inner Func*/
    public void baggage()
    {
        Intent profilePage = new Intent(getActivity(), BaggageActivity.class);
        getActivity().startActivity(profilePage);

    }

    /*Public-Inner Func*/
    public void addUserInfo(String user)
    {
        Intent travellerInfo = new Intent(getActivity(), TravellerInfoAddActivity.class);
        Log.e("user",user);
        travellerInfo.putExtra("USER",user);
        getActivity().startActivity(travellerInfo);

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
