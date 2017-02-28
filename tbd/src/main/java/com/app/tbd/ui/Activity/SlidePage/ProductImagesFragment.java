package com.app.tbd.ui.Activity.SlidePage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.tbd.R;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Model.Request.PagerBoardingPassObj;
import com.app.tbd.ui.Model.Request.ProductImage;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class ProductImagesFragment extends BaseFragment {


    private static final String KEY_PRODUCTIMAGE = ProductImagesFragment.class.getSimpleName() + ":KEY_PRODUCTIMAGE";

    public static ProductImagesFragment newInstance(ProductImage productImage) {
        ProductImagesFragment fragment = new ProductImagesFragment();
        Bundle args = new Bundle();
        args.putParcelable(KEY_PRODUCTIMAGE, productImage);
        fragment.setArguments(args);
        return fragment;
    }

    public static ProductImagesFragment newInstance(PagerBoardingPassObj productImage, List<PagerBoardingPassObj> listProductImages) {
        ProductImagesFragment fragment = new ProductImagesFragment();
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

    private PagerBoardingPassObj boardingObj = null;
    private String gsonBoardingPassObj;
    private ArrayList<String>listProductImages;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        gsonBoardingPassObj = args.getString(KEY_PRODUCTIMAGE);

        Gson gson = new Gson();
        boardingObj = gson.fromJson(gsonBoardingPassObj, PagerBoardingPassObj.class);

        //listProductImages = args.getStringArrayList("imgUrls");

        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_PRODUCTIMAGE)) {
            boardingObj = savedInstanceState.getParcelable(KEY_PRODUCTIMAGE);
        }


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

        //test.setText(boardingObj.getArrivalStation());

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_PRODUCTIMAGE, (new Gson()).toJson(boardingObj));
    }
}
