package com.app.tbd.ui.Activity.BookingFlight.Add;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.app.tbd.MainController;
import com.app.tbd.R;
import com.app.tbd.application.AnalyticsApplication;
import com.app.tbd.application.MainApplication;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Model.JSON.AddOnInfo;
import com.app.tbd.ui.Model.JSON.AddonCached;
import com.app.tbd.ui.Model.JSON.TravellerCached;
import com.app.tbd.ui.Model.JSON.TravellerInfo;
import com.app.tbd.ui.Model.JSON.UserInfoJSON;
import com.app.tbd.ui.Model.Receive.AddInsuranceReceive;
import com.app.tbd.ui.Model.Receive.AddOnReceive;
import com.app.tbd.ui.Model.Receive.LoadInsuranceReceive;
import com.app.tbd.ui.Model.Receive.TBD.LoginReceive;
import com.app.tbd.ui.Model.Request.AddInsuranceRequest;
import com.app.tbd.ui.Module.InsuranceModule;
import com.app.tbd.ui.Presenter.BookingPresenter;
import com.app.tbd.ui.Realm.RealmObjectController;
import com.app.tbd.utils.FontStyle.PlayTextView;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import javax.inject.Inject;

import butterknife.ButterKnife;

import butterknife.InjectView;
import io.realm.Realm;
import io.realm.RealmResults;

public class InsuranceFragment extends BaseFragment implements BookingPresenter.InsuranceView {

    @Inject
    BookingPresenter presenter;

    /*@InjectView(R.id.txtAddOnNot)
    TextView txtAddOnNot;

    @InjectView(R.id.txtTotalInsurancePts)
    TextView txtTotalInsurancePts;

    @InjectView(R.id.btnAddInsurance)
    LinearLayout btnAddInsurance;
*/
    @InjectView(R.id.listview)
    ListView listview;

    @InjectView(R.id.noInsuranceAvailable)
    LinearLayout noInsuranceAvailable;

    @InjectView(R.id.passengerInsuranceList)
    LinearLayout passengerInsuranceList;

    /*@InjectView(R.id.extended_term)
    LinearLayout extended_term;*/

    /*@InjectView(R.id.listview)
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;*/

    private static String token;
    private static String username;
    private static String signature;
    private Boolean addFooter = true;

    static Boolean noInsurance = false;
    static CheckBox agree, agree2, tick;
    static BookingPresenter newPresenter;
    static String staticAlert, staticMessage1, staticMessage2;

    TravellerInfo travellerInfo;
   /* static LinearLayout extended_term;*/

    /*String point = getString(R.string.addons_points);*/
    String message1, message2, alert;


    public static InsuranceFragment newInstance(Bundle bundle) {
        InsuranceFragment fragment = new InsuranceFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RealmObjectController.clearCachedResult(getActivity());
        MainApplication.get(getActivity()).createScopedGraph(new InsuranceModule(this)).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.traveller_insurance, container, false);
        ButterKnife.inject(this, view);

        /*extended_term = (LinearLayout) view.findViewById(R.id.extended_term);*/

        dataSetup();

        return view;
    }

    public void dataSetup() {

        Bundle bundle = getArguments();

        String loadInsurance = bundle.getString("PASSENFER_INSURANCE");
        String added_info = bundle.getString("ADDED_INFO");

        Realm realm = RealmObjectController.getRealmInstance(getActivity());
        final RealmResults<UserInfoJSON> result2 = realm.where(UserInfoJSON.class).findAll();

        LoginReceive loginReceive = (new Gson()).fromJson(result2.get(0).getUserInfo(), LoginReceive.class);
        LoadInsuranceReceive loadInsuranceReceive = (new Gson()).fromJson(loadInsurance, LoadInsuranceReceive.class);
        AddOnReceive addOnReceive = (new Gson()).fromJson(added_info, AddOnReceive.class);

        token = loginReceive.getToken();
        signature = loginReceive.getSignature();
        username = loginReceive.getUserName();

        newPresenter = presenter;

        if (loadInsuranceReceive.getPassenger().size() > 0) {

            loadPage(loadInsuranceReceive, addOnReceive);
            noInsuranceAvailable.setVisibility(View.GONE);
            passengerInsuranceList.setVisibility(View.VISIBLE);
            noInsurance = false;

        } else {
            noInsurance = true;
            noInsuranceAvailable.setVisibility(View.VISIBLE);
            passengerInsuranceList.setVisibility(View.GONE);
        }

        /*AddOnInfo addOnInfo = new AddOnInfo();
        addOnInfo.setSeatSelected("N");
        String addOnInfoString = gsonUserInfo.toJson(addOnInfo);
        RealmObjectController.saveAddOnInfo(getActivity(), addOnInfoString);*/

    }


    public void loadPage(LoadInsuranceReceive obj, AddOnReceive addOnReceive) {

        loadAdapter(obj, addOnReceive);

        /*if (agree.isChecked()){
            extended_term.setVisibility(View.VISIBLE);

        }else{
            extended_term.setVisibility(View.GONE);
        }*/

        /*alert = getString(R.string.addons_alert);*/
        /*message1 = getString(R.string.add);*/
        /*message2 = getString(R.string.addons_agree);*/

    }

    public static void sendRequest(Activity act) {

        staticAlert = AnalyticsApplication.getContext().getString(R.string.insurance_alert);
        staticMessage1 = AnalyticsApplication.getContext().getString(R.string.insurance_textA);
        staticMessage2 = AnalyticsApplication.getContext().getString(R.string.insurance_textB);

        if (noInsurance) {
            /*act.finish();*/
            setAlertDialog(act, staticMessage2, staticAlert);
        } else {
            if (agree.isChecked() && agree2.isChecked()) {

                initiateLoading(act);

                AddInsuranceRequest addInsurance = new AddInsuranceRequest();
                addInsurance.setToken(token);
                addInsurance.setUserName(username);
                addInsurance.setSignature(signature);
                addInsurance.setInsurance("Y");

                newPresenter.onAddInsuranceRequest(addInsurance);

            } else if (agree.isChecked() && !agree2.isChecked()) {
                setAlertDialog(act, staticMessage1, staticAlert);

            } else if (tick.isChecked()) {

                initiateLoading(act);

                AddInsuranceRequest addInsurance = new AddInsuranceRequest();
                addInsurance.setToken(token);
                addInsurance.setUserName(username);
                addInsurance.setSignature(signature);
                addInsurance.setInsurance("N");

                newPresenter.onAddInsuranceRequest(addInsurance);

            } else {
                /*act.finish();*/
                setAlertDialog(act, staticMessage1, staticAlert);
            }
        }


    }

    public void loadAdapter(LoadInsuranceReceive obj, AddOnReceive addOnReceive) {

        /*layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new TravellerInsuranceAdapter(getActivity(), obj.getPassenger());*/

        Realm realm = RealmObjectController.getRealmInstance(getActivity());
        final RealmResults<TravellerCached> travellerCaches = realm.where(TravellerCached.class).findAll();
        travellerInfo = (new Gson()).fromJson(travellerCaches.get(0).getTraveller(), TravellerInfo.class);

        /*int sizeAll = travellerInfo.getList().size();*/

        int totalInsurancePts = 0;
        for (int b = 0; b < obj.getPassenger().size(); b++) {
            String type = travellerInfo.getList().get(b).getType();

            if (!type.equals("Infant")) {
                totalInsurancePts = totalInsurancePts + Integer.parseInt(obj.getPassenger().get(b).getPoints());
            }
        }


        TravellerInsuranceAdapter adapter = new TravellerInsuranceAdapter(getActivity(), obj.getPassenger(), travellerInfo);

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View footerView = layoutInflater.inflate(R.layout.bottom_insurance, null);
        View headerInsurance = layoutInflater.inflate(R.layout.header_insurance, null);

        listview.addHeaderView(headerInsurance);
        listview.addFooterView(footerView);
        listview.setAdapter(adapter);

        String point_text = getString(R.string.addons_points);

        final TextView totalPoints = (TextView) footerView.findViewById(R.id.totalPoints);
        totalPoints.setText(changeThousand(Integer.toString(totalInsurancePts)) + " " + point_text);

        final CheckBox checked = (CheckBox) footerView.findViewById(R.id.checked);
        final CheckBox checked2 = (CheckBox) footerView.findViewById(R.id.checked2);
        final CheckBox checked3 = (CheckBox) footerView.findViewById(R.id.checked3);
        final TextView insurance_link1 = (TextView) footerView.findViewById(R.id.insurance_link1);
        String parentConfirmation = getResources().getString(R.string.insurance_text2);

        /*String upToNCharacters0 = parentConfirmation.substring(0, Math.min(parentConfirmation.length(), 180));
        insurance_link1.setText(upToNCharacters0 + "..." + "more", CheckBox.BufferType.SPANNABLE);
        String filterNo0 = upToNCharacters0 + "..." + "[more]";
        filterMoreText(parentConfirmation, filterNo0, insurance_link1);*/

        insurance_link1.setText(Html.fromHtml(parentConfirmation));
        insurance_link1.setLinksClickable(true);
        insurance_link1.setLinkTextColor(ContextCompat.getColor(getActivity(), R.color.textLinkColor));
        insurance_link1.setMovementMethod(LinkMovementMethod.getInstance());


        //autoTick
        checked.setChecked(true);
        //checked2.setChecked(true);

        if (addOnReceive.getInsurance().equals("Y")) {
            checked.setChecked(true);
            checked2.setChecked(true);
            checked3.setChecked(false);
        }

        checked3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    checked.setChecked(false);
                    checked2.setChecked(false);
                }else{
                    checked.setChecked(true);
                }
            }
        });

        checked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    checked3.setChecked(false);
                } else {
                    checked2.setChecked(false);
                    checked3.setChecked(true);

                }
            }
        });

        checked2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    checked3.setChecked(false);
                    checked.setChecked(true);
                }
            }
        });

        agree = checked;
        agree2 = checked2;
        tick = checked3;

        //autoFill();

        /*if (addFooter) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View footerView = layoutInflater.inflate(R.layout.button_add_insurance, null);
            listview.addFooterView(footerView);
            addFooter = false;

            final TextView txtAddOnNot = (TextView) footerView.findViewById(R.id.txtAddOnNot);
            final TextView txtTotalInsurancePts = (TextView) footerView.findViewById(R.id.txtTotalInsurancePts);
            final LinearLayout btnAddInsurance = (LinearLayout) footerView.findViewById(R.id.btnAddInsurance);

            btnAddInsurance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    initiateLoading(getActivity());

                    AddInsuranceRequest addInsurance = new AddInsuranceRequest();
                    addInsurance.setToken(token);
                    addInsurance.setUserName(username);
                    addInsurance.setSignature(signature);
                    addInsurance.setInsurance("Y");

                    presenter.onAddInsuranceRequest(addInsurance);

                }
            });

            int totalInsurancePts = 0;
            for (int b = 0; b < obj.getPassenger().size(); b++) {
                totalInsurancePts = totalInsurancePts + Integer.parseInt(obj.getPassenger().get(b).getPoints());
            }

            txtTotalInsurancePts.setText(Integer.toString(totalInsurancePts));

        }*/

    }

    /*public String changeThousand(String newValue) {

        int x2 = Integer.parseInt(newValue);
        String str = String.format("%,d", x2);
        return str;
    }
*/
    @Override
    public void onAddInsuranceReceive(AddInsuranceReceive obj) {

        dismissLoading();
        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {

            Gson gsonUserInfo = new Gson();
            Realm realm = RealmObjectController.getRealmInstance(getActivity());
            final RealmResults<AddonCached> result2 = realm.where(AddonCached.class).findAll();
            AddOnInfo addOnInfo = (new Gson()).fromJson(result2.get(0).getAddonInfo(), AddOnInfo.class);
            addOnInfo.setInsuranceIncluded("Y");

            String addOnInfoString = gsonUserInfo.toJson(addOnInfo);
            RealmObjectController.saveAddOnInfo(getActivity(), addOnInfoString);
            getActivity().finish();

        }
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