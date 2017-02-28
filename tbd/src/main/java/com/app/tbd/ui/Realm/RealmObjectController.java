package com.app.tbd.ui.Realm;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.app.tbd.ui.Activity.Profile.ProfileFragment;
import com.app.tbd.ui.Activity.PushNotificationInbox.NotificationInboxList;
import com.app.tbd.ui.Model.JSON.AddonCached;
import com.app.tbd.ui.Model.JSON.FlightInProgressJSON;
import com.app.tbd.ui.Model.JSON.FreeItem;
import com.app.tbd.ui.Model.JSON.GCMClass;
import com.app.tbd.ui.Model.JSON.OverlayInfoGSON;
import com.app.tbd.ui.Model.JSON.PromotionCache;
import com.app.tbd.ui.Model.JSON.PushNotificationKey;
import com.app.tbd.ui.Model.JSON.RecentSearch;
import com.app.tbd.ui.Model.JSON.RecentSearchArrival;
import com.app.tbd.ui.Model.JSON.SeatCached;
import com.app.tbd.ui.Model.JSON.SelectedSeatInfoGSON;
import com.app.tbd.ui.Model.JSON.TravellerCached;
import com.app.tbd.ui.Model.JSON.UserInfoJSON;
import com.app.tbd.ui.Model.JSON.bookingInfoJSON;
import com.app.tbd.ui.Model.JSON.countryLanguageJSON;
import com.app.tbd.ui.Model.Receive.FlightSummaryReceive;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Model.Receive.TBD.BigPointReceive;
import com.app.tbd.ui.Model.Request.NotificationMessage;
import com.app.tbd.ui.Model.Request.RealmFlightObj;
import com.app.tbd.ui.Realm.Cached.CachedBigPointResult;
import com.app.tbd.ui.Realm.Cached.CachedLanguageCountry;
import com.app.tbd.ui.Realm.Cached.CachedResult;
import com.google.gson.Gson;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.exceptions.RealmMigrationNeededException;

/**
 * Created by Dell on 2/11/2016.
 */
public class RealmObjectController extends BaseFragment {


    public static Realm getRealmInstance(Activity act) {

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(act).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfiguration);

        try {
            return Realm.getInstance(realmConfiguration);
        } catch (RealmMigrationNeededException e) {
            try {
                Realm.deleteRealm(realmConfiguration);
                //Realm file has been deleted.
                return Realm.getInstance(realmConfiguration);
            } catch (Exception ex) {
                throw ex;
                //No Realm file to remove.
            }
        }
    }

    public static Realm getRealmInstanceContext(Context act) {

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(act).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfiguration);

        try {
            return Realm.getInstance(realmConfiguration);
        } catch (RealmMigrationNeededException e) {
            try {
                Realm.deleteRealm(realmConfiguration);
                //Realm file has been deleted.
                return Realm.getInstance(realmConfiguration);
            } catch (Exception ex) {
                throw ex;
                //No Realm file to remove.
            }
        }
    }

    public static RealmResults<NotificationMessage> getNotificationMessage(Activity act) {

        Realm realm = getRealmInstance(act);
        final RealmResults<NotificationMessage> result = realm.where(NotificationMessage.class).findAll();

        return result;
    }

    public static void clearLogout(Activity act) {

        Realm realm = getRealmInstanceContext(act);

        final RealmResults<UserInfoJSON> result = realm.where(UserInfoJSON.class).findAll();
        realm.beginTransaction();
        result.clear();
        realm.commitTransaction();

    }

    public static void clearNotificationMessage(Context act) {

        Realm realm = getRealmInstanceContext(act);

        final RealmResults<NotificationMessage> result = realm.where(NotificationMessage.class).findAll();
        realm.beginTransaction();
        result.clear();
        realm.commitTransaction();
        realm.close();

    }


    /*Save user info*/
    public static void saveFlightSearchInfo(Activity act, String searchRequest, String searchReceive, String selectReceive, String selectedSegment, String total) {

        Realm realm = getRealmInstance(act);

        //clear user info in realm first.
        final RealmResults<FlightInProgressJSON> result = realm.where(FlightInProgressJSON.class).findAll();
        realm.beginTransaction();
        result.clear();
        realm.commitTransaction();

        //add new user info in realm
        realm.beginTransaction();
        FlightInProgressJSON realmObject = realm.createObject(FlightInProgressJSON.class);
        realmObject.setSearchFlightReceive(searchReceive);
        realmObject.setSearchFlightRequest(searchRequest);
        realmObject.setSelectReceive(selectReceive);
        realmObject.setSelectedSegment(selectedSegment);
        realmObject.setTotal(total);

        realm.commitTransaction();
        realm.close();
    }


    /*Save user info*/
    public static void saveFlightFreeInfo(Activity act, String freeInfo) {

        Realm realm = getRealmInstance(act);

        //clear user info in realm first.
        final RealmResults<FreeItem> result = realm.where(FreeItem.class).findAll();
        realm.beginTransaction();
        result.clear();
        realm.commitTransaction();

        //add new user info in realm
        realm.beginTransaction();
        FreeItem realmObject = realm.createObject(FreeItem.class);
        realmObject.setFreeInfo(freeInfo);
        realm.commitTransaction();
        realm.close();
    }

    /*Save user info*/
    public static void saveSelectedSeatInfo(Activity act, String selectedSeatInfo) {

        Realm realm = getRealmInstance(act);

        //clear user info in realm first.
        final RealmResults<SelectedSeatInfoGSON> result = realm.where(SelectedSeatInfoGSON.class).findAll();
        realm.beginTransaction();
        result.clear();
        realm.commitTransaction();

        //add new user info in realm
        realm.beginTransaction();
        SelectedSeatInfoGSON realmObject = realm.createObject(SelectedSeatInfoGSON.class);
        realmObject.setSelectedSeat(selectedSeatInfo);
        realm.commitTransaction();
        realm.close();
    }


    /*Save user info*/
    public static void saveCountryLanguage(Activity act, String countryLanguage) {

        Realm realm = getRealmInstance(act);

        //clear user info in realm first.
        final RealmResults<countryLanguageJSON> result = realm.where(countryLanguageJSON.class).findAll();
        realm.beginTransaction();
        result.clear();
        realm.commitTransaction();

        //add new user info in realm
        realm.beginTransaction();
        countryLanguageJSON realmObject = realm.createObject(countryLanguageJSON.class);
        realmObject.setCountryLanguageReceive(countryLanguage);

        realm.commitTransaction();
        realm.close();
    }

    public static void clearAllRealmFileRelatedWithBooking(Activity act) {

        Realm realm = getRealmInstance(act);
        final RealmResults<SelectedSeatInfoGSON> result = realm.where(SelectedSeatInfoGSON.class).findAll();
        realm.beginTransaction();
        result.clear();
        realm.commitTransaction();
        realm.close();

    }

    /*Save user info*/
    /*public static void saveAddBookingInfo(Activity act, String bookingInfo) {

        Realm realm = getRealmInstance(act);

        //clear user info in realm first.
        final RealmResults<bookingInfoJSON> result = realm.where(bookingInfoJSON.class).findAll();
        realm.beginTransaction();
        result.clear();
        realm.commitTransaction();

        //add new user info in realm
        realm.beginTransaction();
        bookingInfoJSON realmObject = realm.createObject(bookingInfoJSON.class);
        realmObject.setBookingInfo(bookingInfo);

        realm.commitTransaction();
        realm.close();
    }*/

    /*Save user info*/
    public static void savePromotion(Activity act, String stringfyObj) {

        Realm realm = getRealmInstance(act);

        //clear user info in realm first.
        final RealmResults<PromotionCache> result = realm.where(PromotionCache.class).findAll();
        realm.beginTransaction();
        result.clear();
        realm.commitTransaction();

        //add new user info in realm
        realm.beginTransaction();
        PromotionCache realmObject = realm.createObject(PromotionCache.class);
        realmObject.setPromotion(stringfyObj);
        realm.commitTransaction();
        realm.close();
    }

    /*Save user info*/
    public static void saveTravellerCache(Activity act, String stringfyObj) {

        Realm realm = getRealmInstance(act);

        //clear user info in realm first.
        final RealmResults<TravellerCached> result = realm.where(TravellerCached.class).findAll();
        realm.beginTransaction();
        result.clear();
        realm.commitTransaction();

        //add new user info in realm
        realm.beginTransaction();
        TravellerCached realmObject = realm.createObject(TravellerCached.class);
        realmObject.setTraveller(stringfyObj);
        realm.commitTransaction();
        realm.close();
    }

    public static void saveRecentSearch(Activity act, String stringfyObj) {

        Realm realm = getRealmInstance(act);

        //clear user info in realm first.
        final RealmResults<RecentSearch> result = realm.where(RecentSearch.class).findAll();
        realm.beginTransaction();
        result.clear();
        realm.commitTransaction();

        //add new user info in realm
        realm.beginTransaction();
        RecentSearch realmObject = realm.createObject(RecentSearch.class);
        realmObject.setRecentSearch(stringfyObj);
        realm.commitTransaction();
        realm.close();
    }

    public static void clearRecentSearch(Activity act) {

        Realm realm = getRealmInstance(act);

        //clear user info in realm first.
        final RealmResults<RecentSearch> result = realm.where(RecentSearch.class).findAll();
        realm.beginTransaction();
        result.clear();
        realm.commitTransaction();
        realm.close();

    }

    public static void saveRecentSearchArrival(Activity act, String stringfyObj) {

        Realm realm = getRealmInstance(act);

        //clear user info in realm first.
        final RealmResults<RecentSearchArrival> result = realm.where(RecentSearchArrival.class).findAll();
        realm.beginTransaction();
        result.clear();
        realm.commitTransaction();

        //add new user info in realm
        realm.beginTransaction();
        RecentSearchArrival realmObject = realm.createObject(RecentSearchArrival.class);
        realmObject.setRecentSearch(stringfyObj);
        realm.commitTransaction();
        realm.close();
    }


    /*Save user info*/
    public static void saveAddOnInfo(Activity act, String stringfyObj) {

        Realm realm = getRealmInstance(act);

        //clear user info in realm first.
        final RealmResults<AddonCached> result = realm.where(AddonCached.class).findAll();
        realm.beginTransaction();
        result.clear();
        realm.commitTransaction();

        //add new user info in realm
        realm.beginTransaction();
        AddonCached realmObject = realm.createObject(AddonCached.class);
        realmObject.setAddonInfo(stringfyObj);
        realm.commitTransaction();
        realm.close();
    }


    /*Save user info*/
    public static void saveSeatCache(Activity act, String stringfyObj) {

        Realm realm = getRealmInstance(act);

        //clear user info in realm first.
        final RealmResults<SeatCached> result = realm.where(SeatCached.class).findAll();
        realm.beginTransaction();
        result.clear();
        realm.commitTransaction();

        //add new user info in realm
        realm.beginTransaction();
        SeatCached realmObject = realm.createObject(SeatCached.class);
        realmObject.setSeatCached(stringfyObj);
        realm.commitTransaction();
        realm.close();
    }

    /*Save user info*/
    public static void saveUserInformation(Context act, String stringfyObj) {

        Realm realm = getRealmInstanceContext(act);

        //clear user info in realm first.
        final RealmResults<UserInfoJSON> result = realm.where(UserInfoJSON.class).findAll();
        realm.beginTransaction();
        result.clear();
        realm.commitTransaction();

        //add new user info in realm
        realm.beginTransaction();
        UserInfoJSON realmObject = realm.createObject(UserInfoJSON.class);
        realmObject.setUserInfo(stringfyObj);
        realm.commitTransaction();
        realm.close();
    }


    /*Save overlayinfo*/
    public static void saveOverlayInfo(Context act, String stringfyObj) {

        Realm realm = getRealmInstanceContext(act);

        //clear user info in realm first.
        final RealmResults<OverlayInfoGSON> result = realm.where(OverlayInfoGSON.class).findAll();
        realm.beginTransaction();
        result.clear();
        realm.commitTransaction();


        //add new user info in realm
        realm.beginTransaction();
        OverlayInfoGSON realmObject = realm.createObject(OverlayInfoGSON.class);
        realmObject.setOverlayInfo(stringfyObj);
        realm.commitTransaction();
        realm.close();
    }


    public static void saveNotificationMessage(Context act, String message, String title) {

        //trigger inbox number

        Realm realm = getRealmInstanceContext(act);

        final RealmResults<NotificationMessage> result = realm.where(NotificationMessage.class).findAll();
        realm.beginTransaction();
        result.clear();
        realm.commitTransaction();

        realm.beginTransaction();
        NotificationMessage realmObject = realm.createObject(NotificationMessage.class);
        realmObject.setMessage(message);
        realmObject.setTitle(title);
        realm.commitTransaction();
        realm.close();

        try {
            ProfileFragment.triggerInbox(act);
        }catch (Exception e){

        }

    }

    public static void saveKey(Context act, String key) {

        Realm realm = getRealmInstanceContext(act);
        //clear user info in realm first.
        final RealmResults<PushNotificationKey> result = realm.where(PushNotificationKey.class).findAll();
        realm.beginTransaction();
        result.clear();
        realm.commitTransaction();

        //add new user info in realm
        realm.beginTransaction();
        PushNotificationKey realmObject = realm.createObject(PushNotificationKey.class);
        realmObject.setCachedKey(key);
        realm.commitTransaction();
        realm.close();

    }

    public static void clearKey(Context act){
        Realm realm = getRealmInstanceContext(act);
        //clear user info in realm first.
        final RealmResults<PushNotificationKey> result = realm.where(PushNotificationKey.class).findAll();
        realm.beginTransaction();
        result.clear();
        realm.commitTransaction();
        realm.close();

    }


   /* public static void notificationAddList(Context act, final GCMClass obj, final String username) {
        final Realm realm = RealmObjectController.getRealmInstanceContext(act);
        realm.beginTransaction();

        NotificationInboxList realmObject = realm.createObject(NotificationInboxList.class);
        realmObject.setUsername(username);
        realmObject.setMessage(obj.getBody());
        realmObject.setTitle(obj.getTitle());
        realmObject.setDatetime("09/12/2017 10.30 AM");
        realmObject.setStatus("Y");
        realm.commitTransaction();

        //RealmResults<NotificationInboxList> inbox = realm.where(NotificationInboxList.class).equalTo("username", username).findAll();
        //Log.e("Size", Integer.toString(inbox.size()));

    }*/

    public static void deleteRealmFile(Activity act) {

        Realm realm = getRealmInstance(act);
        realm.beginTransaction();

        realm.commitTransaction();

        realm.close();

    }

    /* ----------------------- TBD - Retrieve cached result ----------------- */

    //get default result cached (remove later)
    public static RealmResults<CachedResult> getCachedResult(Activity act) {

        Realm realm = getRealmInstance(act);
        final RealmResults<CachedResult> result = realm.where(CachedResult.class).findAll();

        return result;
    }

    //get big point nc
    public static BigPointReceive getCachedBigPointResult(Activity act) {

        Realm realm = getRealmInstance(act);
        BigPointReceive obj = null;
        final RealmResults<CachedBigPointResult> result = realm.where(CachedBigPointResult.class).findAll();

        if (result.size() > 0) {
            Gson gson = new Gson();
            obj = gson.fromJson(result.get(0).getCachedResult(), BigPointReceive.class);
        }

        final RealmResults<CachedBigPointResult> clearResult = realm.where(CachedBigPointResult.class).findAll();
        realm.beginTransaction();
        clearResult.removeLast();
        realm.commitTransaction();
        realm.close();

        Log.e("clearResult", Integer.toString(clearResult.size()));
        return obj;
    }

    //get choose country (language selection) nc
    public static RealmResults<CachedLanguageCountry> getCachedLanguageCountry(Activity act) {

        Realm realm = getRealmInstance(act);
        final RealmResults<CachedLanguageCountry> result = realm.where(CachedLanguageCountry.class).findAll();

        //final RealmResults<CachedLanguageCountry> clearResult = realm.where(CachedLanguageCountry.class).findAll();
        //realm.beginTransaction();
        //clearResult.clear();
        //realm.commitTransaction();

        return result;
    }

    /* ------------------TBD - Cached Result -------------------------*/

    //cached default request
    public static void cachedResult(Activity act, String cachedResult) {

        Realm realm = getRealmInstance(act);

        final RealmResults<CachedResult> result = realm.where(CachedResult.class).findAll();
        realm.beginTransaction();
        result.clear();
        realm.commitTransaction();

        realm.beginTransaction();
        CachedResult realmObject = realm.createObject(CachedResult.class);
        realmObject.setCachedResult(cachedResult);
        realm.commitTransaction();
        realm.close();

    }


    //cached big point request
    public static void cachedSearchFlightResult(Activity act, String cachedResult, String api) {

        Realm realm = getRealmInstance(act);

        /*final RealmResults<CachedResult> result = realm.where(CachedResult.class).findAll();
        realm.beginTransaction();
        result.clear();
        realm.commitTransaction();*/

        realm.beginTransaction();
        CachedResult realmObject = realm.createObject(CachedResult.class);
        realmObject.setCachedResult(cachedResult);
        realmObject.setCachedAPI(api);
        realm.commitTransaction();
        realm.close();

    }

    //cached big point request
    public static void cachedBigPointRequest(Activity act, String cachedBigPointResult) {

        Realm realm = getRealmInstance(act);

        final RealmResults<CachedBigPointResult> result = realm.where(CachedBigPointResult.class).findAll();
        realm.beginTransaction();
        result.clear();
        realm.commitTransaction();

        realm.beginTransaction();
        CachedBigPointResult realmObject = realm.createObject(CachedBigPointResult.class);
        realmObject.setCachedResult(cachedBigPointResult);
        realm.commitTransaction();
        realm.close();

    }

    //cached big point request
    public static void cachedLanguageCountry(Activity act, String cachedLanguageCountry) {

        Realm realm = getRealmInstance(act);

        final RealmResults<CachedLanguageCountry> result = realm.where(CachedLanguageCountry.class).findAll();
        realm.beginTransaction();
        result.clear();
        realm.commitTransaction();

        realm.beginTransaction();
        CachedLanguageCountry realmObject = realm.createObject(CachedLanguageCountry.class);
        realmObject.setCachedResult(cachedLanguageCountry);
        realm.commitTransaction();
        realm.close();

    }

    /* ------------------------tbd clear cached result -------------------------*/

    public static void clearCachedResult(Activity act) {

        Realm realm = getRealmInstance(act);

        final RealmResults<CachedResult> result = realm.where(CachedResult.class).findAll();
        realm.beginTransaction();
        result.clear();
        realm.commitTransaction();
        realm.close();

    }

    public static void clearBigPointCached(Activity act) {

        Realm realm = getRealmInstance(act);

        final RealmResults<CachedBigPointResult> result = realm.where(CachedBigPointResult.class).findAll();
        realm.beginTransaction();
        result.clear();
        realm.commitTransaction();
        realm.close();

    }


}
