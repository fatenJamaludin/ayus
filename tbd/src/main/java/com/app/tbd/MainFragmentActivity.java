package com.app.tbd;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidquery.util.AQUtility;
import com.app.tbd.base.AQuery;
import com.app.tbd.base.BaseFragmentActivity;
import com.app.tbd.utils.SharedPrefManager;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Locale;

import butterknife.ButterKnife;

//import com.actionbarsherlock.view.Menu;
//import com.actionbarsherlock.view.MenuItem;

public class MainFragmentActivity extends BaseFragmentActivity {

    private static Activity instance;
    /**
     * Fragment managing the behaviors, interactions and presentation of the
     * navigation drawer.
     */
    private SharedPrefManager pref;

    public static Activity setContext(Activity act) {
        instance = act;
        return instance;

    }

    public static Activity getContext() {
        //return instance.getApplicationContext();
        //return ActivityName.this;
        return instance;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pref = new SharedPrefManager(this);

        changeLanguage();
        setContentView(R.layout.activity_main);
        aq = new AQuery(this);
        ButterKnife.inject(this);
        instance = this;
        /*View v = this.getView();*/
        /*LayoutInflater layoutInflater = LayoutInflater.from(this);
        View v = layoutInflater.inflate(R.layout.activity_main, null);

        overrideFonts(this, v);*/

        /*setDefaultFont(this, "GRUNDSCHRIFT", "fonts/grundschrift_regular.otf");*/

        /*Typeface face = Typeface.createFromAsset(getAssets(), "font.otf");*/

        /*Testing*/
        //moveDrawerToTop();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);
        // mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));

        //FragmentManager fragmentManager = getSupportFragmentManager();
        //fragmentManager.beginTransaction().replace(R.id.tab_container, TabButtomFragment.newInstance()).commit();

    }

    private void changeLanguage() {
        HashMap<String, String> code = pref.getSavedLanguageSCode();
        String languageCode = code.get(SharedPrefManager.SAVED_S_LANGUAGE);

        String codeToUse;
        if (languageCode == null) {
            codeToUse = "en";
            /*Log.e("languageCode", languageCode);*/
        } else if (languageCode.equalsIgnoreCase("tt")) {
            codeToUse = "th";
        } else {
            codeToUse = languageCode;
        }

        Locale locale = new Locale(codeToUse);
        Locale.setDefault(locale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = locale;
        this.getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }

    private void setFontToAllChilds(ViewGroup myMostParentLayout, Typeface tf) {
        int childCount = myMostParentLayout.getChildCount();
        for (int i = 0; i < childCount; ++i) {
            View child = myMostParentLayout.getChildAt(i);

            if (child instanceof ViewGroup) {
                setFontToAllChilds((ViewGroup) child, tf);
            } else if (child instanceof TextView) {
            }
            ((TextView) child).setTypeface(tf);
            Log.e("Here", "OK");
        }

    }

    /*private void overrideFonts(final Context context, final View v) {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    overrideFonts(context, child);
                }
            } else if (v instanceof TextView ) {
                Log.e("TEST FONT", "IN");
                ((TextView) v).setTypeface(Typeface.createFromAsset(context.getAssets(), "play_regular.ttf"));
            }
        } catch (Exception e) {
        }
    }*/

    public static void setDefaultFont(Context context,
                                      String staticTypefaceFieldName, String fontAssetName) {
        final Typeface regular = Typeface.createFromAsset(context.getAssets(),
                fontAssetName);
        replaceFont(staticTypefaceFieldName, regular);
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

    private void initActionBar() {

    }

    private void moveDrawerToTop() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        DrawerLayout drawer = (DrawerLayout) inflater.inflate(R.layout.decor3, null); // "null" is important.

        // HACK: "steal" the first child of decor view
        ViewGroup decor = (ViewGroup) getWindow().getDecorView();
        View child = decor.getChildAt(0);
        decor.removeView(child);
        LinearLayout container = (LinearLayout) drawer.findViewById(R.id.main_activity_fragment_container); // This is the container we defined just now.
        container.addView(child, 0);
        drawer.findViewById(R.id.navigation_drawer).setPadding(0, getStatusBarHeight(), 0, 0);

        // Make the drawer replace the first child
        decor.addView(drawer);
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();

        // if (isTaskRoot()) {
        // clean the file cache with advance option
        long triggerSize = 300000; // starts cleaning when cache size is
        // larger than 3M
        long targetSize = 200000; // remove the least recently used files
        // until cache size is less than 2M
        AQUtility.cleanCacheAsync(this, triggerSize, targetSize);
        // }
    }

    ///  public void hideTabButton() {
    //     aq.id(R.id.tab_container).gone();
    // }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        // actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        // actionBar.setDisplayShowTitleEnabled(true);
        // actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        /*int id = item.getItemId();
        switch (id)
        {
            case R.id.action_settings:
                break;

            default:
                break;
        }*/
        return super.onOptionsItemSelected(item);
    }

}
