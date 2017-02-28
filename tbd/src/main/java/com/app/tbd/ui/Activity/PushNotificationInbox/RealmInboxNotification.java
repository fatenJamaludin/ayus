package com.app.tbd.ui.Activity.PushNotificationInbox;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.app.tbd.ui.Activity.Profile.ProfileFragment;
import com.app.tbd.ui.Model.JSON.FlightInProgressJSON;
import com.app.tbd.ui.Model.JSON.GCMClass;
import com.app.tbd.ui.Model.Request.NotificationMessage;
import com.app.tbd.ui.Realm.Cached.CachedResult;
import com.app.tbd.ui.Realm.RealmObjectController;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.exceptions.RealmMigrationNeededException;

import static com.app.tbd.ui.Realm.RealmObjectController.getRealmInstance;

/**
 * Created by Dell on 1/23/2017.
 */

public class RealmInboxNotification {

    //add notification messages into notifications box
    public static void notificationAddList(Context act, final GCMClass obj, final String username) {

        final Realm realm = RealmObjectController.getRealmInstanceContext(act);
        realm.beginTransaction();
        NotificationInboxList realmObject = realm.createObject(NotificationInboxList.class);
        realmObject.setUsername(username);
        realmObject.setMessage(obj.getMessage());
        realmObject.setMessageID(obj.getMessageID());
        realmObject.setTitle(obj.getTitle());
        realmObject.setDatetime(obj.getDate());
        realmObject.setStatus("Unread");
        realm.commitTransaction();
        realm.close();

        //getSize(act,username);
    }

    //add overlay message into notifications box
    public static void notificationAddListOverlay(Context act, final GCMClass obj) {

        final Realm realm = RealmObjectController.getRealmInstanceContext(act);
        realm.beginTransaction();

        NotificationInboxList inbox = realm.where(NotificationInboxList.class).equalTo("username", "overlay").findFirst();

        // This query is fast because "datetime" is an indexed field
        if (inbox == null) {
            Log.e("Message update null", "Record not exist");

            NotificationInboxList realmObject = realm.createObject(NotificationInboxList.class);
            realmObject.setUsername("overlay");
            realmObject.setMessage(obj.getMessage());
            realmObject.setTitle(obj.getTitle());
            realmObject.setMessageID(obj.getMessageID());
            realmObject.setDatetime(obj.getDate());
            realmObject.setStatus("Unread");

        } else {
            inbox.setMessage(obj.getMessage());
            inbox.setTitle(obj.getTitle());
            inbox.setDatetime(obj.getDate());
            inbox.setMessageID(obj.getMessageID());
        }

        realm.commitTransaction();
        realm.close();

    }

    //add all messages into notifications box
    public static void notificationAddAllMessages(Context act, final GCMClass obj) {

        final Realm realm = RealmObjectController.getRealmInstanceContext(act);
        realm.beginTransaction();

        NotificationInboxList realmObject = realm.createObject(NotificationInboxList.class);

        realmObject.setUsername("");
        realmObject.setMessage(obj.getMessage());
        realmObject.setTitle(obj.getTitle());
        realmObject.setDatetime(obj.getDate());
        realmObject.setStatus(obj.getStatus());
        realmObject.setMessageID(obj.getMessageID());
        realmObject.setBody(obj.getBody());
        realmObject.setMessageType(obj.getType());
        realmObject.setId(obj.getId());

        realm.commitTransaction();
        realm.close();

    }

   /* public static void getSize(Context act,String username){

        final Realm realm = RealmObjectController.getRealmInstanceContext(act);
        realm.beginTransaction();
        RealmResults<NotificationInboxList> inbox = realm.where(NotificationInboxList.class).equalTo("username", username).findAll();
        Log.e("Size", Integer.toString(inbox.size()));
        realm.commitTransaction();

        ProfileFragment.triggerInbox(act);

    }*/

    public static void clearNotificationInbox(Activity act) {

        Realm realm = getRealmInstance(act);

        final RealmResults<NotificationInboxList> result = realm.where(NotificationInboxList.class).findAll();
        realm.beginTransaction();
        result.clear();
        realm.commitTransaction();
        realm.close();

    }

    //hold
    /*public static boolean notificationList(Context act, final GCMClass obj, final String username) {

        final Realm realm = RealmObjectController.getRealmInstanceContext(act);

        // Query and update the result asynchronously in another thread
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {


                NotificationInboxList realmObject = realm.createObject(NotificationInboxList.class);
                realmObject.setUsername(username);
                realmObject.setMessage(obj.getBody());
                realmObject.setTitle(obj.getTitle());
                realmObject.setDatetime("09/12/2017 10.30 AM");
                realmObject.setStatus("Y");

                *//*if (inbox.size() == 0) {

                    //insert message into inbox here
                    //first time insert .. all status Y
                    Log.e("Insert", "OK");
                    NotificationInboxList realmObject = realm.createObject(NotificationInboxList.class);
                    realmObject.setUsername(username);
                    realmObject.setMessage(obj.getBody());
                    realmObject.setTitle(obj.getTitle());
                    realmObject.setDatetime("09/12/2017 10.30 AM");
                    realmObject.setStatus("Y");
                    //realmObject.getInbox().add(notificationBox);

                } else {

                    //update inbox message
                    Log.e("Update", "OK");
                    NotificationBox notificationBox = new NotificationBox();
                    notificationBox.setMessage(obj.getBody());
                    notificationBox.setTitle(obj.getTitle());
                    notificationBox.setDatetime("10/12/2017 10.30 AM");
                    notificationBox.setStatus("Y");
                    //inbox.get(0).getInbox().add(notificationBox);

                }*//*
            }
        }, new Realm.Transaction.Callback() {
            @Override
            public void onSuccess() {
                Log.e("Query", "Success");
                RealmResults<NotificationInboxList> inbox = realm.where(NotificationInboxList.class).equalTo("username", username).findAll();
                Log.e("Size", Integer.toString(inbox.size()));

            }

            @Override
            public void onError(Exception e) {
                // transaction is automatically rolled-back, do any cleanup here
                Log.e("Exception", e.getMessage());
            }

        });

        return true;
    }*/

    public static void notificationStatusUpdate(Context act,String username, String id) {

        final Realm realm = RealmObjectController.getRealmInstanceContext(act);
        realm.beginTransaction();
        NotificationInboxList inbox = realm.where(NotificationInboxList.class).equalTo("username", username).equalTo("id", id).findFirst();

        // This query is fast because "datetime" is an indexed field
        if (inbox == null) {
            Log.e("Message update null", "Record not exist");
        } else {
            inbox.setStatus("3");
        }
        realm.commitTransaction();
        realm.close();
    }

    public static boolean deleteNotification(final Activity act, final String username, final String id) {

        final Realm realm = getRealmInstance(act);

        // Query and update the result asynchronously in another thread
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                RealmResults<NotificationInboxList> deleteMessage = realm.where(NotificationInboxList.class).equalTo("username", username).equalTo("id", id).findAll();
                if (deleteMessage.size() > 0) {
                    deleteMessage.clear();
                }

            }
        }, new Realm.Transaction.Callback() {
            @Override
            public void onSuccess() {

                RealmResults<NotificationInboxList> checkSize = realm.where(NotificationInboxList.class).equalTo("username", username).findAll();

                Log.e("Query", "Success");
                Log.e("Size", Integer.toString(checkSize.size()));
            }

            @Override
            public void onError(Exception e) {
                // transaction is automatically rolled-back, do any cleanup here
                Log.e("Exception", e.getMessage());
            }

        });

        return true;
    }
}
