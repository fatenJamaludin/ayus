package com.app.tbd.ui.Activity.SplashScreen.OnBoarding;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.app.tbd.R;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Model.Request.ProductImage;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class OnBoardingImage extends BaseFragment {

    @InjectView(R.id.onboarding_image)
    ImageView onboarding_image;

    @InjectView(R.id.onboardingImageLoading)
    ProgressBar onboardingImageLoading;


    private static final String KEY_PRODUCTIMAGE = OnBoardingImage.class.getSimpleName() + ":KEY_PRODUCTIMAGE";

    public static OnBoardingImage newInstance(ProductImage productImage) {
        OnBoardingImage fragment = new OnBoardingImage();
        Bundle args = new Bundle();
        args.putParcelable(KEY_PRODUCTIMAGE, productImage);
        fragment.setArguments(args);
        return fragment;
    }

    public static OnBoardingImage newInstance(OnBoarding productImage, List<OnBoarding> listProductImages) {
        OnBoardingImage fragment = new OnBoardingImage();
        ArrayList<String> imgUrls = new ArrayList<String>();
        Bundle args = new Bundle();
        args.putString(KEY_PRODUCTIMAGE, (new Gson()).toJson(productImage));
        //flight.putExtra("FLIGHT_OBJ", (new Gson()).toJson(obj));
        //for (PagerBoardingPassObj pI : listProductImages) {
        //    imgUrls.add(pI.getQRCodeURL());
        // }
        // args.putStringArrayList("imgUrls", imgUrls);
        fragment.setArguments(args);
        return fragment;
    }

    private OnBoarding boardingObj = null;
    private String gsonBoardingPassObj;
    private ArrayList<String> listProductImages;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        gsonBoardingPassObj = args.getString(KEY_PRODUCTIMAGE);

        Gson gson = new Gson();
        boardingObj = gson.fromJson(gsonBoardingPassObj, OnBoarding.class);

        //listProductImages = args.getStringArrayList("imgUrls");

        //if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_PRODUCTIMAGE)) {
        //    boardingObj = savedInstanceState.getString(KEY_PRODUCTIMAGE);
        // }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.on_boarding_image, container, false);
        aq.recycle(rootView);
        ButterKnife.inject(this, rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        aq.recycle(view);

        onboardingImageLoading.setVisibility(View.VISIBLE);

        Glide.with(this)
                .load(boardingObj.getURL())
                .dontAnimate()
                .thumbnail(0.4f)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        onboardingImageLoading.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(onboarding_image);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_PRODUCTIMAGE, (new Gson()).toJson(boardingObj));
    }
}
