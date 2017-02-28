package com.app.tbd.application;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.app.tbd.GCMIntentService;
import com.app.tbd.R;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Activity.BookingFlight.PushNotificationActivity;
import com.app.tbd.ui.Activity.Profile.UserProfile.EditProfileActivity;
import com.app.tbd.ui.Activity.PushNotificationInbox.RealmInboxNotification;
import com.app.tbd.ui.Activity.SplashScreen.SplashScreenActivity;
import com.app.tbd.ui.Activity.TAB.TabActivity;
import com.app.tbd.ui.Model.JSON.GCMClass;
import com.app.tbd.ui.Realm.RealmObjectController;
import com.app.tbd.utils.Utils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }


        final String message = remoteMessage.getData().get("message");
        final String title = remoteMessage.getData().get("title");
        final String key = remoteMessage.getData().get("key");
        final String messageID = remoteMessage.getData().get("messageId");

        Log.e("messageId", "messageId" + messageID);

        final Context context = this;
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                setPushNotificationAlert(context, message, title, key);
            }
        });

        //sendNotification(message, title, messageID);

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.

        //save message to realm object

    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received FCM message.
     */


    public void setPushNotificationAlert(final Context act, String message, String title, final String key) {

        LayoutInflater li = LayoutInflater.from(act);
        final View myView = li.inflate(R.layout.push_notification_alert_v2, null);
        Button show = (Button) myView.findViewById(R.id.btnShow);
        Button cont = (Button) myView.findViewById(R.id.btnClose);

        TextView pushTitle = (TextView) myView.findViewById(R.id.push_content);
        TextView pushMessage = (TextView) myView.findViewById(R.id.push_title);

        pushTitle.setText(message);
        pushMessage.setText(title);

        if (key.equals("n/a")) {
            Log.e("NO KEY", "A: " + key);
            RealmObjectController.clearKey(act);
        } else {
            RealmObjectController.saveKey(act, key);
            Log.e("KEY", "A: " + key);
        }

        final Dialog dialog = new Dialog(act, R.style.DialogThemePush);
        dialog.setContentView(myView);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#80000000")));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //lp.height = 570;
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
        //LayoutParams.TYPE_TOAST or TYPE_APPLICATION_PANEL
        dialog.show();
        //Unable to add window -- token null is not valid; is your activity running?

        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }

        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (key.equals("n/a")) {
                    TabActivity.setPager(1);
                    Intent notificationPage = new Intent(act, com.app.tbd.ui.Activity.PushNotificationInbox.PushNotificationActivity.class);
                    notificationPage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    act.startActivity(notificationPage);
                    Log.e("1", "1");
                } else {
                    Log.e("2", "2");
                    BaseFragment.deepLinkContext(act);
                }
                dialog.dismiss();


            }

        });
    }


    private void sendNotification(String messageBody, String messageTitle, String messageId) {

        String dateString = getCurrentTimeStamp();
        saveNotificationInbox("-", messageBody, messageTitle, dateString, messageId);

        //save message to realm object
        RealmObjectController.clearNotificationMessage(this);
        RealmObjectController.saveNotificationMessage(this, messageBody, messageTitle);


       /* if (key == null){
            Log.e("K1","1");
            RealmObjectController.clearKey(this);
        }else {
            Log.e("K1","2");
            RealmObjectController.saveKey(this, key);
        }*/

        /*if (!key.equals("")) {
            Log.e("K1","1");
            RealmObjectController.saveKey(this, key);
        } else {
            Log.e("K1","2");
            RealmObjectController.clearKey(this);
        }*/

        Intent intent = new Intent(this, PushNotificationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.test_icon)
                .setContentTitle(messageTitle)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    public void saveNotificationInbox(String username, String message, String title, String date, String messageId) {

        //save push notification message
        GCMClass gcmClass = new GCMClass();
        gcmClass.setTitle(title);
        gcmClass.setMessage(message);
        gcmClass.setDate(date);
        gcmClass.setMessageID(messageId);

        RealmInboxNotification.notificationAddList(this, gcmClass, "-");

    }

    public static String getCurrentTimeStamp() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }
}
