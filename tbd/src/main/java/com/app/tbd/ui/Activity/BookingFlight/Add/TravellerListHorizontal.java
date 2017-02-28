package com.app.tbd.ui.Activity.BookingFlight.Add;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.tbd.R;
import com.app.tbd.application.AnalyticsApplication;
import com.app.tbd.ui.Activity.DragDrop.DropClass;
import com.app.tbd.ui.Activity.Homepage.HomePromoFragment;
import com.app.tbd.ui.Model.Adapter.RecyclerAdapter;
import com.app.tbd.ui.Model.JSON.SeatCached;
import com.app.tbd.ui.Model.JSON.TravellerAddon;
import com.app.tbd.ui.Model.JSON.TravellerInfo;
import com.app.tbd.ui.Model.JSON.UserInfoJSON;
import com.app.tbd.ui.Model.JSON.countryLanguageJSON;
import com.app.tbd.ui.Model.Receive.BaggageMealReceive;
import com.app.tbd.ui.Model.Receive.LanguageCountryReceive;
import com.app.tbd.ui.Model.Receive.LoadInsuranceReceive;
import com.app.tbd.ui.Model.Receive.TBD.LoginReceive;
import com.app.tbd.ui.Realm.RealmObjectController;
import com.app.tbd.utils.SharedPrefManager;
import com.app.tbd.utils.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.realm.Realm;
import io.realm.RealmResults;

public class TravellerListHorizontal extends RecyclerView.Adapter<TravellerListHorizontal.ViewHolder> {

    Activity c;
    TravellerInfo travellerInfos;
    ArrayList<ArrayList<TravellerAddon>> travellerAddonSegment;
    int segment;
    int max;
    BaggageFragmentV2 frag;
    View mainView;
    /*BaggageMealReceive baggageMealReceive;*/

    public TravellerListHorizontal(BaggageFragmentV2 frag, View mainView, Activity ctx, TravellerInfo obj, int segment, ArrayList<ArrayList<TravellerAddon>> travellerAddonSegment, int max/*, BaggageMealReceive baggageMealReceive*/) {
        this.c = ctx;
        this.travellerInfos = obj;
        this.travellerAddonSegment = travellerAddonSegment;
        this.segment = segment;
        this.max = max;
        this.frag = frag;
        this.mainView = mainView;
        /*this.baggageMealReceive = baggageMealReceive;*/
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.txtName)
        TextView txtName;

        @InjectView(R.id.ssrQtyIndicator)
        LinearLayout ssrQtyIndicator;

        @InjectView(R.id.txtSsrQtyIndicator)
        TextView txtSsrQtyIndicator;

        @InjectView(R.id.userImg)
        LinearLayout userImg;

        @InjectView(R.id.userImage)
        ImageView userImage;

        @InjectView(R.id.onAddAddon)
        LinearLayout onAddAddon;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.traveller_horizontal_list, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        v.setOnDragListener(new DropClass(i));
        v.setOnClickListener(new ClickClass(i));

        Log.e("I", Integer.toString(i));

        /*ClickClass
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.e(Integer.toString(segment), Integer.toString(i));
            }
        });*/
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        //first user take the image.. if available.
        Realm realm = RealmObjectController.getRealmInstance(c);
        final RealmResults<UserInfoJSON> result3 = realm.where(UserInfoJSON.class).findAll();
        LoginReceive loginReceive = (new Gson()).fromJson(result3.get(0).getUserInfo(), LoginReceive.class);

        String name = loginReceive.getFirstName() + loginReceive.getLastName();
        String inAdapterName = travellerInfos.getList().get(i).getGiven_name() + travellerInfos.getList().get(i).getFamily_name();

        if (name.equals(inAdapterName)) {

            if (loginReceive.getProfile_URL() == null || loginReceive.getProfile_URL().equals("")) {
                Log.e("No Image", "Y");
                String AB = (getFirstCharacter(travellerInfos.getList().get(i).getGiven_name()) + getFirstCharacter(travellerInfos.getList().get(i).getFamily_name()));
                viewHolder.txtName.setText(AB);
                viewHolder.txtName.setVisibility(View.VISIBLE);
                viewHolder.userImg.setVisibility(View.GONE);

            } else {
                viewHolder.userImg.setVisibility(View.VISIBLE);
                viewHolder.txtName.setVisibility(View.GONE);

                Glide.with(c)
                        .load(loginReceive.getProfile_URL())
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(viewHolder.userImage);
            }

        } else {
            String AB = (getFirstCharacter(travellerInfos.getList().get(i).getGiven_name()) + getFirstCharacter(travellerInfos.getList().get(i).getFamily_name()));
            viewHolder.txtName.setText(AB);
            viewHolder.txtName.setVisibility(View.VISIBLE);
            viewHolder.userImg.setVisibility(View.GONE);
        }

        if (travellerAddonSegment.get(segment).get(i).getSsrListPerPassenger() != null) {
            if (travellerAddonSegment.get(segment).get(i).getSsrListPerPassenger().size() > 0) {
                int ssrSizePerPassenger = travellerAddonSegment.get(segment).get(i).getSsrListPerPassenger().size();
                viewHolder.txtSsrQtyIndicator.setText(Integer.toString(ssrSizePerPassenger));
                viewHolder.ssrQtyIndicator.setVisibility(View.VISIBLE);
                Log.e("Notify", "More than 0");

            } else {
                Log.e("Notify", "Less than 0");
                viewHolder.ssrQtyIndicator.setVisibility(View.GONE);
                Log.e(travellerInfos.getList().get(i).getGiven_name(), Integer.toString(travellerAddonSegment.get(segment).get(i).getSsrListPerPassenger().size()));
            }
        } else {
            Log.e("Notify", "NA");
            viewHolder.ssrQtyIndicator.setVisibility(View.GONE);
        }

        viewHolder.onAddAddon.setTag(Integer.toString(i));

    }

    public String getFirstCharacter(String givenString) {


        String toBeCapped = givenString.trim();

        String[] tokens = toBeCapped.split("\\s"); // equal to white space
        toBeCapped = "";

        //loop each string - convert first letter to capital letter
        for (int i = 0; i < 1; i++) {
            char capLetter = Character.toUpperCase(tokens[i].charAt(0));
            toBeCapped += capLetter;
        }

        //remove white space at start & end
        toBeCapped = toBeCapped.trim();

        return toBeCapped;
    }

    @Override
    public int getItemCount() {
        return travellerInfos.getList().size();
    }

    public ArrayList<ArrayList<TravellerAddon>> getAdapterObj() {
        return travellerAddonSegment;
    }

    public void customNotify(ArrayList<ArrayList<TravellerAddon>> obj) {
        travellerAddonSegment = obj;
        notifyDataSetChanged();
        Log.e("Notify Data", "Y");
    }

    //drag class
    class ClickClass implements View.OnClickListener {

        int dropPosition;

        public ClickClass(int dropPosition) {
            this.dropPosition = dropPosition;
        }

        @Override
        public void onClick(View v) {
            LinearLayout onAddAddon = (LinearLayout) v.findViewById(R.id.onAddAddon);
            //Log.e("x", onAddAddon.getTag().toString());
            frag.showBottomFlipper(c, mainView, travellerInfos, travellerAddonSegment, segment, Integer.parseInt(onAddAddon.getTag().toString()));

        }
    }

    //drag class
    class DropClass implements View.OnDragListener {

        int dropPosition;

        public DropClass(int dropPosition) {
            this.dropPosition = dropPosition;
            // Log.e("Position", Integer.toString(dropPosition));
        }

        Drawable enterShape = c.getResources().getDrawable(R.drawable.shape_drop);
        Drawable normalShape = c.getResources().getDrawable(R.drawable.shape);

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();

            LinearLayout onAddAddon = (LinearLayout) v.findViewById(R.id.onAddAddon);

            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    Log.e("DRAG ON", "Y");
                    //myimage1.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.arrow_in_icon));

                    break;
                case DragEvent.ACTION_DRAG_ENTERED:

                    Log.e("ACTION_DRAG_ENTERED", onAddAddon.getTag().toString());
                    onAddAddon.setVisibility(View.VISIBLE);
                    //v.setBackgroundDrawable(enterShape);

                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    Log.e("DRAG EXITED", "Y");
                    //v.setBackgroundDrawable(normalShape);
                    onAddAddon.setVisibility(View.GONE);

                    break;
                case DragEvent.ACTION_DROP:

                    onAddAddon.setVisibility(View.GONE);
                    Log.e("ACTION_DROP DROP", onAddAddon.getTag().toString());

                    if (travellerAddonSegment.get(segment).get(Integer.parseInt(onAddAddon.getTag().toString())).getSsrListPerPassenger() != null) {
                        if (travellerAddonSegment.get(segment).get(Integer.parseInt(onAddAddon.getTag().toString())).getSsrListPerPassenger().size() == max) {
                            String message = AnalyticsApplication.getContext().getString(R.string.text_message);
                            Utils.toastNotification(c, message);
                        } else {
                            String clipData = event.getClipDescription().getLabel().toString();
                            travellerAddonSegment.get(segment).get(Integer.parseInt(onAddAddon.getTag().toString())).getSsrListPerPassenger().add(clipData);
                            notifyDataSetChanged();
                        }
                    }


                    // Dropped, reassign View to ViewGroup
                    /*View view = (View) event.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();
                    owner.removeView(view);
                    LinearLayout container = (LinearLayout) v;
                    container.addView(view);
                    view.setVisibility(View.VISIBLE);*/


                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    //v.setBackgroundDrawable(normalShape);
                default:
                    break;
            }
            return true;
        }
    }
}