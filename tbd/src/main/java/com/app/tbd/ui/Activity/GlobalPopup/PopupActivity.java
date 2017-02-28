package com.app.tbd.ui.Activity.GlobalPopup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class PopupActivity extends Activity {

    private String msj;
    private String title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null){
            msj = bundle.getString("msj");
            title = bundle.getString("title");

        }

        setAlertDialog(this,msj,title);
    }

    public static void setAlertDialog(Activity act, String msg, String title) {

        if (act != null) {
                new SweetAlertDialog(act, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText(title)
                        .setContentText(msg)
                        .show();

        }
    }

}
