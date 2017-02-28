package com.app.tbd.utils;

import android.util.Log;

import com.app.tbd.base.BaseFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Dell on 3/17/2016.
 */
public class LocalNotification extends BaseFragment {

    public static Boolean dateCompare(String date){

        boolean status = false;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);


        Date date1 = null;
        try {
            date1 = sdf.parse(date);
        }catch (Exception e){
            Log.e("message",e.getMessage());
        }

        if(date1.before(cal.getTime())){
            status = true;
        }

        return status;
    }

    public static Boolean compare(String x1,String x2){

        boolean status = false;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date date1 = null;
        Date date2 = null;
        try {
            date1 = sdf.parse(x1);
            date2 = sdf.parse(x2);

        }catch (Exception e){
        }

        if(date2.before(date1)){
            status = true;
        }

        return status;
    }


}
