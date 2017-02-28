package com.app.tbd.ui.Activity.Profile.QRCode;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.tbd.MainController;
import com.app.tbd.MainFragmentActivity;
import com.app.tbd.application.MainApplication;
import com.app.tbd.ui.Activity.Profile.BigPoint.BigPointBaseActivity;
import com.app.tbd.ui.Activity.Profile.BigPoint.TransactionHistoryActivity;
import com.app.tbd.ui.Activity.Profile.Option.OptionsActivity;
import com.app.tbd.ui.Activity.Profile.UserProfile.MyProfileActivity;
import com.app.tbd.ui.Model.Receive.TBD.BigPointReceive;
import com.app.tbd.ui.Model.Receive.TBD.BigPointReceiveFailed;
import com.app.tbd.ui.Model.Receive.TransactionHistoryReceive;
import com.app.tbd.ui.Model.Receive.ViewUserReceive;
import com.app.tbd.ui.Model.Request.TBD.BigPointRequest;
import com.app.tbd.ui.Model.Request.TransactionHistoryRequest;
import com.app.tbd.ui.Model.Request.ViewUserRequest;
import com.app.tbd.ui.Module.ProfileModule;
import com.app.tbd.ui.Presenter.ProfilePresenter;
import com.google.gson.Gson;
import com.app.tbd.R;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Model.JSON.UserInfoJSON;
import com.app.tbd.ui.Model.Receive.TBD.LoginReceive;
import com.app.tbd.ui.Realm.RealmObjectController;
import com.app.tbd.utils.SharedPrefManager;
import com.google.zxing.WriterException;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.mobsandgeeks.saripaar.Validator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import io.realm.Realm;
import io.realm.RealmResults;

import static android.content.Context.WINDOW_SERVICE;

public class QRCodeFragment extends BaseFragment {

    @InjectView(R.id.qrcodeImage)
    ImageView qrcodeImage;

    @InjectView(R.id.txtBigShotID)
    TextView txtBigShotID;

    @InjectView(R.id.txtBigShotName)
    TextView txtBigShotName;

    public static QRCodeFragment newInstance(Bundle bundle) {

        QRCodeFragment fragment = new QRCodeFragment();
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RealmObjectController.clearCachedResult(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.qrcode_layout, container, false);
        ButterKnife.inject(this, view);

        dataSetup();


        return view;
    }


    public void dataSetup() {


        Bundle bundle = getArguments();

        WindowManager manager = (WindowManager) getActivity().getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;

        String bigShotID = bundle.getString("BIG_SHOT_ID");
        String bigShotName = bundle.getString("BIG_SHOT_NAME");
        String nameText = getString(R.string.profile_name);
        String bigText = getString(R.string.profile_bigshot_id);

        txtBigShotName.setText( nameText + " : " + bigShotName);
        txtBigShotID.setText(bigText + " " + bigShotID);

        // Initializing the QR Encoder with your value to be encoded, type you required and Dimension
        QRGEncoder qrgEncoder = new QRGEncoder(bigShotID, null, QRGContents.Type.TEXT, smallerDimension);

        try {
            Bitmap bitmap = qrgEncoder.encodeAsBitmap();
            qrcodeImage.setImageBitmap(bitmap);

        } catch (WriterException e) {
            //Log.v(TAG, e.toString());
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*if (resultCode != 0)
          return;*/
    }

    @Override
    public void onResume() {
        super.onResume();

    }


    @Override
    public void onPause() {
        super.onPause();
    }

}
