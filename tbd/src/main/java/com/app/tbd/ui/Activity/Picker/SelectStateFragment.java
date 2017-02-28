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
import android.widget.ListView;
import android.widget.TextView;

import com.app.tbd.R;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Activity.Register.RegisterFragment;
import com.app.tbd.ui.Activity.Register.RegisterFragmentPending;
import com.app.tbd.utils.DropDownItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import dev.dworks.libs.astickyheader.SimpleSectionedListAdapter;

public class SelectStateFragment extends DialogFragment {
    public static final String KEY_STATE_LIST = "stateList";

    String[] filteredCountry;
    Integer[] headerPosition;

    ArrayList<DropDownItem> countries;
    ArrayList<DropDownItem> originalCountries = new ArrayList<DropDownItem>();

    ListView lvCountries;
    EditText txtSearchCustom;
    SelectStateAdapter adapter;
    TextView txtCountry;
    SimpleSectionedListAdapter simpleSectionedGridAdapter;

    private ArrayList<SimpleSectionedListAdapter.Section> sections = new ArrayList<SimpleSectionedListAdapter.Section>();

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

    public static SelectStateFragment newInstance(ArrayList<DropDownItem> countries) {
        SelectStateFragment fragment = new SelectStateFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(KEY_STATE_LIST, countries);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        countries = getArguments().getParcelableArrayList(KEY_STATE_LIST);

        View view = inflater.inflate(R.layout.fragment_country_list_dialog, container, false);
        lvCountries = (ListView) view.findViewById(R.id.lvCountries);
        getDialog().setTitle(getActivity().getString(R.string.search_flight_title));

        //GET CHAR AT - FILTER
       /* List<String> countryChar = new ArrayList<String>();
        for (int i = 0; i < countries.size(); i++) {
            String country = countries.get(i).getText();
            countryChar.add(Character.toString(country.charAt(0)));
        }

        //GET HEADER POSITION

        filteredCountry = BaseFragment.getCharAt(countryChar);
        headerPosition = BaseFragment.headerPosition(countryChar);
*/
        txtSearchCustom = (EditText) view.findViewById(R.id.txtSearchCustom);
        initControls();
        txtSearchCustom.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String text = txtSearchCustom.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });

        //lvCountries.setAdapter(adapter);
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

    public void initControls() {
        originalCountries = BaseFragment.getState(getActivity());

        adapter = new SelectStateAdapter(getActivity().getApplicationContext(), SelectStateFragment.this, countries, originalCountries);
        //for (int i = 0; i < headerPosition.length; i++) {
        //    sections.add(new SimpleSectionedListAdapter.Section(headerPosition[i], filteredCountry[i]));
        // }

        //simpleSectionedGridAdapter = new SimpleSectionedListAdapter(getActivity(), adapter, R.layout.listview_section_header, R.id.txt_listview_header);
        //simpleSectionedGridAdapter.setSections(sections.toArray(new SimpleSectionedListAdapter.Section[0]));

        lvCountries.setAdapter(adapter);
    }

    public void notifyAnotherAdapter() {
        simpleSectionedGridAdapter.notifyDataSetChanged();
    }

    public void recreateAdapter(ArrayList<DropDownItem> countries2) {

        lvCountries.setAdapter(null);
        sections = new ArrayList<SimpleSectionedListAdapter.Section>();

        //List<String> countryChar = new ArrayList<String>();
        // for (int i = 0; i < countries2.size(); i++) {
        // String country = countries2.get(i).getText();
        //   countryChar.add(Character.toString(country.charAt(0)));
        //}
        //filteredCountry = BaseFragment.getCharAt(countryChar);
        ///eaderPosition = BaseFragment.headerPosition(countryChar);

        originalCountries = BaseFragment.getState(getActivity());

        adapter = new SelectStateAdapter(getActivity().getApplicationContext(), SelectStateFragment.this, countries2, originalCountries);

        //for (int i = 0; i < headerPosition.length; i++) {
        //    sections.add(new SimpleSectionedListAdapter.Section(headerPosition[i], filteredCountry[i]));
        // }

        //SimpleSectionedListAdapter simpleSectionedGridAdapter = new SimpleSectionedListAdapter(getActivity(), adapter, R.layout.listview_section_header, R.id.txt_listview_header);
        //simpleSectionedGridAdapter.setSections(sections.toArray(new SimpleSectionedListAdapter.Section[0]));
        lvCountries.setAdapter(adapter);

    }

    private void sendResult(DropDownItem country) {
        if (getTargetFragment() == null) {

            Log.e("Get Target Fragment", "NULL");
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(KEY_STATE_LIST, country);

        getTargetFragment().onActivityResult(1, Activity.RESULT_OK, intent);
        Log.e("Get Target Fragment", "NOT NULL");
        dismiss();
    }

}
