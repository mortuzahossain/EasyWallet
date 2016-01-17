package com.appracks.easy_wallet.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appracks.easy_wallet.R;
import com.appracks.easy_wallet.data_object.StatementData;
import com.appracks.easy_wallet.operation.StatementDetails;
import com.appracks.easy_wallet.view.Expense;
import com.appracks.easy_wallet.view.Income;

import java.util.ArrayList;

public class CategoryNameViewAdapter extends BaseAdapter{

    String[] list;
    Context context;
    public static final int incomeAdapter=0;
    public static final int expenseAdapter=1;
    private int type;
    CustomInterfaceAdapter cia;
    private ArrayList<Boolean> itemChecked = new ArrayList<Boolean>();

    public CategoryNameViewAdapter(Activity activity, String[] list, int adapterType) {
        this.context=activity;
        this.list=list;
        this.type=adapterType;
        if(type==incomeAdapter){
            cia=(Income)activity;
        }else{
            cia=(Expense)activity;
        }
        for (int i = 0; i < this.getCount(); i++) {
            itemChecked.add(i, false); // initializes all items value with false
        }
    }

    @Override
    public int getCount() {
        return list.length;
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
    public View getView(final int pos, View inView, ViewGroup parent) {
        if (inView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inView = inflater.inflate(R.layout.cat_view, null);
        }

        final CheckBox cBox = (CheckBox) inView.findViewById(R.id.cb_cat_name);
        cBox.setText(list[pos]);
        cBox.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                CheckBox cb = (CheckBox) v.findViewById(R.id.cb_cat_name);

                if (cb.isChecked()) {
                    itemChecked.set(pos, true);
                    cia.addCat(cb.getText().toString());
                    // do some operations here
                } else if (!cb.isChecked()) {
                    itemChecked.set(pos, false);
                    // do some operations here
                    cia.removeCat(cb.getText().toString());
                }
            }
        });
        cBox.setChecked(itemChecked.get(pos));
        return inView;
    }
}
