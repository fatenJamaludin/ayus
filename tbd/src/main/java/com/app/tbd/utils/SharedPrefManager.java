package com.app.tbd.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

public class SharedPrefManager {
    private static final String PREF_NAME = "AndroidHivePref";
    public static final String SIGNATURE = "SIGNATURE";
    public static final String SELECTED = "SELECTED";
    public static final String USER_EMAIL = "USER_EMAIL";
    public static final String USER_INFO = "USER_INFO";
    public static final String CHECKIN_INFO = "CHECKIN_INFO";
    public static final String TERM_INFO = "TERM_INFO";
    public static final String TITLE = "TITLE";
    public static final String FLIGHT = "FLIGHT";
    public static final String COUNTRY = "COUNTRY";
    public static final String STATE = "STATE";

    public static final String ISLOGIN = "ISLOGIN";
    public static final String ISREAD = "ISREAD";
    public static final String ISNEWSLETTER = "ISNEWSLETTER";
    public static final String PROMO_BANNER = "PM";
    public static final String DEFAULT_BANNER = "DB";
    public static final String USERNAME = "USERNAME";
    public static final String STATECODE = "STATECODE";

    public static final String PROMOSTATUS = "PROMOSTATUS";

    public static final String BOOKING_ID = "BOOKING_ID";

    public static final String SELECTED_DEPART_FARE_KEY = "SDFK";
    // public static final String SELECTED = "SELECTED";

    //Edit Profile
    public static final String EDIT_DOB = "EDIT_DOB";
    public static final String EDIT_STATE_NAME = "EDIT_STATE_NAME";
    public static final String EDIT_SALUTATION = "EDIT_SALUTATION";
    public static final String EDIT_GIVEN_NAME = "EDIT_GIVEN_NAME";
    public static final String EDIT_NATIONALITY = "EDIT_NATIONALITY";
    public static final String EDIT_FAMILY_NAME = "EDIT_FAMILY_NAME";
    public static final String EDIT_MOBILE = "EDIT_MOBILE";
    public static final String EDIT_PASSPORT = "EDIT_PASSPORT";
    public static final String EDIT_STREET1 = "EDIT_STREET1";
    public static final String EDIT_STREET2 = "EDIT_STREET2";
    public static final String EDIT_CITY = "EDIT_CITY";
    public static final String EDIT_POSTCODE = "EDIT_POSTCODE";
    public static final String EDIT_COUNTRY = "EDIT_COUNTRY";

    public static final String SEAT = "SEAT";
    public static final String PAYMENT_DUMMY = "PAYMENT_DUMMY";
    public static final String PNR = "PNR";
    public static final String PASSWORD = "PASSWORD";
    public static final String SOCIAL_MEDIA = "SM";
    public static final String DATA_VERSION = "DV";
    public static final String TEMP_RESULT = "TR";
    public static final String BANNER_MODULE = "BM";

    public static final String FLIGHT_TYPE = "FT";
    public static final String USER_ID = "UI";

    public static final String OFFERSSR1 = "OFS1";
    public static final String OFFERSSR2 = "OFS2";

    public static final String CUSTOMER_NUMBER = "CN";

    public static final String PERSON_ID = "PI";
    public static final String BANNER_REDIRECT_URL = "BRU";

    public static final String APP_VERSION = "AV";

    public static final String FORCE_LOGOUT = "N";

    // TBD //
    public static final String FIRST_TIME_USER = "Y";

    public static final String FIRST_TIME_LOGIN = "Y";


    public static final String SELECT_ADULT = "SELECT_ADULT";
    public static final String SELECT_CHILD = "SELECT_CHILD";
    public static final String SELECT_INFANT = "SELECT_INFANT";

    public static final String TOKEN = "TI";
    public static final String LANGUAGE_COUNTRY = "LC";

    public static final String LOAD_BIGPOINT = "LB";

    public static final String LANGUAGE_LIST = "LL";
    public static final String LANGUAGE_CODE = "LANGUAGE_CODE";

    public static final String NEWSLETTER_LANGUAGE_LIST = "NLL";
    public static final String ROUTE = "R";

    public static final String LONGITUDE = "LONGITUDE";
    public static final String LATITUDE = "LATITUDE";
    public static final String IP = "IP";
    public static final String CURRENT_COUNTRY = "CURRENT_COUNTRY";
    public static final String CURRENT_COUNTRY_CODE = "CURRENT_COUNTRY_CODE";

    public static final String SAVED_COUNTRY = "SAVED_COUNTRY";
    public static final String SAVED_COUNTRY_CODE = "SAVED_COUNTRY_CODE";
    public static final String SAVED_LANGUAGE = "SAVED_LANGUAGE";
    public static final String SAVED_S_LANGUAGE = "SAVED_S_LANGUAGE";
    public static final String SAVED_LANGUAGE_CODE = "SAVED_LANGUAGE_CODE";

    public static final String BOOKING_COUNTRY_STATE = "BCS";
    public static final String LANGUAGE = "LANGUAGE";

    public static final String CN = "CN";
    public static final String REGISTER_CODE = "REGISTER_CODE";
    public static final String COUNTRY_CALL_OPTION = "COUNTRY_CALL_OPTION";
    public static final String ON_BOARDING_IMAGE = "OBI";

    public static final String IMAGEBASE = "IMAGEBASE";

    public static final String HARD_CODE_LANGUAGE = "HARD_CODE_LANGUAGE";
    public static final String THAILAND = "THAILAND";
    public static final String CHINESE = "CHINESE";
    public static final String MUSIC = "Y";

    public static final String PROMO_LAST_UPDATE = "PROMO_LAST_UPDATE";
    public static final String RELOAD_PROMO = "RELOAD_PROMO";
    public static final String OVERLAY= "OL";

    int PRIVATE_MODE = 0;
    Context _context;
    private SharedPreferences _sharedPrefs;
    private Editor _prefsEditor;


    public SharedPrefManager(Context context) {
        this._context = context;
        _sharedPrefs = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        _prefsEditor = _sharedPrefs.edit();
    }

    public HashMap<String, String> getMusic() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(MUSIC, _sharedPrefs.getString(MUSIC, null));
        return init;
    }

    public HashMap<String, String> getOverlay() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(OVERLAY, _sharedPrefs.getString(OVERLAY, null));
        return init;
    }

    public HashMap<String, String> getReloadPromo() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(RELOAD_PROMO, _sharedPrefs.getString(RELOAD_PROMO, null));
        return init;
    }

    public HashMap<String, String> getPromoLastUpdate() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(PROMO_LAST_UPDATE, _sharedPrefs.getString(PROMO_LAST_UPDATE, null));
        return init;
    }

    /*ForceLogout*/
    public void setMusic(String music) {
        _prefsEditor.putString(MUSIC, music);
        _prefsEditor.apply();
    }

    public HashMap<String, String> getHardCodeLanguage() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(HARD_CODE_LANGUAGE, _sharedPrefs.getString(HARD_CODE_LANGUAGE, null));
        return init;
    }

    public HashMap<String, String> getThailand() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(THAILAND, _sharedPrefs.getString(THAILAND, null));
        return init;
    }

    public HashMap<String, String> getChinese() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(CHINESE, _sharedPrefs.getString(CHINESE, null));
        return init;
    }

    public HashMap<String, String> getBoardingList() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(ON_BOARDING_IMAGE, _sharedPrefs.getString(ON_BOARDING_IMAGE, null));
        return init;
    }

    public HashMap<String, String> getBookingCountryCode() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(BOOKING_COUNTRY_STATE, _sharedPrefs.getString(BOOKING_COUNTRY_STATE, null));
        return init;
    }

    public HashMap<String, String> getSelectedDepartKey() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(SELECTED_DEPART_FARE_KEY, _sharedPrefs.getString(SELECTED_DEPART_FARE_KEY, null));
        return init;
    }

    public HashMap<String, String> getCountryCallOption() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(COUNTRY_CALL_OPTION, _sharedPrefs.getString(COUNTRY_CALL_OPTION, null));
        return init;
    }

    public HashMap<String, String> getRoute() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(ROUTE, _sharedPrefs.getString(ROUTE, null));
        return init;
    }

    public HashMap<String, String> getPromoStatus() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(PROMOSTATUS, _sharedPrefs.getString(PROMOSTATUS, null));
        return init;
    }

    public HashMap<String, String> getLatitude() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(LATITUDE, _sharedPrefs.getString(LATITUDE, null));
        return init;
    }

    public HashMap<String, String> getLongitude() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(LONGITUDE, _sharedPrefs.getString(LONGITUDE, null));
        return init;
    }

    public HashMap<String, String> getIP() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(IP, _sharedPrefs.getString(IP, null));
        return init;
    }

    public HashMap<String, String> getSelectAdult() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(SELECT_ADULT, _sharedPrefs.getString(SELECT_ADULT, null));
        return init;
    }

    public HashMap<String, String> getSelectChild() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(SELECT_CHILD, _sharedPrefs.getString(SELECT_CHILD, null));
        return init;
    }

    public HashMap<String, String> getSelectInfant() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(SELECT_INFANT, _sharedPrefs.getString(SELECT_INFANT, null));
        return init;
    }

    public HashMap<String, String> getCurrentCountry() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(CURRENT_COUNTRY, _sharedPrefs.getString(CURRENT_COUNTRY, null));
        return init;
    }

    public HashMap<String, String> getCurrentCountryCode() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(CURRENT_COUNTRY_CODE, _sharedPrefs.getString(CURRENT_COUNTRY_CODE, null));
        return init;
    }

    public HashMap<String, String> getSavedCountry() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(SAVED_COUNTRY, _sharedPrefs.getString(SAVED_COUNTRY, null));
        return init;
    }

    public HashMap<String, String> getSavedCountryCode() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(SAVED_COUNTRY_CODE, _sharedPrefs.getString(SAVED_COUNTRY_CODE, null));
        return init;
    }

    public HashMap<String, String> getSavedLanguage() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(SAVED_LANGUAGE, _sharedPrefs.getString(SAVED_LANGUAGE, null));
        return init;
    }

    public HashMap<String, String> getSavedLanguageSCode() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(SAVED_S_LANGUAGE, _sharedPrefs.getString(SAVED_S_LANGUAGE, null));
        return init;
    }


    public HashMap<String, String> getSavedLanguageCode() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(SAVED_LANGUAGE_CODE, _sharedPrefs.getString(SAVED_LANGUAGE_CODE, null));
        return init;
    }

    public HashMap<String, String> getNewsletterLanguageList() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(NEWSLETTER_LANGUAGE_LIST, _sharedPrefs.getString(NEWSLETTER_LANGUAGE_LIST, null));
        return init;
    }

    public HashMap<String, String> getLanguageList() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(LANGUAGE_LIST, _sharedPrefs.getString(LANGUAGE_LIST, null));
        return init;
    }

    public HashMap<String, String> getLanguageCode() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(LANGUAGE_CODE, _sharedPrefs.getString(LANGUAGE_CODE, null));
        return init;
    }

    public HashMap<String, String> getBigPointRequestStatus() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(LOAD_BIGPOINT, _sharedPrefs.getString(LOAD_BIGPOINT, null));
        return init;
    }


    public HashMap<String, String> getLanguageCountry() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(LANGUAGE_COUNTRY, _sharedPrefs.getString(LANGUAGE_COUNTRY, null));
        return init;
    }


    public HashMap<String, String> getLanguageOption() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(LANGUAGE, _sharedPrefs.getString(LANGUAGE, null));
        return init;
    }

    public HashMap<String, String> getFirstTimeUser() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(FIRST_TIME_USER, _sharedPrefs.getString(FIRST_TIME_USER, null));
        return init;
    }

    public HashMap<String, String> getFirstTimeLogin() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(FIRST_TIME_LOGIN, _sharedPrefs.getString(FIRST_TIME_LOGIN, null));
        return init;
    }

    public HashMap<String, String> getToken() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(TOKEN, _sharedPrefs.getString(TOKEN, null));
        return init;
    }

    /*ForceLogout*/
    public HashMap<String, String> getForceLogout() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(FORCE_LOGOUT, _sharedPrefs.getString(FORCE_LOGOUT, null));
        return init;
    }

    /*PAYMENT*/
    public HashMap<String, String> getBannerRedirectURL() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(BANNER_REDIRECT_URL, _sharedPrefs.getString(BANNER_REDIRECT_URL, null));
        return init;
    }

    /*SSR2*/
    public HashMap<String, String> getCustomerNumber() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(CUSTOMER_NUMBER, _sharedPrefs.getString(CUSTOMER_NUMBER, null));
        return init;
    }

    /*SSR2*/
    public HashMap<String, String> getPersonID() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(PERSON_ID, _sharedPrefs.getString(PERSON_ID, null));
        return init;
    }

    /*SSR1*/
    public HashMap<String, String> getSSR1() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(OFFERSSR1, _sharedPrefs.getString(OFFERSSR1, null));
        return init;
    }

    /*SSR2*/
    public HashMap<String, String> getSSR2() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(OFFERSSR2, _sharedPrefs.getString(OFFERSSR2, null));
        return init;
    }


    /*user id*/
    public HashMap<String, String> getUserID() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(USER_ID, _sharedPrefs.getString(USER_ID, null));
        return init;
    }

    /*SOCIAL MEDIA*/
    public HashMap<String, String> getAppVersion() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(APP_VERSION, _sharedPrefs.getString(APP_VERSION, null));
        return init;
    }

    /*SOCIAL MEDIA*/
    public HashMap<String, String> getFlightType() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(FLIGHT_TYPE, _sharedPrefs.getString(FLIGHT_TYPE, null));
        return init;
    }

    /*SOCIAL MEDIA*/
    public HashMap<String, String> getBannerModule() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(BANNER_MODULE, _sharedPrefs.getString(BANNER_MODULE, null));
        return init;
    }

    /*SOCIAL MEDIA*/
    public HashMap<String, String> getSocialMedia() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(SOCIAL_MEDIA, _sharedPrefs.getString(SOCIAL_MEDIA, null));
        return init;
    }

    /*SOCIAL MEDIA*/
    public HashMap<String, String> getTempResult() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(TEMP_RESULT, _sharedPrefs.getString(TEMP_RESULT, null));
        return init;
    }

    /*DATA VERSION*/
    public HashMap<String, String> getDataVesion() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(DATA_VERSION, _sharedPrefs.getString(DATA_VERSION, null));
        return init;
    }

    /*PAYMENT*/
    public HashMap<String, String> getPNR() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(PNR, _sharedPrefs.getString(PNR, null));
        return init;
    }

    /*PAYMENT*/
    public HashMap<String, String> getPaymentDummy() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(PAYMENT_DUMMY, _sharedPrefs.getString(PAYMENT_DUMMY, null));
        return init;
    }

    /*SEAT*/
    public HashMap<String, String> getSeat() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(SEAT, _sharedPrefs.getString(SEAT, null));
        return init;
    }

    /*Return Booking ID*/
    public HashMap<String, String> getBookingID() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(BOOKING_ID, _sharedPrefs.getString(BOOKING_ID, null));
        return init;
    }

    /*Return Login Status*/
    public HashMap<String, String> getPromoBanner() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(PROMO_BANNER, _sharedPrefs.getString(PROMO_BANNER, null));
        return init;
    }

    /*Return State*/
    public HashMap<String, String> getState() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(STATE, _sharedPrefs.getString(STATE, null));
        return init;
    }

    /*Return Username*/
    public HashMap<String, String> getUsername() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(USERNAME, _sharedPrefs.getString(USERNAME, null));
        return init;
    }

    /*Return State Code*/
    public HashMap<String, String> getStateCode() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(STATECODE, _sharedPrefs.getString(STATECODE, null));
        return init;
    }

    /*Return UserInfo*/
    public HashMap<String, String> getUserInfo() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(USER_INFO, _sharedPrefs.getString(USER_INFO, null));
        return init;
    }


    /*Return Login Status*/
    public HashMap<String, String> getDefaultBanner() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(DEFAULT_BANNER, _sharedPrefs.getString(DEFAULT_BANNER, null));
        return init;
    }


    /*Return Login Status*/
    public HashMap<String, String> getLoginStatus() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(ISLOGIN, _sharedPrefs.getString(ISLOGIN, null));
        return init;
    }

    /*Return Notification Status*/
    public HashMap<String, String> getNotificationStatus() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(ISREAD, _sharedPrefs.getString(ISREAD, null));
        return init;
    }

    /*Return newsletter Status*/
    public HashMap<String, String> getNewsletterStatus() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(ISNEWSLETTER, _sharedPrefs.getString(ISNEWSLETTER, null));
        return init;
    }


    /*Return Signature Value*/
    public HashMap<String, String> getSignatureFromLocalStorage() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(SIGNATURE, _sharedPrefs.getString(SIGNATURE, null));
        return init;
    }

    /*Return Selected Value*/
    public HashMap<String, String> getSelectedPopupSelection() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(SELECTED, _sharedPrefs.getString(SELECTED, null));
        return init;
    }

    /*Return Selected Value*/
    public HashMap<String, String> getTitle() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(TITLE, _sharedPrefs.getString(TITLE, null));
        return init;
    }

    /*Return Country Value*/
    public HashMap<String, String> getCountry() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(COUNTRY, _sharedPrefs.getString(COUNTRY, null));
        return init;
    }

    /*Return Country Value*/
    public HashMap<String, String> getFlight() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(FLIGHT, _sharedPrefs.getString(FLIGHT, null));
        return init;
    }


    /*Return Checkin_info*/
    public HashMap<String, String> getCheckinInfo() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(CHECKIN_INFO, _sharedPrefs.getString(CHECKIN_INFO, null));
        return init;
    }

    public HashMap<String, String> getCn() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(CN, _sharedPrefs.getString(CN, null));
        return init;
    }

    public HashMap<String, String> getRegisterCode() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(REGISTER_CODE, _sharedPrefs.getString(REGISTER_CODE, null));
        return init;
    }


    /*Return Term_info*/
    public HashMap<String, String> getTermInfo() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(TERM_INFO, _sharedPrefs.getString(TERM_INFO, null));
        return init;
    }


    /*  public <T> void setList(String key, List<T> list)
      {
          Gson gson = new Gson();
          String json = gson.toJson(list);

          set(key, json);
      }
      public void set(String key, String value)
      {
          _prefsEditor.putString(key, value);
          _prefsEditor.apply();
      }

  */


    /*ForceLogout*/
    public void setHardCodeLanguageCode(String languageCountry) {
        _prefsEditor.putString(HARD_CODE_LANGUAGE, languageCountry);
        _prefsEditor.apply();
    }

    public void setOverlay(String overlay) {
        _prefsEditor.putString(OVERLAY, overlay);
        _prefsEditor.apply();
    }


    public void setThailand(String thailand) {
        _prefsEditor.putString(THAILAND, thailand);
        _prefsEditor.apply();
    }

    public void setChinese(String chinese) {
        _prefsEditor.putString(CHINESE, chinese);
        _prefsEditor.apply();
    }

    public void setReloadPromo(String reloadPromo) {
        _prefsEditor.putString(RELOAD_PROMO, reloadPromo);
        _prefsEditor.apply();
    }

    public void setOnBoardingImage(String board) {
        _prefsEditor.putString(ON_BOARDING_IMAGE, board);
        _prefsEditor.apply();
    }

    /*ForceLogout*/
    public void setLanguageCountry(String languageCountry) {
        _prefsEditor.putString(LANGUAGE_COUNTRY, languageCountry);
        _prefsEditor.apply();
    }

    public void setCn(String cn) {
        _prefsEditor.putString(CN, cn);
        _prefsEditor.apply();
    }

    public void setRegisterCode(String registerCode) {
        _prefsEditor.putString(REGISTER_CODE, registerCode);
        _prefsEditor.apply();
    }

    public void setCountryCallOption(String countryCallOption) {
        _prefsEditor.putString(COUNTRY_CALL_OPTION, countryCallOption);
        _prefsEditor.apply();
    }

    public void setLanguageOption(String languageOption) {
        _prefsEditor.putString(LANGUAGE, languageOption);
        _prefsEditor.apply();
    }

    public void setEditDOB(String dob) {
        _prefsEditor.putString(EDIT_DOB, dob);
        _prefsEditor.apply();
    }

    public void setLanguageList(String lang) {
        _prefsEditor.putString(LANGUAGE_LIST, lang);
        _prefsEditor.apply();
    }

    public void setLanguageCode(String lang) {
        _prefsEditor.putString(LANGUAGE_CODE, lang);
        _prefsEditor.apply();
    }

    public void setPromoLastUpdate(String lastUpdate) {
        _prefsEditor.putString(PROMO_LAST_UPDATE, lastUpdate);
        _prefsEditor.apply();
    }

    public void setEditStateName(String stateName) {
        _prefsEditor.putString(EDIT_STATE_NAME, stateName);
        _prefsEditor.apply();
    }

    public void setSelectAdult(String selectAdult) {
        _prefsEditor.putString(SELECT_ADULT, selectAdult);
        _prefsEditor.apply();
    }

    public void setSelectInfant(String selectInfant) {
        _prefsEditor.putString(SELECT_INFANT, selectInfant);
        _prefsEditor.apply();
    }

    public void setSelectChild(String selectChild) {
        _prefsEditor.putString(SELECT_CHILD, selectChild);
        _prefsEditor.apply();
    }

    public HashMap<String, String> getEditStateName() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(EDIT_STATE_NAME, _sharedPrefs.getString(EDIT_STATE_NAME, null));
        return init;
    }

    public HashMap<String, String> get() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(EDIT_DOB, _sharedPrefs.getString(EDIT_DOB, null));
        return init;
    }

    public HashMap<String, String> getEditDOB() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(EDIT_DOB, _sharedPrefs.getString(EDIT_DOB, null));
        return init;
    }


    public void setSelectedDepartFareKey(String fare) {
        _prefsEditor.putString(SELECTED_DEPART_FARE_KEY, fare);
        _prefsEditor.apply();
    }

    public void setRoute(String route) {
        _prefsEditor.putString(ROUTE, route);
        _prefsEditor.apply();
    }

    /*ForceLogout*/
    public void setNewsletterLanguageList(String logout) {
        _prefsEditor.putString(NEWSLETTER_LANGUAGE_LIST, logout);
        _prefsEditor.apply();
    }

    public void setPromoStatus(String status) {
        _prefsEditor.putString(PROMOSTATUS, status);
        _prefsEditor.apply();
    }

    public void setLongitude(String longitude) {
        _prefsEditor.putString(LONGITUDE, longitude);
        _prefsEditor.apply();
    }

    public void setLatitude(String latitude) {
        _prefsEditor.putString(LATITUDE, latitude);
        _prefsEditor.apply();
    }

    public void setIP(String ip) {
        _prefsEditor.putString(IP, ip);
        _prefsEditor.apply();
    }

    public void setCurrentCountry(String currentCountry) {
        _prefsEditor.putString(CURRENT_COUNTRY, currentCountry);
        _prefsEditor.apply();
    }

    public void setBookingCountry(String code) {
        _prefsEditor.putString(BOOKING_COUNTRY_STATE, code);
        _prefsEditor.apply();
    }

    public void setCurrentCountryCode(String currentCountryCode) {
        _prefsEditor.putString(CURRENT_COUNTRY_CODE, currentCountryCode);
        _prefsEditor.apply();
    }

    public void setSavedCountry(String savedCountry) {
        _prefsEditor.putString(SAVED_COUNTRY, savedCountry);
        _prefsEditor.apply();
    }

    public void setSavedLanguage(String savedLanguage) {
        _prefsEditor.putString(SAVED_LANGUAGE, savedLanguage);
        _prefsEditor.apply();
    }

    public void setSavedLanguageSCode(String savedLanguageSCode) {
        _prefsEditor.putString(SAVED_S_LANGUAGE, savedLanguageSCode);
        _prefsEditor.apply();
    }

    public void setSavedCountryCode(String savedCountryCode) {
        _prefsEditor.putString(SAVED_COUNTRY_CODE, savedCountryCode);
        _prefsEditor.apply();
    }

    public void setSavedLanguageCode(String savedLanguageCode) {
        _prefsEditor.putString(SAVED_LANGUAGE_CODE, savedLanguageCode);
        _prefsEditor.apply();
    }

    /*ForceLogout*/
    public void setForceLogout(String logout) {
        _prefsEditor.putString(FORCE_LOGOUT, logout);
        _prefsEditor.apply();
    }

    /*Set SEAT*/
    public void setCustomerNumber(String customerNumber) {
        _prefsEditor.putString(CUSTOMER_NUMBER, customerNumber);
        _prefsEditor.apply();
    }

    public void setSSR1(String type) {
        _prefsEditor.putString(OFFERSSR1, type);
        _prefsEditor.apply();
    }

    public void setSSR2(String type) {
        _prefsEditor.putString(OFFERSSR2, type);
        _prefsEditor.apply();
    }

    /*Return User_info*/
    public HashMap<String, String> getUserEmail() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(USER_EMAIL, _sharedPrefs.getString(USER_EMAIL, null));
        return init;
    }

    /*Return User_info*/
    public HashMap<String, String> getUserPassword() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(PASSWORD, _sharedPrefs.getString(PASSWORD, null));
        return init;
    }

    /*Set SEAT*/
    public void setToken(String token) {
        _prefsEditor.putString(TOKEN, token);
        _prefsEditor.apply();
    }

    /*Set Booking ID*/
    public void setFirstTimeUser(String status) {
        _prefsEditor.putString(FIRST_TIME_USER, status);
        _prefsEditor.apply();
    }

    public void setFirstTimeLogin(String status) {
        _prefsEditor.putString(FIRST_TIME_LOGIN, status);
        _prefsEditor.apply();
    }

    /*Set Booking ID*/
    public void setPersonID(String personID) {
        _prefsEditor.putString(PERSON_ID, personID);
        _prefsEditor.apply();
    }

    /*Set Booking ID*/
    public void setAppVersion(String version) {
        _prefsEditor.putString(APP_VERSION, version);
        _prefsEditor.apply();
    }

    /*Set Booking ID*/
    public void setUserID(String id) {
        _prefsEditor.putString(USER_ID, id);
        _prefsEditor.apply();
    }

    /*Set Booking ID*/
    public void setFlightType(String type) {
        _prefsEditor.putString(FLIGHT_TYPE, type);
        _prefsEditor.apply();
    }

    /*Set SEAT*/
    public void setTempResult(String tempResult) {
        _prefsEditor.putString(TEMP_RESULT, tempResult);
        _prefsEditor.apply();
    }

    /*Set Booking ID*/
    public void setSocialMedia(String version) {
        _prefsEditor.putString(SOCIAL_MEDIA, version);
        _prefsEditor.apply();
    }

    /*Set Booking ID*/
    public void setDataVersion(String version) {
        _prefsEditor.putString(DATA_VERSION, version);
        _prefsEditor.apply();
    }

    /*Set SEAT*/
    public void setPNR(String pnr) {
        _prefsEditor.putString(PNR, pnr);
        _prefsEditor.apply();
    }

    /*Set SEAT*/
    public void setPaymentDummy(String payment) {
        _prefsEditor.putString(PAYMENT_DUMMY, payment);
        _prefsEditor.apply();
    }

    /*Set SEAT*/
    public void setSeat(String seat) {
        _prefsEditor.putString(SEAT, seat);
        _prefsEditor.apply();
    }


    /*Set Booking ID*/
    public void setUserPassword(String password) {
        _prefsEditor.putString(PASSWORD, password);
        _prefsEditor.apply();
    }


    /*Set Booking ID*/
    public void setBookingID(String id) {
        _prefsEditor.putString(BOOKING_ID, id);
        _prefsEditor.apply();
    }


    /*Set Checkin Value*/
    public void setCheckinInfo(String url) {
        _prefsEditor.putString(CHECKIN_INFO, url);
        _prefsEditor.apply();
    }


    /*Set Checkin Value*/
    public void setTermInfo(String url) {
        _prefsEditor.putString(TERM_INFO, url);
        _prefsEditor.apply();
    }

    /*Set Username Value*/
    public void setUsername(String url) {
        _prefsEditor.putString(USERNAME, url);
        _prefsEditor.apply();
    }

    /*Set Username Value*/
    public void setStateCode(String url) {
        _prefsEditor.putString(STATECODE, url);
        _prefsEditor.apply();
    }

    /*Set STATE value*/
    public void setState(String state) {
        _prefsEditor.putString(STATE, state);
        _prefsEditor.apply();
    }


    /*Set Username Value*/
    public void setUserInfo(String url) {
        _prefsEditor.putString(USER_INFO, url);
        _prefsEditor.apply();
    }

    /*Set Signature Value*/
    public void setBannerUrl(String url) {
        _prefsEditor.putString(DEFAULT_BANNER, url);
        _prefsEditor.apply();
    }

    /*Set Signature Value*/
    public void setPromoBannerUrl(String url) {
        _prefsEditor.putString(PROMO_BANNER, url);
        _prefsEditor.apply();
    }

    /*Set Signature Value*/
    public void setBannerModule(String module) {
        _prefsEditor.putString(BANNER_MODULE, module);
        _prefsEditor.apply();
    }

    /*Set Signature Value*/
    public void setBannerRedirectURL(String url) {
        _prefsEditor.putString(BANNER_REDIRECT_URL, url);
        _prefsEditor.apply();
    }


    /*Set Signature Value*/
    public void setLoginStatus(String status) {
        _prefsEditor.putString(ISLOGIN, status);
        _prefsEditor.apply();
    }

    /*Set Signature Value*/
    public void setNotificationStatus(String status) {
        _prefsEditor.putString(ISREAD, status);
        _prefsEditor.apply();
    }

    /*Set Signature Value*/
    public void setNewsletterStatus(String status) {
        _prefsEditor.putString(ISNEWSLETTER, status);
        _prefsEditor.apply();
    }

    /*Set Signature Value*/
    public void setSignatureToLocalStorage(String signature) {
        _prefsEditor.putString(SIGNATURE, signature);
        _prefsEditor.apply();
    }

    /*Set Airport Value*/
    public void setSelectedPopupSelection(String signature) {
        _prefsEditor.putString(SELECTED, signature);
        _prefsEditor.apply();
    }

    /*Set Airport Value*/
    public void setUserTitle(String title) {
        _prefsEditor.putString(TITLE, title);
        _prefsEditor.apply();
    }

    /*Set Airport Value*/
    public void setFlight(String flight) {
        _prefsEditor.putString(FLIGHT, flight);
        _prefsEditor.apply();
    }

    /*Set Airport Value*/
    public void setCountry(String country) {
        _prefsEditor.putString(COUNTRY, country);
        _prefsEditor.apply();
    }

    /*Set Userinfo Value*/
    public void setUserEmail(String url) {
        _prefsEditor.putString(USER_EMAIL, url);
        _prefsEditor.apply();
    }

    /*Set Booking ID*/
    public void clearSocialMedia() {
        _sharedPrefs.edit().remove(SOCIAL_MEDIA).apply();
    }

    /*Clear TermValue*/
    public void clearPNR() {
        // Clearing All URL
        _sharedPrefs.edit().remove(PNR).apply();

    }

    /*Clear Checkin Value*/
    public void removeUserID() {
        // Clearing All URL
        _sharedPrefs.edit().remove(USER_ID).apply();

    }

    /*Clear Checkin Value*/
    public void removeSeat() {
        // Clearing All URL
        _sharedPrefs.edit().remove(SEAT).apply();

    }

    /*Clear Checkin Value*/
    public void checkinInfoURL() {
        // Clearing All URL
        _sharedPrefs.edit().remove(CHECKIN_INFO).apply();

    }

    /*Clear Checkin Value*/
    public void clearPassword() {
        // Clearing All URL
        _sharedPrefs.edit().remove(PASSWORD).apply();

    }

    /*Clear TermValue*/
    public void clearPayment() {
        // Clearing All URL
        _sharedPrefs.edit().remove(PAYMENT_DUMMY).apply();


    }

    /*Clear TermValue*/
    public void termInfoURL() {
        // Clearing All URL
        _sharedPrefs.edit().remove(TERM_INFO).apply();

    }

    /*Clear State Value*/
    public void clearFlightType() {
        // Clearing Selected
        _sharedPrefs.edit().remove(FLIGHT_TYPE).apply();
    }

    public void clearPromoStatus() {
        _sharedPrefs.edit().remove(PROMOSTATUS).apply();
    }

    /*Clear Signature Value*/
    public void clearDataVersion() {
        // Clearing All URL
        _sharedPrefs.edit().remove(DATA_VERSION).apply();

    }


    /*Clear Signature Value*/
    public void clearBannerURL() {
        // Clearing All URL
        _sharedPrefs.edit().remove(PROMO_BANNER).apply();
        _sharedPrefs.edit().remove(DEFAULT_BANNER).apply();

    }

    /*Clear Login Status*/
    public void clearLoginStatus() {
        // Clearing Siganture
        _sharedPrefs.edit().remove(ISLOGIN).apply();
    }

    /*Clear Login Status*/
    public void clearNewsletterStatus() {
        // Clearing Siganture
        _sharedPrefs.edit().remove(ISNEWSLETTER).apply();
    }

    /*Clear Signature Value*/
    public void clearSignatureFromLocalStorage() {
        // Clearing Siganture
        _sharedPrefs.edit().remove(SIGNATURE).apply();
    }

    /*Clear Signature Value*/
    public void setUsername() {
        // Clearing Siganture
        _sharedPrefs.edit().remove(USERNAME).apply();
    }

    /*Clear State Code Value*/
    public void setStateCode() {
        // Clearing State Code
        _sharedPrefs.edit().remove(STATECODE).apply();
    }

    public void setUserInfo() {
        // Clearing Siganture
        _sharedPrefs.edit().remove(USER_INFO).apply();
    }

    /*Clear Selected Value*/
    public void clearBookingID() {
        // Clearing Selected
        _sharedPrefs.edit().remove(BOOKING_ID).apply();
    }

    /*Clear Selected Value*/
    public void clearSelectedPopupSelection() {
        // Clearing Selected
        _sharedPrefs.edit().remove(SELECTED).apply();
    }

    /*Clear Selected Value*/
    public void clearTitle() {
        // Clearing Selected
        _sharedPrefs.edit().remove(TITLE).apply();
    }

    /*Clear State Value*/
    public void clearState() {
        // Clearing Selected
        _sharedPrefs.edit().remove(STATE).apply();
    }

    /*Clear State Value*/
    public void clearAppVersion() {
        // Clearing Selected
        _sharedPrefs.edit().remove(APP_VERSION).apply();
    }

    /*Clear Flight Value*/
    public void clearFlight() {
        // Clearing Selected
        _sharedPrefs.edit().remove(FLIGHT).apply();
    }

    /*Clear Country Value*/
    public void clearCoutnry() {
        // Clearing Selected
        _sharedPrefs.edit().remove(COUNTRY).apply();
    }


    /*Clear UserInfo Value*/
    public void clearUserEmail() {
        // Clearing Selected
        _sharedPrefs.edit().remove(USER_EMAIL).apply();
    }

    /*Clear UserInfo Value*/
    public void clearTempResult() {
        // Clearing Selected
        _sharedPrefs.edit().remove(TEMP_RESULT).apply();
        Log.e("Clear", "True");
    }

    /*Clear UserInfo Value*/
    public void clearBannerModule() {
        // Clearing Selected
        _sharedPrefs.edit().remove(BANNER_MODULE).apply();
        Log.e("Clear", "True");
    }

    /*Clear UserInfo Value*/
    public void clearCustomerNumber() {
        // Clearing Selected
        _sharedPrefs.edit().remove(CUSTOMER_NUMBER).apply();
        Log.e("Clear", "True");
    }

    /*Clear UserInfo Value*/
    public void clearPersonID() {
        // Clearing Selected
        _sharedPrefs.edit().remove(PERSON_ID).apply();
        Log.e("Clear", "True");
    }

    /*Clear UserInfo Value*/
    public void clearLongitude() {
        // Clearing Selected
        _sharedPrefs.edit().remove(LONGITUDE).apply();
        Log.e("Clear long", "True");
    }

    /*Clear UserInfo Value*/
    public void clearLatitude() {
        // Clearing Selected
        _sharedPrefs.edit().remove(LATITUDE).apply();
        Log.e("Clear lat", "True");
    }

    /*Clear UserInfo Value*/
    public void clearIP() {
        // Clearing Selected
        _sharedPrefs.edit().remove(IP).apply();
        Log.e("Clear ip ", "True");
    }

    public void clearCurrentCountry() {
        // Clearing Selected
        _sharedPrefs.edit().remove(CURRENT_COUNTRY).apply();
        Log.e("Clear current country", "True");
    }

    public void clearSelectAdult() {
        // Clearing Selected
        _sharedPrefs.edit().remove(SELECT_ADULT).apply();
        Log.e("Clear adult quantity", "True");
    }

    public void clearSelectChild() {
        // Clearing Selected
        _sharedPrefs.edit().remove(SELECT_CHILD).apply();
        Log.e("Clear child quantity", "True");
    }

    public void clearSelectInfant() {
        // Clearing Selected
        _sharedPrefs.edit().remove(SELECT_INFANT).apply();
        Log.e("Clear infant quantity", "True");
    }

    /* ---------------------------- TBD -------------------------------*/


    public void setBigPointRequestStatus(String status) {
        _prefsEditor.putString(LOAD_BIGPOINT, status);
        _prefsEditor.apply();
    }

    //---------------------------IMAGEBASE--------------------------------//

    public void setImageBase(String imageBase) {
        _prefsEditor.putString(IMAGEBASE, imageBase);
        _prefsEditor.apply();
    }

    public HashMap<String, String> getImageBase() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(IMAGEBASE, _sharedPrefs.getString(IMAGEBASE, null));
        return init;
    }

    public void clearImageBase() {
        _sharedPrefs.edit().remove(IMAGEBASE).apply();
    }

}