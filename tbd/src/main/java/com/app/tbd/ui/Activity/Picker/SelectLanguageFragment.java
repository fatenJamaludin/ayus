package com.app.tbd.ui.Activity.Picker;

import android.app.Activity;
import android.app.Dialog;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.app.tbd.R;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.utils.DropDownItem;

import java.util.ArrayList;

import dev.dworks.libs.astickyheader.SimpleSectionedListAdapter;

public class SelectLanguageFragment extends DialogFragment {
    public static final String KEY_LANGUAGE_LIST = "languageList";

    String[] filteredCountry;
    Integer[] headerPosition;

    ArrayList<DropDownItem> language;
    LinearLayout searchViewLayout;
    ListView lvCountries;
    TextView txtSelectionTitle;
    EditText txtSearchCustom;
    SelectLanguageAdapter adapter;
    TextView txtCountry;
    ImageView backbutton;

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

    public static SelectLanguageFragment newInstance(ArrayList<DropDownItem> countries) {
        SelectLanguageFragment fragment = new SelectLanguageFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(KEY_LANGUAGE_LIST, countries);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        language = getArguments().getParcelableArrayList(KEY_LANGUAGE_LIST);

        View view = inflater.inflate(R.layout.fragment_country_list_dialog, container, false);
        lvCountries = (ListView) view.findViewById(R.id.lvCountries);
        txtSelectionTitle = (TextView) view.findViewById(R.id.txtSelectionTitle);

        searchViewLayout = (LinearLayout) view.findViewById(R.id.searchViewLayout);
        searchViewLayout.setVisibility(View.GONE);
        txtSelectionTitle.setText(getResources().getString(R.string.option_select_language));

        backbutton = (ImageView) view.findViewById(R.id.backbutton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });



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

        adapter = new SelectLanguageAdapter(getActivity().getApplicationContext(), SelectLanguageFragment.this, language);
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
