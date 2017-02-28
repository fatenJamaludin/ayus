package com.app.tbd.ui.Activity.PushNotificationInbox;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.tbd.MainController;
import com.app.tbd.R;
import com.app.tbd.application.MainApplication;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Activity.FragmentContainerActivity;
import com.app.tbd.ui.Model.Adapter.ListViewAdapter;
import com.app.tbd.ui.Model.JSON.NotificationInfo;
import com.app.tbd.ui.Model.JSON.UserInfoJSON;
import com.app.tbd.ui.Model.Receive.MessageStatusReceive;
import com.app.tbd.ui.Model.Receive.TBD.LoginReceive;
import com.app.tbd.ui.Model.Request.MessageStatusRequest;
import com.app.tbd.ui.Module.PushNotificationModule;
import com.app.tbd.ui.Presenter.PushNotificationPresenter;
import com.app.tbd.ui.Realm.RealmObjectController;
import com.daimajia.swipe.util.Attributes;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.realm.Realm;
import io.realm.RealmResults;

public class PushNotificationFragment extends BaseFragment implements PushNotificationPresenter.PushNotificationView{

    private int fragmentContainerId;
    private ListViewAdapter mAdapter;
    Activity act;
    static PushNotificationPresenter staticPresenter;

    @Inject
    PushNotificationPresenter presenter;

    @InjectView(R.id.listview)
    ListView mListView;

    @InjectView(R.id.no_noty)
    TextView no_noty;

    public static PushNotificationFragment newInstance() {

        PushNotificationFragment fragment = new PushNotificationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        act = getActivity();
        MainApplication.get(getActivity()).createScopedGraph(new PushNotificationModule(this)).inject(this);
        RealmObjectController.clearCachedResult(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.listview, container, false);
        ButterKnife.inject(this, view);

        staticPresenter = presenter;

        //call from Realm
        Realm realm = RealmObjectController.getRealmInstance(getActivity());
        final RealmResults<NotificationInboxList> result2 = realm.where(NotificationInboxList.class).findAll();

        Log.e("Size Notification", String.valueOf(result2.size()));

        /*adapter = new PushNotificationAdapter(getActivity(), result2);
        notificationList.setAdapter(adapter);*/

        if (result2.size()!= 0){

            //convert
            List<NotificationInfo> notificationInfo = new ArrayList<NotificationInfo>();

            for (int position = 0; position < result2.size(); position++){
                NotificationInfo info = new NotificationInfo();
                info.setDatetime(result2.get(position).getDatetime());
                info.setMessage(result2.get(position).getMessage());
                info.setStatus(result2.get(position).getStatus());
                info.setTitle(result2.get(position).getTitle());
                info.setUsername(result2.get(position).getUsername());
                info.setBody(result2.get(position).getBody());
                info.setMessageID(result2.get(position).getMessageID());
                info.setType(result2.get(position).getMessageType());
                info.setId(result2.get(position).getId());
                notificationInfo.add(info);
            }

            Date dd = dateString(result2.get(0).getDatetime());
            Log.e("DD", String.valueOf(dd));

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*((SwipeLayout)(mListView.getChildAt(position - mListView.getFirstVisiblePosition()))).open(false);*/
                    /*Toast.makeText(getActivity(), "setOnItemClickListener", Toast.LENGTH_SHORT).show();*/

                }
            });
            mListView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Log.e("ListView", "OnTouch");
                    /*Toast.makeText(getActivity(), "OnTouch", Toast.LENGTH_SHORT).show();*/
                    return false;
                }
            });

            mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getActivity(), "OnItemLongClickListener", Toast.LENGTH_SHORT).show();
                    return false;
                }
            });

            mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                    /*Toast.makeText(getActivity(), "onScrollStateChanged", Toast.LENGTH_SHORT).show();*/
                    Log.e("ListView", "onScrollStateChanged");
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    /*Toast.makeText(getActivity(), "onScroll", Toast.LENGTH_SHORT).show();*/
                }
            });

            mListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Log.e("ListView", "onItemSelected:" + position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    Log.e("ListView", "onNothingSelected:");
                }
            });

            //Collections.reverse(notificationInfo);
            mAdapter = new ListViewAdapter(getContext(), getActivity(), notificationInfo);
            mListView.setAdapter(mAdapter);
            mAdapter.setMode(Attributes.Mode.Single);

        } else {
            mListView.setVisibility(View.GONE);
            no_noty.setVisibility(View.VISIBLE);
        }

        return view;
    }

    public static void updateMessage(Activity act, String messageID, String module, String messageType){

        Realm realm = RealmObjectController.getRealmInstance(act);
        final RealmResults<UserInfoJSON> result2 = realm.where(UserInfoJSON.class).findAll();
        LoginReceive loginReceive = (new Gson()).fromJson(result2.get(0).getUserInfo(), LoginReceive.class);

        String token = loginReceive.getToken();

        MessageStatusRequest messageStatusRequest = new MessageStatusRequest();
        messageStatusRequest.setToken(token);
        messageStatusRequest.setModule(module);
        messageStatusRequest.setMessageId(messageID);
        messageStatusRequest.setMessageType(messageType);

        staticPresenter.onRequestMessageStatus(messageStatusRequest);
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

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    public void onMessageStatusReceive(MessageStatusReceive obj) {

        Boolean status = MainController.getRequestStatus(obj.getStatus(), "", getActivity());
        if (status) {
            Log.e("Update Message", "SUCCESS");
        } else {
            Log.e("Update Message", "FAIL");
        }

    }

}