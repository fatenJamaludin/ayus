/*
package com.app.tbd.ui.Model.Adapter;

import android.app.Activity;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.tbd.R;
import com.app.tbd.application.AnalyticsApplication;
import com.app.tbd.ui.Activity.PushNotificationInbox.NotificationInboxList;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.facebook.FacebookSdk.getApplicationContext;

public class PushNotificationAdapter extends BaseAdapter {

    private final Activity context;
    private final List<NotificationInboxList> obj;
    private Animation animationUp;
    private Animation animationDown;

    public PushNotificationAdapter(Activity context, List<NotificationInboxList> obj) {
        this.context = context;
        this.obj = obj;
    }

    @Override
    public int getCount() {
        return obj == null ? 0 : obj.size();
    }

    @Override
    public Object getItem(int position) {
        */
/*return notification == null ? null : notification.get(position);*//*

        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    static class ViewHolder {
        @InjectView(R.id.notification_date)
        TextView notification_date;

        @InjectView(R.id.notification_message)
        TextView notification_message;

        @InjectView(R.id.notification_body)
        TextView notification_body;

        @InjectView(R.id.notification_icon)
        ImageView notification_icon;

    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        */
/*Log.e("Invalidate","True");*//*

        final ViewHolder vh;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.push_notification_item, parent, false);
            vh = new ViewHolder();
            ButterKnife.inject(vh, view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }

        Log.e("TITLE NOTIFICATION", obj.get(position).getTitle());

        vh.notification_message.setText(obj.get(position).getTitle());
        vh.notification_body.setText(obj.get(position).getMessage());

        String newDate = obj.get(position).getDatetime().substring(0,10);
        Log.e("Date cut", changeDate(newDate));

        vh.notification_date.setText(changeDate(newDate));

         */
/*(push_notification_item)*//*

        vh.notification_body.setVisibility(View.GONE);
        vh.notification_message.setTypeface(null, Typeface.BOLD);
        vh.notification_date.setTypeface(null, Typeface.BOLD);
        vh.notification_date.setTextColor(AnalyticsApplication.getContext().getResources().getColor(R.color.bright_red));

        animationUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        animationDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);

        vh.notification_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vh.notification_body.isShown()){
                    //open
                    vh.notification_body.startAnimation(animationUp);
                    vh.notification_body.setVisibility(View.GONE);
                    vh.notification_message.setTypeface(null, Typeface.BOLD);
                    vh.notification_date.setTypeface(null, Typeface.BOLD);
                    vh.notification_date.setTextColor(AnalyticsApplication.getContext().getResources().getColor(R.color.bright_red));
                    vh.notification_message.setMaxLines(1);
                    vh.notification_icon.setImageDrawable(AnalyticsApplication.getContext().getResources().getDrawable(R.drawable.ic_red_down));

                }
                else{
                    //close
                    vh.notification_body.startAnimation(animationDown);
                    vh.notification_body.setVisibility(View.VISIBLE);
                    vh.notification_date.setTypeface(null,Typeface.NORMAL);
                    vh.notification_message.setTypeface(null,Typeface.NORMAL);
                    vh.notification_date.setTextColor(AnalyticsApplication.getContext().getResources().getColor(R.color.black));
                    vh.notification_message.setMaxLines(Integer.MAX_VALUE);
                    vh.notification_icon.setImageDrawable(AnalyticsApplication.getContext().getResources().getDrawable(R.drawable.ic_red_up));
                }

            }

        });



        return view;
    }

    public String changeDate(String dateString){

        DateFormat fromFormat = new SimpleDateFormat("dd/MM/yyyy");
        fromFormat.setLenient(false);
        DateFormat toFormat = new SimpleDateFormat("dd MMM yyyy");
        toFormat.setLenient(false);
        */
/*String dateStr = "2011-07-09";*//*

        String dateStr = dateString;
        Date date = null;
        try {
            date = fromFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(toFormat.format(date));

        return toFormat.format(date);
    }
}
*/
