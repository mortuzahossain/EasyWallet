package com.appracks.easy_wallet.view;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appracks.easy_wallet.OverViewActivity;
import com.appracks.easy_wallet.R;
import com.appracks.easy_wallet.adapter.NDSpinner;
import com.appracks.easy_wallet.database.DB_Manager;
import com.appracks.easy_wallet.dateOperation.DateOperation;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;

public class Calculator extends AppCompatActivity {


    DrawerLayout myDrawer;
    ActionBarDrawerToggle actionBarDrawerToggle;
    private NDSpinner spn_filter_category;
    DB_Manager dbManager;
    private PieChart pieChart;
    private BarChart barChart;
    DateOperation dt;
    private String firstDate, secondDate;
    private TextView tv_message, tv_current_balance;
    private Toolbar toolbar;

    float b, h, m, c, f, l, ho, bo, in, ot, ta, nta, ant1, ant2;
    double m1, m2;

    EditText editText1, editText2, editText3, editText4, editText5, editText6, editText7, editText8, editText9, editText10, editText12, editText13, editText14, editText15;
    Button button5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        button5 = (Button) findViewById(R.id.button5);

        editText1 = (EditText) findViewById(R.id.editText1);
        editText2 = (EditText) findViewById(R.id.editText2);
        editText3 = (EditText) findViewById(R.id.editText3);
        editText4 = (EditText) findViewById(R.id.editText4);
        editText5 = (EditText) findViewById(R.id.editText5);
        editText6 = (EditText) findViewById(R.id.editText6);
        editText7 = (EditText) findViewById(R.id.editText7);
        editText8 = (EditText) findViewById(R.id.editText8);
        editText9 = (EditText) findViewById(R.id.editText9);
        editText10 = (EditText) findViewById(R.id.editText10);
        editText12 = (EditText) findViewById(R.id.editText12);
        editText13 = (EditText) findViewById(R.id.editText13);
        editText14 = (EditText) findViewById(R.id.editText14);
        editText15 = (EditText) findViewById(R.id.editText15);


        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    b = Float.parseFloat(editText1.getText().toString());
                    h = Float.parseFloat(editText2.getText().toString());
                    m = Float.parseFloat(editText3.getText().toString());
                    c = Float.parseFloat(editText4.getText().toString());
                    f = Float.parseFloat(editText5.getText().toString());
                    l = Float.parseFloat(editText6.getText().toString());
                    ho = Float.parseFloat(editText7.getText().toString());
                    bo = Float.parseFloat(editText8.getText().toString());
                    in = Float.parseFloat(editText9.getText().toString());
                    ot = Float.parseFloat(editText10.getText().toString());


                    ta = (b + h + l + ho + bo + in + ot);

                    editText12.setText(Float.toString(ta));

                    nta = (m + c + f);
                    editText13.setText(Float.toString(nta));

                    ant1 = ta * 12;
                    ant2 = nta * 12;

                    if (ant1 <= 250000) {
                        editText14.setText("No tax needed");
                    } else {
                        m1 = (ant1 - 250000) * 0.1;
                        editText14.setText(Double.toString(m1));
                    }


                    if (ant2 <= 250000) {
                        editText15.setText("No tax needed");
                    } else {
                        m2 = (ant2 - 250000) * 0.1;
                        editText15.setText(Double.toString(m2));
                    }

                } catch (Exception e) {

                }
            }


        });


        setNavMenu();
    }

    private void setNavMenu() {
        myDrawer = (DrawerLayout) findViewById(R.id.myDrawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, myDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        myDrawer.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        LinearLayout ly_summery = (LinearLayout) findViewById(R.id.ly_summery);
        ly_summery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDrawer.closeDrawer(GravityCompat.START);
                startActivity(new Intent(Calculator.this, OverViewActivity.class));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        });
        LinearLayout ly_income = (LinearLayout) findViewById(R.id.ly_income);
        ly_income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDrawer.closeDrawer(GravityCompat.START);
                startActivity(new Intent(Calculator.this, Income.class).putExtra("cat_type", 0));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        });
        LinearLayout ly_expense = (LinearLayout) findViewById(R.id.ly_expense);
        ly_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDrawer.closeDrawer(GravityCompat.START);
                startActivity(new Intent(Calculator.this, Expense.class).putExtra("cat_type", 0));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        });
        LinearLayout ly_graph = (LinearLayout) findViewById(R.id.ly_graph);
        ly_graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDrawer.closeDrawer(GravityCompat.START);
                startActivity(new Intent(Calculator.this, Graph.class));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        });
        LinearLayout ly_tax_calculator = (LinearLayout) findViewById(R.id.ly_tax_calculator);
        ly_tax_calculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDrawer.closeDrawer(GravityCompat.START);
                Toast.makeText(getApplicationContext(), "You are here", Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        myDrawer.closeDrawer(GravityCompat.START);
        startActivity(new Intent(Calculator.this, OverViewActivity.class));
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        finish();
    }
}
