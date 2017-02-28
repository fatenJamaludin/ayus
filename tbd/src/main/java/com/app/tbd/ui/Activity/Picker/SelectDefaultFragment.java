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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.app.tbd.R;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Activity.Register.RegisterFragment;
import com.app.tbd.utils.DropDownItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import dev.dworks.libs.astickyheader.SimpleSectionedListAdapter;

public class SelectDefaultFragment extends DialogFragment {
    public static final String KEY_LANGUAGE_LIST = "languageList";

    LinearLayout searchViewLayout;
    ArrayList<DropDownItem> language;
    ListView lvCountries;
    SelectDefaultFragmentAdapter adapter;

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

    public static SelectDefaultFragment newInstance(ArrayList<DropDownItem> countries) {
        SelectDefaultFragment fragment = new SelectDefaultFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(KEY_LANGUAGE_LIST, countries);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        language = getArguments().getParcelableArrayList(KEY_LANGUAGE_LIST);
        String language_text = getString(R.string.picker_preferred);

        View view = inflater.inflate(R.layout.fragment_country_list_dialog, container, false);
        lvCountries = (ListView) view.findViewById(R.id.lvCountries);
        getDialog().setTitle(language_text);
        searchViewLayout = (LinearLayout) view.findViewById(R.id.searchViewLayout);
        searchViewLayout.setVisibility(View.GONE);

        initControls();

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

        adapter = new SelectDefaultFragmentAdapter(getActivity().getApplicationContext(), SelectDefaultFragment.this, language);
        lvCountries.setAdapter(adapter);
    }

    private void sendResult(DropDownItem country) {
        if (getTargetFragment() == null) {

            Log.e("Get Target Fragment", "NULL");
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(KEY_LANGUAGE_LIST, country);

        getTargetFragment().onActivityResult(1, Activity.RESULT_OK, intent);
        Log.e("Get Target Fragment", "NOT NULL");
        dismiss();
    }

}
