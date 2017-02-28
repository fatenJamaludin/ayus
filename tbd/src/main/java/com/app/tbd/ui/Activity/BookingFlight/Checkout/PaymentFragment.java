package com.app.tbd.ui.Activity.BookingFlight.Checkout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.tbd.MainController;
import com.app.tbd.MainFragmentActivity;
import com.app.tbd.R;
import com.app.tbd.application.MainApplication;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Activity.BookingFlight.SearchFlightFragment;
import com.app.tbd.ui.Activity.FragmentContainerActivity;
import com.app.tbd.ui.Activity.Login.LoginActivity;
import com.app.tbd.ui.Activity.Picker.SelectStateFragment;
import com.app.tbd.ui.Activity.Picker.SelectionListFragment;
import com.app.tbd.ui.Model.JSON.AddOnInfo;
import com.app.tbd.ui.Model.JSON.BillingCountry;
import com.app.tbd.ui.Model.JSON.FlightInProgressJSON;
import com.app.tbd.ui.Model.JSON.SeatCached;
import com.app.tbd.ui.Model.JSON.UserInfoJSON;
import com.app.tbd.ui.Model.Receive.AddPaymentReceive;
import com.app.tbd.ui.Model.Receive.BookingFromStateReceive;
import com.app.tbd.ui.Model.Receive.EditProfileReceive;
import com.app.tbd.ui.Model.Receive.RegisterReceive;
import com.app.tbd.ui.Model.Receive.SearchFlightReceive;
import com.app.tbd.ui.Model.Receive.StateReceive;
import com.app.tbd.ui.Model.Receive.TBD.LoginReceive;
import com.app.tbd.ui.Model.Receive.UploadPhotoReceive;
import com.app.tbd.ui.Model.Request.PaymentRequest;
import com.app.tbd.ui.Model.Request.RegisterRequest;
import com.app.tbd.ui.Model.Request.StateRequest;
import com.app.tbd.ui.Module.LoginModule;
import com.app.tbd.ui.Module.PaymentModule;
import com.app.tbd.ui.Presenter.BookingPresenter;
import com.app.tbd.ui.Presenter.LoginPresenter;
import com.app.tbd.ui.Presenter.ProfilePresenter;
import com.app.tbd.ui.Realm.Cached.CachedResult;
import com.app.tbd.ui.Realm.RealmObjectController;
import com.app.tbd.utils.DropDownItem;
import com.app.tbd.utils.SharedPrefManager;
import com.app.tbd.utils.Utils;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.realm.Realm;
import io.realm.RealmResults;

public class PaymentFragment extends BaseFragment implements Validator.ValidationListener, BookingPresenter.PaymentView {

    // Validator Attributes
    private static Validator mValidator;

    @Inject
    BookingPresenter presenter;

    @NotEmpty(sequence = 1)
    @InjectView(R.id.txtCardSelect)
    TextView txtCardSelect;

    @InjectView(R.id.processingFeeInfoLayout)
    LinearLayout processingFeeInfoLayout;

    @InjectView(R.id.txtProcessingFee)
    TextView txtProcessingFee;

    @NotEmpty(sequence = 1)
    @InjectView(R.id.txtPaymentMonth)
    TextView txtPaymentMonth;

    @NotEmpty(sequence = 1)
    @InjectView(R.id.txtPaymentYear)
    TextView txtPaymentYear;

    @InjectView(R.id.txtBillingCountry)
    TextView txtBillingCountry;

    @InjectView(R.id.txtBillingState)
    TextView txtBillingState;

    @InjectView(R.id.txtBillingAddress1)
    EditText txtBillingAddress1;

    @InjectView(R.id.txtBillingAddress2)
    EditText txtBillingAddress2;

    @InjectView(R.id.txtBillingCity)
    EditText txtBillingCity;

    @InjectView(R.id.txtBillingPostcode)
    EditText txtBillingPostcode;

    @NotEmpty(sequence = 1)
    @InjectView(R.id.txtCardHolderName)
    EditText txtCardHolderName;

    @NotEmpty(sequence = 1)
    @InjectView(R.id.txtCardNumber)
    EditText txtCardNumber;

    @NotEmpty(sequence = 1)
    @InjectView(R.id.txtCardCVV)
    EditText txtCardCVV;

    //@NotEmpty(sequence = 1)
    @InjectView(R.id.txtCardIssuer)
    TextView txtCardIssuer;

    //@NotEmpty(sequence = 1)
    @InjectView(R.id.txtCardIssuerCountry)
    TextView txtCardIssuerCountry;

    @InjectView(R.id.txtTotalDue)
    TextView txtTotalDue;

    //@InjectView(R.id.btnProceedMSOS)
    //TextView btnProceedMSOS;

    private int fragmentContainerId;
    private FirebaseAnalytics mFireBaseAnalytics;

    private ArrayList<DropDownItem> cardList;
    private ArrayList<DropDownItem> yearList;
    private ArrayList<DropDownItem> countryList = new ArrayList<DropDownItem>();
    private static ArrayList<DropDownItem> stateList = new ArrayList<DropDownItem>();
    SharedPrefManager pref;

    private ArrayList<DropDownItem> monthList;
    private String token, signature, username;
    private String point, bundleAmount, stringNewAmount;
    private String CURRENT_PICKER;
    int dp;
    double newAmount;
    int intValue;

    BookingFromStateReceive bookingFromStateReceive;

    public static PaymentFragment newInstance(Bundle bundle) {

        PaymentFragment fragment = new PaymentFragment();
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainApplication.get(getActivity()).createScopedGraph(new PaymentModule(this)).inject(this);
        RealmObjectController.clearCachedResult(getActivity());
        mFireBaseAnalytics = FirebaseAnalytics.getInstance(getActivity());

        // Validator
        mValidator = new Validator(this);
        mValidator.setValidationListener(this);
        mValidator.setValidationMode(Validator.Mode.BURST);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.payment_form, container, false);
        ButterKnife.inject(this, view);

        pref = new SharedPrefManager(getActivity());

        //
        // userSetup();
        dataSetup();

        dp = Integer.parseInt(bookingFromStateReceive.getCurrency().getDisplayDigits());

        txtCardSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupSelectionPaymentRoundFactor(cardList, getActivity(), txtCardSelect, true, view, bookingFromStateReceive.getProcessingFee(), stringNewAmount, bookingFromStateReceive.getInitialSliderLogic().getBookingCurrencyCode(), dp);
            }
        });

        txtPaymentYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupSelection(yearList, getActivity(), txtPaymentYear, true, view, null, null, bookingFromStateReceive.getInitialSliderLogic().getBookingCurrencyCode());
            }
        });

        txtPaymentMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                popupSelection(monthList, getActivity(), txtPaymentMonth, true, view, null, null, bookingFromStateReceive.getInitialSliderLogic().getBookingCurrencyCode());
            }
        });

        txtBillingCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CURRENT_PICKER = "PAYMENTCOUNTRY";
                showCountrySelector(getActivity(), getCountryList(), "PAYMENTCOUNTRY");
            }
        });

        txtBillingState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CURRENT_PICKER = "PAYMENTSTATE";
                showCountrySelector(getActivity(), stateList, "PAYMENTSTATE");

            }
        });

        return view;
    }

    public void retrieveState(String countryCode) {

        txtBillingState.setHint(getResources().getString(R.string.register_general_loading));

        HashMap<String, String> initAppData = pref.getLanguageCountry();
        String langCountryCode = initAppData.get(SharedPrefManager.LANGUAGE_COUNTRY);

        StateRequest stateRequest = new StateRequest();
        stateRequest.setLanguageCode(langCountryCode);
        stateRequest.setCountryCode(countryCode);

        presenter.onStateRequest(stateRequest);

    }

    /*Country selector - > need to move to main activity*/
    public void showCountrySelector(Activity act, ArrayList constParam, String data) {
        if (act != null) {
            try {
                android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
                SelectionListFragment routeListDialogFragment = SelectionListFragment.newInstance(data, "na", false);
                routeListDialogFragment.setTargetFragment(PaymentFragment.this, 0);
                routeListDialogFragment.show(fm, "countryListDialogFragment");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void userSetup() {
        Realm realm = RealmObjectController.getRealmInstance(getActivity());
        final RealmResults<UserInfoJSON> result2 = realm.where(UserInfoJSON.class).findAll();
        final LoginReceive obj = (new Gson()).fromJson(result2.get(0).getUserInfo(), LoginReceive.class);

        if (obj != null) {

            String stateName = getStateName(getActivity(), obj.getProvinceStateCode());
            String countryName = getCountryName(getActivity(), obj.getCountryCode());

            txtBillingAddress1.setText(obj.getAddressLine1());
            txtBillingAddress2.setText(obj.getAddressLine2());
            txtBillingCity.setText(obj.getCity());
            txtBillingPostcode.setText(obj.getPostalCode());
            txtBillingState.setText(stateName);
            txtBillingCountry.setText(countryName);
        } else {
            txtBillingAddress1.setText("");
            txtBillingAddress2.setText("");
            txtBillingCity.setText("");
            txtBillingPostcode.setText("");
            txtBillingState.setText("");
            txtBillingCountry.setText("");
        }

    }

    public void dataSetup() {

        Bundle bundle = getArguments();

        point = bundle.getString("POINT");
        bundleAmount = bundle.getString("AMOUNT");
        stringNewAmount = bundleAmount;

        //filter (replace)

        Log.e("PaymentFragment Amount", stringNewAmount);
        //String[] split = amount.split(" ");
        //amount = split[1];

        cardList = new ArrayList<>();

        Realm realm = RealmObjectController.getRealmInstance(getActivity());
        final RealmResults<SeatCached> addon = realm.where(SeatCached.class).findAll();
        bookingFromStateReceive = (new Gson()).fromJson(addon.get(0).getSeatCached(), BookingFromStateReceive.class);

        Double amountDouble = Double.parseDouble(stringNewAmount);
        dp = Integer.parseInt(bookingFromStateReceive.getCurrency().getDisplayDigits());

        newAmount = round(amountDouble, dp);
        stringNewAmount = bundleAmount;

        intValue = (int) newAmount;

        //if dp=0
        if (dp == 0) {
            /*String thousand = changeThousand(String.valueOf(intValue));*/
            txtTotalDue.setText(intValue + " " + bookingFromStateReceive.getInitialSliderLogic().getBookingCurrencyCode());

        } else {
            /*String twoPoints = String.format("%.2f", newAmount);*/ //500.00
            /*String newTwoPoints = doubleZero(twoPoints);*/
            /*String thousand = changeThousand(twoPoints);*/
            String twoPoints = doubleZero(String.valueOf(newAmount));
            txtTotalDue.setText(twoPoints + " " + bookingFromStateReceive.getInitialSliderLogic().getBookingCurrencyCode());
        }

        final RealmResults<UserInfoJSON> result2 = realm.where(UserInfoJSON.class).findAll();
        LoginReceive loginReceive = (new Gson()).fromJson(result2.get(0).getUserInfo(), LoginReceive.class);

        token = loginReceive.getToken();
        signature = loginReceive.getSignature();
        username = loginReceive.getUserName();

        setCardSelection(bookingFromStateReceive);
        monthList = getListOfMonth(getActivity());
        yearList = getListOfYear(getActivity());
        countryList = getCountry(getActivity());
        /*stateList = getState(getActivity());*/

        //billingCountry = getBookingCountryList();
        txtProcessingFee.setText(bookingFromStateReceive.getProcessingFee() + " " + bookingFromStateReceive.getInitialSliderLogic().getBookingCurrencyCode());
    }

    public ArrayList<DropDownItem> getCountryList() {
        return countryList;
    }

    public static ArrayList<DropDownItem> getStateList() {
        return stateList;
    }

    public void setCardSelection(BookingFromStateReceive bookingFromStateReceive) {

        for (int i = 0; i < bookingFromStateReceive.getCardType().size(); i++) {

            DropDownItem itemFlight = new DropDownItem();
            itemFlight.setText(bookingFromStateReceive.getCardType().get(i).getDescription());
            itemFlight.setCode(bookingFromStateReceive.getCardType().get(i).getCode() + "-" + bookingFromStateReceive.getCardType().get(i).getProcessingFee());
            itemFlight.setTag("CARD_TYPE");
            cardList.add(itemFlight);

        }

    }

    public static void onCCPayment(Activity act) {
        mValidator.validate();
    }

    @Override
    public void onValidationSucceeded() {

        hideKeyboard();
        initiateLoading(getActivity());

        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setUserName(username);
        paymentRequest.setToken(token);
        paymentRequest.setSignature(signature);

        paymentRequest.setCardHolderName(txtCardHolderName.getText().toString());
        paymentRequest.setCardNumber(txtCardNumber.getText().toString());
        paymentRequest.setCardType(txtCardSelect.getTag().toString());

        //split
        paymentRequest.setExpirationDate(txtPaymentYear.getText().toString() + "-" + txtPaymentMonth.getText().toString() + "-" + "01");
        paymentRequest.setCVV(txtCardCVV.getText().toString());
        //paymentRequest.setCVV(txtCardIssuer.getTag().toString());
        paymentRequest.setCardIssuer("TEST");
        paymentRequest.setCardIssuerCountry("MY");
        //txtCardIssuerCountry

        paymentRequest.setBillingAddress1(txtBillingAddress1.getText().toString());
        paymentRequest.setBillingAddress2(txtBillingAddress2.getText().toString());
        paymentRequest.setBillingCity(txtBillingCity.getText().toString());
        paymentRequest.setBillingCountry("MY");
        paymentRequest.setBillingStateOrProvince("SEL");
        paymentRequest.setBillingPostalCode(txtBillingPostcode.getText().toString());

        paymentRequest.setBigPoint(point);


        if (dp == 0) {
            paymentRequest.setPaymentAmount(String.valueOf(intValue));
            Log.e("Send Integer Amount", String.valueOf(intValue));
        } else {

            /*String twoPoints = String.format("%.2f", newAmount);*/ //500.00
            String twoPoints = doubleZero(String.valueOf(newAmount));

            paymentRequest.setPaymentAmount(String.valueOf(twoPoints));
            Log.e("Send String Amount", String.valueOf(newAmount));
        }

        presenter.onAddPayment(paymentRequest);

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {

        for (ValidationError error : errors) {

            View view = error.getView();

            setShake(view);
            String message = error.getCollatedErrorMessage(getActivity());
            String splitErrorMsg[] = message.split("\\r?\\n");

            // Display error messages
            if (view instanceof EditText) {
                ((EditText) view).setError(splitErrorMsg[0]);
            } else if (view instanceof TextView) {
                ((TextView) view).setError(splitErrorMsg[0]);
            }
        }
        croutonAlert(getActivity(), getResources().getString(R.string.fill_emtpy_field));

    }

    @Override
    public void onAddPaymentReceive(AddPaymentReceive obj) {

        dismissLoading();
        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {

            fireBase();
            if (obj.getPaymentURL().equals("")) {

                Intent intent = new Intent(getActivity(), PaymentPendingActivity.class);
                intent.putExtra("RECORD_LOCATOR", obj.getRecordLocator());
                getActivity().startActivity(intent);
            } else {

                Intent intent = new Intent(getActivity(), PaymentWebviewActivity.class);
                intent.putExtra("RECORD_LOCATOR", obj.getRecordLocator());
                intent.putExtra("PAYMENT_URL", obj.getPaymentURL());
                getActivity().startActivity(intent);
                //Intent intent = new Intent(getActivity(), PaymentPendingActivity.class);
                //intent.putExtra("RECORD_LOCATOR", obj.getRecordLocator());
                //getActivity().startActivity(intent);
            }

        }

    }

    public void fireBase() {
        Bundle fireBaseBundle = new Bundle();

        fireBaseBundle.putString("CardHolderName", txtCardHolderName.getText().toString());
        fireBaseBundle.putString("CardNumber", txtCardNumber.getText().toString());
        fireBaseBundle.putString("CardType", txtCardSelect.getTag().toString());
        fireBaseBundle.putString("ExpirationDate", txtPaymentYear.getText().toString() + "-" + txtPaymentMonth.getText().toString() + "-" + "01");
        fireBaseBundle.putString("CVV", txtCardCVV.getText().toString());
        fireBaseBundle.putString("CardIssuerCountry", "MY");
        fireBaseBundle.putString("BillingAddress1", txtBillingAddress1.getText().toString());
        fireBaseBundle.putString("BillingAddress2", txtBillingAddress2.getText().toString());
        fireBaseBundle.putString("BillingCity", txtBillingCity.getText().toString());
        fireBaseBundle.putString("BillingCountry", "MY");
        fireBaseBundle.putString("BillingPostalCode", txtBillingPostcode.getText().toString());

        mFireBaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_PAYMENT_INFO, fireBaseBundle);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentContainerId = ((FragmentContainerActivity) getActivity()).getFragmentContainerId();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();

        RealmResults<CachedResult> result = RealmObjectController.getCachedResult(MainFragmentActivity.getContext());
        if (result.size() > 0) {
            Gson gson = new Gson();
            AddPaymentReceive obj = gson.fromJson(result.get(0).getCachedResult(), AddPaymentReceive.class);
            onAddPaymentReceive(obj);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (CURRENT_PICKER.equals("PAYMENTCOUNTRY")) {
            DropDownItem selectedCountry = data.getParcelableExtra(CURRENT_PICKER);
            txtBillingCountry.setText(selectedCountry.getText());

            String splitCountryCode = splitCountryDialingCode("CountryCode", selectedCountry.getCode());
            txtBillingCountry.setTag(splitCountryCode);

            txtBillingState.setClickable(false);

            retrieveState(splitCountryCode);

        } else if (CURRENT_PICKER.equals("PAYMENTSTATE")) {
            DropDownItem selectedState = data.getParcelableExtra(CURRENT_PICKER);
            txtBillingState.setText(selectedState.getText());
            txtBillingState.setTag(selectedState.getCode());

        }
    }

    @Override
    public void onSuccessRequestState(StateReceive obj) {

        //txtBillingState.setHint(getResources().getString(R.string.register_newsletter_state_hint));
        stateList = new ArrayList<DropDownItem>();

        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {

            RealmObjectController.clearCachedResult(getActivity());

            txtBillingState.setClickable(true);
            String selectHint = getString(R.string.select_state);
            txtBillingState.setHint(selectHint);

            /*Travel Doc*/
            for (int i = 0; i < obj.getStateList().size(); i++) {

                DropDownItem itemDoc = new DropDownItem();
                itemDoc.setText(obj.getStateList().get(i).getProvinceStateName());
                itemDoc.setCode(obj.getStateList().get(i).getProvinceStateCode());
                stateList.add(itemDoc);
            }
        }
    }
}
