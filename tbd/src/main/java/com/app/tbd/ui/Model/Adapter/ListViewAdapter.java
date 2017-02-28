package com.app.tbd.ui.Model.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.tbd.R;
import com.app.tbd.application.AnalyticsApplication;
import com.app.tbd.ui.Activity.PushNotificationInbox.PushNotificationFragment;
import com.app.tbd.ui.Activity.PushNotificationInbox.RealmInboxNotification;
import com.app.tbd.ui.Model.JSON.NotificationInfo;
import com.app.tbd.utils.SharedPrefManager;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import java.util.List;

public class ListViewAdapter extends BaseSwipeAdapter {

    private Context mContext;
    private SharedPrefManager pref;
    private final Activity context;
    private final List<NotificationInfo> obj;

    public ListViewAdapter(Context mContext, Activity context,List<NotificationInfo> obj) {
        this.mContext = mContext;
        this.context = context;
        this.obj = obj;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public View generateView(final int position, ViewGroup parent) {
        pref = new SharedPrefManager(context);

        View v = LayoutInflater.from(mContext).inflate(R.layout.listview_item, null);
        final SwipeLayout swipeLayout = (SwipeLayout)v.findViewById(getSwipeLayoutResourceId(position));
        final LinearLayout notification_body = (LinearLayout)v.findViewById(R.id.notification_body);
        final TextView notification_message = (TextView)v.findViewById(R.id.notification_message);
        final TextView notification_date = (TextView)v.findViewById(R.id.notification_date);
        final ImageView notification_icon = (ImageView)v.findViewById(R.id.notification_icon);


        notification_body.setVisibility(View.GONE);
        notification_date.setTextColor(AnalyticsApplication.getContext().getResources().getColor(R.color.bright_red));

        Log.e("Status",obj.get(position).getStatus());
        if(obj.get(position).getStatus().equals("2") || obj.get(position).getStatus().equals("Unread")){ //2
            notification_message.setTypeface(null, Typeface.BOLD);
            notification_date.setTypeface(null, Typeface.BOLD);
        }else if(obj.get(position).getStatus().equals("3") || obj.get(position).getStatus().equals("Read")){
            notification_date.setTypeface(null,Typeface.NORMAL);
            notification_message.setTypeface(null,Typeface.NORMAL);
            notification_date.setTextColor(AnalyticsApplication.getContext().getResources().getColor(R.color.black));
        }

        swipeLayout.setDragEdges(SwipeLayout.DragEdge.Left);
        swipeLayout.setBottomViewIds(R.id.bottom, SwipeLayout.EMPTY_LAYOUT, SwipeLayout.EMPTY_LAYOUT, SwipeLayout.EMPTY_LAYOUT);
        swipeLayout.findViewById(R.id.trash).setTag(position);

        swipeLayout.findViewById(R.id.trash).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,AnalyticsApplication.getContext().getString(R.string.notification_delete), Toast.LENGTH_SHORT).show();
                RealmInboxNotification.deleteNotification(context, obj.get(position).getUsername(), obj.get(position).getId());
                PushNotificationFragment.updateMessage(context, obj.get(position).getMessageID(), "Delete",obj.get(position).getType());
                obj.remove(position);
                notifyDataSetChanged();

                notification_message.setTypeface(null, Typeface.BOLD);
                notification_date.setTypeface(null, Typeface.BOLD);
                notification_body.setVisibility(View.GONE);
                notification_message.setMaxLines(1);
                notification_date.setTextColor(AnalyticsApplication.getContext().getResources().getColor(R.color.bright_red));
                notification_icon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_red_down));
                swipeLayout.close();

                if (obj.size()== 0){
                    context.finish();
                }
            }
        });

        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
            }
        });

        swipeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isOpen(position) ){
                    // Handle Clicks
                    someFunction(position,notification_body,notification_message,notification_date,notification_icon);
                    PushNotificationFragment.updateMessage(context, obj.get(position).getMessageID(), "Read",obj.get(position).getType());
                }
            }
        });

        swipeLayout.findViewById(R.id.notification_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("HAI","SUCCESS");

                someFunction(position,notification_body,notification_message,notification_date,notification_icon);
                Log.e("Type", "Type :" + obj.get(position).getType());
                PushNotificationFragment.updateMessage(context, obj.get(position).getMessageID(), "Read",obj.get(position).getType());

            }
        });

        return v;
    }

    @Override
    public void fillValues(int position, View convertView) {

        TextView date = (TextView)convertView.findViewById(R.id.notification_date);
        TextView title = (TextView)convertView.findViewById(R.id.notification_message);
        TextView textMessage = (TextView) convertView.findViewById(R.id.message);

        title.setText(obj.get(position).getTitle());
        textMessage.setText(obj.get(position).getMessage());

        String newDate = obj.get(position).getDatetime().substring(0,11).toUpperCase();
        Log.e("Date Realm Cut", newDate);

        date.setText(newDate);

    }

    @Override
    public int getCount() {
        return obj == null ? 0 : obj.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void someFunction(int position,LinearLayout notification_body,TextView notification_message,TextView notification_date,ImageView notification_icon){
        if(notification_body.isShown()){

            //close
            notification_body.setVisibility(View.GONE);
            notification_message.setMaxLines(1);
            notification_icon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_red_down));
            Log.e("STATUS CLOSE", obj.get(position).getStatus());

        } else{

            //open
            notification_body.setVisibility(View.VISIBLE);
            notification_date.setTypeface(null,Typeface.NORMAL);
            notification_message.setTypeface(null,Typeface.NORMAL);
            notification_date.setTextColor(AnalyticsApplication.getContext().getResources().getColor(R.color.black));
            notification_message.setMaxLines(Integer.MAX_VALUE);
            notification_icon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_red_up));
            RealmInboxNotification.notificationStatusUpdate(mContext, obj.get(position).getUsername(), obj.get(position).getId());
            Log.e("STATUS OPEN", obj.get(position).getStatus());
        }
    }

}
