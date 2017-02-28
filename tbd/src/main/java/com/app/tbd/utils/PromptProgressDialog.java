package com.app.tbd.utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by N0695 on 28/5/2015.
 */
public class PromptProgressDialog {
    private static ProgressDialog progressDialog;
    private static boolean isShowing = false;

    public static void promptProgressDialog(Context context) {
        if (!isShowing) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMessage("Loading");
            progressDialog.show();
            isShowing = true;
        }
    }

    public static void dismissProgressDialog() {
        if (isShowing) {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
                isShowing = false;
            }
        }
    }
}
