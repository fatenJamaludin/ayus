package com.app.tbd.ui.Activity.Profile.BigPoint;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.app.tbd.application.MainApplication;
import com.app.tbd.ui.Model.Adapter.ExpandableListAdapter;
import com.app.tbd.ui.Model.Receive.TBD.BigPointReceive;
import com.app.tbd.ui.Model.Receive.TransactionHistoryReceive;
import com.app.tbd.ui.Model.TransactionHistory;
import com.app.tbd.ui.Model.TransactionItem;
import com.app.tbd.ui.Module.BigPointBaseModule;
import com.app.tbd.ui.Presenter.ProfilePresenter;
import com.google.gson.Gson;
import com.app.tbd.R;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Activity.FragmentContainerActivity;
import com.app.tbd.ui.Model.JSON.UserInfoJSON;
import com.app.tbd.ui.Model.Receive.TBD.LoginReceive;
import com.app.tbd.ui.Realm.RealmObjectController;
import com.app.tbd.utils.SharedPrefManager;
import com.triggertrap.seekarc.SeekArc;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.realm.Realm;
import io.realm.RealmResults;

public class BigPointBaseFragment extends BaseFragment implements ProfilePresenter.BigPointView {

    @Inject
    ProfilePresenter presenter;

    @InjectView(R.id.txtBigShotPoint)
    TextView txtBigShotPoint;

    @InjectView(R.id.bigPointExpiryDateLayout)
    LinearLayout bigPointExpiryDateLayout;

    @InjectView(R.id.transactionHistoryList)
    ExpandableListView transactionHistoryList;

    @InjectView(R.id.transactionHistoryLayout)
    LinearLayout transactionHistoryLayout;

    @InjectView(R.id.noTransactionHistoryLayout)
    LinearLayout noTransactionHistoryLayout;

    @InjectView(R.id.txtBigShotId)
    TextView txtBigShotId;

    @InjectView(R.id.textToday)
    TextView textToday;

    @InjectView(R.id.fade_text)
    TextView fade_text;

    @InjectView(R.id.seekArc)
    SeekArc seekArc;

    private int fragmentContainerId;
    private static final String SCREEN_LABEL = "Login";
    private SharedPrefManager pref;
    private String customerNumber;
    private BigPointReceive bigPointReceive;
    private TransactionHistoryReceive transactionHistoryReceive;
    private String bigPointInfo, bigPointTransaction;

    private ExpandableListAdapter adapter;
    private ArrayList<TransactionHistory> header;

    ArrayList<String> /*listDataHeader,*/ listChildMerchant, listChildPoint, listChildDate, listChildYear;
    ArrayList<Integer> listDataHeaderInt;

    public static BigPointBaseFragment newInstance(Bundle bundle) {

        BigPointBaseFragment fragment = new BigPointBaseFragment();
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainApplication.get(getActivity()).createScopedGraph(new BigPointBaseModule(this)).inject(this);
        RealmObjectController.clearCachedResult(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.big_point_profile, container, false);
        ButterKnife.inject(this, view);
        getDate();
        dataSetup();
        /*AnalyticsApplication.sendScreenView("My big point loaded");*/

        bigPointExpiryDateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Utils.toastNotification(getActivity(), "Work in progress");

                Intent expiryDate = new Intent(getActivity(), ExpiryDateActivity.class);
                expiryDate.putExtra("BIG_POINT_EXPIRY", bigPointInfo);
                getActivity().startActivity(expiryDate);

                //loadExpiryDate();
            }
        });

        seekArc.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        return view;
    }

    public void loadExpiryDate() {
/*
        initiateLoading(getActivity());

        //convert from realm cache data to basic class
        Realm realm = RealmObjectController.getRealmInstance(getActivity());
        final RealmResults<UserInfoJSON> result2 = realm.where(UserInfoJSON.class).findAll();
        final LoginReceive obj = (new Gson()).fromJson(result2.get(0).getUserInfo(), LoginReceive.class);

        //Log.e(obj.getCustomerNumber(), obj.getHash());
        TransactionHistoryRequest transactionHistoryRequest = new TransactionHistoryRequest();
        transactionHistoryRequest.setCustomerNumber("2790025233");
        //transactionHistoryRequest.setCustomerNumber(obj.getCustomerNumber());
        transactionHistoryRequest.setHash("4f2767f04b731d6186a8fc2ba06b3eb7");
        //transactionHistoryRequest.setHash(obj.getHash());
        presenter.onRequestTransactionHistory(transactionHistoryRequest);*/
    }

    public void dataSetup() {

        pref = new SharedPrefManager(getActivity());

        Bundle bundle = getArguments();
        bigPointInfo = bundle.getString("BIG_POINT");

        bigPointTransaction = bundle.getString("TRANSACTION_HISTORY");
        Log.e("bigPointExpiry", bigPointTransaction);

        Gson gson = new Gson();
        bigPointReceive = gson.fromJson(bigPointInfo, BigPointReceive.class);

        Gson history = new Gson();
        transactionHistoryReceive = history.fromJson(bigPointTransaction, TransactionHistoryReceive.class);

        //convert from realm cache data to basic class
        Realm realm = RealmObjectController.getRealmInstance(getActivity());
        final RealmResults<UserInfoJSON> result2 = realm.where(UserInfoJSON.class).findAll();
        final LoginReceive obj = (new Gson()).fromJson(result2.get(0).getUserInfo(), LoginReceive.class);

        customerNumber = obj.getCustomerNumber();
        txtBigShotId.setText(customerNumber);

        double newValue = Double.parseDouble((bigPointReceive.getAvailablePts()));
        int x2 = (int) newValue;
        String str = String.format(Locale.US,"%,d", x2);

        txtBigShotPoint.setText(str);

        setArc(bigPointReceive);
        setDynamicText(bigPointReceive);
        if (transactionHistoryReceive.getTransaction().size() >0){
            setData(transactionHistoryReceive);
        }else{
            Log.e("Status", "No Transaction");
        }

    }

    public void getDate() {

        Calendar c = Calendar.getInstance();
        System.out.println("Current time : " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(c.getTime());

        textToday.setText(getContext().getResources().getString(R.string.points_as_of) + " " + formattedDate);
    }

    public void setArc(BigPointReceive obj) {

        int p1 = changeDoubleSeek(obj.getExpPts1());
        int p2 = changeDoubleSeek(obj.getExpPts2());
        int p3 = changeDoubleSeek(obj.getExpPts3());
        int p4 = changeDoubleSeek(obj.getExpPts4());
        int p5 = changeDoubleSeek(obj.getExpPts5());
        int p6 = changeDoubleSeek(obj.getExpPts6());

        int sum = p1 + p2 + p3 + p4 + p5 + p6;
        int total = changeDoubleSeek(obj.getAvailablePts());

        if (total != 0) {
            int progress = sum / total * 100;
            seekArc.setProgress(progress);

            if (sum == 0) {
                seekArc.setProgress(3);
            } else if (progress == 100) {
                seekArc.setProgress(98);

            }
        } else {

            seekArc.setProgress(3);
        }
    }

    public void setDynamicText(BigPointReceive obj) {

        int p1 = changeDoubleSeek(obj.getExpPts1());
        int p2 = changeDoubleSeek(obj.getExpPts2());
        int p3 = changeDoubleSeek(obj.getExpPts3());
        int p4 = changeDoubleSeek(obj.getExpPts4());
        int p5 = changeDoubleSeek(obj.getExpPts5());
        int p6 = changeDoubleSeek(obj.getExpPts6());

        int sum = p1 + p2 + p3 + p4 + p5 + p6;
        String total = Integer.toString(sum);

        fade_text.setText(total);
    }

    public int changeDoubleSeek(String db) {

        double newPts1 = Double.parseDouble((db));
        int x = (int) newPts1;

        return x;
    }

    public void setData(TransactionHistoryReceive obj) {

        header = setStandardGroups(obj); //return list
        Log.e("List Size", String.valueOf(header.size()));

        if (obj.getTransaction().size() == 0) {
            transactionHistoryLayout.setVisibility(View.GONE);
            noTransactionHistoryLayout.setVisibility(View.VISIBLE);
        } else {
            transactionHistoryLayout.setVisibility(View.VISIBLE);
            noTransactionHistoryLayout.setVisibility(View.GONE);

            adapter = new ExpandableListAdapter(getActivity(), header);
            transactionHistoryList.setAdapter(adapter);
            transactionHistoryList.expandGroup(0);
        }
    }

    public ArrayList<TransactionHistory> setStandardGroups(TransactionHistoryReceive obj) {

        /*listDataHeader = new ArrayList<String>();*/
        listDataHeaderInt = new ArrayList<Integer>();
        listChildMerchant = new ArrayList<String>();
        listChildPoint = new ArrayList<String>();
        listChildDate = new ArrayList<String>();
        /*listChildDateInt = new ArrayList<Integer>();*/
        listChildYear = new ArrayList<String>();

        int length = obj.getTransaction().size();

        //HEAD
        for (int head = 0; head < length; head++) {
            String date = obj.getTransaction().get(head).getTxnDate();
            int year = Integer.parseInt(date.substring(date.length() - 4));

            if (!listDataHeaderInt.contains(year)) {
                listDataHeaderInt.add(year);
            }
        }

        /*Collections.sort(listDataHeaderInt);*/

        Integer[] nums = new Integer[listDataHeaderInt.size()];
        for (int h = 0; h < listDataHeaderInt.size(); h++) {
            nums[h] = listDataHeaderInt.get(h);
        }

        Arrays.sort(nums, Collections.reverseOrder());
        String[] newHead = Arrays.toString(nums).split("[\\[\\]]")[1].split(", ");
        System.out.println(Arrays.toString(newHead));


        //CHILD_YEAR
        for (int c1 = 0; c1 < length; c1++) {
            String date = obj.getTransaction().get(c1).getTxnDate();
            String year = date.substring(date.length() - 4);
            listChildYear.add(year);
            Log.e(year, Integer.toString(listChildYear.size()));//2016 : 1 - 126
        }

        /*ArrayList to Array Conversion */
        String[] newChildYear = new String[listChildYear.size()];
        for (int x = 0; x < listChildYear.size(); x++) {
            newChildYear[x] = listChildYear.get(x);
        }


        //CHILD_MERCHANT
        for (int c2 = 0; c2 < length; c2++) {
            String merchant = obj.getTransaction().get(c2).getMID();
            listChildMerchant.add(merchant);
            Log.e(merchant, Integer.toString(listChildMerchant.size()));//merchant : 1-126
        }

        /*ArrayList to Array Conversion */
        String[] newChildMerchant = new String[listChildMerchant.size()];
        for (int y = 0; y < listChildMerchant.size(); y++) {
            newChildMerchant[y] = listChildMerchant.get(y);
        }


        //CHILD_POINT
        for (int c3 = 0; c3 < length; c3++) {
            String point = obj.getTransaction().get(c3).getTxnPts();
            listChildPoint.add(point);
            Log.e(point, Integer.toString(listChildPoint.size()));//2016 : 1
        }

        /*ArrayList to Array Conversion */
        String[] newChildPoint = new String[listChildPoint.size()];
        for (int z = 0; z < listChildPoint.size(); z++) {
            newChildPoint[z] = listChildPoint.get(z);
        }


        //CHILD_DATE
        for (int c4 = 0; c4 < length; c4++) {
            String date = obj.getTransaction().get(c4).getTxnDate(); // 02/09/2016
            Log.e("TxnDate from API", obj.getTransaction().get(0).getTxnDate());

            String day = date.substring(0, 2);
            String month = date.substring(3, date.length() - 5);
            String year = date.substring(4);

            String newDob = day + "/" + month + "/" + year;

            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            Date startDate;

            try {
                startDate = df.parse(newDob);
                Date myDate = startDate;
                String reportDate = (new SimpleDateFormat("dd MMMM yyyy").format(myDate));

                String d = reportDate.substring(0, 2);
                String m = reportDate.substring(2, reportDate.length() - 4);
                String newDate = d + " " + m;

                listChildDate.add(newDate);
                Log.e(newDate, Integer.toString(listChildDate.size()));//01  March: 3

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        /*Integer[] nums2 = new Integer[listChildDateInt.size()];
        for (int i = 0; i < listChildDateInt.size(); i++) {
            nums2[i] = listChildDateInt.get(i);
        }

        Arrays.sort(nums2, Collections.reverseOrder());
        String[] newChildDate = Arrays.toString(nums2).split("[\\[\\]]")[1].split(", ");
        System.out.println(Arrays.toString(newChildDate));
*/

        /*ArrayList to Array Conversion */
        String[] newChildDate = new String[listChildDate.size()];
        for (int a = 0; a < listChildDate.size(); a++) {
            newChildDate[a] = listChildDate.get(a);
        }

        ArrayList<TransactionHistory> list = new ArrayList<TransactionHistory>();
        ArrayList<TransactionItem> childList;

        for (String group_name : newHead) { //2016 : 2016[0]

            TransactionHistory headList = new TransactionHistory();

            headList.setYear(group_name);
            Log.e("Set Year", group_name); //Set Year: 2016

            childList = new ArrayList<TransactionItem>();

            for (int k = 0; k < listChildYear.size(); k++) { //listChildYear : 126
                Log.e("Status", "1");
                Log.e(newChildYear[k] + " = ", group_name);

                if (newChildYear[k].equals(group_name)) {
                    //Log.e(newChildYear[k] + " = ", group_name);
                    Log.e("Status", "2");

                    TransactionItem child = new TransactionItem();
                    child.setMerchant(newChildMerchant[k]);
                    child.setItemPoint(newChildPoint[k]);
                    child.setTransactionDate(newChildDate[k]);
                    child.setItemYear(newChildYear[k]);
                    childList.add(child);

                    Log.e("Child Size", String.valueOf(childList.size()));

                } else {
                    Log.e("STATUS", "Child year not equal head");
                }

                //loop child
                headList.setItems(childList);
                Log.e(group_name, "l");

            }
            list.add(headList);
            //loop head
        }
        return list;
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

    }

    public void changeInt() {

        String arr = "[1,2]";
        String[] items = arr.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");

        int[] results = new int[items.length];

        for(
        int i = 0;
        i<items.length;i++)

        {
            try {
                results[i] = Integer.parseInt(items[i]);
            } catch (NumberFormatException nfe) {
                //NOTE: write something here if you need to recover from formatting errors
            }
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }
}
