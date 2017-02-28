package com.app.tbd.ui.Presenter;

import com.app.tbd.ui.Model.Receive.AddBaggageReceive;
import com.app.tbd.ui.Model.Receive.AddInsuranceReceive;
import com.app.tbd.ui.Model.Receive.AddMealReceive;
import com.app.tbd.ui.Model.Receive.AddOnReceiveV2;
import com.app.tbd.ui.Model.Receive.AddPaymentReceive;
import com.app.tbd.ui.Model.Receive.BaggageMealReceive;
import com.app.tbd.ui.Model.Receive.BookingFromStateReceive;
import com.app.tbd.ui.Model.Receive.LoadInsuranceReceive;
import com.app.tbd.ui.Model.Receive.PaymentStatusReceive;
import com.app.tbd.ui.Model.Receive.SearchFlightReceive;
import com.app.tbd.ui.Model.Receive.SeatAssignReceive;
import com.app.tbd.ui.Model.Receive.SeatInfoReceive;
import com.app.tbd.ui.Model.Receive.SelectFlightReceive;
import com.app.tbd.ui.Model.Receive.SignatureReceive;
import com.app.tbd.ui.Model.Receive.StateReceive;
import com.app.tbd.ui.Model.Receive.UpdateTravellerReceive;
import com.app.tbd.ui.Model.Receive.ViewUserReceive;
import com.app.tbd.ui.Model.Request.AddInsuranceRequest;
import com.app.tbd.ui.Model.Request.AddOnRequest;
import com.app.tbd.ui.Model.Request.BookingFromStateRequest;
import com.app.tbd.ui.Model.Request.LoadBaggageRequest;
import com.app.tbd.ui.Model.Request.LoadInsuranceRequest;
import com.app.tbd.ui.Model.Request.PaymentRequest;
import com.app.tbd.ui.Model.Request.PaymentStatusRequest;
import com.app.tbd.ui.Model.Request.SearchFlightRequest;
import com.app.tbd.ui.Model.Request.SeatInfoRequest;
import com.app.tbd.ui.Model.Request.SelectFlightRequest;
import com.app.tbd.ui.Model.Request.SignatureRequest;
import com.app.tbd.ui.Model.Request.StateRequest;
import com.app.tbd.ui.Model.Request.ViewUserRequest;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import java.util.HashMap;

public class BookingPresenter {


    public interface PaymentStatusView {
        void onPaymentStatusReceive(PaymentStatusReceive obj);
    }

    public interface PaymentView {
        void onAddPaymentReceive(AddPaymentReceive obj);

        void onSuccessRequestState(StateReceive obj);
    }

    public interface BaggageView {
        void onBaggageAddReceive(AddBaggageReceive obj);
    }

    public interface MealView {
        void onMealAddReceive(AddMealReceive obj);
    }

    public interface InsuranceView {
        void onAddInsuranceReceive(AddInsuranceReceive obj);
    }

    public interface SeatView {
        void onSeatAssignReceive(SeatAssignReceive obj);

        void onSeatInfoReceive(SeatInfoReceive obj);
    }

    public interface AddOnView {
        void onSeatInfoReceive(SeatInfoReceive obj);

        void onBookingFromState(BookingFromStateReceive obj);

        void onLoadInsuranceReceive(LoadInsuranceReceive obj);

        void onBaggageMealReceive(BaggageMealReceive obj);

        void onAddOnReceive(AddOnReceiveV2 obj);
    }

    public interface TravellerView {
        void onSubmitTravellerReceive(UpdateTravellerReceive obj);
    }

    public interface SearchFlightView {
        void onSearchFlightReceive(SearchFlightReceive obj);

        void onSignatureReceive(SignatureReceive obj);
    }

    public interface ListFlightView {
        void onSearchFlightReceive(SearchFlightReceive obj);

        void onSelectFlightReceive(SelectFlightReceive obj);
    }

    public interface ProfileView {
        void onViewUserSuccess(ViewUserReceive obj);
    }

    private SearchFlightView searchFlightView;
    private TravellerView travellerView;
    private ListFlightView listFlightView;
    private ProfileView profileView;
    private AddOnView addOnView;
    private SeatView seatView;
    private InsuranceView insuranceView;
    private MealView mealView;
    private BaggageView baggageView;
    private PaymentView paymentView;
    private PaymentStatusView paymentStatusView;


    private final Bus bus;

    public BookingPresenter(PaymentStatusView view, Bus bus) {
        this.paymentStatusView = view;
        this.bus = bus;
    }

    public BookingPresenter(BaggageView view, Bus bus) {
        this.baggageView = view;
        this.bus = bus;
    }

    public BookingPresenter(PaymentView view, Bus bus) {
        this.paymentView = view;
        this.bus = bus;
    }

    public BookingPresenter(MealView view, Bus bus) {
        this.mealView = view;
        this.bus = bus;
    }

    public BookingPresenter(InsuranceView view, Bus bus) {
        this.insuranceView = view;
        this.bus = bus;
    }

    public BookingPresenter(SearchFlightView view, Bus bus) {
        this.searchFlightView = view;
        this.bus = bus;
    }

    public BookingPresenter(ListFlightView view, Bus bus) {
        this.listFlightView = view;
        this.bus = bus;
    }

    public BookingPresenter(TravellerView view, Bus bus) {
        this.travellerView = view;
        this.bus = bus;
    }

    public BookingPresenter(ProfileView view, Bus bus) {
        this.profileView = view;
        this.bus = bus;
    }

    public BookingPresenter(AddOnView view, Bus bus) {
        this.addOnView = view;
        this.bus = bus;
    }

    public BookingPresenter(SeatView view, Bus bus) {
        this.seatView = view;
        this.bus = bus;
    }

    public void onSeatAssignRequest(HashMap<String, String> dicMap) {
        bus.post(new HashMap<String, String>(dicMap));
    }

    public void onStateRequest(StateRequest obj) {
        bus.post(new StateRequest(obj));
    }

    public void onAddPayment(PaymentRequest paymentRequest) {
        bus.post(new PaymentRequest(paymentRequest));
    }

    public void onAddInsuranceRequest(AddInsuranceRequest addInsuranceRequest) {
        bus.post(new AddInsuranceRequest(addInsuranceRequest));
    }

    public void onBookingFromStateRequest(BookingFromStateRequest bookingFromStateRequest) {
        bus.post(new BookingFromStateRequest(bookingFromStateRequest));
    }

    public void onSeatInfoRequest(SeatInfoRequest seatInfoRequest) {
        bus.post(new SeatInfoRequest(seatInfoRequest));
    }

    public void onLoadBaggageRequest(LoadBaggageRequest loadBaggageRequest) {
        bus.post(new LoadBaggageRequest(loadBaggageRequest));
    }

    public void onLoadInsuranceRequest(LoadInsuranceRequest loadInsuranceRequest) {
        bus.post(new LoadInsuranceRequest(loadInsuranceRequest));
    }

    //profile request
    public void onProfileRequest(ViewUserRequest viewUserRequest) {
        bus.post(new ViewUserRequest(viewUserRequest));
    }

    //profile request
    public void onRequestPaymentStatus(PaymentStatusRequest paymentStatusRequest) {
        bus.post(new PaymentStatusRequest(paymentStatusRequest));
    }

    /*AAB - Search Flight*/
    public void onTravellerUpdateRequest(HashMap<String, String> dicMap) {
        bus.post(new HashMap<String, String>(dicMap));
    }

    /*AAB - Search Flight*/
    public void onSearchFlight(SearchFlightRequest flightObj) {
        bus.post(new SearchFlightRequest(flightObj));
    }

    /*AAB - Search Flight*/
    public void onRequestSignature(SignatureRequest event) {
        bus.post(new SignatureRequest(event));
    }

    /*AAB - Select Flight*/
    public void onSelectFlight(SelectFlightRequest event) {
        bus.post(new SelectFlightRequest(event));
    }

    /*AAB - Select Flight*/
    public void onAddMealRequest(HashMap<String, String> dicMap) {
        bus.post(new HashMap<String, String>(dicMap));
    }

    public void onAddBaggageRequest(HashMap<String, String> dicMap) {
        bus.post(new HashMap<String, String>(dicMap));
    }

    public void onAddOnRequest(AddOnRequest obj) {
        bus.post(new AddOnRequest(obj));
    }

    // @Subscribe
    // public void onAddPaymentReceive(AddOnReceive event) {
    //     addOnView.onAddOnReceive(event);
    // }

    @Subscribe
    public void onAddPaymentReceive(AddOnReceiveV2 event) {
        if (addOnView != null) {
            addOnView.onAddOnReceive(event);
        }
    }


    @Subscribe
    public void onSuccessRequestState(StateReceive event) {
        if (paymentView != null) {
            paymentView.onSuccessRequestState(event);
        }
    }

    @Subscribe
    public void onAddPaymentReceive(AddPaymentReceive event) {
        if (paymentView != null) {
            paymentView.onAddPaymentReceive(event);
        }
    }

    @Subscribe
    public void onPaymentStatusReceive(PaymentStatusReceive event) {
        if (paymentStatusView != null) {
            paymentStatusView.onPaymentStatusReceive(event);
        }
    }


    @Subscribe
    public void onAddBaggageReceive(AddBaggageReceive event) {
        if (baggageView != null) {
            baggageView.onBaggageAddReceive(event);
        }
    }

    @Subscribe
    public void onAddMealReceive(AddMealReceive event) {
        if (mealView != null) {
            mealView.onMealAddReceive(event);
        }
    }

    @Subscribe
    public void onAddInsuranceReceive(AddInsuranceReceive event) {
        if (insuranceView != null) {
            insuranceView.onAddInsuranceReceive(event);
        }
    }


    @Subscribe
    public void onBaggageMealReceive(BaggageMealReceive event) {
        if (addOnView != null) {
            addOnView.onBaggageMealReceive(event);
        }
    }


    @Subscribe
    public void onSearchFlightSuccess(SearchFlightReceive event) {
        if (searchFlightView != null) {
            searchFlightView.onSearchFlightReceive(event);
        }
        if (listFlightView != null) {
            listFlightView.onSearchFlightReceive(event);
        }
    }

    @Subscribe
    public void onUpdateTravellerReceive(ViewUserReceive event) {
        if (profileView != null) {
            profileView.onViewUserSuccess(event);
        }
    }


    @Subscribe
    public void onLoadInsuranceReceive(LoadInsuranceReceive event) {
        if (addOnView != null) {
            addOnView.onLoadInsuranceReceive(event);
        }
    }

    @Subscribe
    public void onSeatAssignReceive(SeatAssignReceive event) {
        if (seatView != null) {
            seatView.onSeatAssignReceive(event);
        }
    }

    @Subscribe
    public void onBookingFromStateReceive(BookingFromStateReceive event) {
        if (addOnView != null) {
            addOnView.onBookingFromState(event);
        }
    }

    @Subscribe
    public void onSeatInfoReceive(SeatInfoReceive event) {
        if (addOnView != null) {
            addOnView.onSeatInfoReceive(event);
        }
        if (seatView != null) {
            seatView.onSeatInfoReceive(event);
        }
    }

    @Subscribe
    public void onUpdateTravellerReceive(UpdateTravellerReceive event) {
        if (travellerView != null) {
            travellerView.onSubmitTravellerReceive(event);
        }
    }

    @Subscribe
    public void onSignatureReceive(SignatureReceive event) {
        if (searchFlightView != null) {
            searchFlightView.onSignatureReceive(event);
        }
    }

    @Subscribe
    public void onSelectFlightReceive(SelectFlightReceive event) {
        if (listFlightView != null) {
            listFlightView.onSelectFlightReceive(event);
        }
    }


    public void onResume() {
        bus.register(this);
    }

    public void onPause() {
        bus.unregister(this);
    }
}
