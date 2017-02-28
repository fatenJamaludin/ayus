package com.app.tbd.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.tbd.application.AnalyticsApplication;
import com.app.tbd.application.MainApplication;
import com.app.tbd.MainController;
import com.app.tbd.R;
import com.app.tbd.ui.Activity.BookingFlight.SearchFlightFragment;
import com.app.tbd.ui.Activity.PushNotificationInbox.PushNotificationActivity;
import com.app.tbd.ui.Activity.PushNotificationInbox.RealmInboxNotification;
import com.app.tbd.ui.Activity.RedirectActivity;
import com.app.tbd.ui.Activity.SplashScreen.SplashScreenActivity;
import com.app.tbd.ui.Activity.TAB.TabActivity;
import com.app.tbd.ui.Model.JSON.GCMClass;
import com.app.tbd.ui.Model.JSON.PromoTransaction;
import com.app.tbd.ui.Model.JSON.PromotionCache;
import com.app.tbd.ui.Model.JSON.PushNotificationKey;
import com.app.tbd.ui.Model.Receive.BaggageMealReceive;
import com.app.tbd.ui.Model.Receive.LanguageCountryReceive;
import com.app.tbd.ui.Model.Receive.PromotionReceive;
import com.app.tbd.ui.Model.Request.Country;
import com.app.tbd.ui.Realm.RealmObjectController;
import com.app.tbd.utils.DropDownItem;
import com.app.tbd.utils.DropMenuAdapter;
import com.app.tbd.utils.SharedPrefManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.text.Collator;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.keyboardsurfer.android.widget.crouton.Configuration;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import dmax.dialog.SpotsDialog;
import io.realm.Realm;
import io.realm.RealmResults;

public class BaseFragment extends Fragment {

    public com.app.tbd.base.AQuery aq;
    private int indexForState = -1;
    private static SharedPrefManager prefManager;
    private static Country obj = new Country();
    private static Dialog dialog;
    private static ProgressDialog progressDialog;

    private static Boolean status;
    Boolean manualValidationStatus = true;
    private static int staticIndex = -1;

    static MediaPlayer backgroundMP;
    static int currentLength = 0;

    //big fun music
    public void comic_btnClicked(Activity act) {
        final MediaPlayer mp = MediaPlayer.create(act, R.raw.gets_in_the_way);
        mp.start();
    }

    public static void comic_backgroundMusic(Activity act) {
        if (backgroundMP != null) {
            backgroundMP.stop();
        }
        backgroundMP = MediaPlayer.create(act, R.raw.payon);
        backgroundMP.setLooping(true);
        backgroundMP.setVolume(80, 80);
        backgroundMP.start();
    }

    public static void comic_stopBackgroundMusic() {
        if (backgroundMP != null) {
            backgroundMP.stop();
        }
    }

    public static void comic_pauseBackgroundMusic() {
        if (backgroundMP != null) {
            backgroundMP.pause();
            currentLength = backgroundMP.getCurrentPosition();
        }
    }

    public static void comic_playBackgroundMusic(Activity act) {
        if (backgroundMP != null) {
            backgroundMP.stop();
            if (currentLength != 0) {
                backgroundMP = MediaPlayer.create(act, R.raw.payon);
                backgroundMP.setLooping(true);
                backgroundMP.setVolume(80, 80);
                backgroundMP.seekTo(currentLength);
                backgroundMP.start();
            } else {
                backgroundMP = MediaPlayer.create(act, R.raw.payon);
                backgroundMP.setLooping(true);
                backgroundMP.setVolume(80, 80);
                backgroundMP.start();
            }
        } else {
            backgroundMP = MediaPlayer.create(act, R.raw.payon);
            backgroundMP.setLooping(true);
            backgroundMP.setVolume(80, 80);
            backgroundMP.start();
        }
    }

    public String parseStringDate(String unparseDate) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat reformat = new SimpleDateFormat("dd MMM  yyyy");

        try {

            Date date = formatter.parse(unparseDate);
            return reformat.format(date).toString();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String parseStringDateFromSearchFlight(String unparseDate) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat reformat = new SimpleDateFormat("dd MMM yyyy");

        try {

            Date date = formatter.parse(unparseDate);
            return reformat.format(date).toString();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String splitCountryDialingCode(String data, String codeToSplit) {

        String code;

        String string = codeToSplit;
        String[] parts = string.split("/");

        if (data.equals("CountryCode")) {
            code = parts[0];
        } else {
            code = parts[1];
        }
        return code;
    }

    //check added fragment
    public boolean checkFragmentAdded() {

        boolean addFragment = false;

        Fragment fragment = getFragmentManager().findFragmentByTag("DATEPICKER_TAG");
        addFragment = fragment == null;

        return addFragment;

    }

    public boolean flightDuration2(String arrivalTime, String returnDepartureTime) {

        boolean status = true;

        SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mma");

        Date arrival = null;
        Date departureReturn = null;

        try {
            arrival = parseFormat.parse(arrivalTime);
            departureReturn = parseFormat.parse(returnDepartureTime);
        } catch (Exception e) {
        }

        long count60minute = arrival.getTime() - departureReturn.getTime();

        if (Math.abs(TimeUnit.MILLISECONDS.toMinutes(count60minute)) <= 60) {
            status = false;
        }

        return status;
    }

    public boolean flightDuration(String arrivalTime, String returnDepartureTime) {

        boolean status = true;

        SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");

        Log.e("Flight Duration", "True.");
        Log.e(arrivalTime, returnDepartureTime);

        try {

            Date arrival = parseFormat.parse(arrivalTime);
            Date departureReturn = parseFormat.parse(returnDepartureTime);

            if (arrival != null && departureReturn != null) {
                long count60minute = arrival.getTime() - departureReturn.getTime();

                if (Math.abs(TimeUnit.MILLISECONDS.toMinutes(count60minute)) <= 60) {
                    status = false;
                }
            }

        } catch (Exception e) {

            Log.e("getTime()", e.toString());
            status = false;

        }

        return status;
    }

    public void blinkText(TextView txt) {

        //try set blinking textview
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(120); //You can manage the blinking time with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        txt.startAnimation(anim);

    }

   /* public String getDialingCode(String coutryCode, Activity act) {

        String dialingCode = null;

        JSONArray jsonCountry = getCountry(act);
        for (int x = 0; x < jsonCountry.length(); x++) {

            JSONObject row = (JSONObject) jsonCountry.opt(x);
            if (coutryCode.equals(row.optString("country_code"))) {
                dialingCode = row.optString("dialing_code");
            }
        }

        return dialingCode;
    }*/

    public boolean validateDialingCode(String dialingCode, String mobilePhone) {

        boolean status = false;

        String twoChar = mobilePhone.substring(0, 2);
        if (!dialingCode.equals(twoChar)) {
            status = true;
        }
        return status;
    }

    public boolean timeCompare(String arrivalTime, String returnDepartureTime) {

        boolean status = false;

        SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
        arrivalTime = "8:40 PM";
        returnDepartureTime = "1:40 PM";
        Date arrival = null;
        Date departureReturn = null;

        try {
            arrival = parseFormat.parse(arrivalTime);
            departureReturn = parseFormat.parse(returnDepartureTime);

            Log.e(arrival.toString(), departureReturn.toString());

        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
        }

        if (arrival.getTime() > departureReturn.getTime()) {
            status = true;
        }

        return status;
    }

    public boolean compare90Minute(String arrivalTime, String returnDepartureTime) {

        boolean status = false;

        SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");

        Date arrival = null;
        Date departureReturn = null;

        try {
            arrival = parseFormat.parse(arrivalTime);
            departureReturn = parseFormat.parse(returnDepartureTime);
        } catch (Exception e) {
        }

        long count90minute = arrival.getTime() - departureReturn.getTime();

        if (Math.abs(TimeUnit.MILLISECONDS.toMinutes(count90minute)) < 90) {
            status = true;
        }

        return status;
    }

    public boolean travellerAgeValidation(ArrayList<Integer> ageOfTraveller) {

        boolean lessThan12 = true;
        //checkAgeOfTraveller
        for (int y = 0; y < ageOfTraveller.size(); y++) {
            if (ageOfTraveller.get(y) > 12) {
                lessThan12 = false;
            }
        }
        return lessThan12;
    }

    public int travellerAge(String dob) {

        int age;

        String[] splitDOB = dob.split("/");
        String dobYear = splitDOB[2];

        boolean status = false;

        Calendar now = Calendar.getInstance();   // Gets the current date and time
        int currentYear = now.get(Calendar.YEAR);

        age = currentYear - Integer.parseInt(dobYear);

        return age;
    }

    public static String getCurrencyCode(Activity act, String departureCode) {

        String currencyCode = "";
        JSONArray sortedJsonArray = getFlight(act);

        for (int i = 0; i < sortedJsonArray.length(); i++) {
            JSONObject row = (JSONObject) sortedJsonArray.opt(i);
            if (row.optString("DepartureStation").equals(departureCode)) {
                currencyCode = row.optString("DepartureStationCurrencyCode");
                break;
            }
        }

        return currencyCode;
    }

    public static String getCurrencyCodeContext(Context act, String departureCode) {

        String currencyCode = "";
        JSONArray sortedJsonArray = getFlightContext(act);

        for (int i = 0; i < sortedJsonArray.length(); i++) {
            JSONObject row = (JSONObject) sortedJsonArray.opt(i);
            if (row.optString("DepartureStation").equals(departureCode)) {
                currencyCode = row.optString("DepartureStationCurrencyCode");
                break;
            }
        }

        return currencyCode;
    }

    public static ArrayList<DropDownItem> getPromoList(Activity activity, String type) {

        ArrayList<DropDownItem> promotionList = new ArrayList<DropDownItem>();
        ArrayList<String> added = new ArrayList<String>();
        Realm realm = RealmObjectController.getRealmInstance(activity);
        final RealmResults<PromotionCache> result2 = realm.where(PromotionCache.class).findAll();
        PromotionReceive promotionReceive = (new Gson()).fromJson(result2.get(0).getPromotion(), PromotionReceive.class);

        //sorting
        Collection<String> countryNames = new TreeSet<String>(Collator.getInstance());
        for (int v = 0; v < promotionReceive.getPromoList().size(); v++) {
            if (promotionReceive.getPromoList().get(v).getCategoryCode().equals(type)) {
                for (int f = 0; f < promotionReceive.getPromoList().get(v).getDetails().size(); f++) {
                    if (!countryNames.contains(promotionReceive.getPromoList().get(v).getDetails().get(f).getDeparture() + "/" + promotionReceive.getPromoList().get(v).getDetails().get(f).getDepartureCityCode())) {
                        countryNames.add(promotionReceive.getPromoList().get(v).getDetails().get(f).getDeparture() + "/" + promotionReceive.getPromoList().get(v).getDetails().get(f).getDepartureCityCode());
                        Log.e("CODE", promotionReceive.getPromoList().get(v).getDetails().get(f).getDepartureCityCode());
                    }
                }
            }
        }

        for (int j = 0; j < countryNames.size(); j++) {

            String[] departure = countryNames.toArray()[j].toString().split("/");
            DropDownItem item = new DropDownItem();
            item.setText(departure[0] + " (" + departure[1] + ")");
            item.setCode(departure[1]);
            promotionList.add(item);

        }

        return promotionList;
    }

    public static ArrayList<DropDownItem> getCountry(Activity act) {

        ArrayList<DropDownItem> countrys = new ArrayList<DropDownItem>();

        JSONArray json = null;

        prefManager = new SharedPrefManager(act);
        HashMap<String, String> init = prefManager.getCountry();
        String dataCountry = init.get(SharedPrefManager.COUNTRY);

        //original
        try {
            json = new JSONArray(dataCountry);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //sort here
        JSONArray sortedJsonArray = new JSONArray();
        List<JSONObject> jsonList = new ArrayList<JSONObject>();
        for (int i = 0; i < json.length(); i++) {
            try {
                jsonList.add(json.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Collections.sort(jsonList, new Comparator<JSONObject>() {

            public int compare(JSONObject a, JSONObject b) {
                String valA = new String();
                String valB = new String();

                try {
                    valA = (String) a.get("CountryName");
                    valB = (String) b.get("CountryName");
                } catch (JSONException e) {
                    //do something
                }

                return valA.compareTo(valB);
            }
        });

        for (int i = 0; i < json.length(); i++) {
            sortedJsonArray.put(jsonList.get(i));
        }

        for (int i = 0; i < json.length(); i++) {
            JSONObject row = (JSONObject) sortedJsonArray.opt(i);
            DropDownItem itemCountry = new DropDownItem();

            if (i == 0) {
                itemCountry.setText("Malaysia");
                itemCountry.setCode("MY/60");
                itemCountry.setTag("Country");
                countrys.add(itemCountry);
            } else {
                if (row.optString("CountryCode").equals("MY")) {
                    //do nothing
                } else {
                    itemCountry.setText(row.optString("CountryName"));
                    itemCountry.setCode(row.optString("CountryCode") + "/" + row.optString("DialingCode"));
                    itemCountry.setTag("Country");
                    countrys.add(itemCountry);
                }
            }
        }

        return countrys;
    }

    public static String[] getCharAt(List<String> countryChar) {

        String[] charToBeFilter = new String[countryChar.size()];
        for (int i = 0; i < countryChar.size(); i++) {
            charToBeFilter[i] = countryChar.get(i);
        }

        String[] newFilter = removeDuplicates(charToBeFilter);

        return newFilter;
    }

    public static Integer[] headerPosition(List<String> countryChar) {

        String[] charToBeFilter = new String[countryChar.size()];
        for (int i = 0; i < countryChar.size(); i++) {
            charToBeFilter[i] = countryChar.get(i);
        }

        Set<String> alreadyPresent = new HashSet<>();
        Integer[] whitelist = new Integer[charToBeFilter.length];
        int b = 0;
        int loop = 0;
        for (String element : charToBeFilter) {
            if (alreadyPresent.add(element)) {
                whitelist[b++] = loop;
            }
            loop++;
        }

        return Arrays.copyOf(whitelist, b);
    }

    public static String[] removeDuplicates(String[] arr) {
        Set<String> alreadyPresent = new HashSet<>();
        String[] whitelist = new String[arr.length];
        int i = 0;

        for (String element : arr) {
            if (alreadyPresent.add(element)) {
                whitelist[i++] = element;
            }
        }

        return Arrays.copyOf(whitelist, i);
    }

    public static ArrayList<DropDownItem> getGenderBase(Activity act) {

		/*Travelling Purpose*/
        ArrayList<DropDownItem> genderList = new ArrayList<DropDownItem>();

        final String[] gender = act.getResources().getStringArray(R.array.gender);
        for (int i = 0; i < gender.length; i++) {
            DropDownItem itemTitle = new DropDownItem();

            String[] genderSplit = gender[i].split("/");

            itemTitle.setText(genderSplit[0]);
            itemTitle.setCode(genderSplit[1]);
            genderList.add(itemTitle);
        }

        return genderList;
    }

    public static ArrayList<DropDownItem> getTitle(Activity act) {

        ArrayList<DropDownItem> title = new ArrayList<DropDownItem>();
        JSONArray json = null;

        prefManager = new SharedPrefManager(act);
        HashMap<String, String> init = prefManager.getTitle();
        String dataTitle = init.get(SharedPrefManager.TITLE);

        try {
            json = new JSONArray(dataTitle);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < json.length(); i++) {
            JSONObject row = (JSONObject) json.opt(i);

            DropDownItem itemCountry = new DropDownItem();
            itemCountry.setText(row.optString("Description"));
            itemCountry.setCode(row.optString("GenderCode"));
            itemCountry.setTag("Country");
            itemCountry.setId(i);
            title.add(itemCountry);
        }

        return title;
    }


    public static ArrayList<DropDownItem> getState(Activity act) {

        ArrayList<DropDownItem> state = new ArrayList<DropDownItem>();
        JSONArray json = null;

        prefManager = new SharedPrefManager(act);
        HashMap<String, String> init = prefManager.getState();
        String dataTitle = init.get(SharedPrefManager.STATE);

        try {
            json = new JSONArray(dataTitle);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < json.length(); i++) {
            JSONObject row = (JSONObject) json.opt(i);

            DropDownItem itemCountry = new DropDownItem();
            itemCountry.setText(row.optString("ProvinceStateName"));
            itemCountry.setCode(row.optString("ProvinceStateCode"));
            itemCountry.setTag("State");
            itemCountry.setId(i);
            state.add(itemCountry);
        }

        return state;
    }

    public static ArrayList<DropDownItem> getCountryOptionCall(Activity act) {

        ArrayList<DropDownItem> countryList = new ArrayList<DropDownItem>();
        JSONArray json = null;

        prefManager = new SharedPrefManager(act);
        HashMap<String, String> init = prefManager.getCountryCallOption();
        String data = init.get(SharedPrefManager.COUNTRY_CALL_OPTION);

        try {
            json = new JSONArray(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < json.length(); i++) {
            JSONObject row = (JSONObject) json.opt(i);

            DropDownItem itemCountry = new DropDownItem();
            itemCountry.setText(row.optString("CountryName"));
            itemCountry.setCode(row.optString("CountryCode"));
            countryList.add(itemCountry);
        }

        return countryList;
    }


    public static ArrayList<DropDownItem> getCountryOption(LanguageCountryReceive obj) {

        ArrayList<DropDownItem> countryOption = new ArrayList<DropDownItem>();
        int size = obj.getCountryList().size();

        for (int i = 0; i < size; i++) {

            DropDownItem itemCountry = new DropDownItem();
            itemCountry.setText(obj.getCountryList().get(i).getCountryName());
            itemCountry.setCode(obj.getCountryList().get(i).getCountryCode());
            itemCountry.setId(i);
            countryOption.add(itemCountry);
        }

        return countryOption;
    }

    public static ArrayList<DropDownItem> getLanguageOption(LanguageCountryReceive obj, String selectedCountryCode) {

        ArrayList<DropDownItem> languageOption = new ArrayList<DropDownItem>();
        int size = obj.getCountryList().size();

        for (int i = 0; i < size; i++) {

            if (selectedCountryCode.equals(obj.getCountryList().get(i).getCountryCode())) {
                int size2 = obj.getCountryList().get(i).getLanguageList().size();

                for (int z = 0; z < size2; z++) {

                    DropDownItem itemLanguage = new DropDownItem();
                    itemLanguage.setText(obj.getCountryList().get(i).getLanguageList().get(z).getLanguageName());
                    itemLanguage.setCode(obj.getCountryList().get(i).getLanguageList().get(z).getLanguageCode());
                    itemLanguage.setId(i);
                    languageOption.add(itemLanguage);
                }
                break;

            }

        }

        return languageOption;
    }

    public static ArrayList<DropDownItem> getNewsletterLanguage(Activity act) {

        ArrayList<DropDownItem> languageList = new ArrayList<DropDownItem>();
        JSONArray json = null;

        prefManager = new SharedPrefManager(act);
        HashMap<String, String> init = prefManager.getNewsletterLanguageList();
        String language = init.get(SharedPrefManager.NEWSLETTER_LANGUAGE_LIST);

        try {
            json = new JSONArray(language);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < json.length(); i++) {
            JSONObject row = (JSONObject) json.opt(i);

            DropDownItem itemCountry = new DropDownItem();
            itemCountry.setText(row.optString("LanguageName"));
            itemCountry.setCode(row.optString("LanguageCode"));
            itemCountry.setTag("Language");
            itemCountry.setId(i);
            languageList.add(itemCountry);
        }

        return languageList;
    }

    public void setShake(View view) {
        Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
        view.startAnimation(shake);
    }

    public void filterMoreText(final String originalTxt, final String filter, TextView check) {

        String insurance3 = filter.toString();

        int i1 = insurance3.indexOf("[m");
        int i2 = insurance3.indexOf("e]");

        Spannable mySpannable = (Spannable) check.getText();
        ClickableSpan myClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                    /* do something */
                moreText(originalTxt);
            }
        };
        mySpannable.setSpan(myClickableSpan, i1, i2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        check.setMovementMethod(LinkMovementMethod.getInstance());

    }

    public void moreText(String text) {

        Dialog moreTextDialog;

        LayoutInflater li = LayoutInflater.from(getActivity());
        final View thisView = li.inflate(R.layout.default_dialog, null);

        TextView moreTxt = (TextView) thisView.findViewById(R.id.moreTxt);
        moreTxt.setText(Html.fromHtml(text));
        moreTxt.setLinksClickable(true);
        moreTxt.setLinkTextColor(ContextCompat.getColor(getActivity(), R.color.textLinkColor));
        moreTxt.setMovementMethod(LinkMovementMethod.getInstance());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(thisView);

        //moreTextDialog = builder.create();
        moreTextDialog = new Dialog(getActivity(), R.style.DialogThemePush);
        moreTextDialog.setContentView(thisView);
        moreTextDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#80000000")));
        moreTextDialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(moreTextDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        moreTextDialog.getWindow().setAttributes(lp);
        moreTextDialog.show();

    }


    public static void setSuccessDialog(final Activity act, String msg, final Class<?> cls, String message) {

        if (cls == null) {
            new SweetAlertDialog(act, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText(message)
                    .setContentText(msg)
                    .show();
        } else {
            new SweetAlertDialog(act, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText(message)
                    .setContentText(msg)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            Intent explicitIntent = new Intent(act, cls);
                            explicitIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            explicitIntent.putExtra("AlertDialog", "Y");
                            act.startActivity(explicitIntent);
                            act.finish();
                            sDialog.dismiss();
                        }
                    })
                    .show();
        }
    }

    public static void setSuccessDialogNoClear(final Activity act, String msg, final Class<?> cls, String message) {

        if (cls == null) {
            new SweetAlertDialog(act, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText(message)
                    .setContentText(msg)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            act.finish();
                            sDialog.dismiss();
                        }
                    })
                    .show();
        }
    }


    public static void setAlertMaintance(final Activity act, String msg, final Class<?> cls, String message) {


        new SweetAlertDialog(act, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(message)
                .setContentText(msg)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        Intent explicitIntent = new Intent(act, cls);
                        explicitIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        //explicitIntent.putExtra("AlertDialog", "Y");
                        act.startActivity(explicitIntent);
                        act.finish();

                    }
                })
                .show();

    }

    public static void setAlertDialog(Activity act, String msg, String title) {

        if (act != null) {
            if (!act.isFinishing()) {
                new SweetAlertDialog(act, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText(title)
                        .setContentText(msg)
                        .show();
            } else {

            }
        }
    }

    public static String changeThousand(String newValue) {

        int x2 = Integer.parseInt(newValue);
        String str = String.format(Locale.US, "%,d", x2);
        return str;

    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public String doubleZero(String text) {

        double number = Double.parseDouble(text);
        //DecimalFormat format = new DecimalFormat("0.00");
        //return String.format("%,.2f", number);
        //format.format(number);

        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
        otherSymbols.setDecimalSeparator('.');
        otherSymbols.setGroupingSeparator(',');
        DecimalFormat df = new DecimalFormat("##0.00", otherSymbols);

        return df.format(number);

    }


    public static void setNormalDialog(Context act, String msg, String title) {
        new SweetAlertDialog(act, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText(title)
                .setContentText(msg)
                .show();

        //RealmObjectController.clearNotificationMessage(act);

    }


    public ArrayList<DropDownItem> getListOfYear(Activity act) {
        int totalMonth = 12;
        String monthToDisplay;
        ArrayList<DropDownItem> yearList = new ArrayList<DropDownItem>();

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);

        for (int yearX = year; yearX < year + 15; yearX++) {

            DropDownItem itemYear = new DropDownItem();
            itemYear.setText(Integer.toString(yearX));
            itemYear.setCode(Integer.toString(yearX));
            itemYear.setTag("Year");
            yearList.add(itemYear);
        }


        return yearList;

    }

    public ArrayList<DropDownItem> getListOfMonth(Activity act) {
        int totalMonth = 12;
        String monthToDisplay;
        ArrayList<DropDownItem> monthList = new ArrayList<DropDownItem>();

        for (int month = 1; month < totalMonth + 1; month++) {
            if (month < 10) {
                monthToDisplay = "0" + Integer.toString(month);
            } else {
                monthToDisplay = Integer.toString(month);
            }

            DropDownItem itemTitle = new DropDownItem();
            itemTitle.setText(monthToDisplay);
            itemTitle.setCode(monthToDisplay);
            itemTitle.setTag("Month");
            monthList.add(itemTitle);
        }


        return monthList;

    }

    public static void croutonAlert(Activity act, String msg) {
        Crouton.makeText(act, msg, Style.ALERT)
                .setConfiguration(new Configuration.Builder()
                        .setDuration(Configuration.DURATION_LONG).build())
                .show();
    }

    public static void initiateLoading(Activity act) {

        if (progressDialog != null) {
            progressDialog.dismiss();
        }

        progressDialog = new ProgressDialog(act);
        progressDialog.show();
        progressDialog.setMessage(AnalyticsApplication.getContext().getResources().getString(R.string.base_loading_ellipsis));
        progressDialog.setCancelable(false);

    }

    public static void dismissLoading() {

        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
                Log.e("Dismiss", "Y");
            }
        }
        Log.e("Dismiss", "N");
    }

    public void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
    }

    public void popupAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                    }
                });

        // Create the AlertDialog object and return it
        builder.create();
        builder.show();
    }


    /*Global PoPup*/
    public void popupSelection(final ArrayList array, Activity act, final TextView txt, final Boolean tagStatus, final View v, final String fee, final String amount, final String currency) {

        prefManager = new SharedPrefManager(act);
        //Utils.hideKeyboard(getActivity(), v);

        final ArrayList<DropDownItem> a = array;
        DropMenuAdapter dropState = new DropMenuAdapter(act);
        dropState.setItems(a);

        AlertDialog.Builder alertStateCode = new AlertDialog.Builder(act);

        alertStateCode.setSingleChoiceItems(dropState, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String selected = a.get(which).getText();
                String selectedCode = a.get(which).getCode();

                txt.setText(selected);

                if (a.get(which).getTag() == "CARD_TYPE") {
                    String[] splitDoc = selectedCode.split("-");

                    if (tagStatus) {
                        txt.setTag(splitDoc[0]);
                    }

                    //CHECK PROCESSING FEE
                    LinearLayout processingFeeInfoLayout = (LinearLayout) v.findViewById(R.id.processingFeeInfoLayout);
                    TextView txtTotalDue = (TextView) v.findViewById(R.id.txtTotalDue);
                    if (splitDoc[1].equals("Y")) {
                        processingFeeInfoLayout.setVisibility(View.VISIBLE);
                        txtTotalDue.setText(Double.toString(Double.parseDouble(fee) + Double.parseDouble(amount)) + "" + currency);

                    } else {
                        processingFeeInfoLayout.setVisibility(View.GONE);
                    }

                }

                indexForState = which;

                dialog.dismiss();
            }
        });


        AlertDialog mDialog = alertStateCode.create();
        mDialog.show();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(mDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //lp.horizontalMargin = 100;
        //lp.verticalMargin = 100;
        mDialog.getWindow().setAttributes(lp);
    }

    /*Global PoPup*/
    public void popupSelectionPaymentRoundFactor(final ArrayList array, Activity act, final TextView txt, final Boolean tagStatus, final View v, final String fee, final String amount, final String currency, final int displayDigit) {

        prefManager = new SharedPrefManager(act);
        //Utils.hideKeyboard(getActivity(), v);

        final ArrayList<DropDownItem> a = array;
        DropMenuAdapter dropState = new DropMenuAdapter(act);
        dropState.setItems(a);

        AlertDialog.Builder alertStateCode = new AlertDialog.Builder(act);

        alertStateCode.setSingleChoiceItems(dropState, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String selected = a.get(which).getText();
                String selectedCode = a.get(which).getCode();

                txt.setText(selected);

                if (a.get(which).getTag() == "CARD_TYPE") {
                    String[] splitDoc = selectedCode.split("-");

                    if (tagStatus) {
                        txt.setTag(splitDoc[0]);
                    }

                    //CHECK PROCESSING FEE
                    LinearLayout processingFeeInfoLayout = (LinearLayout) v.findViewById(R.id.processingFeeInfoLayout);
                    TextView txtTotalDue = (TextView) v.findViewById(R.id.txtTotalDue);
                    if (splitDoc[1].equals("Y")) {
                        processingFeeInfoLayout.setVisibility(View.VISIBLE);
                        double totalPlusProcessingFee = round(Double.parseDouble(fee) + Double.parseDouble(amount), displayDigit);
                        int intValue = (int) totalPlusProcessingFee;

                        if (displayDigit == 0) {
                            String p2 = Integer.toString(intValue);
                            txtTotalDue.setText(p2 + " " + currency);

                        } else {

                            /*String twoPoints = String.format("%.2f", totalPlusProcessingFee);*/
                            String twoPoints = doubleZero(String.valueOf(totalPlusProcessingFee));
                            txtTotalDue.setText(twoPoints + " " + currency);
                            /*txtTotalDue.setText(currency + "" + Double.toString(totalPlusProcessingFee));*/

                        }

                    } else {
                        if (displayDigit == 0) {

                            double totalPlusProcessingFee2 = round(Double.parseDouble(amount), displayDigit);
                            int intValue = (int) totalPlusProcessingFee2;
                            String twoPoints = Integer.toString(intValue);
                            txtTotalDue.setText(twoPoints + " " + currency);
                            processingFeeInfoLayout.setVisibility(View.GONE);

                        } else {

                            double totalPlusProcessingFee2 = round(Double.parseDouble(amount), displayDigit);
                            String twoPoints = doubleZero(String.valueOf(totalPlusProcessingFee2));
                            txtTotalDue.setText(twoPoints + " " + currency);
                            processingFeeInfoLayout.setVisibility(View.GONE);

                        }

                    }

                }

                indexForState = which;

                dialog.dismiss();
            }
        });


        AlertDialog mDialog = alertStateCode.create();
        mDialog.show();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(mDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //lp.horizontalMargin = 100;
        //lp.verticalMargin = 100;
        mDialog.getWindow().setAttributes(lp);
    }

    public String getCountryCode(Activity act, String countryData) {

        String countryCode = null;
        JSONArray json = null;

        prefManager = new SharedPrefManager(act);
        HashMap<String, String> init = prefManager.getCountry();
        String dataCountry = init.get(SharedPrefManager.COUNTRY);

        try {
            json = new JSONArray(dataCountry);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < json.length(); i++) {
            JSONObject row = (JSONObject) json.opt(i);

            if (countryData.equals(row.optString("country_name"))) {
                countryCode = row.optString("country_code");
            }
        }

        return countryCode;

    }

    public static String getCountryName(Activity act, String countryCode) {

        String countryName = null;
        JSONArray json = null;

        prefManager = new SharedPrefManager(act);
        HashMap<String, String> init = prefManager.getCountry();
        String dataCountry = init.get(SharedPrefManager.COUNTRY);

        try {
            json = new JSONArray(dataCountry);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < json.length(); i++) {
            JSONObject row = (JSONObject) json.opt(i);

            if (countryCode.equals(row.optString("CountryCode"))) {
                countryName = row.optString("CountryName");
            }
        }

        return countryName;

    }

    public static JSONArray getFlight(Activity act) {

        JSONArray json = null;

        prefManager = new SharedPrefManager(act);
        HashMap<String, String> init = prefManager.getRoute();
        String dataFlight = init.get(SharedPrefManager.ROUTE);

        try {
            json = new JSONArray(dataFlight);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }

    public static JSONArray getFlightContext(Context act) {

        JSONArray json = null;

        prefManager = new SharedPrefManager(act);
        HashMap<String, String> init = prefManager.getRoute();
        String dataFlight = init.get(SharedPrefManager.ROUTE);

        try {
            json = new JSONArray(dataFlight);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }

    public static String getFlightDetail(Activity act, String airportCode) {

        String flightInfo = "";
        JSONArray json = null;

        prefManager = new SharedPrefManager(act);
        HashMap<String, String> init = prefManager.getRoute();
        String dataFlight = init.get(SharedPrefManager.ROUTE);

        try {
            json = new JSONArray(dataFlight);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < json.length(); i++) {
            JSONObject row = (JSONObject) json.opt(i);
            if (row.optString("DepartureStation").equals(airportCode)) {
                //return name & country name
                flightInfo = row.optString("DepartureStationName") + "/-" + row.optString("DepartureCountryName");
            }
        }

        return flightInfo;
    }

    public static String getFlightAirportName(Activity act, String airportCode) {

        String airportName = "";
        JSONArray json = null;

        prefManager = new SharedPrefManager(act);
        HashMap<String, String> init = prefManager.getRoute();
        String dataFlight = init.get(SharedPrefManager.ROUTE);

        try {
            json = new JSONArray(dataFlight);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < json.length(); i++) {
            JSONObject row = (JSONObject) json.opt(i);
            if (row.optString("DepartureStation").equalsIgnoreCase(airportCode)) {
                //return name
                Log.e(airportName, airportCode);
                airportName = row.optString("DepartureStationName");
            }
        }

        return airportName;
    }

    public static String getFlightAirportNameContext(Context act, String airportCode) {

        String airportName = "";
        JSONArray json = null;

        prefManager = new SharedPrefManager(act);
        HashMap<String, String> init = prefManager.getRoute();
        String dataFlight = init.get(SharedPrefManager.ROUTE);

        try {
            json = new JSONArray(dataFlight);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < json.length(); i++) {
            JSONObject row = (JSONObject) json.opt(i);
            if (row.optString("DepartureStation").equalsIgnoreCase(airportCode)) {
                //return name
                Log.e(airportName, airportCode);
                airportName = row.optString("DepartureStationName");
            }
        }

        return airportName;
    }


    /*Return month in alphabet*/
    public static String getMonthAlphabet(int month) {
        return new DateFormatSymbols().getShortMonths()[month];
    }

    /*Get All Country From OS*/
   /* public JSONArray getCountry(Activity act) {
        JSONArray json = null;

        prefManager = new SharedPrefManager(act);
        HashMap<String, String> init = prefManager.getCountry();
        String dataCountry = init.get(SharedPrefManager.COUNTRY);

        try {
            json = new JSONArray(dataCountry);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;

		/*Locale[] locales = Locale.getAvailableLocales();
        ArrayList<String> countries = new ArrayList<String>();

		for (Locale locale : locales) {
			String country = locale.getDisplayCountry();
			String countryCode = locale.getCountry();


			if (country.trim().length()>0 && !countries.contains(country)) {
				countries.add(country+"-"+countryCode);
			}
		}

		Collections.sort(countries);
		return countries;*/
    //}


    /*Get All User Info From OS*/
    public JSONObject getUserInfo(Activity act) {
        JSONObject json = null;

        prefManager = new SharedPrefManager(act);
        HashMap<String, String> init = prefManager.getUserInfo();
        String userInfo = init.get(SharedPrefManager.USER_INFO);

        try {
            json = new JSONObject(userInfo);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;

    }

    public static String reformatDOB(String dob) {
        String date;

        if (dob != "") {
            String string = dob;
            String[] parts = string.split("-");
            String year = parts[0];
            String month = parts[1];
            String day = parts[2];
            date = day + "/" + (month) + "/" + year;
        } else {
            date = "";
        }
        return date;
    }


    public static String reformatDOB3(String dob) {
        String date;

        if (dob != "") {
            String string = dob;
            String[] parts = string.split("-");

            String year = parts[2];
            Log.e(dob, year);

            String month = parts[1];
            String day = parts[0];
            date = day + "/" + (month) + "/" + year;
        } else {
            date = "";
        }
        return date;
    }

    public String reformatDOB2(String dob) {
        String date;
        String string = dob;
        String[] parts = string.split("/");
        String day = parts[0];
        String month = parts[1];
        String year = parts[2];
        date = year + "-" + month + "-" + day;

        return date;
    }

    public String reformatDOB4(String dob) {
        String date;
        String string = dob;
        String[] parts = string.split("/");
        String day = parts[0];
        String month = parts[1];
        String year = parts[2];
        date = day + "-" + month + "-" + year;

        return date;
    }


    /*Get All User Info From OS*/
    public JSONObject getCheckinInfo(Activity act) {
        JSONObject json = null;

        prefManager = new SharedPrefManager(act);
        HashMap<String, String> init = prefManager.getCheckinInfo();
        String checkinInfo = init.get(SharedPrefManager.CHECKIN_INFO);

        try {
            json = new JSONObject(checkinInfo);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;

    }

    public String getStateName(Activity act, String stateCode) {

        String stateName = null;
        JSONArray json = null;

        prefManager = new SharedPrefManager(act);
        HashMap<String, String> init = prefManager.getState();
        String dataState = init.get(SharedPrefManager.STATE);

        try {
            json = new JSONArray(dataState);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //JSONArray jsonState = getState(act);
        for (int x = 0; x < json.length(); x++) {

            JSONObject row = (JSONObject) json.opt(x);
            if (stateCode.equals(row.optString("ProvinceStateCode"))) {
                stateName = row.optString("ProvinceStateName");
            }
        }

        return stateName;

    }


    public static void setAlertNotification(Activity act) {

        dismissLoading();
        if (MainController.connectionAvailable(act)) {
            setAlertDialog(act, AnalyticsApplication.getContext().getString(R.string.base_connection_server), AnalyticsApplication.getContext().getString(R.string.base_connection_error));
        } else {
            setAlertDialog(act, AnalyticsApplication.getContext().getString(R.string.base_no_internet), AnalyticsApplication.getContext().getString(R.string.base_connection_error));
        }
    }

    /*Get From OS*/
    public JSONArray getTermInfo(Activity act) {
        JSONArray json = null;

        prefManager = new SharedPrefManager(act);
        HashMap<String, String> init = prefManager.getTermInfo();
        String dataTerm = init.get(SharedPrefManager.TERM_INFO);

        try {
            json = new JSONArray(dataTerm);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        aq = new com.app.tbd.base.AQuery(getActivity());
    }

    public static void displayImage(Activity act, ImageView imgView, String url) {
        Glide.with(act).load(url)
                .crossFade()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgView);
    }

	/*public void showUTCError(String msg)
    {
		Activity activity = getActivity();
		if (activity instanceof MainFragmentActivity)
		{
			MainFragmentActivity myactivity = (MainFragmentActivity) activity;
			myactivity.unableToConnectToServer(msg);
		}
	}*/

    public boolean isNetworkAvailable(Activity activity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public String checkResultCached(Activity act) {

        HashMap<String, String> initResult = prefManager.getTempResult();
        String tempResult = initResult.get(SharedPrefManager.TEMP_RESULT);

        try {
        } catch (Exception e) {

        }

        return tempResult;
    }

    //Cached Result In GSON String
    public static void tempResult(String cachedResult) {

        prefManager.setTempResult(cachedResult);

    }


    public static void deepLink(Activity act) {

        Log.e("DeepLink", "Y");
        Realm realm = RealmObjectController.getRealmInstance(act);
        final RealmResults<PushNotificationKey> result2 = realm.where(PushNotificationKey.class).findAll();
        Log.e("result2", String.valueOf(result2));

        if (result2.size() == 0) {

            TabActivity.setPager(1);
            Intent notificationPage = new Intent(act, PushNotificationActivity.class);
            act.startActivity(notificationPage);

        } else {
            Log.e("XX", "PAPE2");
            String keyToSplit = result2.get(0).getCachedKey();
            Log.e("keyToSplit", "keytosplit " + keyToSplit);

            String[] parts = keyToSplit.split("/");

            Boolean promo = false;
            for (int f = 0; f < parts.length; f++) {
                if (parts[f].equals("promos")) {
                    promo = true;
                }
            }

            String[] flightType = parts[1].split(":");
            String[] departCode = parts[2].split(":");
            String[] arrivalCode = parts[3].split(":");

            String departName = getFlightAirportName(act, departCode[1]);
            String arrivalName = getFlightAirportName(act, arrivalCode[1]);

            Log.e("Airport Name", departName + " + " + arrivalName);

            if (promo) {
                //with information
                PromoTransaction promoTransaction = new PromoTransaction();
                promoTransaction.setArrivalCode(arrivalCode[1]);
                promoTransaction.setDepartureCode(departCode[1]);
                promoTransaction.setDepartText(departName + "(" + departCode[1] + ")");
                promoTransaction.setArrivalText(arrivalName + "(" + arrivalCode[1] + ")");
                promoTransaction.setFlightType(flightType[1]);
                promoTransaction.setFlightCode("FG");

                //check currency
                String currencyCode = getCurrencyCode(act, departCode[1]);
                promoTransaction.setDepartureCurrencyCode(currencyCode);


                //redirect
                Gson gsonUserInfo = new Gson();
                String promoTransactionGSON = gsonUserInfo.toJson(promoTransaction);

                Intent explicitIntent = new Intent(act, RedirectActivity.class);
                explicitIntent.putExtra("REDIRECT", promoTransactionGSON);
                act.startActivity(explicitIntent);

            }

        }
    }


    public static void deepLinkContext(Context act) {

        Log.e("DeepLink", "Y");
        Realm realm = RealmObjectController.getRealmInstanceContext(act);
        final RealmResults<PushNotificationKey> result2 = realm.where(PushNotificationKey.class).findAll();
        Log.e("result2", String.valueOf(result2));

        if (result2.size() == 0) {

            TabActivity.setPager(1);
            Intent notificationPage = new Intent(act, PushNotificationActivity.class);
            act.startActivity(notificationPage);

        } else {
            Log.e("XX", "PAPE2");
            String keyToSplit = result2.get(0).getCachedKey();
            Log.e("keyToSplit", "keytosplit " + keyToSplit);

            String[] parts = keyToSplit.split("/");

            Boolean promo = false;
            for (int f = 0; f < parts.length; f++) {
                if (parts[f].equals("promos")) {
                    promo = true;
                }
            }

            String[] flightType = parts[1].split(":");
            String[] departCode = parts[2].split(":");
            String[] arrivalCode = parts[3].split(":");

            String departName = getFlightAirportNameContext(act, departCode[1]);
            String arrivalName = getFlightAirportNameContext(act, arrivalCode[1]);

            Log.e("Airport Name", departName + " + " + arrivalName);

            if (promo) {
                //with information
                PromoTransaction promoTransaction = new PromoTransaction();
                promoTransaction.setArrivalCode(arrivalCode[1]);
                promoTransaction.setDepartureCode(departCode[1]);
                promoTransaction.setDepartText(departName + "(" + departCode[1] + ")");
                promoTransaction.setArrivalText(arrivalName + "(" + arrivalCode[1] + ")");
                promoTransaction.setFlightType(flightType[1]);
                promoTransaction.setFlightCode("FG");

                //check currency
                String currencyCode = getCurrencyCodeContext(act, departCode[1]);
                promoTransaction.setDepartureCurrencyCode(currencyCode);

                TabActivity.setPager(2);

                Log.e("Redirect to page 2", "Y");
                SearchFlightFragment.triggerPromoInfoContext(act, promoTransaction);

                //redirect
                //Gson gsonUserInfo = new Gson();
                //
                // String promoTransactionGSON = gsonUserInfo.toJson(promoTransaction);

                //Intent explicitIntent = new Intent(act, RedirectActivity.class);
                //explicitIntent.putExtra("REDIRECT", promoTransactionGSON);
                //act.startActivity(explicitIntent);

            }

        }
        //with information
        /*PromoTransaction promoTransaction = new PromoTransaction();
        promoTransaction.setArrivalCode(redeemBtn.getTag().toString());
        promoTransaction.setDepartureCode(txtBeforePoints.getTag().toString());
        promoTransaction.setDepartText(txtCurrentPtsForPromo.getTag().toString());
        promoTransaction.setArrivalText(txtArrivalStation.getTag().toString());
        promoTransaction.setFlightType(flightType.getTag().toString());
        promoTransaction.setFlightCode("FG");*/

        /*Realm realm = RealmObjectController.getRealmInstanceContext(act);
        final RealmResults<PushNotificationKey> result2 = realm.where(PushNotificationKey.class).findAll();

        String keyToSplit = result2.get(0).getCachedKey();
        //String keyToSplit = "promos/type:return/from:KUL:Kuala Lumpur/to:SIN:Singapore";
        String[] parts = keyToSplit.split("/");

        Boolean promo = false;
        for (int f = 0; f < parts.length; f++) {
            if (parts[f].equals("promos")) {
                promo = true;
            }
        }

        String[] flightType = parts[1].split(":");
        String[] departCode = parts[2].split(":");
        String[] arrivalCode = parts[3].split(":");

        if (promo) {
            //with information
            PromoTransaction promoTransaction = new PromoTransaction();
            promoTransaction.setArrivalCode(arrivalCode[1]);
            promoTransaction.setDepartureCode(departCode[1]);
            promoTransaction.setDepartText(departCode[1] + "(" + departCode[1] + ")");
            promoTransaction.setArrivalText(arrivalCode[1] + "(" + arrivalCode[1] + ")");
            promoTransaction.setFlightType(flightType[1]);
            promoTransaction.setFlightCode("FX");

            //check currency
            String currencyCode = getCurrencyCodeContext(act, departCode[1]);
            promoTransaction.setDepartureCurrencyCode(currencyCode);

            TabActivity.setPager(2);

            Log.e("Redirect to page 2", "Y");
            SearchFlightFragment.triggerPromoInfoContext(act, promoTransaction);
        }*/
    }

    public static void setPushNotificationAlert(Activity act, String message, String title) {

        LayoutInflater li = LayoutInflater.from(act);
        final View myView = li.inflate(R.layout.push_notification_alert, null);
        Button cont = (Button) myView.findViewById(R.id.push_close_btn);
        TextView pushTitle = (TextView) myView.findViewById(R.id.push_content);
        TextView pushMessage = (TextView) myView.findViewById(R.id.push_title);

        pushTitle.setText(message);
        pushMessage.setText(title);


        dialog = new Dialog(act, R.style.DialogThemePush);
        //dialog = builder.create();
        dialog.setContentView(myView);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#80000000")));
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //lp.height = 570;
        dialog.getWindow().setAttributes(lp);
        dialog.show();

        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }

        });

    }


    protected static void replaceFont(String staticTypefaceFieldName,
                                      final Typeface newTypeface) {
        try {
            final Field staticField = Typeface.class
                    .getDeclaredField(staticTypefaceFieldName);
            staticField.setAccessible(true);
            staticField.set(null, newTypeface);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void saveNotificationInbox(Activity act, String message, String title, String date, String body, String status, String messageID, String type, String id) {

        //save push notification message
        GCMClass gcmClass = new GCMClass();
        gcmClass.setTitle(title);
        gcmClass.setBody(body);
        gcmClass.setDate(date);
        gcmClass.setMessage(message);
        gcmClass.setStatus(status);
        gcmClass.setMessageID(messageID);
        gcmClass.setType(type);
        gcmClass.setId(id);

        RealmInboxNotification.notificationAddAllMessages(act, gcmClass);

    }

    public void saveOverlayInbox(Activity act, String message, String title, String date, String messageID) {

        //save overlay message
        GCMClass gcmClass = new GCMClass();
        gcmClass.setTitle(title);
        gcmClass.setMessage(message);
        gcmClass.setDate(date);
        gcmClass.setMessageID(messageID);
        RealmInboxNotification.notificationAddListOverlay(act, gcmClass);

    }

    public static String getCurrentTimeStamp() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);

        Log.e("getCurrentTimeStamp", strDate);
        return strDate;
    }

    public Date dateString(String dateInString) {

        String dtStart = dateInString;
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
        Date date = new Date();
        try {
            date = format.parse(dtStart);
            System.out.println(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }


    public String stringDate(String dateStr) {
        DateFormat fromFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        fromFormat.setLenient(false);
        DateFormat toFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
        toFormat.setLenient(false);

        Date date = null;
        try {
            date = fromFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        /*Log.e("String Date", toFormat.format(date));*/

        return toFormat.format(date);
    }

}
