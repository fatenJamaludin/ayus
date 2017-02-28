package com.app.tbd.ui.Activity.Profile.BigPoint;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import com.app.tbd.ui.Model.Adapter.ExpandableListAdapter;
import com.app.tbd.ui.Model.Adapter.TransactionHistoryAdapter;
import com.app.tbd.ui.Model.JSON.UserFacebookInfo;
import com.app.tbd.ui.Model.Receive.TransactionHistoryReceive;
import com.app.tbd.ui.Model.TransactionHistory;
import com.app.tbd.ui.Model.TransactionItem;
import com.app.tbd.utils.StringMatcher;
import com.google.gson.Gson;
import com.app.tbd.R;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Activity.FragmentContainerActivity;
import com.app.tbd.ui.Realm.RealmObjectController;
import com.app.tbd.utils.SharedPrefManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TransactionHistoryFragment extends BaseFragment {

    //@Inject
    //ProfilePresenter presenter;

    @InjectView(R.id.transactionHistoryList)
    ExpandableListView transactionHistoryList;

    @InjectView(R.id.transactionHistoryLayout)
    LinearLayout transactionHistoryLayout;

    @InjectView(R.id.noTransactionHistoryLayout)
    LinearLayout noTransactionHistoryLayout;

    private int fragmentContainerId;
    private static final String SCREEN_LABEL = "Login";
    private SharedPrefManager pref;
    private String customerNumber;
    //private TransactionHistoryAdapter transactionHistoryAdapter;

    private ExpandableListAdapter adapter;
    private ArrayList<TransactionHistory> header;
    //private ExpandableListView transactionHistoryList;

    ArrayList<String> listDataHeader, listChildMerchant, listChildPoint, listChildDate, listChildYear;

    public static TransactionHistoryFragment newInstance(Bundle bundle) {

        TransactionHistoryFragment fragment = new TransactionHistoryFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //MainApplication.get(getActivity()).createScopedGraph(new ProfileModule(this)).inject(this);
        RealmObjectController.clearCachedResult(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.big_point_transaction_history, container, false);

        //transactionHistoryList = (ExpandableListView) view.findViewById(R.id.transactionHistoryList);
        ButterKnife.inject(this, view);
        dataSetup();

        return view;
    }

    public void dataSetup() {

        pref = new SharedPrefManager(getActivity());
        Bundle bundle = getArguments();
        String bigPointTransaction = bundle.getString("TRANSACTION_HISTORY");
        Log.e("bigPointExpiry", bigPointTransaction);

        Gson gson = new Gson();
        TransactionHistoryReceive transactionHistoryReceive = gson.fromJson(bigPointTransaction, TransactionHistoryReceive.class);

        setData(transactionHistoryReceive);
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
        }
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

    public ArrayList<TransactionHistory> setStandardGroups(TransactionHistoryReceive obj) {

        listDataHeader = new ArrayList<String>();
        listChildMerchant = new ArrayList<String>();
        listChildPoint = new ArrayList<String>();
        listChildDate = new ArrayList<String>();
        listChildYear = new ArrayList<String>();

        int length = obj.getTransaction().size();

        //HEAD
        for (int head = 0; head < length; head++) {
            String date = obj.getTransaction().get(head).getTxnDate();
            String year = date.substring(date.length() - 4);
            if (!listDataHeader.contains(year)) {
                listDataHeader.add(year);
                Log.e(year, Integer.toString(listDataHeader.size()));//2016 : 1
                Log.e(year, listDataHeader.get(0)); //2016 : 2016
            }
        }

        /*ArrayList to Array Conversion */
        String[] newHead = new String[listDataHeader.size()];
        for (int h = 0; h < listDataHeader.size(); h++) {
            newHead[h] = listDataHeader.get(h);
            Log.e("Head", String.valueOf(newHead.length)); // Head : 1
        }

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

                if (newChildYear[k].equals(group_name)) {
                    Log.e(newChildYear[k] + " = ", group_name);
                    Log.e("Status", "2");

                    TransactionItem child = new TransactionItem();
                    child.setMerchant(newChildMerchant[k]);
                    child.setItemPoint(newChildPoint[k]);
                    child.setTransactionDate(newChildDate[k]);
                    child.setItemYear(newChildYear[k]);
                    childList.add(child);

                    Log.e("Child Size", String.valueOf(childList.size()));

                }else{
                    Log.e("STATUS","Child year not equal head");
                    break;
                }

                //loop child
                headList.setItems(childList);

            }
            list.add(headList);
            //loop head
        } return list;
    }
}