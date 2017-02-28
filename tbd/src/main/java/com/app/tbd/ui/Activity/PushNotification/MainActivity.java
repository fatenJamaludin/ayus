package com.app.tbd.ui.Activity.PushNotification;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.tbd.R;
import com.google.android.gcm.GCMRegistrar;

import static com.app.tbd.ui.Activity.PushNotification.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static com.app.tbd.ui.Activity.PushNotification.CommonUtilities.EXTRA_MESSAGE;
import static com.app.tbd.ui.Activity.PushNotification.CommonUtilities.SENDER_ID;

public class MainActivity extends Activity {
	// label to display gcm messages
	TextView lblMessage;
	EditText textView;
	// Asyntask
	AsyncTask<Void, Void, Void> mRegisterTask;

	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();

	// Connection detector
	ConnectionDetector cd;

	public static String name;
	public static String email;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainmain);

		cd = new ConnectionDetector(getApplicationContext());

		// Check if Internet present
		if (!cd.isConnectingToInternet()) {
			// Internet Connection is not present
			alert.showAlertDialog(MainActivity.this,
					getString(R.string.main_error),
					getString(R.string.main_connection_body), false);
			// stop executing code by return
			return;
		}

		// Getting name, email from intent
		Intent i = getIntent();

		name = i.getStringExtra("name");
		email = i.getStringExtra("email");

		// Make sure the device has the proper dependencies.
		GCMRegistrar.checkDevice(this);

		// Make sure the manifest was properly set - comment out this line
		// while developing the app, then uncomment it when it's ready.
		//GCMRegistrar.checkManifest(this);
		GCMRegistrar.checkManifest(this);

		lblMessage = (TextView) findViewById(R.id.lblMessage);
		textView = (EditText) findViewById(R.id.textView);

		registerReceiver(mHandleMessageReceiver, new IntentFilter(DISPLAY_MESSAGE_ACTION));

		 String regId = GCMRegistrar.getRegistrationId(this);
		Log.e("regId",regId);

		// Get GCM registration id
		// Check if regid already presents
		//textView.setText(regId);
		//Log.e("regId", regId);
		if (regId.equals("")) {
			// Registration is not present, register now with GCM			
			GCMRegistrar.register(this, SENDER_ID);
			Log.e("regId", "1");

		} else {
			Log.e("regId", "2");
			// Device is already registered on GCM

			//	if (GCMRegistrar.isRegisteredOnServer(this)) {
			//		// Skips registration.
			//		Toast.makeText(getApplicationContext(), "Already registered with GCM", Toast.LENGTH_LONG).show();
			//		Log.e("dah", "New1");
			//
			//	} else {
			Log.e("Register","New2");

			// Try to register again, but not in the UI thread.
			// It's also necessary to cancel the thread onDestroy(),
			// hence the use of AsyncTask instead of a raw thread.
			final Context context = this;
			mRegisterTask = new AsyncTask<Void, Void, Void>() {

				@Override
				protected Void doInBackground(Void... params) {
					// Register on our server
					// On server creates a new user
					//ServerUtilities.register(context, name, email, regId);
					return null;
				}

				@Override
				protected void onPostExecute(Void result) {
					mRegisterTask = null;
				}

			};
			mRegisterTask.execute(null, null, null);
		}
		//}
	}

	/**
	 * Receiving push messages
	 * */
	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
			String mainToast = getString(R.string.main_toast);
			// Waking up mobile if it is sleeping
			WakeLocker.acquire(getApplicationContext());
			Log.e("Receive", "True");
			Log.e("Receive", newMessage);


			/**
			 * Take appropriate action on this message
			 * depending upon your app requirement
			 * For now i am just displaying it on the screen
			 * */

			// Showing received message
			lblMessage.append(newMessage + "\n");
			Toast.makeText(getApplicationContext(), mainToast + " " + newMessage, Toast.LENGTH_LONG).show();

			// Releasing wake lock
			WakeLocker.release();
		}
	};


	@Override
	protected void onDestroy() {
		if (mRegisterTask != null) {
			mRegisterTask.cancel(true);
		}
		try {
			unregisterReceiver(mHandleMessageReceiver);
			GCMRegistrar.onDestroy(this);
		} catch (Exception e) {
			//Log.e("UnRegister Receiver Error", "> " + e.getMessage());
		}
		super.onDestroy();
	}

}
