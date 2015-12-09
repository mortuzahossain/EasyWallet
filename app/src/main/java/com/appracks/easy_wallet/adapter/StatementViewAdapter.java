package com.appracks.easy_wallet.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.appracks.easy_wallet.R;
import com.appracks.easy_wallet.data_object.StatementData;
import java.util.ArrayList;

public class StatementViewAdapter extends BaseAdapter{

    ArrayList<StatementData> list;
    Context context;
    public static final int incomeAdapter=0;
    public static final int expenseAdapter=1;
    private int type;
    public StatementViewAdapter(Activity activity, ArrayList<StatementData> list, int adapterType) {
        this.context=activity;
        this.list=list;
        this.type=adapterType;
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
                Snackbar.make(v,sd.getDescription()+" from "+String.valueOf(type),Snackbar.LENGTH_LONG).show();
            }
        });
        return convertView;
    }
}
