package com.app.tbd.ui.Activity.BookingFlight.SSR;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.tbd.R;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Activity.BookingFlight.Checkout.CheckoutActivity;
import com.app.tbd.ui.Activity.FragmentContainerActivity;
import com.app.tbd.ui.Presenter.LoginPresenter;
import com.app.tbd.ui.Realm.RealmObjectController;

import com.mobsandgeeks.saripaar.Validator;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class BaggageFragment extends BaseFragment {

    // Validator Attributes
    private Validator mValidator;

    @Inject
    LoginPresenter presenter;

    @InjectView(R.id.btnSSRNext)
    ImageView btnSSRNext;

    @InjectView(R.id.btnSSRPrevious)
    ImageView btnSSRPrevious;

    @InjectView(R.id.mealSSR_layout)
    LinearLayout mealSSR_layout;

    @InjectView(R.id.baggageSSR_layout)
    LinearLayout baggageSSR_layout;

    @InjectView(R.id.imgUserSSR2)
    ImageView imgUserSSR2;

    @InjectView(R.id.txtSSRType)
    TextView txtSSRType;

    @InjectView(R.id.deductBaggage)
    ImageView deductBaggage;

    @InjectView(R.id.addBaggage)
    ImageView addBaggage;

    @InjectView(R.id.txtBaggageWeight)
    TextView txtBaggageWeight;

    @InjectView(R.id.imgUserSSR1)
    ImageView imgUserSSR1;

    @InjectView(R.id.add_insurance_2)
    Button add_insurance_2;

    @InjectView(R.id.add_insurance_1)
    Button add_insurance_1;

    @InjectView(R.id.mealBaggageLayout)
    LinearLayout mealBaggageLayout;

    @InjectView(R.id.insuranceSpecialRequest)
    LinearLayout insuranceLayout;

    @InjectView(R.id.previous_insurance_btn)
    ImageView previous_insurance_btn;

    @InjectView(R.id.insuranceNextBtn)
    ImageView insuranceNextBtn;

    @InjectView(R.id.insuranceLayout)
    LinearLayout insuranceLayout2;

    @InjectView(R.id.specialAssistance)
    LinearLayout specialAssistance;

    @InjectView(R.id.txtInsuranceSpecial)
    TextView txtInsuranceSpecial;

    @InjectView(R.id.seatSelectionLayout)
    LinearLayout seatSelectionLayout;

    @InjectView(R.id.seatPreviousBtn)
    ImageView seatPreviousBtn;

    @InjectView(R.id.insuranceSpecialRequest)
    LinearLayout insuranceSpecialRequest;

    @InjectView(R.id.seatNextBtn)
    ImageView seatNextBtn;

    @InjectView(R.id.seatList)
    LinearLayout seatList;

    private int fragmentContainerId;
    private AlertDialog dialog;
    private int baggageWeight = 10;
    private int currentPage = 1;

    private int insuranceBtn1 = 0;
    private int insuranceBtn2 = 0;

    String kg;

    public static BaggageFragment newInstance() {

        BaggageFragment fragment = new BaggageFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.ssr_info_baggage, container, false);
        ButterKnife.inject(this, view);

        kg = getString(R.string.addons_kg);

        btnSSRPrevious.setVisibility(View.INVISIBLE);
        btnSSRNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPage == 2) {
                    insurance();
                } else if (currentPage == 1) {
                    meal();
                }
            }
        });

        txtBaggageWeight.setText("+" + baggageWeight + " " + kg);

        // maskUserDP2();
        // maskUserDP1();

        btnSSRPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baggage();
            }
        });

        imgUserSSR1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSSRDetail("Imal Pasha");
            }
        });

        imgUserSSR2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSSRDetail("Username");
            }
        });

        deductBaggage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (baggageWeight != 10) {
                    baggageWeight = baggageWeight - 10;
                    txtBaggageWeight.setText("+" + baggageWeight + " " + kg);
                }
            }
        });

        addBaggage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                baggageWeight = baggageWeight + 10;
                txtBaggageWeight.setText("+" + baggageWeight + " " + kg);

            }
        });


        add_insurance_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (insuranceBtn2 == 0) {
                    add_insurance_2.setText(getResources().getString(R.string.insurance_added));
                    add_insurance_2.setBackground(getResources().getDrawable(R.drawable.insurance_btn_clicked));
                    insuranceBtn2 = 1;
                } else {
                    add_insurance_2.setText(getResources().getString(R.string.insurance_add));
                    add_insurance_2.setBackground(getResources().getDrawable(R.drawable.insurance_btn));
                    insuranceBtn2 = 0;

                }
            }
        });

        add_insurance_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (insuranceBtn1 == 0) {
                    add_insurance_1.setText(getResources().getString(R.string.insurance_added));
                    add_insurance_1.setBackground(getResources().getDrawable(R.drawable.insurance_btn_clicked));
                    insuranceBtn1 = 1;
                } else {
                    add_insurance_1.setText(getResources().getString(R.string.insurance_add));
                    add_insurance_1.setBackground(getResources().getDrawable(R.drawable.insurance_btn));
                    insuranceBtn1 = 0;
                }
            }
        });

        previous_insurance_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentPage == 3) {
                    mealBaggageLayout.setVisibility(View.VISIBLE);
                    insuranceLayout.setVisibility(View.GONE);
                    currentPage = 2;
                } else if (currentPage == 4) {
                    insurance();
                    specialAssistance.setVisibility(View.GONE);
                }

            }
        });

        insuranceNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentPage == 3) {
                    specialAssistance.setVisibility(View.VISIBLE);
                    insuranceLayout2.setVisibility(View.GONE);
                    txtInsuranceSpecial.setText((getResources().getString(R.string.addons_special_assistance)));
                    currentPage = 4;
                } else if (currentPage == 4) {
                    insuranceSpecialRequest.setVisibility(View.GONE);
                    seatSelectionLayout.setVisibility(View.VISIBLE);
                    currentPage = 5;
                }
            }
        });


        seatPreviousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentPage == 5) {
                    insuranceSpecialRequest.setVisibility(View.VISIBLE);
                    seatSelectionLayout.setVisibility(View.GONE);
                    txtInsuranceSpecial.setText((getResources().getString(R.string.addons_special_assistance)));
                    currentPage = 4;
                }
            }
        });


        seatNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent profilePage = new Intent(getActivity(), CheckoutActivity.class);
                getActivity().startActivity(profilePage);
            }
        });

        setSeat1(seatList);
        return view;
    }

    public void maskUserDP1() {

        //mask dp profile
        Bitmap original = BitmapFactory.decodeResource(getResources(), R.drawable.tbd_dp2);
        Bitmap mask = BitmapFactory.decodeResource(getResources(), R.drawable.dp_mask);
        Bitmap result = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Bitmap.Config.ARGB_8888);

        Bitmap resized = Bitmap.createScaledBitmap(original, mask.getWidth(), mask.getHeight(), true);


        Canvas mCanvas = new Canvas(result);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mCanvas.drawBitmap(resized, 0, 0, null);
        mCanvas.drawBitmap(mask, 0, 0, paint);
        paint.setXfermode(null);
        imgUserSSR1.setImageBitmap(result);
        imgUserSSR1.setScaleType(ImageView.ScaleType.CENTER);
        //imgUserDP.setBackgroundResource(R.drawable.tbd_image_border);

    }

    public void maskUserDP2() {

        //mask dp profile
        Bitmap original = BitmapFactory.decodeResource(getResources(), R.drawable.tbd_icon);
        Bitmap mask = BitmapFactory.decodeResource(getResources(), R.drawable.dp_mask);
        Bitmap result = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Bitmap.Config.ARGB_8888);
        Bitmap resized = Bitmap.createScaledBitmap(original, mask.getWidth(), mask.getHeight(), true);


        Canvas mCanvas = new Canvas(result);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mCanvas.drawBitmap(resized, 0, 0, null);
        mCanvas.drawBitmap(mask, 0, 0, paint);
        paint.setXfermode(null);
        imgUserSSR2.setImageBitmap(result);
        imgUserSSR2.setScaleType(ImageView.ScaleType.CENTER);
        //imgUserDP.setBackgroundResource(R.drawable.tbd_image_border);

    }

    /*Public-Inner Func*/
    public void meal() {
        txtSSRType.setText((getResources().getString(R.string.inflight_meal)));
        mealSSR_layout.setVisibility(View.VISIBLE);
        baggageSSR_layout.setVisibility(View.GONE);
        btnSSRPrevious.setVisibility(View.VISIBLE);
        currentPage = 2;
    }

    /*Public-Inner Func*/
    public void baggage() {
        txtSSRType.setText((getResources().getString(R.string.addons_baggage)));
        mealSSR_layout.setVisibility(View.GONE);
        baggageSSR_layout.setVisibility(View.VISIBLE);
        btnSSRPrevious.setVisibility(View.INVISIBLE);
        currentPage = 1;

    }

    public void
    insurance() {

        txtInsuranceSpecial.setText((getResources().getString(R.string.addons_travel_insurance)));
        mealBaggageLayout.setVisibility(View.GONE);
        insuranceLayout.setVisibility(View.VISIBLE);
        insuranceLayout2.setVisibility(View.VISIBLE);
        currentPage = 3;
    }

    public void setSeat1(LinearLayout seatList) {

        //Seat Colum
        int colum = 9;
        int row = 15;
        String allAvailable = "Y";


        for (int label = 0; label < row; label++) {


            LinearLayout seatRow = new LinearLayout(getActivity());
            seatRow.setOrientation(LinearLayout.HORIZONTAL);
            seatRow.setGravity(LinearLayout.TEXT_ALIGNMENT_GRAVITY);


            for (int x = 0; x < colum + 1; x++) {
                String a = Integer.toString(x);
                if (label == 0) {
                    a = "a" + Integer.toString(x);
                } else {
                    if (x == 0) {
                        a = Integer.toString(label);
                    }
                }

                String seatNumber = a;
                String seatType = "standard";
                String seatStatus = "Y";
                String compartment = "d";

                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT, 1f);


                final TextView txtDetailList = new TextView(getActivity());
                txtDetailList.setText(seatNumber);
                txtDetailList.setGravity(Gravity.CENTER);
                txtDetailList.setPadding(5, 20, 5, 20);
                txtDetailList.setTag(seatNumber);

                if (label == 0) {
                    if (x == 0) {
                        txtDetailList.setTextColor(getResources().getColor(R.color.white));
                    } else {
                        txtDetailList.setBackgroundColor(getResources().getColor(R.color.white));
                        txtDetailList.setTextColor(getResources().getColor(R.color.grey));
                    }

                } else {
                    if (x == 0) {
                        txtDetailList.setBackgroundColor(getResources().getColor(R.color.white));
                        txtDetailList.setTextColor(getResources().getColor(R.color.grey));
                    } else {
                        txtDetailList.setBackgroundColor(getResources().getColor(R.color.grey));
                        txtDetailList.setTextColor(getResources().getColor(R.color.white));
                    }
                }

                txtDetailList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //seat select user by image
                        openUserSeatImage();
                    }

                });

                seatRow.setBackgroundColor(getResources().getColor(R.color.white));

                //Set color and clickable
                /*if (seatType.equals("standard")) {
                    txtDetailList.setClickable(true);
                    seatRow.setBackgroundColor(getResources().getColor(R.color.seat_standard));

                } else if (seatType.equals("preferred")) {
                    txtDetailList.setClickable(true);
                    seatRow.setBackgroundColor(getResources().getColor(R.color.seat_preferred));

                } else if (seatType.equals("desired")) {
                    txtDetailList.setClickable(true);
                    seatRow.setBackgroundColor(getResources().getColor(R.color.seat_desired));
                }

                if (seatStatus.equals(allAvailable)) {
                    txtDetailList.setBackgroundColor(getResources().getColor(R.color.grey));
                }*/

                if (x == 2) {
                    param.setMargins(5, 8, 5, 8);
                } else if (x == 0) {
                    param.setMargins(5, 8, 5, 8);
                } else if (x == 3) {
                    param.setMargins(5, 8, 20, 8);
                } else if (x == 1) {
                    param.setMargins(20, 8, 5, 8);
                } else if (x == 4) {
                    param.setMargins(20, 8, 5, 8);
                } else if (x == 5) {
                    param.setMargins(5, 8, 5, 8);
                } else if (x == 6) {
                    param.setMargins(5, 8, 20, 8);
                } else if (x == 7) {
                    param.setMargins(20, 8, 5, 8);
                } else if (x == 8) {
                    param.setMargins(5, 8, 5, 8);
                } else if (x == 9) {
                    param.setMargins(5, 8, 20, 8);
                }

                txtDetailList.setLayoutParams(param);


                seatRow.addView(txtDetailList);
            }

            seatList.addView(seatRow);

        }
    }

    public void openUserSeatImage(){

        LayoutInflater li = LayoutInflater.from(getActivity());
        final View myView = li.inflate(R.layout.user_list_sear, null);


        final TextView txtCloseSSRDetail = (TextView) myView.findViewById(R.id.txtCloseSSRDetail);
        txtCloseSSRDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }

        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(myView);

        dialog = builder.create();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //lp.height = 570;
        dialog.getWindow().setAttributes(lp);
        dialog.show();

    }

    public void openSSRDetail(String username) {

        LayoutInflater li = LayoutInflater.from(getActivity());
        final View myView = li.inflate(R.layout.user_ssr_detail, null);


        final TextView txtCloseSSRDetail = (TextView) myView.findViewById(R.id.txtCloseSSRDetail);
        txtCloseSSRDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }

        });

        /*final Button cont = (Button) myView.findViewById(R.id.btnContinue);
        final Button close = (Button) myView.findViewById(R.id.btnClose);
        final Button login = (Button) myView.findViewById(R.id.btnLogin);
        final Button btnConfirmAsGuest = (Button) myView.findViewById(R.id.btnConfirmAsGuest);
        final LinearLayout txtConfirmContinue = (LinearLayout) myView.findViewById(R.id.txtConfirmContinue);
        final LinearLayout linearBtnCancel = (LinearLayout) myView.findViewById(R.id.linearBtnCancel);
        final LinearLayout linearClose = (LinearLayout) myView.findViewById(R.id.linearClose);
        final LinearLayout linearConfirmAsGuest = (LinearLayout) myView.findViewById(R.id.linearConfirmAsGuest);
        final LinearLayout linearContinueAsGuest = (LinearLayout) myView.findViewById(R.id.linearContinueAsGuest);
        final Button btnCancel = (Button) myView.findViewById(R.id.btnCancel);
        final EditText userId = (EditText) myView.findViewById(R.id.txtUserId);
        final EditText password = (EditText) myView.findViewById(R.id.txtPassword);
        password.setTransformationMethod(new PasswordTransformationMethod());


        final EditText editEmail = (EditText) myView.findViewById(R.id.editTextemail_login);
        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        txtCloseSSRDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!userId.getText().toString().equals("") && !password.getText().toString().equals("")) {
                    txtConfirmContinue.setVisibility(View.VISIBLE);
                    linearConfirmAsGuest.setVisibility(View.VISIBLE);
                    linearBtnCancel.setVisibility(View.VISIBLE);
                    linearClose.setVisibility(View.GONE);
                    linearContinueAsGuest.setVisibility(View.GONE);
                } else {
                    dialog.dismiss();
                }
            }

        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtConfirmContinue.setVisibility(View.GONE);
                cont.setText("Continue as guest");
                linearConfirmAsGuest.setVisibility(View.GONE);
                linearBtnCancel.setVisibility(View.GONE);
                linearClose.setVisibility(View.VISIBLE);
                linearContinueAsGuest.setVisibility(View.VISIBLE);
            }

        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }

        });
        */

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(myView);

        dialog = builder.create();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(lp);
        dialog.show();

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
