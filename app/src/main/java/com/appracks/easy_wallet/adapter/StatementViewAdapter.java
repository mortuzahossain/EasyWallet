package com.appracks.easy_wallet.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appracks.easy_wallet.R;
import com.appracks.easy_wallet.data_object.StatementData;
import com.appracks.easy_wallet.view.Expense;
import com.appracks.easy_wallet.view.Income;
import com.appracks.easy_wallet.operation.StatementDetails;

import java.util.ArrayList;

public class StatementViewAdapter extends BaseAdapter{

    ArrayList<StatementData> list;
    Context context;
    public static final int incomeAdapter=0;
    public static final int expenseAdapter=1;
    private int type;
    CustomInterfaceAdapter cia;

    public StatementViewAdapter(Activity activity, ArrayList<StatementData> list, int adapterType) {
        this.context=activity;
        this.list=list;
        this.type=adapterType;
        if(type==incomeAdapter){
            cia=(Income)activity;
        }else{
            cia=(Expense)activity;
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.statement_view, parent, false);
        }

        TextView date=(TextView)convertView.findViewById(R.id.tv_date);
        TextView source_way=(TextView)convertView.findViewById(R.id.tv_source_way);
        TextView amount=(TextView)convertView.findViewById(R.id.tv_amount);
        LinearLayout ll= (LinearLayout) convertView.findViewById(R.id.ly_statement);

        final StatementData sd=list.get(position);
        date.setText(sd.getDate());
        source_way.setText(sd.getSourceWay());
        amount.setText(String.valueOf(sd.getAmount()));
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, StatementDetails.class).putExtra("from",type).putExtra("statementObject",sd));
                cia.adapterClick();
            }
        });
        return convertView;
    }
}
