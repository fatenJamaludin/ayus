package com.app.tbd.ui.Activity.Profile.UserProfile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.app.tbd.MainController;
import com.app.tbd.R;
import com.app.tbd.application.MainApplication;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Activity.FragmentContainerActivity;
import com.app.tbd.ui.Activity.Picker.SelectionListFragment;
import com.app.tbd.ui.Activity.Picker.SelectDefaultFragment;
import com.app.tbd.ui.Activity.Picker.SelectStateFragment;
import com.app.tbd.ui.Model.Receive.EditProfileReceive;
import com.app.tbd.ui.Model.Receive.StateReceive;
import com.app.tbd.ui.Model.Receive.ViewUserReceive;
import com.app.tbd.ui.Model.Request.EditProfileRequest;
import com.app.tbd.ui.Model.Request.StateRequest;
import com.app.tbd.ui.Module.EditProfileModule;
import com.app.tbd.ui.Presenter.EditProfilePresenter;
import com.app.tbd.ui.Realm.RealmObjectController;
import com.app.tbd.utils.DropDownItem;
import com.app.tbd.utils.SharedPrefManager;
import com.app.tbd.utils.Utils;
import com.google.gson.Gson;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class EditProfileFragment extends BaseFragment implements EditProfilePresenter.EditProfileView, DatePickerDialog.OnDateSetListener, Validator.ValidationListener {

    @Inject
    EditProfilePresenter presenter;

    @InjectView(R.id.edit_salutation)
    TextView edit_salutation;

    @InjectView(R.id.edit_given_name)
    EditText edit_given_name;

    @InjectView(R.id.edit_family_name)
    EditText edit_family_name;

    @InjectView(R.id.edit_dob)
    TextView edit_dob;

    @InjectView(R.id.edit_nationality)
    TextView edit_nationality;

    @InjectView(R.id.edit_mobile)
    EditText edit_mobile;

    @InjectView(R.id.edit_passport)
    EditText edit_passport;

    @InjectView(R.id.edit_street1)
    EditText edit_street1;

    @InjectView(R.id.edit_street2)
    EditText edit_street2;

    @InjectView(R.id.edit_city)
    EditText edit_city;

    @InjectView(R.id.edit_post_code)
    EditText edit_post_code;

    @InjectView(R.id.edit_state)
    TextView edit_state;

    @InjectView(R.id.edit_country)
    TextView edit_country;

    @InjectView(R.id.update_btn)
    Button update_btn;

    private int fragmentContainerId;
    public static final String DATEPICKER_TAG = "DATEPICKER_TAG";
    private Validator mValidator;
    private DatePickerDialog register_dob_datepicker;
    private String CURRENT_PICKER;
    private String dateOfBirth;
    private SharedPrefManager pref;

    final Calendar calendar = Calendar.getInstance();

    /*DropDown Variable*/
    private ArrayList<DropDownItem> titleList = new ArrayList<DropDownItem>();
    private ArrayList<DropDownItem> countryList = new ArrayList<DropDownItem>();
    private static ArrayList<DropDownItem> state = new ArrayList<DropDownItem>();

    public static EditProfileFragment newInstance(Bundle bundle) {

        EditProfileFragment fragment = new EditProfileFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainApplication.get(getActivity()).createScopedGraph(new EditProfileModule(this)).inject(this);
        RealmObjectController.clearCachedResult(getActivity());

        // Validator
        mValidator = new Validator(this);
        mValidator.setValidationListener(this);
        mValidator.setValidationMode(Validator.Mode.BURST);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.edit_profile, container, false);
        ButterKnife.inject(this, view);
        aq.recycle(view);

        datePickerSetting();

        Bundle bundle = getArguments();
        String insurance = bundle.getString("USER_INFORMATION");

        Gson gson = new Gson();
        ViewUserReceive obj = gson.fromJson(insurance, ViewUserReceive.class);

        edit_salutation.setText(obj.getTitle());
        edit_given_name.setText(obj.getFirstName());
        edit_family_name.setText(obj.getEmergencyFamilyName());
        edit_mobile.setText(obj.getMobilePhone());
        edit_passport.setText(obj.getPID());
        edit_street1.setText(obj.getAddressLine1());
        edit_street2.setText(obj.getAddressLine2());
        edit_city.setText(obj.getCity());
        edit_post_code.setText(obj.getPostalCode());

        String date = obj.getDOB();

        String day = date.substring(0, 2);
        String month = date.substring(2, 4);
        String year = date.substring(4);
        String newDob = day + "-" + month + "-" + year;

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date startDate;
        try {
            startDate = df.parse(newDob);

            Date myDate = startDate;
            String reportDate = (new SimpleDateFormat("dd MMM yyyy").format(myDate));
            edit_dob.setText(reportDate);
            pref.setEditDOB(reportDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        String c = getCountryName(getActivity(), obj.getCountryCode());
        String n = getCountryName(getActivity(), obj.getNationality());

        edit_country.setText(c);
        edit_nationality.setText(n);

        HashMap<String, String> initAuth = pref.getEditStateName();
        final String state2 = initAuth.get(SharedPrefManager.EDIT_STATE_NAME);

        edit_state.setText(state2);

        //date of birth
        edit_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkFragmentAdded()) {
                    register_dob_datepicker.show(getActivity().getFragmentManager(), DATEPICKER_TAG);
                }
            }
        });

        edit_nationality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkFragmentAdded()) {
                    showCountrySelector(getActivity(), countryList, "NATIONALITY");
                    CURRENT_PICKER = "NATIONALITY";
                }
            }
        });

        //country selection
        edit_country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkFragmentAdded()) {
                    showCountrySelector(getActivity(), countryList, "COUNTRY");
                    CURRENT_PICKER = "COUNTRY";
                    edit_state.setClickable(true);
                }
            }
        });

        //state selection
        edit_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edit_country.getText().toString() != "") {
                    if (checkFragmentAdded()) {
                        showCountrySelector(getActivity(), state, "STATE");
                        CURRENT_PICKER = "STATE";
                    }
                } else {
                    String utilsText = getString(R.string.edit_utils);
                    Utils.toastNotification(getActivity(), utilsText);
                }
            }
        });

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mValidator.validate();
            }
        });


        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentContainerId = ((FragmentContainerActivity) getActivity()).getFragmentContainerId();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        } else {
            if (CURRENT_PICKER.equals("COUNTRY")) {
                DropDownItem selectedCountry = data.getParcelableExtra(SelectionListFragment.KEY_COUNTRY_LIST);
                edit_country.setText(selectedCountry.getText());

                String splitCountryCode = splitCountryDialingCode("CountryCode", selectedCountry.getCode());
                retrieveState(splitCountryCode);

                edit_country.setTag(splitCountryCode);

            } else if (CURRENT_PICKER.equals("STATE")) {
                DropDownItem selectedState = data.getParcelableExtra(SelectStateFragment.KEY_STATE_LIST);
                edit_state.setText(selectedState.getText());
                edit_state.setTag(selectedState.getCode());

            } else if (CURRENT_PICKER.equals("NATIONALITY")) {
                DropDownItem selectedNationality = data.getParcelableExtra(SelectionListFragment.KEY_COUNTRY_LIST);
                edit_nationality.setText(selectedNationality.getText());
                edit_nationality.setText(selectedNationality.getText());

                //split
                String splitCountryCode = splitCountryDialingCode("CountryCode", selectedNationality.getCode());
                edit_nationality.setTag(splitCountryCode);
                edit_country.setTag(splitCountryCode);

                retrieveState(splitCountryCode);
            }
        }
    }

    public void retrieveState(String countryCode) {

        edit_state.setHint(getResources().getString(R.string.register_general_loading));

        StateRequest stateRequest = new StateRequest();
        stateRequest.setLanguageCode("en-GB");
        stateRequest.setCountryCode(countryCode);

        presenter.onStateRequest(stateRequest);
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

    public void showCountrySelector(Activity act, ArrayList constParam, String data) {
        if (act != null) {
            try {
                android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
                if (data.equals("COUNTRY") || data.equals("NATIONALITY")) {

                    SelectionListFragment countryListDialogFragment = SelectionListFragment.newInstance("test","na",false);
                    countryListDialogFragment.setTargetFragment(EditProfileFragment.this, 0);
                    countryListDialogFragment.show(fm, "countryListDialogFragment");

                } else if (data.equals("STATE")) {

                    SelectStateFragment countryListDialogFragment = SelectStateFragment.newInstance(constParam);
                    countryListDialogFragment.setTargetFragment(EditProfileFragment.this, 0);
                    countryListDialogFragment.show(fm, "countryListDialogFragment");

                } else if (data.equals("LANGUAGE")) {

                    SelectDefaultFragment countryListDialogFragment = SelectDefaultFragment.newInstance(constParam);
                    countryListDialogFragment.setTargetFragment(EditProfileFragment.this, 0);
                    countryListDialogFragment.show(fm, "countryListDialogFragment");

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
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

        edit_dob.setText(day + "/" + (month + 1) + "/" + year);
        dateOfBirth = varDay + "" + day + "" + varMonth + "" + (month + 1) + "" + year;

    }

    @Override
    public void onUpdateUserSuccess(EditProfileReceive obj) {
        //successUpdate
        dismissLoading();
        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {
            String success = getString(R.string.edit_success);
            setSuccessDialog(getActivity(), success, MyProfileActivity.class, getString(R.string.general_success));
        }
    }

    @Override
    public void onSuccessRequestState(StateReceive obj) {
        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {
            edit_state.setHint(getResources().getString(R.string.register_select_state));
            setState(obj);
        }

    }

    @Override
    public void onValidationSucceeded() {
        //requestUpdate
        //proceed with presenter

        HashMap<String, String> initTicket = pref.getToken();
        final String ticket = initTicket.get(SharedPrefManager.TOKEN);

        initiateLoading(getActivity());

        EditProfileRequest editRequest = new EditProfileRequest();
        editRequest.setToken(ticket);
        editRequest.setTitle(edit_salutation.getText().toString());
        editRequest.setFirstName(edit_given_name.getText().toString());
        editRequest.setLastName(edit_family_name.getText().toString());
        editRequest.setAddressLine1(edit_street1.getText().toString());
        editRequest.setAddressLine2(edit_street2.getText().toString());
        editRequest.setAddressLine3("-");
        editRequest.setGender("0");
        editRequest.setMobilePhone(edit_mobile.getText().toString());
        editRequest.setNationality(edit_nationality.getTag().toString());
        editRequest.setCountry(edit_country.getTag().toString());
        editRequest.setState(edit_state.getTag().toString());
        editRequest.setPostalCode(edit_post_code.getText().toString());
        editRequest.setCity(edit_city.getText().toString());
        editRequest.setDateOfBirth(dateOfBirth);
        editRequest.setQuestionAns1("0");
        editRequest.setPID(edit_passport.getText().toString());
        editRequest.setQuestionAns2("en-GB");
        editRequest.setUserName("nurfatin93.jamaludin@gmail.com");
        editRequest.setCID("1080028674");
        editRequest.setBusinessEmail("nurfatin93.jamaludin@gmail.com");
        editRequest.setParentGuardianFullName("");
        editRequest.setEmergencyDialingCode("");
        editRequest.setEmergencyFamilyName("");
        editRequest.setEmergencyGivenName("");
        editRequest.setEmergencyPhoneNumber("");
        editRequest.setEmergencyRelationship("");
        editRequest.setParentGuardian("");
        editRequest.setParentGuardianDocNumber("");
        editRequest.setParentGuardianEmail("");

        presenter.updateFunction(editRequest);

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
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }
}



