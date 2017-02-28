package com.app.tbd.api;

import com.app.tbd.ui.Model.Receive.AddOnReceive;
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
import com.app.tbd.ui.Model.Receive.InitialLoadReceive;
import com.app.tbd.ui.Model.Receive.ForgotPasswordReceive;
import com.app.tbd.ui.Model.Receive.LanguageCountryReceive;
import com.app.tbd.ui.Model.Receive.LanguageReceive;
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
import com.app.tbd.ui.Model.Receive.TBD.LoginReceive;
import com.app.tbd.ui.Model.Receive.TBD.LogoutReceive;
import com.app.tbd.ui.Model.Receive.NewsletterLanguageReceive;
import com.app.tbd.ui.Model.Receive.RegisterReceive;
import com.app.tbd.ui.Model.Receive.StateReceive;
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
import com.app.tbd.ui.Model.Request.InitialLoadRequest;
import com.app.tbd.ui.Model.Request.LanguageRequest;
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
import com.app.tbd.ui.Model.Request.TBD.LoginRequest;
import com.app.tbd.ui.Model.Request.TBD.LogoutRequest;
import com.app.tbd.ui.Model.Request.NewsletterLanguageRequest;
import com.app.tbd.ui.Model.Request.RegisterRequest;
import com.app.tbd.ui.Model.Request.StateRequest;
import com.app.tbd.ui.Model.Request.TransactionHistoryRequest;
import com.app.tbd.ui.Model.Request.UploadPhotoRequest;
import com.app.tbd.ui.Model.Request.UserPhotoRequest;
import com.app.tbd.ui.Model.Request.ViewUserRequest;

import java.util.HashMap;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

public interface ApiService {

    //@POST("/api.php")
    //void onRegisterNotification(@Body PushNotificationObj task, Callback<PushNotificationReceive> callback);

    //GET ALL DATA
    @POST("/GetAllData")
    void onSendDeviceInfo(@Body InitialLoadRequest task, Callback<InitialLoadReceive> callback);

    //LOGIN
    @POST("/AuthenticateUser")
    void onRequestToLogin(@Body LoginRequest task, Callback<LoginReceive> callback);

    //@POST("/AuthenticateUser")
    //void onRequestToLogin(@Body HashMap<String, String> task, Callback<LoginReceive> callback);

    //GET STATE
    @POST("/GetProvinceState")
    void onStateRequest(@Body StateRequest task, Callback<StateReceive> callback);

    @POST("/RegisterUser")
    void onRegisterRequest(@Body RegisterRequest obj, Callback<RegisterReceive> callback);

    @POST("/GetPreferredLanguage")
    void onRequestNewsletterLanguage(@Body NewsletterLanguageRequest task, Callback<NewsletterLanguageReceive> callback);

    @POST("/Logout")
    void onRequestLogout(@Body LogoutRequest task, Callback<LogoutReceive> callback);

    @POST("/ForgottenPassword")
    void onRequestForgotPassword(@Body ForgotPasswordRequest task, Callback<ForgotPasswordReceive> callback);

    @POST("/UpdatePassword")
    void onRequestResetPassword(@Body ResetPasswordRequest obj, Callback<ResetPasswordReceive> callback);

    @POST("/Language/GetLanguage")
    void onLanguageRequest(@Body LanguageRequest obj, Callback<LanguageReceive> callback);

    @GET("/Language/GetCountryAndLanguage?PlatformID=Android")
    void onCountryRequestV2(Callback<LanguageCountryReceive> callback);

    @GET("/Language/GetCountry")
    void onCountryRequest(Callback<LanguageCountryReceive> callback);

    @POST("/GetUser")
    void onViewUserRequest(@Body ViewUserRequest obj, Callback<ViewUserReceive> callback);

    @POST("/GetBalance")
    void onBigPointRequest(@Body BigPointRequest obj, Callback<BigPointReceive> callback);

    //@POST("/GetPointsExpiry")
    //void onTransactionHistoryRequest(@Body TransactionHistoryRequest obj, Callback<TransactionHistoryReceive> callback);

    @POST("/GetTransaction")
    void onTransactionHistoryRequest(@Body TransactionHistoryRequest obj, Callback<TransactionHistoryReceive> callback);

    @POST("/UpdateUserPhoto")
    void onUploadProfilePhoto(@Body UploadPhotoRequest obj, Callback<UploadPhotoReceive> callback);

    @POST("/GetUserPhoto")
    void onRequestUserPhoto(@Body UserPhotoRequest obj, Callback<UserPhotoReceive> callback);

    /*@POST("/Language/GetLanguage")
    void onLanguageRequest(@Body LanguageRequest obj, Callback<LanguageReceive> callback);*/

    @POST("/UpdateUser")
    void onUpdateProfileRequest(@Body EditProfileRequest obj, Callback<EditProfileReceive> callback);

    @POST("/Booking/GetAvailability")
    void onRequestSearchFlight(@Body SearchFlightRequest obj, Callback<SearchFlightReceive> callback);

    @POST("/Booking/LogOn")
    void onSignatureRequest(@Body SignatureRequest obj, Callback<SignatureReceive> callback);

    @POST("/Booking/SellJourney")
    void onSelectFlightRequest(@Body SelectFlightRequest obj, Callback<SelectFlightReceive> callback);

    @POST("/Booking/UpdateTraveler")
    void onUpdateTravellerRequest(@Body HashMap<String, String> task, Callback<UpdateTravellerReceive> callback);

    @POST("/Booking/GetSeatAvailability")
    void onSeatRequest(@Body SeatInfoRequest obj, Callback<SeatInfoReceive> callback);

    @POST("/Booking/GetBookingFromState")
    void onBookingFromStateRequest(@Body BookingFromStateRequest obj, Callback<BookingFromStateReceive> callback);

    @POST("/Booking/AddSeat")
    void onSeatAssignRequest(@Body HashMap<String, String> task, Callback<SeatAssignReceive> callback);

    @POST("/Booking/GetInsurance")
    void onLoadInsurance(@Body LoadInsuranceRequest obj, Callback<LoadInsuranceReceive> callback);

    @POST("/Booking/AddInsurance")
    void onAddInsuranceRequest(@Body AddInsuranceRequest obj, Callback<AddInsuranceReceive> callback);

    @POST("/Booking/GetSSRAvailabilityForBooking")
    void onLoadBaggageMeal(@Body LoadBaggageRequest obj, Callback<BaggageMealReceive> callback);

    @POST("/Booking/AddMeal")
    void onAddMealRequest(@Body HashMap<String, String> task, Callback<AddMealReceive> callback);

    @POST("/Booking/AddBaggage")
    void onAddBaggageRequest(@Body HashMap<String, String> task, Callback<AddBaggageReceive> callback);

    @POST("/Booking/AddPayment")
    void onAddPayment(@Body PaymentRequest obj, Callback<AddPaymentReceive> callback);

    @POST("/Booking/GetBooking")
    void onPaymentStatusRequest(@Body PaymentStatusRequest obj, Callback<PaymentStatusReceive> callback);

    @POST("/Onboarding")
    void onBoardingReceive(@Body OnBoardingRequest obj, Callback<OnBoardingReceive> callback);

    @POST("/GetContent")
    void onAboutUsRequest(@Body ContentRequest obj, Callback<ContentReceive> callback);

    @POST("/GetPromoAll")
    void onPromotionRequest(@Body PromotionRequest obj, Callback<PromotionReceive> callback);

    @POST("/Booking/AddOns")
    void onAddOnRequest(@Body AddOnRequest obj, Callback<AddOnReceive> callback);

    @POST("/Booking/AddOns2")
    void onAddOnRequestV2(@Body AddOnRequest obj, Callback<AddOnReceiveV2> callback);

    /*@POST("/LoginInfo")
    void onLoginInfoRequest(@Body LoginInfoRequest obj);*/

    @POST("/LoginInfo")
    void onLoginInfoRequest(@Body LoginInfoRequest obj, Callback<LoginInfoReceive> callback);

    @POST("/CheckVersion")
    void onAppVersion(@Body AppVersionRequest obj, Callback<AppVersionReceive> callback);

    @POST("/overlay")
    void onOverlayRequest(@Body OverlayRequest obj, Callback<OverlayReceive> callback);

    @POST("/getMessage")
    void onMessageRequest(@Body MessageRequest obj, Callback<MessageReceive> callback);

    @POST("/updateMessageStatus")
    void onMessageStatusRequest(@Body MessageStatusRequest obj, Callback<MessageStatusReceive> callback);

    /*@FormUrlEncoded
    @POST("/api.php")
    void onRegisterNotification(@Field("cmd") String cmd, @Field("user_id") String user_id, @Field("token") String token, @Field("name") String name, @Field("code") String code, Callback<PushNotificationReceive> callback);

    @GET("/users/{user}")
    void getFeed2(@Path("user") String user, Callback<LoginRequest> callback); */
}


