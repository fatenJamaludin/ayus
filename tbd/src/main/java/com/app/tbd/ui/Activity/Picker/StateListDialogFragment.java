package com.app.tbd.ui.Activity.Picker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.app.tbd.R;
import com.app.tbd.utils.DropDownItem;

import java.util.ArrayList;

public class StateListDialogFragment extends DialogFragment{
    public static final String KEY_COUNTRY_LIST = "countryList";
    public static final String EXTRA_COUNTRY = "country";
    public static final String EXTRA_PARAM = "param";

    ArrayList<DropDownItem> countries;
    ListView lvCountries;
    SearchView etSearchCountry;
    CountryListDialogAdapter adapter;
    TextView txtCountry;

    public static StateListDialogFragment newInstance(ArrayList<DropDownItem> countries) {
        StateListDialogFragment fragment = new StateListDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(KEY_COUNTRY_LIST, countries);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        countries = getArguments().getParcelableArrayList(KEY_COUNTRY_LIST);
        getDialog().setTitle(getActivity().getString(R.string.stateListDialogTitle));

        View view = inflater.inflate(R.layout.fragment_country_list_dialog, container, false);
        lvCountries = (ListView) view.findViewById(R.id.lvCountries);
        //etSearchCountry = (SearchView) view.findViewById(R.id.etSearchCountry);
        //etSearchCountry.setOnQueryTextListener(this);

        //adapter = new CountryListDialogAdapter(getActivity().getApplicationContext(), countries);
        lvCountries.setAdapter(adapter);
        lvCountries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DropDownItem xx = (DropDownItem) parent.getAdapter().getItem(position);

                // View country = inflater.inflate(R.layout.register, container, false);
                // txtCountry = (TextView) country.findViewById(R.id.editTextCountry);
                // txtCountry.setText("Hello World");
                String param = xx.getText();
                sendResult((DropDownItem) parent.getAdapter().getItem(position));
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getRootView().getWindowToken(), 0);


            }
        });

        return view;
    }

    private void sendResult(DropDownItem country) {
        if (getTargetFragment() == null) {

            Log.e("Get Target Fragment", "NULL");
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_COUNTRY, country);

        getTargetFragment().onActivityResult( 1 , Activity.RESULT_OK, intent);
        Log.e("Get Target Fragment", "NOT NULL");
        dismiss();
    }

    /*@Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.getFilter().filter(newText);
        return false;
    }*/

}
