package com.app.tbd.ui.Activity.BookingFlight.TravellerInfo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

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

public class TravellerInfoAddFragment extends BaseFragment{

    // Validator Attributes
    private Validator mValidator;

    @Inject
    LoginPresenter presenter;

    @InjectView(R.id.guest1)
    LinearLayout guest1;

    @InjectView(R.id.guest2)
    LinearLayout guest2;

    @InjectView(R.id.txtCancelGuest1)
    TextView txtCancelGuest1;

    @InjectView(R.id.txtCancelGuest2)
    TextView txtCancelGuest2;

    @InjectView(R.id.txtDoneGuest1)
    TextView txtDoneGuest1;

    @InjectView(R.id.txtDoneGuest2)
    TextView txtDoneGuest2;


    private int fragmentContainerId;

    public static TravellerInfoAddFragment newInstance(Bundle bundle) {

        TravellerInfoAddFragment fragment = new TravellerInfoAddFragment();
        fragment.setArguments(bundle);
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

        View view = inflater.inflate(R.layout.traveller_info_add, container, false);
        ButterKnife.inject(this, view);
        Bundle bundle = getArguments();
        String user = bundle.getString("USER");

        if(user.equals("USER1")){
            guest1.setVisibility(View.VISIBLE);
            guest2.setVisibility(View.GONE);
        }else{
            guest1.setVisibility(View.GONE);
            guest2.setVisibility(View.VISIBLE);
        }

        txtCancelGuest1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        txtCancelGuest2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        txtDoneGuest1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        txtDoneGuest2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });




        maskUserDP();

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
        //imgUser.setImageBitmap(result);
        //imgUser.setScaleType(ImageView.ScaleType.CENTER);
        //imgUserDP.setBackgroundResource(R.drawable.tbd_image_border);

    }

    /*Public-Inner Func*/
    public void baggage()
    {
        Intent profilePage = new Intent(getActivity(), BaggageActivity.class);
        getActivity().startActivity(profilePage);

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
