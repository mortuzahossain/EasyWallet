package com.appracks.easy_wallet.view;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.appracks.easy_wallet.OverViewActivity;
import com.appracks.easy_wallet.R;
import com.appracks.easy_wallet.adapter.NDSpinner;
import com.appracks.easy_wallet.data_object.BarDatas;
import com.appracks.easy_wallet.database.DB_Manager;
import com.appracks.easy_wallet.dateOperation.DateOperation;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

public class Graph extends AppCompatActivity {
    DrawerLayout myDrawer;
    ActionBarDrawerToggle actionBarDrawerToggle;
    private NDSpinner spn_filter_category;
    DB_Manager dbManager;
    private PieChart pieChart;
    private BarChart barChart;
    DateOperation dt;
    private String firstDate,secondDate;
    private TextView tv_message,tv_current_balance;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        toolbar=(Toolbar)findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        dbManager=DB_Manager.getInstance(this);
        dt=new DateOperation();
       // spn_graph_type=(Spinner)findViewById(R.id.spn_graph_type);
        tv_message=(TextView)findViewById(R.id.tv_message);
        tv_current_balance=(TextView)findViewById(R.id.tv_current_balance);
        pieChart=(PieChart)findViewById(R.id.piechart);
        barChart=(BarChart)findViewById(R.id.barchart);
        double[] summary=dbManager.getSummery();
        tv_current_balance.setText(String.valueOf(summary[8]));
        if(summary[8]<0){
            tv_current_balance.setTextColor(Color.RED);
        }
        spn_filter_category=(com.appracks.easy_wallet.adapter.NDSpinner)findViewById(R.id.spn_filter_category);
        String[] list=getResources().getStringArray(R.array.spinner_filter_category_4graph);
       // spn_graph_type.setAdapter(new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,new String[]{"PIE CHART","BAR CHART: INCOME"}));
        spn_filter_category.setAdapter(new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,list));
//        spn_graph_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                setGraph(position, spn_filter_category.getSelectedItemPosition());
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
        spn_filter_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setGraph(0, position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                setGraph(0,0);
            }
        });

        setNavMenu();
        showBannerAds();
    }
    private void setGraph(int type,int cat){
        if(type==0){
            double summary[]=dbManager.getSummery();
            if(cat==0){
                setPieGraph((float)summary[0], (float)summary[4],"");
            }else if(cat==1){
                setPieGraph((float)summary[1],(float)summary[5],"");
            }else if(cat==2){
                setPieGraph((float)summary[2],(float)summary[6],"");
            }else if(cat==3){
                setPieGraph((float)summary[3],(float)summary[7],"");
            }else if(cat==4){
                //noinspection deprecation
                showDialog(1);
            }
            barChart.setVisibility(View.GONE);
            pieChart.setVisibility(View.VISIBLE);
        }else if(type==1){
            if(cat==0){
               // setBarGraph(dbManager.getDateAndAmountBetweenDate(dt.getCurrentDateN7(),dt.getCurrentDate(),"in"),"");
            }else if(cat==1){
              //  setBarGraph(dbManager.getDateAndAmountBetweenDate("01-"+dt.getCurrentMonth()+"-"+dt.getCurrentYear(),dt.getCurrentDate(),"in"),"");
            }else if(cat==2){

            }else if(cat==3){

            }else {

            }
            pieChart.setVisibility(View.GONE);
            barChart.setVisibility(View.VISIBLE);
        }
    }
    private void setBarGraph(ArrayList<BarDatas> list, String sms){
        if(list.size()>0){
            tv_message.setText(sms);
        }else{
            tv_message.setText("No data available");
        }

        barChart.setDescription("");
        barChart.setDrawValueAboveBar(true);

        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                if (e == null) return;
                Toast.makeText(getApplicationContext(), e.getVal() + " /-", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });
        ArrayList<BarEntry> yVals=new ArrayList<BarEntry>();
        ArrayList<String> xVals=new ArrayList<String>();
        for(int i=0;i<list.size();i++){
            yVals.add(new BarEntry(list.get(i).getAmount(),i));
            xVals.add(dt.getDay(list.get(i).getDate()));
        }
        BarDataSet barDataSet=new BarDataSet(yVals,"");

        BarData barData=new BarData(xVals,barDataSet);
        barData.setGroupSpace(0f);
        barChart.setData(barData);
        barChart.highlightValues(null);
        barChart.invalidate();

        Legend l = barChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(5);
        l.setYEntrySpace(7);
    }
    private void setPieGraph(float inAmount,float exAmount, String sms){
        if(inAmount>0 || exAmount>0){
            tv_message.setText(sms);
        }else{
            tv_message.setText("No data available");
        }
        float[] yData={inAmount,exAmount};
        final String[] xData={"Income","Expense"};
        pieChart.setUsePercentValues(true);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColorTransparent(true);
        pieChart.setHoleRadius(15);
        pieChart.setTransparentCircleRadius(10);
        pieChart.setDescription("");
        pieChart.setRotationAngle(0);
        pieChart.setRotationEnabled(true);
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                if (e == null) return;
                Toast.makeText(getApplicationContext(), xData[e.getXIndex()] + " = " + e.getVal() + " /-", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });
        ArrayList<Entry> yVals=new ArrayList<Entry>();
        ArrayList<String> xVals=new ArrayList<String>();
        for(int i=0;i<yData.length;i++){
            yVals.add(new Entry(yData[i],i));
            xVals.add(xData[i]);
        }
        PieDataSet pieDataSet=new PieDataSet(yVals,"");
        pieDataSet.setSliceSpace(6);
        pieDataSet.setSelectionShift(10);

        pieDataSet.setColors(getResources().getIntArray(R.array.color_list_pie));
        PieData pieData=new PieData(xVals,pieDataSet);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(20f);
        pieData.setValueTextColor(Color.WHITE);
        pieChart.setData(pieData);
        pieChart.highlightValues(null);
        pieChart.invalidate();

        Legend l = pieChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(5);
        l.setYEntrySpace(7);
    }
    private void setNavMenu(){
        myDrawer= (DrawerLayout) findViewById(R.id.myDrawer);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this, myDrawer,toolbar, R.string.drawer_open, R.string.drawer_close);
        myDrawer.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        LinearLayout ly_summery=(LinearLayout) findViewById(R.id.ly_summery);
        ly_summery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDrawer.closeDrawer(GravityCompat.START);
                startActivity(new Intent(Graph.this, OverViewActivity.class));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        });
        LinearLayout ly_income=(LinearLayout) findViewById(R.id.ly_income);
        ly_income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDrawer.closeDrawer(GravityCompat.START);
                startActivity(new Intent(Graph.this, Income.class).putExtra("cat_type", 0));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        });
        LinearLayout ly_expense=(LinearLayout) findViewById(R.id.ly_expense);
        ly_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDrawer.closeDrawer(GravityCompat.START);
                startActivity(new Intent(Graph.this, Expense.class).putExtra("cat_type", 0));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        });
        LinearLayout ly_graph=(LinearLayout) findViewById(R.id.ly_graph);
        ly_graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDrawer.closeDrawer(GravityCompat.START);
                Toast.makeText(getApplicationContext(), "You are here", Toast.LENGTH_LONG).show();
            }
        });

        LinearLayout ly_tax_calculator = (LinearLayout) findViewById(R.id.ly_tax_calculator);
        ly_tax_calculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDrawer.closeDrawer(GravityCompat.START);
                startActivity(new Intent(Graph.this, Calculator.class));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        myDrawer.closeDrawer(GravityCompat.START);
        startActivity(new Intent(Graph.this, OverViewActivity.class));
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        finish();
    }
    @SuppressWarnings("deprecation")
    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 1) {
            return new DatePickerDialog(this, pieFirstDate, Integer.valueOf(dt.getCurrentYear()), Integer.valueOf(dt.getCurrentMonth())-1, Integer.valueOf(dt.getCurrentDay()));
        }else if (id == 2) {
            return new DatePickerDialog(this, pieSecondDate, Integer.valueOf(dt.getCurrentYear()), Integer.valueOf(dt.getCurrentMonth())-1, Integer.valueOf(dt.getCurrentDay()));
        }
        return null;
    }
    @SuppressWarnings("deprecation")
    private DatePickerDialog.OnDateSetListener pieFirstDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int year, int month, int day) {
            firstDate=dt.getDateFromRaw(year, month+1, day);
            showDialog(2);
        }
    };
    private DatePickerDialog.OnDateSetListener pieSecondDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int year, int month, int day) {
            secondDate=dt.getDateFromRaw(year, month+1, day);
            setPieGraph((float) dbManager.getBetweenDateAmount(dt.getDateOrder(firstDate), dt.getDateOrder(secondDate), "in"), (float) dbManager.getBetweenDateAmount(dt.getDateOrder(firstDate), dt.getDateOrder(secondDate), "ex"), "From " + firstDate + " to " + secondDate);
        }
    };
    public void showBannerAds(){
        final AdView mAdView = (AdView) findViewById(R.id.adView);
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
