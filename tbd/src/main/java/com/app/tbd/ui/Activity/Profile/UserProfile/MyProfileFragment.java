package com.app.tbd.ui.Activity.Profile.UserProfile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.method.KeyListener;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.tbd.MainController;
import com.app.tbd.Manifest;
import com.app.tbd.R;
import com.app.tbd.application.AnalyticsApplication;
import com.app.tbd.application.MainApplication;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Activity.FragmentContainerActivity;
import com.app.tbd.ui.Activity.Picker.SelectionListFragment;
import com.app.tbd.ui.Model.JSON.UserInfoJSON;
import com.app.tbd.ui.Model.Receive.EditProfileReceive;
import com.app.tbd.ui.Model.Receive.StateReceive;
import com.app.tbd.ui.Model.Receive.TBD.LoginReceive;
import com.app.tbd.ui.Model.Receive.UploadPhotoReceive;
import com.app.tbd.ui.Model.Receive.ViewUserReceive;
import com.app.tbd.ui.Model.Request.EditProfileRequest;
import com.app.tbd.ui.Model.Request.StateRequest;
import com.app.tbd.ui.Model.Request.UploadPhotoRequest;
import com.app.tbd.ui.Module.MyProfileModule;
import com.app.tbd.ui.Presenter.ProfilePresenter;
import com.app.tbd.ui.Realm.RealmObjectController;
import com.app.tbd.utils.DropDownItem;
import com.app.tbd.utils.SharedPrefManager;
import com.app.tbd.utils.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.gson.Gson;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.realm.Realm;
import io.realm.RealmResults;

import static com.facebook.FacebookSdk.getApplicationContext;

public class MyProfileFragment extends BaseFragment implements ProfilePresenter.MyProfileView, DatePickerDialog.OnDateSetListener {

    @Inject
    ProfilePresenter presenter;

    @InjectView(R.id.txtUserName)
    TextView txtUserName;

    @InjectView(R.id.txtUserBigID)
    TextView txtUserBigID;

    @InjectView(R.id.edit_btn)
    Button edit_btn;

    @InjectView(R.id.myImgUserDP)
    ImageView myImgUserDP;

    @InjectView(R.id.profile_dob_month)
    TextView profile_dob_month;

    @InjectView(R.id.profile_dob_year)
    TextView profile_dob_year;

    @InjectView(R.id.profile_dob_day)
    TextView profile_dob_day;

    @InjectView(R.id.profile_mobile_prefix)
    TextView profile_mobile_prefix;

    @InjectView(R.id.profile_emergency_mobile_prefix)
    TextView profile_emergency_mobile_prefix;

    /*@InjectView(R.id.imageLoadProgressBar)
    ProgressBar imageLoadProgressBar;*/

    @InjectView(R.id.loading_progress)
    ProgressBar loading_progress;

    // static EditText profile_given_name;
    // static EditText profile_family_name;
    static EditText profile_mobile;
    // static EditText profile_passport;
    static EditText profile_street1;
    static EditText profile_street2;
    static EditText profile_post_code;
    static EditText profile_city;
    static TextView profile_salutation;

    static EditText profile_emergency_name;
    static EditText profile_emergency_mobile;

    //static TextView profile_dob_month;
    //static TextView profile_dob_day;
    //static TextView profile_dob_year;

    static TextView profile_nationality;
    static TextView profile_state;
    static TextView profile_country;
    static Boolean selectable = false;
    static String token;
    static ProfilePresenter presenter2;
    static Drawable drawableRight, drawableRightDob;
    static EditProfileRequest editRequest;
    static String username;
    static LinearLayout userParentInformationBlock;
    static int travellerAge;
    static Boolean parentInfo;
    static TextView parentTitle;

    static EditText txtParentFullName;
    static EditText txtParentID;
    static EditText txtParentEmail;

    static String gender;
    static String cid;

    public static final String URL_REGEX = "^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$";

    DatePickerDialog dob_picker;

    Calendar calendar = Calendar.getInstance();
    ArrayList<DropDownItem> titleList = new ArrayList<DropDownItem>();
    ArrayList<DropDownItem> parentTitleList = new ArrayList<DropDownItem>();
    ArrayList<DropDownItem> countryList = new ArrayList<DropDownItem>();
    ArrayList<DropDownItem> stateList = new ArrayList<DropDownItem>();

    private AlertDialog dialog;
    private Uri mImageCaptureUri;

    int REQUEST_CAMERA = 0, SELECT_FILE = 1;

    String DATEPICKER_TAG = "DATEPICKER_TAG";
    String CURRENT_PICKER;
    String customerNumber;
    String userInfo;
    String stateCode;
    String stateName;
    String date;
    String userChoosenTask;
    Boolean galleryTrue = false;
    Boolean cameraTrue = false;

    SharedPrefManager pref;
    int fragmentContainerId;
    Activity act;
    private static String userInformation;
    private ViewUserReceive viewUserReceive;
    private View view;
    private Boolean result, result2;

    public static MyProfileFragment newInstance(Bundle bundle) {

        MyProfileFragment fragment = new MyProfileFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainApplication.get(getActivity()).createScopedGraph(new MyProfileModule(this)).inject(this);
        RealmObjectController.clearCachedResult(getActivity());
        act = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.my_profile, container, false);
        ButterKnife.inject(this, view);
        aq.recycle(view);
        /*AnalyticsApplication.sendScreenView("My profile loaded");*/

        pref = new SharedPrefManager(act);

        loading_progress.setVisibility(View.GONE);
        dataSetup();

        Bundle bundle = getArguments();
        customerNumber = bundle.getString("BIG_ID");
        userInformation = bundle.getString("USER_INFORMATION");

        Gson gson = new Gson();
        viewUserReceive = gson.fromJson(userInformation, ViewUserReceive.class);
        setShow(viewUserReceive);

        HashMap<String, String> initTicket = pref.getToken();
        token = initTicket.get(SharedPrefManager.TOKEN);
        Log.e("token", token);

        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //reconvert
                Intent editPage = new Intent(act, EditProfileActivity.class);
                editPage.putExtra("USER_INFORMATION", userInformation);
                getActivity().startActivity(editPage);

            }
        });

        profile_salutation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkFragmentAdded()) {
                    if (selectable) {
                        showCountrySelector(act, titleList, "TITLE");
                        CURRENT_PICKER = "TITLE";
                    }
                }
            }
        });

        profile_nationality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkFragmentAdded()) {
                    if (selectable) {
                        showCountrySelector(act, countryList, "NATIONALITY");
                        CURRENT_PICKER = "NATIONALITY";
                    }
                }
            }
        });

       /* profile_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkFragmentAdded()) {
                    if (selectable) {
                        dob_picker.show(getActivity().getFragmentManager(), DATEPICKER_TAG);
                    }
                }
            }
        });*/

        profile_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkFragmentAdded()) {
                    if (selectable) {
                        showCountrySelector(act, stateList, "STATE");
                        CURRENT_PICKER = "STATE";
                    }
                }
            }
        });

        profile_country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkFragmentAdded()) {
                    if (selectable) {
                        showCountrySelector(act, countryList, "COUNTRY");
                        CURRENT_PICKER = "COUNTRY";
                    }
                }
            }
        });

    /*    parentTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkFragmentAdded()) {
                    if (selectable) {
                        showCountrySelector(getActivity(), countryList, "PARENT_GUARDIAN_TITLE");
                        CURRENT_PICKER = "PARENT_GUARDIAN_TITLE";
                    }
                }
            }
        });*/

        myImgUserDP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //result = Utils.checkPermissionGallery(getActivity());
                dialog.show();


                //get manifest permission
               /* for (int x = 0; x < 2; x++) {
                    if (!result && selected) {
                        result = Utils.checkPermissionGallery(getActivity());
                        if (result) {
                            galleryTrue = true;
                            selected = true;
                            break;
                        }
                    }
                }*/

                /*//get manifest permission
                for (int x = 0; x < 2; x++) {
                    if (!result) {
                        result = Utils.checkPermissionCamera(getActivity());
                        if (result) {
                            cameraTrue = true;
                            break;
                        }
                    }
                }

                if (galleryTrue && cameraTrue) {
                    dialog.show();
                }*/

            }
        });

        myProfileNotEditable();

        return view;
    }

    public void showCountrySelector(Activity act, ArrayList constParam, String data) {
        if (act != null) {
            try {
                android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
                SelectionListFragment countryListDialogFragment = SelectionListFragment.newInstance(data, "na", false);
                countryListDialogFragment.setTargetFragment(MyProfileFragment.this, 0);
                countryListDialogFragment.show(fm, "countryListDialogFragment");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void dataSetup() {

        captureImageInitialization();

        presenter2 = presenter;
        // profile_given_name = (EditText) view.findViewById(R.id.profile_given_name);
        // profile_given_name.setTag(profile_given_name.getKeyListener());
        // profile_family_name = (EditText) view.findViewById(R.id.profile_family_name);
        // profile_family_name.setTag(profile_family_name.getKeyListener());

        profile_mobile = (EditText) view.findViewById(R.id.profile_mobile);
        profile_mobile.setTag(profile_mobile.getKeyListener());

        // profile_passport = (EditText) view.findViewById(R.id.profile_passport);
        // profile_passport.setTag(profile_passport.getKeyListener());
        profile_street1 = (EditText) view.findViewById(R.id.profile_street1);
        profile_street1.setTag(profile_street1.getKeyListener());

        profile_street2 = (EditText) view.findViewById(R.id.profile_street2);
        profile_street2.setTag(profile_street2.getKeyListener());

        profile_post_code = (EditText) view.findViewById(R.id.profile_post_code);
        profile_post_code.setTag(profile_post_code.getKeyListener());

        profile_city = (EditText) view.findViewById(R.id.profile_city);
        profile_city.setTag(profile_city.getKeyListener());

        txtParentFullName = (EditText) view.findViewById(R.id.profile_parent_name);
        txtParentFullName.setTag(txtParentFullName.getKeyListener());

        txtParentID = (EditText) view.findViewById(R.id.profile_parent_doc);
        txtParentID.setTag(txtParentID.getKeyListener());

        txtParentEmail = (EditText) view.findViewById(R.id.profile_parent_email);
        txtParentEmail.setTag(txtParentEmail.getKeyListener());

        profile_emergency_name = (EditText) view.findViewById(R.id.profile_emergency_name);
        profile_emergency_name.setTag(profile_emergency_name.getKeyListener());

        profile_emergency_mobile = (EditText) view.findViewById(R.id.profile_emergency_mobile);
        profile_emergency_mobile.setTag(profile_emergency_mobile.getKeyListener());

        profile_salutation = (TextView) view.findViewById(R.id.profile_salutation);
        profile_nationality = (TextView) view.findViewById(R.id.profile_nationality);
        profile_state = (TextView) view.findViewById(R.id.profile_state);
        //profile_dob = (TextView) view.findViewById(R.id.profile_dob);
        profile_country = (TextView) view.findViewById(R.id.profile_country);
        userParentInformationBlock = (LinearLayout) view.findViewById(R.id.userParentInformationBlock);
        //parentTitle = (TextView) view.findViewById(R.id.parentTitle);

        titleList = getTitle(act);
        countryList = getCountry(act);
        stateList = getState(act);
        //parentTitleList = getParentTitle(getActivity());
        drawableRight = ContextCompat.getDrawable(act, R.drawable.down_arrow_green);
        drawableRightDob = ContextCompat.getDrawable(act, R.drawable.down_arrow);

        Realm realm = RealmObjectController.getRealmInstance(act);
        final RealmResults<UserInfoJSON> result2 = realm.where(UserInfoJSON.class).findAll();
        final LoginReceive obj = (new Gson()).fromJson(result2.get(0).getUserInfo(), LoginReceive.class);

        username = obj.getUserName();

        if (obj.getProfile_URL() != null) {
            if (!obj.getProfile_URL().equals("")) {
                displayImage(act, myImgUserDP, obj.getProfile_URL());
            }
            /*aq.id(R.id.myImgUserDP).image(obj.getProfile_URL());*/
        } else {
            myImgUserDP.setImageDrawable(ContextCompat.getDrawable(act, R.drawable.no_profile_image));
        }
    }

    public static void myProfileEditable() {

        //profile_given_name.setKeyListener((KeyListener) profile_given_name.getTag());
        //.setKeyListener((KeyListener) profile_family_name.getTag());
        profile_mobile.setKeyListener((KeyListener) profile_mobile.getTag());
        // profile_passport.setKeyListener((KeyListener) profile_passport.getTag());
        profile_street1.setKeyListener((KeyListener) profile_street1.getTag());
        profile_street2.setKeyListener((KeyListener) profile_street2.getTag());
        profile_post_code.setKeyListener((KeyListener) profile_post_code.getTag());
        profile_city.setKeyListener((KeyListener) profile_city.getTag());
        txtParentFullName.setKeyListener((KeyListener) txtParentFullName.getTag());
        // txtParentID.setKeyListener((KeyListener) txtParentID.getTag());
        // txtParentEmail.setKeyListener((KeyListener) txtParentEmail.getTag());


        //  parentTitle.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableRight, null);
        profile_salutation.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableRight, null);
        profile_nationality.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableRight, null);
        profile_state.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableRight, null);
        profile_country.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableRight, null);
        //profile_dob.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableRightDob, null);


        //profile_salutation.setCompoundDrawables(null, null, drawableRight, null);
        selectable = true;

        dismissLoading();
    }

    public static void myProfileNotEditable() {

        //profile_given_name.setKeyListener(null);
        //profile_family_name.setKeyListener(null);
        profile_mobile.setKeyListener(null);
        //  profile_passport.setKeyListener(null);
        profile_street1.setKeyListener(null);
        profile_street2.setKeyListener(null);
        profile_post_code.setKeyListener(null);
        profile_city.setKeyListener(null);

        txtParentFullName.setKeyListener(null);
        txtParentID.setKeyListener(null);
        txtParentEmail.setKeyListener(null);

        profile_emergency_mobile.setKeyListener(null);
        profile_emergency_name.setKeyListener(null);

        //  parentTitle.setCompoundDrawables(null, null, null, null);
        profile_salutation.setCompoundDrawables(null, null, null, null);
        profile_nationality.setCompoundDrawables(null, null, null, null);
        profile_state.setCompoundDrawables(null, null, null, null);
        profile_country.setCompoundDrawables(null, null, null, null);
        //  profile_dob.setCompoundDrawables(null, null, null, null);

        selectable = false;
    }

    public static void editProfileRequest() {

        editRequest = new EditProfileRequest();
        editRequest.setToken(token);
        editRequest.setTitle(profile_salutation.getText().toString());
        //editRequest.setFirstName(profile_given_name.getText().toString());
        // editRequest.setLastName(profile_family_name.getText().toString());
        editRequest.setAddressLine1(profile_street1.getText().toString());
        editRequest.setAddressLine2(profile_street2.getText().toString());
        //editRequest.setAddressLine3("-");
        editRequest.setGender(gender);
        editRequest.setMobilePhone(profile_mobile.getText().toString());
        editRequest.setNationality(profile_nationality.getTag().toString());
        editRequest.setCountry(profile_country.getTag().toString());
        editRequest.setState(profile_state.getTag().toString());
        editRequest.setPostalCode(profile_post_code.getText().toString());
        editRequest.setCity(profile_city.getText().toString());
        //editRequest.setDateOfBirth(profile_dob.getTag().toString());
        editRequest.setQuestionAns1("0");
        editRequest.setQuestionAns2("en-GB");
        // editRequest.setPID(profile_passport.getText().toString());
        editRequest.setUserName(username);

        if (parentInfo) {
            editRequest.setParentGuardianFullName(txtParentFullName.getText().toString());
            //  editRequest.setParentGuardian(parentTitle.getText().toString());
            editRequest.setParentGuardianDocNumber(txtParentID.getText().toString());
            editRequest.setParentGuardianEmail(txtParentEmail.getText().toString());
        }

        editRequest.setCID(cid);
        //editRequest.setBusinessEmail("nurfatin93.jamaludin@gmail.com");
        /*
        editRequest.setEmergencyDialingCode("");
        editRequest.setEmergencyFamilyName("");
        editRequest.setEmergencyGivenName("");
        editRequest.setEmergencyPhoneNumber("");
        editRequest.setEmergencyRelationship("");*/


        presenter2.updateFunction(editRequest);
    }

    @Override
    public void onUpdateUserSuccess(EditProfileReceive obj) {
        //successUpdate
        dismissLoading();
        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), act);
        if (status) {
            String success = getString(R.string.edit_success);
            setSuccessDialogNoClear(act, success, null, getString(R.string.general_success));
        }
    }

    public void datePickerSetting(int year, int month, int day) {
        //datePicker setting
        dob_picker = DatePickerDialog.newInstance(this, year, month, day);
        dob_picker.setYearRange(calendar.get(Calendar.YEAR) - 80, calendar.get(Calendar.YEAR));
        dob_picker.setAccentColor(ContextCompat.getColor(act, R.color.default_theme_colour));

        Calendar output = Calendar.getInstance();
        output.set(Calendar.YEAR, output.get(Calendar.YEAR));
        output.set(Calendar.DAY_OF_MONTH, output.get(Calendar.DAY_OF_MONTH));
        output.set(Calendar.MONTH, output.get(Calendar.MONTH));
        dob_picker.setMaxDate(output);
    }

    public void retrieveState(String countryCode) {

        //profile_state.setText("");
        profile_state.setHint(getResources().getString(R.string.register_general_loading));

        HashMap<String, String> initAppData = pref.getLanguageCountry();
        String langCountryCode = initAppData.get(SharedPrefManager.LANGUAGE_COUNTRY);

        StateRequest stateRequest = new StateRequest();
        stateRequest.setLanguageCode(langCountryCode);
        stateRequest.setCountryCode(countryCode);

        presenter.onStateRequest(stateRequest);

    }

    public static String returnUserInfo() {
        return userInformation;
    }

    @Override
    public void onSuccessRequestState(StateReceive obj) {

        dismissLoading();

        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), act);
        if (status) {

            Gson gson = new Gson();
            String stateList = gson.toJson(obj.getStateList());
            pref.setState(stateList);
            //profile_state.setHint(getResources().getString(R.string.register_select_state));
            profile_state.setText(stateName);
            stateName = getStateName(act, stateCode);
            setState(obj);
        }
    }

    public void setState(StateReceive stateList2) {

        /*Each country click - reset state obj*/
        stateList = new ArrayList<DropDownItem>();

        for (int x = 0; x < stateList2.getStateList().size(); x++) {

            DropDownItem itemCountry = new DropDownItem();
            itemCountry.setText(stateList2.getStateList().get(x).getProvinceStateName());
            itemCountry.setCode(stateList2.getStateList().get(x).getProvinceStateCode());
            itemCountry.setTag("State");

            stateList.add(itemCountry);
        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentContainerId = ((FragmentContainerActivity) act).getFragmentContainerId();
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

        String newDob = day + "-" + month + "-" + year;

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date startDate;
        try {
            startDate = df.parse(newDob);
            Date myDate = startDate;
            String reportDate = (new SimpleDateFormat("dd MMM yyyy").format(myDate));
            //  profile_dob.setText(reportDate);


        } catch (ParseException e) {
            e.printStackTrace();
        }

        travellerAge = getAge(year, month + 1, day);
        if (travellerAge < 18) {
            userParentInformationBlock.setVisibility(View.VISIBLE);
            parentInfo = true;
        } else {
            userParentInformationBlock.setVisibility(View.GONE);
            parentInfo = false;
        }

        //profile_dob.setText(day + " " + getMonthAlphabet(month) + " " + year);
        date = varDay + "" + day + "" + varMonth + "" + (month + 1) + "" + year;

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

    public void setShow(ViewUserReceive returnData) {
        String salutation = returnData.getTitle();
        String givenName = returnData.getFirstName();
        String familyName = returnData.getLastName();
        String dob = returnData.getDOB();
        String nationalityCode = returnData.getNationality();
        String mobile = returnData.getMobilePhone();
        String passport = returnData.getPID();
        String street1 = returnData.getAddressLine1();
        String street2 = returnData.getAddressLine2();
        String city = returnData.getCity();
        String postcode = returnData.getPostalCode();
        String parentName = returnData.getParentGuardianFullName();
        String parentDoc = returnData.getParentGuardianDocNumber();
        String parentEmail = returnData.getParentGuardianEmail();
        String emergencyName = returnData.getEmergencyGivenName() + " " + returnData.getEmergencyFamilyName();
        String emergencyMobile = returnData.getEmergencyPhoneNumber();
        stateCode = returnData.getProvinceStateCode();

        String countryCode = returnData.getCountryCode();
        cid = returnData.getCID();
        date = dob;

        String day = date.substring(0, 2);
        String month = date.substring(2, 4);
        String year = date.substring(4);

        travellerAge = getAge(Integer.parseInt(day), Integer.parseInt(month), Integer.parseInt(year));
        if (travellerAge < 18) {
            userParentInformationBlock.setVisibility(View.VISIBLE);
            parentInfo = true;

            // parentTitle.setText(returnData.getParentGuardian());
            txtParentEmail.setText(returnData.getParentGuardianEmail());
            txtParentFullName.setText(returnData.getParentGuardianFullName());
            txtParentID.setText(returnData.getParentGuardianDocNumber());
        } else {
            userParentInformationBlock.setVisibility(View.GONE);
            parentInfo = false;
        }

        String newDob = day + "-" + month + "-" + year;

        datePickerSetting(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day));

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date startDate;
        try {
            startDate = df.parse(newDob);
            Date myDate = startDate;
            String reportDate = (new SimpleDateFormat("dd MMMM yyyy").format(myDate));

            String d = reportDate.substring(0, 2);
            String m = reportDate.substring(2, reportDate.length() - 4);
            String y = reportDate.substring(reportDate.length() - 4);

            profile_dob_month.setText(m);
            profile_dob_day.setText(d);
            profile_dob_year.setText(y);
            // profile_dob.setText(reportDate);
            // profile_dob.setTag(date);


        } catch (ParseException e) {
            e.printStackTrace();
        }

        //call from local
        String country = getCountryName(act, countryCode);
        String nationality = getCountryName(act, nationalityCode);

        if (getStateName(act, stateCode) == null) {

            HashMap<String, String> initAppData = pref.getLanguageCode();
            String languageCode = initAppData.get(SharedPrefManager.LANGUAGE_CODE);

            StateRequest stateRequest = new StateRequest();
            stateRequest.setLanguageCode(languageCode);
            stateRequest.setCountryCode(countryCode);
            stateRequest.setPresenterName("ProfilePresenter");

            presenter.onStateRequest(stateRequest);

        } else {
            stateName = getStateName(act, stateCode);
        }

        if (salutation.equalsIgnoreCase("MR")){
            profile_salutation.setText(getString(R.string.title_mr));
        }else{
            profile_salutation.setText(getString(R.string.title_ms));
        }

        //  profile_given_name.setText(givenName);
        //  profile_family_name.setText(familyName);

        profile_nationality.setText(nationality);
        profile_nationality.setTag(nationalityCode);

        //split

        if (mobile != null) {
            if (!mobile.equals("")) {
                String dialingCode = mobile.substring(0, 2);
                String phoneNo = mobile.substring(2, mobile.length());

                profile_mobile.setText(phoneNo);
                profile_mobile_prefix.setText("+" + dialingCode);
            }

        }

        profile_street1.setText(street1);
        profile_street2.setText(street2);
        profile_city.setText(city);
        profile_post_code.setText(postcode);
        profile_country.setText(country);
        profile_country.setTag(countryCode);
        profile_state.setText(stateName);
        profile_state.setTag(stateCode);
        gender = returnData.getGender();
        String bigText = getString(R.string.profile_bigshot_id);

        txtUserName.setText(givenName + " " + familyName);
        txtUserBigID.setText(bigText + " " + customerNumber);

        profile_emergency_name.setText(emergencyName);

        if (emergencyMobile != null) {
            if (!emergencyMobile.equals("")) {
                String dialingCode = emergencyMobile.substring(0, 2);
                String phoneNo = emergencyMobile.substring(2, emergencyMobile.length());

                profile_emergency_mobile.setText(phoneNo);
                profile_emergency_mobile_prefix.setText("+" + dialingCode);
            }
        }

        txtParentFullName.setText(parentName);
        txtParentEmail.setText(parentEmail);
        txtParentID.setText(parentDoc);
    }

    //-----------------------------------ADD NEW IMAGE--------------------------------------//

    private void captureImageInitialization() {

        LayoutInflater li = LayoutInflater.from(act);
        final View myView = li.inflate(R.layout.add_profile_image, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(act);
        builder.setView(myView);

        final TextView btnGallery = (TextView) myView.findViewById(R.id.btn_select_gallery);
        TextView btnCamera = (TextView) myView.findViewById(R.id.btn_take_camera);
        TextView btnRemove = (TextView) myView.findViewById(R.id.btn_remove_img);
        TextView btnCancel = (TextView) myView.findViewById(R.id.btn_cancel);

        dialog = builder.create();

        btnRemove.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                myImgUserDP.setImageDrawable(ContextCompat.getDrawable(act, R.drawable.no_profile_image));
                String noPicture = changeImage();
                loading_progress.setVisibility(View.VISIBLE);
                uploadPhoto(noPicture);
                dialog.dismiss();
            }
        });

        btnGallery.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                userChoosenTask = "Choose Photo";
                btnGallery.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            result = Utils.checkPermissionGallery(act);
                            if (result) {
                                galleryIntent();
                                dialog.dismiss();
                            } else {
                                Log.e("Gallery", "CLOSE");
                               // Utils.toastNotification(act, "Gallery Intent");

                            }
                        } catch (Exception e) {

                        }
                    }
                });

            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                userChoosenTask = "Take Photo";
                btnGallery.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            result = Utils.checkPermissionCamera(act);
                            /*result2 = Utils.checkPermissionWrite(getActivity());*/
                            if (result /*&& result2*/) {
                                cameraIntent();
                                dialog.dismiss();
                            } else {
                                Log.e("Camera", "CLOSE");
                                //Utils.toastNotification(act, "Camera Intent");
                            }
                        } catch (Exception e) {

                        }
                    }
                });

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog = builder.create();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = 1070;
        dialog.getWindow().setAttributes(lp);

    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString());
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                onSelectFromGalleryResult(data);

            } else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        } else {
            Log.e("STATUS UPLOAD", "Image not uploaded");
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        myImgUserDP.setImageBitmap(thumbnail);
        String imageString = changeImage();
        loading_progress.setVisibility(View.VISIBLE);
        uploadPhoto(imageString);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Uri selectedImageUri = null;

        try {
            selectedImageUri = data.getData();
        } catch (Exception e) {

        }

        if(selectedImageUri != null){
            String[] projection = {MediaStore.MediaColumns.DATA};
            Cursor cursor = act.managedQuery(selectedImageUri, projection, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            Log.e("column_index", String.valueOf(column_index));
            cursor.moveToFirst();
            String selectedImagePath = cursor.getString(column_index);

            Log.e("cursor", String.valueOf(cursor.getString(column_index)));

            if (selectedImagePath==null){
                Log.e("selectedImagePath", "NULL");
                Utils.toastNotification(act,"Image path not supported.");
                selectedImagePath = selectedImageUri.getPath();
                Log.e("selectedImagePath2", selectedImagePath);

            }else{
                Log.e("selectedImagePath", selectedImagePath);

                Pattern p = Pattern.compile(URL_REGEX);
                Matcher m = p.matcher(selectedImagePath);//replace with string to compare

                if (m.find()) {
                    Log.e("Message", "String contains URL");

                    Glide.with(getApplicationContext())
                            .load(selectedImagePath)
                            .asBitmap()
                            .into(new SimpleTarget<Bitmap>(100, 100) {
                                @Override
                                public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                                    myImgUserDP.setImageBitmap(resource);
                    /*String imageString = changeImageGlide();*/

                    /*Bitmap bitmap = resource.getBitmap();*/
                                    //here..
                                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                    resource.compress(Bitmap.CompressFormat.PNG, 100, bos);
                                    byte[] bb = bos.toByteArray();
                                    String image = Base64.encodeToString(bb, 0);

                                    loading_progress.setVisibility(View.VISIBLE);
                                    uploadPhoto(image);
                                }
                            });

                } else {
                    Bitmap bm;
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(selectedImagePath, options);
                    final int REQUIRED_SIZE = 400;
                    int scale = 1;
                    while (options.outWidth / scale / 2 >= REQUIRED_SIZE && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                        scale *= 2;
                    options.inSampleSize = scale;
                    options.inJustDecodeBounds = false;
                    bm = BitmapFactory.decodeFile(selectedImagePath, options);
                    myImgUserDP.setImageBitmap(bm);
                    String imageString = changeImage();
                    loading_progress.setVisibility(View.VISIBLE);
                    uploadPhoto(imageString);
                }
            }

            //String imageString = changeImage();
            //uploadPhoto(imageString);

        } else{
            Utils.toastNotification(act,"Image file not supported.");
        }


    }

    //-----------------------------------CONVERT IMAGE TO 64BASE--------------------------------------//


    public String changeImage() {
        BitmapDrawable drawable = (BitmapDrawable) myImgUserDP.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] bb = bos.toByteArray();
        String image = Base64.encodeToString(bb, 0);

        return image;
    }

    /*public String changeImageGlide() {
        GlideBitmapDrawable drawable = (GlideBitmapDrawable) myImgUserDP.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        //here..
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] bb = bos.toByteArray();
        String image = Base64.encodeToString(bb, 0);
        //..to here
>>>>>>> dae2d618c3046543cf39deab000489176f9c314e

        return image;
    }*/


    //-----------------------------------SAVE IMAGE TO DATABASE--------------------------------------//

    public void uploadPhoto(String ib) {

       /* HashMap<String, String> initImage = pref.getImageBase();
        String ib = initImage.get(SharedPrefManager.IMAGEBASE);*/

        UploadPhotoRequest uploadPhotoRequest = new UploadPhotoRequest();
        uploadPhotoRequest.setUserName(username);
        uploadPhotoRequest.setData(ib);
        uploadPhotoRequest.setExtension("png");
        uploadPhotoRequest.setToken(token);

        myImgUserDP.setAlpha(0.5f);
        /*loading_progress.setVisibility(View.VISIBLE);*/
        /*imageLoadProgressBar.setVisibility(View.VISIBLE);*/
        presenter.onRequestUploadPhoto(uploadPhotoRequest);

    }

    @Override
    public void onUploadPhotoSuccess(UploadPhotoReceive obj) {
        //dismissLoading();
        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), act);
        if (status) {

            myImgUserDP.setAlpha(1f);
            loading_progress.setVisibility(View.GONE);
            /*imageLoadProgressBar.setVisibility(View.GONE);*/
            displayImage(act, myImgUserDP, obj.getURL());

            /*aq.id(R.id.myImgUserDP).image(obj.getURL());*/

            //add URL to REALM User info
            Realm realm = RealmObjectController.getRealmInstance(act);
            final RealmResults<UserInfoJSON> result2 = realm.where(UserInfoJSON.class).findAll();
            LoginReceive loginReceive = (new Gson()).fromJson(result2.get(0).getUserInfo(), LoginReceive.class);

            loginReceive.setProfile_URL(obj.getURL());

            Gson gsonUserInfo = new Gson();
            String userInfo = gsonUserInfo.toJson(loginReceive);
            RealmObjectController.saveUserInformation(act, userInfo);
        }
        else {
            String textError = getString(R.string.error_not_save);
            setAlertDialog(act,textError, getString(R.string.addons_alert));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case Utils.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    /*if (userChoosenTask.equals("Take Photo")) {
                        cameraIntent();
                    } else if (userChoosenTask.equals("Choose Photo")) {*/
                    galleryIntent();
                    // }
                } else {
                    dialog.dismiss();
                }
                break;
        }
    }
}