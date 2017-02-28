package com.app.tbd.ui.Model.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.tbd.R;
import com.app.tbd.ui.Model.Receive.TransactionHistoryReceive;
import com.app.tbd.ui.Model.TransactionHistory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TransactionHistoryAdapter extends BaseAdapter {

    private final Context context;
    private final List<TransactionHistoryReceive.Transaction> obj;
    private final List<TransactionHistory> head = new ArrayList<TransactionHistory>();
    TransactionHistory mItem = new TransactionHistory();
    private Integer selected_position = -1;
    private Boolean COLOUR = false;

    public TransactionHistoryAdapter(Context context, List<TransactionHistoryReceive.Transaction> arrayList, String module) {
        this.context = context;
        this.obj = arrayList;
    }

    @Override
    public int getCount() {
        return obj == null ? 0 : obj.size();
    }

    @Override
    public Object getItem(int position) {
        return obj == null ? null : obj.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    static class ViewHolder {

        @InjectView(R.id.txtTransactionDate)
        TextView txtTransactionDate;

        @InjectView(R.id.txtHistoryMerchant)
        TextView txtHistoryMerchant;

        @InjectView(R.id.txtHistoryPoints)
        TextView txtHistoryPoints;

        @InjectView(R.id.transactionDetailLayout)
        LinearLayout transactionDetailLayout;

        @InjectView(R.id.transaction_icon)
        ImageView transaction_icon;

    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        ViewHolder vh;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.transaction_history_detail, parent, false);
            vh = new ViewHolder();
            ButterKnife.inject(vh, view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }

        if (COLOUR) {
            COLOUR = false;
            vh.transactionDetailLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.dark_lvl0));
        } else {
            COLOUR = true;
            vh.transactionDetailLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.dark_lvl0));
        }

         // <-- instantiate a new Item.

        /*int length = obj.size(); //start with 1
        //start with 1

        for (int b=0; b<length; b++){
            String date = obj.get(b).getTxnDate();
            String year = date.substring(date.length()-4);
            if (!head.contains(year)) {
                mItem.setHeaderDate(year);
                head.add(mItem);
                Log.e(year, Integer.toString(head.size()));//2016 : 1
                Log.e(year, head.toString());
            }
        }

        int title = head.size();

        for (int a=0; a<title; a++){
            String y = head.get(a).getHeaderDate();
            vh.lblListHeader.setText(y);
        }*/

        vh.txtTransactionDate.setText(obj.get(position).getTxnDate());
        vh.txtHistoryMerchant.setText(obj.get(position).getMID());

        int pts = Integer.parseInt(obj.get(position).getTxnPts());

        if (pts>0){
            vh.transaction_icon.setImageResource(R.drawable.arrow_in_icon);
            vh.txtHistoryPoints.setText("+"+obj.get(position).getTxnPts());
        }else{
            vh.transaction_icon.setImageResource(R.drawable.arrow_out_icon);
            vh.txtHistoryPoints.setText(obj.get(position).getTxnPts());
        }

        return view;

    }
}
