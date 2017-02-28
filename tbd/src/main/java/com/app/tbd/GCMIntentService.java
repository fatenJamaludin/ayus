package com.app.tbd;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.app.tbd.ui.Activity.SplashScreen.Pop2NotificationActivity;
import com.app.tbd.ui.Realm.RealmObjectController;
import com.google.android.gcm.GCMBaseIntentService;
import com.google.gson.Gson;

import static com.app.tbd.ui.Activity.PushNotification.CommonUtilities.SENDER_ID;
import static com.app.tbd.ui.Activity.PushNotification.CommonUtilities.displayMessage;

public class GCMIntentService extends GCMBaseIntentService {

	private static final String TAG = "GCMIntentService";

    public GCMIntentService() {
        super(SENDER_ID);
    }

    /**
     * Method called on device registered
     **/
    @Override
    protected void onRegistered(Context context, String registrationId) {
        Log.i(TAG, "Device registered: regId = " + registrationId);
        Log.e("Registering", "tRUE");
        displayMessage(context, "Your device registred with GCM");
        //ServerUtilities.register(context, MainActivity.name, MainActivity.email, registrationId);

        //back to splash screen
        //BaseFragment.splashScreen(context,registrationId);
        //SplashScreenFragment.splash(context,registrationId);

    }

    /**
     * Method called on device un registred
     * */
    @Override
    protected void onUnregistered(Context context, String registrationId) {
        Log.i(TAG, "Device unregistered");
        displayMessage(context, "Device Unregistered");
        //ServerUtilities.unregister(context, registrationId);
    }

    /**
     * Method called on Receiving a new message
     * */
    @Override
    protected void onMessage(Context context, Intent intent) {
        Log.i(TAG, "Received message");
        Log.e("intent", intent.getClass().getCanonicalName());
        String message = intent.getExtras().getString("message");
        
        displayMessage(context, message);
        // notifies user
        generateNotification(context, message);
        //BaseFragmentActivity.setAppStatus(true);
       // Log.e("CheckAppStatus", Boolean.toString(BaseFragmentActivity.getAppStatus()));

    }

    /**
     * Method called on receiving a deleted message
     * */
    @Override
    protected void onDeletedMessages(Context context, int total) {
        Log.i(TAG, "Received deleted messages notification");
        String message = "Register";
        displayMessage(context, message);
        // notifies user
        generateNotification(context, message);
    }

    /**
     * Method called on Error
     * */
    @Override
    public void onError(Context context, String errorId) {
        Log.i(TAG, "Received error: " + errorId);
        Log.e("Registering", "NOT REGISTER");

        displayMessage(context, "Register");
    }

    @Override
    protected boolean onRecoverableError(Context context, String errorId) {
        // log message
        Log.i(TAG, "Received recoverable error: " + errorId);
        displayMessage(context, getString(R.string.register_title,errorId));
        return super.onRecoverableError(context, errorId);
    }

    /**
     * Issues a notification to inform the user that server has sent a message.
     */

    public class GCMClass {

        private String title;
        private String body;

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    private void generateNotification(Context context,String notificationMessage) {

        int requestID = (int) System.currentTimeMillis();

        Gson gson = new Gson();
        GCMClass obj = gson.fromJson(notificationMessage, GCMClass.class);

        Intent notificationIntent = new Intent(context, Pop2NotificationActivity.class);
        notificationIntent.setAction(Intent.ACTION_MAIN);

        //notificationIntent.setFlags(Intent.FLAG_ONE_SHOT);
        Log.e("Visibile","true");

        //notificationIntent.setAction("android.intent.action.MAIN");
        //notificationIntent.addCategory("android.intent.category.LAUNCHER");
        //notificationIntent.putExtra("MESSAGE", notificationMessage);

        //save message to realm object
        RealmObjectController.clearNotificationMessage(context);
        RealmObjectController.saveNotificationMessage(context,obj.getBody(),obj.getTitle());

        PendingIntent contentIntent = PendingIntent.getActivity(this, requestID,notificationIntent, PendingIntent.FLAG_ONE_SHOT);


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setAutoCancel(true).setDefaults(Notification.DEFAULT_ALL);
        mBuilder
                .setContentText(obj.getBody())
                .setContentTitle(String.format(obj.getTitle()))
                .setSmallIcon(R.drawable.test_icon)
                .setColor(Color.argb(0x55, 0x00, 0x00, 0xff))
                .setTicker(String.format(obj.getTitle()));

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder.setContentIntent(contentIntent);

        notificationManager.notify(1, mBuilder.build());

    }


    private void generateNotification2(Context context, String message) {

        Gson gson = new Gson();
        GCMClass obj = gson.fromJson(message, GCMClass.class);

        //convert string to json
        Intent viewIntent = new Intent(context, Pop2NotificationActivity.class);
        Log.e("Visibile","true");
        viewIntent.setAction("android.intent.action.MAIN");
        viewIntent.addCategory("android.intent.category.LAUNCHER");
        viewIntent.putExtra("MESSAGE", message);


        PendingIntent viewPendingIntent = PendingIntent.getActivity(this, 0, viewIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
        notificationBuilder.setAutoCancel(true).setDefaults(Notification.DEFAULT_ALL);
        notificationBuilder
                .setContentText(obj.getBody())
                .setContentTitle(String.format(obj.getTitle()))
                .setSmallIcon(R.drawable.logo_tbd)
                .setColor(Color.argb(0x55, 0x00, 0x00, 0xff))
                .setTicker(String.format(obj.getTitle()));

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationBuilder.setContentIntent(viewPendingIntent);
        notificationManager.notify(1, notificationBuilder.build());

    }


}
