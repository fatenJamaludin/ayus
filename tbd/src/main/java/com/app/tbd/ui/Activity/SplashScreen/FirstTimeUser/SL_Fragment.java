package com.app.tbd.ui.Activity.SplashScreen.FirstTimeUser;

import android.app.Activity;
import android.app.Dialog;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.tbd.R;
import com.app.tbd.application.AnalyticsApplication;
import com.app.tbd.utils.DropDownItem;
import com.app.tbd.utils.Utils;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import dev.dworks.libs.astickyheader.SimpleSectionedListAdapter;

public class SL_Fragment extends DialogFragment {


    public static final String KEY_COUNTRY_LIST = "countryList";
    public static final String KEY_LANGUAGE_LIST = "languageList";
    public static final String LIST_NAME = "LIST_NAME";
    public static final String PASS_SELECTION_LIST = "PASS_SELECTION_LIST";
    public static final String PASS_SELECTION_LIST_TITLE = "PASS_SELECTION_LIST_TITLE";

    @InjectView(R.id.backbutton)
    ImageView backbutton;

    @InjectView(R.id.txtSelectionTitle)
    TextView txtSelectionTitle;

    ArrayList<DropDownItem> list = new ArrayList<DropDownItem>();
    ListView lvCountries;
    OnBoardingListAdapter adapter;

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

    public static SL_Fragment newInstance(ArrayList<DropDownItem> language) {
        SL_Fragment fragment = new SL_Fragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(PASS_SELECTION_LIST, language);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_language_list_dialog, container, false);
        lvCountries = (ListView) view.findViewById(R.id.lvCountries);
        ButterKnife.inject(this, view);
        /*AnalyticsApplication.sendScreenView("Select language loaded");*/

        list = getArguments().getParcelableArrayList(PASS_SELECTION_LIST);

        /*String title = list.get(0).getTag().toString();*/

       /* if (list == null || list.equals("")){
            Utils.toastNotification(getActivity(), "No language available");

        } else{*/

            txtSelectionTitle.setText(list.get(0).getTag().toString());

            backbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

            initControls();

            lvCountries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    DropDownItem xx = (DropDownItem) parent.getAdapter().getItem(position);
                    sendResult((DropDownItem) parent.getAdapter().getItem(position));
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getRootView().getWindowToken(), 0);

                }
            });
        //}



        return view;
    }


    public void initControls() {

        adapter = new OnBoardingListAdapter(getActivity(), list);
        lvCountries.setAdapter(adapter);

    }

    private void sendResult(DropDownItem list) {
        if (getTargetFragment() == null) {

            Log.e("Get Target Fragment", "NULL");
            return;
        }

        Intent intent = new Intent();
        intent.putExtra("LANGUAGE_LIST", list);

        getTargetFragment().onActivityResult(1, Activity.RESULT_OK, intent);
        Log.e("Get Target Fragment", "NOT NULL");
        dismiss();
    }
}
