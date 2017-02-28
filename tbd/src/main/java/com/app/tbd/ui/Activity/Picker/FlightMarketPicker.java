package com.app.tbd.ui.Activity.Picker;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
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
import com.app.tbd.ui.Activity.BookingFlight.SearchFlightFragment;
import com.app.tbd.utils.DropDownItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import dev.dworks.libs.astickyheader.SimpleSectionedListAdapter;

public class FlightMarketPicker extends DialogFragment {
    public static final String KEY_COUNTRY_LIST = "countryList";
    public static final String KEY_LANGUAGE_LIST = "languageList";
    public static final String LIST_NAME = "LIST_NAME";
    public static final String PASS_SELECTION_LIST = "PASS_SELECTION_LIST";

    String[] filteredCountry;
    Integer[] headerPosition;

    String listName;

    ArrayList<DropDownItem> holdThisList = new ArrayList<DropDownItem>();
    ArrayList<DropDownItem> list = new ArrayList<DropDownItem>();
    ArrayList<DropDownItem> originalList = new ArrayList<DropDownItem>();

    ListView lvCountries;
    EditText txtSearchCustom;
    FlightMarketAdapter adapter;
    TextView txtCountry;
    TextView txtSelectionTitle;
    ImageButton backbutton;
    LinearLayout searchViewLayout;
    Boolean searchOn = false;
    SimpleSectionedListAdapter simpleSectionedGridAdapter;
    String adapterFor = "NON-FLIGHT";

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

    public static FlightMarketPicker newInstance(String data, ArrayList<DropDownItem> departList) {
        FlightMarketPicker fragment = new FlightMarketPicker();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(PASS_SELECTION_LIST, departList);
        bundle.putString(LIST_NAME, data);

        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_country_list_dialog, container, false);
        lvCountries = (ListView) view.findViewById(R.id.lvCountries);
        txtSelectionTitle = (TextView) view.findViewById(R.id.txtSelectionTitle);
        backbutton = (ImageButton) view.findViewById(R.id.backbutton);
        searchViewLayout = (LinearLayout) view.findViewById(R.id.searchViewLayout);

        list = getArguments().getParcelableArrayList(PASS_SELECTION_LIST);
        holdThisList = list;
        listName = getArguments().getString(LIST_NAME);

        String departure_text = getString(R.string.picker_departure_station);
        String arrival_text = getString(R.string.picker_arrival_station);

        if (listName.equals("DEPARTURE_FLIGHT")) {
            //txtSelectionTitle.setText(getResources().getString(R.string.select_departure_station));
            txtSelectionTitle.setText(departure_text);
            searchViewLayout.setVisibility(View.VISIBLE);
            adapterFor = "FLIGHT";
        } else if (listName.equals("ARRIVAL_FLIGHT")) {
            //txtSelectionTitle.setText(getResources().getString(R.string.select_arrival_station));
            txtSelectionTitle.setText(arrival_text);
            searchViewLayout.setVisibility(View.VISIBLE);

            adapterFor = "FLIGHT";

        }

        //country selection
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
                //MainActivity.this.adapter.getFilter().filter(cs);
                Log.e("holdThisList SIZE", Integer.toString(holdThisList.size()));
                adapter.filter(text, list);
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

        return view;
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
        originalList = list;
        Log.e("originalList", Integer.toString(originalList.size()));
        adapter = new FlightMarketAdapter(getActivity().getApplicationContext(), FlightMarketPicker.this, originalList, originalList, adapterFor);

        if (searchOn) {
            for (int i = 0; i < headerPosition.length; i++) {
                sections.add(new SimpleSectionedListAdapter.Section(headerPosition[i], filteredCountry[i]));
            }
            simpleSectionedGridAdapter = new SimpleSectionedListAdapter(getActivity(), adapter, R.layout.listview_section_header, R.id.txt_listview_header);
            simpleSectionedGridAdapter.setSections(sections.toArray(new SimpleSectionedListAdapter.Section[0]));
            lvCountries.setAdapter(simpleSectionedGridAdapter);
        } else {
            lvCountries.setAdapter(adapter);
        }
    }

    public void recreateAdapter(ArrayList<DropDownItem> useToRecreate) {

        lvCountries.setAdapter(null);
        originalList = list;
        adapter = new FlightMarketAdapter(getActivity().getApplicationContext(), FlightMarketPicker.this, useToRecreate, originalList, adapterFor);
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
}
