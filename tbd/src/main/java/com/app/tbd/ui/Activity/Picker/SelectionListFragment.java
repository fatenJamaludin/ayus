package com.app.tbd.ui.Activity.Picker;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.app.tbd.R;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Activity.BookingFlight.Checkout.PaymentFragment;
import com.app.tbd.ui.Activity.BookingFlight.SearchFlightFragment;
import com.app.tbd.ui.Model.JSON.RecentSearch;
import com.app.tbd.ui.Model.JSON.RecentSearchArrival;
import com.app.tbd.ui.Model.JSON.RecentSearchList;
import com.app.tbd.ui.Model.JSON.RecentSearchListArrival;
import com.app.tbd.ui.Model.JSON.countryLanguageJSON;
import com.app.tbd.ui.Model.Receive.LanguageCountryReceive;
import com.app.tbd.ui.Model.Receive.StateReceive;
import com.app.tbd.ui.Realm.RealmObjectController;
import com.app.tbd.utils.DropDownItem;
import com.app.tbd.utils.SharedPrefManager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import dev.dworks.libs.astickyheader.SimpleSectionedListAdapter;
import io.realm.Realm;
import io.realm.RealmResults;

public class SelectionListFragment extends DialogFragment {
    public static final String KEY_COUNTRY_LIST = "countryList";
    public static final String KEY_LANGUAGE_LIST = "languageList";
    public static final String LIST_NAME = "LIST_NAME";
    public static final String PASS_SELECTION_LIST = "PASS_SELECTION_LIST";

    String[] filteredCountry;
    Integer[] headerPosition;
    SharedPrefManager pref;

    String listName;

    ArrayList<DropDownItem> list = new ArrayList<DropDownItem>();
    ArrayList<DropDownItem> originalList = new ArrayList<DropDownItem>();

    ListView lvCountries;
    EditText txtSearchCustom;
    TextView txtClearSearch;
    SelectionListAdapter adapter;
    TextView txtCountry;
    TextView txtSelectionTitle;
    ImageButton backbutton;
    LinearLayout searchViewLayout;
    Boolean searchOn = false;
    SimpleSectionedListAdapter simpleSectionedGridAdapter;
    String adapterFor = "NON-FLIGHT";
    String stationCode;
    Boolean notHide = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.MY_DIALOG);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog d = getDialog();
        if (d != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            d.getWindow().setLayout(width, height);
        }
    }

    private ArrayList<SimpleSectionedListAdapter.Section> sections = new ArrayList<SimpleSectionedListAdapter.Section>();

    public static SelectionListFragment newInstance(String data, String code, Boolean searchOn) {
        SelectionListFragment fragment = new SelectionListFragment();
        Bundle bundle = new Bundle();
        // bundle.putParcelableArrayList(PASS_SELECTION_LIST, countries);
        bundle.putString(LIST_NAME, data);
        /*bundle.putString("STATION_CODE", code);*/
        bundle.putString("STATION_CODE", code);
        bundle.putBoolean("SEARCH_STATUS", searchOn);

        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {


        pref = new SharedPrefManager(getActivity());

        list = getArguments().getParcelableArrayList(PASS_SELECTION_LIST);
        listName = getArguments().getString(LIST_NAME);

        stationCode = getArguments().getString("STATION_CODE");
        searchOn = getArguments().getBoolean("SEARCH_STATUS");

        final String enter_keyword = getString(R.string.picker_enter_keyword);
        View view;
        if (searchOn) {
            view = inflater.inflate(R.layout.list_with_search_box, container, false);
            txtSearchCustom = (EditText) view.findViewById(R.id.txtSearchCustom);
            txtClearSearch = (TextView) view.findViewById(R.id.txtClearSearch);

            txtClearSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (listName.equals("DEPARTURE_FLIGHT")) {
                        txtSearchCustom.setHint(getString(R.string.picker_select_departure));

                        //addRecentSearchView("DEPART");

                    } else {
                        txtSearchCustom.setHint(getString(R.string.picker_select_arrival));
                    }
                    txtSearchCustom.setText("");

                }
            });

        } else {
            view = inflater.inflate(R.layout.fragment_country_list_dialog, container, false);
            txtSelectionTitle = (TextView) view.findViewById(R.id.txtSelectionTitle);

        }

        lvCountries = (ListView) view.findViewById(R.id.lvCountries);
        backbutton = (ImageButton) view.findViewById(R.id.backbutton);

        if (listName.equals("LANGUAGE_COUNTRY")) {
            txtSelectionTitle.setText(getString(R.string.select_country));
        } else if (listName.equals("CHANGE_COUNTRY")) {
            txtSelectionTitle.setText(getString(R.string.select_country));
        } else if (listName.equals("LANGUAGE")) {
            txtSelectionTitle.setText(getString(R.string.select_language));
        } else if (listName.equals("GENDER")) {
            txtSelectionTitle.setText(getString(R.string.picker_select_gender));
        } else if (listName.equals("STATE")) {
            txtSelectionTitle.setText(getString(R.string.select_state));
        } else if (listName.equals("COUNTRY")) {
            txtSearchCustom.setHint(getString(R.string.picker_enter_keyword));
        } else if (listName.equals("PAYMENTCOUNTRY")) {
            txtSelectionTitle.setText(getString(R.string.register_country));
        } else if (listName.equals("PAYMENTSTATE")) {
            txtSelectionTitle.setText(getString(R.string.register_state));
        } else if (listName.equals("NATIONALITY")) {
        } else if (listName.equals("NEWSLETTER_LANGUAGE")) {
            txtSelectionTitle.setText(getResources().getString(R.string.select_language));
        } else if (listName.equals("TITLE")) {
            txtSelectionTitle.setText(getResources().getString(R.string.select_title));
        } else if (listName.equals("PROMO_SEARCH") || listName.equals("FINAL_CALL")) {
            txtSelectionTitle.setText(getString(R.string.picker_select_departure));
        } else if (listName.equals("DEPARTURE_FLIGHT")) {
            txtSearchCustom.setHint(getString(R.string.picker_select_departure));
            adapterFor = "FLIGHT";
            addRecentSearchView("DEPART");
        } else if (listName.equals("ARRIVAL_FLIGHT")) {
            txtSearchCustom.setHint(getString(R.string.picker_select_arrival));
            adapterFor = "FLIGHT";
            //addRecentSearchView("ARRIVAL");
        }

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        initControls();

        txtSearchCustom = (EditText) view.findViewById(R.id.txtSearchCustom);
        txtSearchCustom.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String text = txtSearchCustom.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);

                View headerView = lvCountries.findViewWithTag("header");

                try {
                    headerView.setVisibility(View.GONE);
                } catch (Exception e) {

                }

                //lvCountries.removeHeaderView(headerView);
                if (s.length() == 0 && listName.equals("DEPARTURE_FLIGHT")) {

                    try {
                        headerView.setVisibility(View.VISIBLE);
                    } catch (Exception e) {

                    }
                    //addRecentSearchView("DEPART");
                }
                /*if (notHide) {
                    LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                    View recentView = layoutInflater.inflate(R.layout.recent_search_header, null);
                    lvCountries.removeHeaderView(recentView);
                    notHide = false;
                }*/

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });

        lvCountries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DropDownItem xx = (DropDownItem) parent.getAdapter().getItem(position);
                sendResult((DropDownItem) parent.getAdapter().getItem(position));
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getRootView().getWindowToken(), 0);

            }
        });

        //setRecentSearch

        return view;
    }

    public void addRecentSearchView(String type) {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View recentView = layoutInflater.inflate(R.layout.recent_search_header, null);

        LinearLayout recentSearchAppend = (LinearLayout) recentView.findViewById(R.id.recentSearchAppend);

        Realm realm = RealmObjectController.getRealmInstance(getActivity());

        if (type.equals("DEPART")) {
            final RealmResults<RecentSearch> recentSearchResult = realm.where(RecentSearch.class).findAll();
            if (recentSearchResult.size() > 0) {
                RecentSearchList recentSearchList = (new Gson()).fromJson(recentSearchResult.get(0).getRecentSearch(), RecentSearchList.class);
                lvCountries.addHeaderView(recentView);

                for (int v = 0; v < recentSearchList.getRecentSearchList().size(); v++) {

                    final LinearLayout perRecentSearch = new LinearLayout(getActivity());
                    perRecentSearch.setOrientation(LinearLayout.VERTICAL);
                    perRecentSearch.setPadding(10, 10, 3, 15);
                    perRecentSearch.setWeightSum(1);
                    perRecentSearch.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.recent_search_click));

                    LinearLayout departWithCode = new LinearLayout(getActivity());
                    departWithCode.setOrientation(LinearLayout.HORIZONTAL);
                    departWithCode.setWeightSum(1);
                    departWithCode.setPadding(15, 0, 3, 0);
                    //perRecentSearch.setLayoutParams(matchParent);
                    //perRecentSearch.setGravity(Gravity.LEFT);

                    TextView recentSearchName = new TextView(getActivity());
                    recentSearchName.setPadding(0, 10, 3, 0);
                    recentSearchName.setTextSize(16);
                    recentSearchName.setText(recentSearchList.getRecentSearchList().get(v).getDepartureName());

                    TextView recentSearchCode = new TextView(getActivity());
                    recentSearchCode.setPadding(20, 10, 3, 0);
                    recentSearchCode.setTextSize(16);
                    recentSearchCode.setText("(" + recentSearchList.getRecentSearchList().get(v).getDepartureCode() + ")");

                    departWithCode.addView(recentSearchName);
                    departWithCode.addView(recentSearchCode);
                    perRecentSearch.addView(departWithCode);

                    TextView recentSearchCountry = new TextView(getActivity());
                    recentSearchCountry.setPadding(15, 0, 3, 10);
                    recentSearchCountry.setTextSize(12);
                    recentSearchCountry.setText(recentSearchList.getRecentSearchList().get(v).getDepartureCountry());

                    //perRecentSearch.addView(recentSearchName);
                    perRecentSearch.addView(recentSearchCountry);
                    perRecentSearch.setTag(recentSearchList.getRecentSearchList().get(v).getDepartureCode() + "/" + recentSearchList.getRecentSearchList().get(v).getDepartureCountry() + "/" + recentSearchList.getRecentSearchList().get(v).getDepartureCurrency() + "/" + recentSearchList.getRecentSearchList().get(v).getDepartureName());

                    recentSearchAppend.addView(perRecentSearch);

                    perRecentSearch.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.e("Country Code", perRecentSearch.getTag().toString());
                            //convert to dropdown item
                            String tagSplit = perRecentSearch.getTag().toString();
                            String[] str1 = tagSplit.split("/");

                            String departureStation = str1[0];
                            String departureCountryName = str1[1];
                            String departureStationCurrencyCode = str1[2];
                            String departureStationName = str1[3];

                            DropDownItem item = new DropDownItem();
                            item.setCode(perRecentSearch.getTag().toString());
                            item.setText(departureStationName + " (" + departureStation + ")");

                            sendResult(item);
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getRootView().getWindowToken(), 0);

                        }
                    });

                }

            }
        } else {
            final RealmResults<RecentSearchArrival> recentSearchResult = realm.where(RecentSearchArrival.class).findAll();

            if (recentSearchResult.size() > 0) {
                RecentSearchListArrival recentSearchListArrival = (new Gson()).fromJson(recentSearchResult.get(0).getRecentSearch(), RecentSearchListArrival.class);

                lvCountries.addHeaderView(recentView);

                for (int v = 0; v < recentSearchListArrival.getRecentSearchList().size(); v++) {

                    final LinearLayout perRecentSearch = new LinearLayout(getActivity());
                    perRecentSearch.setOrientation(LinearLayout.VERTICAL);
                    perRecentSearch.setPadding(0, 10, 3, 15);
                    perRecentSearch.setWeightSum(1);
                    perRecentSearch.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.recent_search_click));

                    LinearLayout departWithCode = new LinearLayout(getActivity());
                    departWithCode.setOrientation(LinearLayout.HORIZONTAL);
                    departWithCode.setWeightSum(1);
                    departWithCode.setPadding(0, 0, 3, 0);
                    //perRecentSearch.setLayoutParams(matchParent);
                    //perRecentSearch.setGravity(Gravity.LEFT);

                    TextView recentSearchName = new TextView(getActivity());
                    recentSearchName.setPadding(0, 10, 3, 0);
                    recentSearchName.setTextSize(16);
                    recentSearchName.setText(recentSearchListArrival.getRecentSearchList().get(v).getDepartureName());

                    TextView recentSearchCode = new TextView(getActivity());
                    recentSearchCode.setPadding(0, 10, 3, 0);
                    recentSearchCode.setTextSize(16);
                    recentSearchCode.setText("(" + recentSearchListArrival.getRecentSearchList().get(v).getDepartureCode() + ")");

                    departWithCode.addView(recentSearchName);
                    departWithCode.addView(recentSearchCode);
                    perRecentSearch.addView(departWithCode);

                    TextView recentSearchCountry = new TextView(getActivity());
                    recentSearchCountry.setPadding(0, 0, 3, 10);
                    recentSearchCountry.setTextSize(12);
                    recentSearchCountry.setText(recentSearchListArrival.getRecentSearchList().get(v).getDepartureCountry());

                    //perRecentSearch.addView(recentSearchName);
                    perRecentSearch.addView(recentSearchCountry);
                    perRecentSearch.setTag(recentSearchListArrival.getRecentSearchList().get(v).getDepartureCode() + "/" + recentSearchListArrival.getRecentSearchList().get(v).getDepartureCountry() + "/" + recentSearchListArrival.getRecentSearchList().get(v).getDepartureCurrency() + "/" + recentSearchListArrival.getRecentSearchList().get(v).getDepartureName());

                    recentSearchAppend.addView(perRecentSearch);

                    perRecentSearch.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.e("Country Code", perRecentSearch.getTag().toString());
                            //convert to dropdown item
                            String tagSplit = perRecentSearch.getTag().toString();
                            String[] str1 = tagSplit.split("/");

                            String departureStation = str1[0];
                            String departureCountryName = str1[1];
                            String departureStationCurrencyCode = str1[2];
                            String departureStationName = str1[3];

                            DropDownItem item = new DropDownItem();
                            item.setCode(perRecentSearch.getTag().toString());
                            item.setText(departureStationName + " (" + departureStation + ")");

                            sendResult(item);
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getRootView().getWindowToken(), 0);

                        }
                    });

                }

            }
        }

    }

    public void setSearchMethod(ArrayList<DropDownItem> list) {

        searchOn = true;
        //GET CHAR AT - FILTER
        List<String> countryChar = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            String country = list.get(i).getText();
            countryChar.add(Character.toString(country.charAt(0)));
        }

        //GET HEADER POSITION
        filteredCountry = BaseFragment.getCharAt(countryChar);
        headerPosition = BaseFragment.headerPosition(countryChar);

    }

    public void initControls() {

        originalList = new ArrayList<DropDownItem>();
        lvCountries.setAdapter(null);
        sections = new ArrayList<SimpleSectionedListAdapter.Section>();
        originalList = initiateDataForAdapter(listName);
        Log.e("originalList", Integer.toString(originalList.size()));
        adapter = new SelectionListAdapter(getActivity().getApplicationContext(), SelectionListFragment.this, originalList, originalList, adapterFor);

        if (searchOn) {

            HashMap<String, String> init2 = pref.getSavedLanguageSCode();
            String code = init2.get(SharedPrefManager.SAVED_S_LANGUAGE);
            Log.e("SEARCH ON", "c" + code);

            if (code == null) {
                code = "en";
            }
            if (code.equalsIgnoreCase("tt") || code.equalsIgnoreCase("zh")) {
                lvCountries.setAdapter(adapter);
            } else {
                for (int i = 0; i < headerPosition.length; i++) {
                    sections.add(new SimpleSectionedListAdapter.Section(headerPosition[i], filteredCountry[i]));
                }
                simpleSectionedGridAdapter = new SimpleSectionedListAdapter(getActivity(), adapter, R.layout.listview_section_header, R.id.txt_listview_header);
                simpleSectionedGridAdapter.setSections(sections.toArray(new SimpleSectionedListAdapter.Section[0]));
                lvCountries.setAdapter(simpleSectionedGridAdapter);

            }

        } else {
            lvCountries.setAdapter(adapter);
        }
    }

    public ArrayList<DropDownItem> initiateDataForAdapter(String name) {

        ArrayList<DropDownItem> listToReturn = new ArrayList<DropDownItem>();

        /*if (name.equals("LANGUAGE_COUNTRY")) {
            listToReturn = BaseFragment.getLanguageCountry(getActivity());
        /*} else if (name.equals("LANGUAGE")) {
            listToReturn = BaseFragment.getLanguage(getActivity());
        } else if (name.equals("LANGUAGE")) {
            listToReturn = BaseFragment.getLanguageOption();

        } else if (name.equals("LANGUAGE")) {
            listToReturn = BaseFragment.getLanguage(getActivity());
        }*/

        if (name.equals("LANGUAGE")) {
            Realm realm = RealmObjectController.getRealmInstance(getActivity());
            final RealmResults<countryLanguageJSON> result9 = realm.where(countryLanguageJSON.class).findAll();
            LanguageCountryReceive languageCountryReceive = (new Gson()).fromJson(result9.get(0).getCountryLanguageReceive(), LanguageCountryReceive.class);

            HashMap<String, String> initCode = pref.getSavedCountryCode();
            String savedCountryCode = initCode.get(SharedPrefManager.SAVED_COUNTRY_CODE);

            //listToReturn = BaseFragment.getLanguageOption(getActivity());
            listToReturn = BaseFragment.getLanguageOption(languageCountryReceive, savedCountryCode);

        } else if (name.equals("CHANGE_COUNTRY")) {

            Realm realm = RealmObjectController.getRealmInstance(getActivity());
            final RealmResults<countryLanguageJSON> result9 = realm.where(countryLanguageJSON.class).findAll();
            LanguageCountryReceive languageCountryReceive = (new Gson()).fromJson(result9.get(0).getCountryLanguageReceive(), LanguageCountryReceive.class);
            listToReturn = BaseFragment.getCountryOption(languageCountryReceive);

        } else if (name.equals("GENDER")) {
            listToReturn = BaseFragment.getGenderBase(getActivity());
        } else if (name.equals("NEWSLETTER_LANGUAGE")) {
            listToReturn = BaseFragment.getNewsletterLanguage(getActivity());
        } else if (name.equals("COUNTRY")) {
            listToReturn = BaseFragment.getCountry(getActivity());
        } else if (name.equals("PAYMENTCOUNTRY")) {
            listToReturn = BaseFragment.getCountry(getActivity());
        } else if (name.equals("PAYMENTSTATE")) {
            listToReturn = PaymentFragment.getStateList();
            /*listToReturn = BaseFragment.getCountry(getActivity());*/
        } else if (name.equals("STATE")) {
            listToReturn = BaseFragment.getState(getActivity());
        } else if (name.equals("NATIONALITY")) {
            listToReturn = BaseFragment.getCountry(getActivity());
        } else if (name.equals("TITLE")) {
            listToReturn = BaseFragment.getTitle(getActivity());
        } else if (name.equals("DEPARTURE_FLIGHT")) {
            listToReturn = SearchFlightFragment.initiateFlightStation(getActivity());
            setSearchMethod(listToReturn);
        } else if (name.equals("ARRIVAL_FLIGHT")) {
            listToReturn = SearchFlightFragment.initiateArrivalStation(getActivity(), stationCode);
            setSearchMethod(listToReturn);
        } else if (name.equals("PROMO_SEARCH")) {
            listToReturn = BaseFragment.getPromoList(getActivity(), "FG");
        } else if (name.equals("FINAL_CALL")) {
            listToReturn = BaseFragment.getPromoList(getActivity(), "FC");
        }


        return listToReturn;
    }

    public void recreateAdapter(ArrayList<DropDownItem> list) {

        //originalList = new ArrayList<DropDownItem>();
        lvCountries.setAdapter(null);
        originalList = initiateDataForAdapter(listName);
        Log.e("OriginalListSize", "b" + Integer.toString(originalList.size()));

        adapter = new SelectionListAdapter(getActivity().getApplicationContext(), SelectionListFragment.this, list, originalList, adapterFor);
        lvCountries.setAdapter(adapter);

    }

    private void sendResult(DropDownItem list) {
        if (getTargetFragment() == null) {

            Log.e("Get Target Fragment", "NULL");
            return;
        }

        Intent intent = new Intent();
        intent.putExtra("LIST_NAME", listName);
        intent.putExtra(listName, list);

        getTargetFragment().onActivityResult(1, Activity.RESULT_OK, intent);
        Log.e("Get Target Fragment", "NOT NULL");
        dismiss();
    }

    /*private void sendResultRecentSearch(String tag) {
        if (getTargetFragment() == null) {
            Log.e("Get Target Fragment", "NULL");
            return;
        }


        intent.putExtra(listName, tag);

        getTargetFragment().onActivityResult(1, Activity.RESULT_OK, intent);
        Log.e("Get Target Fragment", "NOT NULL");
        dismiss();
    }*/


}
