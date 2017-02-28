package com.app.tbd.ui.Activity.BookingFlight.Checkout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import com.app.tbd.R;
import com.app.tbd.application.AnalyticsApplication;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Activity.FragmentContainerActivity;
import com.app.tbd.ui.Model.JSON.SeatCached;
import com.app.tbd.ui.Model.JSON.UserInfoJSON;
import com.app.tbd.ui.Model.Receive.BookingFromStateReceive;
import com.app.tbd.ui.Model.Receive.TBD.BigPointReceive;
import com.app.tbd.ui.Model.Receive.TBD.LoginReceive;
import com.app.tbd.ui.Realm.RealmObjectController;
import com.google.gson.Gson;
import com.mobsandgeeks.saripaar.Validator;
import java.util.Locale;
import butterknife.ButterKnife;
import butterknife.InjectView;
import io.realm.Realm;
import io.realm.RealmResults;
import static java.lang.Math.ceil;

public class BigPointSliderFragment extends BaseFragment {

    // Validator Attributes
    private Validator mValidator;

    @InjectView(R.id.txtAvailableBigPoint)
    TextView txtAvailableBigPoint;

    @InjectView(R.id.txtDuePoint)
    TextView txtDuePoint;

    @InjectView(R.id.bigPointSeekBar)
    SeekBar bigPointSeekBar;

    @InjectView(R.id.txtPaymentDue)
    TextView txtPaymentDue;

    @InjectView(R.id.txtBigPointMin)
    TextView txtBigPointMin;

    @InjectView(R.id.txtTotalQuotedPointToUse)
    TextView txtTotalQuotedPointToUse;

    @InjectView(R.id.txtPOINT)
    EditText txtPOINT;

    @InjectView(R.id.txtIndicator)
    TextView txtIndicator;

    static String usePoint;
    static String useAmount;
    static String newUseAmount;
   /* @Inject
    LoginPresenter presenter;



    @InjectView(R.id.txtBigPoint)
    TextView txtBigPoint;

    @InjectView(R.id.txtDiscount)
    TextView txtDiscount;

    @InjectView(R.id.customSeekBar)
    SeekBar customSeekBar;*/

    private int fragmentContainerId;
    BookingFromStateReceive bookingFromStateReceive;

    double PointConversionRate;
    double MaxMargin;
    double BookingMaxNoOfBucket;
    double MarginPerBucket;
    double PaymentNoOfBucket;
    double PaymentMinCashPayment;
    double BookingTotalAmount;
    double BookingQuotedAmount;

    int MinimumBigPointForSlider;
    int PaymentMinBigPoint;
    int PaymentMaxBigPoint;
    int BookingQuotedPoints;
    int maximum;

    String dp;
    String BookingCurrencyCode;
    String points;
    String original;
    static String alertMessage;
    Boolean disableTextWatcher = false;
    Boolean disableSliderCalculation = false;

    public static BigPointSliderFragment newInstance(Bundle bundle) {

        BigPointSliderFragment fragment = new BigPointSliderFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        /*PaymentMinBigPoint = min pts;
        PaymentMaxBigPoint = max pts;*/

        View view = inflater.inflate(R.layout.payment_due, container, false);
        ButterKnife.inject(this, view);
        dataSetup();

        maximum = PaymentMaxBigPoint;

        bigPointSeekBar.setMax(maximum - MinimumBigPointForSlider);
        bigPointSeekBar.setProgress(maximum);


        bigPointSeekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                disableSliderCalculation = false;
                txtPOINT.removeTextChangedListener(yourTextWatcher);
                return false;
            }
        });

        bigPointSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int progressChanged = MinimumBigPointForSlider;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //progressChanged = progress;
                Log.e("Auto", "Y");

                if (!disableSliderCalculation) {

                    int biggies = progressChanged + progress;

                    //$BIG_MINIMUM_REDEMPTION_POINTS = $InitialSliderLogic['PaymentMinBigPoint'];
                    double BIG_MINIMUM_REDEMPTION_POINTS = PaymentMinBigPoint;

                    double bucketNo = ceil(biggies / BIG_MINIMUM_REDEMPTION_POINTS) - 1; // Need to -1 to achieve correct calculation

                    double varMargin = MaxMargin - (MarginPerBucket * bucketNo);

                    // To handle BQP <= 500 pts
                    if (BookingMaxNoOfBucket == 0) {
                        varMargin = 0;
                    }

                    double cashEquiv = biggies * (1 - varMargin) * PointConversionRate;
                    String textPoint = Integer.toString(biggies);

                    if (cashEquiv > BookingTotalAmount - PaymentMinCashPayment)
                        cashEquiv = BookingTotalAmount - PaymentMinCashPayment;

                    if (biggies == PaymentMaxBigPoint) {

                        if (BookingQuotedAmount < PaymentMinCashPayment) {
                            txtPOINT.setText(textPoint);

                            double newValue = round(PaymentMinCashPayment, Integer.parseInt(dp));
                            int intValue = (int) newValue;

                            //if dp=0
                            if (dp.equals("0")) {

                                txtPaymentDue.setText(Integer.toString(intValue) + " " + BookingCurrencyCode);
                                useAmount = Integer.toString(intValue);

                            } else {
                                String twoPoints = String.format(Locale.US,"%.2f", newValue);
                                txtPaymentDue.setText(twoPoints + " " + BookingCurrencyCode);
                                /*useAmount = Double.toString(newValue);*/
                                useAmount = doubleZero(String.valueOf(newValue));
                            }

                            usePoint = Integer.toString(biggies);

                        } else {
                            double newValue = round(BookingQuotedAmount, Integer.parseInt(dp));
                            int intValue = (int) newValue;

                            if (dp.equals("0")) {
                                txtPaymentDue.setText(Integer.toString(intValue) + " " + BookingCurrencyCode);
                                useAmount = Integer.toString(intValue);

                            } else {
                                String twoPoints = String.format(Locale.US,"%.2f", newValue);
                                txtPaymentDue.setText(twoPoints + " " + BookingCurrencyCode);
                                /*useAmount = Double.toString(newValue);*/
                                useAmount = doubleZero(String.valueOf(newValue));
                            }

                            txtPOINT.setText(textPoint);
                            usePoint = Integer.toString(biggies);
                        }

                    } else {
                        double newValue = round(BookingTotalAmount - cashEquiv, Integer.parseInt(dp));
                        int intValue = (int) newValue;

                        if (dp.equals("0")) {
                            txtPaymentDue.setText(Integer.toString(intValue) + " " + BookingCurrencyCode);
                            useAmount = Integer.toString(intValue);

                        } else {
                            String twoPoints = String.format(Locale.US,"%.2f", newValue);
                            txtPaymentDue.setText(twoPoints + " " + BookingCurrencyCode);
                            /*useAmount = Double.toString(newValue);*/
                            useAmount = doubleZero(String.valueOf(newValue));
                        }

                        txtPOINT.setText(textPoint);
                        usePoint = Integer.toString(biggies);
                    }
                }

            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        return view;
    }

    private void dataSetup() {

        Realm realm = RealmObjectController.getRealmInstance(getActivity());
        final RealmResults<UserInfoJSON> result2 = realm.where(UserInfoJSON.class).findAll();
        LoginReceive loginReceive = (new Gson()).fromJson(result2.get(0).getUserInfo(), LoginReceive.class);

        BigPointReceive obj = RealmObjectController.getCachedBigPointResult(getActivity());

        try {
            convertPointDecimal(txtAvailableBigPoint, loginReceive.getBigPoint());
        } catch (Exception e) {
            String fail_message = getString(R.string.fail_message);
            txtAvailableBigPoint.setText(fail_message);
        }

        Bundle bundle = getArguments();
        // String obj2 = bundle.getString("TAX_FEE");

        final RealmResults<SeatCached> addon = realm.where(SeatCached.class).findAll();
        BookingFromStateReceive bookingFromStateReceive = (new Gson()).fromJson(addon.get(0).getSeatCached(), BookingFromStateReceive.class);
        // bookingFromStateReceive = (new Gson()).fromJson(obj2, BookingFromStateReceive.class);
        //bookingFromStateReceive = (new Gson()).fromJson(obj, BookingFromStateReceive.class);

        PointConversionRate = Double.parseDouble(bookingFromStateReceive.getInitialSliderLogic().getPointConversionRate());
        MaxMargin = Double.parseDouble(bookingFromStateReceive.getInitialSliderLogic().getMaxMargin());
        BookingMaxNoOfBucket = Integer.parseInt(bookingFromStateReceive.getInitialSliderLogic().getBookingMaxNoOfBucket());//int
        MarginPerBucket = Double.parseDouble(bookingFromStateReceive.getInitialSliderLogic().getMarginPerBucket());
        PaymentNoOfBucket = Double.parseDouble(bookingFromStateReceive.getInitialSliderLogic().getPaymentNoOfBucket());
        PaymentMinBigPoint = Integer.parseInt(bookingFromStateReceive.getInitialSliderLogic().getPaymentMinBigPoint());
        PaymentMinCashPayment = Double.parseDouble(bookingFromStateReceive.getInitialSliderLogic().getPaymentMinCashPayment());
        dp = bookingFromStateReceive.getCurrency().getDisplayDigits();
        BookingTotalAmount = Double.parseDouble(bookingFromStateReceive.getInitialSliderLogic().getBookingTotalAmount());
        PaymentMaxBigPoint = Integer.parseInt(bookingFromStateReceive.getInitialSliderLogic().getPaymentMaxBigPoint());
        BookingQuotedAmount = Double.parseDouble(bookingFromStateReceive.getInitialSliderLogic().getBookingQuotedAmount());
        BookingQuotedPoints = Integer.parseInt(bookingFromStateReceive.getInitialSliderLogic().getBookingQuotedPoints()); // int
        BookingCurrencyCode = bookingFromStateReceive.getInitialSliderLogic().getBookingCurrencyCode();
        MinimumBigPointForSlider = Integer.parseInt(bookingFromStateReceive.getInitialSliderLogic().getMinimumBigPointSlider());

        convertPointDecimal(txtTotalQuotedPointToUse, Integer.toString(BookingQuotedPoints));
        convertPointDecimal(txtDuePoint, Integer.toString(BookingQuotedPoints));
        convertPointDecimal(txtBigPointMin, Integer.toString(MinimumBigPointForSlider));
        txtPOINT.setText(Integer.toString(PaymentMaxBigPoint));

        double newValue = round(PaymentMinCashPayment, Integer.parseInt(dp));
        int intValue = (int) newValue;

        if (dp.equals("0")) {
            txtPaymentDue.setText(Integer.toString(intValue) + " " + BookingCurrencyCode);
            useAmount = Integer.toString(intValue);

        } else {
            String twoPoints = String.format(Locale.US,"%.2f", newValue);
            txtPaymentDue.setText(twoPoints + " " + BookingCurrencyCode);
            /*useAmount = Double.toString(newValue);*/
            useAmount = doubleZero(String.valueOf(newValue));

        }

        //set editable false if zero fare
        if (PaymentMinBigPoint == 0) {
            txtPOINT.setFocusable(false);
            txtPOINT.setClickable(false);
        }

        usePoint = Integer.toString(PaymentMaxBigPoint);

        double x = PaymentMaxBigPoint;
        int intPaymentMaxBigPoint = (int) x;

        points = getString(R.string.pts);
        txtTotalQuotedPointToUse.setText(changeThousand(Integer.toString(intPaymentMaxBigPoint)) + " " + points);
        //txtPaymentDue.setText(Double.toString(PaymentMinCashPayment));

        txtPOINT.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                txtPOINT.addTextChangedListener(yourTextWatcher);
                original = txtPOINT.getText().toString();
                disableSliderCalculation = true;

                Log.e("X", "Y");
                return false;
            }
        });

        //editable point
     /*   if(!disableTextWatcher){
            txtPOINT.addTextChangedListener(new TextWatcher() {

                @Override
                public void afterTextChanged(Editable arg0) {
                    //progressChanged = progress;

                    int biggies = Integer.parseInt(txtPOINT.getText().toString());

                    //$BIG_MINIMUM_REDEMPTION_POINTS = $InitialSliderLogic['PaymentMinBigPoint'];
                    double BIG_MINIMUM_REDEMPTION_POINTS = PaymentMinBigPoint;

                    double bucketNo = ceil(biggies / BIG_MINIMUM_REDEMPTION_POINTS) - 1; // Need to -1 to achieve correct calculation

                    double varMargin = MaxMargin - (MarginPerBucket * bucketNo);

                    // To handle BQP <= 500 pts
                    if (BookingMaxNoOfBucket == 0) {
                        varMargin = 0;
                    }

                    double cashEquiv = biggies * (1 - varMargin) * PointConversionRate;
                    String textPoint = changeThousand(Integer.toString(biggies));

                    if (cashEquiv > BookingTotalAmount - PaymentMinCashPayment)
                        cashEquiv = BookingTotalAmount - PaymentMinCashPayment;

                    if (biggies == PaymentMaxBigPoint) {

                        if (BookingQuotedAmount < PaymentMinCashPayment) {
                            txtPOINT.setText(textPoint);
                            txtPaymentDue.setText(Double.toString(rounded(PaymentMinCashPayment)) + " " + BookingCurrencyCode);
                            useAmount = Double.toString(rounded(PaymentMinCashPayment));
                            usePoint = Integer.toString(biggies);

                        } else {
                            txtPOINT.setText(textPoint);
                            txtPaymentDue.setText(Double.toString(rounded(BookingQuotedAmount)) + " " + BookingCurrencyCode);
                            useAmount = Double.toString(rounded(BookingQuotedAmount));
                            usePoint = Integer.toString(biggies);
                        }

                    } else {
                        txtPOINT.setText(textPoint);
                        txtPaymentDue.setText(Double.toString(rounded(BookingTotalAmount - cashEquiv)) + " " + BookingCurrencyCode);
                        useAmount = Double.toString(rounded(BookingTotalAmount - cashEquiv));
                        usePoint = Integer.toString(biggies);
                    }
                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                }

            });
      }
      */

    }

    public TextWatcher yourTextWatcher = new TextWatcher() {

        @Override
        public void afterTextChanged(Editable arg0) {

            int toChange = 0;
            if (arg0.toString().equals("")) {
                toChange = 0;
            } else {
                toChange = Integer.parseInt(arg0.toString());
            }

            if (toChange <= (maximum) && toChange >= MinimumBigPointForSlider) {
                //calculate ..
                //progressChanged = progress;
                final int biggies;
                if (txtPOINT.getText().toString().equals("")) {
                    biggies = 0;
                } else {
                    biggies = Integer.parseInt(txtPOINT.getText().toString());
                }

                //$BIG_MINIMUM_REDEMPTION_POINTS = $InitialSliderLogic['PaymentMinBigPoint'];
                double BIG_MINIMUM_REDEMPTION_POINTS = PaymentMinBigPoint;

                double bucketNo = ceil(biggies / BIG_MINIMUM_REDEMPTION_POINTS) - 1; // Need to -1 to achieve correct calculation

                double varMargin = MaxMargin - (MarginPerBucket * bucketNo);

                // To handle BQP <= 500 pts
                if (BookingMaxNoOfBucket == 0) {
                    varMargin = 0;
                }

                double cashEquiv = biggies * (1 - varMargin) * PointConversionRate;
                String textPoint = Integer.toString(biggies);

                if (cashEquiv > BookingTotalAmount - PaymentMinCashPayment)
                    cashEquiv = BookingTotalAmount - PaymentMinCashPayment;

                if (biggies == PaymentMaxBigPoint) {

                    if (BookingQuotedAmount < PaymentMinCashPayment) {
                        //txtPOINT.setText(textPoint);
                        double newValue = round(PaymentMinCashPayment, Integer.parseInt(dp));
                        int intValue = (int) newValue;

                        if (dp.equals("0")) {
                            txtPaymentDue.setText(Integer.toString(intValue) + " " + BookingCurrencyCode);
                            useAmount = Integer.toString(intValue);

                        } else {
                            String twoPoints = String.format(Locale.US,"%.2f", newValue);
                            txtPaymentDue.setText(twoPoints + " " + BookingCurrencyCode);
                            /*useAmount = Double.toString(newValue);*/
                            useAmount = doubleZero(String.valueOf(newValue));

                        }

                        usePoint = Integer.toString(biggies);

                    } else {
                        //txtPOINT.setText(textPoint);
                        double newValue = round(BookingQuotedAmount, Integer.parseInt(dp));
                        int intValue = (int) newValue;

                        if (dp.equals("0")) {
                            txtPaymentDue.setText(Integer.toString(intValue) + " " + BookingCurrencyCode);
                            useAmount = Integer.toString(intValue);

                        } else {
                            String twoPoints = String.format(Locale.US,"%.2f", newValue);
                            txtPaymentDue.setText(twoPoints + " " + BookingCurrencyCode);
                            /*useAmount = Double.toString(newValue);*/
                            useAmount = doubleZero(String.valueOf(newValue));

                        }

                        usePoint = Integer.toString(biggies);
                    }

                } else {
                    //txtPOINT.setText(textPoint);

                    double newValue = round(BookingTotalAmount - cashEquiv, Integer.parseInt(dp));
                    int intValue = (int) newValue;

                    if (dp.equals("0")) {
                        txtPaymentDue.setText(Integer.toString(intValue) + " " + BookingCurrencyCode);
                        useAmount = Integer.toString(intValue);

                    } else {
                        String twoPoints = String.format(Locale.US,"%.2f", newValue);
                        txtPaymentDue.setText(twoPoints + " " + BookingCurrencyCode);
                        /*useAmount = Double.toString(newValue);*/
                        useAmount = doubleZero(String.valueOf(newValue));

                    }

                    usePoint = Integer.toString(biggies);
                }

                //bigPointSeekBar.setProgress(biggies);
                // ...
                bigPointSeekBar.post(new Runnable() {
                    @Override
                    public void run() {
                        if (bigPointSeekBar != null) {
                            //bigPointSeekBar.setMax(maximum - MinimumBigPointForSlider);
                            if (biggies == MinimumBigPointForSlider) {
                                bigPointSeekBar.setProgress(0);
                            } else {
                                bigPointSeekBar.setProgress(biggies - MinimumBigPointForSlider);
                            }
                        }
                    }
                });
                txtIndicator.setText("");
                alertMessage = "";
            } else if (toChange < MinimumBigPointForSlider) {
                String message = getString(R.string.text_message2);

                txtIndicator.setText(message);

                alertMessage = getString(R.string.message2);

                bigPointSeekBar.post(new Runnable() {
                    @Override
                    public void run() {
                        if (bigPointSeekBar != null) {
                            //bigPointSeekBar.setMax(maximum - MinimumBigPointForSlider);
                            bigPointSeekBar.setProgress(0);
                        }
                    }
                });

            } else if (toChange > (maximum)) {
                String text = getString(R.string.message3);
                txtIndicator.setText(text);
                alertMessage = getString(R.string.message3);
            } else {

            }
//
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

        }
    };

    public static void onProceedPayment(Activity act) {

        if(alertMessage != null){
            String alert = AnalyticsApplication.getContext().getString(R.string.addons_alert);
            setAlertDialog(act, alertMessage, alert);
        }else{
            Log.e("useAmount", useAmount);
            Intent intent = new Intent(act, PaymentActivity.class);
            intent.putExtra("POINT", usePoint);
            intent.putExtra("AMOUNT", useAmount);
            act.startActivity(intent);
        }

    }

    public void convertPointDecimal(TextView txt, String pts) {

        double newValue = Double.parseDouble(pts);
        int x2 = (int) newValue;

        String str = String.format(Locale.US,"%,d", x2);
        String points = getString(R.string.pts);
        txt.setText(str + " " + points);

    }


    public void convertPointDecimalV2(TextView txt, String pts) {

        double newValue = Double.parseDouble(pts);
        int x2 = (int) newValue;

        String str = String.format(Locale.US,"%,d", x2);
        txt.setText(str);

    }

    public String convertPointDecimalV2(String pts) {

        String str;

        double newValue = Double.parseDouble(pts);
        int x2 = (int) newValue;
        str = String.format(Locale.US,"%,d", x2);

        return str;
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
