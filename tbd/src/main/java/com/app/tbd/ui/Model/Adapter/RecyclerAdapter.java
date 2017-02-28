package com.app.tbd.ui.Model.Adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.tbd.R;
import com.app.tbd.ui.Activity.Homepage.HomePromoFragment;
import com.app.tbd.utils.SharedPrefManager;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    Context c;

    public RecyclerAdapter(Context ctx)
    {
        this.c=ctx;
    }

    private String[] from = {"From Kuala Lumpur", "From Kuala Lumpur", "From Kuala Lumpur"};

    private String[] to = {"KOTA BHARU", "KOTA KINABALU", "JOHOR BHARU"};

    private String[] oneway = {"One Way", "Return", "Return"};

    private String[] pts = {"4,500pts", "10,500pts", "500pts"};

    private int[] background = { R.drawable.back_purple, R.drawable.insurance_tbd, R.drawable.back_sand };

    class ViewHolder extends RecyclerView.ViewHolder{

        public int currentItem;
        //public ImageView itemImage;
        public TextView from;
        public TextView to;
        public TextView oneway;
        public TextView pts;
        public LinearLayout back;
        private SharedPrefManager pref;


        public ViewHolder(final View itemView) {
            super(itemView);
            from = (TextView)itemView.findViewById(R.id.item_from);
            to = (TextView)itemView.findViewById(R.id.item_to);
            pts = (TextView)itemView.findViewById(R.id.pts);
            oneway = (TextView)itemView.findViewById(R.id.oneway);
            back = (LinearLayout)itemView.findViewById(R.id.back);
            pref = new SharedPrefManager(c);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {

                   /* int position = getAdapterPosition();

                    Snackbar.make(v, "Click detected on item " + position,
                            Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                            pref.setPromoStatus("Yes");

                    Intent next = new Intent(c, TabActivity.class);
                    c.startActivity(next);*/

                    enterNextFragment();
                }
            });
        }
    }

    private void enterNextFragment() {
        /**/HomePromoFragment myFragment = new HomePromoFragment();
        //FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        FragmentTransaction transaction = ((FragmentActivity)c).getSupportFragmentManager().beginTransaction();
        // Store the Fragment in stack
        transaction.replace(R.id.home_layout, myFragment);
        transaction.addToBackStack(null).commit();
        Log.e("weh", "success");


        //Toast.makeText(itemView.getContext(), "You have clicked " + itemView.getId(), Toast.LENGTH_SHORT).show(); //you can add data to the tag of your cardview in onBind... and retrieve it here with with.getTag().toString()..
        //AppCompatActivity activity = (AppCompatActivity) itemView.getContext();
        //HomePromoFragment myFragment = new HomePromoFragment();
        //activity.getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, myFragment).addToBackStack(null).commit();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.home_detail, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.from.setText(from[i]);
        viewHolder.to.setText(to[i]);
        viewHolder.pts.setText(pts[i]);
        viewHolder.oneway.setText(oneway[i]);
        viewHolder.back.setBackgroundResource(background[i]);
    }

    @Override
    public int getItemCount() {
        return from.length;
    }
}