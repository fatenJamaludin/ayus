package com.app.tbd.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.provider.Settings.Secure;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.app.tbd.R;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Utils {


    //Static Reference

    public static final String SEARCH_FLIGHT = "SEARCH_FLIGHT";
    public static final String SEARCH_FLIGHT_SIGNATURE = "SEARCH_FLIGHT_SIGNATURE";
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 2;
    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 3;



    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPermissionGallery(final Context context)
    {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if(currentAPIVersion>=android.os.Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPermissionCamera(final Context context)
    {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if(currentAPIVersion>=android.os.Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.CAMERA)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPermissionWrite(final Context context)
    {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if(currentAPIVersion>=android.os.Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }






























    public static void launchBrowser(Context context, String url) {
        try {
            Uri uriUrl = Uri.parse(url);
            Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
            context.startActivity(launchBrowser);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void launchNavigator(Context context, String url) {
        try {
            Uri uriUrl = Uri.parse(url);
            Intent navigator = new Intent(Intent.ACTION_VIEW, uriUrl);
            context.startActivity(navigator);

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String getDeviceType(Activity activity) {
        String deviceType = null;
        if ((activity.getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE) {
            deviceType = "1";
        } else {
            deviceType = "2";
        }

        return deviceType;

    }

    public static void launchNavigator(Context context, double lat, double lng) {
        try {
            Intent navIntent = new Intent(Intent.ACTION_VIEW);
            navIntent.setData(Uri.parse(String.format("geo: %s,%s", lat, lng)));
            context.startActivity(navIntent);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String bitmapToBase64(Bitmap b) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    /*public static void makeCall(Context context, String phone) {
        try {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel: " + phone));
            context.startActivity(callIntent);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }*/

    public static void addCalendar(Context context, String title, String location, String description, Date startDate, Date endDate) {
        try {
            Intent calIntent = new Intent(Intent.ACTION_EDIT);
            calIntent.setType("vnd.android.cursor.item/event");
            calIntent.putExtra(Events.TITLE, title);
            calIntent.putExtra(Events.EVENT_LOCATION, location);
            calIntent.putExtra(Events.DESCRIPTION, description);

            calIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false);
            calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startDate.getTime());
            calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endDate.getTime());
            context.startActivity(calIntent);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void alert(Context context, String title, String message, String yesText, String noText, DialogInterface.OnClickListener dialogClickListener) {
        yesText = (yesText == null || yesText.isEmpty()) ? "Yes" : yesText;
        noText = (noText == null || noText.isEmpty()) ? "No" : noText;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(yesText, dialogClickListener);
        builder.setNegativeButton(noText, dialogClickListener);
        builder.show();
    }

    /* Get width number by percentage of this device screen */
    public static int getScreenWidthByPercent(Activity activity, int percent) {
        ScreenDensity dpi = new ScreenDensity(activity);
        return (percent / 100) * dpi.getWidth();
    }

    /* Get height number by percentage of this device screen */
    public static int getScreenHeightByPercent(Activity activity, int percent) {
        ScreenDensity dpi = new ScreenDensity(activity);
        return (percent / 100) * dpi.getHeight();
    }

    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return (Environment.MEDIA_MOUNTED.equals(state));
    }

    /* Checks if external storage is available to at least read */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state));
    }

    public static void getScreenDpi(Context context) {
        int screenSize = context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        String toastMsg = "";
        switch (screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                toastMsg = "Large screen ";
                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                toastMsg = "Normal screen ";
                break;
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                toastMsg = "Small screen ";
                break;
            default:
                toastMsg = "Screen size is neither large, normal or small";
        }

        switch (context.getResources().getDisplayMetrics().densityDpi) {
            case DisplayMetrics.DENSITY_LOW:
                // handle your code here for ldpi
                toastMsg += "ldpi";
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                // handle your code here for mdpi
                toastMsg += "mdpi";
                break;
            case DisplayMetrics.DENSITY_HIGH:
                // handle your code here for hdpi
                toastMsg += "hdpi";
                break;
            case DisplayMetrics.DENSITY_XHIGH:
                // handle your code here for xhdpi
                toastMsg += "xhdpi";
                break;
        }

        Toast.makeText(context, toastMsg, Toast.LENGTH_LONG).show();
    }

    public static void log(String text) {
        File logFile = new File("sdcard/log.txt");
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            // BufferedWriter for performance, true to set append to file flag
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            buf.append(text);
            buf.newLine();
            buf.newLine();
            buf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void hideKeyboard(Activity activity, View v) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public static void toastNotification(Activity activity, String msg) {
        Context context = activity;
        CharSequence text = msg;
        int duration = Toast.LENGTH_SHORT;

        Toast toast1 = Toast.makeText(context, text, duration);
        toast1.show();

        /*final Toast toast2 = Toast.makeText(context, text, duration);
        toast2.show();

        Handler toast_handler = new Handler();
        toast_handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast2.show();
            }
        }, duration - 50);
        */



    }

    public static void toastNotificationLong(Activity activity, String msg) {
        Context context = activity;
        CharSequence text = msg;
        int duration = Toast.LENGTH_LONG;

        Toast toast1 = Toast.makeText(context, text, duration);
        toast1.show();

        /*final Toast toast2 = Toast.makeText(context, text, duration);
        toast2.show();

        Handler toast_handler = new Handler();
        toast_handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast2.show();
            }
        }, duration - 50);
        */



    }



    public static String getRandomImageUrl() {
        Random r = new Random();
        String[] images = {"http://icons.iconarchive.com/icons/webalys/kameleon.pics/128/Alien-icon.png",
                "http://icons.iconarchive.com/icons/webalys/kameleon.pics/128/Analytics-icon.png",
                "http://icons.iconarchive.com/icons/webalys/kameleon.pics/128/Apartment-icon.png",
                "http://icons.iconarchive.com/icons/webalys/kameleon.pics/128/Battery-Charging-icon.png",
                "http://icons.iconarchive.com/icons/webalys/kameleon.pics/128/Batman-icon.png",
                "http://icons.iconarchive.com/icons/webalys/kameleon.pics/128/Bag-Present-icon.png",
                "http://icons.iconarchive.com/icons/webalys/kameleon.pics/128/Boss-2-icon.png",
                "http://icons.iconarchive.com/icons/webalys/kameleon.pics/128/Burglar-icon.png",
                "http://icons.iconarchive.com/icons/webalys/kameleon.pics/128/Camera-Front-icon.png",};
        return images[r.nextInt(images.length)];
    }

    public static ShapeDrawable getRoundedButton(Activity activity, float radius, int color) {
        float px = convertDpInt(activity, radius);
        float[] outerR = new float[]{px, px, px, px, px, px, px, px};
        RoundRectShape rr = new RoundRectShape(outerR, null, null);
        ShapeDrawable sd = new ShapeDrawable(rr);
        sd.getPaint().setColor(color);
        return sd;
    }

    public static int convertDpInt(Activity activity, float value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, activity.getResources().getDisplayMetrics());
    }

    public static String getVersion(Activity activity) {
        String version = "";
        try {
            PackageInfo packageInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
            version = String.format("v%s r%s", packageInfo.versionName, packageInfo.versionCode);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    public static String addressFormater(String street1, String street2, String street3) {
        String address, s1 = null, s2 = null, s3 = null;
        String returnAddress;

        if (!street1.equals("")) {
            s1 = street1;
        }
        if (!street2.equals("")) {
            s2 = "\n" + street2;
        }
        if (!street3.equals("")) {
            s3 = "\n" + street3;
        }
        address = s1 + s2 + s3;
        returnAddress = address.replace("null", "");

        return returnAddress;
    }

    public static String replaceAMP(String code) {
        String test = code.replace("&amp;", "&");
        return test;
    }

    public static void showAlert(Activity activity, String string) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = LayoutInflater.from(activity);
        View alertView = inflater.inflate(R.layout.error_popup, null);
        TextView textError = (TextView) alertView.findViewById(R.id.text_error);
        textError.setText(string);
        builder.setView(alertView);

        builder.show();
    }

    public static boolean isNetworkAvailable(Activity activity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /*public static String getFullMonth(Activity activity, String month) {
        String thisMonth = month;
        String subMonth = null;
        String monthName = null;
        String[] month_name = activity.getResources().getStringArray(R.array.full_month);
        for (int i = 0; i < month_name.length; i++) {

            if (Integer.parseInt(thisMonth) < 10) {
                subMonth = thisMonth.substring(1).trim();
            } else {
                subMonth = month;
            }
            if (Integer.parseInt(subMonth) == i + 1) {
                monthName = month_name[i];
            }
        }
        return monthName;
    }*/

    /*public static String get3alphabetMonth(Activity activity, String month) {
        String thisMonth = month;
        String subMonth = null;
        String monthName = null;
        String[] month_name = activity.getResources().getStringArray(R.array.month);
        for (int i = 0; i < month_name.length; i++) {

            if (Integer.parseInt(thisMonth) < 10) {
                subMonth = thisMonth.substring(1).trim();
            } else {
                subMonth = month;
            }
            if (Integer.parseInt(subMonth) == i + 1) {
                monthName = month_name[i];
            }
        }
        return monthName;
    }*/

    public static int getNDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);

        return year;
    }

    public static String trim(String string) {
        if (string.contains("anyType{}")) {
            return string.replace("anyType{}", "");
        }

        return string;
    }

    public static class ScreenDensity {
        private int width = 0;
        private int height = 0;

        @SuppressWarnings("deprecation")
        public ScreenDensity(Activity activity) {
            Display display = activity.getWindowManager().getDefaultDisplay();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                Point size = new Point();
                display.getSize(size);
                width = size.x;
                height = size.y;
            } else {
                width = display.getWidth(); // deprecated
                height = display.getHeight(); // deprecated
            }
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }
    }

    public static int convertDPToPixel(Context context, int dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        int px = (int) (dp * scale + 0.5f);

        return px;
    }

    public static String formatDateAndTime(String DateAndTime) {

        String newFormat = null;
        SimpleDateFormat inputDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz");
        SimpleDateFormat formattedDate = new SimpleDateFormat("yyyy/MM/dd HH:mm");

        try {
            newFormat = formattedDate.format(inputDF.parse(DateAndTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return newFormat;
    }

    public static String formatDate(String Date) {

        String newFormatDate = null;
        SimpleDateFormat inputDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz");
        SimpleDateFormat formattedDate = new SimpleDateFormat("dd/MM/yyyy");

        try {
            newFormatDate = formattedDate.format(inputDF.parse(Date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return newFormatDate;
    }
}

