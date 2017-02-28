package com.app.tbd.ui.Activity.BookingFlight.Checkout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.tbd.R;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Activity.FragmentContainerActivity;
import com.app.tbd.ui.Model.Receive.BookingFromStateReceive;
import com.app.tbd.ui.Realm.RealmObjectController;
import com.google.gson.Gson;
import com.mobsandgeeks.saripaar.Validator;

import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.realm.Realm;

public class CheckoutFragment extends BaseFragment {

    // Validator Attributes
    private Validator mValidator;

   /* @Inject
    LoginPresenter presenter;

    @InjectView(R.id.btnPay)
    TextView btnPay;

    @InjectView(R.id.txtBigPoint)
    TextView txtBigPoint;

    @InjectView(R.id.txtDiscount)
    TextView txtDiscount;

    @InjectView(R.id.customSeekBar)
    SeekBar customSeekBar;*/

    @InjectView(R.id.airFareListLayout)
    LinearLayout airFareListLayout;

    @InjectView(R.id.addOnLayout)
    LinearLayout addOnLayout;

    @InjectView(R.id.returnAddon)
    LinearLayout returnAddon;

    @InjectView(R.id.txtSubtotalAirFare)
    TextView txtSubtotalAirFare;

    @InjectView(R.id.txtSubTotalPrice)
    TextView txtSubTotalPrice;

    @InjectView(R.id.txtTotalPoint)
    TextView txtTotalPoint;

    @InjectView(R.id.txtTotalPrice)
    TextView txtTotalPrice;

    @InjectView(R.id.departList)
    LinearLayout departList;

    @InjectView(R.id.txtAddonSubPoints)
    TextView txtAddonSubPoints;

    @InjectView(R.id.returnList)
    LinearLayout returnList;

    @InjectView(R.id.returnBlock)
    LinearLayout returnBlock;

    @InjectView(R.id.returnAirFareListLayout)
    LinearLayout returnAirFareListLayout;

    @InjectView(R.id.txtReturnSubTotalPrice)
    TextView txtReturnSubTotalPrice;

    @InjectView(R.id.txtReturnSubtotalAirFare)
    TextView txtReturnSubtotalAirFare;

    @InjectView(R.id.txtReturnAddonSubPoints)
    TextView txtReturnAddonSubPoints;

    private int fragmentContainerId;
    static BookingFromStateReceive bookingFromStateReceive2;
    BookingFromStateReceive bookingFromStateReceive;

    public static CheckoutFragment newInstance(Bundle bundle) {

        CheckoutFragment fragment = new CheckoutFragment();
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //MainApplication.get(getActivity()).createScopedGraph(new LoginModule(this)).inject(this);
        RealmObjectController.clearCachedResult(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.check_out, container, false);
        ButterKnife.inject(this, view);

        dataSetup();

        return view;
    }

    public static void continueToContinue(Activity act) {

        Intent sliderActivity = new Intent(act, BigPointSliderActivity.class);
        sliderActivity.putExtra("TAX_FEE", new Gson().toJson(bookingFromStateReceive2));
        act.startActivity(sliderActivity);

    }

    private void dataSetup() {

        Realm realm = RealmObjectController.getRealmInstance(getActivity());
       /*final RealmResults<SeatCached> addon = realm.where(SeatCached.class).findAll();
        BookingFromStateReceive bookingFromStateReceive = (new Gson()).fromJson(addon.get(0).getSeatCached(), BookingFromStateReceive.class);
*/
        Bundle bundle = getArguments();
        String obj = bundle.getString("TAX_FEE");
        bookingFromStateReceive = (new Gson()).fromJson(obj, BookingFromStateReceive.class);
        bookingFromStateReceive2 = bookingFromStateReceive;


        appendFee(bookingFromStateReceive);
    }

    public void appendFee(BookingFromStateReceive bookingFromStateReceive) {

        String tbd_pts = getResources().getString(R.string.pts);
        String currency = bookingFromStateReceive.getInitialSliderLogic().getBookingCurrencyCode();

        LinearLayout.LayoutParams half06 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 0.5f);
        LinearLayout.LayoutParams half04 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 0.5f);
        LinearLayout.LayoutParams matchParent2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1f);

        int addonPts = 0;
        int returnAddonPts = 0;

        if (bookingFromStateReceive.getJourney().size() > 1) {
            //return flight available
            returnBlock.setVisibility(View.VISIBLE);
        }

        for (int services = 0; services < bookingFromStateReceive.getJourney().size(); services++) {

            if (bookingFromStateReceive.getJourney().get(0).getAddOns().size() > 0) {
                addOnLayout.setVisibility(View.VISIBLE);

                if (services == 0) {
                    for (int xx = 0; xx < bookingFromStateReceive.getJourney().get(services).getAddOns().size(); xx++) {

                        LinearLayout servicesRow = new LinearLayout(getActivity());
                        servicesRow.setOrientation(LinearLayout.HORIZONTAL);
                        servicesRow.setPadding(2, 2, 2, 2);
                        servicesRow.setWeightSum(1);
                        servicesRow.setLayoutParams(matchParent2);

                        String servicePrice = bookingFromStateReceive.getJourney().get(services).getAddOns().get(xx).getPoints();
                        String servicesName = bookingFromStateReceive.getJourney().get(services).getAddOns().get(xx).getDescription();

                        TextView txtServicesName = new TextView(getActivity());
                        txtServicesName.setText(servicesName);
                        txtServicesName.setLayoutParams(half04);
                        txtServicesName.setGravity(Gravity.LEFT);

                        Log.e("points", servicePrice);

                        TextView txtServicePrice = new TextView(getActivity());

                        String s1 = changeThousand(servicePrice);

                        txtServicePrice.setText(s1 + " " + tbd_pts);

                        txtServicePrice.setLayoutParams(half06);
                        txtServicePrice.setGravity(Gravity.RIGHT);
                        //txtServicesName.setLayoutParams(param);
                        servicesRow.addView(txtServicesName);
                        servicesRow.addView(txtServicePrice);

                        addonPts = addonPts + Integer.parseInt(bookingFromStateReceive.getJourney().get(services).getAddOns().get(xx).getPoints());

                        departList.addView(servicesRow);

                    }
                    String newAddonPts = Integer.toString(addonPts);
                    String s2 = changeThousand(newAddonPts);

                    txtAddonSubPoints.setText(s2 + " " + tbd_pts);
                }

            } else {
                addOnLayout.setVisibility(View.GONE);
            }

            if (services == 1) {

                if (bookingFromStateReceive.getJourney().get(1).getAddOns().size() > 0) {
                    returnAddon.setVisibility(View.VISIBLE);
                    for (int xx = 0; xx < bookingFromStateReceive.getJourney().get(services).getAddOns().size(); xx++) {

                        LinearLayout servicesRow = new LinearLayout(getActivity());
                        servicesRow.setOrientation(LinearLayout.HORIZONTAL);
                        servicesRow.setPadding(2, 2, 2, 2);
                        servicesRow.setWeightSum(1);
                        servicesRow.setLayoutParams(matchParent2);

                        String servicePrice = bookingFromStateReceive.getJourney().get(services).getAddOns().get(xx).getPoints();
                        String servicesName = bookingFromStateReceive.getJourney().get(services).getAddOns().get(xx).getDescription();

                        TextView txtServicesName = new TextView(getActivity());
                        txtServicesName.setText(servicesName);
                        txtServicesName.setLayoutParams(half04);
                        txtServicesName.setGravity(Gravity.LEFT);

                        TextView txtServicePrice = new TextView(getActivity());

                        String s3 = changeThousand(servicePrice);
                        txtServicePrice.setText(s3 + " " + tbd_pts);
                        txtServicePrice.setLayoutParams(half06);
                        txtServicePrice.setGravity(Gravity.RIGHT);
                        //txtServicesName.setLayoutParams(param);
                        servicesRow.addView(txtServicesName);
                        servicesRow.addView(txtServicePrice);

                        returnList.addView(servicesRow);
                        returnAddonPts = returnAddonPts + Integer.parseInt(bookingFromStateReceive.getJourney().get(services).getAddOns().get(xx).getPoints());

                    }
                    String newReturnAddonSubPoints = changeThousand(Integer.toString(returnAddonPts));

                    txtReturnAddonSubPoints.setText( newReturnAddonSubPoints + " " + tbd_pts);
                } else {
                    returnAddon.setVisibility(View.GONE);
                }
            }

            //air fare depart
            if (services == 0) {
                int totalPassenger = bookingFromStateReceive.getPassenger().size();
                /*int totalQuotedPoints = (Integer.parseInt(bookingFromStateReceive.getJourney().get(services).getAdultQuotedPoints()) + Integer.parseInt(bookingFromStateReceive.getJourney().get(services).getChildQuotedPoints()) + Integer.parseInt(bookingFromStateReceive.getJourney().get(services).getInfantQuotedPoints()));*/
                int adultNo = Integer.parseInt(bookingFromStateReceive.getAdult());
                int childNo = Integer.parseInt(bookingFromStateReceive.getChild());
                int infantNo = Integer.parseInt(bookingFromStateReceive.getInfant());

                int totalAdult = Integer.parseInt(bookingFromStateReceive.getJourney().get(services).getAdultQuotedPoints()) * adultNo;
                int totalChild = Integer.parseInt(bookingFromStateReceive.getJourney().get(services).getChildQuotedPoints()) * childNo;
                int totalInfant = Integer.parseInt(bookingFromStateReceive.getJourney().get(services).getInfantQuotedPoints()) * infantNo;
                Log.e("TOTAL", String.valueOf(totalAdult) + String.valueOf(totalChild) + String.valueOf(totalInfant));

                int totalQuotedPoints = totalAdult + totalChild + totalInfant;
                Log.e("TOTAL ALL", String.valueOf(totalQuotedPoints));

                //
                String s4 = changeThousand(Integer.toString(totalQuotedPoints));
                txtSubtotalAirFare.setText(s4 + " " + tbd_pts);
                //txtTotalPoint.setText(bookingFromStateReceive.getTotalQuotedPoints() + " pts");
                //txtTotalPrice.setText(bookingFromStateReceive.getTotalQuotedAmount() + currency);

                double departureTotalAmount = 0;
                for (int g = 0; g < bookingFromStateReceive.getJourney().get(services).getTaxesAndFees().size(); g++) {
                    departureTotalAmount = departureTotalAmount + Double.parseDouble(bookingFromStateReceive.getJourney().get(services).getTaxesAndFees().get(g).getAmount());
                }

                String dp = bookingFromStateReceive.getCurrency().getDisplayDigits();
                double newValue = round(departureTotalAmount, Integer.parseInt(dp));
                int intValue = (int) newValue;

                if (dp.equals("0")){
                    txtSubTotalPrice.setText(intValue + " " + currency);

                }else {
                    String twoPoints = String.format(Locale.US,"%.2f", newValue);
                    txtSubTotalPrice.setText(twoPoints + " " + currency);

                }

                //loop this air fare
                LinearLayout passenger = new LinearLayout(getActivity());
                passenger.setOrientation(LinearLayout.HORIZONTAL);
                passenger.setPadding(2, 2, 2, 2);
                passenger.setWeightSum(1);
                passenger.setLayoutParams(matchParent2);

                TextView passngerTotal = new TextView(getActivity());
                String pass = getString(R.string.passanger_x);
                passngerTotal.setText(Integer.toString(totalPassenger) + pass);
                passngerTotal.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
                passngerTotal.setLayoutParams(half04);
                passngerTotal.setGravity(Gravity.LEFT);

                TextView passengerQuotedAmount = new TextView(getActivity());
                String s5 = changeThousand(Integer.toString(totalQuotedPoints));
                passengerQuotedAmount.setText(s5 + " " + tbd_pts);
                passengerQuotedAmount.setLayoutParams(half06);
                passengerQuotedAmount.setGravity(Gravity.RIGHT);
                //txtServicesName.setLayoutParams(param);
                passenger.addView(passngerTotal);
                passenger.addView(passengerQuotedAmount);

                airFareListLayout.addView(passenger);

                for (int vv = 0; vv < bookingFromStateReceive.getJourney().get(services).getTaxesAndFees().size(); vv++) {
                    LinearLayout airfare = new LinearLayout(getActivity());
                    airfare.setOrientation(LinearLayout.HORIZONTAL);
                    airfare.setPadding(2, 2, 2, 2);
                    airfare.setWeightSum(1);
                    airfare.setLayoutParams(matchParent2);

                    TextView airFareDesc = new TextView(getActivity());
                    airFareDesc.setText(bookingFromStateReceive.getJourney().get(services).getTaxesAndFees().get(vv).getDescription());
                    airFareDesc.setLayoutParams(half04);
                    airFareDesc.setGravity(Gravity.LEFT);

                    TextView airFarePrice = new TextView(getActivity());
                    airFarePrice.setText(bookingFromStateReceive.getJourney().get(services).getTaxesAndFees().get(vv).getAmount() + " " + currency);
                    airFarePrice.setLayoutParams(half06);
                    airFarePrice.setGravity(Gravity.RIGHT);
                    airfare.addView(airFareDesc);
                    airfare.addView(airFarePrice);

                    airFareListLayout.addView(airfare);
                }
            }

            if (services == 1) {
                //air fare depart
                int returnTotalPassenger = bookingFromStateReceive.getPassenger().size();
                int returnTotalQuotedPoints = Integer.parseInt(bookingFromStateReceive.getJourney().get(services).getAdultQuotedPoints()) + Integer.parseInt(bookingFromStateReceive.getJourney().get(services).getChildQuotedPoints()) + Integer.parseInt(bookingFromStateReceive.getJourney().get(services).getInfantQuotedPoints());

                String s6 = changeThousand(Integer.toString(returnTotalQuotedPoints));

                txtReturnSubtotalAirFare.setText(s6 + " " + tbd_pts);

                double returnTotalAmount = 0;
                for (int g = 0; g < bookingFromStateReceive.getJourney().get(services).getTaxesAndFees().size(); g++) {
                    returnTotalAmount = returnTotalAmount + Double.parseDouble(bookingFromStateReceive.getJourney().get(services).getTaxesAndFees().get(g).getAmount());
                }

                String dp = bookingFromStateReceive.getCurrency().getDisplayDigits();
                double newValue = round(returnTotalAmount, Integer.parseInt(dp));
                int intValue = (int) newValue;

                if (dp.equals("0")){
                    String p2 = Integer.toString(intValue);
                    txtReturnSubTotalPrice.setText(p2 + " " + currency);

                }else {

                    String twoPoints = String.format(Locale.US,"%.2f", newValue);
//                    String p2 = Double.toString(newValue);
                    txtReturnSubTotalPrice.setText(twoPoints + " " + currency);

                }

                //add Passenger separetely
                LinearLayout returnPassenger = new LinearLayout(getActivity());
                returnPassenger.setOrientation(LinearLayout.HORIZONTAL);
                returnPassenger.setPadding(2, 2, 2, 2);
                returnPassenger.setWeightSum(1);
                returnPassenger.setLayoutParams(matchParent2);

                //String servicePrice = bookingFromStateReceive.getJourney().get(services).getAddOns().get(xx).getPoints();
                //String servicesName = bookingFromStateReceive.getJourney().get(services).getAddOns().get(xx).getDescription();

                TextView passngerTotal = new TextView(getActivity());
                String pass = getString(R.string.passanger_x);
                passngerTotal.setText(Integer.toString(returnTotalPassenger) + pass);
                passngerTotal.setLayoutParams(half04);
                passngerTotal.setGravity(Gravity.LEFT);

                TextView passengerQuotedAmount = new TextView(getActivity());
                String s7 = changeThousand(Integer.toString(returnTotalQuotedPoints));
                passengerQuotedAmount.setText(s7 + " " + tbd_pts);
                passengerQuotedAmount.setLayoutParams(half06);
                passengerQuotedAmount.setGravity(Gravity.RIGHT);

                returnPassenger.addView(passngerTotal);
                returnPassenger.addView(passengerQuotedAmount);

                returnAirFareListLayout.addView(returnPassenger);

                for (int vv = 0; vv < bookingFromStateReceive.getJourney().get(services).getTaxesAndFees().size(); vv++) {
                    LinearLayout airfare = new LinearLayout(getActivity());
                    airfare.setOrientation(LinearLayout.HORIZONTAL);
                    airfare.setPadding(2, 2, 2, 2);
                    airfare.setWeightSum(1);
                    airfare.setLayoutParams(matchParent2);

                    TextView airFareDesc = new TextView(getActivity());
                    airFareDesc.setText(bookingFromStateReceive.getJourney().get(services).getTaxesAndFees().get(vv).getDescription());
                    airFareDesc.setLayoutParams(half04);
                    airFareDesc.setGravity(Gravity.LEFT);

                    TextView airFarePrice = new TextView(getActivity());
                    airFarePrice.setText(bookingFromStateReceive.getJourney().get(services).getTaxesAndFees().get(vv).getAmount() + " " + currency);
                    airFarePrice.setLayoutParams(half06);
                    airFarePrice.setGravity(Gravity.RIGHT);
                    airfare.addView(airFareDesc);
                    airfare.addView(airFarePrice);

                    returnAirFareListLayout.addView(airfare);
                }

            }

        }


        String p3 = bookingFromStateReceive.getTotalQuotedAmount();
        String s8 = changeThousand(bookingFromStateReceive.getTotalQuotedPoints());

        txtTotalPoint.setText(s8 + " " + tbd_pts);
        txtTotalPrice.setText(p3 + " " + currency);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentContainerId = ((FragmentContainerActivity) getActivity()).getFragmentContainerId();
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
