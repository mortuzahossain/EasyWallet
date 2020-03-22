package com.appracks.easy_wallet.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.appracks.easy_wallet.adapter.CustomInterfaceAdapter;
import com.appracks.easy_wallet.OverViewActivity;
import com.appracks.easy_wallet.R;
import com.appracks.easy_wallet.adapter.NDSpinner;
import com.appracks.easy_wallet.adapter.StatementViewAdapter;
import com.appracks.easy_wallet.data_object.StatementData;
import com.appracks.easy_wallet.database.DB_Manager;
import com.appracks.easy_wallet.dateOperation.DateOperation;
import com.appracks.easy_wallet.operation.AddStatement;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

public class Expense extends AppCompatActivity implements CustomInterfaceAdapter{

    private ListView lv_in_statement;
    StatementViewAdapter statementViewAdapter;
    DrawerLayout myDrawer;
    ActionBarDrawerToggle actionBarDrawerToggle;
    private TextView tv_category,tv_date_from,tv_date_to;
    Toolbar toolbar;
    DB_Manager dbManager;
    DateOperation dt;
    ArrayList<StatementData> list;
    String firstDate,secondDate;
    private TextView tv_nav_balance,tv_filter_balance;
    ImageView iv_currency_ex;
    private ImageButton btn_add_statement;
    boolean isFirst=true;
    LinearLayout lay_category_wise;
    Spinner spn_filter_category_list;
    NDSpinner spn_filter_category;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        toolbar=(Toolbar)findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        dbManager=DB_Manager.getInstance(this);
        dt=new DateOperation();

        lay_category_wise=(LinearLayout)findViewById(R.id.lay_category_wise);
        iv_currency_ex=(ImageView)findViewById(R.id.iv_currency_ex);
        if(OverViewActivity.sign.equalsIgnoreCase("DOLLAR")){
            iv_currency_ex.setImageResource(R.drawable.dollar_sign);
        }else if(OverViewActivity.sign.equalsIgnoreCase("TAKA")){
            iv_currency_ex.setImageResource(R.drawable.taka_sign);
        }else if(OverViewActivity.sign.equalsIgnoreCase("POUND")){
            iv_currency_ex.setImageResource(R.drawable.pound_sign);
        }else if(OverViewActivity.sign.equalsIgnoreCase("RUPEE")){
            iv_currency_ex.setImageResource(R.drawable.rupee_sign);
        }else if(OverViewActivity.sign.equalsIgnoreCase("RIAL")){
            iv_currency_ex.setImageResource(R.drawable.rial_sign);
        }else if(OverViewActivity.sign.equalsIgnoreCase("EURO")){
            iv_currency_ex.setImageResource(R.drawable.euro_sign);
        }else if(OverViewActivity.sign.equalsIgnoreCase("YEN")){
            iv_currency_ex.setImageResource(R.drawable.yen_sign);
        }else if(OverViewActivity.sign.equalsIgnoreCase("YUAN")){
            iv_currency_ex.setImageResource(R.drawable.yuan_sign);
        }else if(OverViewActivity.sign.equalsIgnoreCase("FRANC")){
            iv_currency_ex.setImageResource(R.drawable.franc_sign);
        }else if(OverViewActivity.sign.equalsIgnoreCase("NO CURRENCY")){
            iv_currency_ex.setImageResource(R.drawable.no_currency);
        }

        lv_in_statement=(ListView)findViewById(R.id.lv_in_statement);
        lv_in_statement=(ListView)findViewById(R.id.lv_in_statement);
        tv_date_from=(TextView)findViewById(R.id.tv_date_from);
        tv_date_to=(TextView)findViewById(R.id.tv_date_to);

        tv_category=(TextView)findViewById(R.id.tv_category);
        tv_nav_balance=(TextView)findViewById(R.id.tv_current_balance);
        tv_filter_balance=(TextView)findViewById(R.id.tv_filter_balance);
        btn_add_statement=(ImageButton)findViewById(R.id.btn_add_statement);
        btn_add_statement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Expense.this, AddStatement.class).putExtra("from","ex"));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        });
        setNavMenu();
        spn_filter_category_list=(Spinner)findViewById(R.id.spn_filter_category_list);
        spn_filter_category_list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dbManager.getAllCategory("ex")));

        spn_filter_category=(com.appracks.easy_wallet.adapter.NDSpinner)findViewById(R.id.spn_filter_category);
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
        i=getIntent().getIntExtra("cat_type",99);
        setStatementList(i);
        spn_filter_category.setSelection(i,true);
        showBannerAds();

    }
    public void btn_setDateFrom(View v){
        //noinspection deprecation
        showDialog(3);
    }
    public void btn_setDateTo(View v){
        //noinspection deprecation
        showDialog(4);
    }
    private void setStatementList(int cat){
        double[] summery=dbManager.getSummery();
        if(cat==0){//0 for weekly
            list=dbManager.getBetweenDateStatement(dt.getDateOrder(dt.getCurrentDateN7()),dt.getCurrentDateOrder(),"ex");
            statementViewAdapter=new StatementViewAdapter(this,list,StatementViewAdapter.expenseAdapter);
            statementViewAdapter.notifyDataSetChanged();
            lv_in_statement.setAdapter(statementViewAdapter);
            tv_category.setText(":::  Current week expense  :::");
            tv_filter_balance.setText(String.valueOf(summery[4]));
        }else if(cat==1){//1 for monthly
            list=dbManager.getMonthStatement(dt.getCurrentMonth(), dt.getCurrentYear(), "ex");
            statementViewAdapter=new StatementViewAdapter(this,list,StatementViewAdapter.expenseAdapter);
            statementViewAdapter.notifyDataSetChanged();
            lv_in_statement.setAdapter(statementViewAdapter);
            tv_category.setText(":::  Current month expense  :::");
            tv_filter_balance.setText(String.valueOf(summery[5]));
        }else if(cat==2){//2 for yearly
            list=dbManager.getYearStatement(dt.getCurrentYear(),"ex");
            statementViewAdapter=new StatementViewAdapter(this,list,StatementViewAdapter.expenseAdapter);
            statementViewAdapter.notifyDataSetChanged();
            lv_in_statement.setAdapter(statementViewAdapter);
            tv_category.setText(":::  Current year expense  :::");
            tv_filter_balance.setText(String.valueOf(summery[6]));
        }else if(cat==3){//3 for all
            list=dbManager.getAllStatement("ex");
            statementViewAdapter=new StatementViewAdapter(this,list,StatementViewAdapter.expenseAdapter);
            statementViewAdapter.notifyDataSetChanged();
            lv_in_statement.setAdapter(statementViewAdapter);
            tv_category.setText(":::  All expense  :::");
            tv_filter_balance.setText(String.valueOf(summery[7]));
        }else if(cat==4){//4 for date wise
            //noinspection deprecation
            showDialog(1);
        }else if(cat==5){//5 for category wise
            tv_date_from.setText(dt.getCurrentDate());
            tv_date_to.setText(dt.getCurrentDate());
            lay_category_wise.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.push_up_in));
            lay_category_wise.setVisibility(View.VISIBLE);
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
                startActivity(new Intent(Expense.this, Income.class).putExtra("cat_type", 0));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        });
        LinearLayout ly_expense=(LinearLayout)findViewById(R.id.ly_expense);
        ly_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDrawer.closeDrawer(GravityCompat.START);
                Toast.makeText(getApplicationContext(),"You are here",Toast.LENGTH_LONG).show();
            }
        });
        LinearLayout ly_graph=(LinearLayout)findViewById(R.id.ly_graph);
        ly_graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDrawer.closeDrawer(GravityCompat.START);
                startActivity(new Intent(Expense.this, Graph.class));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        });
        LinearLayout ly_tax_calculator = (LinearLayout) findViewById(R.id.ly_tax_calculator);
        ly_tax_calculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDrawer.closeDrawer(GravityCompat.START);
                startActivity(new Intent(Expense.this, Calculator.class));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Expense.this, OverViewActivity.class));
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        finish();
    }
    private void setBetweenStatement(){
        list=dbManager.getBetweenDateStatement(dt.getDateOrder(firstDate),dt.getDateOrder(secondDate),"ex");
        statementViewAdapter=new StatementViewAdapter(this,list,StatementViewAdapter.expenseAdapter);
        statementViewAdapter.notifyDataSetChanged();
        lv_in_statement.setAdapter(statementViewAdapter);
        tv_category.setText("::: Expense from " + firstDate + " to " + secondDate + " :::");
        tv_filter_balance.setText(String.valueOf(dbManager.getBetweenDateAmount(dt.getDateOrder(firstDate),dt.getDateOrder(secondDate),"ex")));
    }

    @SuppressWarnings("deprecation")
    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 1) {
            return new DatePickerDialog(this, firstDateListener, Integer.valueOf(dt.getCurrentYear()), Integer.valueOf(dt.getCurrentMonth())-1, Integer.valueOf(dt.getCurrentDay()));
        }else if (id == 2) {
            return new DatePickerDialog(this, secondDateListener, Integer.valueOf(dt.getCurrentYear()), Integer.valueOf(dt.getCurrentMonth())-1, Integer.valueOf(dt.getCurrentDay()));
        }else if (id == 3) {
            return new DatePickerDialog(this, dateFrom, Integer.valueOf(dt.getCurrentYear()), Integer.valueOf(dt.getCurrentMonth())-1, Integer.valueOf(dt.getCurrentDay()));
        }else if (id == 4) {
            return new DatePickerDialog(this, dateTo, Integer.valueOf(dt.getCurrentYear()), Integer.valueOf(dt.getCurrentMonth())-1, Integer.valueOf(dt.getCurrentDay()));
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
    private DatePickerDialog.OnDateSetListener dateFrom = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int year, int month, int day) {
            tv_date_from.setText(dt.getDateFromRaw(year, month + 1, day));
        }
    };
    private DatePickerDialog.OnDateSetListener dateTo = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int year, int month, int day) {
            tv_date_to.setText(dt.getDateFromRaw(year, month + 1, day));
        }
    };
    @Override
    public void adapterClick() {
        overridePendingTransition(R.anim.push_up_in,R.anim.style_static);
        finish();
    }

    @Override
    public void addCat(String cName) {
    }

    @Override
    public void removeCat(String cName) {
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
                if (isFirst) {
                    ll.setPadding(0, 0, 0, 200);
                    mAdView.loadAd(adRequest);
                    isFirst = false;
                    mAdView.setVisibility(View.VISIBLE);
                } else {
                    ll.setPadding(0, 0, 0, la.getHeight() + mAdView.getHeight());
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
    public void btn__cancel(View v){
        lay_category_wise.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.push_down_out));
        lay_category_wise.setVisibility(View.GONE);
        setStatementList(i);
        spn_filter_category.setSelection(i, true);
    }
    public void btn_setFilterOk(View v){
        list=dbManager.getBetweenDateStatementWithCat(dt.getDateOrder(tv_date_from.getText().toString()),dt.getDateOrder(tv_date_to.getText().toString()),"ex",spn_filter_category_list.getSelectedItem().toString());
        statementViewAdapter=new StatementViewAdapter(this,list,StatementViewAdapter.expenseAdapter);
        statementViewAdapter.notifyDataSetChanged();
        lv_in_statement.setAdapter(statementViewAdapter);
        tv_category.setText("::: Expense from " + tv_date_from.getText().toString() + " to " + tv_date_to.getText().toString() + ":::");
        tv_filter_balance.setText(String.valueOf(dbManager.getBetweenDateAmountWithCat(dt.getDateOrder(tv_date_from.getText().toString()), dt.getDateOrder(tv_date_to.getText().toString()), "ex", spn_filter_category_list.getSelectedItem().toString())));
        lay_category_wise.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.push_down_out));
        lay_category_wise.setVisibility(View.GONE);
    }
}
