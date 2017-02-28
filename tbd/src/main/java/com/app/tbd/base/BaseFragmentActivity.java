package com.app.tbd.base;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.app.tbd.MainFragmentActivity;
import com.app.tbd.R;
import com.app.tbd.application.AnalyticsApplication;
import com.app.tbd.ui.Activity.BookingFlight.Add.AddOnFragment;
import com.app.tbd.ui.Activity.BookingFlight.Add.BaggageFragmentV2;
import com.app.tbd.ui.Activity.BookingFlight.Add.InsuranceFragment;
import com.app.tbd.ui.Activity.BookingFlight.Add.MealFragment;
import com.app.tbd.ui.Activity.BookingFlight.Add.SeatTabFragment;
import com.app.tbd.ui.Activity.BookingFlight.Checkout.BigPointSliderFragment;
import com.app.tbd.ui.Activity.BookingFlight.Checkout.CheckoutFragment;
import com.app.tbd.ui.Activity.BookingFlight.Checkout.FlightSummaryFragment;
import com.app.tbd.ui.Activity.BookingFlight.Checkout.PaymentFragment;
import com.app.tbd.ui.Activity.BookingFlight.FlightItinenaryFragment;
import com.app.tbd.ui.Activity.BookingFlight.FlightListFragment;
import com.app.tbd.ui.Activity.BookingFlight.FlightListFragmentV2;
import com.app.tbd.ui.Activity.BookingFlight.PassengerInfoFragment;
import com.app.tbd.ui.Activity.ForgotPassword.ForgotPasswordFragment;
import com.app.tbd.ui.Activity.Login.LoginFragment;
import com.app.tbd.ui.Activity.Profile.Option.ChangeLanguageFragment;
import com.app.tbd.ui.Activity.Profile.Option.OptionsFragment;
import com.app.tbd.ui.Activity.Profile.Option.ResetPasswordFragment;
import com.app.tbd.ui.Activity.Profile.UserProfile.MyProfileFragment;
import com.app.tbd.ui.Model.Request.NotificationMessage;
import com.app.tbd.utils.App;
import com.app.tbd.ui.Realm.RealmObjectController;

import io.realm.RealmResults;

//import com.actionbarsherlock.app.ActionBar;
//import com.actionbarsherlock.app.SherlockFragmentActivity;
//import com.actionbarsherlock.internal.widget.ScrollingTabContainerView.TabView;

public class BaseFragmentActivity extends FragmentActivity {

    public static String appStatus;
    public com.app.tbd.base.AQuery aq;
    private static Activity instance;

    //TabView tabsView;

    public static String getAppStatus() {
        return appStatus;
    }

   /* public void tabSearch(View v)
    {


    }

    public void tabWish(View v)
    {
        Intent intent = new Intent(BaseFragmentActivity.this, MyWishListList.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        BaseFragmentActivity.this.startActivity(intent);
    }

    public void tabCart(View v)
    {
        Intent intent = new Intent(BaseFragmentActivity.this, CartsView.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        BaseFragmentActivity.this.startActivity(intent);
    }*/

    public static void setAppStatus(String status) {
        appStatus = status;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aq = new com.app.tbd.base.AQuery(this);
        Log.e("test2", "test2");

        //SET TO POTRAIT ONLY
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //View actionBarView = getActionBar().getCustomView();
        //actionBarView.setElevation(0);


      /* if ((getApplicationContext().getResources().getConfiguration().screenLayout &
                android.content.res.Configuration.SCREENLAYOUT_SIZE_MASK) >= android.content.res.Configuration.SCREENLAYOUT_SIZE_LARGE){
            //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
           setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
           getApplicationContext().getResources().getConfiguration().orientation = 2;

        }else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            getApplicationContext().getResources().getConfiguration().orientation = 1;
        }*/

        try {
            ActionBar actionBar = getActionBar();
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            //actionBar.setElevation(0);
            //actionBar.setBackgroundDrawable(null);
            // tabsView = new ScrollingTabContainerView(actionBar.getThemedContext());
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            actionBar.setCustomView(R.layout.actionbar);
            View actionBarView = actionBar.getCustomView();
            aq.recycle(actionBarView);
            aq.id(R.id.title).typeface(Typeface.createFromAsset(getAssets(), App.FONT_HELVETICA_NEUE)).textSize(22).textColor(Color.WHITE);
            // if(Utils.getDeviceType(this) == "1")
            //{
            //   aq.id(R.id.tabContainerTablet).visible();
            //display tab here
            // }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        View actionBarView = getActionBar().getCustomView();
        aq.recycle(actionBarView);
        aq.id(R.id.title).text(title);
    }


    public void hideTitle() {
        View actionBarView = getActionBar().getCustomView();
        aq.recycle(actionBarView);
        aq.id(R.id.title).visibility(View.GONE);
    }

    public void setCancelButton() {
        View actionBarView = getActionBar().getCustomView();
        aq.recycle(actionBarView);
        aq.id(R.id.txtCancel).visible();
        aq.id(R.id.txtCancel).clicked(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MyProfileFragment.myProfileNotEditable();
                hideBackButton();
                hideDoneButton();
                hideCancelButton();
                setEditButton();
                setBackButton();
            }
        });
    }

    public void hideCancelButton() {
        View actionBarView = getActionBar().getCustomView();
        aq.recycle(actionBarView);
        aq.id(R.id.txtCancel).gone();
    }

    public void hideDoneButton() {
        View actionBarView = getActionBar().getCustomView();
        aq.recycle(actionBarView);
        aq.id(R.id.txtDone).gone();
    }

    public void hideBackButton() {
        View actionBarView = getActionBar().getCustomView();
        aq.recycle(actionBarView);
        aq.id(R.id.backbutton).gone();
    }


    public void hideEditButton() {
        View actionBarView = getActionBar().getCustomView();
        aq.recycle(actionBarView);
        aq.id(R.id.txtEdit).gone();
    }

    public void setDoneButton() {
        View actionBarView = getActionBar().getCustomView();
        aq.recycle(actionBarView);
        aq.id(R.id.txtDone).visible();
        aq.id(R.id.txtDone).clicked(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MyProfileFragment.editProfileRequest();
                BaseFragment.initiateLoading(BaseFragmentActivity.this);
            }
        });
    }

    public void setBackButtonToCancelBooking() {
        View actionBarView = getActionBar().getCustomView();
        aq.recycle(actionBarView);
        aq.id(R.id.backbutton).visible();
        aq.id(R.id.backbutton).clicked(new OnClickListener() {
            @Override
            public void onClick(View v) {
                PassengerInfoFragment.backToFirst(BaseFragmentActivity.this);
            }
        });

        aq.id(R.id.backbutton2).clicked(new OnClickListener() {
            @Override
            public void onClick(View v) {
                PassengerInfoFragment.backToFirst(BaseFragmentActivity.this);
            }
        });
    }

    public void setEditButton() {
        View actionBarView = getActionBar().getCustomView();
        aq.recycle(actionBarView);
        aq.id(R.id.txtEdit).visible();
        aq.id(R.id.txtEdit).clicked(new OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(BaseFragmentActivity.this, EditProfileActivity.class);
                String userInfo = MyProfileFragment.returnUserInfo();
                intent.putExtra("USER_INFORMATION", userInfo);
                BaseFragmentActivity.this.startActivity(intent);*/
                setCancelButton();
                setDoneButton();
                hideEditButton();
                hideBackButton();

                BaseFragment.initiateLoading(BaseFragmentActivity.this);
                MyProfileFragment.myProfileEditable();
            }
        });
    }

    public void setLogOutButton() {
        View actionBarView = getActionBar().getCustomView();
        aq.recycle(actionBarView);
        aq.id(R.id.btnLogout).visible();
        aq.id(R.id.btnLogout).clicked(new OnClickListener() {
            @Override
            public void onClick(View v) {
                OptionsFragment.setLogout();
                BaseFragment.initiateLoading(BaseFragmentActivity.this);
            }
        });
    }

    public void hideLeftPart() {
        View actionBarView = getActionBar().getCustomView();
        aq.recycle(actionBarView);
        aq.id(R.id.leftPart).gone();
        aq.id(R.id.hiddenLayout).visible();
    }

    public void setARButton() {
        View actionBarView = getActionBar().getCustomView();
        aq.recycle(actionBarView);
        aq.id(R.id.btnAR).visible();
    }

    /*public void setEditButton()
    {
        View actionBarView = getActionBar().getCustomView();
        aq.recycle(actionBarView);
        aq.id(R.id.btn).visible();
    }*/

    public void setBackButton2() {
        View actionBarView = getActionBar().getCustomView();
        aq.recycle(actionBarView);
        aq.id(R.id.backbutton).visible();
        aq.id(R.id.backbutton).clicked(new OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginFragment.onBackPressed(BaseFragmentActivity.this);
            }
        });

        aq.id(R.id.backbutton2).clicked(new OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginFragment.onBackPressed(BaseFragmentActivity.this);
            }
        });
    }

    public void setBackButton() {
        View actionBarView = getActionBar().getCustomView();
        aq.recycle(actionBarView);
        aq.id(R.id.backbutton).visible();
        aq.id(R.id.backbutton).clicked(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        aq.id(R.id.backbutton2).clicked(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setHomeButton() {
        View actionBarView = getActionBar().getCustomView();
        aq.recycle(actionBarView);
        aq.id(R.id.homeButton).visible();
        aq.id(R.id.homeButton).clicked(new OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseFragmentActivity.this.finish();
                FlightSummaryFragment.backToHomepage(BaseFragmentActivity.this);
            }
        });
    }


    public void setLoginButton() {
        View actionBarView = getActionBar().getCustomView();
        aq.recycle(actionBarView);
        aq.id(R.id.loginBtn).visible();
        aq.id(R.id.loginBtn).clicked(new OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginFragment.onLogin();
            }
        });
    }


    public void setPayButton() {
        View actionBarView = getActionBar().getCustomView();
        aq.recycle(actionBarView);
        aq.id(R.id.pay).visible();
        aq.id(R.id.pay).clicked(new OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentFragment.onCCPayment(BaseFragmentActivity.this);
            }
        });
    }

    public void setNewFlight() {
        View actionBarView = getActionBar().getCustomView();
        aq.recycle(actionBarView);
        aq.id(R.id.newFlight).visible();
        aq.id(R.id.newFlight).clicked(new OnClickListener() {
            @Override
            public void onClick(View v) {
                FlightSummaryFragment.newFlight(BaseFragmentActivity.this);
            }
        });
    }


    public void setPayment() {
        View actionBarView = getActionBar().getCustomView();
        aq.recycle(actionBarView);
        aq.id(R.id.paymentContinue).visible();
        aq.id(R.id.paymentContinue).clicked(new OnClickListener() {
            @Override
            public void onClick(View v) {
                BigPointSliderFragment.onProceedPayment(BaseFragmentActivity.this);
            }
        });
    }


    public void setContinueButton() {
        View actionBarView = getActionBar().getCustomView();
        aq.recycle(actionBarView);
        aq.id(R.id.continueSelectFlight).visible();
        aq.id(R.id.continueSelectFlight).clicked(new OnClickListener() {
            @Override
            public void onClick(View v) {
                FlightListFragment.onSelectFlight(BaseFragmentActivity.this);
            }
        });
    }

    public void setContinueButtonV2() {
        View actionBarView = getActionBar().getCustomView();
        aq.recycle(actionBarView);
        aq.id(R.id.continueSelectFlightv2).visible();
        aq.id(R.id.continueSelectFlightv2).clicked(new OnClickListener() {
            @Override
            public void onClick(View v) {
                FlightListFragmentV2.onSelectFlight();
            }
        });
    }


    public void setReturnButton() {
        View actionBarView = getActionBar().getCustomView();
        aq.recycle(actionBarView);
        aq.id(R.id.continueReturn).visible();
        aq.id(R.id.continueReturn).clicked(new OnClickListener() {
            @Override
            public void onClick(View v) {
                FlightListFragment.onSelectFlight(BaseFragmentActivity.this);
            }
        });
    }

    public void setTravellerInfoBtn() {
        View actionBarView = getActionBar().getCustomView();
        aq.recycle(actionBarView);
        aq.id(R.id.travellerInfoBtn).visible();
        aq.id(R.id.travellerInfoBtn).clicked(new OnClickListener() {
            @Override
            public void onClick(View v) {
                FlightItinenaryFragment.loadProfileInfo(BaseFragmentActivity.this);
            }
        });
    }


    public void setSubmitButton() {
        View actionBarView = getActionBar().getCustomView();
        aq.recycle(actionBarView);
        aq.id(R.id.submitButton).visible();
        aq.id(R.id.submitButton).clicked(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetPasswordFragment.submitResetPassword();
            }
        });
    }

    public void setSaveButton() {
        View actionBarView = getActionBar().getCustomView();
        aq.recycle(actionBarView);
        aq.id(R.id.saveButton).visible();
        aq.id(R.id.saveButton).clicked(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeLanguageFragment.onSavePassword(BaseFragmentActivity.this);
            }
        });
    }

    public void setSubmitButton2() {
        View actionBarView = getActionBar().getCustomView();
        aq.recycle(actionBarView);
        aq.id(R.id.submitButton2).visible();
        aq.id(R.id.submitButton2).clicked(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgotPasswordFragment.submitForgotPassword();
            }
        });
    }

    public void setSelectPayment() {
        View actionBarView = getActionBar().getCustomView();
        aq.recycle(actionBarView);
        aq.id(R.id.selectPayment).visible();
        aq.id(R.id.selectPayment).clicked(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckoutFragment.continueToContinue(BaseFragmentActivity.this);
            }
        });
    }

    public void setAddOnButton() {
        View actionBarView = getActionBar().getCustomView();
        aq.recycle(actionBarView);
        aq.id(R.id.addOn).visible();
        aq.id(R.id.addOn).clicked(new OnClickListener() {
            @Override
            public void onClick(View v) {

                PassengerInfoFragment.onSubmitPassenger(BaseFragmentActivity.this);
                //PassengerInfoFragment.onSubmitPassenger(BaseFragmentActivity.this);
                //Intent intent = new Intent(BaseFragmentActivity.this, CheckoutActivity.class);
                //BaseFragmentActivity.this.startActivity(intent);
            }
        });
    }

    //need to put in one place later
    public void setSeatDone() {
        View actionBarView = getActionBar().getCustomView();
        aq.recycle(actionBarView);
        aq.id(R.id.seatDone).visible();
        aq.id(R.id.seatDone).clicked(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SeatTabFragment.onSeatDone(BaseFragmentActivity.this);
            }
        });
    }

    //need to put in one place later
    public void setAddonDone(String from) {

        View actionBarView = getActionBar().getCustomView();
        aq.recycle(actionBarView);
        aq.id(R.id.addonDone).visible();

        if (from.equals("MEAL")) {
            aq.id(R.id.addonDone).clicked(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    MealFragment.onRequestMealSSR(BaseFragmentActivity.this);
                }
            });
        } else if (from.equals("BAGGAGE")) {
            aq.id(R.id.addonDone).clicked(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    BaggageFragmentV2.onRequestBaggage(BaseFragmentActivity.this);
                }
            });
        }

    }

    public void setSaveInsurance() {
        View actionBarView = getActionBar().getCustomView();
        aq.recycle(actionBarView);
        aq.id(R.id.insuranceCheck).visible();


        aq.id(R.id.insuranceCheck).clicked(new OnClickListener() {
            @Override
            public void onClick(View v) {

                InsuranceFragment.sendRequest(BaseFragmentActivity.this);

                //PassengerInfoFragment.onSubmitPassenger(BaseFragmentActivity.this);
                /*Intent intent = new Intent(BaseFragmentActivity.this, AddOnActivity.class);
                BaseFragmentActivity.this.startActivity(intent);*/
            }
        });
    }


    public void setCheckOutButton() {
        View actionBarView = getActionBar().getCustomView();
        aq.recycle(actionBarView);
        aq.id(R.id.checkOut).visible();
        aq.id(R.id.checkOut).clicked(new OnClickListener() {
            @Override
            public void onClick(View v) {

                AddOnFragment.checkOut(BaseFragmentActivity.this);
                //PassengerInfoFragment.onSubmitPassenger(BaseFragmentActivity.this);
                //Intent intent = new Intent(BaseFragmentActivity.this, CheckoutActivity.class);
                //BaseFragmentActivity.this.startActivity(intent);
            }
        });
    }

    public void setGlobalSearchButton() {
        View actionBarView = getActionBar().getCustomView();
        aq.recycle(actionBarView);
        aq.id(R.id.tabMySearch1).gone();
        aq.id(R.id.centerPart).gone();

        /*RelativeLayout leftPart = (RelativeLayout) actionBarView.findViewById(R.id.leftPart);
        RelativeLayout.LayoutParams lp = new RelativeLayout().LayoutParams(0,1);
        lp.weight = 0.05f;
        leftPart.setLayoutParams(lp);*/

        /*aq.id(R.id.tabBackButton).clicked(new OnClickListener() {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });*/
    }

    public void setTabBackButton() {
        View actionBarView = getActionBar().getCustomView();
        aq.recycle(actionBarView);
        aq.id(R.id.tabBackButton).visible();
        aq.id(R.id.tabBackButton).clicked(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void setTitle(int titleId) {
        super.setTitle(titleId);
    }

    public void setTitleImage(String imageUrl) {
        View actionBarView = getActionBar().getCustomView();

        //aq.recycle(actionBarView);
        //aq.id(R.id.icon).image(imageUrl);
    }

    public void setTitleImage(int imageId) {
        View actionBarView = getActionBar().getCustomView();
        aq.recycle(actionBarView);
        aq.id(R.id.icon).image(imageId);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        //RealmObjectController.clearCachedResult(this);

    }

    @Override
    public void finish() {
        super.finish();
        RealmObjectController.clearCachedResult(this);
        System.gc();

        //overridePendingTransition(R.anim.fadeout,R.anim.fadein);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.gc();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.fade_out);
        // RealmObjectController.clearCachedResult(this);
        //setResult(RESULT_CANCELED);
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onResume() {

        super.onResume();
        MainFragmentActivity.setContext(this);
        Log.e("getSimpleName()",this.getClass().getSimpleName());
        //push notification alert
        try {
            if (this.getClass().getSimpleName().equals("TabActivity")) {
                if (appStatus != null && appStatus.equals("ready_for_notification")) {
                    if (appStatus.equals("ready_for_notification")) {
                        RealmResults<NotificationMessage> result = RealmObjectController.getNotificationMessage(this);
                        if (result.size() > 0) {
                            /*BaseFragment.setPushNotificationAlert(this, result.get(0).getMessage(), result.get(0).getTitle());*/
                            BaseFragment.deepLink(this);
                            MainFragmentActivity.setAppStatus("not_ready_for_notification");
                            RealmObjectController.clearNotificationMessage(this);
                        }
                    } else {
                    }
                } else {
                    appStatus = "not_for_notification";
                }
            }
        } catch (Exception e) {

        }

        sendPageAnalytics();
    }

    public void sendPageAnalytics() {
        if (this.getClass().getSimpleName().equals("SplashScreenActivity")) {
            AnalyticsApplication.sendScreenView("SplashScreen loaded");

        } else if (this.getClass().getSimpleName().equals("SC_Activity")) {
            AnalyticsApplication.sendScreenView("Change country language loaded");

        } else if (this.getClass().getSimpleName().equals("OnBoardingActivity")) {
            AnalyticsApplication.sendScreenView("OnBoarding loaded");

        } else if (this.getClass().getSimpleName().equals("AfterBoardingActivity")) {
            AnalyticsApplication.sendScreenView("After onBoarding loaded");

        } else if (this.getClass().getSimpleName().equals("RegisterActivity")) {
            AnalyticsApplication.sendScreenView("Register loaded");

        } else if (this.getClass().getSimpleName().equals("LoginActivity")) {
            AnalyticsApplication.sendScreenView("Login loaded");

        } else if (this.getClass().getSimpleName().equals("ForgotPasswordActivity")) {
            AnalyticsApplication.sendScreenView("Forgot password loaded");

        } else if (this.getClass().getSimpleName().equals("MyProfileActivity")) {
            AnalyticsApplication.sendScreenView("My profile loaded");

        } else if (this.getClass().getSimpleName().equals("BigPointBaseActivity")) {
            AnalyticsApplication.sendScreenView("Big point loaded");

        } else if (this.getClass().getSimpleName().equals("ExpiryDateActivity")) {
            AnalyticsApplication.sendScreenView("Expiry date loaded");

        } else if (this.getClass().getSimpleName().equals("OptionsActivity")) {
            AnalyticsApplication.sendScreenView("Options loaded");

        } else if (this.getClass().getSimpleName().equals("ResetPasswordActivity")) {
            AnalyticsApplication.sendScreenView("Reset password loaded");

        } else if (this.getClass().getSimpleName().equals("ChangeLanguageActivity")) {
            AnalyticsApplication.sendScreenView("Change language loaded");

        } else if (this.getClass().getSimpleName().equals("AboutActivity")) {
            AnalyticsApplication.sendScreenView("About loaded");

        } else if (this.getClass().getSimpleName().equals("PrivacyPolicyActivity")) {
            AnalyticsApplication.sendScreenView("Privacy policy loaded");

        } else if (this.getClass().getSimpleName().equals("TermsActivity")) {
            AnalyticsApplication.sendScreenView("Terms loaded");

        } else if (this.getClass().getSimpleName().equals("TermOfUseActivity")) {
            AnalyticsApplication.sendScreenView("Term of use loaded");

        } else if (this.getClass().getSimpleName().equals("FlightListDepartActivity")) {
            AnalyticsApplication.sendScreenView("Flight list departure loaded");

        } else if (this.getClass().getSimpleName().equals("FlightListReturnActivity")) {
            AnalyticsApplication.sendScreenView("Flight list return loaded");

        } else if (this.getClass().getSimpleName().equals("FlightItinenaryActivity")) {
            AnalyticsApplication.sendScreenView("Flight itinerary loaded");

        } else if (this.getClass().getSimpleName().equals("PassengerInfoActivity")) {
            AnalyticsApplication.sendScreenView("Passenger info loaded");

        } else if (this.getClass().getSimpleName().equals("AddOnActivity")) {
            AnalyticsApplication.sendScreenView("Add on loaded");

        } else if (this.getClass().getSimpleName().equals("InsuranceActivity")) {
            AnalyticsApplication.sendScreenView("Insurance loaded");

        } else if (this.getClass().getSimpleName().equals("CheckoutActivity")) {
            AnalyticsApplication.sendScreenView("Checkout loaded");

        } else if (this.getClass().getSimpleName().equals("BigPointSliderActivity")) {
            AnalyticsApplication.sendScreenView("Big point slider loaded");

        } else if (this.getClass().getSimpleName().equals("PaymentActivity")) {
            AnalyticsApplication.sendScreenView("Payment loaded");

        } else if (this.getClass().getSimpleName().equals("PaymentWebviewActivity")) {
            AnalyticsApplication.sendScreenView("Payment web-view loaded");

        } else if (this.getClass().getSimpleName().equals("PaymentFailedActivity")) {
            AnalyticsApplication.sendScreenView("Payment failed loaded");

        } else if (this.getClass().getSimpleName().equals("PaymentPendingActivity")) {
            AnalyticsApplication.sendScreenView("Payment pending loaded");

        } else if (this.getClass().getSimpleName().equals("FlightSummaryActivity")) {
            AnalyticsApplication.sendScreenView("Flight summary loaded");

        } else if (this.getClass().getSimpleName().equals("MealActivity")) {
            AnalyticsApplication.sendScreenView("Meal loaded");

        } else if (this.getClass().getSimpleName().equals("BaggageActivity")) {
            AnalyticsApplication.sendScreenView("Baggage loaded");

        } else if (this.getClass().getSimpleName().equals("SeatTabActivity")) {
            AnalyticsApplication.sendScreenView("Seat loaded");

        }
    }
}
