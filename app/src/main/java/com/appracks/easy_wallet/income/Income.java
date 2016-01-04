package com.appracks.easy_wallet.income;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.appracks.easy_wallet.MainActivity;
import com.appracks.easy_wallet.R;
import com.appracks.easy_wallet.adapter.CustomInterfaceAdapter;
import com.appracks.easy_wallet.adapter.StatementViewAdapter;
import com.appracks.easy_wallet.data_object.StatementData;
import com.appracks.easy_wallet.database.DB_Manager;
import com.appracks.easy_wallet.dateOperation.DateOperation;
import com.appracks.easy_wallet.expense.Expense;
import com.appracks.easy_wallet.graph.Graph;
import com.appracks.easy_wallet.operation.AddStatement;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdRequest;

import java.util.ArrayList;

public class Income extends AppCompatActivity implements CustomInterfaceAdapter{

    private ListView lv_in_statement;
    StatementViewAdapter statementViewAdapter;
    DrawerLayout myDrawer;
    ActionBarDrawerToggle actionBarDrawerToggle;
    private TextView tv_category;
    Toolbar toolbar;
    DB_Manager dbManager;
    DateOperation dt;
    ArrayList<StatementData> list;
    String firstDate,secondDate;
    private TextView tv_nav_balance,tv_filter_balance;
    private ImageButton btn_add_statement;
    boolean isFirst=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);
        toolbar=(Toolbar)findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        dbManager=DB_Manager.getInstance(this);
        dt=new DateOperation();
        lv_in_statement=(ListView)findViewById(R.id.lv_in_statement);
        tv_category=(TextView)findViewById(R.id.tv_category);
        tv_nav_balance=(TextView)findViewById(R.id.tv_current_balance);
        tv_filter_balance=(TextView)findViewById(R.id.tv_filter_balance);
        btn_add_statement=(ImageButton)findViewById(R.id.btn_add_statement);
        btn_add_statement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Income.this, AddStatement.class).putExtra("from", "in"));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        });
        setNavMenu();
        final Spinner spn_filter_category=(Spinner)findViewById(R.id.spn_filter_category);
        spn_filter_category.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.spinner_filter_category)));
        spn_filter_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setStatementList(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                setStatementList(0);
                spn_filter_category.setSelection(0, true);
            }
        });
        int i=getIntent().getIntExtra("cat_type",99);
        setStatementList(i);
        spn_filter_category.setSelection(i, true);
        showBannerAds();
    }
    private void setStatementList(int cat){
        double[] summery=dbManager.getSummery();
        if(cat==0){//0 for weekly
            list=dbManager.getBetweenDateStatement(dt.getDateOrder(dt.getCurrentDateN7()),dt.getCurrentDateOrder(),"in");
            statementViewAdapter=new StatementViewAdapter(this,list,StatementViewAdapter.incomeAdapter);
            statementViewAdapter.notifyDataSetChanged();
            lv_in_statement.setAdapter(statementViewAdapter);
            tv_category.setText(":::  Current week income  :::");
            tv_filter_balance.setText(String.valueOf(summery[0]));
        }else if(cat==1){//1 for monthly
            list=dbManager.getMonthStatement(dt.getCurrentMonth(), dt.getCurrentYear(), "in");
            statementViewAdapter=new StatementViewAdapter(this,list,StatementViewAdapter.incomeAdapter);
            statementViewAdapter.notifyDataSetChanged();
            lv_in_statement.setAdapter(statementViewAdapter);
            tv_category.setText(":::  Current month income  :::");
            tv_filter_balance.setText(String.valueOf(summery[1]));
        }else if(cat==2){//2 for yearly
            list=dbManager.getYearStatement(dt.getCurrentYear(),"in");
            statementViewAdapter=new StatementViewAdapter(this,list,StatementViewAdapter.incomeAdapter);
            statementViewAdapter.notifyDataSetChanged();
            lv_in_statement.setAdapter(statementViewAdapter);
            tv_category.setText(":::  Current year income  :::");
            tv_filter_balance.setText(String.valueOf(summery[2]));
        }else if(cat==3){//3 for all
            list=dbManager.getAllStatement("in");
            statementViewAdapter=new StatementViewAdapter(this,list,StatementViewAdapter.incomeAdapter);
            statementViewAdapter.notifyDataSetChanged();
            lv_in_statement.setAdapter(statementViewAdapter);
            tv_category.setText(":::  All income  :::");
            tv_filter_balance.setText(String.valueOf(summery[3]));
        }else if(cat==4){//4 for date wise
            //noinspection deprecation
            showDialog(1);
        }
        tv_nav_balance.setText(String.valueOf(summery[8]));
        if(summery[8]<0){
            tv_nav_balance.setTextColor(Color.RED);
        }
    }

    private void setNavMenu(){
        myDrawer= (DrawerLayout) findViewById(R.id.myDrawer);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this, myDrawer,toolbar, R.string.drawer_open, R.string.drawer_close);
        myDrawer.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        LinearLayout ly_summery=(LinearLayout)findViewById(R.id.ly_summery);
        ly_summery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDrawer.closeDrawer(GravityCompat.START);
                onBackPressed();
            }
        });
        LinearLayout ly_income=(LinearLayout)findViewById(R.id.ly_income);
        ly_income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDrawer.closeDrawer(GravityCompat.START);
                Toast.makeText(getApplicationContext(), "You are here", Toast.LENGTH_LONG).show();
            }
        });
        LinearLayout ly_expense=(LinearLayout)findViewById(R.id.ly_expense);
        ly_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDrawer.closeDrawer(GravityCompat.START);
                startActivity(new Intent(Income.this, Expense.class).putExtra("cat_type", 0));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        });
        LinearLayout ly_graph=(LinearLayout)findViewById(R.id.ly_graph);
        ly_graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDrawer.closeDrawer(GravityCompat.START);
                startActivity(new Intent(Income.this, Graph.class));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Income.this, MainActivity.class));
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        finish();
    }
    private void setBetweenStatement(){
        list=dbManager.getBetweenDateStatement(dt.getDateOrder(firstDate),dt.getDateOrder(secondDate),"in");
        statementViewAdapter=new StatementViewAdapter(this,list,StatementViewAdapter.incomeAdapter);
        statementViewAdapter.notifyDataSetChanged();
        lv_in_statement.setAdapter(statementViewAdapter);
        tv_category.setText("::: Income from " + firstDate + " to " + secondDate + " :::");
        tv_filter_balance.setText(String.valueOf(dbManager.getBetweenDateAmount(dt.getDateOrder(firstDate),dt.getDateOrder(secondDate),"in")));
    }

    @SuppressWarnings("deprecation")
    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 1) {
            return new DatePickerDialog(this, firstDateListener, Integer.valueOf(dt.getCurrentYear()), Integer.valueOf(dt.getCurrentMonth())-1, Integer.valueOf(dt.getCurrentDay()));
        }else if (id == 2) {
            return new DatePickerDialog(this, secondDateListener, Integer.valueOf(dt.getCurrentYear()), Integer.valueOf(dt.getCurrentMonth())-1, Integer.valueOf(dt.getCurrentDay()));
        }
        return null;
    }
    @SuppressWarnings("deprecation")
    private DatePickerDialog.OnDateSetListener firstDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int year, int month, int day) {
            firstDate=dt.getDateFromRaw(year, month+1, day);
            showDialog(2);
        }
    };
    private DatePickerDialog.OnDateSetListener secondDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int year, int month, int day) {
            secondDate=dt.getDateFromRaw(year, month+1, day);
            setBetweenStatement();
        }
    };

    @Override
    public void adapterClick() {
        overridePendingTransition(R.anim.push_up_in, R.anim.style_static);
        finish();
    }
    public void showBannerAds(){
        final LinearLayout ll=(LinearLayout)findViewById(R.id.full_layout);
        final LinearLayout la=(LinearLayout)findViewById(R.id.amount_lay);
        final AdView mAdView = (AdView) findViewById(R.id.adView);
        mAdView.setVisibility(View.GONE);
        final AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                if(isFirst){
                    ll.setPadding(0, 0, 0, 200);
                    mAdView.loadAd(adRequest);
                    isFirst=false;
                    mAdView.setVisibility(View.VISIBLE);
                }else{
                    ll.setPadding(0, 0, 0, la.getHeight()+mAdView.getHeight());
                }
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }
        });
    }
}
