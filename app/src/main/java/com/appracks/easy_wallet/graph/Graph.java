package com.appracks.easy_wallet.graph;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.appracks.easy_wallet.MainActivity;
import com.appracks.easy_wallet.R;
import com.appracks.easy_wallet.database.DB_Manager;
import com.appracks.easy_wallet.dateOperation.DateOperation;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Graph extends AppCompatActivity {

    private Spinner spn_graph_type,spn_filter_category;
    DB_Manager dbManager;
    private PieChart pieChart;
    DateOperation dt;
    private String firstDate,secondDate;
    private TextView tv_message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        dbManager=DB_Manager.getInstance(this);
        dt=new DateOperation();
        spn_graph_type=(Spinner)findViewById(R.id.spn_graph_type);
        tv_message=(TextView)findViewById(R.id.tv_message);
        spn_filter_category=(Spinner)findViewById(R.id.spn_filter_category);
        String[] list=getResources().getStringArray(R.array.spinner_filter_category);
        spn_graph_type.setAdapter(new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,new String[]{"PIE CHART","BAR CHART"}));
        spn_filter_category.setAdapter(new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,list));
        spn_graph_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setGraph(position, spn_filter_category.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spn_filter_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setGraph(spn_graph_type.getSelectedItemPosition(), position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
            }else{
                //noinspection deprecation
                showDialog(1);
            }
        }else if(type==1){
            if(cat==0){

            }else if(cat==1){

            }else if(cat==2){

            }else if(cat==3){

            }else {

            }
        }
    }
    private void setPieGraph(float inAmount,float exAmount, String sms){
        if(inAmount>0 || exAmount>0){
            tv_message.setText(sms);
        }else{
            tv_message.setText("No data available");
        }
        float[] yData={inAmount,exAmount};
        final String[] xData={"Income","Expense"};
        pieChart=(PieChart)findViewById(R.id.piechart);
        pieChart.setUsePercentValues(true);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColorTransparent(true);
        pieChart.setHoleRadius(15);
        pieChart.setTransparentCircleRadius(10);
        pieChart.setDescription("description");
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
        pieData.setValueTextSize(24f);
        pieData.setValueTextColor(Color.WHITE);
        pieChart.setData(pieData);
        pieChart.highlightValues(null);
        pieChart.invalidate();

        Legend l = pieChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(5);
        l.setYEntrySpace(7);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Graph.this, MainActivity.class));
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
            dbManager.getBetweenDateAmount(firstDate,secondDate,"in");
            setPieGraph((float)dbManager.getBetweenDateAmount(firstDate,secondDate,"in"),(float)dbManager.getBetweenDateAmount(firstDate,secondDate,"ex"),"From "+firstDate+" to "+secondDate);
        }
    };
}
