package com.appracks.easy_wallet.income;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.appracks.easy_wallet.MainActivity;
import com.appracks.easy_wallet.R;
import com.appracks.easy_wallet.adapter.StatementViewAdapter;
import com.appracks.easy_wallet.data_object.StatementData;
import com.appracks.easy_wallet.database.DB_Manager;
import com.appracks.easy_wallet.dateOperation.DateOperation;
import java.util.ArrayList;

public class Income extends AppCompatActivity {

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
        setNavMenu();
        Spinner spn_income_filter_category=(Spinner)findViewById(R.id.spn_income_filter_category);
        spn_income_filter_category.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.spinner_filter_category)));
        spn_income_filter_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setStatementList(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                setStatementList(0);
            }
        });
    }
    private void setStatementList(int cat){
        if(cat==0){//0 for weekly
            list=dbManager.getBetweenDateStatement(dt.getDateOrder(dt.getCurrentDateN7()),dt.getCurrentDateOrder(),"in");
            statementViewAdapter=new StatementViewAdapter(this,list,StatementViewAdapter.incomeAdapter);
            statementViewAdapter.notifyDataSetChanged();
            lv_in_statement.setAdapter(statementViewAdapter);
            tv_category.setText(":::  Current week income  :::");
        }else if(cat==1){//1 for monthly
            list=dbManager.getMonthStatement(dt.getCurrentMonth(), dt.getCurrentYear(), "in");
            statementViewAdapter=new StatementViewAdapter(this,list,StatementViewAdapter.incomeAdapter);
            statementViewAdapter.notifyDataSetChanged();
            lv_in_statement.setAdapter(statementViewAdapter);
            tv_category.setText(":::  Current month income  :::");
        }else if(cat==2){//2 for yearly
            list=dbManager.getYearStatement(dt.getCurrentYear(),"in");
            statementViewAdapter=new StatementViewAdapter(this,list,StatementViewAdapter.incomeAdapter);
            statementViewAdapter.notifyDataSetChanged();
            lv_in_statement.setAdapter(statementViewAdapter);
            tv_category.setText(":::  Current year income  :::");
        }else if(cat==3){//3 for all
            list=dbManager.getAllStatement("in");
            statementViewAdapter=new StatementViewAdapter(this,list,StatementViewAdapter.incomeAdapter);
            statementViewAdapter.notifyDataSetChanged();
            lv_in_statement.setAdapter(statementViewAdapter);
            tv_category.setText(":::  All income  :::");
        }else if(cat==4){//4 for date wise
            //noinspection deprecation
            showDialog(1);
            Toast.makeText(getApplicationContext(), "Not calling again", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getApplicationContext(),"ly_expense",Toast.LENGTH_LONG).show();
            }
        });
        LinearLayout ly_graph=(LinearLayout)findViewById(R.id.ly_graph);
        ly_graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDrawer.closeDrawer(GravityCompat.START);
                Toast.makeText(getApplicationContext(), "ly_graph", Toast.LENGTH_LONG).show();
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
        tv_category.setText("::: Income from "+firstDate+" to "+secondDate+" :::");
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
}
