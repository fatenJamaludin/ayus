package com.app.tbd.api;

import com.app.tbd.ui.Model.Receive.AddOnReceiveV2;
import com.app.tbd.ui.Model.Receive.AppVersionReceive;
import com.app.tbd.ui.Model.Receive.ContentReceive;
import com.app.tbd.ui.Model.Receive.AddBaggageReceive;
import com.app.tbd.ui.Model.Receive.AddInsuranceReceive;
import com.app.tbd.ui.Model.Receive.AddMealReceive;
import com.app.tbd.ui.Model.Receive.AddPaymentReceive;
import com.app.tbd.ui.Model.Receive.BaggageMealReceive;
import com.app.tbd.ui.Model.Receive.BookingFromStateReceive;
import com.app.tbd.ui.Model.Receive.EditProfileReceive;
import com.app.tbd.ui.Model.Receive.LanguageCountryReceive;
import com.app.tbd.ui.Model.Receive.LoadInsuranceReceive;
import com.app.tbd.ui.Model.Receive.LoginInfoReceive;
import com.app.tbd.ui.Model.Receive.MessageReceive;
import com.app.tbd.ui.Model.Receive.MessageStatusReceive;
import com.app.tbd.ui.Model.Receive.OnBoardingReceive;
import com.app.tbd.ui.Model.Receive.OverlayReceive;
import com.app.tbd.ui.Model.Receive.PaymentStatusReceive;
import com.app.tbd.ui.Model.Receive.PromotionReceive;
import com.app.tbd.ui.Model.Receive.ResetPasswordReceive;
import com.app.tbd.ui.Model.Receive.SearchFlightReceive;
import com.app.tbd.ui.Model.Receive.SeatAssignReceive;
import com.app.tbd.ui.Model.Receive.SeatInfoReceive;
import com.app.tbd.ui.Model.Receive.SelectFlightReceive;
import com.app.tbd.ui.Model.Receive.SignatureReceive;
import com.app.tbd.ui.Model.Receive.TBD.BigPointReceive;
import com.app.tbd.ui.Model.Receive.TBD.BigPointReceiveFailed;
import com.app.tbd.ui.Model.Receive.TBD.LoginReceive;
import com.app.tbd.ui.Model.Receive.TBD.LogoutReceive;
import com.app.tbd.ui.Model.Receive.NewsletterLanguageReceive;
import com.app.tbd.ui.Model.Receive.TransactionHistoryReceive;
import com.app.tbd.ui.Model.Receive.UpdateTravellerReceive;
import com.app.tbd.ui.Model.Receive.UploadPhotoReceive;
import com.app.tbd.ui.Model.Receive.UserPhotoReceive;
import com.app.tbd.ui.Model.Receive.ViewUserReceive;
import com.app.tbd.ui.Model.Request.AddOnRequest;
import com.app.tbd.ui.Model.Request.AppVersionRequest;
import com.app.tbd.ui.Model.Request.ContentRequest;
import com.app.tbd.ui.Model.Request.AddInsuranceRequest;
import com.app.tbd.ui.Model.Request.BookingFromStateRequest;
import com.app.tbd.ui.Model.Request.EditProfileRequest;
import com.app.tbd.ui.Model.Request.ForgotPasswordRequest;
import com.app.tbd.ui.Model.Request.LanguageCountryRequest;
import com.app.tbd.ui.Model.Request.LoadBaggageRequest;
import com.app.tbd.ui.Model.Request.LoadInsuranceRequest;
import com.app.tbd.ui.Model.Request.LoginInfoRequest;
import com.app.tbd.ui.Model.Request.MessageRequest;
import com.app.tbd.ui.Model.Request.MessageStatusRequest;
import com.app.tbd.ui.Model.Request.OnBoardingRequest;
import com.app.tbd.ui.Model.Request.OverlayRequest;
import com.app.tbd.ui.Model.Request.PaymentRequest;
import com.app.tbd.ui.Model.Request.PaymentStatusRequest;
import com.app.tbd.ui.Model.Request.PromotionRequest;
import com.app.tbd.ui.Model.Request.ResetPasswordRequest;
import com.app.tbd.ui.Model.Request.SearchFlightRequest;
import com.app.tbd.ui.Model.Request.SeatInfoRequest;
import com.app.tbd.ui.Model.Request.SelectFlightRequest;
import com.app.tbd.ui.Model.Request.SignatureRequest;
import com.app.tbd.ui.Model.Request.TBD.BigPointRequest;
import com.app.tbd.ui.Model.Request.TBD.LogoutRequest;
import com.app.tbd.ui.Model.Receive.LanguageReceive;
import com.app.tbd.ui.Model.Request.LanguageRequest;
import com.app.tbd.ui.Model.Request.NewsletterLanguageRequest;
import com.app.tbd.ui.Model.Request.TransactionHistoryRequest;
import com.app.tbd.ui.Model.Request.UploadPhotoRequest;
import com.app.tbd.ui.Model.Request.UserPhotoRequest;
import com.app.tbd.ui.Model.Request.ViewUserRequest;
import com.app.tbd.utils.SharedPrefManager;
import com.app.tbd.utils.Utils;
import com.app.tbd.MainFragmentActivity;
import com.app.tbd.ui.Model.Receive.InitialLoadReceive;
import com.app.tbd.ui.Model.Receive.ForgotPasswordReceive;
import com.app.tbd.ui.Model.Receive.RegisterReceive;
import com.app.tbd.ui.Model.Receive.StateReceive;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Model.Request.InitialLoadRequest;
import com.app.tbd.ui.Model.Request.TBD.LoginRequest;
import com.app.tbd.ui.Model.Request.RegisterRequest;
import com.app.tbd.ui.Model.Request.StateRequest;
import com.app.tbd.ui.Realm.RealmObjectController;
import com.google.gson.Gson;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.HashMap;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ApiRequestHandler {

    private final Bus bus;
    private final ApiService apiService;
    private int inc;
    private boolean retry;

    public ApiRequestHandler(Bus bus, ApiService apiService) {
        this.bus = bus;
        this.apiService = apiService;
        retry = false;
    }


    @Subscribe
    public void onAppVersion(final AppVersionRequest event) {

        apiService.onAppVersion(event, new Callback<AppVersionReceive>() {

            @Override
            public void success(AppVersionReceive rhymesResponse, Response response) {

                if (rhymesResponse != null) {
                    bus.post(new AppVersionReceive(rhymesResponse));
                    RealmObjectController.cachedResult(MainFragmentActivity.getContext(), (new Gson()).toJson(rhymesResponse));
                } else {
                    BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
            }

        });
    }

    @Subscribe
    public void onAddOnRequest(final AddOnRequest event) {

        apiService.onAddOnRequestV2(event, new Callback<AddOnReceiveV2>() {

            @Override
            public void success(AddOnReceiveV2 rhymesResponse, Response response) {

                if (rhymesResponse != null) {
                    bus.post(new AddOnReceiveV2(rhymesResponse));
                    RealmObjectController.cachedResult(MainFragmentActivity.getContext(), (new Gson()).toJson(rhymesResponse));
                } else {
                    BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
            }

        });
    }



    @Subscribe
    public void onOverlayRequest(final OverlayRequest event) {

        apiService.onOverlayRequest(event, new Callback<OverlayReceive>() {

            @Override
            public void success(OverlayReceive rhymesResponse, Response response) {

                if (rhymesResponse != null) {
                    bus.post(new OverlayReceive(rhymesResponse));
                    RealmObjectController.cachedResult(MainFragmentActivity.getContext(), (new Gson()).toJson(rhymesResponse));

                    //save here
                    //savePromotionInfo
                    Gson gsonUserInfo = new Gson();
                    String promotionObj = gsonUserInfo.toJson(rhymesResponse);
                    RealmObjectController.savePromotion(MainFragmentActivity.getContext(), promotionObj);

                    SharedPrefManager pref = new SharedPrefManager(MainFragmentActivity.getContext());
                    pref.setReloadPromo("Y");

                } else {
                    BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
            }

        });
    }

    @Subscribe
    public void onMessageRequest(final MessageRequest event) {

        apiService.onMessageRequest(event, new Callback<MessageReceive>() {

            @Override
            public void success(MessageReceive rhymesResponse, Response response) {

                if (rhymesResponse != null) {
                    bus.post(new MessageReceive(rhymesResponse));
                    RealmObjectController.cachedResult(MainFragmentActivity.getContext(), (new Gson()).toJson(rhymesResponse));

                    /*//save here
                    //savePromotionInfo
                    Gson gsonUserInfo = new Gson();
                    String promotionObj = gsonUserInfo.toJson(rhymesResponse);
                    RealmObjectController.savePromotion(MainFragmentActivity.getContext(), promotionObj);

                    SharedPrefManager pref = new SharedPrefManager(MainFragmentActivity.getContext());
                    pref.setReloadPromo("Y");*/

                } else {
                    BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
            }

        });
    }

    @Subscribe
    public void onMessageStatusRequest(final MessageStatusRequest event) {

        apiService.onMessageStatusRequest(event, new Callback<MessageStatusReceive>() {

            @Override
            public void success(MessageStatusReceive rhymesResponse, Response response) {

                if (rhymesResponse != null) {
                    bus.post(new MessageStatusReceive(rhymesResponse));
                    RealmObjectController.cachedResult(MainFragmentActivity.getContext(), (new Gson()).toJson(rhymesResponse));

                } else {
                    BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
            }

        });
    }

    @Subscribe
    public void onPromotionRequest(final PromotionRequest event) {

        apiService.onPromotionRequest(event, new Callback<PromotionReceive>() {

            @Override
            public void success(PromotionReceive rhymesResponse, Response response) {

                if (rhymesResponse != null) {
                    bus.post(new PromotionReceive(rhymesResponse));
                    RealmObjectController.cachedResult(MainFragmentActivity.getContext(), (new Gson()).toJson(rhymesResponse));

                    Gson gsonUserInfo = new Gson();
                    String promotionObj = gsonUserInfo.toJson(rhymesResponse);
                    RealmObjectController.savePromotion(MainFragmentActivity.getContext(), promotionObj);

                    SharedPrefManager pref = new SharedPrefManager(MainFragmentActivity.getContext());
                    pref.setReloadPromo("Y");

                } else {
                    BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
            }

        });
    }

    @Subscribe
    public void onLoginInfoRequest(final LoginInfoRequest event) {

        apiService.onLoginInfoRequest(event, new Callback<LoginInfoReceive>() {

            @Override
            public void success(LoginInfoReceive retroResponse, Response response) {

                if (retroResponse != null) {
                    bus.post(new LoginInfoReceive(retroResponse));
                } else {
                    BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
            }
        });
    }

    @Subscribe
    public void onAboutUsRequest(final ContentRequest event) {

        apiService.onAboutUsRequest(event, new Callback<ContentReceive>() {

            @Override
            public void success(ContentReceive rhymesResponse, Response response) {

                if (rhymesResponse != null) {
                    if (event.getContentName().equals("About")) {
                        rhymesResponse.setWhichContent("About");
                        bus.post(new ContentReceive(rhymesResponse));
                    } else if (event.getContentName().equals("PrivacyPolicy")) {
                        rhymesResponse.setWhichContent("PrivacyPolicy");
                        bus.post(new ContentReceive(rhymesResponse));
                    } else if (event.getContentName().equals("Terms")) {
                        rhymesResponse.setWhichContent("Terms");
                        bus.post(new ContentReceive(rhymesResponse));
                    } else if (event.getContentName().equals("TermsConditions")) {
                        rhymesResponse.setWhichContent("TermsConditions");
                        bus.post(new ContentReceive(rhymesResponse));
                    }

                    RealmObjectController.cachedResult(MainFragmentActivity.getContext(), (new Gson()).toJson(rhymesResponse));
                } else {
                    BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
            }

        });
    }

    @Subscribe
    public void onBoardingImage(final OnBoardingRequest event) {

        apiService.onBoardingReceive(event, new Callback<OnBoardingReceive>() {

            @Override
            public void success(OnBoardingReceive rhymesResponse, Response response) {

                if (rhymesResponse != null) {
                    bus.post(new OnBoardingReceive(rhymesResponse));
                    RealmObjectController.cachedResult(MainFragmentActivity.getContext(), (new Gson()).toJson(rhymesResponse));
                } else {
                    BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
            }

        });
    }

    @Subscribe
    public void onPaymentStatusRequest(final PaymentStatusRequest event) {

        apiService.onPaymentStatusRequest(event, new Callback<PaymentStatusReceive>() {

            @Override
            public void success(PaymentStatusReceive rhymesResponse, Response response) {

                if (rhymesResponse != null) {
                    bus.post(new PaymentStatusReceive(rhymesResponse));
                    RealmObjectController.cachedResult(MainFragmentActivity.getContext(), (new Gson()).toJson(rhymesResponse));
                } else {
                    BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
            }

        });
    }


    @Subscribe
    public void onAddPayment(final PaymentRequest event) {

        apiService.onAddPayment(event, new Callback<AddPaymentReceive>() {

            @Override
            public void success(AddPaymentReceive rhymesResponse, Response response) {

                if (rhymesResponse != null) {
                    bus.post(new AddPaymentReceive(rhymesResponse));
                    RealmObjectController.cachedResult(MainFragmentActivity.getContext(), (new Gson()).toJson(rhymesResponse));
                } else {
                    BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
            }

        });
    }


    @Subscribe
    public void onLoadBaggageMeal(final LoadBaggageRequest event) {

        apiService.onLoadBaggageMeal(event, new Callback<BaggageMealReceive>() {

            @Override
            public void success(BaggageMealReceive rhymesResponse, Response response) {

                if (rhymesResponse != null) {
                    bus.post(new BaggageMealReceive(rhymesResponse));
                    RealmObjectController.cachedResult(MainFragmentActivity.getContext(), (new Gson()).toJson(rhymesResponse));
                } else {
                    BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
            }

        });
    }

    @Subscribe
    public void onAddInsuranceRequest(final AddInsuranceRequest event) {

        apiService.onAddInsuranceRequest(event, new Callback<AddInsuranceReceive>() {

            @Override
            public void success(AddInsuranceReceive rhymesResponse, Response response) {

                if (rhymesResponse != null) {
                    bus.post(new AddInsuranceReceive(rhymesResponse));
                    RealmObjectController.cachedResult(MainFragmentActivity.getContext(), (new Gson()).toJson(rhymesResponse));
                } else {
                    BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
            }

        });
    }


    @Subscribe
    public void onBookingFromStateRequest(final BookingFromStateRequest event) {

        apiService.onBookingFromStateRequest(event, new Callback<BookingFromStateReceive>() {

            @Override
            public void success(BookingFromStateReceive rhymesResponse, Response response) {

                if (rhymesResponse != null) {
                    bus.post(new BookingFromStateReceive(rhymesResponse));
                    RealmObjectController.cachedResult(MainFragmentActivity.getContext(), (new Gson()).toJson(rhymesResponse));
                } else {
                    BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
            }

        });
    }

    // Login API //

    @Subscribe
    public void onLoginRequest(final LoginRequest event) {

        apiService.onRequestToLogin(event, new Callback<LoginReceive>() {

            @Override
            public void success(LoginReceive rhymesResponse, Response response) {

                if (rhymesResponse != null) {
                    bus.post(new LoginReceive(rhymesResponse));
                    RealmObjectController.cachedResult(MainFragmentActivity.getContext(), (new Gson()).toJson(rhymesResponse));
                } else {
                    BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
            }

        });
    }

     /*@Subscribe
    public void onLoginRequest(final HashMap<String, String> event) {

        apiService.onRequestToLogin(event, new Callback<LoginReceive>() {

            @Override
            public void success(LoginReceive rhymesResponse, Response response) {

                if (rhymesResponse != null) {
                    bus.post(new LoginReceive(rhymesResponse));
                    RealmObjectController.cachedResult(MainFragmentActivity.getContext(), (new Gson()).toJson(rhymesResponse));
                } else {
                    BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
            }

        });
    }*/


    @Subscribe
    public void onStateRequest(final StateRequest event) {

        apiService.onStateRequest(event, new Callback<StateReceive>() {

            @Override
            public void success(StateReceive retroResponse, Response response) {

                if (retroResponse != null) {
                    bus.post(new StateReceive(retroResponse));
                    RealmObjectController.cachedResult(MainFragmentActivity.getContext(), (new Gson()).toJson(retroResponse));
                } else {
                    BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
                }

            }

            @Override
            public void failure(RetrofitError error) {

                BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
            }

        });
    }

    @Subscribe
    public void onRegisterRequest(final RegisterRequest event) {

        apiService.onRegisterRequest(event, new Callback<RegisterReceive>() {

            @Override
            public void success(RegisterReceive rhymesResponse, Response response) {

                if (rhymesResponse != null) {
                    bus.post(new RegisterReceive(rhymesResponse));
                    RealmObjectController.cachedResult(MainFragmentActivity.getContext(), (new Gson()).toJson(rhymesResponse));
                } else {
                    BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
                }
            }

            @Override
            public void failure(RetrofitError error) {

                BaseFragment.setAlertNotification(MainFragmentActivity.getContext());

            }

        });
    }

    @Subscribe
    public void onRegisterRequest(final NewsletterLanguageRequest event) {

        apiService.onRequestNewsletterLanguage(event, new Callback<NewsletterLanguageReceive>() {

            @Override
            public void success(NewsletterLanguageReceive rhymesResponse, Response response) {

                if (rhymesResponse != null) {
                    bus.post(new NewsletterLanguageReceive(rhymesResponse));
                    RealmObjectController.cachedResult(MainFragmentActivity.getContext(), (new Gson()).toJson(rhymesResponse));
                } else {
                    BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
                }
            }

            @Override
            public void failure(RetrofitError error) {

                BaseFragment.setAlertNotification(MainFragmentActivity.getContext());

            }

        });
    }

    @Subscribe
    public void onRequestForgotPassword(final ForgotPasswordRequest event) {

        apiService.onRequestForgotPassword(event, new Callback<ForgotPasswordReceive>() {

            @Override
            public void success(ForgotPasswordReceive rhymesResponse, Response response) {

                if (rhymesResponse != null) {
                    bus.post(new ForgotPasswordReceive(rhymesResponse));
                    // RealmObjectController.cachedSearchFlightResult(MainFragmentActivity.getContext(), (new Gson()).toJson(rhymesResponse), Utils.SEARCH_FLIGHT);
                } else {
                    BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
                }
            }

            @Override
            public void failure(RetrofitError error) {

                BaseFragment.setAlertNotification(MainFragmentActivity.getContext());

            }

        });
    }

    @Subscribe
    public void onRequestLogout(final LogoutRequest event) {

        apiService.onRequestLogout(event, new Callback<LogoutReceive>() {

            @Override
            public void success(LogoutReceive rhymesResponse, Response response) {
                if (rhymesResponse != null) {
                    bus.post(new LogoutReceive(rhymesResponse));
                    //RealmObjectController.cachedSearchFlightResult(MainFragmentActivity.getContext(), (new Gson()).toJson(rhymesResponse), Utils.SEARCH_FLIGHT);
                } else {
                    BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
            }

        });
    }

    @Subscribe
    public void onRequestResetPassword(final ResetPasswordRequest event) {

        apiService.onRequestResetPassword(event, new Callback<ResetPasswordReceive>() {

            @Override
            public void success(ResetPasswordReceive rhymesResponse, Response response) {

                if (rhymesResponse != null) {
                    bus.post(new ResetPasswordReceive(rhymesResponse));
//                    RealmObjectController.cachedResult(MainFragmentActivity.getContext(), (new Gson()).toJson(rhymesResponse));
                } else {
                    BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
            }
        });
    }

    @Subscribe
    public void onLanguageRequest(final LanguageRequest event) {

        apiService.onLanguageRequest(event, new Callback<LanguageReceive>() {

            @Override
            public void success(LanguageReceive retroResponse, Response response) {

                if (retroResponse != null) {
                    bus.post(new LanguageReceive(retroResponse));
                    RealmObjectController.cachedResult(MainFragmentActivity.getContext(), (new Gson()).toJson(retroResponse));
                } else {
                    BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
            }
        });
    }

    @Subscribe
    public void onCountryRequest(final LanguageCountryRequest event) {

        apiService.onCountryRequestV2(new Callback<LanguageCountryReceive>() {

            @Override
            public void success(LanguageCountryReceive retroResponse, Response response) {

                if (retroResponse != null) {
                    bus.post(new LanguageCountryReceive(retroResponse));
                    RealmObjectController.cachedLanguageCountry(MainFragmentActivity.getContext(), (new Gson()).toJson(retroResponse));
                } else {
                    BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
            }
        });
    }

    @Subscribe
    public void onBigPointRequest(final BigPointRequest event) {

        apiService.onBigPointRequest(event, new Callback<BigPointReceive>() {

            @Override
            public void success(BigPointReceive retroResponse, Response response) {

                //if (retroResponse != null) {
                bus.post(new BigPointReceive(retroResponse));
                RealmObjectController.cachedResult(MainFragmentActivity.getContext(), (new Gson()).toJson(retroResponse));
                RealmObjectController.cachedBigPointRequest(MainFragmentActivity.getContext(), (new Gson()).toJson(retroResponse));
                //} else {
                //    BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
                //}
            }

            @Override
            public void failure(RetrofitError error) {
                bus.post(new BigPointReceiveFailed());
            }
        });
    }

    @Subscribe
    public void onTransactionHistoryRequest(final TransactionHistoryRequest event) {

        apiService.onTransactionHistoryRequest(event, new Callback<TransactionHistoryReceive>() {

            @Override
            public void success(TransactionHistoryReceive retroResponse, Response response) {

                if (retroResponse != null) {
                    bus.post(new TransactionHistoryReceive(retroResponse));
                    RealmObjectController.cachedResult(MainFragmentActivity.getContext(), (new Gson()).toJson(retroResponse));
                } else {
                    BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
            }
        });
    }

    @Subscribe
    public void onUpdateProfile(final EditProfileRequest event) {

        apiService.onUpdateProfileRequest(event, new Callback<EditProfileReceive>() {

            @Override
            public void success(EditProfileReceive retroResponse, Response response) {

                if (retroResponse != null) {
                    bus.post(new EditProfileReceive(retroResponse));
                    RealmObjectController.cachedResult(MainFragmentActivity.getContext(), (new Gson()).toJson(retroResponse));
                } else {
                    BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
            }
        });
    }

    @Subscribe
    public void onUpdateProfile(final UploadPhotoRequest event) {

        apiService.onUploadProfilePhoto(event, new Callback<UploadPhotoReceive>() {

            @Override
            public void success(UploadPhotoReceive retroResponse, Response response) {

                if (retroResponse != null) {
                    bus.post(new UploadPhotoReceive(retroResponse));
                    RealmObjectController.cachedResult(MainFragmentActivity.getContext(), (new Gson()).toJson(retroResponse));
                } else {
                    BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
            }
        });
    }

    @Subscribe
    public void onLoadInsuranceRequest(final LoadInsuranceRequest event) {

        apiService.onLoadInsurance(event, new Callback<LoadInsuranceReceive>() {

            @Override
            public void success(LoadInsuranceReceive rhymesResponse, Response response) {

                if (rhymesResponse != null) {
                    bus.post(new LoadInsuranceReceive(rhymesResponse));
                    RealmObjectController.cachedResult(MainFragmentActivity.getContext(), (new Gson()).toJson(rhymesResponse));
                } else {
                    BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
                }
            }

            @Override
            public void failure(RetrofitError error) {

                BaseFragment.setAlertNotification(MainFragmentActivity.getContext());

            }

        });
    }

    @Subscribe
    public void onRequestUserPhoto(final UserPhotoRequest event) {

        apiService.onRequestUserPhoto(event, new Callback<UserPhotoReceive>() {

            @Override
            public void success(UserPhotoReceive retroResponse, Response response) {

                if (retroResponse != null) {
                    bus.post(new UserPhotoReceive(retroResponse));
                    RealmObjectController.cachedResult(MainFragmentActivity.getContext(), (new Gson()).toJson(retroResponse));
                } else {
                    BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
            }
        });
    }

    @Subscribe
    public void onRequestSearchFlight(final SearchFlightRequest event) {

        apiService.onRequestSearchFlight(event, new Callback<SearchFlightReceive>() {

            @Override
            public void success(SearchFlightReceive retroResponse, Response response) {

                if (retroResponse != null) {
                    bus.post(new SearchFlightReceive(retroResponse));
                    RealmObjectController.cachedResult(MainFragmentActivity.getContext(), (new Gson()).toJson(retroResponse));
                } else {
                    BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
            }
        });
    }

    @Subscribe
    public void onSignatureRequest(final SignatureRequest event) {

        apiService.onSignatureRequest(event, new Callback<SignatureReceive>() {

            @Override
            public void success(SignatureReceive retroResponse, Response response) {

                if (retroResponse != null) {
                    bus.post(new SignatureReceive(retroResponse));
                    RealmObjectController.cachedSearchFlightResult(MainFragmentActivity.getContext(), (new Gson()).toJson(retroResponse), Utils.SEARCH_FLIGHT_SIGNATURE);
                } else {
                    BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
            }
        });
    }

    @Subscribe
    public void onSignatureRonSelectFlightRequestequest(final SelectFlightRequest event) {

        apiService.onSelectFlightRequest(event, new Callback<SelectFlightReceive>() {

            @Override
            public void success(SelectFlightReceive retroResponse, Response response) {

                if (retroResponse != null) {
                    bus.post(new SelectFlightReceive(retroResponse));
                    RealmObjectController.cachedResult(MainFragmentActivity.getContext(), (new Gson()).toJson(retroResponse));
                } else {
                    BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
            }
        });
    }

    @Subscribe
    public void onSeatRequest(final SeatInfoRequest event) {

        apiService.onSeatRequest(event, new Callback<SeatInfoReceive>() {

            @Override
            public void success(SeatInfoReceive retroResponse, Response response) {

                if (retroResponse != null) {
                    bus.post(new SeatInfoReceive(retroResponse));
                    RealmObjectController.cachedResult(MainFragmentActivity.getContext(), (new Gson()).toJson(retroResponse));
                } else {
                    BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
            }
        });
    }


    ////
    ///
    //
    ///
    ///
    ////


   /* @Subscribe
    public void onRegisterNotification(final PushNotificationObj event) {

        //
        // apiService.onRegisterNotification(event, new Callback<PushNotificationReceive>() {
        apiService.onRegisterNotification(event.getCmd(), event.getUser_id(), event.getToken(), event.getName(), event.getCode(), new Callback<PushNotificationReceive>() {

            @Override
            public void success(PushNotificationReceive retroResponse, Response response) {

                if (retroResponse != null) {
                    bus.post(new PushNotificationReceive(retroResponse));
                    RealmObjectController.cachedResult(MainFragmentActivity.getContext(), (new Gson()).toJson(retroResponse));
                } else {
                    BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
                }

            }

            @Override
            public void failure(RetrofitError error) {

                BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
            }

        });
    }*/
    // ------------------------------------------------------------------------------ //

    /* Subscribe From HomePresenter - Send Device Information to server - ImalPasha */
    @Subscribe
    public void onDeviceInfo(final InitialLoadRequest event) {

        apiService.onSendDeviceInfo(event, new Callback<InitialLoadReceive>() {

            @Override
            public void success(InitialLoadReceive retroResponse, Response response) {

                if (retroResponse != null) {
                    bus.post(new InitialLoadReceive(retroResponse));
                    RealmObjectController.cachedResult(MainFragmentActivity.getContext(), (new Gson()).toJson(retroResponse));
                } else {
                    BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
                }

            }

            @Override
            public void failure(RetrofitError error) {
                BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
            }

        });
    }


    /*@Subscribe
    public void onForgotPassword(final ForgotPasswordRequest event) {

        apiService.onForgotPassword(event, new Callback<ForgotPasswordReceive>() {

            @Override
            public void success(ForgotPasswordReceive rhymesResponse, Response response) {

                if (rhymesResponse != null) {
                    bus.post(new ForgotPasswordReceive(rhymesResponse));
                    RealmObjectController.cachedResult(MainFragmentActivity.getContext(), (new Gson()).toJson(rhymesResponse));
                } else {
                    BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
                }

            }

            @Override
            public void failure(RetrofitError error) {

                BaseFragment.setAlertNotification(MainFragmentActivity.getContext());

            }

        });
    }*/

    @Subscribe
    public void onSeatAssignRequest(final HashMap<String, String> dicMap) {

        if (dicMap.get("FROM_WHICH").equals("UPDATE_TRAVELLER")) {

            //onUpdateTravellerRequest
            apiService.onUpdateTravellerRequest(dicMap, new Callback<UpdateTravellerReceive>() {

                @Override
                public void success(UpdateTravellerReceive rhymesResponse, Response response) {

                    if (rhymesResponse != null) {
                        bus.post(new UpdateTravellerReceive(rhymesResponse));
                        RealmObjectController.cachedResult(MainFragmentActivity.getContext(), (new Gson()).toJson(rhymesResponse));
                    } else {
                        BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
                    }

                }

                @Override
                public void failure(RetrofitError error) {

                    BaseFragment.setAlertNotification(MainFragmentActivity.getContext());

                }

            });
        } else if (dicMap.get("FROM_WHICH").equals("SEAT_ASSIGN")) {
            //assign seat
            apiService.onSeatAssignRequest(dicMap, new Callback<SeatAssignReceive>() {

                @Override
                public void success(SeatAssignReceive rhymesResponse, Response response) {

                    if (rhymesResponse != null) {
                        bus.post(new SeatAssignReceive(rhymesResponse));
                        RealmObjectController.cachedResult(MainFragmentActivity.getContext(), (new Gson()).toJson(rhymesResponse));
                    } else {
                        BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
                    }

                }

                @Override
                public void failure(RetrofitError error) {

                    BaseFragment.setAlertNotification(MainFragmentActivity.getContext());

                }

            });
        } else if (dicMap.get("FROM_WHICH").equals("MEAL_ASSIGN")) {
            apiService.onAddMealRequest(dicMap, new Callback<AddMealReceive>() {

                @Override
                public void success(AddMealReceive rhymesResponse, Response response) {

                    if (rhymesResponse != null) {
                        bus.post(new AddMealReceive(rhymesResponse));
                        RealmObjectController.cachedResult(MainFragmentActivity.getContext(), (new Gson()).toJson(rhymesResponse));
                    } else {
                        BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
                }

            });
        } else {
            apiService.onAddBaggageRequest(dicMap, new Callback<AddBaggageReceive>() {

                @Override
                public void success(AddBaggageReceive rhymesResponse, Response response) {

                    if (rhymesResponse != null) {
                        bus.post(new AddBaggageReceive(rhymesResponse));
                        RealmObjectController.cachedResult(MainFragmentActivity.getContext(), (new Gson()).toJson(rhymesResponse));
                    } else {
                        BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
                }

            });
        }

    }






    /*@Subscribe
    public void onLanguageRequest(final LanguageRequest event) {

        apiService.onLanguageRequest(event, new Callback<LanguageReceive>() {

            @Override
            public void success(LanguageReceive retroResponse, Response response) {

                if (retroResponse != null) {
                    bus.post(new LanguageReceive(retroResponse));
                    RealmObjectController.cachedResult(MainFragmentActivity.getContext(), (new Gson()).toJson(retroResponse));
                } else {
                    BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
            }
        });
    }

    @Subscribe
    public void onCountryRequest(final LanguageCountryRequest event) {

        apiService.onCountryRequest(new Callback<LanguageCountryReceive>() {

            @Override
            public void success(LanguageCountryReceive retroResponse, Response response) {

                if (retroResponse != null) {
                    bus.post(new LanguageCountryReceive(retroResponse));
                    RealmObjectController.cachedResult(MainFragmentActivity.getContext(), (new Gson()).toJson(retroResponse));
                } else {
                    BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
            }
        });
    }*/

    @Subscribe
    public void onViewUserRequest(final ViewUserRequest event) {

        apiService.onViewUserRequest(event, new Callback<ViewUserReceive>() {

            @Override
            public void success(ViewUserReceive retroResponse, Response response) {

                if (retroResponse != null) {
                    bus.post(new ViewUserReceive(retroResponse));
                    RealmObjectController.cachedResult(MainFragmentActivity.getContext(), (new Gson()).toJson(retroResponse));
                } else {
                    BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
            }
        });
    }
}
