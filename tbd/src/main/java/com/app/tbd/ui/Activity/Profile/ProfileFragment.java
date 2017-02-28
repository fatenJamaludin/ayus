package com.app.tbd.ui.Activity.Profile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.app.tbd.MainController;
import com.app.tbd.MainFragmentActivity;
import com.app.tbd.application.AnalyticsApplication;
import com.app.tbd.application.MainApplication;
import com.app.tbd.ui.Activity.Profile.BigPoint.BigPointBaseActivity;
import com.app.tbd.ui.Activity.Profile.Option.OptionsActivity;
import com.app.tbd.ui.Activity.Profile.QRCode.QRCodeActivity;
import com.app.tbd.ui.Activity.Profile.UserProfile.MyProfileActivity;
import com.app.tbd.ui.Activity.PushNotificationInbox.PushNotificationActivity;
import com.app.tbd.ui.Activity.PushNotificationInbox.NotificationInboxList;
import com.app.tbd.ui.Model.Receive.TBD.BigPointReceive;
import com.app.tbd.ui.Model.Receive.TBD.BigPointReceiveFailed;
import com.app.tbd.ui.Model.Receive.TransactionHistoryReceive;
import com.app.tbd.ui.Model.Receive.ViewUserReceive;
import com.app.tbd.ui.Model.Request.NotificationMessage;
import com.app.tbd.ui.Model.Request.TBD.BigPointRequest;
import com.app.tbd.ui.Model.Request.TransactionHistoryRequest;
import com.app.tbd.ui.Model.Request.ViewUserRequest;
import com.app.tbd.ui.Module.ProfileModule;
import com.app.tbd.ui.Presenter.ProfilePresenter;
import com.google.gson.Gson;
import com.app.tbd.R;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Model.JSON.UserInfoJSON;
import com.app.tbd.ui.Model.Receive.TBD.LoginReceive;
import com.app.tbd.ui.Realm.RealmObjectController;
import com.app.tbd.utils.SharedPrefManager;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import io.realm.Realm;
import io.realm.RealmResults;

public class ProfileFragment extends BaseFragment implements ProfilePresenter.ProfileView {

    @Inject
    ProfilePresenter presenter;

    @InjectView(R.id.imgUserDP)
    CircularImageView imgUserDP;

    @InjectView(R.id.txtUserName)
    TextView txtUserName;

    @InjectView(R.id.txtBigUsername)
    TextView txtBigUsername;

    @InjectView(R.id.txtUserBigID)
    TextView txtUserBigID;

    @InjectView(R.id.profile_options)
    LinearLayout profile_options;

    @InjectView(R.id.txtBigPoint)
    TextView txtBigPoint;

    @InjectView(R.id.profileBigPointClickLayout)
    LinearLayout profileBigPointClickLayout;

    @InjectView(R.id.profileQRCode)
    LinearLayout profileQRCode;

    @InjectView(R.id.profile_myProfile)
    LinearLayout profile_myProfile;

    @InjectView(R.id.profileInbox)
    LinearLayout profileInbox;

    //@InjectView(R.id.imageLoadProgressBar)
    //ProgressBar imageLoadProgressBar;

    private int fragmentContainerId;
    private static final String SCREEN_LABEL = "Login";
    private SharedPrefManager pref;
    private ProgressDialog progress;
    private String customerNumber;
    private String userInfo;
    private String bigPoint;
    private LoginReceive loginReceive;
    private String gsonBigPoint;
    private Boolean bigPointClickable = false;
    private String username;
    private String token;
    static TextView inbox;
    static TextView inbox2;
    static View view;
    static Activity acti;

    public static ProfileFragment newInstance() {

        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainApplication.get(getActivity()).createScopedGraph(new ProfileModule(this)).inject(this);
        RealmObjectController.clearCachedResult(getActivity());
        acti = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.profile, container, false);
        ButterKnife.inject(this, view);

        inbox = (TextView) view.findViewById(R.id.txtInbox);
        inbox2 = (TextView) view.findViewById(R.id.txtInbox2);
        // aq = new com.app.tbd.base.AQuery(getActivity());
        // aq.recycle(view);

        triggerInbox(acti);
        dataSetup();

        //maskUserDP();

        profile_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent optionsPage = new Intent(getActivity(), OptionsActivity.class);
                getActivity().startActivity(optionsPage);
            }
        });

        profileQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent optionsPage = new Intent(getActivity(), QRCodeActivity.class);
                optionsPage.putExtra("BIG_SHOT_ID", loginReceive.getCustomerNumber());
                String lastname;
                if (loginReceive.getLastName() == null) {
                    lastname = "";
                } else {
                    lastname = loginReceive.getLastName();
                }
                optionsPage.putExtra("BIG_SHOT_NAME", loginReceive.getFirstName() + " " + lastname);
                getActivity().startActivity(optionsPage);
            }
        });

        profileInbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent notification = new Intent(getActivity(), PushNotificationActivity.class);
                getActivity().startActivity(notification);
            }
        });

        profileBigPointClickLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (bigPointClickable) {
                    loadTransactionHistory();
                }

            }
        });

        profile_myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadProfileInfo();

            }
        });

        return view;
    }

    public static void triggerInbox(Context act) {

        final Realm realm = RealmObjectController.getRealmInstanceContext(act);
        final RealmResults<NotificationInboxList> resultInbox = realm.where(NotificationInboxList.class).findAll();

        final String size = Integer.toString(resultInbox.size());
        Log.e("Size", size);

        int count = 0;
        for (NotificationInboxList s : resultInbox  ){
            if(s.getStatus().equalsIgnoreCase("Unread") || s.getStatus().equalsIgnoreCase("2")){
                count++;
            }
        }
        Log.e("Unread = ", String.valueOf(count));

        final String unread = String.valueOf(count);
        final String message;

        if (resultInbox.size() < 2){
            message = "Message";
        }else {
            message = "Messages";
        }

        if (count == 0 || count < 0){
            try {
                acti.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        inbox.setText(size + " " + message);
                        inbox2.setText(unread);
                        inbox2.setVisibility(View.GONE);
                    }
                });
            } catch (Exception e) {

            }

        }else{
            try {
                acti.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        /*inbox.setText("Notification" + "(" + unread + ")");*/
                        inbox.setText(size + " " + message);
                        inbox2.setText(unread);
                        inbox2.setVisibility(View.VISIBLE);
                    }
                });
            } catch (Exception e) {

            }
        }
    }

    public void loadProfileInfo() {

        initiateLoading(getActivity());
        // HashMap<String, String> initPassword = pref.getUserPassword();
        // String password = initPassword.get(SharedPrefManager.PASSWORD);
        ViewUserRequest data = new ViewUserRequest();
        data.setUserName(username);
        data.setToken(token);

        presenter.showFunction(data);

    }

    public void loadTransactionHistory() {

        initiateLoading(getActivity());

        TransactionHistoryRequest transactionHistoryRequest = new TransactionHistoryRequest();

        transactionHistoryRequest.setUserName(username);
        transactionHistoryRequest.setToken(token);

        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        String newDate = sdfDate.format(now);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate;
        try {
            startDate = df.parse(newDate);
            Date myDate = startDate;
            String reportDate = (new SimpleDateFormat("ddMMyyyy").format(myDate));

            String d = reportDate.substring(0, 2);
            String m = reportDate.substring(2, reportDate.length() - 4);
            String y = reportDate.substring(reportDate.length() - 4);

            int newYear = Integer.parseInt(y) - 4;
            String newY = Integer.toString(newYear);

            transactionHistoryRequest.setEndDate(y + m + d);
            transactionHistoryRequest.setStartDate(newY + m + d);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        presenter.onRequestTransactionHistory(transactionHistoryRequest);
    }

    @Override
    public void onViewUserSuccess(ViewUserReceive obj) {

        dismissLoading();
        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {

            userInfo = new Gson().toJson(obj);

            Intent myProfilePage = new Intent(getActivity(), MyProfileActivity.class);
            myProfilePage.putExtra("BIG_ID", customerNumber);
            myProfilePage.putExtra("USER_INFORMATION", userInfo);
            getActivity().startActivity(myProfilePage);

        }
    }

    @Override
    public void onTransactionHistorySuccess(TransactionHistoryReceive obj) {

        dismissLoading();
        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());

        if (status) {

            String transObj = new Gson().toJson(obj);
            Intent bigPointBase = new Intent(getActivity(), BigPointBaseActivity.class);
            bigPointBase.putExtra("TRANSACTION_HISTORY", transObj);
            bigPointBase.putExtra("BIG_POINT", gsonBigPoint);
            getActivity().startActivity(bigPointBase);

        }

    }

    @Override
    public void onBigPointReceiveFailed(BigPointReceiveFailed obj) {
        txtBigPoint.setText(getResources().getString(R.string.failed_load));
    }

    @Override
    public void onBigPointReceive(BigPointReceive obj) {

        //Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (obj.getStatus().equals("OK")) {

            Gson gsonUserInfo = new Gson();

            RealmObjectController.clearBigPointCached(MainFragmentActivity.getContext());

            Realm realm = RealmObjectController.getRealmInstance(getActivity());
            final RealmResults<UserInfoJSON> result2 = realm.where(UserInfoJSON.class).findAll();
            LoginReceive loginReceive = (new Gson()).fromJson(result2.get(0).getUserInfo(), LoginReceive.class);
            loginReceive.setBigPoint(obj.getAvailablePts());
            String userInfo = gsonUserInfo.toJson(loginReceive);
            RealmObjectController.saveUserInformation(getActivity(), userInfo);

            bigPointClickable = true;
            profileBigPointClickLayout.setClickable(true);

            double newValue = Double.parseDouble((obj.getAvailablePts()));
            int x2 = (int) newValue;
            String pts_text = getString(R.string.pts);
            String str = String.format(Locale.US, "%,d", x2);

            /*String str2 = changeThousand(obj.getAvailablePts());*/

            txtBigPoint.setText(str + " " + pts_text);
            bigPoint = str;

            //convert big point to string -> sent to big point detail
            gsonBigPoint = gsonUserInfo.toJson(obj);

        } else {
            txtBigPoint.setText(getResources().getString(R.string.failed_load));
        }
    }

    public void dataSetup() {

        pref = new SharedPrefManager(getActivity());
        progress = new ProgressDialog(getActivity());

        //convert from realm cache data to basic class
        Realm realm = RealmObjectController.getRealmInstance(getActivity());
        final RealmResults<UserInfoJSON> result2 = realm.where(UserInfoJSON.class).findAll();
        String textBig = getString(R.string.profile_bigshot_id);

        if (result2.size() > 0) {
            loginReceive = (new Gson()).fromJson(result2.get(0).getUserInfo(), LoginReceive.class);

            txtUserName.setText(loginReceive.getFirstName() + " " + loginReceive.getLastName());

            /*String textLong = "Nurul Shafikah Amira binti Aminnudin Abdul Karim bla bla bla bla";
            String textShort = "Imal Pasha";

            txtUserName.setText(textLong);*/

            txtUserName.post(new Runnable() {
                @Override
                public void run() {
                    int lineCount = txtUserName.getLineCount();
                    // Use lineCount here
                    Log.e("MAX LINE", String.valueOf(lineCount));
                    if (lineCount > 2){
                        txtUserName.setTextSize(21);

                        txtUserName.post(new Runnable() {
                            @Override
                            public void run() {
                                int lineCount2 = txtUserName.getLineCount();
                                // Use lineCount here
                                Log.e("NEW MAX LINE", String.valueOf(lineCount2));
                                if (lineCount2 > 2){
                                    txtUserName.setTextSize(18);

                                    txtUserName.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            int lineCount3 = txtUserName.getLineCount();
                                            // Use lineCount here
                                            Log.e("NEW MAX LINE", String.valueOf(lineCount3));
                                            if (lineCount3 > 2){
                                                txtUserName.setTextSize(16);

                                                txtUserName.post(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        int lineCount4 = txtUserName.getLineCount();
                                                        // Use lineCount here
                                                        Log.e("NEW MAX LINE", String.valueOf(lineCount4));
                                                        if (lineCount4 > 2){
                                                            txtUserName.setTextSize(14);
                                                        }
                                                    }
                                                });
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            });

            txtBigUsername.setText(loginReceive.getFirstName() + " " + loginReceive.getLastName());
            txtUserBigID.setText(textBig + " " + loginReceive.getCustomerNumber());
            customerNumber = loginReceive.getCustomerNumber();
            username = loginReceive.getUserName();
            token = loginReceive.getToken();

            if (loginReceive.getProfile_URL() == null) {
                Log.e("URL", "No Profile photo");
                imgUserDP.setImageResource(R.drawable.no_profile_image);
            }

            loadBigPointData(loginReceive);

        } else {
            Log.e("Login", "NULL");
        }
    }

    public void loadBigPointData(LoginReceive obj) {

        txtBigPoint.setText(getResources().getString(R.string.register_general_loading));

        BigPointRequest bigPointRequest = new BigPointRequest();
        bigPointRequest.setToken(obj.getToken());
        bigPointRequest.setUserName(obj.getUserName());
        presenter.onRequestBigPoint(bigPointRequest);

    }

   /* public void maskUserDP() {

        //mask dp profile
        Bitmap original = BitmapFactory.decodeResource(getResources(), R.drawable.no_profile_image);
        Bitmap mask = BitmapFactory.decodeResource(getResources(), R.drawable.dp_mask);
        Bitmap result = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Bitmap.Config.ARGB_8888);

        Bitmap resized = Bitmap.createScaledBitmap(original, mask.getWidth(), mask.getHeight(), true);

        Canvas mCanvas = new Canvas(result);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mCanvas.drawBitmap(resized, 0, 0, null);
        mCanvas.drawBitmap(mask, 0, 0, paint);
        paint.setXfermode(null);
        imgUserDP.setImageBitmap(result);
        imgUserDP.setScaleType(ImageView.ScaleType.CENTER);
        //imgUserDP.setBackgroundResource(R.drawable.image_border);

    }*/

    /*@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentContainerId = ((FragmentContainerActivity) getActivity()).getFragmentContainerId();
    }*/


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*if (resultCode != 0)
          return;*/
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
        checkBigPointResult();
        dismissLoading();

        //redisplay
        if (loginReceive == null) {
            Log.e("Error", "login null");
            RealmObjectController.clearCachedResult(getActivity());
        } else {
            reDisplayImage();
            triggerInbox(acti);
        }


    }

    public void reDisplayImage() {

        Realm realm = RealmObjectController.getRealmInstance(getActivity());
        final RealmResults<UserInfoJSON> result2 = realm.where(UserInfoJSON.class).findAll();
        loginReceive = (new Gson()).fromJson(result2.get(0).getUserInfo(), LoginReceive.class);

        if (loginReceive != null) {
            if (loginReceive.getProfile_URL() != null && !loginReceive.getProfile_URL().equals("")) {
                Log.e("With Photo", "True");
                displayImage(getActivity(), imgUserDP, loginReceive.getProfile_URL());
            }
        } else {
            Log.e("Error", "Failed retrieve login saved in Realm");
        }

    }

    public void checkBigPointResult() {
        /*RealmResults<CachedBigPointResult> result = RealmObjectController.getCachedBigPointResult(MainFragmentActivity.getContext());
        if (result.size() > 0) {
            Gson gson = new Gson();
            BigPointReceive obj = gson.fromJson(result.get(0).getCachedResult(), BigPointReceive.class);
            RealmObjectController.clearBigPointCached(getActivity());

            onBigPointReceive(obj);
        }*/
        BigPointReceive obj = RealmObjectController.getCachedBigPointResult(MainFragmentActivity.getContext());
        if (obj != null) {
            // Gson gson = new Gson();
            //RealmObjectController.clearBigPointCached(getActivity());
            onBigPointReceive(obj);
            Log.e("obj", obj.getStatus());
            Log.e("?", "1");

        } else {
            Log.e("?", "2");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }

    public void exitApp() {

        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText(getString(R.string.home_exit))
                .setContentText(getString(R.string.home_confirm_exit))
                .showCancelButton(true)
                .setCancelText(getString(R.string.home_cancel))
                .setConfirmText(getString(R.string.home_close))
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {

                        getActivity().finish();
                        System.exit(0);

                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                    }
                })
                .show();

    }
}
