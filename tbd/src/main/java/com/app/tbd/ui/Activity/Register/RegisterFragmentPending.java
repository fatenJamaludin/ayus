package com.app.tbd.ui.Activity.Register;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.tbd.MainController;
import com.app.tbd.application.MainApplication;
import com.app.tbd.MainFragmentActivity;
import com.app.tbd.R;
import com.app.tbd.ui.Activity.Login.LoginActivity;
import com.app.tbd.ui.Activity.Picker.BasicPicker;
import com.app.tbd.ui.Activity.Picker.SelectionListFragment;
import com.app.tbd.ui.Model.JSON.UserFacebookInfo;
import com.app.tbd.ui.Model.Receive.NewsletterLanguageReceive;
import com.app.tbd.ui.Model.Receive.RegisterReceive;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Activity.FragmentContainerActivity;
import com.app.tbd.ui.Activity.Picker.DatePickerFragment;
import com.app.tbd.ui.Model.Receive.StateReceive;
import com.app.tbd.ui.Model.Request.NewsletterLanguageRequest;
import com.app.tbd.ui.Model.Request.RegisterRequest;
import com.app.tbd.ui.Model.Request.StateRequest;
import com.app.tbd.ui.Module.RegisterModule;
import com.app.tbd.ui.Realm.Cached.CachedResult;
import com.app.tbd.ui.Presenter.RegisterPresenter;
import com.app.tbd.utils.DropDownItem;
import com.app.tbd.ui.Realm.RealmObjectController;
import com.app.tbd.utils.SharedPrefManager;
import com.app.tbd.utils.Utils;
import com.google.gson.Gson;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.realm.RealmResults;

public class RegisterFragmentPending extends BaseFragment implements RegisterPresenter.RegisterView, DatePickerDialog.OnDateSetListener, Validator.ValidationListener {


    // Validator Attributes
    @Inject
    RegisterPresenter presenter;

    @NotEmpty(sequence = 1)
    @Email(sequence = 2)
    @InjectView(R.id.txtRegisterEmail)
    EditText txtRegisterEmail;

    @NotEmpty(sequence = 1)
    @Password(sequence = 2, scheme = Password.Scheme.ALPHA_NUMERIC_MIXED_CASE, messageResId = R.string.register_password_invalid)
    @Length(sequence = 3, min = 8, max = 15, messageResId = R.string.register_password_length)
    @InjectView(R.id.txtRegisterPassword)
    EditText txtRegisterPassword;

    @NotEmpty(sequence = 1)
    @ConfirmPassword(sequence = 2)
    @InjectView(R.id.txtRegisterConfirmPassword)
    EditText txtRegisterConfirmPassword;

    @NotEmpty(sequence = 1)
    @InjectView(R.id.txtRegisterGivenName)
    EditText txtRegisterGivenName;

    @NotEmpty(sequence = 1)
    @InjectView(R.id.txtRegisterFamilyName)
    EditText txtRegisterFamilyName;

    @NotEmpty(sequence = 1)
    @InjectView(R.id.txtRegisterNickName)
    EditText txtRegisterNickName;

    @NotEmpty(sequence = 1)
    @InjectView(R.id.txtRegisterDOB)
    TextView txtRegisterDOB;

    @NotEmpty(sequence = 1)
    @InjectView(R.id.txtRegisterNationality)
    TextView txtRegisterNationality;

    @NotEmpty(sequence = 1)
    @InjectView(R.id.txtRegisterMobile)
    EditText txtRegisterMobile;

    @NotEmpty(sequence = 1)
    @InjectView(R.id.txtRegisterCountry)
    TextView txtRegisterCountry;

    @NotEmpty(sequence = 1)
    @InjectView(R.id.txtRegisterState)
    TextView txtRegisterState;

    @NotEmpty(sequence = 1)
    @InjectView(R.id.txtRegisterPostCode)
    EditText txtRegisterPostCode;

    @NotEmpty(sequence = 1)
    @InjectView(R.id.txtRegisterTown)
    TextView txtRegisterTown;

    @InjectView(R.id.btnRegisterContinue)
    Button btnRegisterContinue;

    @InjectView(R.id.registerMrBtn)
    Button registerMrBtn;

    @InjectView(R.id.registerMsBtn)
    Button registerMsBtn;

    @InjectView(R.id.registerParentTitle)
    Button registerParentTitle;

    @InjectView(R.id.registerGuardianTitle)
    Button registerGuardianTitle;

    @InjectView(R.id.registerSubscribeNewsletter)
    TextView registerSubscribeNewsletter;

    @InjectView(R.id.registerConfirmInformation)
    TextView registerConfirmInformation;

    @InjectView(R.id.registerAcknowledgeMemberShip)
    TextView registerAcknowledgeMemberShip;

    @InjectView(R.id.registerSubscribeNewsletterBtn)
    ImageView registerSubscribeNewsletterBtn;

    @InjectView(R.id.registerConfirmInformationBtn)
    ImageView registerConfirmInformationBtn;

    @InjectView(R.id.registerAcknowledgeMemberShipBtn)
    ImageView registerAcknowledgeMemberShipBtn;

    @InjectView(R.id.registerConfirmParent)
    ImageView registerConfirmParent;

    @InjectView(R.id.newsletterPreferredLanguage)
    LinearLayout newsletterPreferredLanguage;

    @InjectView(R.id.txtRegisterNewsletterLanguage)
    TextView txtRegisterNewsletterLanguage;

    @InjectView(R.id.registerParentConfirmInformation)
    TextView registerParentConfirmInformation;

    @InjectView(R.id.userParentInformationBlock)
    LinearLayout userParentInformationBlock;

    @InjectView(R.id.txtRegisterParentFullName)
    EditText txtRegisterParentFullName;

    @InjectView(R.id.txtRegisterParentID)
    EditText txtRegisterParentID;

    @InjectView(R.id.txtRegisterParentEmail)
    EditText txtRegisterParentEmail;


    ProgressDialog progress;
    private Validator mValidator;
    private int fragmentContainerId;
    private static final String SCREEN_LABEL = "Register";
    public static final String DATEPICKER_TAG = "DATEPICKER_TAG";
    private DatePickerDialog register_dob_datepicker;
    private String CURRENT_PICKER;
    private SharedPrefManager pref;

    final Calendar calendar = Calendar.getInstance();

    private String subscribeNewsletter = "0";
    private boolean acknowledgeInfo = false;
    private boolean confirmInfo = false;
    private boolean confirmParent = false;
    private String title = "Mr";
    private String gender = "1";
    private String dateOfBirth;
    private int travellerAge;
    private Boolean parentInfo;
    private String parentTitle;
    private Boolean proceed = false;

    /*DropDown Variable*/
    private ArrayList<DropDownItem> titleList = new ArrayList<DropDownItem>();
    private ArrayList<DropDownItem> countryList = new ArrayList<DropDownItem>();
    private static ArrayList<DropDownItem> state = new ArrayList<DropDownItem>();
    private ArrayList<DropDownItem> language = new ArrayList<DropDownItem>();

    public static RegisterFragmentPending newInstance(Bundle bundle) {
        RegisterFragmentPending fragment = new RegisterFragmentPending();
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainApplication.get(getActivity()).createScopedGraph(new RegisterModule(this)).inject(this);
        RealmObjectController.clearCachedResult(getActivity());

        // Validator
        mValidator = new Validator(this);
        mValidator.setValidationListener(this);
        mValidator.setValidationMode(Validator.Mode.BURST);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.register, container, false);
        ButterKnife.inject(this, view);
        Bundle bundle = getArguments();

        datePickerSetting();
        dataSetup(bundle);

        registerMrBtn.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.default_theme_colour));
        registerMsBtn.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.grey));

        registerParentTitle.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.default_theme_colour));
        registerGuardianTitle.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.grey));

        //title
        registerParentTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerParentTitle.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.default_theme_colour));
                registerGuardianTitle.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.grey));
                parentTitle = "Parent";
            }
        });

        registerGuardianTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerGuardianTitle.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.default_theme_colour));
                registerParentTitle.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.grey));
                parentTitle = "Guardian";
            }
        });

        //title
        registerMrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerMrBtn.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.default_theme_colour));
                registerMsBtn.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.grey));
                title = "Mr";
                gender = "1";
            }
        });

        registerMsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerMrBtn.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.grey));
                registerMsBtn.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.default_theme_colour));
                title = "Ms";
                gender = "0";

            }
        });

        //date of birth
        txtRegisterDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkFragmentAdded()) {
                    register_dob_datepicker.show(getActivity().getFragmentManager(), DATEPICKER_TAG);
                    register_dob_datepicker.show(getActivity().getFragmentManager(), DATEPICKER_TAG);
                }
            }
        });

        //nationality selection
        txtRegisterNationality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkFragmentAdded()) {

                    //basicPicker();
                    showCountrySelector(getActivity(), countryList, "NATIONALITY");
                    CURRENT_PICKER = "NATIONALITY";
                }
            }
        });

        //country selection
        txtRegisterCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkFragmentAdded()) {
                    showCountrySelector(getActivity(), countryList, "COUNTRY");
                    CURRENT_PICKER = "COUNTRY";
                    txtRegisterState.setClickable(true);
                }
            }
        });

        //state selection
        txtRegisterState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txtRegisterCountry.getText().toString() != "") {
                    if (checkFragmentAdded()) {
                        showCountrySelector(getActivity(), state, "STATE");
                        CURRENT_PICKER = "STATE";
                    }
                } else {
                    Utils.toastNotification(getActivity(), getString(R.string.register_select_state));
                }
            }
        });

        //state selection
        txtRegisterNewsletterLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txtRegisterCountry.getText().toString() != "") {
                    if (checkFragmentAdded()) {
                        showCountrySelector(getActivity(), language, "NEWSLETTER_LANGUAGE");
                        CURRENT_PICKER = "NEWSLETTER_LANGUAGE";
                    }
                } else {
                    Utils.toastNotification(getActivity(), getString(R.string.register_select_country));
                }
            }
        });


        //subscribe selection
        registerSubscribeNewsletterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!subscribeNewsletter.equals("0")) {
                    registerSubscribeNewsletterBtn.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.checked));
                    subscribeNewsletter = "0";
                    newsletterPreferredLanguage.setVisibility(View.VISIBLE);

                } else {
                    registerSubscribeNewsletterBtn.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.un_checked));
                    subscribeNewsletter = "1";
                    newsletterPreferredLanguage.setVisibility(View.GONE);

                }
            }
        });

        //acknowledge information
        registerConfirmInformationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!acknowledgeInfo) {
                    registerConfirmInformationBtn.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.checked));
                    acknowledgeInfo = true;
                } else {
                    registerConfirmInformationBtn.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.un_checked));
                    acknowledgeInfo = false;
                }
            }
        });

        //information true
        registerAcknowledgeMemberShipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!confirmInfo) {
                    registerAcknowledgeMemberShipBtn.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.checked));
                    confirmInfo = true;
                } else {
                    registerAcknowledgeMemberShipBtn.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.un_checked));
                    confirmInfo = false;
                }
            }
        });

        //confirm parent
        registerConfirmParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!confirmParent) {
                    registerConfirmParent.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.checked));
                    confirmParent = true;
                } else {
                    registerConfirmParent.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.un_checked));
                    confirmParent = false;
                }
            }
        });

        //checkbox confirm information
        String parentConfirmation = getResources().getString(R.string.register_parent_confirm);
        String upToNCharacters0 = parentConfirmation.substring(0, Math.min(parentConfirmation.length(), 180));
        registerParentConfirmInformation.setText(upToNCharacters0 + "..." + "more", CheckBox.BufferType.SPANNABLE);
        String filterNo0 = upToNCharacters0 + "..." + "[more]";
        filterMoreText(parentConfirmation, filterNo0, registerParentConfirmInformation);

        //checkbox confirm information
        String confirmInformation = getResources().getString(R.string.register_confirm_information);
        String upToNCharacters1 = confirmInformation.substring(0, Math.min(confirmInformation.length(), 180));
        registerConfirmInformation.setText(upToNCharacters1 + "..." + "more", CheckBox.BufferType.SPANNABLE);
        String filterNo1 = upToNCharacters1 + "..." + "[more]";
        filterMoreText(confirmInformation, filterNo1, registerConfirmInformation);

        //checkbox acknowledge membership
        String acknowledgeMemberShip = getResources().getString(R.string.register_acknowledge_member);
        String upToNCharacters2 = acknowledgeMemberShip.substring(0, Math.min(acknowledgeMemberShip.length(), 180));
        registerAcknowledgeMemberShip.setText(upToNCharacters2 + "..." + "more", CheckBox.BufferType.SPANNABLE);
        String filterNo2 = upToNCharacters2 + "..." + "[more]";
        filterMoreText(acknowledgeMemberShip, filterNo2, registerAcknowledgeMemberShip);

        //checkbox subsribe newsletter
        String subscribeNewsletter = getResources().getString(R.string.register_check_subscribe);
        String upToNCharacters3 = subscribeNewsletter.substring(0, Math.min(subscribeNewsletter.length(), 180));
        registerSubscribeNewsletter.setText(upToNCharacters3);

        //continue to register
        btnRegisterContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mValidator.validate();
            }
        });


        return view;
    }

    public void basicPicker() {

        Intent basicPicker = new Intent(getActivity(), BasicPicker.class);
        basicPicker.putParcelableArrayListExtra("TOTAL_DUE", countryList);
        getActivity().startActivity(basicPicker);
    }

    //more text
    public void moreText(String text) {

        Dialog moreTextDialog;

        LayoutInflater li = LayoutInflater.from(getActivity());
        final View thisView = li.inflate(R.layout.default_dialog, null);

        TextView moreTxt = (TextView) thisView.findViewById(R.id.moreTxt);
        moreTxt.setText(Html.fromHtml(text));
        moreTxt.setLinksClickable(true);
        moreTxt.setLinkTextColor(ContextCompat.getColor(getActivity(), R.color.textLinkColor));
        moreTxt.setMovementMethod(LinkMovementMethod.getInstance());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(thisView);


        //moreTextDialog = builder.create();
        moreTextDialog = new Dialog(getActivity(), R.style.DialogThemePush);
        moreTextDialog.setContentView(thisView);
        moreTextDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#80000000")));
        moreTextDialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(moreTextDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        moreTextDialog.getWindow().setAttributes(lp);
        moreTextDialog.show();

    }

    public void filterMoreText(final String originalTxt, final String filter, TextView check) {

        String insurance3 = filter.toString();

        int i1 = insurance3.indexOf("[m");
        int i2 = insurance3.indexOf("e]");

        Spannable mySpannable = (Spannable) check.getText();
        ClickableSpan myClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                    /* do something */
                moreText(originalTxt);
            }
        };
        mySpannable.setSpan(myClickableSpan, i1, i2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        check.setMovementMethod(LinkMovementMethod.getInstance());

    }

    public void dataSetup(Bundle bundle) {

        progress = new ProgressDialog(getActivity());
        countryList = getCountry(getActivity());
        titleList = getTitle(getActivity());
        pref = new SharedPrefManager(getActivity());

        Intent extras = getActivity().getIntent();
        if (extras.hasExtra("FACEBOOK_USER_INFORMATION")) {
            String fbInfo = bundle.getString("FACEBOOK_USER_INFORMATION");
            Gson gson = new Gson();
            final UserFacebookInfo userInfo = gson.fromJson(fbInfo, UserFacebookInfo.class);
        }
        //ready to use.
    }

    public void datePickerSetting() {
        //datePicker setting
        register_dob_datepicker = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        register_dob_datepicker.setYearRange(calendar.get(Calendar.YEAR) - 80, calendar.get(Calendar.YEAR));
        register_dob_datepicker.setAccentColor(ContextCompat.getColor(getActivity(), R.color.default_theme_colour));

        Calendar output = Calendar.getInstance();
        output.set(Calendar.YEAR, output.get(Calendar.YEAR));
        output.set(Calendar.DAY_OF_MONTH, output.get(Calendar.DAY_OF_MONTH));
        output.set(Calendar.MONTH, output.get(Calendar.MONTH));
        register_dob_datepicker.setMaxDate(output);
    }

    @Override
    public void onValidationSucceeded() {


        //check t&c first
        if (parentInfo) {
            if (!confirmParent) {
                croutonAlert(getActivity(), "Please agree with parent/guardian term & conditions.");
            } else {
                proceed = true;
            }
        } else {
            proceed = true;
        }

        if (proceed) {
            if (!confirmInfo || !acknowledgeInfo) {
                croutonAlert(getActivity(), "Please agree with term & conditions.");
            } else {
                //proceed with presenter
                initiateLoading(getActivity());

                RegisterRequest registerRequest = new RegisterRequest();
                registerRequest.setUserName(txtRegisterEmail.getText().toString());
                registerRequest.setPassword(txtRegisterPassword.getText().toString());
                registerRequest.setTitle(title);
                registerRequest.setGender(gender);
                registerRequest.setAddressLine1("-");
                registerRequest.setAddressLine2("-");
                registerRequest.setAddressLine3("-");
                registerRequest.setMobilePhone(txtRegisterMobile.getText().toString());
                registerRequest.setFirstName(txtRegisterGivenName.getText().toString());
                registerRequest.setLastName(txtRegisterFamilyName.getText().toString());
                registerRequest.setNationality(txtRegisterNationality.getTag().toString());
                registerRequest.setCountry(txtRegisterCountry.getTag().toString());
                registerRequest.setState(txtRegisterState.getTag().toString());
                registerRequest.setPostalCode(txtRegisterPostCode.getText().toString());
                registerRequest.setCity(txtRegisterTown.getText().toString());
                registerRequest.setDateOfBirth(dateOfBirth);
                registerRequest.setQuestionAns1(subscribeNewsletter);
                registerRequest.setQuestionAns2(txtRegisterNewsletterLanguage.getTag().toString());
                registerRequest.setNickName(txtRegisterNickName.getText().toString());
                if (parentInfo) {
                    registerRequest.setParentGuardian(parentTitle);
                    registerRequest.setParentGuardianEmail(txtRegisterParentEmail.getText().toString());
                    registerRequest.setParentGuardianFullName(txtRegisterParentFullName.getText().toString());
                    registerRequest.setParentGuardianDocNumber(txtRegisterParentID.getText().toString());
                }
                presenter.onRequestRegister(registerRequest);
            }
        }


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

    /*Date Picker -> need to move to main activity*/
    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.setTargetFragment(RegisterFragmentPending.this, 0);
        newFragment.show(getFragmentManager(), "datePicker");
    }

    /*Country selector - > need to move to main activity*/
    public void showCountrySelector(Activity act, ArrayList constParam, String data) {
        if (act != null) {
            try {
                android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
                SelectionListFragment countryListDialogFragment = SelectionListFragment.newInstance( data,"na",false);
                countryListDialogFragment.setTargetFragment(RegisterFragmentPending.this, 0);
                countryListDialogFragment.show(fm, "countryListDialogFragment");

               /* if (data.equals("COUNTRY") || data.equals("NATIONALITY")) {


                }*/ /*else if (data.equals("STATE")) {

                    SelectStateFragment countryListDialogFragment = SelectStateFragment.newInstance(constParam);
                    countryListDialogFragment.setTargetFragment(RegisterFragmentPending.this, 0);
                    countryListDialogFragment.show(fm, "countryListDialogFragment");

                } else if (data.equals("LANGUAGE")) {

                    SelectDefaultFragment countryListDialogFragment = SelectDefaultFragment.newInstance(constParam);
                    countryListDialogFragment.setTargetFragment(RegisterFragmentPending.this, 0);
                    countryListDialogFragment.show(fm, "countryListDialogFragment");

                }*/
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        } else {
            if (CURRENT_PICKER.equals("COUNTRY")) {
                DropDownItem selectedCountry = data.getParcelableExtra(CURRENT_PICKER);
                txtRegisterCountry.setText(selectedCountry.getText());

                String splitCountryCode = splitCountryDialingCode("CountryCode", selectedCountry.getCode());
                retrieveState(splitCountryCode);

                txtRegisterCountry.setTag(splitCountryCode);

                NewsletterLanguageRequest newsletterLanguageRequest = new NewsletterLanguageRequest();
                newsletterLanguageRequest.setCountryCode(splitCountryCode);

                presenter.onRetrieveLanguage(newsletterLanguageRequest);

            } else if (CURRENT_PICKER.equals("STATE")) {
                DropDownItem selectedState = data.getParcelableExtra(CURRENT_PICKER);
                txtRegisterState.setText(selectedState.getText());
                txtRegisterState.setTag(selectedState.getCode());

            } else if (CURRENT_PICKER.equals("NATIONALITY")) {
                DropDownItem selectedNationality = data.getParcelableExtra(CURRENT_PICKER);
                txtRegisterNationality.setText(selectedNationality.getText());
                txtRegisterCountry.setText(selectedNationality.getText());

                //split
                String splitCountryCode = splitCountryDialingCode("CountryCode", selectedNationality.getCode());
                txtRegisterNationality.setTag(splitCountryCode);
                txtRegisterCountry.setTag(splitCountryCode);

                retrieveState(splitCountryCode);

                NewsletterLanguageRequest newsletterLanguageRequest = new NewsletterLanguageRequest();
                newsletterLanguageRequest.setCountryCode(splitCountryCode);

                presenter.onRetrieveLanguage(newsletterLanguageRequest);


            } else if (CURRENT_PICKER.equals("NEWSLETTER_LANGUAGE")) {

                DropDownItem selectedLanguage = data.getParcelableExtra(CURRENT_PICKER);
                txtRegisterNewsletterLanguage.setText(selectedLanguage.getText());
                txtRegisterNewsletterLanguage.setTag(selectedLanguage.getCode());

            }
        }
    }

    public void retrieveState(String countryCode) {

        txtRegisterState.setText("");
        txtRegisterState.setHint(getResources().getString(R.string.register_general_loading));
        txtRegisterNewsletterLanguage.setHint(getResources().getString(R.string.register_general_loading));

        StateRequest stateRequest = new StateRequest();
        stateRequest.setLanguageCode("en-GB");
        stateRequest.setCountryCode(countryCode);

        presenter.onStateRequest(stateRequest);
    }

    @Override
    public void onSuccessRequestState(StateReceive obj) {

        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {

            txtRegisterState.setHint(getResources().getString(R.string.register_select_state));

            Gson gson = new Gson();
            String state = gson.toJson(obj.getStateList());
            pref.setState(state);


            setState(obj);
        }

    }

    @Override
    public void onSuccessRequestLanguage(NewsletterLanguageReceive obj) {

        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {

            txtRegisterNewsletterLanguage.setHint(getResources().getString(R.string.register_newsletter_language_hint));
            if (obj.getCultureList().size() > 0) {

                Gson gson = new Gson();
                String newsletterLanguage = gson.toJson(obj.getCultureList());
                pref.setNewsletterLanguageList(newsletterLanguage);
                setLanguage(obj);
            }
        }

    }

    @Override
    public void onSuccessRegister(RegisterReceive obj) {

        dismissLoading();
        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {

            setSuccessDialog(getActivity(), "Thank You! Please check your email to complete your registration", LoginActivity.class, "Success!");

        }

    }

    public void setState(StateReceive stateList) {

        /*Each country click - reset state obj*/
        state = new ArrayList<DropDownItem>();

        for (int x = 0; x < stateList.getStateList().size(); x++) {

            DropDownItem itemCountry = new DropDownItem();
            itemCountry.setText(stateList.getStateList().get(x).getProvinceStateName());
            itemCountry.setCode(stateList.getStateList().get(x).getProvinceStateCode());
            itemCountry.setTag("State");

            state.add(itemCountry);
        }

    }

    public void setLanguage(NewsletterLanguageReceive languageList) {

        /*Each country click - reset state obj*/
        language = new ArrayList<DropDownItem>();

        for (int x = 0; x < languageList.getCultureList().size(); x++) {

            DropDownItem itemCountry = new DropDownItem();
            itemCountry.setText(languageList.getCultureList().get(x).getName());
            itemCountry.setCode(languageList.getCultureList().get(x).getCultureCode());
            itemCountry.setTag("Language");

            language.add(itemCountry);
        }
        language.get(0).getText();

    }

    public static ArrayList<DropDownItem> getStaticState() {
        return state;
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
            RegisterReceive obj = gson.fromJson(result.get(0).getCachedResult(), RegisterReceive.class);
            //onSuccessRegister(obj);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {

        //Reconstruct DOB
        String varMonth = "";
        String varDay = "";

        if (month < 9) {
            varMonth = "0";
        }
        if (day < 10) {
            varDay = "0";
        }

        //check age
        travellerAge = getAge(year, month + 1, day);
        if (travellerAge < 18) {
            userParentInformationBlock.setVisibility(View.VISIBLE);
            parentInfo = true;
        } else {
            userParentInformationBlock.setVisibility(View.GONE);
            parentInfo = false;
        }

        txtRegisterDOB.setText(day + "/" + (month + 1) + "/" + year);
        dateOfBirth = varDay + "" + day + "" + varMonth + "" + (month + 1) + "" + year;

    }

    public int getAge(int _year, int _month, int _day) {

        GregorianCalendar cal = new GregorianCalendar();
        int y, m, d, a;

        y = cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH);
        d = cal.get(Calendar.DAY_OF_MONTH);
        cal.set(_year, _month, _day);
        a = y - cal.get(Calendar.YEAR);
        if ((m < cal.get(Calendar.MONTH))
                || ((m == cal.get(Calendar.MONTH)) && (d < cal
                .get(Calendar.DAY_OF_MONTH)))) {
            --a;
        }
        if (a < 0)
            throw new IllegalArgumentException("Age < 0");
        return a;
    }

}
