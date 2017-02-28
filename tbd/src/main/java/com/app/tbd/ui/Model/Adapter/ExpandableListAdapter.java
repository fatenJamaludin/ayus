package com.app.tbd.ui.Model.Adapter;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.tbd.R;
import com.app.tbd.ui.Model.TransactionHistory;
import com.app.tbd.ui.Model.TransactionItem;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<TransactionHistory> groups;

    private Boolean COLOUR = false;

    public ExpandableListAdapter(Context context, ArrayList<TransactionHistory> groups) {
        this.context = context;
        this.groups = groups;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<TransactionItem> chList = groups.get(groupPosition).getItems();
        return chList.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
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

    static class ViewHolderGroup {

        @InjectView(R.id.lblListHeader)
        TextView lblListHeader;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition,boolean isLastChild, View convertView, ViewGroup parent) {

        ViewHolder vh;
        /*TextView txtHistoryMerchant = (TextView) convertView.findViewById(R.id.txtHistoryMerchant);
        TextView txtHistoryPoints = (TextView) convertView.findViewById(R.id.txtHistoryPoints);
        TextView txtTransactionDate = (TextView) convertView.findViewById(R.id.txtTransactionDate);
        ImageView transaction_icon = (ImageView)convertView.findViewById(R.id.transaction_icon);
        LinearLayout transactionDetailLayout = (LinearLayout) convertView.findViewById(R.id.transactionDetailLayout);*/

        TransactionItem child = (TransactionItem) getChild(groupPosition, childPosition);

        if (convertView == null) {

            /*LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.transaction_history_detail, null);*/

            convertView = LayoutInflater.from(context).inflate(R.layout.transaction_history_detail, parent, false);
            vh = new ViewHolder();
            ButterKnife.inject(vh, convertView);
            convertView.setTag(vh);

        }else {
            vh = (ViewHolder) convertView.getTag();
        }

        if (COLOUR) {
            COLOUR = false;
            vh.transactionDetailLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.dark_lvl0));
        } else {
            COLOUR = true;
            vh.transactionDetailLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.dark_lvl0));
        }

        vh.txtTransactionDate.setText(child.getTransactionDate());
        vh.txtHistoryMerchant.setText(child.getMerchant());

        int pts = Integer.parseInt(child.getItemPoint());

        if (pts>0){
            vh.transaction_icon.setImageResource(R.drawable.arrow_in_icon);
            vh.txtHistoryPoints.setText("+" + child.getItemPoint());
        }else{
            vh.transaction_icon.setImageResource(R.drawable.arrow_out_icon);
            vh.txtHistoryPoints.setText(child.getItemPoint());
        }

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        ArrayList<TransactionItem> chList = groups.get(groupPosition).getItems();
        return chList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        Log.e("Group size", String.valueOf(groups.size()));
        return groups.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,View convertView, ViewGroup parent) {

        ViewHolderGroup vhG;
        TransactionHistory group = (TransactionHistory) getGroup(groupPosition);

        if (convertView == null) {

            /*LayoutInflater inf = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.list_group, null);*/

            convertView = LayoutInflater.from(context).inflate(R.layout.list_group, parent, false);
            vhG = new ViewHolderGroup();
            ButterKnife.inject(vhG, convertView);
            convertView.setTag(vhG);
        }else {
            vhG = (ViewHolderGroup) convertView.getTag();
        }

        vhG.lblListHeader.setText(group.getYear());

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
